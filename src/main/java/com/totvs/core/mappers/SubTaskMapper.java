package com.totvs.core.mappers;


import com.totvs.core.domain.SubTask.SubTask;
import com.totvs.core.dto.SubTask.SubTaskResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubTaskMapper {
    @Mapping(source = "task.id", target = "taskId")
    SubTaskResponseDTO toSubTaskResponseDTO(SubTask subTask);
}
