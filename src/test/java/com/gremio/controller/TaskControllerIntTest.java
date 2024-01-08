package com.gremio.controller;

import com.gremio.enums.TaskStatus;
import com.gremio.persistence.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.security.test.context.support.WithMockUser;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureGraphQlTester
public class TaskControllerIntTest {
    @Autowired
    GraphQlTester graphQlTester;
    
    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void TaskController_CreateTask_ReturnTask() {
       
       //language=GraphQL
       String document = """
        mutation {
          createTask(task:{status:PASSED, title:"test", description:"testdescruiption", due:"2023-07-09T11:54:42", projectId: 1}){
            title
            description
            status
            due
          }
        }
           """;
      
       graphQlTester.document(document).execute().path("createTask").entity(Task.class).satisfies( task -> {
           assertEquals(task.getStatus(), TaskStatus.PASSED);
           assertEquals(task.getTitle(), "test");
           assertEquals(task.getDescription(), "testdescruiption");
           assertEquals(task.getDue().toString(), "2023-07-09T11:54:42");
       });
       
    }
    
}