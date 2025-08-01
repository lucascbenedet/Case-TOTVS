package com.totvs.core.controller;

import com.totvs.core.dto.User.UserResponseDTO;
import com.totvs.core.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.totvs.core.domain.User.User;
import com.totvs.core.dto.User.CreateUserDTO;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User endpoints")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserDTO data) {
        User newUser = this.userService.createUser(data);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID userId) {
        UserResponseDTO user = this.userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
}
