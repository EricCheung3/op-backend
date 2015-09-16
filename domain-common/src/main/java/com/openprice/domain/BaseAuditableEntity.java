package com.openprice.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @Column(name="created_by")
    @CreatedBy
    @JsonIgnore
    private String createdBy;

    @Getter
    @Column(name="created_time")
    @CreatedDate
    @JsonIgnore
    private LocalDateTime createdTime;

    @Getter
    @Column(name="last_modified_by")
    @LastModifiedBy
    @JsonIgnore
    private String lastModifiedBy;

    @Getter
    @Column(name="last_modified_time")
    @LastModifiedDate
    @JsonIgnore
    private LocalDateTime lastModifiedTime;

}
