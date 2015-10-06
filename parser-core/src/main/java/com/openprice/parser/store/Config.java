package com.openprice.parser.store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.openprice.parser.data.FieldName;

import lombok.Getter;

public class Config {
    private static final String SPLITTER=",";//splitter in the lines of the config.properties file
    private final Properties prop;
    @Getter
    private final List<StoreBranch> branches;

    private final String HeaderSeparator=SPLITTER;

    private final List<String> category;
    private final List<String> skipBefore;
    private final List<String> skipAfter;

    public Config(final Properties prop,
            final List<StoreBranch> branches,
            final List<String> category,
            final List<String> skipBefore,
            final List<String> skipAfter) {
        this.prop = prop;
        this.branches = branches;
        this.category = category;
        this.skipBefore = skipBefore;
        this.skipAfter = skipAfter;
    }

    public List<String> accountHeader(){return splitLine("AccountHeader"); }

    public List<String> addressLine1Header(){return splitLine("AddressLine1Header"); }
    public List<String> addressLine2Header(){return splitLine("AddressLine2Header"); }
    public List<String> addressCityHeader(){return splitLine("AddressCityHeader"); }
    public List<String> addressProvHeader(){return splitLine("AddressProvHeader"); }
    public List<String> addressPostHeader(){return splitLine("AddressPostHeader"); }

    public List<String> approvedHeader(){return splitLine("ApprovedHeader"); }

    public List<String> authorHeader(){return splitLine("AuthorHeader"); }

    public List<String> cardHeader(){return splitLine("CardHeader"); }

    public List<String> cashierHeader(){return splitLine("CashierHeader"); }

    public List<String> chainHeader(){return splitLine("ChainHeader"); }

    public List<String> chainIDHeader(){return splitLine("ChainIDHeader"); }

    public List<String> couponHeader(){return splitLine("CouponHeader"); }

    public List<String> dateHeader(){return splitLine("DateHeader"); }

    public List<String> depositHeader(){return splitLine("DepositHeader");}

    public List<String> gstHeader(){return splitLine("GstHeader"); }
    public List<String> gstAmountHeader(){return gstHeader(); }

    public List<String> phoneHeader(){return splitLine("PhoneHeader"); }

    public List<String> gstNumberHeader(){return splitLine("GstNumberHeader"); }

    public List<String> recycleHeader(){return splitLine("RecycleHeader"); }

    public List<String> refHeader(){return splitLine("RefHeader"); }

    public List<String> regPriceHeader(){
        return splitLine("RegularPriceHeader");
    }

    public List<String> savingHeader(){
        return splitLine("SavingHeader");
    }

    public List<String> sloganHeader(){return splitLine("SloganHeader"); }

    public List<String> storeBranchHeader(){
        return  splitLine("StoreBranchHeader");
    }

    public List<String> storeIDHeader(){return splitLine("StoreIDHeader"); }

    //a substring pattern of the quantity price line (sell by numbers of items)
    public List<String> substringOfQuantityPriceLine(){
        return  splitLine("SubstringOfQuantityPriceLine");
    }
    public List<String> substringOfUnitPriceLine(){
        return  splitLine("SubstringOfUnitPriceLine");
    }
    public List<String> subTotalHeader(){
        return  splitLine("SubTotalHeader");
    }
    public List<String> thankyouHeader(){
        return  splitLine("ThankYouHeader");
    }
    public List<String> totalHeader(){
        return  splitLine("TotalHeader");
    }
    public List<String> totalSoldHeader(){
        return  splitLine("TotalSoldHeader");
    }

    //non-header configurations
    public String authorExample(){
        return prop.getProperty("AuthorExample");
    }

    public int buyPriceStartColumn() throws NumberFormatException{
        return Integer.valueOf(prop.getProperty("BuyPriceStartColumn"));
    }

    public boolean buyPriceIsAdjusted(){
        return prop.getProperty("BuyPriceIsAdjusted").toLowerCase().equals("true");
    }

    public String chain(){
        return prop.getProperty("Chain");
    }

    public String DTFormat(){
        return prop.getProperty("DTFormat");
    }

    public String gstExample(){
        return prop.getProperty("GstExample");
    }

    public int minHeadSpacesBeforeCategory()throws NumberFormatException{
        return Integer.valueOf(prop.getProperty("MinHeadSpacesBeforeCategory"));
    }

    public double similarityThresholdOfTwoStrings() throws NumberFormatException{
        return Double.valueOf(prop.getProperty("SimilarityThresholdOfTwoStrings"));
    }

    public String phoneExample(){
        return prop.getProperty("PhoneExample");
    }
    public String priceTail(){
        return prop.getProperty("PriceTail");
    }
    //quantity price appears first
    public boolean quantityPriceFirst(){
        return prop.getProperty("QuantityPriceFirst").toLowerCase().equals("true");
    }
    public String quantityPriceExample(){
        return prop.getProperty("QuantityPriceExample");
    }

    public String refExample(){
        return prop.getProperty("RefExample");
    }

    public String slogan(){
        return prop.getProperty("Slogan");
    }

    public boolean unitPriceFirst(){
        String str=prop.getProperty("UnitPriceAppearsFirst").trim();
        return str.toLowerCase().equals("true");
    }
    public int unitPriceStartColumn() throws NumberFormatException{
        return Integer.valueOf(prop.getProperty("UnitPriceStartColumn"));
    }
    public String weightUnit()throws NumberFormatException{
        return prop.getProperty("WeightUnit");
    }
    public int widthLowerBound()throws NumberFormatException{
        return Integer.valueOf(prop.getProperty("WidthLowerBound"));
    }


    private List<String> splitLine(final String headerName){
        String lineStr=prop.getProperty(headerName);
        if(lineStr==null) return new ArrayList<String>();
        List<String>  result=Arrays.asList(lineStr.split(SPLITTER));
        return result;
    }

    public List<String> getFieldHeaderMatchStrings(final FieldName fieldName) {
        return splitLine(fieldName.name()+"Header");
    }
}
