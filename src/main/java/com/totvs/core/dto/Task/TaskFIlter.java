package com.totvs.core.dto.Task;

import com.totvs.core.domain.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskFIlter(
        TaskStatus status,
        UUID user,
        LocalDateTime createdAfter,
        LocalDateTime createdBefore,
        String descriptionContains
){}