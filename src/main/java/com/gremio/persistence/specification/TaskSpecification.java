package com.gremio.persistence.specification;

import com.gremio.model.dto.filter.TaskFilter;
import com.gremio.persistence.entity.Task;
import com.gremio.persistence.entity.Task_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class TaskSpecification implements Specification<Task> {

    private final TaskFilter taskFilter;
    private final List<Predicate> predicateList = new ArrayList<>();
    
    public TaskSpecification(final TaskFilter taskFilter) {
        super();
        this.taskFilter = taskFilter;
    }

    @Override
    public Predicate toPredicate(final Root<Task> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {

           if (StringUtils.hasLength(taskFilter.getTitle())) {
                final Predicate titlePredicate = criteriaBuilder.equal(root.get(Task_.TITLE), taskFilter.getTitle());
                predicateList.add(titlePredicate);
            }
           
           if (StringUtils.hasLength(taskFilter.getDescription())) {
               final Predicate descriptionPredicate = criteriaBuilder.equal(root.get(Task_.DESCRIPTION), taskFilter.getDescription());
               predicateList.add(descriptionPredicate);
        }

        return criteriaBuilder.or(this.predicateList.toArray(Predicate[]::new));
    }
}