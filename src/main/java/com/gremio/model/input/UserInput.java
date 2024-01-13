package com.gremio.model.input;

import com.gremio.enums.RoleType;
import lombok.Builder;

@Builder
public record UserInput(Long id, String email, String password, String firstName, String lastName, RoleType role, String token) {
}