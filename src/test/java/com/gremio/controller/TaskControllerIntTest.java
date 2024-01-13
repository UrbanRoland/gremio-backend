package com.gremio.controller;

//test over http
//todo need to be reworked
/*
@GraphQlTest(TaskController.class)
@Import({TaskServiceImpl.class, GraphQLConfig.class})
public class TaskControllerIntTest {
    @Autowired
    GraphQlTester graphQlTester;
    
    @MockBean
    private TaskRepository taskRepository;
    @Autowired
    private TaskServiceImpl taskService;
    
    @MockBean
    private ProjectRepository projectRepository;
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
          createTask(task:{status:PASSED, title:"test", description:"12223testdescruiption", due:"2023-07-09T11:54:42", projectId: 1}) {
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
*/