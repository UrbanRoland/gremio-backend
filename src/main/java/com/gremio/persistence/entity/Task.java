package com.gremio.persistence.entity;

import com.gremio.enums.TaskStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Entity
@Audited
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Task extends BaseEntity {

    @OneToMany
    private List<User> assignee;
    private String title;
    private Date due;
    @ManyToOne
    private Project project;
    private String description;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
}