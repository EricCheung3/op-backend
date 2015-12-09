package com.openprice.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * Super class for all entity classes.
 *
 */
@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown=true)
@SuppressWarnings("serial")
public abstract class BaseEntity implements Serializable {

    @Getter @Setter
    @Column(name="id")
    @Id
    @GeneratedValue(generator="system-uuid")
    @org.hibernate.annotations.GenericGenerator(name="system-uuid", strategy = "uuid2")
    private String id;

    @Column(name="version", nullable=false)
    @Version
    @JsonIgnore
    private Long version;

    @Override
    public int hashCode() {
        return (getId() == null) ? 0 : getId().hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;  // FIXME should not use getClass()
        final BaseEntity other = (BaseEntity) obj;
        if (getId() == null) return other.getId() == null;
        return getId().equals(other.getId());
    }
}
