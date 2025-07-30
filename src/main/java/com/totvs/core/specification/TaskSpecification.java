package com.totvs.core.specification;

import com.totvs.core.domain.Task.Task;
import com.totvs.core.domain.enums.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.UUID;

public class TaskSpecification {
    public static Specification<Task> byStatus(TaskStatus status) {
        return (root, query, cb) -> status == null
                ? cb.conjunction():cb.equal(root.get("status"), status);
    }

    public static Specification<Task> hasUser(UUID userId) {
        return (root, query, cb) ->
                userId == null
                        ? cb.conjunction()
                        : cb.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Task> createdAfter(LocalDateTime date) {
        return (root, query, cb) ->
                date == null
                        ? cb.conjunction()
                        : cb.greaterThanOrEqualTo(root.get("createdAt"), date);
    }

    public static Specification<Task> createdBefore(LocalDateTime date) {
        return (root, query, cb) ->
                date == null
                        ? cb.conjunction()
                        : cb.lessThanOrEqualTo(root.get("createdAt"), date);
    }

}

