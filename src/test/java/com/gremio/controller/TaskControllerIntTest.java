package com.gremio.controller;

import com.gremio.enums.TaskStatus;
import com.gremio.model.dto.TaskDto;
import com.gremio.persistence.entity.Project;
import com.gremio.persistence.entity.Task;
import com.gremio.service.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.security.test.context.support.WithMockUser;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

//test over http
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureGraphQlTester
public class TaskControllerIntTest {
    @Autowired
    GraphQlTester graphQlTester;
    
    @MockBean
    private TaskServiceImpl taskService;
    private Project project;
    private LocalDateTime localDateTime;

    @BeforeEach
    void setup() {
        project = new Project();
        project.setId(1L);
        localDateTime = LocalDateTime.of(2023, 7, 9, 11, 54, 42);
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void TaskController_CreateTask_ReturnTask() {
        Task expectedTask =  Task.builder()
            .title("test")
            .status(TaskStatus.PASSED)
            .description("123testdescruiption")
            .due(localDateTime)
            .project(project)
            .build();

        Mockito.when(taskService.addTask(Mockito.any(TaskDto.class))).thenReturn(expectedTask);

        //language=GraphQL
       String document = """
        mutation {
          createTask(task:{status:PASSED, title:"test", description:"123testdescruiption", due:"2023-07-09T11:54:42", projectId: 1}) {
            title
            description
            status
            due
          }
        }
           """;
    
        graphQlTester.document(document).execute().path("createTask").entity(Task.class).satisfies(task -> {
            assertEquals(expectedTask.getStatus(), task.getStatus());
            assertEquals(expectedTask.getTitle(), task.getTitle());
            assertEquals(expectedTask.getDescription(), task.getDescription());
            assertEquals(expectedTask.getDue(), task.getDue());
        });
       
    }
    
}