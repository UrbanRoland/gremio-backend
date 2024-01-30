package com.gremio.service;

import com.gremio.model.input.IssueInput;
import com.gremio.persistence.domain.Issue;
import com.gremio.persistence.domain.Project;
import com.gremio.repository.IssueRepository;
import com.gremio.repository.ProjectRepository;
import com.gremio.service.interfaces.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "issueCache")
public class IssueServiceImpl implements IssueService {
    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Issue addIssue(final IssueInput issue) {

        final Project project = Mono.justOrEmpty(issue.projectId())
            .flatMap(projectRepository::findById)
            .switchIfEmpty(Mono.empty())
            .block();

        final Issue result = Issue.builder()
              .title(issue.title())
              .status(issue.status())
              .description(issue.description())
              .due(issue.due())
              .projectId(project != null ? project.getId() : null)
              .build();

        return issueRepository.save(result).block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(value = "issue", key = "#id")
    public Issue findIssueById(final Long id) {
        return issueRepository.findById(id).switchIfEmpty(Mono.empty()).block();
    }
}