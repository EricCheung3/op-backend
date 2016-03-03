package com.openprice.parser.price;

import com.openprice.common.StringCommon;
import com.openprice.parser.line.SimpleLinePredcitor;
import com.openprice.parser.ml.api.LineType;
import com.openprice.parser.ml.api.PriceParserConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * a price parser that aims to work for every store receipt line.
 * The idea is simple. There are only a few variations of receipt line formats.
 * All the formats is a based on a line containing one, two, three, four, five strings in a receipt line.
 * So this price parser basically generate the product name, number, price from these string tuples.
 * see wiki
 * https://bitbucket.org/groundtruthinc/openprice-parser/issues/65/universal-price-parser
 *
 */
@Slf4j
public class ParserFromStringTuple implements ParserFromNStrings {

//    private static NonWideSpaceParserImpl nonSpaceParser = new NonWideSpaceParserImpl();

    private final static SimpleLinePredcitor linePredictor = new SimpleLinePredcitor();

    //product and price from four strings
    //product and price from four strings
    @Override
    public ProductPrice fromFourStrings(final FourStrings four) throws Exception{
        final boolean firstIsNumber=isItemNumber(four.getFirst());
        final boolean secondIsNumber=isItemNumber(four.getSecond());
        final boolean thirdIsNumber=isItemNumber(four.getThird());
        //final boolean FourthIsNumber=isItemNumber(four.getFourth()); //ignore this variable

        String itemNumber=StringCommon.EMPTY;
        String itemName=StringCommon.EMPTY;
        String price=StringCommon.EMPTY;
        if(firstIsNumber && secondIsNumber && thirdIsNumber){
            throw new Exception("The first three strings are all numbers. Kind of rare in receipts.");
        }

        if(firstIsNumber && secondIsNumber){
            //            log.debug("both first and second are item numbers");
            itemName=four.getThird();
            price=four.getFourth();
            itemNumber= four.getFirst()+" "+ four.getSecond();

            return ProductPriceUtils.fromNameCut(itemName, itemNumber, price);
        }

        if(secondIsNumber){
            //            log.debug("second is item numbers");
            itemName=four.getFirst();
            itemNumber=four.getSecond();
            price=priceFromTwoSuccesiveStrings(four.getThird(), four.getFourth());
            return ProductPriceUtils.fromNameCut(itemName, itemNumber, price);
        }

        if(firstIsNumber){//second is not item number
            //            log.debug("first is item numbers");
            itemNumber=four.getFirst();
            if(linePredictor.classify(four.getThird()) == LineType.Item){
                itemName=four.getSecond()+StringCommon.WIDE_SPACES+four.getThird();
                price=four.getFourth();
            }else{
                itemName=four.getSecond();
                price=priceFromTwoSuccesiveStrings(four.getThird(), four.getFourth());
            }
            return ProductPriceUtils.fromNameCut(itemName, itemNumber, price);
        }

        //        log.debug("assumed to be no item numbers");
        //TODO: put item number splitting detection
        if(linePredictor.classify(four.getThird()) == LineType.Item){
            itemName=four.getFirst()+StringCommon.WIDE_SPACES+
                    four.getSecond()+StringCommon.WIDE_SPACES+four.getThird();
            price=four.getFourth();
        }else{
            itemName=four.getFirst()+StringCommon.WIDE_SPACES+four.getSecond();
            price=priceFromTwoSuccesiveStrings(four.getThird(), four.getFourth());
        }
        return ProductPriceUtils.fromNameCut(itemName, itemNumber, price);
    }

//    private static ProductPrice fromNameCut(final String itemName, final String itemNumber, final String price){
//        String name = itemName;
//        String number = itemNumber;
//        if(itemNumber.isEmpty()){
//            //number is embedded in name, split
//            final ProductPrice pp = nonSpaceParser.parse(itemName);
//            name = pp.getName();
//            number = pp.getNumber();
//        }
//        return new ProductPrice(ProductImpl.fromNameNumber(name, number), StringCommon.formatPrice(price));
//    }
//
//    private static String priceFromTwoSuccesiveStrings(final String s1, final String s2){
//        String price="";
//        if(isNotPrice(s1)){
//            //            log.debug("s1="+s1+" is not price, s2="+s2+" is assumed to be price.");
//            price=s2;
//        }else{
//            if(isNotPrice(s2)){
//                //                log.debug("s2="+s1+" is not price, s1="+s1+" is assumed to be price.");
//                price=s1;
//            }else
//                price=s1+StringCommon.WIDE_SPACES+s2;
//        }
//        return price;
//    }

