package com.gremio.model.dto;

import com.gremio.enums.RoleType;
import lombok.Builder;

@Builder
public record UserDto(Long id, String email, String password, String firstName, String lastName, RoleType role, String token) {
}