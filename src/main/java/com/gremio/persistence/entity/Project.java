package com.gremio.persistence.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Entity
@Audited
@NoArgsConstructor
@Getter
@Setter
public class Project extends BaseEntity {

    private String name;

}