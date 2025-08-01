package com.totvs.core.service;

import com.totvs.core.domain.Task.Task;
import com.totvs.core.domain.User.User;
import com.totvs.core.dto.Task.CreateTaskDTO;
import com.totvs.core.mappers.TaskMapper;
import com.totvs.core.repository.SubTaskRepository;
import com.totvs.core.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private SubTaskRepository subTaskRepository;
    @Mock
    private UserService userService;
    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @Test
    @DisplayName("Should create task successfully")
    void createTaskCase1() {
        UUID randomUUID = UUID.randomUUID();
        User user = new User(randomUUID, "lucas", "lucasbenedet8@gmail.com");
        when(userService.findById(randomUUID)).thenReturn(user);

        CreateTaskDTO newTask = new CreateTaskDTO("teste", "teste", randomUUID);
        taskService.createTask(newTask);
        verify(taskRepository, times(1)).save(any(Task.class));

    }

    @DisplayName("Throw exception ")
    @Test
    void createTaskCase2() {
    }

    @Test
    void hasPendingSubTasks() {
    }
}