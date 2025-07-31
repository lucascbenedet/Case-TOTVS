package com.totvs.core.service;

import com.totvs.core.domain.Task.Task;
import com.totvs.core.domain.User.User;
import com.totvs.core.domain.enums.TaskStatus;
import com.totvs.core.dto.Task.*;
import com.totvs.core.dto.Task.ListTasksResponse;
import com.totvs.core.repository.TaskRepository;
import com.totvs.core.repository.UserRepository;
import com.totvs.core.repository.SubTaskRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.totvs.core.specification.TaskSpecification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubTaskRepository subTaskRepository;


    public List<ListTasksResponse> listFilteredTasks(TaskFilter taskFilter) {
        Specification<Task> spec = TaskSpecification.hasUser(taskFilter.user())
                        .and(TaskSpecification.createdAfter(taskFilter.createdAfter()))
                        .and(TaskSpecification.createdBefore(taskFilter.createdBefore()))
                        .and(TaskSpecification.byStatus(taskFilter.status()));
        return taskRepository.findAll(spec).stream()
                .map(task -> new ListTasksResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getCreatedAt(),
                        task.getEndedAt(),
                        task.getStatus(),
                        task.getUser().getId()
//                        new RetrieveUser(
//                                task.getUser().getId(),
//                                task.getUser().getName(),
//                                task.getUser().getEmail())
                        )
                ).toList();

    }


    @Transactional
    public CreateTaskResponse createTask(CreateTaskRequest data) {
        User user = userRepository.findById(data.user()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Task newTask = new Task();

        newTask.setTitle(data.title());
        newTask.setDescription(data.description());
        newTask.setUser(user);


        Task saved = taskRepository.save(newTask);
        return new CreateTaskResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getCreatedAt(),
                saved.getEndedAt(),
                saved.getStatus(),
                saved.getUser().getId()
        );

    }
    @Transactional
    public CreateTaskResponse updateTask(UUID id, UpdateTaskRequest data) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found"));

        data.title().ifPresent(task::setTitle);
        data.description().ifPresent(task::setDescription);
        data.user().ifPresent(uid ->
        {
            if (!uid.equals(task.getUser().getId())) {
                User newUser = userRepository.findById(uid).orElseThrow(() -> new EntityNotFoundException("User not found"));
                task.setUser(newUser);
            }

        });

        data.status().ifPresent(newStatus -> {
            if (newStatus == TaskStatus.COMPLETED) {
                long pendingSubTasks = subTaskRepository.countByTask_IdAndStatusNot(task.getId(),TaskStatus.COMPLETED);
                if (pendingSubTasks > 0) {
                    throw new IllegalStateException("Task status can not be changed to COMPLETED. Task has " + pendingSubTasks + " sub tasks pending.");
                }
                task.setEndedAt(LocalDateTime.now());


            }
            task.setStatus(newStatus);

        });

        Task updatedTask = taskRepository.save(task);
        return new CreateTaskResponse(
                updatedTask.getId(),
                updatedTask.getTitle(),
                updatedTask.getDescription(),
                updatedTask.getCreatedAt(),
                updatedTask.getEndedAt(),
                updatedTask.getStatus(),
                updatedTask.getUser().getId()
//                new ListUserResponse(
//                        updatedTask.getUser().getId(),
//                        updatedTask.getUser().getName(),
//                        updatedTask.getUser().getEmail())
               );


    }

}
