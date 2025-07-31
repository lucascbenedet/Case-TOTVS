package com.totvs.core.dto.Task;

import jakarta.validation.constraints.NotBlank;
import com.totvs.core.domain.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateTaskRequest(
        @NotBlank(message = "Title is required") String title,
        String description,
        @NotNull(message = "User id is required") UUID user
)
{ }
