package com.gremio.persistence.filter;

import com.gremio.model.dto.filter.TaskFilter;
import com.gremio.persistence.entity.QTask;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class TaskFilterImpl {

    private final TaskFilter taskFilter;

    /**
     * Creates a {@link Predicate} object containing the filter criteria for the provided {@link TaskFilter} object.
     *
     * @param taskFilter The TaskFilter object containing the filter criteria.
     * @return A Predicate object containing the filter criteria.
     */
    public Predicate getAllTasksByFilter(final TaskFilter taskFilter) {
        final QTask qTask = QTask.task;
        final BooleanBuilder builder = new BooleanBuilder();

        if (taskFilter == null) {
            return builder;
        }

        if (StringUtils.hasLength(taskFilter.title())) {
            builder.and(qTask.title.like(taskFilter.title()));
        }

        if (StringUtils.hasLength(taskFilter.description())) {
            builder.and(qTask.description.like(taskFilter.description()));
        }

        if (taskFilter.status() != null) {
            builder.and(qTask.status.eq(taskFilter.status()));
        }

        return builder;
    }
}