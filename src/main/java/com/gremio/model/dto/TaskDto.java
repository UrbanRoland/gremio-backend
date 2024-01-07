package com.gremio.model.dto;

import com.gremio.enums.TaskStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TaskDto(String title, LocalDateTime due, Long projectId, String description, TaskStatus status) {
}