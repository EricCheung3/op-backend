package com.openprice.parser.data;

import java.util.ArrayList;
import java.util.List;

/**
    a pure version of ReceiptDebug

 */
public class ReceiptPure {

    private final FieldSetPure fields;
    private final List<ItemPure> items;
    private final List<Warning> warnings;

    public static ReceiptPure fromReceiptDebug(ReceiptDebug rd) throws Exception{
        List<Warning> warnings=rd.warnings();
        FieldSetPure fields=FieldSetPure.fromFieldSet(rd.fields());
        List<Item> it=rd.items();
        List<ItemPure> items=new ArrayList<ItemPure>();
        for(int i=0;i<it.size();i++){
            items.add(ItemPure.fromItem(it.get(i)));
        }
        return new ReceiptPure(fields, items, warnings);
    }

    public ReceiptPure(FieldSetPure f, List<ItemPure> i, List<Warning> w){
        fields=f;
        items=i;
        warnings=w;
    }

    public FieldSetPure fields(){return fields;}

    public List<ItemPure> items(){return items;}

    public List<Warning> warnings(){return warnings;}
}
