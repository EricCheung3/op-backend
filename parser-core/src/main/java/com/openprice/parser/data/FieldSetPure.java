package com.openprice.parser.data;

import java.util.List;
import java.util.ArrayList;


/*
a "Pure" version of FieldSet, which assigns correct data types.
 
*/
public class FieldSetPure{
    final ValueLine account;
    final ValueLine author;
    final ValueLine cashier;
    final ValueLine chain;//chain name
    final IntegerLine chainID;//chain id given by us
    final ValueLine date;
    final FloatLine gstAmount;//gst amount
    final ValueLine gstNumber;//gst number
    final ValueLine phone;
    final FloatLine recycle;//total recycle amount
    final ValueLine ref;
    final ValueLine addressLine1;//store address
    final ValueLine addressLine2;//store address
    final ValueLine addressCity;//store address
    final ValueLine addressProv;//store address
    final ValueLine addressPost;//store address
    
    final ValueLine storeBranch;//storeBranch name
    final IntegerLine storeID;
    final FloatLine subTotal;
    final FloatLine total;
    final IntegerLine totalSold;//total number of items fold

    final List<FloatLine> savings;
    final List<FloatLine> regPrices;
    final List<FloatLine> coupons;
    final List<FloatLine> deposits;
    final List<FloatLine> recycles;
 
    //a static factory
    public static FieldSetPure fromFieldSet(FieldSet fs) throws Exception{
        ValueLine account=fs.account();        
        ValueLine author =fs.author();
        ValueLine cashier=fs.cashier();
        ValueLine chain=fs.chain();
        IntegerLine chainID=IntegerLine.fromValueLine(fs.chainID());
        ValueLine date=fs.date();
        FloatLine gstAmount=FloatLine.fromValueLine(fs.gstAmount());
        ValueLine gstNumber=fs.gstNumber();
        ValueLine phone=fs.phone();
        ValueLine ref=fs.ref();
        ValueLine line1=fs.addressLine1();
        ValueLine line2=fs.addressLine2();
        ValueLine city=fs.addressCity();
        ValueLine prov=fs.addressProv();
        ValueLine post=fs.addressPost();
        ValueLine storeBranch=fs.storeBranch();
        IntegerLine storeID=IntegerLine.fromValueLine(fs.storeID());
        FloatLine subTotal=FloatLine.fromValueLine(fs.subTotal());
        FloatLine total=FloatLine.fromValueLine(fs.total());
        IntegerLine totalSold=IntegerLine.fromValueLine(fs.totalSold());
        FloatLine recycle=FloatLine.fromValueLine(fs.recycle());

        List<FloatLine> savings=toFloatLineList(fs.savings());
        List<FloatLine> regPrices=toFloatLineList(fs.regPrices());
        List<FloatLine> coupons=toFloatLineList(fs.coupons());
        List<FloatLine> deposits=toFloatLineList(fs.deposits());
        List<FloatLine> recycles=toFloatLineList(fs.recycles());

        return new FieldSetPure(account, author, cashier, chain, chainID, date, gstAmount, gstNumber, 
            phone, ref, line1,line2,
            city, prov, post, 
            storeBranch, storeID, 
            subTotal, total, totalSold, recycle,
            savings, regPrices, coupons, deposits, recycles);
    }


    public FieldSetPure(ValueLine ac, ValueLine auth,  ValueLine cash, ValueLine chain, IntegerLine chainID,
        ValueLine date, FloatLine gst, ValueLine gstNumber,  ValueLine phone, ValueLine ref, 
        ValueLine storeAddLine1,
        ValueLine storeAddLine2,
        ValueLine storeAddCity,
        ValueLine storeAddProv,
        ValueLine storeAddPost,
        ValueLine storeBranch, IntegerLine storeID, 
    FloatLine subTotal, FloatLine total, IntegerLine totalSold, FloatLine recycle,
            List<FloatLine> savings, List<FloatLine> regPrices, 
            List<FloatLine> coupons, List<FloatLine> deposits, 
            List<FloatLine> recycles){
        account=ac;
        author=auth;
        cashier=cash;
        this.chain=chain;
        this.chainID=chainID;
        this.date=date;
        this.gstAmount=gst;
        this.gstNumber=gstNumber;
        this.phone=phone;
        this.ref=ref;
        this.addressLine1=storeAddLine1;
        this.addressLine2=storeAddLine2;
        this.addressCity=storeAddCity;
        this.addressProv=storeAddProv;
        this.addressPost=storeAddPost;
        this.storeBranch=storeBranch;
        this.storeID=storeID;
        this.subTotal=subTotal;
        this.total=total;
        this.totalSold=totalSold;
        this.recycle=recycle;

        this.savings=savings;
        this.regPrices=regPrices;
        this.coupons=coupons;
        this.deposits=deposits;
        this.recycles=recycles;
    }
    /*
    public FieldSetPure(){
        account=new ValueLine();
        author=new ValueLine();
        cashier=new ValueLine();
        this.chain=new ValueLine();
        this.date=new ValueLine();
        this.gst=new ValueLine();
        this.gstNumber=new ValueLine();
        this.phone=new ValueLine();
        this.ref=new ValueLine();
        this.storeAdd=new ValueLine();
        this.storeBranch=new ValueLine();
        this.storeID=new IntegerLine();
        this.subTotal=new FloatLine();
        this.total=new FloatLine();
        this.totalSold=new IntegerLine();
        this.recycle=new FloatLine();
    }
    */

    public ValueLine account(){return account;}
    public ValueLine author(){return author;}
    public ValueLine cashier(){return cashier;}
    public ValueLine chain(){return chain;}
    public IntegerLine chainID(){return chainID;}
    public ValueLine date(){return date;}
    // public FloatLine gstAmount(){return gst;}
    public FloatLine gstAmount(){return gstAmount();}
    public ValueLine gstNumber(){return gstNumber;}
    public ValueLine phone(){return phone;}
    public ValueLine ref(){return ref;}
    public FloatLine recycle(){return recycle;}
//    public ValueLine address(){return address;}
    public ValueLine addressLine1(){return addressLine1;}
    public ValueLine addressLine2(){return addressLine2;}
    public ValueLine addressCity(){return addressCity;}
    public ValueLine addressProv(){return addressProv;}
    public ValueLine addressPost(){return addressPost;}
    public ValueLine storeBranch(){return storeBranch;}
    public IntegerLine storeID(){return storeID;}
    public FloatLine subTotal(){return subTotal;}
    public FloatLine total(){return total;}
    public IntegerLine totalSold(){return totalSold;}
    public List<FloatLine> savings(){return savings;}
    public List<FloatLine> coupons(){return coupons;}
    public List<FloatLine> regPrices(){return regPrices;}
    public List<FloatLine> deposits(){return deposits;}
    public List<FloatLine> recycles(){return recycles;}

    public static List<FloatLine> toFloatLineList(final List<ValueLine> list) throws Exception{
        List<FloatLine> res=new ArrayList<FloatLine>();
        for(int i=0;i<list.size();i++){
            ValueLine v=list.get(i);
            res.add(FloatLine.fromValueLine(v));
        }
        return res;
    }


}
