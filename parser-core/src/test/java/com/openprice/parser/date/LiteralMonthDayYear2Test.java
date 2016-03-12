package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

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

    public ThreeStrings parseToThreeStrings(final String line){
        return threeStrings(literalMDY2.parseWithSpaces(line).getDate());
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


}
