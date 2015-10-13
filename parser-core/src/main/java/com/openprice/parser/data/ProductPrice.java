package com.openprice.parser.data;

import com.openprice.parser.common.StringCommon;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductPrice {

    Product product;
    String price;

    public static ProductPrice emptyValue(){
        return ProductPrice.builder()
                .product(Product.emptyProduct())
                .price(StringCommon.EMPTY)
                .build();
    }

    public boolean isEmpty(){
        return product.isEmpty() && price.isEmpty();
    }
}
