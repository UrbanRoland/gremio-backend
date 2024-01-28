package com.gremio.controller;

import com.gremio.model.input.IssueInput;
import com.gremio.persistence.entity.Issue;
import com.gremio.service.interfaces.IssueService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Window;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.query.ScrollSubrange;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class IssueController extends AbstractController {
    private final IssueService issueService;

    IssueController(final IssueService issueService) {
        this.issueService = issueService;
    }

    /**
     * Creates a new issue and adds it to the system.
     *
     * @param issue The issue object to be added.
     * @return A ResponseEntity containing the newly created issue and HTTP status 201 (Created).
     */
    @PreAuthorize("!hasAuthority('ROLE_READ_ONLY')")
    @MutationMapping
    public Issue createIssue(@Argument @Valid final IssueInput issue) {
        return issueService.addIssue(issue);
    }

    /**
     * Retrieves all issues from the system that match the given title.
     * @param title The title to match.
     * @return Existing issues with the given title.
     */
    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public Window<Issue> findAllIssuesByTitle(@Argument final String title, final ScrollSubrange subrange) {
       // return issueService.findAllIssuesByTitle(title, subrange);
       return null;
    }

    /**
     * Retrieves the issue with the given id.
     * @param id The id of the issue to retrieve.
     * @return The issue with the given id.
     */
    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public Issue findIssueById(@Argument final Long id) {
        return issueService.findIssueById(id);
    }
}