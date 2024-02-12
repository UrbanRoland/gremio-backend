package com.gremio.model.dto.filter;

import com.gremio.enums.IssueStatus;
import lombok.Builder;

@Builder
public record IssueFilter(String title, String description, IssueStatus status ) {

}