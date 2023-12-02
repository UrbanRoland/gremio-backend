package com.gremio.model.dto;

import com.gremio.enums.TaskStatus;
import com.gremio.persistence.entity.Project;
import com.gremio.persistence.entity.User;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private User assignee;
    private String title;
    private Date due;
    private List<Project> project;
    private String description;
    private TaskStatus status;
}