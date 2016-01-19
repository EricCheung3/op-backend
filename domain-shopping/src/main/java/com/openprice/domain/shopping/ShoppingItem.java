package com.openprice.domain.shopping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.BaseAuditableEntity;
import com.openprice.domain.product.ProductCategory;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>One item in user shopping list.
 *
 * <p>The <code>catalogCode</code> is copied from <code>Catalog</code> when user add items from receipt parser result
 * or from auto complete catalog search.
 *
 * <p>The <code>productCategory</code> is produced by ProductMatchingService to find the best matching category.
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
    @Column(name="name")
    private String name;

    /**
     * Associated catalog product. Comes from user receipt item or search result item.
     * Maybe null if no product can be associated.
     */
    @Getter @Setter
    @Column(name="catalog_code")
    private String catalogCode;

    /**
     * Category this item belongs to.
     * Originally come from catalog product it associates with. If no catalog product found, system will
     * try to find a best match category. Or user can select.
     * DISCUSS: Back-end algorithm to find match category with user input name or receipt item name.
     *
     */
    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name="product_category")
    private ProductCategory productCategory;

    @Getter @Setter
    @Column(name="number")
    private int number;

    ShoppingItem() {}

    @Builder(builderMethodName="testObjectBuilder")
    public static ShoppingItem createTestShoppingItem(final String id,
                                                      final ShoppingStore store,
                                                      final String name,
                                                      final String catalogCode,
                                                      final ProductCategory productCategory,
                                                      final int number) {
        final ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItem.setId(id);
        shoppingItem.setStore(store);
        shoppingItem.setName(name);
        shoppingItem.setCatalogCode(catalogCode);
        shoppingItem.setProductCategory(productCategory);
        shoppingItem.setNumber(number);
        return shoppingItem;
    }
}
