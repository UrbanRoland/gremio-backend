package com.gremio.service.interfaces;

import com.gremio.model.input.IssueInput;
import com.gremio.persistence.domain.Issue;

public interface IssueService {

    /**
     * Adds a new issue to the system.
     *
     * @param issue The issue object to be added.
     * @return The newly added issue.
     */
    Issue addIssue(IssueInput issue);

    /**
     * Retrieves the issue with the given id.
     * @param id The id of the issue to retrieve.
     * @return The issue with the given id.
     */
    Issue findIssueById(Long id);
}