package com.openprice.parser.store;

import com.openprice.parser.common.StringCommon;
import com.openprice.parser.data.ProductPrice;
import com.openprice.parser.price.FourStrings;
import com.openprice.parser.price.PriceParser;
import com.openprice.parser.price.ThreeStrings;
import com.openprice.parser.price.TwoStrings;

import lombok.extern.slf4j.Slf4j;

/**
 * a universal price parser that aims to work for every store receipt line.
 * The idea is simple. There are only a few variations of receipt line formats.
 * see wiki
 * https://bitbucket.org/groundtruthinc/openprice-parser/issues/65/universal-price-parser
 *
 */
@Slf4j
public class UniversalPriceParser implements PriceParser{

    public static final int MIN_ITEM_NUMBER_LENGTH=4;
    private static final double MIN_ITEM_NUMBER_PERCENT=0.7;

    private static final int MIN_ITEM_NAME_LETTERS=2;
    private static final double MIN_ITEM_NAME_LETTERS_PERCENT=0.5;
    public static final String WIDE_SPACES="    ";

    //product and price from four strings
    @Override
    public ProductPrice fromFourStrings(final FourStrings four) throws Exception{
        final boolean firstIsNumber=isItemNumber(four.getFirst());
        final boolean secondIsNumber=isItemNumber(four.getSecond());
        final boolean thirdIsNumber=isItemNumber(four.getThird());
        final boolean FourthIsNumber=isItemNumber(four.getFourth());//ignore this variable

        String itemNumber=StringCommon.EMPTY;
        String itemName=StringCommon.EMPTY;
        String price=StringCommon.EMPTY;
        if(firstIsNumber && secondIsNumber && thirdIsNumber){
            throw new Exception("The first three strings are all numbers. Kind of rare in receipts.");
        }

        if(firstIsNumber && secondIsNumber){
            log.debug("both first and second are item numbers");
            itemName=four.getThird();
            price=four.getFourth();
            itemNumber= four.getFirst()+" "+ four.getSecond();
            return ProductPrice.fromNameCut(itemName, itemNumber, price);
        }

        if(secondIsNumber){
            log.debug("second is item numbers");
            itemName=four.getFirst();
            itemNumber=four.getSecond();
            price=priceFromTwoSuccesiveStrings(four.getThird(), four.getFourth());
            return ProductPrice.fromNameCut(itemName, itemNumber, price);
        }

        if(firstIsNumber){//second is not item number
            log.debug("first is item numbers");
            itemNumber=four.getFirst();
            if(isItemName(four.getThird())){
                itemName=four.getSecond()+WIDE_SPACES+four.getThird();
                price=four.getFourth();
            }else{
                itemName=four.getSecond();
                price=priceFromTwoSuccesiveStrings(four.getThird(), four.getFourth());
            }
            return ProductPrice.fromNameCut(itemName, itemNumber, price);
        }

        log.debug("assumed to be no item numbers");
        //TODO: put item number splitting detection
        if(isItemName(four.getThird())){
            itemName=four.getFirst()+WIDE_SPACES+
                    four.getSecond()+WIDE_SPACES+four.getThird();
            price=four.getFourth();
        }else{
            itemName=four.getFirst()+WIDE_SPACES+four.getSecond();
            price=priceFromTwoSuccesiveStrings(four.getThird(), four.getFourth());
        }
        return ProductPrice.fromNameCut(itemName, itemNumber, price);
    }

    private static String priceFromTwoSuccesiveStrings(final String s1, final String s2){
        String price="";
        if(isNotPrice(s1)){
            log.debug("s1="+s1+" is not price, s2="+s2+" is assumed to be price.");
            price=s2;
        }else{
            if(isNotPrice(s2)){
                log.debug("s2="+s1+" is not price, s1="+s1+" is assumed to be price.");
                price=s1;
            }else
                price=s1+WIDE_SPACES+s2;
        }
        return price;
    }

