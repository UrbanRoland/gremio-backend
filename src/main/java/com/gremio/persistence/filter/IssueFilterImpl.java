package com.gremio.persistence.filter;

import com.gremio.model.dto.filter.IssueFilter;
//import com.gremio.persistence.entity.QIssue;
//import com.querydsl.core.BooleanBuilder;
//import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class IssueFilterImpl {

    private final IssueFilter issueFilter;

    /**
     * Creates a {@link Predicate} object containing the filter criteria for the provided {@link IssueFilter} object.
     *
     * @param issueFilter The IssueFilter object containing the filter criteria.
     * @return A Predicate object containing the filter criteria.
     */
  /*  public Predicate getAllIssuesByFilter(final IssueFilter issueFilter) {
        final QIssue qIssue = QIssue.issue;
        final BooleanBuilder builder = new BooleanBuilder();

        if (issueFilter == null) {
            return builder;
        }

        if (StringUtils.hasLength(issueFilter.title())) {
            builder.and(qIssue.title.like(issueFilter.title()));
        }

        if (StringUtils.hasLength(issueFilter.description())) {
            builder.and(qIssue.description.like(issueFilter.description()));
        }

        if (issueFilter.status() != null) {
            builder.and(qIssue.status.eq(issueFilter.status()));
        }

        return builder;
    }*/
}