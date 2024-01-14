package com.gremio.model.input;

import com.gremio.enums.TaskStatus;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record TaskInput(@NotEmpty String title, LocalDateTime due, Long projectId, String description, TaskStatus status) {
}