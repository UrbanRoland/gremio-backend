package com.gremio.service.interfaces;

import com.gremio.model.dto.filter.IssueFilter;
import com.gremio.model.input.IssueInput;
import com.gremio.persistence.entity.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Window;
import org.springframework.graphql.data.query.ScrollSubrange;

public interface IssueService {

    /**
     * Adds a new issue to the system.
     *
     * @param issue The issue object to be added.
     * @return The newly added issue.
     */
    Issue addIssue(IssueInput issue);

    /**
     * Retrieves all issues from the system that match the given title.
     * @param title The title to match.
     * @return Existing issues with the given title.
     */
    Window<Issue> findAllIssuesByTitle(String title, ScrollSubrange subrange);

    /**
     * Retrieves all issues from the system that match the given filter.
     * @param issueFilter The filter to match.
     * @return Existing issues with the given filter.
     */
    Page<Issue> findIssuesByFilter(IssueFilter issueFilter, Pageable pageable);
}