package com.openprice.parser.data;

import org.springframework.util.StringUtils;

import com.openprice.parser.common.StringCommon;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * represents a product in a catalog
 *
 */
@Data
@Slf4j
public class Product {

    private final String name;
    private String number=StringCommon.EMPTY;

    //catalog code splitter
    private static final String CATALOG_CODE_SPLITTER="_";

    //number of fields when splitting a string containing a product
    private static final int NUM_FIELDS = 2;

    //splitter of name and number in receipt
    private static final String SPLITTER_IN_RECEIPT=" ";

    private Product(String name){
        this.name=name;
    }

    private Product(String name, String number){
        this.name=name;
        this.number=number;
    }

    public static Product fromNameNumber(final String name, final String number){
        return new Product(name, number);
    }

    public static Product fromNameOnly(final String name){
        return new Product(name);
    }

    public static Product emptyProduct(){
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

    public static Product fromString(final String line) {
        final String[] words=line.split(ProductConstant.SPLITTER, -1);
        if(words.length!=NUM_FIELDS){
            throw new RuntimeException("should have "+NUM_FIELDS +" fields at this line: "+line);
        }
        try{
            final Product catLine = Product.fromNameNumber(words[0], words[1]);
            return catLine;
        }catch(Exception e){
            log.debug("line ="+line+", "+e.getMessage());
            return Product.emptyProduct();
        }
    }

    public String toStringNameFirst(){
        return name+SPLITTER_IN_RECEIPT+ number;
    }

    public String toStringNumberFirst(){
        return number+SPLITTER_IN_RECEIPT+ name;
    }

    public String toStringForCatalog(){
        final String name=getName().replaceAll(ProductConstant.SPLITTER, StringCommon.EMPTY);
        final String number=getNumber().replaceAll(ProductConstant.SPLITTER, StringCommon.EMPTY);
        return name + ProductConstant.SPLITTER + number;
    }

    public String toCatalogCode() {
        if (StringUtils.isEmpty(number)) {
            return name;
        }
        return name + CATALOG_CODE_SPLITTER + number;
    }

}
