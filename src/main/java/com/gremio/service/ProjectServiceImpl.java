package com.gremio.service;

import com.gremio.model.input.ProjectInput;
import com.gremio.persistence.entity.Project;
import com.gremio.repository.ProjectRepository;
import com.gremio.service.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Override
    public Project createProject(ProjectInput projectInput) {
        return projectRepository.save(Project.builder()
                .name(projectInput.name())
                .description(projectInput.description())
                .build());
    }
}