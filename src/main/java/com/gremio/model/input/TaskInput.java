package com.gremio.model.input;

import com.gremio.enums.TaskStatus;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record TaskInput(String title, LocalDateTime due, Long projectId, String description, TaskStatus status) {
}