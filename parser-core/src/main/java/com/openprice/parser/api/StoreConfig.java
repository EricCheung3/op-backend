package com.openprice.parser.api;

import java.util.List;

import com.openprice.parser.ReceiptFieldType;

public interface StoreConfig {

    int blackListSize();

    boolean matchesBlackList(String str);

    boolean matchesSkipAfter(String str, double threshold);

    boolean matchesSkipBefore(String str, double threshold);

    //a substring pattern of the quantity price line (sell by numbers of items)
    public List<String> substringOfQuantityPriceLine();

    public List<String> substringOfUnitPriceLine();

    //non-header configurations
    public String authorExample();

    public int buyPriceStartColumn() throws NumberFormatException;

    public boolean buyPriceIsAdjusted();

    public String chain();

    public String DTFormat();

    public String gstExample();

    public int minHeadSpacesBeforeCategory()throws NumberFormatException;

    public double similarityThresholdOfTwoStrings() throws NumberFormatException;

    public String phoneExample();

    public String priceTail();

    //quantity price appears first
    public boolean quantityPriceFirst();

    public String quantityPriceExample();

    public String refExample();

    public String slogan();

    public boolean unitPriceFirst();

    public int unitPriceStartColumn() throws NumberFormatException;

    public String weightUnit()throws NumberFormatException;

    public int widthLowerBound()throws NumberFormatException;

    public List<String> getFieldHeaderMatchStrings(final ReceiptFieldType fieldName);

}
