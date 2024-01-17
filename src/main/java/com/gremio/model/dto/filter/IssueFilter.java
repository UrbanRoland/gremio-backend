package com.gremio.model.dto.filter;

import com.gremio.enums.IssueStatus;

public record IssueFilter(String title, String description, IssueStatus status ) {

}