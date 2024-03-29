package com.gremio.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IssueStatus {

    PASSED("PASSED"),
    FAILED("FAILED"),
    REVIEW("REVIEW");
    private final String name;
    
    
}