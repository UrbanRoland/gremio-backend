package com.gremio.model.dto.request;

import com.gremio.enums.RoleType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserRequest {

    private String email;

    private String password;

    @NotNull(message = "role required")
    private RoleType role;
}