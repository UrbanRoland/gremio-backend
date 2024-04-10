package com.gremio.controller;

import com.gremio.config.GraphQLConfig;
import com.gremio.enums.IssueStatus;
import com.gremio.persistence.entity.Issue;
import com.gremio.persistence.entity.Project;
import com.gremio.repository.IssueRepository;
import com.gremio.repository.ProjectRepository;
import com.gremio.service.IssueServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@GraphQlTest(IssueController.class)
@Import({IssueServiceImpl.class, GraphQLConfig.class})
public class IssueControllerIntTest {
    @Autowired
    GraphQlTester graphQlTester;
    
    @MockBean
    private IssueRepository issueRepository;
    @Autowired
    private IssueServiceImpl issueService;
    
    @MockBean
    private ProjectRepository projectRepository;
    private Project project;

    @BeforeEach
    void setup() {
        project = new Project();
        project.setId(1L);
    }

    //todo nedd to validate all fields
    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void IssueController_CreateIssue_ReturnIssue() {
        LocalDateTime currentDateTime = LocalDateTime.now();
    
        Issue expectedIssue =  Issue.builder()
            .title("testIssue")
            .status(IssueStatus.PASSED)
            .description("123testdescruiption")
            .project(project)
            .build();

        Mockito.when(projectRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(project));
        Mockito.when(issueRepository.save(ArgumentMatchers.any(Issue.class))).thenReturn(expectedIssue);

        //language=GraphQL
       String document = """
        mutation {
          createIssue(issue:{title:"testIssue", description:"123testdescruiption", projectId: 1}) {
            title
            description
            project {
              id
            }
          }
        }
           """;

        graphQlTester.document(document).execute().path("createIssue").entity(Issue.class).satisfies(issue -> {
            assertEquals(expectedIssue.getTitle(), issue.getTitle());
            assertEquals(expectedIssue.getDescription(), issue.getDescription());
            assertEquals(expectedIssue.getProject().getId(), issue.getProject().getId());
        });
    }
    
    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void IssueController_FindIssueById_ReturnIssue() {
        
        Issue expectedIssue =  Issue.builder()
            .title("testIssue")
            .status(IssueStatus.PASSED)
            .description("123testdescruiption")
            .project(project)
            .build();
        
        Mockito.when(issueRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(expectedIssue));
        Mockito.when(issueRepository.save(ArgumentMatchers.any(Issue.class))).thenReturn(expectedIssue);
        
        //language=GraphQL
        String document = """
        query {
          findIssueById(id: 1) {
            title
            description
          }
        }
        """;
        
        graphQlTester.document(document).execute().path("findIssueById").entity(Issue.class).satisfies(issue -> {
            assertEquals(expectedIssue.getTitle(), issue.getTitle());
            assertEquals(expectedIssue.getDescription(), issue.getDescription());
        });
    }
    
}