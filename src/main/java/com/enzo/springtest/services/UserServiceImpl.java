package com.enzo.springtest.services;

import com.enzo.springtest.converters.UserConverter;
import com.enzo.springtest.dtos.UserDTO;
import com.enzo.springtest.entities.User;
import com.enzo.springtest.exceptions.UserNotFoundException;
import com.enzo.springtest.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    private UserConverter userConverter;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            UserNotFoundException exp = new UserNotFoundException(id);
            LOGGER.error("Exception is UserServiceImpl.findById", exp);
            return exp;
        });
        return userConverter.userToUserDTO(user);
    }

    @Override
    public User create(UserDTO userDTO) {
        User user = userConverter.userDTOToUser(userDTO);
        return userRepository.save(user);
    }
}
