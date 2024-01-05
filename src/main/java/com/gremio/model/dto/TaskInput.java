package com.gremio.model.dto;

import com.gremio.enums.TaskStatus;

import java.time.LocalDateTime;

public record TaskInput(String title, LocalDateTime due, Long projectId, String description, TaskStatus status) {
}