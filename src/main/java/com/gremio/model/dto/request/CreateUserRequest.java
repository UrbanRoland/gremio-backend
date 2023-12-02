package com.gremio.model.dto.request;

import com.gremio.enums.RoleType;
import lombok.Data;
@Data
public class CreateUserRequest {
    private String email;
    private String password;
    private RoleType role;
    private String firstName;
    private String lastName;
}