    //product and price from four strings
    @Override
    public ProductPrice fromThreeStrings(final ThreeStrings three) throws Exception{
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
            itemNumber=three.getFirst()+WIDE_SPACES+three.getSecond();
            if(isNotPrice(three.getThird())){
                itemName=three.getThird();
            }else{
                price=three.getThird();
            }
            return ProductPrice.fromNameCut(itemName, itemNumber, price);
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
            return ProductPrice.fromNameCut(itemName, itemNumber, price);
        }

        if(secondIsNumber){
            log.debug("Second is an item number");
            //first is not a number;so assume to be name
            //TODO: throw exception if not a name?
            itemName=three.getFirst();
            if(thirdIsNumber){
                itemNumber=three.getSecond()+WIDE_SPACES+three.getThird();
            }else{
                itemNumber=three.getSecond();
                if(isNotPrice(three.getThird())){
                    throw new Exception("String "+three.getThird()+" got to be a price");
                }
                price=three.getThird();
            }
            return ProductPrice.fromNameCut(itemName, itemNumber, price);
        }

        if(thirdIsNumber){
            log.debug("Third is an item number");
            itemName=three.getFirst()+WIDE_SPACES+three.getSecond();
            itemNumber=three.getThird();
            price="";
        }

        log.debug("No item numbers.");
        if(isNotPrice(three.getThird())){
            price=three.getSecond();
            itemName=three.getFirst();
        }else{
            if(isNotPrice(three.getSecond())){
                price=three.getThird();
                itemName=three.getFirst()+WIDE_SPACES+three.getSecond();
            }else{
                //second is price and third is also price; prefer the third, the second is ignored
                //TODO: good or bad?
                price=price=three.getThird();
                itemName=three.getFirst();
            }
        }
        return ProductPrice.fromNameCut(itemName, itemNumber, price);
    }

    @Override
    public ProductPrice fromTwoStrings(final TwoStrings two) throws Exception{
        final boolean firstIsNumber=isItemNumber(two.getFirst());
        final boolean secondIsNumber=isItemNumber(two.getSecond());

        String itemName="";
        String itemNumber="";
        String price="";
        if(firstIsNumber && secondIsNumber){//all numbers
            itemNumber=two.getFirst() + WIDE_SPACES+two.getSecond();
            return ProductPrice.fromNameCut(itemName, itemNumber, price);
        }

        if(firstIsNumber){
            itemNumber=two.getFirst();
            if(isNotPrice(two.getSecond())){
                itemName=two.getSecond();
            }else{
                price=two.getSecond();
            }
            return ProductPrice.fromNameCut(itemName, itemNumber, price);
        }

        if(secondIsNumber){
            itemName=two.getFirst();
            itemNumber=two.getSecond();
            return ProductPrice.fromNameCut(itemName, itemNumber, price);
        }

        log.debug("No item number.");
        itemNumber="";
        if(isNotPrice(two.getSecond())){
            itemName=two.getFirst()+WIDE_SPACES+two.getSecond();
            price="";
        }else{
            itemName=two.getFirst();
            price=two.getSecond();
        }
        return ProductPrice.fromNameCut(itemName, itemNumber, price);
    }

    //The string is an item number
    public static boolean isItemNumber(final String str){
        int[] counts=StringCommon.countDigitAndLetters(str);
        if(counts[0]<MIN_ITEM_NUMBER_LENGTH)
            return false;
        final double score=(double)counts[0]/(counts[0]+counts[1]);
        //        log.debug("score="+score);
        return score>=MIN_ITEM_NUMBER_PERCENT;
    }

    public static boolean isItemName(final String str){
        int[] counts=StringCommon.countDigitAndLetters(str);
        if(counts[1]<MIN_ITEM_NAME_LETTERS)
            return false;
        final double score=(double)counts[1]/(counts[0]+counts[1]);
        //        log.debug("score="+score);
        return score>=MIN_ITEM_NAME_LETTERS_PERCENT;
    }


    public static boolean isNotPrice(final String str){
        int[] counts=StringCommon.countDigitAndLetters(str);
        if(counts[0]<=0) return true;

        return false;
    }
}
