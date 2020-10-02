package com.enzo.springtest.services;

import com.enzo.springtest.dtos.UserDTO;
import com.enzo.springtest.entities.User;

public interface UserService {

    UserDTO findById(Long id);

    User create(UserDTO userDTO);
}
