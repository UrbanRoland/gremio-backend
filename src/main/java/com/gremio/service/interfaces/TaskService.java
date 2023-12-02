package com.gremio.service.interfaces;

import com.gremio.model.dto.filter.TaskFilter;
import com.gremio.persistence.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    /**
     * Adds a new task to the system.
     *
     * @param task The task object to be added.
     * @return The newly added task.
     */
    Task addTask(Task task);

    /**
     * Retrieves a page of tasks based on the provided task filter and pagination information.
     *
     * @param taskFilter The task filter to apply for retrieving tasks.
     * @param pageable   The pagination information.
     * @return A page of tasks that match the specified filter criteria.
     */
    Page<Task> getAllByFilter(TaskFilter taskFilter, Pageable pageable);
}