package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;

import com.openprice.common.TextResourceUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateParserUtilsTest {


    @Test
    public void getMeaningfulDateWordsTest1(){
        final String[] words = new String[]{"2007", ".", "01"};
        final List<String> cleanWords = DateParserUtils.getMeaningfulDateWords(words);
        assertEquals(2, cleanWords.size());
        assertEquals("2007", cleanWords.get(0));
        assertEquals("01", cleanWords.get(1));
    }

    //TODO: this test should pass
//    @Test
//    public void pruneDateStringWithMatchTest(){
//        final String str =  DateParserUtils.pruneDateString("DATE/ TIME :                15/02/10  16:16:28");
//        assertEquals("15/02/10", str);
//    }

    @Test
    public void toDateFromDigitalFormatParseAsYearMonthDayFormat() throws Exception{
        final LocalDate date = DateParserUtils.parseToLocalDate("15/02/10");
        assertEquals(2015, date.getYear());//year
        assertEquals(2, date.getMonthValue());//month
        assertEquals(10, date.getDayOfMonth());//day
    }

    @Test
    public void toDateFromDigitalFormatPreferADayThatIsBeforeToday2() throws Exception{
        final LocalDate date= DateParserUtils.parseToLocalDate("16/01/12");
        ;
        assertEquals(2016, date.getYear());//year
        assertEquals(1, date.getMonthValue());//month
        assertEquals(12, date.getDayOfMonth());//day
    }

    @Test
    public void toDateFromDigitalFormatPreferADayThatIsBeforeToday3() throws Exception{
        final LocalDate date= DateParserUtils.parseToLocalDate("16/01/16");
        ;
        assertEquals(2016, date.getYear());//year
        assertEquals(1, date.getMonthValue());//month
        assertEquals(16, date.getDayOfMonth());//day
    }

    @Test
    public void toDateFromDigitalFormatPreferADayThatIsBeforeToday4() throws Exception{
        final LocalDate date= DateParserUtils.parseToLocalDate("12/16/16");
        ;
        //will see a warning if today is not this date yet
        assertEquals(2016, date.getYear());//year
        assertEquals(12, date.getMonthValue());//month
        assertEquals(16, date.getDayOfMonth());//day
    }

    //note this test was written before 2016-05-15
    @Test
    public void toDateFromDigitalFormatPreferADayThatIsBeforeToday() throws Exception{
        final LocalDate date= DateParserUtils.parseToLocalDate("15/05/16");
        ;
        assertEquals(2015, date.getYear());//year
        assertEquals(5, date.getMonthValue());//month
        assertEquals(16, date.getDayOfMonth());//day
    }

    @Test
    public void toDateFromDigitalFormat15MustBeYearTest() throws Exception{
        final LocalDate date= DateParserUtils.parseToLocalDate("15/05/10");
        ;
        assertEquals(2015, date.getYear());//year
        assertEquals(5, date.getMonthValue());//month
        assertEquals(10, date.getDayOfMonth());//day
    }

    @Test
    public void toDateFromDigitalFormat15MustBeYear() throws Exception{
        final LocalDate date= DateParserUtils.parseToLocalDate("12/05/15");
        ;
        assertEquals(2015, date.getYear());//year
        assertEquals(12, date.getMonthValue());//month
        assertEquals(5, date.getDayOfMonth());//day
    }

    @Test
    public void toDateFromDigitalFormatPreferMonthDayYearIfNotSure() throws Exception{
        final LocalDate date= DateParserUtils.parseToLocalDate("12/05/12");
        assertEquals(2012, date.getYear());//year
        assertEquals(12, date.getMonthValue());//month
        assertEquals(5, date.getDayOfMonth());//day
    }

    @Test
    public void currentYear(){
        int year = DateUtils.getCurrentYearInTwoDigits();
        log.debug("current year =" + year);
    }

    @Test
    public void zeroBeforeIsFine(){
        final int five = Integer.valueOf("05");
        assertEquals(5, five);
    }

    @Test
    public void spaceTest(){
        final Pattern p = Pattern.compile("a ?b");
        assertTrue(p.matcher("ab").matches());
        assertTrue(p.matcher("a b").matches());
        assertTrue(!p.matcher("a c").matches());
    }

    @Test
    public void dateTest1(){
        final Pattern p = Pattern.compile("(Jan||January)\\s*[1-9]");
        assertTrue(p.matcher("Jan 9").matches());
        assertTrue(p.matcher("Jan9").matches());
        assertTrue(p.matcher("January 9").matches());
        assertTrue(p.matcher("January9").matches());
    }

    @Test
    public void toDateTest1TwoDigitYearFormatIsNowSupported() throws Exception{
        final LocalDate date=DateParserUtils.parseToLocalDate("1/1/13");
        assertEquals(2013, date.getYear());
        assertEquals(1, date.getMonthValue());
        assertEquals(1, date.getDayOfMonth());
    }

    @Test
    public void canParseSingleDigitDayOrMonth()throws Exception{
        final LocalDate date =  LocalDate.parse("2013/1/1", DateParserUtils.DATE_FORMATTER);
        assertEquals(2013, date.getYear());
        assertEquals(1, date.getMonthValue());
        assertEquals(1, date.getDayOfMonth());
    }

    @Test
    public void canParseTwoDigitDayOrMonth()throws Exception{
        final LocalDate date = LocalDate.parse("2013/12/12", DateParserUtils.DATE_FORMATTER);
        assertEquals(2013, date.getYear());
        assertEquals(12, date.getMonthValue());
        assertEquals(12, date.getDayOfMonth());
    }

    @Test
    public void toDateTest1SingleDigitDayIsOkayYearAtEnd() throws Exception{
        final LocalDate date=DateParserUtils.parseToLocalDate("1/1/2013");
        log.debug("date"+date);

        assertEquals(2013, date.getYear());
        assertEquals(1, date.getMonthValue());
        assertEquals(1, date.getDayOfMonth());
    }

    @Test
    public void toDateTest1SingleDigitDayIsOkay() throws Exception{
        final LocalDate date=DateParserUtils.parseToLocalDate("2013/1/1");
        log.debug("date"+date);

        assertEquals(2013, date.getYear());
        assertEquals(1, date.getMonthValue());
        assertEquals(1, date.getDayOfMonth());
    }

    @Test
    public void toDateTest1SingleDigitMonthIsOkay() throws Exception{
        final LocalDate date=DateParserUtils.parseToLocalDate("2013/1/31");
        log.debug("date"+date);

        assertEquals(2013, date.getYear());
        assertEquals(1, date.getMonthValue());
        assertEquals(31, date.getDayOfMonth());
    }

    @Test
    public void toDateTest1() throws Exception{
        final LocalDate date=DateParserUtils.parseToLocalDate("2013/01/31");
        log.debug("date"+date);

        assertEquals(2013, date.getYear());
        assertEquals(1, date.getMonthValue());
        assertEquals(31, date.getDayOfMonth());
    }

    @Test
    public void toDateTest1DotIsOkay() throws Exception{
        final LocalDate date=DateParserUtils.parseToLocalDate("2013.01.31");
        log.debug("date"+date);

        assertEquals(2013, date.getYear());
        assertEquals(1, date.getMonthValue());
        assertEquals(31, date.getDayOfMonth());
    }

    @Test
    public void toDateTest1DashIsOkay() throws Exception{
        final LocalDate date=DateParserUtils.parseToLocalDate("2013-01-31");
        log.debug("date"+date);

        assertEquals(2013, date.getYear());
        assertEquals(1, date.getMonthValue());
        assertEquals(31, date.getDayOfMonth());
    }

    @Test
    public void toDateTest1YearInTheEndIsOkay() throws Exception{
        final LocalDate date=DateParserUtils.parseToLocalDate("01-31-2013");
        log.debug("date"+date);

        assertEquals(2013, date.getYear());
        assertEquals(1, date.getMonthValue());
        assertEquals(31, date.getDayOfMonth());
    }

    @Test
    public void toDateTest1YearInTheEndIsOkayDot() throws Exception{
        final LocalDate date=DateParserUtils.parseToLocalDate("01.31.2013");
        log.debug("date"+date);

        assertEquals(2013, date.getYear());
        assertEquals(1, date.getMonthValue());
        assertEquals(31, date.getDayOfMonth());
    }


    @Test(expected=Exception.class)
    public void toDateTestSplitterIsNotAccepted() throws Exception{
        DateParserUtils.parseToLocalDate("01)31)2013");
    }

    @Test
    public void toDateTest1YearInTheEndIsOkaySlash() throws Exception{
        final LocalDate date=DateParserUtils.parseToLocalDate("01/31/2013");
        log.debug("date"+date);

        assertEquals(2013, date.getYear());
        assertEquals(1, date.getMonthValue());
        assertEquals(31, date.getDayOfMonth());
    }

    @Test
    public void testDate1YearFirstIsOkay() throws Exception{
        final List<String> lines=new ArrayList<String>();
        lines.add("DATE            TIME            AMOUNT");
        lines.add("2015/01/18      17:26:22        $        14.48");
        lines.add("APPROVED");
        lines.add("No Signature Required");
        assertEquals("2015/1/18", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void findDateLiteralMonthDayYearSpace() throws Exception{
        final List<String> lines=new ArrayList<String>();
        lines.add("DATE            TIME            AMOUNT");
        lines.add("Feb 09 2015 TIME 17:26:22        $        14.48");
        lines.add("APPROVED");
        lines.add("No Signature Required");
        assertEquals("2015/2/9", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void findDateLiteralMonthDayYearComma() throws Exception{
        final List<String> lines=new ArrayList<String>();
        lines.add("DATE            TIME            AMOUNT");
        lines.add("Feb 09, 2015 TIME 17:26:22        $        14.48");
        lines.add("APPROVED");
        lines.add("No Signature Required");
        assertEquals("2015/2/9", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void findDateLiteralMonthDayYearMoreSpaces() throws Exception{
        final List<String> lines=new ArrayList<String>();
        lines.add("DATE            TIME            AMOUNT");
        lines.add("Feb  09      2015 TIME 17:26:22        $        14.48");
        lines.add("APPROVED");
        lines.add("No Signature Required");
        assertEquals("2015/2/9", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void findDateLiteralMonthDayYearMoreSpacesAndOneComma() throws Exception{
        final List<String> lines=new ArrayList<String>();
        lines.add("DATE            TIME            AMOUNT");
        lines.add("Feb  09,      2015 TIME 17:26:22        $        14.48");
        lines.add("APPROVED");
        lines.add("No Signature Required");
        assertEquals("2015/2/9", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void findDateLiteralMonthDayYearMoreSpacesAndOneComma2() throws Exception{
        final List<String> lines=new ArrayList<String>();
        lines.add("DATE            TIME            AMOUNT");
        lines.add("Feb  09    ,      2015 TIME 17:26:22        $        14.48");
        lines.add("APPROVED");
        lines.add("No Signature Required");
        assertEquals("2015/2/9", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void findDateLiteralMonthDayYearMoreSpacesAndOneComma3() throws Exception{
        final List<String> lines=new ArrayList<String>();
        lines.add("DATE            TIME            AMOUNT");
        lines.add("DATE: MOn Feb 9,2015 TIME: 17:45:11");
        lines.add("APPROVED");
        lines.add("No Signature Required");
        assertEquals("2015/2/9", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void findDateInALineTest() throws Exception{
        final String line = "01429 388 1953 1621487 2/28/ 15 4 :07P";
        assertEquals("2015/2/28", DateParserUtils.findDateInALine(line));
    }

    @Test
    public void findDateInALineTest2() throws Exception{
        final String line = "01429 15~ 4884 4601652 4/11/13 4:15P";
        assertEquals("2013/4/11", DateParserUtils.findDateInALine(line));
    }

    @Test
    public void findDateInALineTest3() throws Exception{
        final String line = "0142 054 8913 4629940 5/3 1/ 15 4:58P";
        assertEquals("2015/5/31", DateParserUtils.findDateInALine(line));
    }

    @Test
    public void shouldFind2AsMonth() throws Exception{
        final String line = "01429 15~ 7913 4606631  2/3/13             6:04P";
        assertEquals("2013/2/3", DateParserUtils.findDateInALine(line));
    }

    @Test
    public void twoDigitMonth() throws Exception{
        final String searsVariant = "01429 15~ 7913 4606631  12/3/13             6:04P";
        assertEquals("2013/12/3", DateParserUtils.findDateInALine(searsVariant));
    }

    @Test
    public void _99supermarketTest1() throws Exception{
        final String fromLucky99 = "DATE: 23/05/2015 TIME: 5:02:29 PM               HUA";
        assertEquals("2015/5/23", DateParserUtils.findDateInALine(fromLucky99));
    }

    @Test
    public void _99supermarketTest1MonthDayYearIsPrefered() throws Exception{
        final String fromLucky99Variant = "DATE: 12/05/2015 TIME: 5:02:29 PM               HUA";
        assertEquals("2015/12/5", DateParserUtils.findDateInALine(fromLucky99Variant));
    }

    @Test
    public void costco1() throws Exception{
        final String fromCostco = "AUTH#: 097118                02/08/15 16:06:54";
        assertEquals("2015/2/8", DateParserUtils.findDateInALine(fromCostco));
    }

    @Test
    public void safeway1() throws Exception{
        final String fromSafeway = " 5/12/14 12:55 0877 08 029L          b.";
        assertEquals("2014/5/12", DateParserUtils.findDateInALine(fromSafeway));
    }

    @Test
    public void homeDepot1(){
        final String str="7117 00058 77840     DATE: 25/01/15 TIME: 11:30 AM";
        assertEquals("2015/1/25", DateParserUtils.findDateInALine(str));
    }

    @Test
    public void TwoDigitDay() throws Exception{
        final String searsVariant = "01429 15~ 7913 4606631  12/31/13             6:04P";
        assertEquals("2013/12/31", DateParserUtils.findDateInALine(searsVariant));
    }

    @Test
    public void yearMonthDayTest1() throws Exception{
        final String searsVariant = "01429 15~ 7913 4606631  15/3/1             6:04P";
        assertEquals("2015/3/1", DateParserUtils.findDateInALine(searsVariant));
    }

    @Test
    public void yearMonthDayTest2() throws Exception{
        final String searsVariant = "01429 15~ 7913 4606631  15/03/1             6:04P";
        assertEquals("2015/3/1", DateParserUtils.findDateInALine(searsVariant));
    }

    @Test
    public void yearMonthDayTest2A() throws Exception{
        final String searsVariant = "Thu Feb 25,2016    14:40:23";
        assertEquals("2016/2/25", DateParserUtils.findDateInALine(searsVariant));
    }

    @Test
    public void yearMonthDayTest3() throws Exception{
        final String searsVariant = "01429 15~ 7913 4606631  15/03/12             6:04P";
        assertEquals("2015/3/12", DateParserUtils.findDateInALine(searsVariant));
    }

    //note it will prefer parsing results with space
    @Test
    public void yearMonthDayTest4() throws Exception{
        final String searsVariant = "01429 15~ 7913 4606631  15/03/1  2             6:04P";
        assertEquals("2015/3/1", DateParserUtils.findDateInALine(searsVariant));
    }

    //either is Okay
    @Test
    public void yearMonthDayTest5() throws Exception{
        final String searsVariant = "01429 15~ 7913 4606631  1  5/  0  3/1  2             6:04P";
//        assertEquals("2015/3/12", DateParserUtils.findDateInALine(searsVariant));
//        assertEquals("2012/5/3", DateParserUtils.findDateInALine(searsVariant));
        assertEquals("2012/3/15", DateParserUtils.findDateInALine(searsVariant));
    }

    @Test
    public void yearMonthDayTest6() throws Exception{
        final String searsVariant = "01429 15~ 7913 46066311  5/  0  3/1  2             6:04P";
//        assertEquals("2015/3/12", DateParserUtils.findDateInALine(searsVariant));
//        assertEquals("2012/5/3", DateParserUtils.findDateInALine(searsVariant));
        assertEquals("2012/3/15", DateParserUtils.findDateInALine(searsVariant));
    }

    @Test
    public void test11(){
        final String str = "Sep 04 2013 10:12 am Trans# 002475";
        assertEquals("2013/9/4", DateParserUtils.findDateInALine(str));
    }

    @Test
    public void testMonthDayYear(){
        final String str = " 03/06/16";
        assertEquals("2016/3/6", DateParserUtils.findDateInALine(str));
    }

    @Test
    public void testMonthDayYearSpaces1(){
        final String str = " 03 /06/16";
        assertEquals("2016/3/6", DateParserUtils.findDateInALine(str));
    }

    @Test
    public void testMonthDayYearSpaces2(){
        final String str = " 03 / 06/16";
        assertEquals("2016/3/6", DateParserUtils.findDateInALine(str));
    }

    @Test
    public void testMonthDayYearSpaces3(){
        final String str = " 03 / 06 /16";
        assertEquals("2016/3/6", DateParserUtils.findDateInALine(str));
    }

    @Test
    public void testMonthDayYearSpaces4(){
        final String str = " 03 / 06 / 16";
        assertEquals("2016/3/6", DateParserUtils.findDateInALine(str));
    }

    @Test
    public void testMonthDayYearSpaces5(){
        final String str = " 0 3 / 06 / 16";
        assertEquals("2016/3/6", DateParserUtils.findDateInALine(str));
    }

    @Test
    public void testMonthDayYearSpaces6(){
        final String str = " 0 3 / 0 6 / 16";
        assertEquals("2016/3/6", DateParserUtils.findDateInALine(str));
    }

    @Test
    public void testMonthDayYearSpaces7(){
        final String str = " 0 3 / 0 6 / 1 6";
        assertEquals("2016/3/6", DateParserUtils.findDateInALine(str));
    }

    @Test
    public void testMonthDayYearSpaces8(){
        final String str = " 0 3 / 0 6 / 1 6 ";
        assertEquals("2016/3/6", DateParserUtils.findDateInALine(str));
    }

    @Test
    public void testMonthDayYearInALine(){
        final String str = " 3973 02782 05 051 46296                                     3/05/16 11:16 ";
        assertEquals("2016/3/5", DateParserUtils.findDateInALine(str));
    }

    @Test
    public void testMonthDayYearInALineSpaces1(){
        final String str = " 3973 02782 05 051 46296                                     3 /05/16 11:16 ";
        assertEquals("2016/3/5", DateParserUtils.findDateInALine(str));
    }

    @Test
    public void testMonthDayYearInALineSpaces2(){
        final String str = " 3973 02782 05 051 46296                                     3 /05 /16 11:16 ";
        assertEquals("2016/3/5", DateParserUtils.findDateInALine(str));
    }

    @Test
    public void testMonthDayYearInALineSpaces3(){
        final String str = " 3973 02782 05 051 46296                                     3 /05/ 16 11:16 ";
        assertEquals("2016/3/5", DateParserUtils.findDateInALine(str));
    }


    @Test
    public void testDate1()throws Exception{
        final List<String> lines=new ArrayList<String>();
        lines.add("DATE            TIME            AMOUNT");
        lines.add("01/18/2015      17:26:22        $        14.48");
        lines.add("APPROVED");
        lines.add("No Signature Required");
        assertEquals("2015/1/18", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void testDate2()throws Exception{
        final List<String> lines=new ArrayList<String>();
        lines.add("DATE 03/ 06/ 2015                TIME 14 :49:48");
        lines.add("AUTH # 00509Z                    REF # 00000062");
        assertEquals("2015/3/6", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void testDateFile1()throws Exception{
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/2015_02_09_11_34_51.jpg.hengshuai.txt"));
        assertEquals("2015/2/1", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void testDateFile2()throws Exception{
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/2015_02_09_11_34_51_variantionDate.jpg.hengshuai2.txt"));
        assertEquals("2015/2/1", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void testDateFileSafeway1()throws Exception{
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/2015_02_27_20_04_24.jpg.dongcui.txt"));
        assertEquals("2015/2/27", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void testDateFileRCSS1()throws Exception{
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/2015_04_04_21_25_02.jpg.jingwang.txt"));
        assertEquals("2015/2/21", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void testDateFileRCSS2()throws Exception{
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/2015_04_04_21_25_02.jpg.jingwang.txt"));
        assertEquals("2015/2/21", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void testSw1(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/Safeway/2014_12_06_22_36_54.jpg.hengshuai.txt"));
        assertEquals("2014/5/12", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void testSw2(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/Safeway/2014_12_06_22_56_07.jpg.hengshuai.txt"));
        assertEquals("2014/2/1", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void testSw3(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/Safeway/2014_12_06_23_12_59_small.jpg.hengshuai.txt"));
        assertEquals("2013/11/17", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void testSw4(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/Safeway/2014_12_07_00_24_15.jpg.hengshuai.txt"));
        assertEquals("2013/11/29", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void testSw5(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/Safeway/2014_12_06_23_12_59.jpg.hengshuai.txt"));
        assertEquals("2013/11/17", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void testSw6(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/Safeway/2014_12_16_18_12_17.jpg.hengshuai.txt"));
        assertEquals("2014/2/1", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void testSw7(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/Safeway/2015_02_09_11_33_44.jpg.hengshuai.txt"));
        assertEquals("2015/2/7", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void testRCSS1(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/RCSS/2014_12_06_22_13_28.jpg.hengshuai.txt"));
        assertEquals("2014/11/8", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void testRCSS2(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/RCSS/2014_12_06_23_05_23.jpg.hengshuai.txt"));
        assertEquals("2014/4/16", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void testRCSS3(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/RCSS/2014_12_06_23_10_49.jpg.hengshuai.txt"));
        assertEquals("2013/7/21", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void testRCSS4(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/RCSS/2014_12_06_23_21_04.jpg.hengshuai.txt"));
        assertEquals("2014/3/1", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void testRCSS5HasNoDate(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/RCSS/2014_12_06_23_33_53.jpg.hengshuai.txt"));
        assertEquals("", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void testRCSS6(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/RCSS/2014_12_06_23_37_10.jpg.hengshuai.txt"));
        assertEquals("2014/5/9", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void testRCSS7(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/RCSS/2014_12_07_00_16_44.jpg.hengshuai.txt"));
        assertEquals("2014/11/8", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void testRCSS8(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/RCSS/2014_12_07_00_26_02.jpg.hengshuai.txt"));
        assertEquals("2014/11/8", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void testRCSS9(){
        final List<String> lines=TextResourceUtils.loadStringArray(("/testFiles/RCSS/2014_12_16_18_12_24.jpg.hengshuai.txt"));
        assertEquals("2013/7/21", DateParserUtils.findDateInLinesAndSelect(lines, 0).getValue());
    }

    @Test
    public void pruneDateStringTest1YearFirstIsOkay(){
        final String dateString="2009/11/25";
        assertEquals(dateString, DateParserUtils.findDateInALine(dateString+" abc de ef"));
    }

    @Test
    public void pruneDateStringTest1YearFirstIsOkay2(){
        final String dateString="2009/1/25";
        assertEquals(dateString, DateParserUtils.findDateInALine(dateString+" abc de ef"));
    }

    @Test
    public void pruneDateStringTest1YearFirstIsOkay3(){
        final String dateString="2009/1/2";
        assertEquals(dateString, DateParserUtils.findDateInALine(dateString+" abc de ef"));
    }

    @Test
    public void pruneDateStringTest1CorrectStringIsAtHead(){
        final String dateString="2009/11/25";
        assertEquals(dateString, DateParserUtils.findDateInALine(dateString+" abc de ef"));
    }

    @Test
    public void pruneDateStringTest1CorrectStringIsAtTail(){
        final String dateString="2009/11/25";
        assertEquals(dateString, DateParserUtils.findDateInALine(" abc de ef"+dateString));
    }

    @Test
    public void pruneDateStringTest1CorrectStringHasSpacesIsOkay(){
        final String dateString="2009/11/25";
        assertEquals(dateString, DateParserUtils.findDateInALine("11/25/2 009"+" abc de ef"));
    }

    @Test
    public void pruneDateStringTest1CorrectStringHasSpacesIsOkay2(){
        final String dateString="2009/11/25";
        assertEquals(dateString, DateParserUtils.findDateInALine("11/ 2 5/2 009"+" abc de ef"));
    }

    @Test
    public void pruneDateStringTest1FromReceipt1(){
        final String dateString="2015/1/11";
        assertEquals(dateString, DateParserUtils.findDateInALine("01/11/2015  15:09:10       $      87.40"));
    }

    @Test
    public void pruneDateStringTest1FromReceipt2(){
        final String dateString="2015/3/6";
        assertEquals(dateString, DateParserUtils.findDateInALine("DATE 03/06/2015                  TIME 14:49:11"));
    }

    @Test
    public void pruneDateStringTest1FromReceipt3(){
        final String dateString="2015/1/18";
        assertEquals(dateString, DateParserUtils.findDateInALine("01/18/2015      17:26:22        $        14.48"));
    }

    @Test
    public void pruneDateStringTest1DotAsSeparatorIsOkay(){
        final String dateString="2015/1/18";
        assertEquals(dateString, DateParserUtils.findDateInALine("01.18.2015      17:26:22        $        14.48"));
    }

    @Test
    public void pruneDateStringTest1SingleDigitDateIsOkay(){
        final String dateString="2015/1/18";
        assertEquals(dateString, DateParserUtils.findDateInALine("1.18.2015      17:26:22        $        14.48"));
    }

    @Test
    public void pruneDateStringTest1SingleDigitDateMonthIsOkay(){
        final String dateString="2015/2/6";
        assertEquals(dateString, DateParserUtils.findDateInALine("2/6/2015      17:26:22        $        14.48"));
    }

    @Test
    public void pruneDateStringTest1DashAsSeparatorIsOkay(){
        final String dateString="2015/1/18";
        assertEquals(dateString, DateParserUtils.findDateInALine("01-18-2015      17:26:22        $        14.48"));
    }

    @Test
    public void pruneDateStringTest1TwoDigitYearIsOkay(){
        final String dateString="2015/2/27";
        assertEquals(dateString, DateParserUtils.findDateInALine("Term Tran       Store         Oper               02/ 27/ 15"));
    }

    @Test
    public void pruneDateStringTest1TwoDigitYearIsOkayDash(){
        final String dateString="2015/2/27";
        assertEquals(dateString, DateParserUtils.findDateInALine("Term Tran       Store         Oper               02- 27- 15"));
    }

    @Test
    public void pruneDateStringTest1TwoDigitYearIsOkayDot(){
        final String dateString="2015/2/27";
        assertEquals(dateString, DateParserUtils.findDateInALine("Term Tran       Store         Oper               02.27. 15"));
    }

    @Test
    public void pruneDateStringTest1LiteralMonthDayYearTest1(){
        final String dateString="2015/2/27";
        assertEquals(dateString, DateParserUtils.findDateInALine("Term Tran       Store         Oper    "+dateString));
    }

    @Test
    public void pruneDateStringTest11(){
        assertEquals("2014/6/15", DateParserUtils.findDateInALine("DATE/ TIME              14/06/15  17:10:35"));
    }

    @Test
    public void pruneDateStringTest12(){
        assertEquals("2014/6/15", DateParserUtils.findDateInALine("01429 140 5102 4619352  6/15/14    5:06P"));
    }

    @Test
    public void pruneDateStringTest13() throws Exception{
        final String line = "3973 02782 05 051 46296                                     3/05/16 11:16 ";
        assertEquals("2016/3/5", DateParserUtils.findDateInALine(line));
    }

    @Test
    public void pruneDateStringTest14() throws Exception{
        final String line = "3/05/16 12 3* 0970 04 0209 2189  ";
        assertEquals("2016/3/5", DateParserUtils.findDateInALine(line));
    }

    @Test
    public void pruneDateStringTest15() throws Exception{
        final String line = "03/06/16 ";
        assertEquals("2016/3/6", DateParserUtils.findDateInALine(line));
    }

    @Test
    public void pruneDateStringTest16() throws Exception{
        final String line = "Sep 04 2013 10:12 am Trans# 002475 ";
        assertEquals("2013/9/4", DateParserUtils.findDateInALine(line));
    }

    @Test
    public void pruneDateStringYear4() throws Exception{
        final String line = "OCT.08’2015 Trans# 002475 ";
        assertEquals("2015/10/8", DateParserUtils.findDateInALine(line));
    }

    @Test
    public void pruneDateStringYear2() throws Exception{
        final String line = "OCT.08’15 Trans# 002475 ";
        assertEquals("2015/10/8", DateParserUtils.findDateInALine(line));
    }

    @Test
    public void pruneDateStringYear2Spaces() throws Exception{
        final String line = "OCT.08’ 15 Trans# 002475 ";
        assertEquals("2015/10/8", DateParserUtils.findDateInALine(line));
    }

    @Test
    public void pruneDateStringYear2Spaces2() throws Exception{
        final String line = "OCT.08  ’ 15 Trans# 002475 ";
        assertEquals("2015/10/8", DateParserUtils.findDateInALine(line));
    }

    @Test
    public void pruneDateStringYear2Spaces3() throws Exception{
        final String line = "OCT.  08  ’ 15 Trans# 002475 ";
        assertEquals("2015/10/8", DateParserUtils.findDateInALine(line));
    }

    @Test
    public void pruneDateStringYear2Spaces4() throws Exception{
        final String line = "OCT  .  08  ’ 15 Trans# 002475 ";
        assertEquals("2015/10/8", DateParserUtils.findDateInALine(line));
    }

    @Test
    public void pruneDateStringTest18() throws Exception{
        final String line = "OCT.08’2015 Trans# 002475 ";
        assertEquals("2015/10/8", DateParserUtils.findDateInALine(line));
    }

    @Test
    public void pruneDateStringTest19() throws Exception{
        final String line = "10/15/14 15:24 0026 75 007'1 5'1'139 ";
        assertEquals("2014/10/15", DateParserUtils.findDateInALine(line));
    }

    @Test
    public void pruneDateStringTest20() throws Exception{
        final String line = "10 / 15 / 14 15 :24 : 1'1 AUTH : 02506'1 ";
        assertEquals("2014/10/15", DateParserUtils.findDateInALine(line));
    }

    //TODO
    @Test
    public void pruneDateStringTest21ThisIsNotADate() throws Exception{
        final String line = "LD WEST EDMONTON MALL  780 9'1'1 '1526 ";
        assertEquals("1526/1/1", DateParserUtils.findDateInALine(line));
    }


}
