package com.totvs.core.service;

import com.totvs.core.domain.Task.Task;
import com.totvs.core.domain.User.User;
import com.totvs.core.domain.enums.TaskStatus;
import com.totvs.core.dto.Task.*;
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


    public List<Task> findFilteredTasks(TaskFIlter taskFIlter) {
        Specification<Task> spec = Specification.unrestricted();
        spec = spec.and(TaskSpecification.hasUser(taskFIlter.user()))
                        .and(TaskSpecification.createdAfter(taskFIlter.createdAfter()))
                        .and(TaskSpecification.createdBefore(taskFIlter.createdBefore()))
                        .and(TaskSpecification.byStatus(taskFIlter.status()));
        return taskRepository.findAll(spec);
    }


    @Transactional
    public Task createTask(CreateTaskRequest data) {
        User user = userRepository.findById(data.user()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Task newTask = new Task();

        newTask.setTitle(data.title());
        newTask.setDescription(data.description());
        newTask.setUser(user);

        return taskRepository.save(newTask);

    }
    @Transactional
    public Task updateTask(UUID id, UpdateTaskRequest data) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found"));

        data.title().ifPresent(task::setTitle);
        data.description().ifPresent(task::setDescription);
        data.user()
                .filter(uid -> !uid.equals(task.getUser().getId()))
                .map(userRepository::findById).orElseThrow(() -> new EntityNotFoundException("User not found"))
                .ifPresent(task::setUser);

        data.status().ifPresent(newStatus -> {
            if (newStatus == TaskStatus.COMPLETED) {
                long pendingSubTasks = subTaskRepository.countByTask_IdAndStatusNot(task.getId(),TaskStatus.COMPLETED);
                if (pendingSubTasks > 0) {
                    throw new IllegalStateException("Task status can not be changed to COMPLETED. Task has" + pendingSubTasks + " sub tasks.");
                }
                task.setEndedAt(LocalDateTime.now());


            }
            task.setStatus(newStatus);

        });

        return taskRepository.save(task);


    }

}
