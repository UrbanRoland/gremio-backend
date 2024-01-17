package com.gremio.model.input;

import com.gremio.enums.IssueStatus;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record IssueInput(@NotEmpty String title, LocalDateTime due, Long projectId, String description, IssueStatus status) {
}