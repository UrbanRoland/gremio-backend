package com.gremio.service.interfaces;

import com.gremio.model.input.ProjectInput;
import com.gremio.persistence.entity.Project;

public interface ProjectService {
    
    Project createProject(ProjectInput projectInput);
}