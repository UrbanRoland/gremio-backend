package com.gremio.controller;

import com.gremio.model.input.ProjectInput;
import com.gremio.persistence.entity.Project;
import com.gremio.service.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    
    @MutationMapping
    public void createProject(@Argument final ProjectInput project) {
        projectService.createProject(project);
    }
    
    @SubscriptionMapping
    public Flux<Project> notifyProjectCreated() {
        return projectService.getProjectCreatedPublisher();
    }
    
    // Helper method to notify subscribers
    private void notifyProjectCreated(Project project) {
        projectService.publishProjectCreatedEvent(project);
    }
}