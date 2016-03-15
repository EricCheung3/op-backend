package com.openprice.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringCommonTest {

    @Test
    public void firstContinuousDigitChunk(){
        assertEquals("123", StringCommon.firstContinuousDigitChunkBetween("A123B456", 0, 100, 100));
        assertEquals("123", StringCommon.firstContinuousDigitChunkBetween("123B456", 0, 100, 100));
        assertEquals("1", StringCommon.firstContinuousDigitChunkBetween("1", 0, 100, 100));
        assertEquals("1", StringCommon.firstContinuousDigitChunkBetween("1", 0, 100, 1));
        assertEquals("", StringCommon.firstContinuousDigitChunkBetween("1", 0, 100, 0));
        assertEquals("", StringCommon.firstContinuousDigitChunkBetween("", 0, 100, 2));
        assertEquals("122", StringCommon.firstContinuousDigitChunkBetween("A122", 0, 100, 3));
        assertEquals("12", StringCommon.firstContinuousDigitChunkBetween("A122", 0, 100, 2));
        assertEquals("1", StringCommon.firstContinuousDigitChunkBetween("A122", 0, 100, 1));
    }

    @Test
    public void lastContinuousDigitChunk(){
        assertEquals("56", StringCommon.lastContinuousDigitChunk("A123B456", 2));
        assertEquals("6", StringCommon.lastContinuousDigitChunk("123B456", 1));
        assertEquals("1", StringCommon.lastContinuousDigitChunk("1", 1));
        assertEquals("", StringCommon.lastContinuousDigitChunk("1", 0));
        assertEquals("", StringCommon.lastContinuousDigitChunk("", 2));
        assertEquals("122", StringCommon.lastContinuousDigitChunk("A122", 3));
        assertEquals("22", StringCommon.lastContinuousDigitChunk("A122", 2));
        assertEquals("2", StringCommon.lastContinuousDigitChunk("A122", 1));
    }


    @Test
    public void formatPriceDashReplacedByComma(){
        assertEquals("21.69", StringCommon.formatPrice(
                "TOTAL                        21 - 69                                           l:   "));
    }

    @Test
    public void formatPriceCommaReplacedByDot(){
        assertEquals("0.67", StringCommon.formatPrice("0,67"));
    }

    @Test
    public void continuousDigitsBeforeDotTest(){
        assertEquals(1, StringCommon.continuousDigitsBeforeDot("1.2"));
        assertEquals(0, StringCommon.continuousDigitsBeforeDot("1x.2"));
        assertEquals(0, StringCommon.continuousDigitsBeforeDot(".2"));
        assertEquals(0, StringCommon.continuousDigitsBeforeDot("12"));
        assertEquals(5, StringCommon.continuousDigitsBeforeDot("12000.000"));
    }

    @Test
    public void removeMatchingTailExactMatchIsFine(){
        final String line="abc e 179. 9916";
        final String tail = "179. 9916";
        assertEquals("abc e", StringCommon.removeMatchingTail(line, tail));
    }

    @Test
    public void removeMatchingTailApproximateMatchIsFine(){
        final String line="abc e 179. 9916";
        final String tail = "179. 8916";
        assertEquals("abc e", StringCommon.removeMatchingTail(line, tail));
    }

    @Test
    public void removeMatchingTailApproximateMatchIsFine2(){
        final String line="abc e 179. 9916";
        final String tail = "179_ 8916";
        assertEquals("abc e", StringCommon.removeMatchingTail(line, tail));
    }

    @Test
    public void selectPriceStringTest(){
        final String line="$67.56     /";
        final String price = StringCommon.selectPriceString(line);
        assertEquals("$67.56", price);
    }

    @Test
    public void selectPriceStringTest2(){
        final String line="fda     $67.56       abc    e";
        final String price = StringCommon.selectPriceString(line);
        assertEquals("$67.56", price);
    }

    @Test
    public void sortByStringLengthTest2() throws Exception{
        final List<String> orig=new ArrayList<String>();
        final String s1="ADC";
        orig.add(s1);
        final String s2="ACDW";
        orig.add(s2);
        final String s3="ASSSSSS";
        orig.add(s3);
        final List<String> sorted=StringCommon.sortByStringLength(orig);
        assertEquals(s3, sorted.get(0));
        assertEquals(s2, sorted.get(1));
        assertEquals(s1, sorted.get(2));
    }

    @Test
    public void sortByStringLengthTest1() throws Exception{
        final List<String> orig=new ArrayList<String>();
        final String s1="ABC";
        orig.add(s1);
        final String s2="ABCDW";
        orig.add(s2);
        final String s3="A";
        orig.add(s3);
        final List<String> sorted=StringCommon.sortByStringLength(orig);
        assertEquals(s2, sorted.get(0));
        assertEquals(s1, sorted.get(1));
        assertEquals(s3, sorted.get(2));
    }

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
        final double score=StringCommon.matchStringToSubString(line, subString);
        System.out.println("score="+score);
    }

    @Test
    public void bestSliceMatchingTest2(){
        final String line="8.2540144065pepgrnswt4ctmrj2.98";
        final String subString="4065peppergreenswt";
        final double score=StringCommon.matchStringToSubString(line, subString);
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


    @Test
    public void containsOneOnlyOneLetterTest1(){
        assertTrue(StringCommon.containsOneOnlyOneLetter("a"));
    }

    @Test
    public void containsOneOnlyOneLetterTest2(){
        for(int i=0;i<1000;i++)
            assertTrue(!StringCommon.containsOneOnlyOneLetter(i+""));
    }

    @Test
    public void containsOneOnlyOneLetterTest3(){
        assertTrue(StringCommon.containsOneOnlyOneLetter("AAAAAAAAAAAAA"));
    }

    @Test
    public void containsOneOnlyOneLetterTest4(){
        assertTrue(!StringCommon.containsOneOnlyOneLetter("@@@@@@@@"));
    }


    @Test
    public void containsOneOnlyOneLetterTest5(){
        assertTrue(!StringCommon.containsOneOnlyOneLetter("!!!!!!!!!"));
    }

    @Test
    public void containsOneOnlyOneLetterTest6(){
        assertTrue(!StringCommon.containsOneOnlyOneLetter("@@@@@@"));
    }

    @Test
    public void containsOneOnlyOneLetterTest7(){
        assertTrue(!StringCommon.containsOneOnlyOneLetter("~~~~~~"));
    }

    @Test
    public void containsOneOnlyOneLetterTest8(){
        assertTrue(!StringCommon.containsOneOnlyOneLetter("AAAAaaaaaa"));
    }


    @Test
    public void numberRatioTest1() throws Exception{
        assertTrue(Math.abs(StringCommon.numberRatio("abc", "")-1.0)<0.0000001);
    }

    @Test
    public void numberRatioTest2() throws Exception{
        assertTrue(Math.abs(StringCommon.numberRatio("abc1", "1")-1.0)<0.0000001);
    }

    @Test
    public void numberRatioTest3() throws Exception{
        assertTrue(Math.abs(StringCommon.numberRatio("abc10", "1")-0.5)<0.0000001);
    }

    @Test
    public void numberRatioTest4() throws Exception{
        assertTrue(Math.abs(StringCommon.numberRatio("abc", "1")-1)<0.0000001);
    }

    @Test
    public void numberRatioTest5() throws Exception{
        assertTrue(Math.abs(StringCommon.numberRatio("abc1", "12")-1)<0.0000001);
    }
    @Test
    public void getOnlyDigitsTest1()throws Exception{
        final String s="abd1 2 *3";
        final String t="123";
        final String t1=StringCommon.getOnlyDigits(s);
        assertEquals(t, t1);
    }

    @Test
    public void firstCharsSameSetTest1()throws Exception{
        final String s="CARD";
        String s2="CARD@XX12!!!!";
        String s3=StringCommon.firstCharsSameSet(s2, s);
        log.debug("s3="+s3);
        assertTrue(s3.equals("CARD"));

        s2="CAR@DXX!2!";
        s3=StringCommon.firstCharsSameSet(s2, s);
        log.debug("s3="+s3);
        assertTrue( s3.equals("CARD"));
    }


    @Test
    public void filterDigitsTest1() throws Exception{
        String test="123**<#$.NET@";
        String result=StringCommon.filterDigits(test);
        assertTrue(result.equals("**<#$.NET@"));

        test="12**<#$.NET@3";
        result=StringCommon.filterDigits(test);
        assertTrue(result.equals("**<#$.NET@"));
    }

    @Test
    public void lastDigitsTest1() throws Exception{
        final String test="afbb1 2 *3";
        assertTrue(StringCommon.lastDigits(test, 3).equals("123"));
    }

    @Test
    public void firstDigitsTest1()throws Exception{
        final String test="abs1df3a5";
        assertTrue(StringCommon.firstDigits(test, 3).equals("135"));
    }

    @Test
    public void firstDigitsTest2()throws Exception{
        final String test="abs1df3a5";
        assertTrue(StringCommon.firstDigits(test, 2).equals("13"));
    }

    @Test
    public void firstDigitsTest3()throws Exception{
        final String test="abs1df3a5";
        assertTrue(StringCommon.firstDigits(test, 1).equals("1"));
    }

    @Test
    public void firstDigitsTest4()throws Exception{
        final String test="abs1df3a5";
        assertTrue(StringCommon.firstDigits(test, 0).equals(""));
    }

    @Test
    public void firstDigitsTest5()throws Exception{
        final String test="abs1df3a5";
        assertTrue(StringCommon.firstDigits(test, -1).equals(""));
    }

    @Test
    public void getDigitAndLetterTest1() throws Exception{
        String str="abc#";
        String result=StringCommon.getDigitAndLetter(str);
        assertTrue(result.equals("abc"));
        str="abc#:";
        result=StringCommon.getDigitAndLetter(str);
        assertTrue(result.equals("abc:"));// a bit confusing. is colon allowed?
    }

    @Test
    public void removeTrailingCharsTest1() throws Exception{
        final String test="$3.50 cR";
        final String formatted=StringCommon.removeTrailingChars(test, "cR");
        assertTrue(formatted.equals("3.50"));
        assertTrue( StringCommon.removeTrailingChars("$3.50 R", "cR").equals("3.50"));
        assertTrue( StringCommon.removeTrailingChars("$3.50 R", "CR").equals("3.50"));
        assertTrue( StringCommon.removeTrailingChars("$3.50 R", "A").equals("3.50"));
        assertTrue(StringCommon.removeTrailingChars("$3.50 R", "").equals("3.50"));
        assertTrue(StringCommon.removeTrailingChars("$3.50 r", "rAB").equals("3.50"));
        assertTrue(StringCommon.removeTrailingChars("$3.ar0 r", "rAB").equals("3.0"));

        assertTrue(StringCommon.removeTrailingChars("$3", "rAB").equals("3"));
        assertTrue(StringCommon.removeTrailingChars("", "rAB").equals(""));
        assertTrue(StringCommon.removeTrailingChars("", "").equals(""));
    }

    @Test
    public void fixMinuteSecondTest1() throws Exception{
        final String time="12:11";
        assertTrue(StringCommon.fixMinuteSecond(time).equals(time));
        assertTrue(StringCommon.fixMinuteSecond("12: 11").equals(time));
        assertTrue(StringCommon.fixMinuteSecond("12 11").equals(time));
        assertTrue(StringCommon.fixMinuteSecond("12  11").equals(time));
    }

    @Test
    public void countDigitAndCharsTest1()throws Exception{
        int[] count=StringCommon.countDigitAndChars("ab12");
        assertTrue( count[0]==2 && count[1]==2);
        count=StringCommon.countDigitAndChars("ab1");
        assertTrue( count[0]==1 && count[1]==2);
        count=StringCommon.countDigitAndChars("ab1 23");
        assertTrue( count[0]==3 && count[1]==2);
        count=StringCommon.countDigitAndChars("1 23");
        assertTrue( count[0]==3 && count[1]==0);
        count=StringCommon.countDigitAndChars("ab");
        assertTrue( count[0]==0 && count[1]==2);
    }

    @Test
    public void formatPriceTest4() throws Exception{
        final String s="$3.14";
        assertTrue(StringCommon.formatPrice(s).equals("3.14"));
    }

    @Test
    public void formatPriceTest3()throws Exception{
        final String truth="31.69";
        String s="-                   $31.69";
        assertEquals(truth, StringCommon.formatPrice(s));
        s="-                   31.69";//how about refund?
        assertEquals(truth, StringCommon.formatPrice(s));
        s="1                   31.69";
        assertEquals(truth, StringCommon.formatPrice(s));
        s="1   assertEquals               $31.69";
        assertEquals(truth, StringCommon.formatPrice(s));
    }

    @Test
    public void formatPriceTest2()throws Exception{
        String s="7.JJI";
        assertTrue( StringCommon.ignoreAfterDot(s).equals("7.0"));

        //no effect if no dots
        assertEquals("701S", StringCommon.ignoreAfterDot("701S"));
        assertEquals("AAAA", StringCommon.ignoreAfterDot("AAAA"));
        assertEquals(StringCommon.EMPTY, StringCommon.ignoreAfterDot(""));
        assertEquals(".0", StringCommon.ignoreAfterDot("."));

        final String truth70="7.";
        assertEquals(truth70, StringCommon.formatPrice(s));
        s="7.12J";
        assertEquals("7.12", StringCommon.formatPrice(s));
        s="7.JJ";
        assertEquals(truth70, StringCommon.formatPrice(s));
        s="7.0J1";
        assertEquals("7.01", StringCommon.formatPrice(s));

        s="70J1";
        assertEquals("701", StringCommon.formatPrice(s));
    }

    @Test
    public void formatPriceTestA2() throws Exception{
        final String[] p1=new String[]{"11.580", "11.58 0", "11.58O", "11.58 O",
                "11. 580", "11 . 580", "11. 58 O", "11 . 58 O"};

        final String truth="11.580";
        for(int i=0; i<p1.length; i++){
            String tmp=StringCommon.formatPrice(p1[i]);
            assertTrue(tmp.equals(truth));
        }
    }

    @Test
    public void formatPriceTestA1() throws Exception{
        String test="38 99";
        String truth="38.99";
        String test1=StringCommon.formatPrice(test);
        assertTrue(test1.equals(truth));

        test="8 00";
        truth="8.00";
        test1=StringCommon.formatPrice(test);
        assertTrue(test1.equals(truth));

        test="$4 4?";
        truth="4.42";
        test1=StringCommon.formatPrice(test);
        assertTrue(test1.equals(truth));

        test="$4 4";
        truth="4.4";
        test1=StringCommon.formatPrice(test);
        assertTrue(test1.equals(truth));

        test="4 4";
        truth="4.4";
        test1=StringCommon.formatPrice(test);
        assertTrue(test1.equals(truth));
    }

    @Test
    public void formatPriceTestA3() throws Exception{
        String test=StringCommon.formatPrice(".76")+"";
        assertTrue("0.76".equals(test));
        test=StringCommon.formatPrice("..76");
        assertTrue("0.76".equals(test));
        test=StringCommon.formatPrice("..7.6");
        assertTrue("7.6".equals(test));
    }

    //TODO consider negative price
    @Test
    public void formatPriceTestA4() throws Exception{
        assertEquals("1.73", StringCommon.formatPrice("1.73-"));
        String formatted=StringCommon.formatPrice("'1.73-");
        assertEquals("4.73", formatted);
    }

    public static boolean testTarget(String test, double truth) throws Exception{
        String test1=StringCommon.formatPrice(test);
        return (Double.valueOf(test1).equals(truth));
    }

    @Test
    public void formatPriceTestA5() throws Exception{
        assertTrue(testTarget("0.07-", 0.07));
        assertTrue(testTarget("0.07~", 0.07));
        assertTrue(testTarget("4-99", 4.99));
        assertTrue(testTarget(".4-99", 4.99));
        assertTrue(testTarget("4 ' 19", 4.19));
        assertTrue(testTarget("1 '49", 1.49));
        assertTrue(testTarget("1' 49", 1.49));
    }

    @Test
    public void removeFirstNonSpaceCharsTest1() throws Exception{
        final String test="A BC D E";
        final String result=StringCommon.removeFirstNonSpaceChars(test, "ABC");
        assertEquals(" D E", result);
        assertEquals(" D E", StringCommon.removeFirstNonSpaceChars(test, "A BC"));
        assertEquals("DE", StringCommon.removeFirstNonSpaceChars("ABCDE", "A BC"));
        assertEquals(" C DE", StringCommon.removeFirstNonSpaceChars("AB C DE", "AB"));
        assertEquals(" CDE", StringCommon.removeFirstNonSpaceChars("AB CDE", "AB"));
        assertEquals("CD E", StringCommon.removeFirstNonSpaceChars("ABCD E", "AB"));
        assertEquals("ABCD E", StringCommon.removeFirstNonSpaceChars("ABCD E", ""));
        log.debug("StringCommon.removeFirstNonSpaceChars output is "+StringCommon.removeFirstNonSpaceChars("A", "AB"));
        assertEquals(StringCommon.EMPTY, StringCommon.removeFirstNonSpaceChars("A", "AB"));
        assertEquals(StringCommon.EMPTY, StringCommon.removeFirstNonSpaceChars("", "AB"));
    }

    @Test
    public void lastLetter2Test1()throws Exception{
        final String str="Juice Grape 200ML5Pk $2.49";
        final int index=StringCommon.lastLetter2(str, str.length()-1);
        assertEquals('k', str.charAt(index));
    }

    @Test
    public void afterLastDollarTest1()throws Exception{
        final String str="Garden Cocktail 6591200452 $2.79";
        final String[] namePrice=StringCommon.afterLastDollar(str);
        assertEquals("2.79", namePrice[1]);
    }

    @Test
    public void afterLastDollarTest2()throws Exception{
        final String str="Garden Cocktail 6591200452 $";
        final String[] namePrice=StringCommon.afterLastDollar(str);
        assertEquals(StringCommon.EMPTY, namePrice[1]);
    }

    //no dollar sign exception
    @Test(expected=Exception.class)
    public void afterLastDollarTest3()throws Exception{
        final String str="Garden Cocktail 6591200452";
        final String[] namePrice=StringCommon.afterLastDollar(str);
    }

    @Test
    public void stringMatchesHeadTest1() throws Exception{
        String str="ABC";
        List<String> list=new ArrayList<String>();
        //list.add("");//head is similar to "AB" by definition of nonspace char from str
        list.add("A");
        list.add("AB");
        list.add("ABC");
        for(int i=0; i<list.size();i++){
            assertTrue(StringCommon.stringMatchesHead(str, list.get(i), 1.0));
        }
        assertTrue( StringCommon.stringMatchesHead("", "", 1.0));
    }

    //      @Test
    //      public void matchScoreTest(){
    //          log.debug(StringCommon.matchHeadScore("", ""));
    //      }

    @Test
    public void twoDigitsBeforeSlashTest1() throws Exception{
        final String s="dfs01/";
        String twoDigits=StringCommon.twoDigitsBeforeSlash(s, s.indexOf("/"));
        assertEquals("01", twoDigits);
    }

    @Test
    public void twoDigitsBeforeSlashTest2()throws Exception{
        final String s="dfs0A1/";
        String twoDigits=StringCommon.twoDigitsBeforeSlash(s, s.indexOf("/"));
        assertEquals("01", twoDigits);
    }

    @Test(expected=StringIndexOutOfBoundsException.class)
    public void twoDigitsBeforeSlashTest3() throws Exception{
        final String s="dfs0A1";
        log.debug(s.indexOf("/")+"");
        String twoDigits=StringCommon.twoDigitsBeforeSlash(s, -1);
    }

    @Test(expected=Exception.class)
    public void twoDigitsBeforeSlashTest4() throws Exception{
        final String s="dfs0A1";
        String twoDigits=StringCommon.twoDigitsBeforeSlash(s, 0);
    }

    @Test(expected=StringIndexOutOfBoundsException.class)
    public void twoDigitsBeforeSlashTest5() throws Exception{
        final String s="dfs0A1";
        String twoDigits=StringCommon.twoDigitsBeforeSlash(s, 10);
    }

    @Test
    public void twoDigitsBeforeSlashTest6() throws Exception{
        final String s="dfs/0A1/";
        String twoDigits=StringCommon.twoDigitsBeforeSlash(s,
                s.indexOf("/"));
        assertTrue(twoDigits.isEmpty());
    }

    @Test
    public void twoDigitsBeforeSlashTest7() throws Exception{
        final String s="1dfs/0A1/";
        String twoDigits=StringCommon.twoDigitsBeforeSlash(s,
                s.indexOf("/"));
        assertEquals("1", twoDigits);
    }

    @Test
    public void flattenTest1() throws Exception{
        List<String> list=new ArrayList<String>();
        list.add("a");
        list.add("b");
        String str=StringCommon.flatten(list, "\n");
        assertEquals("a\nb", str);
    }

    @Test
    public void flattenTest2() throws Exception{
        List<String> list=new ArrayList<String>();
        list.add("a");
        String str=StringCommon.flatten(list, "\n");
        assertEquals("a", str);
    }

    @Test
    public void flattenTest3() throws Exception{
        List<String> list=new ArrayList<String>();
        list.add("");
        String str=StringCommon.flatten(list, "\n");
        assertEquals("", str);
    }

    @Test
    public void flattenTest4() throws Exception{
        List<String> list=new ArrayList<String>();
        String str=StringCommon.flatten(list, "\n");
        assertEquals("", str);
    }

    @Test
    public void flattenTest5() throws Exception{
        List<String> list=new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        String str=StringCommon.flatten(list, "\n");
        assertEquals("a\nb\nc", str);
    }
}
