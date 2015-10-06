package com.openprice.parser.data;

import java.util.List;

/*
    wrap parsing results
 */
public class ReceiptDebug {

    //just add these objects for generating json objects
    private final FieldSet fields;
    private final List<Item> items;
    private final List<Warning> warnings;

    public ReceiptDebug(final FieldSet fields, final List<Item> items, final List<Warning> warnings){
        this.fields=fields;
        this.items=items;
        this.warnings=warnings;
    }

    public FieldSet fields(){return fields; }
    public List<Item> items(){return items; }
    public List<Warning> warnings(){return warnings; }

    //merge all the objects into one
    //for now just return the first one.
    public static ReceiptDebug merge(final List<ReceiptDebug> list){
        if(list==null) return null;
        return list.get(0);
    }

    @Override
    public String toString(){
        String str="Fields:\n";
        str+=fields().toString();
        str+="Items:\n";
        str+=items().toString();
        return str;
    }

}
