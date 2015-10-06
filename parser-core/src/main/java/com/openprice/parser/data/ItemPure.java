package com.openprice.parser.data;

/**
 *  A "pure" version of Item which assigns correct numerical type to fields.
 */
public class ItemPure{
    private final String name;//item name
    private final int startLine;//start line number of this item
    private final float buyPrice;//price spent on the item; can be a unit price or the price of several units (kg)
    private final float unitPrice;//unit price of the item (usually in kilogram)
    private final float weight;//weight of the item bought
    private final String category;//category of the item
    private final float regPrice;//regular price
    private final float saving;//savings

    public static ItemPure fromItem(Item it) throws NumberFormatException{
        String name=it.getName();
        int startLine=it.getStartLine();
        float buyPrice=floatFromString(it.getBuyPrice());
        float unitPrice=floatFromString(it.getUnitPrice());
        float weight=floatFromString(it.getWeight());
        String category=it.getCategory();
        float regPrice=floatFromString(it.getRegPrice());
        float saving=floatFromString(it.getSaving());
        return new ItemPure(name, startLine, buyPrice, unitPrice,
                weight, category, regPrice, saving);
    }

    public static float floatFromString(String str){
        if(str==null||str.isEmpty()) return 0;
        return Float.valueOf(str);
    }

    public ItemPure(final String n, final int startLine, final float b, final float u, final float w, final String c,
            final float reg, final float sav){
        name=n;
        this.startLine=startLine;
        buyPrice=b;
        unitPrice=u;
        weight=w;
        category=c;
        regPrice=reg;
        saving=sav;
    }
    public String name(){return name;}
    public int startLine(){return startLine;}
    public float buyPrice(){return buyPrice;}
    public float unitPrice(){return unitPrice;}
    public float weight(){return weight;}
    public String category(){return category;}
    public float regPrice(){return regPrice;}
    public float saving(){return saving;}

    @Override
    public String toString(){
        return name()+";"+startLine()+";"+buyPrice()+":"+unitPrice()+":"+weight()+":"+category()+"\n";
    }
}

