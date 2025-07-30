package com.totvs.core.domain.SubTask;

import com.totvs.core.domain.Task.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import com.totvs.core.domain.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subtasks")
public class SubTask {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    @NotNull
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.PENDING;

    private LocalDateTime createdAt =  LocalDateTime.now();

    private LocalDateTime endedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false )
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
}

