package com.openprice.parser.data;

import org.springframework.util.StringUtils;

import com.openprice.parser.common.StringCommon;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * represents a product in a catalog
 *
 */
@Data
@Builder
@Slf4j
public class Product {

    private final String name;
    private final String number;

    private static final int NUM_FIELDS = 2;

    public static Product emptyProduct()
    {
        return Product.builder()
                .name(StringCommon.EMPTY)
                .number(StringCommon.EMPTY)
                .build();
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
            final Product catLine = Product.builder()
                    .name(words[0])
                    .number(words[1])
                    .build();
            return catLine;
        }catch(Exception e){
            log.debug("line ="+line+", "+e.getMessage());
            return Product.emptyProduct();
        }
    }

    //splitter of number and receipt in receipt
    private static final String SPLITTER_IN_RECEIPT=" ";

    public String toStringNameFirst(){
        return name+SPLITTER_IN_RECEIPT+ number;
    }

    public String toStringNumberFirst(){
        return number+SPLITTER_IN_RECEIPT+ name;
    }

    public String toStringForCatalog(){
        String name, number;
        if(getName()==null)
            name=StringCommon.EMPTY;
        else
            name=getName().replaceAll(ProductConstant.SPLITTER, StringCommon.EMPTY);
        if(getNumber()==null)
            number=StringCommon.EMPTY;
        else
            number=getNumber().replaceAll(ProductConstant.SPLITTER, StringCommon.EMPTY);
        return name + ProductConstant.SPLITTER + number;
    }

    public String toCatalogCode() {
        if (StringUtils.isEmpty(number)) {
            return name;
        }
        return name+"_"+number;
    }

}
