package com.gremio.model.dto.response;

import com.gremio.enums.RoleType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private Long id;
    private String email;
    private String accessToken;
    private String refreshToken;
    private RoleType role;
}