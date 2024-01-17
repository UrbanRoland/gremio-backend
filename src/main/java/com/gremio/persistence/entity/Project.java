package com.gremio.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import jakarta.persistence.Column;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Audited
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Project extends BaseEntity {

    private String name;
    @Column(unique = true, nullable = false)
    private String key;
    @ManyToOne
    @JoinColumn(name = "project_lead_id")
    private User projectLead;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private String category;
    @OneToMany(mappedBy = "project")
    private List<Issue> issues;
    @ManyToMany
    @JoinTable(
        name = "project_team_members",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> teamMembers;

}