package com.openprice.domain.shopping;

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
 * <p>One item in user shopping list.
 *
 * <p>The <code>catalogCode</code> is copied from <code>Catalog</code> when user add items from receipt parser result
 * or from auto complete catalog search.
 *
 */
@ToString(callSuper=true, exclude={"store"})
@SuppressWarnings("serial")
@Entity
@Table( name="shopping_item" )
public class ShoppingItem extends BaseAuditableEntity {

    @Getter @Setter
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="store_id")
    private ShoppingStore store;

    @Getter @Setter
    @Column(name="catalog_code")
    private String catalogCode;

    @Getter @Setter
    @Column(name="name")
    private String name;

}
