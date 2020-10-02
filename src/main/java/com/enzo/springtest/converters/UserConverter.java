package com.enzo.springtest.converters;

import com.enzo.springtest.dtos.UserDTO;
import com.enzo.springtest.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {


    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user.getSurname() + " " + user.getName(), user.getAddress());
    }

    public User userDTOToUser(UserDTO userDTO) {
        String[] surnameAndName = userDTO.getName().split(" ");
        return new User(surnameAndName[1], surnameAndName[0], userDTO.getAddress());
    }
}
