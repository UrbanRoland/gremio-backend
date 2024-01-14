package com.gremio.service.interfaces;

import com.gremio.model.dto.filter.TaskFilter;
import com.gremio.model.input.TaskInput;
import com.gremio.persistence.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Window;
import org.springframework.graphql.data.query.ScrollSubrange;

public interface TaskService {

    /**
     * Adds a new task to the system.
     *
     * @param task The task object to be added.
     * @return The newly added task.
     */
    Task addTask(TaskInput task);

    /**
     * Retrieves all tasks from the system that match the given title.
     * @param title The title to match.
     * @return Existing tasks with the given title.
     */
    Window<Task> findAllTasksByTitle(String title, ScrollSubrange subrange);

    /**
     * Retrieves all tasks from the system that match the given filter.
     * @param taskFilter The filter to match.
     * @return Existing tasks with the given filter.
     */
    Page<Task> findTasksByFilter(TaskFilter taskFilter, Pageable pageable);
}