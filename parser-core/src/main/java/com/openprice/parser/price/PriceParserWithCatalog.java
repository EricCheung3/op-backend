package com.openprice.parser.price;

import java.util.HashSet;
import java.util.Set;

import com.openprice.parser.api.Product;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 *  parse a receipt line with catalog to have product, and price
 *
 */
@Data
@Slf4j
public class PriceParserWithCatalog {

    PriceParser priceParser;

    Set<Product> catalog = new HashSet<Product>();

//    private static final NonWideSpaceParserImpl nonSpaceParser = new NonWideSpaceParserImpl();

    public PriceParserWithCatalog(final PriceParser parser, final Set<Product>  catalog){
        this.priceParser=parser;
        this.catalog=catalog;
    }

    public static PriceParserWithCatalog withCatalog(final Set<Product> catalog){
        return  new PriceParserWithCatalog(new PriceParserFromStringTuple(), catalog);
    }

    public static PriceParserWithCatalog emptyCatalog(){
        return PriceParserWithCatalog.withCatalog(new HashSet<Product>());
    }

    /**
     * parse a price line using a catalog to find the best matched product for item name and number
     * the price prefers results from 4 strings separated by wide spaces
     * @param line
     * @return
     */
    public ProductPrice parsePriceLine(final String line){
        final Product matched=PriceParserUtils.matchLineToCatalog(line, catalog);
        if(!matched.isEmpty())
            log.debug("line="+line+", matched product "+matched.toString()+"\n");
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
            return new ProductPrice(matched, pPrice4.getPrice(), true);
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
            return new ProductPrice(matched, pPrice3.getPrice(), true);
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
            return new ProductPrice(matched, pPrice2.getPrice(), true);
        }

        if(pPrice2!=null &&  !pPrice2.isEmpty()) return pPrice2;

//        final int[] digitsChars = StringCommon.countDigitAndAlphabets(line);
        //TODO this needs an ML algorithm!
//        if(line.contains("$") ||
//                (digitsChars[0] >=2 && line.contains(".")) )//likely to be an item
//            return nonSpaceParser.parse(line);

        return ProductPrice.emptyValue();
    }

//    public static List<String> pruneAndSplitLongString(final String line){
//        final List<String> result = new ArrayList<>();
//        final String price = StringCommon.formatPrice(line);
//        final String head = StringCommon.removeMatchingTail(line, price);
////        log.debug("head="+head);
////        log.debug("price="+price);
//        if(head.endsWith("$"))
//            result.add(head.substring(0, head.length()-1).trim());
//        else
//            result.add(head.trim());
//
//        result.add(price.trim());
//        return result;
//    }



}
