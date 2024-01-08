package com.gremio.model.dto.response.archive;

import com.gremio.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {
    private Long id;
    private String email;
    private RoleType role;

}