package com.totvs.core.service;

import com.totvs.core.domain.User.User;
import com.totvs.core.dto.User.CreateUserDTO;
import com.totvs.core.dto.User.UserResponseDTO;
import com.totvs.core.exceptions.DuplicateEmailException;
import com.totvs.core.mappers.UserMapper;
import com.totvs.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @InjectMocks
    private UserService userService;

    @Test
    void userEmailAlreadyExists() {
        when(userRepository.existsByEmail(any())).thenReturn(true);

        DuplicateEmailException exception = assertThrows(DuplicateEmailException.class, () -> {
            CreateUserDTO userDTO = new CreateUserDTO("lucas", "lucasbenedet8@gmail.com");
            userService.createUser(userDTO);
        });
    }

    @Test
    void userEmailDoesNotExist() {
        when(userRepository.existsByEmail(any())).thenReturn(false);
        CreateUserDTO userDTO = new CreateUserDTO("lucas", "lucasbenedet8@gmail.com");

        User savedUser = new User(UUID.randomUUID(), userDTO.name(), userDTO.email());
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        userService.createUser(userDTO);
        verify(userRepository, times(1)).save(userCaptor.capture());
        User captured =  userCaptor.getValue();

        assert(captured.getEmail().equals(userDTO.email()));
        assert(captured.getName().equals(userDTO.name()));
    }
}