package com.totvs.core.service;

import com.totvs.core.domain.SubTask.SubTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import com.totvs.core.repository.SubTaskRepository;

@Service
public class SubTaskService {

    @Autowired
    private SubTaskRepository subTaskRepository;


    public List<SubTask> getSubTasksByTaskId(UUID taskId) {
        return subTaskRepository.findByTask_Id(taskId);
    }

}
