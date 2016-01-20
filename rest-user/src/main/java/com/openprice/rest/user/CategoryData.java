package com.openprice.rest.user;

import com.openprice.domain.product.ProductCategory;

import lombok.Value;

@Value
public class CategoryData {
    String code;
    String label;

    public CategoryData(final ProductCategory pc) {
        this.code = pc.getCode();
        this.label = pc.getLabel();
    }
}
