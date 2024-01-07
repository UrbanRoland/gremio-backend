package com.gremio.service.interfaces;

import com.gremio.model.dto.TaskDto;
import com.gremio.persistence.entity.Task;
import org.springframework.data.domain.*;
import org.springframework.graphql.data.query.ScrollSubrange;

public interface TaskService {

    /**
     * Adds a new task to the system.
     *
     * @param task The task object to be added.
     * @return The newly added task.
     */
    Task addTask(TaskDto task);
    
    /**
     * Retrieves all tasks from the system that match the given title.
     * @param title The title to match.
     * @return Existing tasks with the given title.
     */
    Window<Task> findAllTasksByTitle(String title, ScrollSubrange subrange);
}