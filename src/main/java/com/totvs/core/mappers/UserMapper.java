package com.totvs.core.mappers;

import com.totvs.core.domain.User.User;
import com.totvs.core.dto.User.UserResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toUserResponseDTO(User user);
}
