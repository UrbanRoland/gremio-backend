package com.gremio.service;

import com.gremio.model.input.ProjectInput;
import com.gremio.persistence.entity.Project;
import com.gremio.repository.ProjectRepository;
import com.gremio.service.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    
    //TODO - Remove this processor because deprecated
    private final DirectProcessor<Project> projectCreatedProcessor = DirectProcessor.create();

    @Override
    public Project createProject(ProjectInput projectInput) {
        
        Project createdProject = Project.builder()
                .name(projectInput.name())
                .description(projectInput.description())
                .build();
        projectCreatedProcessor.onNext(createdProject);
        return createdProject;
    }
    
    @Override
    public Flux<Project> getProjectCreatedPublisher() {
        return projectCreatedProcessor;
    }
    
    @Override
    public void publishProjectCreatedEvent(Project project) {
        projectCreatedProcessor.onNext(project);
    }
}