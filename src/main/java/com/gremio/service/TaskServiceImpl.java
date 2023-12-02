package com.gremio.service;

import com.gremio.model.dto.filter.TaskFilter;
import com.gremio.persistence.entity.Task;
import com.gremio.persistence.specification.TaskSpecification;
import com.gremio.repository.TaskRepository;
import com.gremio.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public Task addTask(final Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Page<Task> getAllByFilter(final TaskFilter taskFilter, final Pageable pageable) {
        return taskRepository.findAll(new TaskSpecification(taskFilter), pageable);
    }
}