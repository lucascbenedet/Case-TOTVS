package com.totvs.core.repository;

import com.totvs.core.domain.SubTask.SubTask;
import com.totvs.core.domain.enums.TaskStatus;
import com.totvs.core.dto.SubTask.ListSubTaskResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubTaskRepository extends JpaRepository<SubTask, UUID> {
    long countByTask_IdAndStatusNot(UUID taskId, TaskStatus status);

    //@EntityGraph(attributePaths = {"task", "task.user"})
    List<SubTask> findByTask_Id(UUID taskId);
}
