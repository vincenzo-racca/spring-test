package com.enzo.springtest.controllers;

import com.enzo.springtest.dtos.UserDTO;
import com.enzo.springtest.entities.User;
import com.enzo.springtest.exceptions.UserNotFoundException;
import com.enzo.springtest.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;


@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserDTO userDTO, HttpServletResponse response, HttpServletRequest request) throws UnknownHostException {
        User userCreated = userService.create(userDTO);
        String requestPath = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + request.getServerPort() + request.getContextPath();
        UriComponents uriComponents =
                UriComponentsBuilder.fromUriString(requestPath + "/users/" + userCreated.getId()).build();

        response.addHeader("Location", uriComponents.toUriString());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({UserNotFoundException.class})
    public void handleNotFound() {
        // just return empty 404
    }


}
