package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.openprice.parser.price.ThreeStrings;

public class LiteralMonthDayYear2Test extends DateParserRegularExpressionTestClass {

    public LiteralMonthDayYear2Test() {
        super(new LiteralMonthDayYear2());
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
    public void singleDigitDay() throws Exception{
        final ThreeStrings truth = threeStrings(2015, 1, 9);
        assertEquals(truth, parseToThreeStrings("Jan'9' 1 5  sfdgsd "));
    }

}
