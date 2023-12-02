package com.gremio.service;

import com.gremio.model.dto.filter.TaskFilter;
import com.gremio.persistence.entity.Task;
import com.gremio.persistence.specification.TaskSpecification;
import com.gremio.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.Collections;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;

    private Task task;
    
    
    @Test
    public void TaskService_AddTask_ReturnTask() {
        final Task task = Task.builder()
            .title("test")
            .due(new Date())
            .build();
    
        Mockito.when(taskRepository.save(Mockito.any())).thenReturn(task);
        
        Task savedTask = taskService.addTask(task);
        
        Assertions.assertNotNull(savedTask);
        Mockito.verify(taskRepository).save(task);
    }
    
    @Test
    public void TaskService_GetAllByFilter_ReturnPage() {
        TaskFilter taskFilter = new TaskFilter();
        Pageable pageable = Pageable.ofSize(10).withPage(0);
        
        Page<Task> expectedPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        
        Mockito.when(taskRepository.findAll(Mockito.any(
            TaskSpecification.class), Mockito.any(Pageable.class))).thenReturn(expectedPage);
        
        Page<Task> actualPage = taskService.getAllByFilter(taskFilter, pageable);
        
        Assertions.assertEquals(expectedPage, actualPage);
        Mockito.verify(taskRepository).findAll(Mockito.any(
            TaskSpecification.class), Mockito.any(Pageable.class));
        
    }
    
}