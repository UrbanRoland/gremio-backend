package com.gremio.controller;

import com.gremio.model.dto.TaskInput;
import com.gremio.persistence.entity.Task;
import com.gremio.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Window;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.query.ScrollSubrange;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TaskController{
    private final TaskService taskService;

    /**
     * Creates a new task and adds it to the system.
     *
     * @param task The task object to be added.
     * @return A ResponseEntity containing the newly created task and HTTP status 201 (Created).
     */
   // @PreAuthorize("!hasAuthority('ROLE_READ_ONLY')")
    @MutationMapping
    public Task createTask(@Argument TaskInput task) {
        return taskService.addTask(task);
    }
    
    /**
     * Retrieves all tasks from the system that match the given title.
     * @param title The title to match.
     * @return Existing tasks with the given title.
     */
    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public Window<Task> findAllTasksByTitle(@Argument String title, ScrollSubrange subrange) {

        return taskService.findAllTasksByTitle(title, subrange);
    }
    
    


}