package com.gremio.service;

import com.gremio.model.input.ProjectInput;
import com.gremio.persistence.domain.Project;
import com.gremio.repository.ProjectRepository;
import com.gremio.service.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final Sinks.Many<Project> projectCreatedSink = Sinks.many().multicast().onBackpressureBuffer();

    /**
     * @inheritDoc
     */
    @Override
    public Project createProject(final ProjectInput projectInput) {

        final Project createdProject = Project.builder()
                .name(projectInput.name())
                .description(projectInput.description())
                .key(projectInput.key())
                .category(projectInput.category())
                .startDate(projectInput.startDate())
                .endDate(projectInput.endDate())
                .status(projectInput.status())
                .build();
        
        projectCreatedSink.tryEmitNext(createdProject);
        return  projectRepository.save(createdProject).block();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Flux<Project> getProjectCreatedPublisher() {
        return projectCreatedSink.asFlux();
    }

}