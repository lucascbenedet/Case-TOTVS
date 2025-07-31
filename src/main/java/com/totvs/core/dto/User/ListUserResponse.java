package com.totvs.core.dto.User;

import java.util.UUID;

public record ListUserResponse(UUID id,
                               String name,
                               String email) {
}
