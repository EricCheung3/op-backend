package com.openprice.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

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
    @Id
    @Column
    @GeneratedValue(generator="system-uuid")
    @org.hibernate.annotations.GenericGenerator(name="system-uuid", strategy = "uuid2")
    private String id;

    @Getter @Setter
    @Version
    @Column
    private Long version;

    @Override
    public int hashCode() {
        return (id == null) ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final BaseEntity other = (BaseEntity) obj;
        if (id == null) return other.id == null;
        return id.equals(other.id);
    }
}
