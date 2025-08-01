package com.totvs.core.mappers;


import com.totvs.core.domain.Task.Task;
import com.totvs.core.dto.Task.TaskResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(source = "user.id", target = "user")
    TaskResponseDTO toTaskResponseDTO(Task task);
}
