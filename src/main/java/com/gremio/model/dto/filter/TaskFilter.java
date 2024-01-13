package com.gremio.model.dto.filter;

import com.gremio.enums.TaskStatus;

public record TaskFilter(String title, String description, TaskStatus status ) {

}