package com.enzo.springtest;

import com.enzo.springtest.dtos.UserDTO;
import com.enzo.springtest.entities.User;
import com.enzo.springtest.repos.UserRepository;
import com.enzo.springtest.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void createUserTest() {
        assertEquals(0, userRepository.count());
        UserDTO userDTO = new UserDTO("Racca Vincenzo", "via Roma");
        User user = userService.create(userDTO);
        Optional<User> userFromDB = userRepository.findById(user.getId());
        assertTrue(userFromDB.isPresent());
        assertEquals(userDTO.getName().split(" ")[0], userFromDB.get().getName());
        assertEquals(userDTO.getAddress(), userFromDB.get().getAddress());
    }

    @Test
    @Transactional
    public void findByIdTest() {
        User user = new User("Vincenzo", "Racca", "via Roma");
        userRepository.save(user);
        assertNotNull(user.getId());
        UserDTO userDTO = userService.findById(user.getId());

        assertEquals(user.getName(), userDTO.getName().split(" ")[1]);
        assertEquals(user.getSurname(), userDTO.getName().split(" ")[0]);
        assertEquals(user.getAddress(), userDTO.getAddress());
    }
}
