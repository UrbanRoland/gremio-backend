package com.gremio.service;

import com.gremio.model.dto.filter.IssueFilter;
import com.gremio.model.input.IssueInput;
import com.gremio.persistence.entity.Issue;
import com.gremio.persistence.entity.Project;
import com.gremio.persistence.filter.IssueFilterImpl;
import com.gremio.repository.IssueRepository;
import com.gremio.repository.ProjectRepository;
import com.gremio.service.interfaces.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.graphql.data.query.ScrollSubrange;
import org.springframework.stereotype.Service;

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

        final Project project = projectRepository.findById(issue.projectId()).orElse(null);

        final Issue result = Issue.builder()
              .title(issue.title())
              .status(issue.status())
              .description(issue.description())
              .due(issue.due())
              .project(project)
              .build();

        return issueRepository.save(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(cacheNames = "findAllIssuesByTitle", keyGenerator = "customKeyGenerator")
    public Window<Issue> findAllIssuesByTitle(final String title, final ScrollSubrange subrange) {
        final ScrollPosition position = subrange.position().orElse(ScrollPosition.offset());
        final Limit limit = Limit.of(subrange.count().orElse(10));
        final Sort sort = Sort.by("title").ascending();

        return issueRepository.findAllByTitle(title, position, limit, sort);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Issue> findIssuesByFilter(final IssueFilter issueFilter, final Pageable pageable) {
        final IssueFilterImpl issueFilterImpl = new IssueFilterImpl(issueFilter);
        return issueRepository.findAll(issueFilterImpl.getAllIssuesByFilter(issueFilter), pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(value = "issue", key = "#id")
    public Issue findIssueById(final Long id) {
        return issueRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Issue updateIssue(final Long id, final IssueInput issueInput) {
        final Issue issue = issueRepository.findById(id).orElse(null);
        if (issue == null) {
            return null;
        }
        //todo add all fields
        issue.setTitle(issueInput.title());
        issue.setDescription(issueInput.description());
        issue.setDue(issueInput.due());
        issue.setStatus(issueInput.status());
        return issueRepository.save(issue);
    }
}