package com.totvs.core.controller;

import com.totvs.core.dto.User.ListUserResponse;
import com.totvs.core.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.totvs.core.domain.User.User;
import com.totvs.core.dto.User.CreateUserRequest;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserRequest data) {
        User newUser = this.userService.createUser(data);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ListUserResponse> getUserById(@PathVariable UUID userId) {
        ListUserResponse user = this.userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
}
