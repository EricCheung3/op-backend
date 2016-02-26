package com.openprice.parser.price;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.openprice.common.StringCommon;

public class NumberNameNumberSplittingTest {

    @Test
    public void regularNormalInput(){
        final String str = "7040054391580 RIDER INSULATE $179.99 16 ";
        final NumberNameNumberSplitting splitF = new NumberNameNumberSplitting(str);
        String[] words = splitF.getSplits();
        assertEquals("7040054391580 ", words[0]);
        assertEquals("RIDER INSULATE", words[1]);
        assertEquals(" $179.99 16 ", words[2]);
        assertEquals(str, words[0] + words[1] + words[2]);
    }

    @Test
    public void headHasDigits(){
        final String str = "123abc";
        final int[] boundaries = NumberNameNumberSplitting.cuttingBoundaries(str);
        assertEquals(3, boundaries[0]);
        assertEquals(str.length()-1, boundaries[1]);
    }

    @Test
    public void getSplitsHeadHasDigits(){
        final String str = "123abc";
        final NumberNameNumberSplitting splitF = new NumberNameNumberSplitting(str);
        String[] words = splitF.getSplits();
        assertEquals("123", words[0]);
        assertEquals("abc", words[1]);
        assertEquals(StringCommon.EMPTY, words[2]);
    }

    @Test
    public void tailHasDigits(){
        final String str = "abc123";
        final int[] boundaries = NumberNameNumberSplitting.cuttingBoundaries(str);
        assertEquals(0, boundaries[0]);
        assertEquals(2, boundaries[1]);
    }

    @Test
    public void tailHasDigitsGetSplits(){
        final String str = "abc123";
        final NumberNameNumberSplitting splitF = new NumberNameNumberSplitting(str);
        String[] words = splitF.getSplits();
        assertEquals(StringCommon.EMPTY, words[0]);
        assertEquals("abc", words[1]);
        assertEquals("123", words[2]);
    }

    @Test
    public void headTailBothHaveDigits(){
        final String str = "a123 g";
        final int[] boundaries = NumberNameNumberSplitting.cuttingBoundaries(str);
        assertEquals(0, boundaries[0]);
        assertEquals(0, boundaries[1]);
    }

    @Test
    public void CharsDigitsCharsGetSplitsCannotHandleThisPatternWell(){
        final String str = "a123 g";
        final NumberNameNumberSplitting splitF = new NumberNameNumberSplitting(str);
        String[] words = splitF.getSplits();
        assertEquals(StringCommon.EMPTY, words[0]);
        assertEquals("a", words[1]);
        assertEquals(StringCommon.EMPTY, words[2]);
    }

    @Test
    public void noDigits1(){
        final String str = "abc";
        final int[] boundaries = NumberNameNumberSplitting.cuttingBoundaries(str);
        assertEquals(0, boundaries[0]);
        assertEquals(2, boundaries[1]);
    }

    @Test
    public void noDigits1GetSplits(){
        final String str = "abc";
        final NumberNameNumberSplitting splitF = new NumberNameNumberSplitting(str);
        String[] words = splitF.getSplits();
        assertEquals(StringCommon.EMPTY, words[0]);
        assertEquals("abc", words[1]);
        assertEquals(StringCommon.EMPTY, words[2]);
    }

    @Test
    public void noDigits2(){
        final String str = "a";
        final int[] boundaries = NumberNameNumberSplitting.cuttingBoundaries(str);
        assertEquals(0, boundaries[0]);
        assertEquals(0, boundaries[1]);
    }

    @Test
    public void noDigits2GetSplits(){
        final String str = "a";
        final NumberNameNumberSplitting splitF = new NumberNameNumberSplitting(str);
        String[] words = splitF.getSplits();
        assertEquals(StringCommon.EMPTY, words[0]);
        assertEquals("a", words[1]);
        assertEquals(StringCommon.EMPTY, words[2]);
    }

    @Test
    public void noDigitse3(){
        final String str = "";
        final int[] boundaries = NumberNameNumberSplitting.cuttingBoundaries(str);
        assertEquals(-1, boundaries[0]);
        assertEquals(-1, boundaries[1]);
    }

    @Test
    public void noDigits3GetSplits(){
        final String str = "";
        final NumberNameNumberSplitting splitF = new NumberNameNumberSplitting(str);
        String[] words = splitF.getSplits();
        assertEquals(StringCommon.EMPTY, words[0]);
        assertEquals(StringCommon.EMPTY, words[1]);
        assertEquals(StringCommon.EMPTY, words[2]);
    }

    @Test
    public void numDigits1(){
        final NumberNameNumberSplitting splitF = new NumberNameNumberSplitting("abc123");
        final int[] arr = splitF.computeNumDigits();
        assertEquals(0, arr[0]);
        assertEquals(3, arr[1]);
    }

