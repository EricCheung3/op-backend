package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import com.openprice.parser.price.ThreeStrings;

public class LiteralMonthDayYear2Test {

    private final LiteralMonthDayYear2 literalMDY2 = new LiteralMonthDayYear2();

    public static ThreeStrings threeStrings(final int y, final int m, final int d) {
        return new ThreeStrings(y+"", m+"",  d+"");
    }
    public static ThreeStrings threeStrings(final LocalDate date){
        return new ThreeStrings(date.getYear()+"", date.getMonthValue()+"", date.getDayOfMonth()+"");
    }

    public ThreeStrings parseToThreeStrings(final String line) throws Exception {
        final LocalDateFeatures features = literalMDY2.parseWithSpaces(line);
        if(features == null)
            throw new Exception("parsed result is null");

        return threeStrings(features.getDate());
    }

    @Test
    public void patternlitermonthdayyeartest1() throws Exception{
        final ThreeStrings truth = threeStrings(2015, 1, 19);
        assertEquals(truth, parseToThreeStrings("Jan 19' 15  sfdgsd "));
    }

    @Test
    public void spaceAsSplitter() throws Exception{
        final ThreeStrings truth = threeStrings(2015, 1, 19);
        assertEquals(truth, parseToThreeStrings("Jan 19 15  sfdgsd "));
    }

    @Test
    public void spaceAsSplitter2() throws Exception{
        final ThreeStrings truth = threeStrings(2015, 1, 19);
        assertEquals(truth, parseToThreeStrings("Jan 19  1 5  sfdgsd "));
    }

    @Test
    public void patternlitermonthdayyeartest2() throws Exception{
        final ThreeStrings truth = threeStrings(2015, 1, 19);
        assertEquals(truth, parseToThreeStrings("Jan'19' 1 5  sfdgsd "));
    }

    @Test
    public void literalMonthDayYearSplitTest1(){
        final List<String> words = LiteralMonthDayYear2.literalMonthDayYearSplit("Feb 9  , 2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void literalMonthDayYearSplitTest2(){
        final List<String> words = LiteralMonthDayYear2.literalMonthDayYearSplit("Feb 9,2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void literalMonthDayYearSplitCommaSpacesIsOkay(){
        final List<String> words = LiteralMonthDayYear2.literalMonthDayYearSplit("Feb 9,    2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void literalMonthDayYearUpperCaseIsOkay(){
        final List<String> words = LiteralMonthDayYear2.literalMonthDayYearSplit("FEB 9,    2015");
        assertEquals(3, words.size());
        assertEquals("FEB", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }


    @Test
    public void literalMonthDayYearSplitCommaSpacesIsOkay2(){
        final List<String> words = LiteralMonthDayYear2.literalMonthDayYearSplit("Feb  9  ,    2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void literalMonthDayYearSplitDashSpacesIsOkay(){
        final List<String> words = LiteralMonthDayYear2.literalMonthDayYearSplit("Feb 9-    2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void literalMonthDayYearSplitUpperIsOkay(){
        final List<String> words = LiteralMonthDayYear2.literalMonthDayYearSplit("FEB 9-    2015");
        assertEquals(3, words.size());
        assertEquals("FEB", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void literalMonthDayYearSplitDashSpacesIsOkay2(){
        final List<String> words = LiteralMonthDayYear2.literalMonthDayYearSplit("Feb 9   -       2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void literalMonthDayYearSplitUnderscoreSpacesIsOkay(){
        final List<String> words = LiteralMonthDayYear2.literalMonthDayYearSplit("Feb 9 _    2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void literalMonthDayYearSplitUnderscoreSpacesIsOkay2(){
        final List<String> words = LiteralMonthDayYear2.literalMonthDayYearSplit("Feb 9_    2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void singleQuoteNonUnicodeIsOkay(){
        final List<String> words = LiteralMonthDayYear2.literalMonthDayYearSplit("OCT.08’2015");
        assertEquals(3, words.size());
        assertEquals("OCT", words.get(0));
        assertEquals("08", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void singleQuoteIsOkay(){
        final List<String> words = LiteralMonthDayYear2.literalMonthDayYearSplit("OCT.08'2015");
        assertEquals(3, words.size());
        assertEquals("OCT", words.get(0));
        assertEquals("08", words.get(1));
        assertEquals("2015", words.get(2));
    }

}
