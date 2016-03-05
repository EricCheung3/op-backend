package com.openprice.parser.structure;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.openprice.parser.ml.item.NumberNamePriceFeatures;
import com.openprice.parser.ml.structure.NumberNamePrice;

public class NumberNamePriceTest {

    @Test
    public void headHasDigits(){
        final String str = "123abc";
        final int[] boundaries = NumberNamePrice.cuttingBoundaries(str);
        assertEquals(3, boundaries[0]);
        assertEquals(str.length()-1, boundaries[1]);
    }

    @Test
    public void noDigits2(){
        final String str = "a";
        final int[] boundaries = NumberNamePrice.cuttingBoundaries(str);
        assertEquals(0, boundaries[0]);
        assertEquals(0, boundaries[1]);
    }


    @Test
    public void tailHasDigits(){
        final String str = "abc123";
        final int[] boundaries = NumberNamePrice.cuttingBoundaries(str);
        assertEquals(0, boundaries[0]);
        assertEquals(2, boundaries[1]);
    }

    @Test
    public void headTailBothHaveDigits(){
        final String str = "a123 g";
        final int[] boundaries = NumberNamePrice.cuttingBoundaries(str);
        assertEquals(0, boundaries[0]);
        assertEquals(0, boundaries[1]);
    }
    @Test
    public void noDigits1(){
        final String str = "abc";
        final int[] boundaries = NumberNamePrice.cuttingBoundaries(str);
        assertEquals(0, boundaries[0]);
        assertEquals(2, boundaries[1]);
    }
    @Test
    public void noDigitse3(){
        final String str = "";
        final int[] boundaries = NumberNamePrice.cuttingBoundaries(str);
        assertEquals(-1, boundaries[0]);
        assertEquals(-1, boundaries[1]);
    }

    public static int[] computeHeadingTrailingDigits(final String str){
        final NumberNamePrice numberNamePrice = new NumberNamePrice(str);
        return NumberNamePriceFeatures.computeHeadingTrailingDigits(str, numberNamePrice.boundary1(), numberNamePrice.boundary2());
    }

    @Test
    public void numDigits1(){
        final String str = "abc123";
        final NumberNamePrice splitF = new NumberNamePrice(str);
        final int[] arr = computeHeadingTrailingDigits(str);
        assertEquals(0, arr[0]);
        assertEquals(3, arr[1]);
    }

    @Test
    public void numDigits2(){
        final String str = "1abc123";
        final int[] boundaries = NumberNamePrice.cuttingBoundaries(str);
        assertEquals(1, boundaries[0]);
        assertEquals(3, boundaries[1]);
        final int[] arr = computeHeadingTrailingDigits(str);
        assertEquals(1, arr[0]);
        assertEquals(3, arr[1]);
    }

    @Test
    public void numDigitsDigitIsCounted(){
        final String str = "1abc1.23";
        final int[] arr = computeHeadingTrailingDigits(str);
        assertEquals(1, arr[0]);
        assertEquals(4, arr[1]);
    }


}
