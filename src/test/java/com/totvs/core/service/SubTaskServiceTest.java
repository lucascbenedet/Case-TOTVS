package com.totvs.core.service;

import com.totvs.core.domain.SubTask.SubTask;
import com.totvs.core.domain.enums.TaskStatus;
import com.totvs.core.dto.SubTask.UpdateSubTaskDTO;
import com.totvs.core.dto.SubTask.UpdateSubTaskStatusDTO;
import com.totvs.core.dto.Task.UpdateTaskStatusDTO;
import com.totvs.core.mappers.SubTaskMapper;
import com.totvs.core.repository.SubTaskRepository;
import com.totvs.core.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubTaskServiceTest {

    @Mock
    private SubTaskRepository subTaskRepository;
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private SubTaskMapper subTaskMapper;

    @InjectMocks
    private SubTaskService subTaskService;

    @Test
    void updateSubTaskStatus() {
        TaskStatus update_status_to = TaskStatus.IN_PROGRESS;
        UUID id = UUID.randomUUID();

        when(subTaskRepository.findById(id)).thenReturn(Optional.of(new SubTask()));

        subTaskService.updateSubTaskStatus(id, update_status_to);

    }


}