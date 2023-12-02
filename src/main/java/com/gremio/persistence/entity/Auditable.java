package com.gremio.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
@Setter
@Audited
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<T> {

    @CreatedBy
    @Deprecated(forRemoval = true)
    private T createdBy;

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP")
    @Deprecated(forRemoval = true)
    private LocalDateTime creationDate;

    @LastModifiedBy
    @Deprecated(forRemoval = true)
    private T lastModifiedBy;

    @LastModifiedDate
    @Column(columnDefinition = "TIMESTAMP")
    @Deprecated(forRemoval = true)
    private LocalDateTime lastModifiedDate;
}