package com.enzo.springtest;

import com.enzo.springtest.dtos.UserDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";


    private static RestTemplate restTemplate = null;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public boolean hasError(HttpStatus statusCode) {
                return false;
            }
        });
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port+ "").concat("/users");
    }

    @Test
    @Sql(statements = "INSERT INTO user (id, name, surname, address) VALUES (1, 'Vincenzo', 'Racca', 'via Roma')",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM user",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void returnAPersonWithIdOne() {

        UserDTO userDTOResponse = restTemplate.getForObject(baseUrl.concat("/{id}"), UserDTO.class, 1);
        assertAll(
                () -> assertNotNull(userDTOResponse),
                () -> assertEquals("Racca Vincenzo", userDTOResponse.getName())
        );
    }

    @Test
    public void return404() {
        ResponseEntity<String> err = restTemplate.getForEntity
                (baseUrl.concat("/{id}"), String.class, 1);

        assertAll(
                () -> assertNotNull(err),
                () -> assertEquals(HttpStatus.NOT_FOUND, err.getStatusCode()),
                () -> assertNull(err.getBody())
        );
    }

    @Test
    public void createUserAndReturn201HttpStatus() {
        UserDTO userDTO = new UserDTO("Racca Vincenzo", "via Roma");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDTO> postRequest = new HttpEntity<>(userDTO, headers);
        URI uri = restTemplate.postForLocation(baseUrl, postRequest, UserDTO.class);
        System.out.println("URI: " + uri);

        assertNotNull(uri);

        UserDTO newUserDTO = restTemplate.getForObject(uri, UserDTO.class);
        assertAll(
                () -> assertNotNull(newUserDTO),
                () -> assertEquals(userDTO.getName(), userDTO.getName()),
                () -> assertEquals(userDTO.getAddress(), userDTO.getAddress())
        );
    }

}
