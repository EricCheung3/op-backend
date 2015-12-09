package com.openprice.parser.common;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DateParserUtilsTest {

    @Test
    public void testDate1YearFirstIsOkay(){
        final List<String> lines=new ArrayList<String>();
        lines.add("DATE            TIME            AMOUNT");
        lines.add("2015/01/18      17:26:22        $        14.48");
        lines.add("APPROVED");
        lines.add("No Signature Required");
        assertEquals("2015/01/18", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testDate1(){
        final List<String> lines=new ArrayList<String>();
        lines.add("DATE            TIME            AMOUNT");
        lines.add("01/18/2015      17:26:22        $        14.48");
        lines.add("APPROVED");
        lines.add("No Signature Required");
        assertEquals("01/18/2015", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testDate2(){
        final List<String> lines=new ArrayList<String>();
        lines.add("DATE 03/ 06/ 2015                TIME 14 :49:48");
        lines.add("AUTH # 00509Z                    REF # 00000062");
        assertEquals("03/06/2015", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testDateFile1(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/2015_02_09_11_34_51.jpg.hengshuai.txt"));
        assertEquals("2/1/2015", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testDateFile2(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/2015_02_09_11_34_51_variantionDate.jpg.hengshuai2.txt"));
        assertEquals("02/01/2015", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testDateFileSafeway1(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/2015_02_27_20_04_24.jpg.dongcui.txt"));
        assertEquals("02/27/2015", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testDateFileRCSS1(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/2015_04_04_21_25_02.jpg.jingwang.txt"));
        assertEquals("02/21/2015", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void pruneDateStringTest1YearFirstIsOkay(){
        final String dateString="2009/11/25";
        assertEquals(dateString, DateParserUtils.pruneDateString(dateString+" abc de ef"));
    }

    @Test
    public void pruneDateStringTest1YearFirstIsOkay2(){
        final String dateString="2009/1/25";
        assertEquals(dateString, DateParserUtils.pruneDateString(dateString+" abc de ef"));
    }

    @Test
    public void pruneDateStringTest1YearFirstIsOkay3(){
        final String dateString="2009/1/2";
        assertEquals(dateString, DateParserUtils.pruneDateString(dateString+" abc de ef"));
    }

    @Test
    public void pruneDateStringTest1CorrectStringIsAtHead(){
        final String dateString="11/25/2009";
        assertEquals(dateString, DateParserUtils.pruneDateString(dateString+" abc de ef"));
    }

    @Test
    public void pruneDateStringTest1CorrectStringIsAtTail(){
        final String dateString="11/25/2009";
        assertEquals(dateString, DateParserUtils.pruneDateString(" abc de ef"+dateString));
    }

    @Test
    public void pruneDateStringTest1CorrectStringHasSpacesIsOkay(){
        final String dateString="11/25/2009";
        assertEquals(dateString, DateParserUtils.pruneDateString("11/25/2 009"+" abc de ef"));
    }

    @Test
    public void pruneDateStringTest1CorrectStringHasSpacesIsOkay2(){
        final String dateString="11/25/2009";
        assertEquals(dateString, DateParserUtils.pruneDateString("11/ 2 5/2 009"+" abc de ef"));
    }

    @Test
    public void pruneDateStringTest1FromReceipt1(){
        final String dateString="01/11/2015";
        assertEquals(dateString, DateParserUtils.pruneDateString("01/11/2015  15:09:10       $      87.40"));
    }

    @Test
    public void pruneDateStringTest1FromReceipt2(){
        final String dateString="03/06/2015";
        assertEquals(dateString, DateParserUtils.pruneDateString("DATE 03/06/2015                  TIME 14:49:11"));
    }

    @Test
    public void pruneDateStringTest1FromReceipt3(){
        final String dateString="01/18/2015";
        assertEquals(dateString, DateParserUtils.pruneDateString("01/18/2015      17:26:22        $        14.48"));
    }

    @Test
    public void pruneDateStringTest1DotAsSeparatorIsOkay(){
        final String dateString="01.18.2015";
        assertEquals(dateString, DateParserUtils.pruneDateString("01.18.2015      17:26:22        $        14.48"));
    }

    @Test
    public void pruneDateStringTest1SingleDigitDateIsOkay(){
        final String dateString="1.18.2015";
        assertEquals(dateString, DateParserUtils.pruneDateString("1.18.2015      17:26:22        $        14.48"));
    }

    @Test
    public void pruneDateStringTest1SingleDigitDateMonthIsOkay(){
        final String dateString="2/6/2015";
        assertEquals(dateString, DateParserUtils.pruneDateString("2/6/2015      17:26:22        $        14.48"));
    }

    @Test
    public void pruneDateStringTest1DashAsSeparatorIsOkay(){
        final String dateString="01-18-2015";
        assertEquals(dateString, DateParserUtils.pruneDateString("01-18-2015      17:26:22        $        14.48"));
    }

    @Test
    public void pruneDateStringTest1TwoDigitYearIsOkay(){
        final String dateString="02/27/15";
        assertEquals(dateString, DateParserUtils.pruneDateString("Term Tran       Store         Oper               02/ 27/ 15"));
    }

    @Test
    public void pruneDateStringTest1TwoDigitYearIsOkayDash(){
        final String dateString="02-27-15";
        assertEquals(dateString, DateParserUtils.pruneDateString("Term Tran       Store         Oper               02- 27- 15"));
    }

    @Test
    public void pruneDateStringTest1TwoDigitYearIsOkayDot(){
        final String dateString="02.27.15";
        assertEquals(dateString, DateParserUtils.pruneDateString("Term Tran       Store         Oper               02.27. 15"));
    }
}
