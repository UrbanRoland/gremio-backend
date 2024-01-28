package com.gremio.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gremio.enums.IssueStatus;
import com.gremio.enums.Priority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.time.LocalDateTime;
import java.util.List;

@Table
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Issue extends BaseEntity {
    
    @MappedCollection(idColumn = "issue_id")
    private List<Long> assigneeIds;
    
    private String title;
    private LocalDateTime due;
    
    @Column("project_id")
    private Long projectId;
    
    private String description;
    
    @Column("status")
    private IssueStatus status;
    
    @Column("priority")
    private Priority priority;
}