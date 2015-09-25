package com.openprice.domain.store;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.BaseAuditableEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper=true, exclude={"branches"})
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
    @Column(name="identify_field")
    private String identifyField;

    @Getter @Setter
    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy="store")
    private List<StoreBranch> branches = new ArrayList<>();

}
