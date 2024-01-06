package com.gremio.model.dto;

import com.gremio.enums.RoleType;

public record UserInput(Long id, String email, String password, String firstName, String lastName, RoleType role) {
}