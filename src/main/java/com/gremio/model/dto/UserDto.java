package com.gremio.model.dto;

import com.gremio.enums.RoleType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String email;
    private String password;
    private String accessToken;
    private String refreshToken;
    private RoleType role;
}