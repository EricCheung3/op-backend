package com.openprice.store;

import com.openprice.store.data.CategoryData;

public class ProductCategory {

    private final CategoryData categoryData;

    public ProductCategory(final CategoryData categoryData) {
        this.categoryData = categoryData;
    }

    public String getCode() {
        return categoryData.getCode();
    }

    public String getLabel() {
        return categoryData.getName();
    }
}
