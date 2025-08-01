package com.totvs.core.service;

import com.totvs.core.domain.Task.Task;
import com.totvs.core.domain.User.User;
import com.totvs.core.domain.enums.TaskStatus;
import com.totvs.core.dto.Task.*;
import com.totvs.core.dto.Task.TaskResponseDTO;
import com.totvs.core.mappers.TaskMapper;
import com.totvs.core.repository.TaskRepository;
import com.totvs.core.repository.SubTaskRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.totvs.core.specification.TaskSpecification;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private SubTaskRepository subTaskRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskMapper taskMapper;


    public Page<TaskResponseDTO> listFilteredTasks(TaskFilter taskFilter, Pageable pageable) {
        Specification<Task> spec = TaskSpecification.hasUser(taskFilter.user())
                        .and(TaskSpecification.createdAfter(taskFilter.createdAfter()))
                        .and(TaskSpecification.createdBefore(taskFilter.createdBefore()))
                        .and(TaskSpecification.byStatus(taskFilter.status()));
        return this.taskRepository.findAll(spec, pageable)
                .map(task ->  this.taskMapper.toTaskResponseDTO(task));
    }


    @Transactional
    public TaskResponseDTO createTask(CreateTaskDTO data) {
        User user = this.userService.findById(data.user());
        Task newTask = new Task();

        newTask.setTitle(data.title());
        newTask.setDescription(data.description());
        newTask.setUser(user);


        Task saved = this.taskRepository.save(newTask);
        return this.taskMapper.toTaskResponseDTO(saved);

    }
    @Transactional
    public TaskResponseDTO updateTask(UUID id, UpdateTaskDTO data) {
        Task task = this.findById(id);

        data.title().ifPresent(task::setTitle);
        data.description().ifPresent(task::setDescription);
        data.user().ifPresent(uid ->
        {
            if (!uid.equals(task.getUser().getId())) {
                User newUser = this.userService.findById(uid);
                task.setUser(newUser);
            }

        });

        data.status().ifPresent(newStatus -> {
            if (newStatus == TaskStatus.COMPLETED) {
                if(task.hasPendingSubTasks(() ->
                        subTaskRepository.countByTask_IdAndStatusNot(task.getId(),TaskStatus.COMPLETED))) {
                    throw new IllegalStateException("Task status can not be changed to COMPLETED because has pending sub-tasks");
                }
                task.setEndedAt(LocalDateTime.now());


            }
            task.setStatus(newStatus);

        });

        Task updatedTask = this.taskRepository.save(task);
        return this.taskMapper.toTaskResponseDTO(updatedTask);


    }

    @Transactional
    public TaskResponseDTO updateTaskStatus(UUID id, TaskStatus status) {
        UpdateTaskDTO taskDTO = new UpdateTaskDTO(
                Optional.empty(),
                Optional.empty(),
                Optional.of(status),
                Optional.empty()
        );
        return this.updateTask(id, taskDTO);
    }


    public Task findById(UUID id) {
        return this.taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

}
