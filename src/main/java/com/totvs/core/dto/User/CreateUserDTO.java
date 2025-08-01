package com.totvs.core.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO(
        @NotBlank(message="Name is required") String name,
        @Email(message="Invalid email format") @NotBlank(message = "Email is required") String email
)
{ }
