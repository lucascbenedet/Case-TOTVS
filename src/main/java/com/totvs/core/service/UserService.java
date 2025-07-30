package com.totvs.core.service;

import com.totvs.core.domain.User.*;
import com.totvs.core.dto.User.*;
import com.totvs.core.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User createUser(CreateUserRequest data) {
        User newUser = new User();

        newUser.setEmail(data.email());
        newUser.setName(data.name());
        return userRepository.save(newUser);
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
