package com.gremio.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
import java.util.Set;

@Table
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Project {

    @Id
    private Long id;
    private String name;
    private String key;
    @Column("project_lead_id")
    private Long projectLeadId;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private String category;
    
    @MappedCollection(idColumn = "issue_id")
    private List<Long> issueIds;
    
    @MappedCollection(idColumn = "user_id")
    private Set<Long> teamMemberIds;
}