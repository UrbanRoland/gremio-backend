package com.gremio.model.dto.filter;

import com.gremio.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




public record TaskFilter(String title, String description, TaskStatus status ) {

}