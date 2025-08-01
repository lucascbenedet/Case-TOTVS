package com.totvs.core.dto.User;

import java.util.UUID;

public record UserResponseDTO(UUID id,
                              String name,
                              String email) {
}
