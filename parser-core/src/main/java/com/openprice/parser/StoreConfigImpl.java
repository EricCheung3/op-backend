package com.openprice.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.openprice.parser.api.CatalogFilter;
import com.openprice.parser.api.StoreConfig;
import com.openprice.parser.common.ListCommon;
import com.openprice.parser.price.CatalogFilterImpl;

import lombok.Getter;

public class StoreConfigImpl implements StoreConfig{
    private static final String SPLITTER=",";//splitter in the lines of the config.properties file
    @Getter
    private final Properties prop;

    @Getter
    private final List<String> category;
    @Getter
    private final List<String> skipBefore;
    @Getter
    private final List<String> skipAfter;
    @Getter
    private final CatalogFilter catalogFilter;

    public static StoreConfigImpl fromPropCategorySkipBeforeAfterBlack(
            final Properties prop,
            final List<String> category,
            final List<String> skipBefore,
            final List<String> skipAfter,
            final List<String> blackList){
        return new StoreConfigImpl(prop, category, skipBefore, skipAfter, blackList);
    }

    private StoreConfigImpl(final Properties prop,
            final List<String> category,
            final List<String> skipBefore,
            final List<String> skipAfter,
            final List<String> blackList) {
        this.prop = prop;
        this.category = category;
        this.skipBefore = skipBefore;
        this.skipAfter = skipAfter;
        this.catalogFilter=CatalogFilterImpl.fromList(blackList);
    }

    @Override
    public List<String> substringOfQuantityPriceLine(){
        return  splitLine("SubstringOfQuantityPriceLine");
    }
    @Override
    public List<String> substringOfUnitPriceLine(){
        return  splitLine("SubstringOfUnitPriceLine");
    }

    //non-header configurations
    @Override
    public String authorExample(){
        return prop.getProperty("AuthorExample");
    }

    @Override
    public int buyPriceStartColumn() throws NumberFormatException{
        return Integer.valueOf(prop.getProperty("BuyPriceStartColumn"));
    }

    @Override
    public boolean buyPriceIsAdjusted(){
        return prop.getProperty("BuyPriceIsAdjusted").toLowerCase().equals("true");
    }

    @Override
    public String chain(){
        return prop.getProperty("Chain");
    }

    @Override
    public String DTFormat(){
        return prop.getProperty("DTFormat");
    }

    @Override
    public String gstExample(){
        return prop.getProperty("GstExample");
    }

    @Override
    public int minHeadSpacesBeforeCategory()throws NumberFormatException{
        return Integer.valueOf(prop.getProperty("MinHeadSpacesBeforeCategory"));
    }

    @Override
    public double similarityThresholdOfTwoStrings() throws NumberFormatException{
        return Double.valueOf(prop.getProperty("SimilarityThresholdOfTwoStrings"));
    }

    @Override
    public String phoneExample(){
        return prop.getProperty("PhoneExample");
    }
    @Override
    public String priceTail(){
        return prop.getProperty("PriceTail");
    }
    //quantity price appears first
    @Override
    public boolean quantityPriceFirst(){
        return prop.getProperty("QuantityPriceFirst").toLowerCase().equals("true");
    }
    @Override
    public String quantityPriceExample(){
        return prop.getProperty("QuantityPriceExample");
    }

    @Override
    public String refExample(){
        return prop.getProperty("RefExample");
    }

    @Override
    public String slogan(){
        return prop.getProperty("Slogan");
    }

    @Override
    public boolean unitPriceFirst(){
        String str=prop.getProperty("UnitPriceAppearsFirst").trim();
        return str.toLowerCase().equals("true");
    }
    @Override
    public int unitPriceStartColumn() throws NumberFormatException{
        return Integer.valueOf(prop.getProperty("UnitPriceStartColumn"));
    }
    @Override
    public String weightUnit()throws NumberFormatException{
        return prop.getProperty("WeightUnit");
    }
    @Override
    public int widthLowerBound()throws NumberFormatException{
        return Integer.valueOf(prop.getProperty("WidthLowerBound"));
    }

    private List<String> splitLine(final String headerName){
        String lineStr=prop.getProperty(headerName);
        if(lineStr==null) return new ArrayList<String>();
        List<String> result = Arrays.asList(lineStr.split(SPLITTER));
        return result;
    }

    @Override
    public List<String> getFieldHeaderMatchStrings(final ReceiptFieldType fieldName) {
        return splitLine(fieldName.name()+"Header");
    }

    @Override
    public boolean matchesSkipAfter(String str, double threshold) {
        return ListCommon.matchList(skipAfter, str, threshold);
    }

    @Override
    public boolean matchesSkipBefore(String str, double threshold) {
        return ListCommon.matchList(skipBefore, str, threshold);
    }

    @Override
    public boolean matchesBlackList(String str) {
        return catalogFilter.matchesBlackList(str);
    }

    @Override
    public int blackListSize() {
        return catalogFilter.blackListSize();
    }
}
