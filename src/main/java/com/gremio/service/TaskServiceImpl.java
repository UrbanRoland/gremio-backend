package com.gremio.service;

import com.gremio.model.dto.filter.TaskFilter;
import com.gremio.model.input.TaskInput;
import com.gremio.persistence.entity.Project;
import com.gremio.persistence.entity.Task;
import com.gremio.persistence.filter.TaskFilterImpl;
import com.gremio.repository.ProjectRepository;
import com.gremio.repository.TaskRepository;
import com.gremio.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.graphql.data.query.ScrollSubrange;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Task addTask(final TaskInput task) {

        final Project project = projectRepository.findById(task.projectId()).orElse(null);

        final Task result = Task.builder()
              .title(task.title())
              .status(task.status())
              .description(task.description())
              .due(task.due())
              .project(project)
              .build();

        return taskRepository.save(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Window<Task> findAllTasksByTitle(final String title, final ScrollSubrange subrange) {
        final ScrollPosition position = subrange.position().orElse(ScrollPosition.offset());
        final Limit limit = Limit.of(subrange.count().orElse(10));
        final Sort sort = Sort.by("title").ascending();

        return taskRepository.findAllByTitle(title, position, limit, sort);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Task> findTasksByFilter(final TaskFilter taskFilter, final Pageable pageable) {
        final TaskFilterImpl taskSpecification = new TaskFilterImpl(taskFilter);
        return taskRepository.findAll(taskSpecification.getAllTasksByFilter(taskFilter), pageable);
    }
}