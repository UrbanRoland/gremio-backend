package com.gremio.model.dto;

import com.gremio.enums.TaskStatus;

import java.util.Date;

public record TaskInput(String title, Date due, Long projectId, String description, TaskStatus status) {
}