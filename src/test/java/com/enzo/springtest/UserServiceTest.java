package com.enzo.springtest;

import com.enzo.springtest.converters.UserConverter;
import com.enzo.springtest.dtos.UserDTO;
import com.enzo.springtest.entities.User;
import com.enzo.springtest.exceptions.UserNotFoundException;
import com.enzo.springtest.repos.UserRepository;
import com.enzo.springtest.services.UserService;
import com.enzo.springtest.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    private UserConverter userConverter;

    private UserService userService;

    @BeforeEach
    public void init() {
        userService = new UserServiceImpl(userRepository, userConverter);
    }

    @Test
    public void findByIdSuccess() {
        User user = new User("Vincenzo", "Racca", "via Roma");
        user.setId(1L);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        UserDTO userDTO = userService.findById(1L);

        verify(userRepository, times(1)).findById(anyLong());

        assertNotNull(userDTO);
        String[] surnameAndName = userDTO.getName().split( " ");
        assertEquals(2, surnameAndName.length);
        assertEquals(user.getSurname(), surnameAndName[0]);
        assertEquals(user.getName(), surnameAndName[1]);
        assertEquals(user.getAddress(), userDTO.getAddress());
    }

    @Test
    public void findByIdUnSuccess() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        UserNotFoundException exp = assertThrows(UserNotFoundException.class, () -> userService.findById(1L));
        assertEquals("User with id 1 not found!", exp.getMessage());
    }

    @Test
    public void createTest() {
        User user = new User("Vincenzo", "Racca", "via Roma");
        user.setId(1L);
        when(userRepository.save(user)).thenReturn(user);

        UserDTO userDTO = new UserDTO("Racca Vincenzo", "via Roma");
        User userRet = userService.create(userDTO);
        assertNotNull(userRet);
        assertEquals(user, userRet);
    }
}
