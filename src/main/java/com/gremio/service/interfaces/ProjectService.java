package com.gremio.service.interfaces;

import com.gremio.model.input.ProjectInput;
import com.gremio.persistence.entity.Project;
import reactor.core.publisher.Flux;

public interface ProjectService {

    /**
     * Create a new project
     * @param projectInput
     * @return
     */
    Project createProject(ProjectInput projectInput);

    /**
     * Get the project created publisher
     * @return
     */
    Flux<Project> getProjectCreatedPublisher();

}