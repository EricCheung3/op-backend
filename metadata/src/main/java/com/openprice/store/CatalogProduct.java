package com.openprice.store;

import com.openprice.store.data.ProductData;

public class CatalogProduct {

    private final ProductData productData;

    private final String catalogCode;

    private final ProductCategory productCategory;

    public CatalogProduct(final ProductData productData, final ProductCategory productCategory) {
        this.productData = productData;
        this.productCategory = productCategory;
        this.catalogCode = ProductUtils.generateCatalogCode(productData.getName(), productData.getNumber());
    }

    public String getCatalogCode() {
        return catalogCode;
    }

    public String getPrice() {
        return productData.getPrice();
    }

    public String getNaturalName() {
        return productData.getNaturalName();
    }

    public String getCategoryCode() {
        return productCategory.getCode();
    }

}
