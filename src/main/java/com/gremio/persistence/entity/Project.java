package com.gremio.persistence.entity;

import jakarta.persistence.Entity;
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
@AllArgsConstructor
public class Project extends BaseEntity {

    private String name;

}