package com.openprice.parser.data;
import lombok.Data;

/**
 *
 * item and prices in a receipt.
 * Each item may have one or two prices. If it's two prices, then it's a item soldby weight; otherwise it's sold by quantity and has just one price.
 */
@Data
public class Item {
    private String name;//item name
    private String buyPrice;//price spent on the item; can be a unit price or the price of several units (kg)
    private String unitPrice;//unit price of the item (usually in kilogram)
    private String weight;//weight of the item bought
    private String category;//category of the item
    private String regPrice;//regular price
    private String saving;//savings
    private String catalogCode;

    public Item(final String name) {
        this.name = name;
    }

    public Item(final String name, final String buyPrice) {
        this.name = name;
        this.buyPrice = buyPrice;
    }

    public Item(final String name, final String buyPrice, final String catalogCode) {
        this.name = name;
        this.buyPrice = buyPrice;
        this.catalogCode = catalogCode;
    }

    public Item(final String name, final String buyPrice, final String unitPrice, final String weight, final String category) {
        this.name = name;
        this.buyPrice = buyPrice;
        this.unitPrice = unitPrice;
        this.weight = weight;
        this.category = category;
    }
}

