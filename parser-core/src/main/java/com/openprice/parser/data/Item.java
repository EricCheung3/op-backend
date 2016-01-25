package com.openprice.parser.data;
import com.openprice.parser.common.StringCommon;

import lombok.Builder;
import lombok.Value;

/**
 *
 * item and prices in a receipt.
 * Each item may have one or two prices. If it's two prices, then it's a item soldby weight; otherwise it's sold by quantity and has just one price.
 */
@Value
@Builder
public class Item {
    Product product;

    String buyPrice;//price spent on the item; can be a unit price or the price of several units (kg)
    String unitPrice;//unit price of the item (usually in kilogram)
    String weight;//weight of the item bought
    String category;//category of the item
    String regPrice;//regular price
    String saving;//savings

    public static Item fromNameBuyPUnitPWeightCategory(final String name, final String buyPrice,
            final String unitPrice, final String weight, final String category) {
        return Item.builder()
                .product(Product.builder().name(name).number(StringCommon.EMPTY).build())
                .buyPrice(buyPrice)
                .unitPrice(unitPrice)
                .weight(weight)
                .category(category)
                .regPrice(StringCommon.EMPTY)
                .saving(StringCommon.EMPTY)
                .build();
    }
}

