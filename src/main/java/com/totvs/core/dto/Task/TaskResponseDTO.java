package com.totvs.core.dto.Task;

import com.totvs.core.domain.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponseDTO(UUID id,
                              String title,
                              String description,
                              LocalDateTime createdAt,
                              LocalDateTime endedAt,
                              TaskStatus status,
                              UUID user) {
}
