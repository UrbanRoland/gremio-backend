package com.gremio.model.dto.filter;

import com.gremio.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TaskFilter {
    private String title;
    private String description;
    private TaskStatus status;
}