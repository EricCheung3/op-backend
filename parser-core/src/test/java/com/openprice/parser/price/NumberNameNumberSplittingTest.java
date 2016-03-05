package com.openprice.parser.price;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.openprice.common.StringCommon;
import com.openprice.parser.ml.structure.NumberNamePriceSplit;

public class NumberNameNumberSplittingTest {

    @Test
    public void regularNormalInput(){
        final String str = "7040054391580 RIDER INSULATE $179.99 16 ";
        final NumberNamePriceSplit splitF = new NumberNamePriceSplit(str);
        assertEquals("7040054391580 ", splitF.getParsedNumber());
        assertEquals("RIDER INSULATE", splitF.getParsedName());
        assertEquals(" $179.99 16 ", splitF.getParsedPrice());
        assertEquals(str, splitF.getParsedNumber() + splitF.getParsedName() + splitF.getParsedPrice());
    }



    @Test
    public void getSplitsHeadHasDigits(){
        final String str = "123abc";
        final NumberNamePriceSplit splitF = new NumberNamePriceSplit(str);

        assertEquals("123", splitF.getParsedNumber());
        assertEquals("abc", splitF.getParsedName());
        assertEquals(StringCommon.EMPTY, splitF.getParsedPrice());
    }


    @Test
    public void tailHasDigitsGetSplits(){
        final String str = "abc123";
        final NumberNamePriceSplit splitF = new NumberNamePriceSplit(str);

        assertEquals(StringCommon.EMPTY, splitF.getParsedNumber());
        assertEquals("abc", splitF.getParsedName());
        assertEquals("123", splitF.getParsedPrice());
    }


    @Test
    public void CharsDigitsCharsGetSplitsCannotHandleThisPatternWell(){
        final String str = "a123 g";
        final NumberNamePriceSplit splitF = new NumberNamePriceSplit(str);

        assertEquals(StringCommon.EMPTY, splitF.getParsedNumber());
        assertEquals("a", splitF.getParsedName());
        assertEquals(StringCommon.EMPTY, splitF.getParsedPrice());
    }


    @Test
    public void noDigits1GetSplits(){
        final String str = "abc";
        final NumberNamePriceSplit splitF = new NumberNamePriceSplit(str);

        assertEquals(StringCommon.EMPTY, splitF.getParsedNumber());
        assertEquals("abc", splitF.getParsedName());
        assertEquals(StringCommon.EMPTY, splitF.getParsedPrice());
    }



    @Test
    public void noDigits3GetSplits(){
        final String str = "";
        final NumberNamePriceSplit splitF = new NumberNamePriceSplit(str);
        assertEquals(StringCommon.EMPTY, splitF.getParsedNumber());
        assertEquals(StringCommon.EMPTY, splitF.getParsedName());
        assertEquals(StringCommon.EMPTY, splitF.getParsedPrice());
    }




}