    //product and price from four strings
    @Override
    public ProductPrice fromThreeStrings(final ThreeStrings three) throws Exception{
        log.debug("fromThreeStrings");
        final boolean firstIsNumber=isItemNumber(three.getFirst());
        final boolean secondIsNumber=isItemNumber(three.getSecond());
        final boolean thirdIsNumber=isItemNumber(three.getThird());

        if(firstIsNumber && secondIsNumber && thirdIsNumber){
            throw new Exception("The first three strings are all numbers. Kind of rare in receipts.");
        }

        String itemName="";
        String itemNumber="";
        String price="";
        if(firstIsNumber && secondIsNumber){
            itemNumber=three.getFirst()+StringCommon.WIDE_SPACES+three.getSecond();
            if(isNotPrice(three.getThird())){
                itemName=three.getThird();
            }else{
                price=three.getThird();
            }
            return ProductPriceUtils.fromNameCut(itemName, itemNumber, price);
        }

        if(firstIsNumber){
            log.debug("First is an item number");
            //second is not an item number
            itemNumber=three.getFirst();
            if(isNotPrice(three.getSecond())){
                itemName=three.getSecond();
                price=three.getThird();
            }else{
                price=priceFromTwoSuccesiveStrings(three.getSecond(), three.getThird());
            }
            return ProductPriceUtils.fromNameCut(itemName, itemNumber, price);
        }

        if(secondIsNumber){
            log.debug("Second is an item number");
            //first is not a number;so assume to be name
            //TODO: throw exception if not a name?
            itemName=three.getFirst();
            if(thirdIsNumber){
                itemNumber=three.getSecond()+StringCommon.WIDE_SPACES+three.getThird();
            }else{
                itemNumber=three.getSecond();
                if(isNotPrice(three.getThird())){
                    throw new Exception("String "+three.getThird()+" got to be a price");
                }
                price=three.getThird();
            }
            return ProductPriceUtils.fromNameCut(itemName, itemNumber, price);
        }

        if(thirdIsNumber){
            if(isNotPrice(three.getThird())){
                log.debug("Third "+ three.getThird() + " is NOT considered as a price");
                throw new Exception("String "+three.getThird()+" got to be a price");
            }
            log.debug("Third "+ three.getThird() + " is considered as a price");
            itemName=three.getFirst()+StringCommon.WIDE_SPACES + three.getSecond();
            itemNumber=StringCommon.EMPTY;
            price=three.getThird();;
        }

        log.debug("0: No item numbers.");
        if(isNotPrice(three.getThird())){
            price=three.getSecond();
            itemName=three.getFirst();
            log.debug("the third is not a price, treating "+ price+" as price");
        }else{
            if(isNotPrice(three.getSecond())){
                log.debug("the second is not a price");
                price=three.getThird();
                itemName=three.getFirst()+StringCommon.WIDE_SPACES+three.getSecond();
            }else{
                log.debug("the second is also a price");
                //second is price and third is also price; prefer the third, the second is ignored
                //TODO: good or bad? maybe bad
                price=three.getThird();
                itemName=three.getFirst();
            }
        }
        return ProductPriceUtils.fromNameCut(itemName, itemNumber, price);
    }

