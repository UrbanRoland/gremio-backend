package com.gremio.service;

import com.gremio.model.input.TaskInput;
import com.gremio.persistence.entity.Project;
import com.gremio.persistence.entity.Task;
import com.gremio.repository.ProjectRepository;
import com.gremio.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Limit;
import org.springframework.graphql.data.query.ScrollSubrange;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @InjectMocks
    private TaskServiceImpl taskService;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ProjectRepository projectRepository;
    
    @Test
    public void TaskService_AddTask_ReturnTask() {
        final TaskInput taskDto = TaskInput.builder()
            .title("test")
            .due(LocalDateTime.now())
            .build();
        
        final Project project = new Project(); // You need to create a Project instance here for the test
        
        Mockito.when(projectRepository.findById(Mockito.any())).thenReturn(Optional.of(project));
        Mockito.when(taskRepository.save(Mockito.any())).thenReturn(new Task()); // You may need to create a Task instance here for the test
        
        Task savedTask = taskService.addTask(taskDto);
        
        Assertions.assertNotNull(savedTask);
    }
    
    @Test
    public void TaskService_FindAllTasksByTitle_ReturnWindow() {
        String title = "Test";
        ScrollPosition position = ScrollPosition.offset();
        ScrollSubrange subrange = ScrollSubrange.create(position,10,true);
        List<Task> items = Collections.singletonList(Task.builder().title("Test").build());
    
        Window<Task> windowMock = Window.from(items, index -> position, true);
        
        Mockito.when(taskRepository.findAllByTitle(
            Mockito.eq(title),
            Mockito.any(ScrollPosition.class),
            Mockito.any(Limit.class),
            Mockito.any(Sort.class)
        )).thenReturn(windowMock);

        Window<Task> resultWindow = taskService.findAllTasksByTitle(title, subrange);

        Assertions.assertNotNull(resultWindow);
        Assertions.assertEquals(1, resultWindow.getContent().size());
    }
    
}