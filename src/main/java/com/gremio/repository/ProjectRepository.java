package com.gremio.repository;

import com.gremio.persistence.entity.Project;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends ReactiveCrudRepository<Project, Long> {

}