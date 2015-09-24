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
@Table( name="store" )
public class Store extends BaseAuditableEntity {

    @Getter @Setter
    @Column(name="code", unique=true, nullable=false)
    private String code;

    @Getter @Setter
    @Column(name="name", nullable=false)
    private String name;

    @Getter @Setter
    @Column(name="categories", nullable=false)
    private String categories;

    @Getter @Setter
    @Column(name="identify_field", nullable=false)
    private String identifyField;

}
