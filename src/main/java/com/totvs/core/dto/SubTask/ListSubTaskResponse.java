package com.totvs.core.dto.SubTask;

import com.totvs.core.domain.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ListSubTaskResponse(UUID id,
                                  String title,
                                  String description,
                                  LocalDateTime createdAt,
                                  LocalDateTime endedAt,
                                  TaskStatus status,
                                  UUID taskId) {
}
