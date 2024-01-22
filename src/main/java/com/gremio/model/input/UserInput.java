package com.gremio.model.input;

import com.gremio.enums.RoleType;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record UserInput(Long id, @NotEmpty String email, @NotEmpty String password, String firstName,
                 String lastName, RoleType role, String token) {
}