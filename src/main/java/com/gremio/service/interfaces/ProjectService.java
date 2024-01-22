package com.gremio.service.interfaces;

import com.gremio.model.input.ProjectInput;
import com.gremio.persistence.entity.Project;
import reactor.core.publisher.Flux;

public interface ProjectService {
    
    Project createProject(ProjectInput projectInput);
    
    Flux<Project> getProjectCreatedPublisher();
    
    void publishProjectCreatedEvent(Project project);
}