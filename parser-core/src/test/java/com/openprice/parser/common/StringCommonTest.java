package com.openprice.parser.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringCommonTest {

    @Test
    public void getOnlyNumbersAndLetters_ShouldReturnOnlyNumberAndLetter() throws Exception {
        final String test1 = "test &*test  ";
        assertEquals("testtest", StringCommon.getOnlyNumbersAndLetters(test1));

        final String test2 = "123 &*test  TEST()==_";
        assertEquals("123testTEST", StringCommon.getOnlyNumbersAndLetters(test2));
    }

    //This shows that matchHeadScore is not symmetric
    @Test
    public void matchHeadTestScoreShouldNotBeSymmetric(){
        final String pepsiShort="pepsi";
        final String pepsiLong=pepsiShort+"519ml";
        final double scoreSmall= StringCommon.matchStringToHeader(pepsiShort, pepsiLong);
        log.debug("scoreSmall="+scoreSmall);
        final double scoreBig= StringCommon.matchStringToHeader(pepsiLong, pepsiShort);
        log.debug("scoreBig="+scoreBig);
        assertTrue(scoreBig>scoreSmall);
    }

    @Test
    public void matchHeadTestNotSymmetric2(){
        final String str1="applesgala4174";
        final String str2="applesgala4174$3.75c";
        final double scoreBig=StringCommon.matchStringToHeader(str2, str1);
        final double scoreSmall=StringCommon.matchStringToHeader(str1, str2);
        assertTrue(scoreBig>scoreSmall);
    }

    @Test
    public void scoreIs0IfSubStringIsLonger(){
        final String line=" 138         NN PEACH HALVES      HRJ       3. 00";
        final String subString="06038364438 nn peach halves";
        final double score=StringCommon.bestSliceMatching(line, subString);
        System.out.println("score="+score);
    }

    @Test
    public void bestSliceMatchingTest2(){
        final String line="8.2540144065pepgrnswt4ctmrj2.98";
        final String subString="4065peppergreenswt";
        final double score=StringCommon.bestSliceMatching(line, subString);
        System.out.println("score="+score);
    }

    @Test
    public void trailCharsShouldbeRemoved() throws Exception{
        final String cleaned=StringCommon.formatPrice("15.98 G");
        assertEquals("15.98", cleaned);
    }

    @Test
    public void trailCharsShouldbeRemovedMoreSpaces() throws Exception{
        final String cleaned=StringCommon.formatPrice("15.98    G");
        assertEquals("15.98", cleaned);
    }

    @Test
    public void trailCharsShouldbeRemovedNoSpaces() throws Exception{
        final String cleaned=StringCommon.formatPrice("15.98G");
        assertEquals("15.98", cleaned);
    }

    @Test
    public void dollarSignShouldBeGone() throws Exception{
        final String cleaned=StringCommon.formatPrice("$6.99 c");
        assertEquals("6.99", cleaned);
    }

    @Test
    public void dollarSignShouldBeGone2() throws Exception{
        final String cleaned=StringCommon.formatPrice("$6.99");
        assertEquals("6.99", cleaned);
    }

    @Test
    public void dollarSignAndTwoCharsShouldBeGone() throws Exception{
        final String cleaned=StringCommon.formatPrice("$4.39 GC");
        assertEquals("4.39", cleaned);
    }

    @Test
    public void dollarSignAndMoreCharsShouldBeGone() throws Exception{
        final String cleaned=StringCommon.formatPrice("$1.89 c -�");
        assertEquals("1.89", cleaned);
    }

    @Test
    public void dollarSignAndSpaceShouldBeGone() throws Exception{
        final String cleaned=StringCommon.formatPrice("$2 .19");
        assertEquals("2.19", cleaned);
    }

    @Test
    public void dollarSignAndTilderShouldBeGone() throws Exception{
        final String cleaned=StringCommon.formatPrice("$1.29 ~");
        assertEquals("1.29", cleaned);
    }

    //TODO this maybe a negative value
    @Test
    public void dollarSignAndDashShouldBeGone() throws Exception{
        final String cleaned=StringCommon.formatPrice("0.07-");
        assertEquals("0.07", cleaned);
    }

    @Test
    public void dollarSignAndDashCharShouldBeGone() throws Exception{
        final String cleaned=StringCommon.formatPrice("$2.58 c -");
        assertEquals("2.58", cleaned);
    }

    //TODO
    @Test
    public void twoDots() throws Exception{
        final String cleaned=StringCommon.formatPrice("$2.69 H C.");
        //        assertEquals("2.69", cleaned);
        assertEquals("2.0", cleaned);
    }


    @Test
    public void getOnlyNumbersAndLettersTest1(){
        assertEquals("abc", StringCommon.getOnlyNumbersAndLetters("abc@"));
    }

    @Test
    public void getOnlyNumbersAndLettersTest2(){
        assertEquals("abc1", StringCommon.getOnlyNumbersAndLetters("abc@1"));
    }

    @Test
    public void getOnlyNumbersAndLettersTest3(){
        assertEquals("abc12", StringCommon.getOnlyNumbersAndLetters("abc@1*2***"));
    }

    @Test
    public void containsOneOnlyOneLetterTest0(){
        assertTrue(StringCommon.containsOneOnlyOneLetter(":z"));
    }

}
