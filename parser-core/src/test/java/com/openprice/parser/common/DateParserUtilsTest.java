package com.openprice.parser.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateParserUtilsTest {

    @Test
    public void patternMDY2Test1() throws Exception{
        assertTrue(DateParserUtils.getPatternMonthDayYear2().matcher("5/12/14").matches());
    }

    @Test
    public void patternMDY2Test2() throws Exception{
        assertTrue(DateParserUtils.getPatternMonthDayYear2().matcher("05/12/14").matches());
    }

    @Test
    public void patternMDY4Test1()throws Exception{
        assertTrue(DateParserUtils.getPatternMonthDayYear4().matcher("05/12/2014").matches());
    }

    @Test
    public void patternMDY4Test2()throws Exception{
        assertTrue(DateParserUtils.getPatternMonthDayYear4().matcher("5/12/2014").matches());
    }

    @Test
    public void patternY4MD1Test1()throws Exception{
        assertTrue(DateParserUtils.getPatternYear4MonthDay1().matcher("2014/05/1").matches());
    }

    @Test
    public void patternY4MD1Test2()throws Exception{
        assertTrue(DateParserUtils.getPatternYear4MonthDay1().matcher("2014/5/1").matches());
    }

    @Test
    public void patternY4MD2Test1()throws Exception{
        assertTrue(DateParserUtils.getPatternYear4MonthDay2().matcher("2014/5/01").matches());
    }

    @Test
    public void patternY4MD2Test2()throws Exception{
        assertTrue(DateParserUtils.getPatternYear4MonthDay2().matcher("2014/05/01").matches());
    }

    @Test
    public void formatDateStringTest()throws Exception{
        final Date date=DateParserUtils.toDate("2013/01/31");
        assertEquals("2013/1/31", DateParserUtils.formatDateString(date));
    }

    @Test
    public void toDateTest1TwoDigitYearFormatIsNowSupported() throws Exception{
        final Date date=DateParserUtils.toDate("1/1/13");
        assertEquals(2013, DateParserUtils.getYearMonthDay(date)[0]);
        assertEquals(1, DateParserUtils.getYearMonthDay(date)[1]);
        assertEquals(1, DateParserUtils.getYearMonthDay(date)[2]);
    }

    @Test
    public void toDateTest1SingleDigitDayIsOkayYearAtEnd() throws Exception{
        final Date date=DateParserUtils.toDate("1/1/2013");
        log.debug("date"+date);
        final int[] yMD=DateParserUtils.getYearMonthDay(date);
        assertEquals(2013, yMD[0]);
        assertEquals(1, yMD[1]);
        assertEquals(1, yMD[2]);
    }

    @Test
    public void toDateTest1SingleDigitDayIsOkay() throws Exception{
        final Date date=DateParserUtils.toDate("2013/1/1");
        log.debug("date"+date);
        final int[] yMD=DateParserUtils.getYearMonthDay(date);
        assertEquals(2013, yMD[0]);
        assertEquals(1, yMD[1]);
        assertEquals(1, yMD[2]);
    }

    @Test
    public void toDateTest1SingleDigitMonthIsOkay() throws Exception{
        final Date date=DateParserUtils.toDate("2013/1/31");
        log.debug("date"+date);
        final int[] yMD=DateParserUtils.getYearMonthDay(date);
        assertEquals(2013, yMD[0]);
        assertEquals(1, yMD[1]);
        assertEquals(31, yMD[2]);
    }

    @Test
    public void toDateTest1() throws Exception{
        final Date date=DateParserUtils.toDate("2013/01/31");
        log.debug("date"+date);
        final int[] yMD=DateParserUtils.getYearMonthDay(date);
        assertEquals(2013, yMD[0]);
        assertEquals(1, yMD[1]);
        assertEquals(31, yMD[2]);
    }

    @Test
    public void toDateTest1DotIsOkay() throws Exception{
        final Date date=DateParserUtils.toDate("2013.01.31");
        log.debug("date"+date);
        final int[] yMD=DateParserUtils.getYearMonthDay(date);
        assertEquals(2013, yMD[0]);
        assertEquals(1, yMD[1]);
        assertEquals(31, yMD[2]);
    }

    @Test
    public void toDateTest1DashIsOkay() throws Exception{
        final Date date=DateParserUtils.toDate("2013-01-31");
        log.debug("date"+date);
        final int[] yMD=DateParserUtils.getYearMonthDay(date);
        assertEquals(2013, yMD[0]);
        assertEquals(1, yMD[1]);
        assertEquals(31, yMD[2]);
    }

    @Test
    public void toDateTest1YearInTheEndIsOkay() throws Exception{
        final Date date=DateParserUtils.toDate("01-31-2013");
        log.debug("date"+date);
        final int[] yMD=DateParserUtils.getYearMonthDay(date);
        assertEquals(2013, yMD[0]);
        assertEquals(1, yMD[1]);
        assertEquals(31, yMD[2]);
    }

    @Test
    public void toDateTest1YearInTheEndIsOkayDot() throws Exception{
        final Date date=DateParserUtils.toDate("01.31.2013");
        log.debug("date"+date);
        final int[] yMD=DateParserUtils.getYearMonthDay(date);
        assertEquals(2013, yMD[0]);
        assertEquals(1, yMD[1]);
        assertEquals(31, yMD[2]);
    }


    @Test(expected=Exception.class)
    public void toDateTestSplitterIsNotAccepted() throws Exception{
        final Date date=DateParserUtils.toDate("01)31)2013");
    }

    @Test
    public void toDateTest1YearInTheEndIsOkaySlash() throws Exception{
        final Date date=DateParserUtils.toDate("01/31/2013");
        log.debug("date"+date);
        final int[] yMD=DateParserUtils.getYearMonthDay(date);
        assertEquals(2013, yMD[0]);
        assertEquals(1, yMD[1]);
        assertEquals(31, yMD[2]);
    }

    @Test
    public void testDate1YearFirstIsOkay() throws Exception{
        final List<String> lines=new ArrayList<String>();
        lines.add("DATE            TIME            AMOUNT");
        lines.add("2015/01/18      17:26:22        $        14.48");
        lines.add("APPROVED");
        lines.add("No Signature Required");
        assertEquals("2015/1/18", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testDate1()throws Exception{
        final List<String> lines=new ArrayList<String>();
        lines.add("DATE            TIME            AMOUNT");
        lines.add("01/18/2015      17:26:22        $        14.48");
        lines.add("APPROVED");
        lines.add("No Signature Required");
        assertEquals("2015/1/18", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testDate2()throws Exception{
        final List<String> lines=new ArrayList<String>();
        lines.add("DATE 03/ 06/ 2015                TIME 14 :49:48");
        lines.add("AUTH # 00509Z                    REF # 00000062");
        assertEquals("2015/3/6", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testDateFile1()throws Exception{
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/2015_02_09_11_34_51.jpg.hengshuai.txt"));
        assertEquals("2015/2/1", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testDateFile2()throws Exception{
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/2015_02_09_11_34_51_variantionDate.jpg.hengshuai2.txt"));
        assertEquals("2015/2/1", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testDateFileSafeway1()throws Exception{
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/2015_02_27_20_04_24.jpg.dongcui.txt"));
        assertEquals("2015/2/27", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testDateFileRCSS1()throws Exception{
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/2015_04_04_21_25_02.jpg.jingwang.txt"));
        assertEquals("2015/2/21", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testDateFileRCSS2()throws Exception{
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/2015_04_04_21_25_02.jpg.jingwang.txt"));
        assertEquals("2015/2/21", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testSw1(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/Safeway/2014_12_06_22_36_54.jpg.hengshuai.txt"));
        assertEquals("2014/5/12", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testSw2(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/Safeway/2014_12_06_22_56_07.jpg.hengshuai.txt"));
        assertEquals("2014/2/1", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testSw3(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/Safeway/2014_12_06_23_12_59_small.jpg.hengshuai.txt"));
        assertEquals("2013/11/17", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testSw4(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/Safeway/2014_12_07_00_24_15.jpg.hengshuai.txt"));
        assertEquals("2013/11/29", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testSw5(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/Safeway/2014_12_06_23_12_59.jpg.hengshuai.txt"));
        assertEquals("2013/11/17", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testSw6(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/Safeway/2014_12_16_18_12_17.jpg.hengshuai.txt"));
        assertEquals("2014/2/1", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testSw7(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/Safeway/2015_02_09_11_33_44.jpg.hengshuai.txt"));
        assertEquals("2015/2/7", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testRCSS1(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/RCSS/2014_12_06_22_13_28.jpg.hengshuai.txt"));
        assertEquals("2014/11/8", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testRCSS2(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/RCSS/2014_12_06_23_05_23.jpg.hengshuai.txt"));
        assertEquals("2014/4/16", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testRCSS3(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/RCSS/2014_12_06_23_10_49.jpg.hengshuai.txt"));
        assertEquals("2013/7/21", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testRCSS4(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/RCSS/2014_12_06_23_21_04.jpg.hengshuai.txt"));
        assertEquals("2014/3/1", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testRCSS5HasNoDate(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/RCSS/2014_12_06_23_33_53.jpg.hengshuai.txt"));
        assertEquals("", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testRCSS6(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/RCSS/2014_12_06_23_37_10.jpg.hengshuai.txt"));
        assertEquals("2014/5/9", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testRCSS7(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/RCSS/2014_12_07_00_16_44.jpg.hengshuai.txt"));
        assertEquals("2014/11/8", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testRCSS8(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/RCSS/2014_12_07_00_26_02.jpg.hengshuai.txt"));
        assertEquals("2014/11/8", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
    }

    @Test
    public void testRCSS9(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/RCSS/2014_12_16_18_12_24.jpg.hengshuai.txt"));
        assertEquals("2013/7/21", DateParserUtils.findDateStringAfterLine(lines, 0).getValue());
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
