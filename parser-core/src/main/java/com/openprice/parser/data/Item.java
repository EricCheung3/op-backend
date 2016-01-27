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
    private final Product product;

    String buyPrice=StringCommon.EMPTY;//price spent on the item; can be a unit price or the price of several units (kg)
    String unitPrice=StringCommon.EMPTY;//unit price of the item (usually in kilogram)
    String weight=StringCommon.EMPTY;//weight of the item bought
    String category=StringCommon.EMPTY;//category of the item
    String regPrice=StringCommon.EMPTY;//regular price
    String saving=StringCommon.EMPTY;//savings

    private Item(final Product product, final String buy, final String unitP, final String weight, final String category, final String regP, final String saving){
        this.product=product;
        this.buyPrice=buy;
        this.unitPrice=unitP;
        this.weight=weight;
        this.category=category;
        this.regPrice=regP;
        this.saving=saving;
    }

    private Item(final Product p){
        this.product=p;
    }

    public static Item emptyItem(){
        return new Item(Product.emptyProduct());
    }

    public static Item fromNameOnly(final String name){
        return new Item(Product.fromNameOnly(name));
    }

    private Item (final Product p, final String buyPrice){
        this.product=p;
        this.buyPrice=buyPrice;
    }

    public static Item fromProductPrice (final ProductPrice pp){
        return new Item(pp.getProduct(), pp.getPrice());
    }

    public static Item fromNameBuyPUnitPWeightCategory(final String name, final String buyPrice, final String unitPrice, final String weight, final String category) {
        return new Item(Product.fromNameOnly(name), buyPrice, unitPrice, weight, category, StringCommon.EMPTY, StringCommon.EMPTY);
    }
}

