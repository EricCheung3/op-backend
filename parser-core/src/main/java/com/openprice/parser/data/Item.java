package com.openprice.parser.data;
import com.openprice.parser.common.StringCommon;

import lombok.Data;

/*
item and prices in a receipt.
Each item may have one or two prices. If it's two prices, then it's a item soldby weight; otherwise it's sold by quantity and has just one price.
 */
@Data
public class Item {
    private final String name;//item name
    private final int startLine;//start line number of this item
    private String buyPrice;//price spent on the item; can be a unit price or the price of several units (kg)
    private String unitPrice;//unit price of the item (usually in kilogram)
    private String weight;//weight of the item bought
    private String category;//category of the item
    private String regPrice;//regular price
    private String saving;//savings

    public Item(final String n, final int startLine, final String b, final String u, final String w, final String c) throws Exception{
        name=n;
        this.startLine=startLine;
        buyPrice=b;
        unitPrice=u;
        weight=w;
        makePricesNumeric();
        /*      if ((unitPrice.isEmpty() && !weight.isEmpty() ) ||
             !unitPrice.isEmpty() && weight.isEmpty())
         throw new Exception("unit price and weight price must be empty at the same time. unitPrice="+unitPrice+"; weight="+weight);
         */

        category=c;
    }

    /*
    check whether prices are numeric, and apply formatter if necessary
     */
    public void makePricesNumeric() throws Exception{
        /*
        if(buyPrice()==null || buyPrice().isEmpty()){
            throw new Exception("buy price cannot be empty");
        }
         */
        if(buyPrice==null) return;
        buyPrice=StringCommon.formatPrice(buyPrice)+"";

        //if(!unitPrice().isEmpty()){
        if(unitPrice!=null){
            unitPrice=StringCommon.formatPrice(unitPrice)+"";
            if(weight==null)
                throw new Exception("unitPrice="+unitPrice+", but weigth is null. name()=" + name);
            weight=StringCommon.formatPrice(weight)+"";
        }
    }

}

