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
    private int startLine;//start line number of this item
    private String buyPrice;//price spent on the item; can be a unit price or the price of several units (kg)
    private String unitPrice;//unit price of the item (usually in kilogram)
    private String weight;//weight of the item bought
    private String category;//category of the item
    private String regPrice;//regular price
    private String saving;//savings

    public Item(final String name, final int startLine) {
        this.name = name;
        this.startLine = startLine;
    }

    public Item(final String name, final int startLine, final String buyPrice, final String unitPrice, final String weight, final String category) {
        this.name = name;
        this.startLine = startLine;
        this.buyPrice = buyPrice;
        this.unitPrice = unitPrice;
        this.weight = weight;
        this.category = category;
    }
}

