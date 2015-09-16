package com.openprice.domain.store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.openprice.domain.BaseAuditableEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@SuppressWarnings("serial")
@Entity
@Table
public class Store extends BaseAuditableEntity {

    @Getter @Setter
    @Column(name="name")
    private String name;

}
