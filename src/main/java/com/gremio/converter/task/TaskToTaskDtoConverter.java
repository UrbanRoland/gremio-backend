package com.gremio.converter.task;

import com.gremio.model.dto.TaskDto;
import com.gremio.persistence.entity.Task;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskToTaskDtoConverter implements Converter<Task, TaskDto> {

    @Override
    public TaskDto convert(final Task source) {
        return TaskDto.builder()
            .title(source.getTitle())
            .due(source.getDue())
            .status(source.getStatus())
            .build();
    }
}