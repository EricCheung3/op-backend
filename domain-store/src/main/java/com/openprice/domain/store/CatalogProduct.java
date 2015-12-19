package com.openprice.domain.store;

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
import com.openprice.domain.product.ProductUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>Aggregated receipt catalog data from collected receipt data.
 *
 * <p><b>Notes:</b> Parser will have catalog data in config files. Catalog in database
 * is mostly for user shopping list usage, to display detail shopping item info, like name and price.
 *
 * TODO: remove name and number property, derived them from code in the database.
 *
 */
@ToString(callSuper=true, exclude={"chain"})
@SuppressWarnings("serial")
@Entity
@Table( name="catalog_product" )
public class CatalogProduct extends BaseAuditableEntity {

    @Getter @Setter
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="chain_id")
    @org.hibernate.annotations.OnDelete(
        action = org.hibernate.annotations.OnDeleteAction.CASCADE
    )
    private StoreChain chain;

    @Getter @Setter
    @Column(name="catalog_code", nullable=false)
    private String catalogCode;

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

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name="product_category")
    private ProductCategory productCategory;

    CatalogProduct() {}

    CatalogProduct(final String name, final String number) {
        this.catalogCode = ProductUtils.generateCatalogCode(name, number);
    }

    @Builder
    public static CatalogProduct createTestCatalogProduct(final String id,
                                                          final StoreChain chain,
                                                          final String name,
                                                          final String number,
                                                          final String price,
                                                          final String naturalName,
                                                          final String labelCodes,
                                                          final ProductCategory productCategory) {
        final CatalogProduct catalogProduct = new CatalogProduct();
        catalogProduct.setId(id);
        catalogProduct.setChain(chain);
        catalogProduct.setCatalogCode(ProductUtils.generateCatalogCode(name, number));
        catalogProduct.setPrice(price);
        catalogProduct.setNaturalName(naturalName);
        catalogProduct.setLabelCodes(labelCodes);
        catalogProduct.setProductCategory(productCategory);
        return catalogProduct;
    }
}
