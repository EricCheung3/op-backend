package com.openprice.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.openprice.domain.util.LocalDateTimePersistenceConverter;

import lombok.Getter;
import lombok.ToString;

/**
 * Super class for auditable entity classes.
 */
@ToString(callSuper=true)
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@SuppressWarnings("serial")
public abstract class BaseAuditableEntity extends BaseEntity {

    @Getter
    @CreatedBy
    @Column
    private String createdBy;

    @Getter
    @CreatedDate
    @Column
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    private LocalDateTime createdTime;

    @Getter
    @LastModifiedBy
    @Column
    private String lastModifiedBy;

    @Getter
    @LastModifiedDate
    @Column
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    private LocalDateTime lastModifiedTime;

}
