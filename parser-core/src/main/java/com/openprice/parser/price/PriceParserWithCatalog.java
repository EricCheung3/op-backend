package com.openprice.parser.price;

import java.util.Set;

import com.openprice.parser.data.Product;

import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

/**
 *  parse a receipt line with catalog to have product, and price
 *
 */
@Value
@Builder
@Slf4j
public class PriceParserWithCatalog {

    Set<Product> catalog;

    PriceParser priceParser;

    /**
     * parse a price line using a catalog to find the best matched product for item name and number
     * the price prefers results from 4 strings separated by wide spaces
     * @param line
     * @return
     */
    public ProductPrice parsePriceLine(final String line){
        final Product matched=PriceParserUtils.matchLineToCatalog(line, catalog);
        log.debug("matched product "+matched.toStringForCatalog()+"\n");
        //        if(!matched.isEmpty()){
        //            final String priceAtTail=getPriceAtTail(line, matched);
        //            if(!priceAtTail.isEmpty())
        //                return ProductPrice.builder()
        //                        .product(matched)
        //                        .price(priceAtTail)
        //                        .build();
        //    }

        FourStrings four=null;
        ProductPrice pPrice4 = null;
        try{
            four=StringsFromWideSpace.fourStrings(line);
            pPrice4 = priceParser.fromFourStrings(four);
        }catch(Exception e){
            log.warn("line="+line+",fromFourStrings: "+e.getMessage());
        }

        if( !matched.isEmpty() && !pPrice4.isEmpty()){
            return ProductPrice.builder()
                    .product(matched)
                    .productIsInCatalog(true)
                    .price(pPrice4.getPrice())
                    .build();
        }

        //not matched but pPrice4 is good
        if(pPrice4!=null && !pPrice4.isEmpty()){
            return pPrice4;
        }

        ThreeStrings tri=null;
        ProductPrice pPrice3 = null;
        try{
            tri=StringsFromWideSpace.trippleStrings(line);
            pPrice3=priceParser.fromThreeStrings(tri);
        }catch(Exception e){
            log.warn("line="+ line+", fromThreeStrings: "+e.getMessage());
        }
        if( !matched.isEmpty() && !pPrice3.isEmpty()){
            return ProductPrice.builder()
                    .product(matched)
                    .productIsInCatalog(true)
                    .price(pPrice3.getPrice())
                    .build();
        }

        //not matched but pPrice3 is good
        if(pPrice3!=null && !pPrice3.isEmpty()){
            return pPrice3;
        }

        TwoStrings two=null;
        ProductPrice pPrice2 = null;
        try{
            two=StringsFromWideSpace.twoStrings(line);
            pPrice2=priceParser.fromTwoStrings(two);
        }catch(Exception e){
            log.warn("line="+ line+",fromTwoStrings: "+e.getMessage());
        }
        if( !matched.isEmpty() && !pPrice2.isEmpty()){
            return ProductPrice.builder()
                    .product(matched)
                    .productIsInCatalog(true)
                    .price(pPrice2.getPrice())
                    .build();
        }

        if(pPrice2!=null &&  !pPrice2.isEmpty()) return pPrice2;
        return ProductPrice.emptyValue();
    }

}
