package com.gremio.service;

import com.gremio.model.input.ProjectInput;
import com.gremio.persistence.entity.Project;
import com.gremio.repository.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @InjectMocks
    private ProjectServiceImpl projectService;
    
    @Mock
    private ProjectRepository projectRepository;
    
    @Test
    public void ProjectService_CreateProject_ReturnProject() {
        ProjectInput projectInput = ProjectInput.builder()
            .name("Test")
            .description("Description")
            .key("Key")
            .category("Category")
            .status("Status")
            .build();
        
        Project expectedProject = Project.builder()
            .name("Test")
            .description("Description")
            .key("Key")
            .category("Category")
            .status("Status")
            .build();
        
        Mockito.when(projectRepository.save(Mockito.any())).thenReturn(expectedProject);
        
        Project createdProject = projectService.createProject(projectInput);
        
        Assertions.assertNotNull(createdProject);
        Assertions.assertEquals(expectedProject.getName(), createdProject.getName());
        Mockito.verify(projectRepository, Mockito.times(1)).save(Mockito.any());
    }
    


}