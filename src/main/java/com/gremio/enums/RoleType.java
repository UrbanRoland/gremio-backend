package com.gremio.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum RoleType {
    ROLE_USER("ROLE_USER"),
    ROLE_PM("ROLE_PM"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_READ_ONLY("ROLE_READ_ONLY");

    private final String name;
}