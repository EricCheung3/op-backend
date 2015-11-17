package com.openprice.domain.store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
@ToString(callSuper=true)
@SuppressWarnings("serial")
@Entity
@Table( name="catalog" )
public class Catalog extends BaseAuditableEntity {

    /*
     * reference to StoreChain code
     */
    @Getter @Setter
    @Column(name="code", nullable=false)
    private String code;

    /*
     * Can be used by shopping list items name.
     */
    @Getter @Setter
    @Column(name="display_name", nullable=false)
    private String displayName;

    /*
     * TODO: Which price to save here? Need future design for historical price, low/high/average price, etc.
     */
    @Getter @Setter
    @Column(name="price")
    private String price;

    /*
     *
     */
    @Getter @Setter
    @Column(name="natural_name")
    private String naturalName;

    @Getter @Setter
    @Column(name="label_codes")
    private String labelCodes;


}
