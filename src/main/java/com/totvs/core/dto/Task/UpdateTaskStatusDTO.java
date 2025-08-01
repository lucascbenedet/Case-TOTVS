package com.totvs.core.dto.Task;

import com.totvs.core.domain.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;

public record UpdateTaskStatusDTO(
        @NotBlank(message = "Status is required")
        TaskStatus status
) {
}