    //TODO actually no exception thrown
    @Override
    public ProductPrice fromTwoStrings(final TwoStrings two) throws Exception{
        log.debug("from two strings");
        final boolean firstIsNumber=isItemNumber(two.getFirst());
        final boolean secondIsNumber=isItemNumber(two.getSecond());

        String itemName="";
        String itemNumber="";
        String price="";
        if(firstIsNumber && secondIsNumber){//all number
            log.debug("both first second are numbers.");
            itemNumber=two.getFirst() + StringCommon.WIDE_SPACES+two.getSecond();
            return ProductPriceUtils.fromNameCut(itemName, itemNumber, price);
        }

        if(firstIsNumber){
            itemNumber=two.getFirst();
            if(isNotPrice(two.getSecond())){
                log.debug(two.getSecond()+ " is not a price");
                itemName=two.getSecond();
            }else{
                price=two.getSecond();
            }
            return ProductPriceUtils.fromNameCut(itemName, itemNumber, price);
        }

        if(secondIsNumber){
            itemName=two.getFirst();
//            itemNumber=two.getSecond();
            if(isVeryLikelyPrice(two.getSecond()))
                price = two.getSecond();
            else
                itemNumber=two.getSecond();
            log.debug("itemName = "+itemName + ", itemNumber= "+ itemNumber+", price="+price );
            return ProductPriceUtils.fromNameCut(itemName, itemNumber, price);
        }

        log.debug("1: No item number.");
        itemNumber="";
        if(isNotPrice(two.getSecond())){
            log.debug(two.getSecond()+" is not a price.");
            itemName=two.getFirst() + StringCommon.WIDE_SPACES + two.getSecond();
            price="";
            //return ProductPriceUtils.fromNameCut(itemName, itemNumber, price);
        }else{
            itemName=two.getFirst();
            price=two.getSecond();
        }
        return ProductPriceUtils.fromNameCut(itemName, itemNumber, price);
    }


    //The string is an item number
    public static boolean isItemNumber(final String str){
        int[] counts=StringCommon.countDigitAndLetters(str);
        if(counts[0] < PriceParserConstant.MIN_ITEM_NUMBER_LENGTH)
            return false;
        if(isVeryLikelyPrice(str)){
            return false;
        }
        final double score= (double)counts[0] / (counts[0]+counts[1]);
        //        log.debug("score="+score);
        return score>=PriceParserConstant.MIN_ITEM_NUMBER_PERCENT;
    }

//    public static boolean isItemName(final String str){
////        final String noSpace=str.replaceAll("\\s+", "");
//        int[] counts=StringCommon.countDigitAndLetters(str);
////        log.debug("counts[1]="+counts[1]);
//        if(counts[1] < PriceParserConstant.MIN_ITEM_NAME_LETTERS)
//            return false;
//        final double ratioOfChars=(double)counts[1]/(counts[0]+counts[1]);
////        if(ratioOfChars < PriceParserConstant.MIN_ITEM_NAME_LETTERS_PERCENT)
////            log.debug("score="+ratioOfChars+", PriceParserConstant.MIN_ITEM_NAME_LETTERS_PERCENT= "+ PriceParserConstant.MIN_ITEM_NAME_LETTERS_PERCENT);
//        return ratioOfChars >= PriceParserConstant.MIN_ITEM_NAME_LETTERS_PERCENT;
//    }


    public static boolean isNotPrice(final String str){
        int[] counts=StringCommon.countDigitAndLetters(str);
        if(counts[0] <= 0
                || counts[1] >= PriceParserConstant.MIN_ITEM_NAME_LETTERS+2
                || (str.trim().startsWith("0") && !str.contains(".")  )) return true;

        // too-big-value price (like XXXXXX.) is not allowed
        if( !str.contains(".") && StringCommon.continuousDigitsBeforeDot(str) >= PriceParserConstant.MIN_ITEM_NAME_LETTERS + 5)
            return true;

        return false;
    }

    //
    public static boolean isVeryLikelyPrice(final String str){
        int[] counts=StringCommon.countDigitAndLetters(str);
        //        log.debug("digits="+counts[0]);
        //        log.debug("letters="+counts[1]);
        //        log.debug(str.contains(".")+"");
        //        log.debug(str.contains("$")+"");
        final boolean result=
                counts[0] <= 5 &&
                counts[0] > 0  &&
                    ( str.contains(".")
                        || str.contains("$")
                        || !str.contains(".") && str.contains(","));
        //        if(result)
        //            log.debug(str+" is a very likely a price");
        return result;
    }

    private static String priceFromTwoSuccesiveStrings(final String s1, final String s2){
        String price="";
        if(isNotPrice(s1)){
            //            log.debug("s1="+s1+" is not price, s2="+s2+" is assumed to be price.");
            price=s2;
        }else{
            if(isNotPrice(s2)){
                //                log.debug("s2="+s1+" is not price, s1="+s1+" is assumed to be price.");
                price=s1;
            }else
                price=s1+StringCommon.WIDE_SPACES+s2;
        }
        return price;
    }
}
