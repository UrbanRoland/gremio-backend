package com.gremio.persistence.specification.archive;

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
    
        System.out.println("taskFileter Title: " + taskFilter.title());
        
           if (taskFilter != null && StringUtils.hasLength(taskFilter.title())) {
                final Predicate titlePredicate = criteriaBuilder.equal(root.get("title"), taskFilter.title());
                predicateList.add(titlePredicate);
            }
           
        /*   if (StringUtils.hasLength(taskFilter.description())) {
               final Predicate descriptionPredicate = criteriaBuilder.equal(root.get(Task_.DESCRIPTION), taskFilter.description());
               predicateList.add(descriptionPredicate);
        }
*/
        return criteriaBuilder.or(this.predicateList.toArray(Predicate[]::new));
    }
}