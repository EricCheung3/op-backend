package com.openprice.parser.price;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SplittingFeaturesTest {

    @Test
    public void numDigits1(){
        final SplittingFeatures splitF = new SplittingFeatures("abc123");
        final int[] arr = splitF.computeNumDigits();
        assertEquals(0, arr[0]);
        assertEquals(3, arr[1]);
    }

    @Test
    public void numDigits2(){
        final SplittingFeatures splitF = new SplittingFeatures("1abc123");
        final int[] arr = splitF.computeNumDigits();
        assertEquals(1, arr[0]);
        assertEquals(3, arr[1]);
    }

    @Test
    public void numDigitsDigitIsCounted(){
        final SplittingFeatures splitF = new SplittingFeatures("1abc1.23");
        final int[] arr = splitF.computeNumDigits();
        assertEquals(1, arr[0]);
        assertEquals(4, arr[1]);
    }

    @Test
    public void SplittingFeaturesTest1CharsDigits(){
        final SplittingFeatures splitF = new SplittingFeatures("abc123");
        assertEquals(0, splitF.getFirstNonDigitSpace());
        assertEquals(2, splitF.getLastNonDigitSpace());
        assertEquals(0, splitF.getNumHeadingDigits());
        assertEquals(3, splitF.getNumTrailingDigits());
    }

    @Test
    public void SplittingFeaturesTest1DigitsChars(){
        final SplittingFeatures splitF = new SplittingFeatures("1233abc");
        assertEquals(4, splitF.getFirstNonDigitSpace());
        assertEquals(6, splitF.getLastNonDigitSpace());
        assertEquals(4, splitF.getNumHeadingDigits());
        assertEquals(0, splitF.getNumTrailingDigits());
    }
}
