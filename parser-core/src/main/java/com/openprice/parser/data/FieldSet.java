package com.openprice.parser.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldSet {
    //save the many field members in a map
    private final Map<FieldName, ValueLine> fieldNameToValueLine;

    private final List<ValueLine> savings=new ArrayList<ValueLine>();
    private final List<ValueLine> regPrices=new ArrayList<ValueLine>();
    private final List<ValueLine> coupons=new ArrayList<ValueLine>();
    private final List<ValueLine> deposits=new ArrayList<ValueLine>();
    private final List<ValueLine> recycles=new ArrayList<ValueLine>();

    public Map<FieldName, ValueLine>  fieldNameToValueLine(){return fieldNameToValueLine;}
    public List<ValueLine> coupons(){return coupons;}
    public List<ValueLine> deposits(){return deposits;}
    public List<ValueLine> recycles(){return recycles;}
    public List<ValueLine> regPrices(){return regPrices;}
    public List<ValueLine> savings(){return savings;}

    public static class Param{
        private Map<FieldName, ValueLine> fieldNameToValueLine=new HashMap<FieldName, ValueLine>();

        private Param(){
            for(FieldName f: FieldName.values()){
                fieldNameToValueLine.put(f, ValueLine.defaultValueLine());
            }
        }

        public Param account(ValueLine account){
            fieldNameToValueLine.put(FieldName.Account, account);
            return this;
        }

        public Param line1(ValueLine str){
            fieldNameToValueLine.put(FieldName.AddressLine1, str);
            return this;
        }
        public Param addressLine1(ValueLine str){
            return line1(str);
        }

        public Param line2(ValueLine str){
            fieldNameToValueLine.put(FieldName.AddressLine2, str);
            return this;
        }
        public Param addressLine2(ValueLine str){
            return line2(str);
        }

        public Param addressCity(ValueLine str){
            fieldNameToValueLine.put(FieldName.AddressCity, str);
            return this;
        }
        public Param addressProv(ValueLine str){
            fieldNameToValueLine.put(FieldName.AddressProv, str);
            return this;
        }
        public Param addressPost(ValueLine str){
            fieldNameToValueLine.put(FieldName.AddressPost, str);
            return this;
        }

        public Param approved(ValueLine approved){
            fieldNameToValueLine.put(FieldName.Approved, approved);
            return this;
        }

        public Param author(ValueLine author){
            fieldNameToValueLine.put(FieldName.Author, author);
            return this;
        }

        public Param card(ValueLine card){
            fieldNameToValueLine.put(FieldName.Card, card);
            return this;
        }

        public Param cashier(ValueLine cashier){
            fieldNameToValueLine.put(FieldName.Cashier, cashier);
            return this;
        }

        public Param chain(ValueLine chain){
            fieldNameToValueLine.put(FieldName.Chain, chain);
            return this;
        }

        public Param chainID(ValueLine chainID){
            fieldNameToValueLine.put(FieldName.ChainID, chainID);
            return this;
        }

        public Param date(ValueLine date){
            fieldNameToValueLine.put(FieldName.Date, date);
            return this;
        }

        public Param gstAmount(ValueLine gstAmount){
            fieldNameToValueLine.put(FieldName.GstAmount, gstAmount);
            return this;
        }

        public Param gstNumber(ValueLine gstNumber){
            fieldNameToValueLine.put(FieldName.GstNumber, gstNumber);
            return this;
        }

        public Param phone(ValueLine phone){
            fieldNameToValueLine.put(FieldName.Phone, phone);
            return this;
        }

        public Param recycle(ValueLine recycle){
            fieldNameToValueLine.put(FieldName.Recycle, recycle);
            return this;
        }

        public Param ref(ValueLine ref){
            fieldNameToValueLine.put(FieldName.Ref, ref);
            return this;
        }

        public Param slogan(ValueLine slogan){
            fieldNameToValueLine.put(FieldName.Slogan, slogan);
            return this;
        }

        public Param storeBranch(ValueLine storeBranch){
            fieldNameToValueLine.put(FieldName.StoreBranch, storeBranch);
            return this;
        }
        public Param storeID(ValueLine storeID){
            fieldNameToValueLine.put(FieldName.StoreID, storeID);
            return this;
        }

        public Param subTotal(ValueLine subTotal){
            fieldNameToValueLine.put(FieldName.SubTotal, subTotal);
            return this;
        }

        public Param thankyou(ValueLine thankyou){
            fieldNameToValueLine.put(FieldName.Thankyou, thankyou);
            return this;
        }

        public Param total(ValueLine total){
            fieldNameToValueLine.put(FieldName.Total, total);
            return this;
        }

        public Param totalSold(ValueLine totalSold){
            fieldNameToValueLine.put(FieldName.TotalSold, totalSold);
            return this;
        }

        public FieldSet build(){
            return new FieldSet(this);
        }

    }

    //constructor with Param
    private FieldSet(Param p){
        this.fieldNameToValueLine=p.fieldNameToValueLine;
    }

    //static constructor with default parameters
    public static FieldSet build(){
        return new Param().build();
    }

    /**
     * add two sets together and prefer the first operand
     * @param  primary the primary operand which will be preferred when r2 also has the same element
     * @param  r2      [description]
     * @return         [description]
     */
    public static FieldSet addPrefer(final FieldSet primary, final FieldSet r2){
        final FieldSet result=FieldSet.build();
        for(FieldName fName:FieldName.values()){
            if(primary.fieldNameToValueLine().containsKey(fName) && !primary.fieldNameToValueLine().get(fName).isEmpty()){
                result.fieldNameToValueLine().put(fName, primary.get(fName));
            }else if(r2.fieldNameToValueLine().containsKey(fName) && !r2.fieldNameToValueLine().get(fName).isEmpty()){
                result.fieldNameToValueLine().put(fName, r2.get(fName));
            }
        }
        return result;
    }

    //getters and setters
    private ValueLine get(FieldName f){
        return fieldNameToValueLine.get(f);
    }

    public ValueLine account(){return get(FieldName.Account);}
    public static Param account(ValueLine account){return new Param().account(account);}

    public ValueLine addressLine1(){return get(FieldName.AddressLine1);}
    public ValueLine addressLine2(){return get(FieldName.AddressLine2);}
    public ValueLine addressCity(){return get(FieldName.AddressCity);}
    public ValueLine addressProv(){return get(FieldName.AddressProv);}
    public ValueLine addressPost(){return get(FieldName.AddressPost);}
    //    public ValueLine addressLine3(){return get(FieldNameAddressLines.AddressLine3);}
    //    public ValueLine storeAdd(){return address();}

    public static Param addressLine1(ValueLine str){return new Param().line1(str);}
    public static Param addressLine2(ValueLine str){return new Param().line2(str);}
    //    public static Param addressLine3(ValueLine str){return new Param().line3(str);}
    public static Param addressCity(ValueLine str){return new Param().addressCity(str);}
    public static Param addressProv(ValueLine str){return new Param().addressProv(str);}
    public static Param addressPost(ValueLine str){return new Param().addressPost(str);}

    public ValueLine approved(){return get(FieldName.Approved);}
    public static Param approved(ValueLine approved){return new Param().approved(approved);}

    public ValueLine author(){return get(FieldName.Author);}
    public static Param author(ValueLine author){return new Param().author(author);}

    public ValueLine card(){return get(FieldName.Card);}
    public static Param card(ValueLine card){return new Param().card(card);}

    public ValueLine cashier(){return get(FieldName.Cashier);}
    public static Param cashier(ValueLine cashier){return new Param().cashier(cashier);}

    public ValueLine chain(){return get(FieldName.Chain);}
    public static Param chain(ValueLine chain){return new Param().chain(chain);}

    public ValueLine chainID(){return get(FieldName.ChainID);}
    public static Param chainID(ValueLine chainID){return new Param().chainID(chainID);}

    public ValueLine date(){return get(FieldName.Date);}
    public static Param date(ValueLine date){return new Param().date(date);}

    public ValueLine gstAmount(){return get(FieldName.GstAmount);}
    public static Param gstAmount(ValueLine gstAmount){return new Param().gstAmount(gstAmount);}

    public ValueLine gstNumber(){return get(FieldName.GstNumber);}
    public static Param gstNumber(ValueLine gstNumber){return new Param().gstNumber(gstNumber);}

    public ValueLine phone(){return get(FieldName.Phone);}
    public static Param phone(ValueLine phone){return new Param().phone(phone);}

    public ValueLine ref(){return get(FieldName.Ref);}
    public static Param ref(ValueLine ref){return new Param().phone(ref);}

    public ValueLine recycle(){return get(FieldName.Recycle);}
    public static Param recycle(ValueLine recycle){return new Param().recycle(recycle);}

    public ValueLine slogan(){return get(FieldName.Slogan);}
    public static Param slogan(ValueLine slogan){return new Param().slogan(slogan);}

    public ValueLine storeBranch(){return get(FieldName.StoreBranch);}
    public static Param storeBranch(ValueLine storeBranch){return new Param().storeBranch(storeBranch);}

    public ValueLine storeID(){return get(FieldName.StoreID);}
    public static Param storeID(ValueLine storeID){return new Param().storeID(storeID);}

    public ValueLine subTotal(){return get(FieldName.SubTotal);}
    public static Param subTotal(ValueLine subTotal){return new Param().subTotal(subTotal);}

    public ValueLine thankyou(){return get(FieldName.Thankyou);}
    public static Param thankyou(ValueLine thankyou){return new Param().thankyou(thankyou);}

    public ValueLine total(){return get(FieldName.Total);}
    public static Param total(ValueLine total){return new Param().total(total);}

    public ValueLine totalSold(){return get(FieldName.TotalSold);}
    public static Param totalSold(ValueLine totalSold){return new Param().totalSold(totalSold);}


    public boolean isEmpty(){
        // return fieldNameToValueLine.isEmpty();
        return numNonEmptyFields()==0;
    }

    //all non empty fields; a bit confusing because fieldNameToValueLine is not empty.
    public int numNonEmptyFields(){
        int count=0;
        for(FieldName f:fieldNameToValueLine().keySet() ){
            if(!fieldNameToValueLine().get(f).isEmpty()){
                count++;
                System.out.println("fieldName is "+f+", line is "+fieldNameToValueLine().get(f).getLine());
            }
        }
        return count;
    }

    public int numFields(){
        return fieldNameToValueLine().size();
    }

    //avoid using equal() because it is more strict
    public boolean myEquals(FieldSet another){
        return fieldNameToValueLine().equals(another.fieldNameToValueLine());
    }

    @Override
    public String toString(){
        String str="";
        for(FieldName f: fieldNameToValueLine().keySet()){
            str += f+":"+fieldNameToValueLine().get(f)+"\n";
        }
        return str;
    }


    @Override
    public boolean equals(Object that){
        if(that==null) return false;
        if(getClass() != that.getClass()) return false;
        final FieldSet another=(FieldSet)that;
        return fieldNameToValueLine().equals(another.fieldNameToValueLine());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + fieldNameToValueLine().hashCode();
        return hash;
    }


    // added by Yuan
    // copied from AbstractFieldParser
    public int itemStopLine() {
        int stopLine =
                Math.max(gstAmount().getLine(),
                        Math.max(
                                total().getLine(),
                                subTotal().getLine()
                                )
                        );
        if(stopLine<0) stopLine=Integer.MAX_VALUE;
        return stopLine;

    }
}
