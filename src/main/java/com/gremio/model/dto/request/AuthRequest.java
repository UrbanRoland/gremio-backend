package com.gremio.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
@Data
public class AuthRequest {
    @NotEmpty(message = "email is mandatory")
    private String email;

    @NotBlank(message = "password is mandatory")
    private String password;
}