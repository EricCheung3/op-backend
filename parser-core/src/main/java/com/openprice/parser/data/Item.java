package com.openprice.parser.data;
import com.openprice.parser.common.StringCommon;
import com.openprice.parser.price.ProductPrice;

import lombok.Data;

/**
 *
 * item and prices in a receipt.
 * Each item may have one or two prices. If it's two prices, then it's a item soldby weight; otherwise it's sold by quantity and has just one price.
 */
@Data
public class Item {
    private final ProductPrice product;

    private final String productCategory;//our internally productCategory (corresponding to icon name)

    //String buyPrice=StringCommon.EMPTY;//price spent on the item; can be a unit price or the price of several units (kg)
    String unitPrice=StringCommon.EMPTY;//unit price of the item (usually in kilogram)
    String weight=StringCommon.EMPTY;//weight of the item bought
    String category=StringCommon.EMPTY;//category of the item
    String regPrice=StringCommon.EMPTY;//regular price
    String saving=StringCommon.EMPTY;//savings

    public String getBuyPrice(){
        return product.getPrice();
    }

    private Item(final ProductPrice p){
        this.product=p;
        this.productCategory=StringCommon.EMPTY;
    }

    private Item(final ProductPrice p, final String productCategory){
        this.product=p;
        this.productCategory=productCategory;
    }

    private Item(
            final ProductPrice product,
            final String productCategory,
            final String unitP,
            final String weight,
            final String category,
            final String regP,
            final String saving){
        this.product=product;
        this.productCategory=productCategory;
        this.unitPrice=unitP;
        this.weight=weight;
        this.category=category;
        this.regPrice=regP;
        this.saving=saving;
    }

    public static Item fromProductPrice (final ProductPrice pp){
        return new Item(pp);
    }

    public static Item emptyItem(){
        return new Item(ProductPrice.emptyValue());
    }

    public static Item fromNameOnly(final String name){
        return new Item(ProductPrice.fromNameOnly(name));
    }

    public static Item fromNameProductCategoryBuyPUnitPWeightCategory(
            final String name,
            final String productCategory,
            final String unitPrice,
            final String weight,
            final String category) {
        return new Item(
                ProductPrice.fromNameOnly(name),
                productCategory,
                unitPrice,
                weight,
                category,
                StringCommon.EMPTY,
                StringCommon.EMPTY);
    }
}