    @Test
    public void numDigits2(){
        final String str = "1abc123";
        final int[] boundaries = NumberNameNumberSplitting.cuttingBoundaries(str);
        assertEquals(1, boundaries[0]);
        assertEquals(3, boundaries[1]);
        final NumberNameNumberSplitting splitF = new NumberNameNumberSplitting(str);
        final int[] arr = splitF.computeNumDigits();
        assertEquals(1, arr[0]);
        assertEquals(3, arr[1]);
    }

    @Test
    public void numDigitsDigitIsCounted(){
        final NumberNameNumberSplitting splitF = new NumberNameNumberSplitting("1abc1.23");
        final int[] arr = splitF.computeNumDigits();
        assertEquals(1, arr[0]);
        assertEquals(4, arr[1]);
    }

    @Test
    public void SplittingFeaturesTest1CharsDigits(){
        final NumberNameNumberSplitting splitF = new NumberNameNumberSplitting("abc123");
        assertEquals(0, splitF.getFirstNonDigitSpace());
        assertEquals(2, splitF.getLastNonDigitSpace());
        assertEquals(0, splitF.getNumHeadingDigits());
        assertEquals(3, splitF.getNumTrailingDigits());
    }

    @Test
    public void SplittingFeaturesTest1DigitsChars(){
        final NumberNameNumberSplitting splitF = new NumberNameNumberSplitting("1233abc");
        assertEquals(4, splitF.getFirstNonDigitSpace());
        assertEquals(6, splitF.getLastNonDigitSpace());
        assertEquals(4, splitF.getNumHeadingDigits());
        assertEquals(0, splitF.getNumTrailingDigits());
    }

    @Test
    public void numberNameTest1() throws Exception{
        final String number="0000";
        final String name="ABC";
        final NumberNameNumberSplitting splitF = new NumberNameNumberSplitting(number+name);
        assertEquals(number, (number+name).substring(0, splitF.getFirstNonDigitSpace()));
        assertEquals(name, (number+name).substring(splitF.getFirstNonDigitSpace()));
    }

    @Test
    public void numberNameTest2() throws Exception{
        final String number="001500";
        final String name="ABC";
        final NumberNameNumberSplitting splitF = new NumberNameNumberSplitting(number+name);
        assertEquals(number, (number+name).substring(0, splitF.getFirstNonDigitSpace()));
        assertEquals(name, (number+name).substring(splitF.getFirstNonDigitSpace()));
    }

    @Test
    public void numberNameTest7() throws Exception{
        final String number="00900099500";
        final String name="ABC";
        final NumberNameNumberSplitting splitF = new NumberNameNumberSplitting(number+name);
        assertEquals(number, (number+name).substring(0, splitF.getFirstNonDigitSpace()));
        assertEquals(name, (number+name).substring(splitF.getFirstNonDigitSpace()));
    }

    @Test
    public void numberNameTest8() throws Exception{
        final String number="00         900099    500";
        final String name="ABC";
        final NumberNameNumberSplitting splitF = new NumberNameNumberSplitting(number+name);
        assertEquals(number, (number+name).substring(0, splitF.getFirstNonDigitSpace()));
        assertEquals(name, (number+name).substring(splitF.getFirstNonDigitSpace()));
    }

    @Test
    public void numberNameTest9() throws Exception{
        final String number="   00         900099    500    ";
        final String name="ABC ";
        final NumberNameNumberSplitting splitF = new NumberNameNumberSplitting(number+name);
        assertEquals(number, (number+name).substring(0, splitF.getFirstNonDigitSpace()));
        assertEquals(name, (number+name).substring(splitF.getFirstNonDigitSpace()));
    }

    @Test
    public void numberNameTest10() throws Exception{
        final String number="   00 0 1   ";
        final String name="ABC ";
        final NumberNameNumberSplitting splitF = new NumberNameNumberSplitting(number+name);
        assertEquals(number, (number+name).substring(0, splitF.getFirstNonDigitSpace()));
        assertEquals(name, (number+name).substring(splitF.getFirstNonDigitSpace()));
    }

    @Test
    public void nameNumberTest7() throws Exception{
        final String number="0 0 0 9 ";
        final String name="ABC";
        final NumberNameNumberSplitting splitF = new NumberNameNumberSplitting(name+number);
        assertEquals(name, (name+number).substring(0, splitF.getLastNonDigitSpace()+1));
        assertEquals(number, (name+number).substring(splitF.getLastNonDigitSpace()+1));
    }

    @Test
    public void nameNumberTest8() throws Exception{
        final String number="0           0 0   9 ";
        final String name="ABC";
        final NumberNameNumberSplitting splitF = new NumberNameNumberSplitting(name+number);
        assertEquals(name, (name+number).substring(0, splitF.getLastNonDigitSpace()+1));
        assertEquals(number, (name+number).substring(splitF.getLastNonDigitSpace()+1));
    }


}
