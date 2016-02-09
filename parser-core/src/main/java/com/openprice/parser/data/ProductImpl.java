package com.openprice.parser.data;

import org.springframework.util.StringUtils;

import com.openprice.common.StringCommon;
import com.openprice.parser.api.ProductInterface;
import com.openprice.store.MetadataConstants;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ProductImpl implements ProductInterface{

    private final String name;
    private String number=StringCommon.EMPTY;

    //number of fields when splitting a string containing a product
    private static final int NUM_FIELDS = 2;

    //splitter of name and number in receipt
    private static final String SPLITTER_IN_RECEIPT=" ";

    private ProductImpl(String name){
        this.name=name;
    }

    private ProductImpl(String name, String number){
        this.name=name;
        this.number=number;
    }

    public static ProductImpl fromNameNumber(final String name, final String number){
        return new ProductImpl(name, number);
    }

    public static ProductImpl fromNameOnly(final String name){
        return new ProductImpl(name);
    }

    public static ProductImpl emptyProduct(){
        return fromNameNumber(StringCommon.EMPTY, StringCommon.EMPTY);
    }

    //removing the head of "(2)05870328668" to change to "05870328668"
    public static String cleanItemNumber(final String num){
        String cleaned= num.replaceAll("\\s+", StringCommon.EMPTY);
        if(cleaned.length()>3
                && cleaned.startsWith("(")
                && Character.isDigit(cleaned.charAt(1))
                && cleaned.charAt(2)==')')
            return cleaned.substring(3);
        return cleaned;
    }

    public boolean isEmpty(){
        return name.isEmpty() && number.isEmpty();
    }

    public static ProductImpl fromString(final String line) {
        final String[] words=line.split(MetadataConstants.PRODUCT_LINE_SPLITTER, -1);
        if(words.length!=NUM_FIELDS){
            throw new RuntimeException("should have "+NUM_FIELDS +" fields at this line: "+line);
        }
        try{
            final ProductImpl catLine = ProductImpl.fromNameNumber(words[0], words[1]);
            return catLine;
        }catch(Exception e){
            log.debug("line ="+line+", "+e.getMessage());
            return ProductImpl.emptyProduct();
        }
    }

    public String toStringNameFirst(){
        return name+SPLITTER_IN_RECEIPT+ number;
    }

    public String toStringNumberFirst(){
        return number+SPLITTER_IN_RECEIPT+ name;
    }

    public String toStringForCatalog(){
        final String name=getName().replaceAll(MetadataConstants.PRODUCT_LINE_SPLITTER, StringCommon.EMPTY);
        final String number=getNumber().replaceAll(MetadataConstants.PRODUCT_LINE_SPLITTER, StringCommon.EMPTY);
        return name + MetadataConstants.PRODUCT_LINE_SPLITTER + number;
    }

    public String toCatalogCode() {
        if (StringUtils.isEmpty(number)) {
            return name;
        }
        return name + MetadataConstants.CATALOG_CODE_SPLITTER + number;
    }

}
