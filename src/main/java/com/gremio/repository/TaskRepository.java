package com.gremio.repository;

import com.gremio.persistence.entity.Task;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.graphql.data.GraphQlRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Window;

//@GraphQlRepository
//public interface TaskRepository extends CrudRepository<Task, Long>, QuerydslPredicateExecutor<Task>{
//
//    Window<Task> findAllByTitle(String title, ScrollPosition position, Limit limit, Sort sort);
//}

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

    Window<Task> findAllByTitle(String title, ScrollPosition position, Limit limit, Sort sort);
}