package com.gremio.repository;

import com.gremio.persistence.entity.Issue;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends ReactiveCrudRepository<Issue, Long> {
}