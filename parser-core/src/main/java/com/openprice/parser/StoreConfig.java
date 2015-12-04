package com.openprice.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.openprice.parser.data.ReceiptField;
import com.openprice.parser.price.CatalogFilter;

import lombok.Getter;

public class StoreConfig {
    private static final String SPLITTER=",";//splitter in the lines of the config.properties file
    private final Properties prop;

    private final List<String> category;
    @Getter
    private final List<String> skipBefore;
    @Getter
    private final List<String> skipAfter;
    @Getter
    private final CatalogFilter catalogFilter;

    public StoreConfig(final Properties prop,
            final List<String> category,
            final List<String> skipBefore,
            final List<String> skipAfter,
            final List<String> blackList) {
        this.prop = prop;
        this.category = category;
        this.skipBefore = skipBefore;
        this.skipAfter = skipAfter;
        this.catalogFilter=CatalogFilter.builder().blackList(blackList).build();
    }

    //a substring pattern of the quantity price line (sell by numbers of items)
    public List<String> substringOfQuantityPriceLine(){
        return  splitLine("SubstringOfQuantityPriceLine");
    }
    public List<String> substringOfUnitPriceLine(){
        return  splitLine("SubstringOfUnitPriceLine");
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
        List<String> result = Arrays.asList(lineStr.split(SPLITTER));
        return result;
    }

    public List<String> getFieldHeaderMatchStrings(final ReceiptField fieldName) {
        return splitLine(fieldName.name()+"Header");
    }
}
