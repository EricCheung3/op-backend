package com.openprice.parser.date.ml;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.openprice.common.StringCommon;

public class DateTokenFeaturesTest {

    @Test
    public void fromStringYearTest(){
        final DateTokenFeatures dF = DateTokenFeatures.fromString(" 2015");
        assertEquals(2015, dF.getIntValue());
        assertEquals(4, dF.getTrimLength());
        assertEquals(StringCommon.EMPTY, dF.getMostSimMonthLiteral());
        assertEquals(0, dF.getNumChars());
        assertEquals(4, dF.getNumDigits());
        assertEquals("2015", dF.getStr());
    }

    @Test
    public void fromStringTwoDigitTest(){
        final DateTokenFeatures dF = DateTokenFeatures.fromString("03");
        assertEquals(3, dF.getIntValue());
        assertEquals(2, dF.getTrimLength());
        assertEquals(StringCommon.EMPTY, dF.getMostSimMonthLiteral());
        assertEquals(0, dF.getNumChars());
        assertEquals(2, dF.getNumDigits());
        assertEquals("03", dF.getStr());
    }

    @Test
    public void fromStringOneDigitTest(){
        final DateTokenFeatures dF = DateTokenFeatures.fromString("3");
        assertEquals(3, dF.getIntValue());
        assertEquals(1, dF.getTrimLength());
        assertEquals(StringCommon.EMPTY, dF.getMostSimMonthLiteral());
        assertEquals(0, dF.getNumChars());
        assertEquals(1, dF.getNumDigits());
        assertEquals("3", dF.getStr());
    }

    @Test
    public void fromStringMonthLiteralTest(){
        final DateTokenFeatures dF = DateTokenFeatures.fromString("Feb");
        assertEquals(-1, dF.getIntValue());
        assertEquals(3, dF.getTrimLength());
        assertEquals("feb", dF.getMostSimMonthLiteral());
        assertEquals(3, dF.getNumChars());
        assertEquals(0, dF.getNumDigits());
        assertEquals("Feb", dF.getStr());
    }

    @Test
    public void fromStringMonthLiteralSimilarTest(){
        final DateTokenFeatures dF = DateTokenFeatures.fromString("Fxb");
        assertEquals(-1, dF.getIntValue());
        assertEquals(3, dF.getTrimLength());
        assertEquals("feb", dF.getMostSimMonthLiteral());
        assertEquals(3, dF.getNumChars());
        assertEquals(0, dF.getNumDigits());
        assertEquals("Fxb", dF.getStr());
    }
}
