package com.gremio.controller;

import com.gremio.model.dto.TaskDto;
import com.gremio.model.dto.filter.TaskFilter;
import com.gremio.model.dto.response.PageableResponse;
import com.gremio.persistence.entity.Task;
import com.gremio.service.interfaces.TaskService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController extends AbstractController {
    private final TaskService taskService;

    TaskController(final ConversionService conversionService, final TaskService taskService) {
        super(conversionService);
        this.taskService = taskService;
    }

    /**
     * Creates a new task and adds it to the system.
     *
     * @param task The task object to be added.
     * @return A ResponseEntity containing the newly created task and HTTP status 201 (Created).
     */
    @PostMapping(value = "/create")
    public ResponseEntity<Task> addTask(@RequestBody final Task task) {
        return new ResponseEntity<>(taskService.addTask(task), HttpStatus.CREATED);
    }

    /**
     * Retrieves a paginated list of tasks based on the provided task filter.
     * Requires "ROLE_ADMIN" authority.
     *
     * @param taskFilter The task filter to apply for retrieving tasks.
     * @param pageable   The pagination information.
     * @return A paginated response containing a list of TaskDto objects.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public PageableResponse<TaskDto> getAllByFilter(final TaskFilter taskFilter, final Pageable pageable) {
        return this.getPageableResponse((taskService.getAllByFilter(taskFilter, pageable)), TaskDto.class);
    }

}