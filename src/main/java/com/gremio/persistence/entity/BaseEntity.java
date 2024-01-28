package com.gremio.persistence.entity;



import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity extends Auditable<String> {

    @Id
    @Getter
    @Setter
    private Long id;
}