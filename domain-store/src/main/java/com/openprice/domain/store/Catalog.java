package com.openprice.domain.store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.BaseAuditableEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>Aggregated receipt catalog data from collected receipt data.
 *
 * <p><b>Notes:</b> Parser will have catalog data in config files. Catalog in database
 * is mostly for user shopping list usage, to display detail shopping item info, like name and price.
 *
 */
@ToString(callSuper=true, exclude={"chain"})
@SuppressWarnings("serial")
@Entity
@Table( name="catalog" )
public class Catalog extends BaseAuditableEntity {

    @Getter @Setter
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="chain_id")
    private StoreChain chain;

    @Getter @Setter
    @Column(name="code", nullable=false)
    private String code;

    /*
     * Can be used by shopping list items name.
     */
    @Getter @Setter
    @Column(name="name", nullable=false)
    private String name;

    @Getter @Setter
    @Column(name="number")
    private String number;

    @Getter @Setter
    @Column(name="category")
    private String category;

    /*
     * TODO: Which price to save here? Need future design for historical price, low/high/average price, etc.
     */
    @Getter @Setter
    @Column(name="price")
    private String price;

    /*
     * Manually fixed catalog name to display in UI
     */
    @Getter @Setter
    @Column(name="natural_name")
    private String naturalName;

    @Getter @Setter
    @Column(name="label_codes")
    private String labelCodes;

    Catalog() {}
}
