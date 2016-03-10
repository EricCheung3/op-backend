package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.junit.Test;

import com.openprice.parser.price.ThreeStrings;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Month1DayYear2Test {

    private final Month1DayYear2 m1dy2 = new Month1DayYear2();

    public static ThreeStrings threeStrings(final LocalDate date){
        return new ThreeStrings(date.getYear()+"", date.getMonthValue()+"", date.getDayOfMonth()+"");
    }
    public static ThreeStrings threeStrings(final int a, final int b, final int c){
        return new ThreeStrings(a+"", b+"", c+"");
    }

    public ThreeStrings parseToThreeStrings(final String line){
        return threeStrings(m1dy2.parseWithSpaces(line));
    }

    @Test
    public void testSears() {
        final String fromSear = "01429 15~ 4884 4601652 4/11/13 4:15P";
        assertEquals(threeStrings(2013, 4, 11), parseToThreeStrings(fromSear));
    }

    @Test
    public void testSearsSpacesIsOkay() {
        final String fromSear = "01429 15~ 4884 4601652 4 /11/13 4:15P";
        assertEquals(threeStrings(2013, 4, 11), parseToThreeStrings(fromSear));
    }

    @Test
    public void spaceIsFine() {
        Pattern p = Pattern.compile("(\\d\\s*\\d|\\d{2})");
        assertTrue(p.matcher("11").matches());
        assertTrue(p.matcher("1 1").matches());
    }

    @Test
    public void testSearsSpacesIsOkay2() {
        final String fromSear = "01429 15~ 4884 4601652 4 /1  1  /1 3  4:15P";
        assertEquals(threeStrings(2013, 4, 11), parseToThreeStrings(fromSear));
    }

    @Test
    public void testSearsMessupIsOkay2() {
        final String fromSear = "01429 15~ 4884 46016524 /11  /13  4:15P";
        assertEquals(threeStrings(2013, 4, 11), parseToThreeStrings(fromSear));
    }

    @Test
    public void spacesBetweenMonthDigitsAreOkay() {
        final String str = "01429 15~ 4884 4601652 4 /11  /13  4:15P";
        assertEquals(threeStrings(2013, 4, 11), parseToThreeStrings(str));
    }

    @Test
    public void spaces1() {
        final String str = " 4/11/13";
        assertTrue(Month1DayYear2.getPatternMonthDayYear2().matcher(str).matches());
        assertEquals(threeStrings(2013, 4, 11), parseToThreeStrings(str));
    }

    @Test
    public void spaces2() {
        final String str = "4 /11/13";
        assertTrue(Month1DayYear2.getPatternMonthDayYear2().matcher(str).matches());
        assertEquals(threeStrings(2013, 4, 11), parseToThreeStrings(str));
    }

    @Test
    public void spaces3() {
        final String str = "4 / 11/13";
        assertTrue(Month1DayYear2.getPatternMonthDayYear2().matcher(str).matches());
        assertEquals(threeStrings(2013, 4, 11), parseToThreeStrings(str));
    }

    @Test
    public void spaces4() {
        final String str = "4 / 1 1/13";
        assertTrue(Month1DayYear2.getPatternMonthDayYear2().matcher(str).matches());
        assertEquals(threeStrings(2013, 4, 11), parseToThreeStrings(str));
    }

    @Test
    public void spaces5() {
        final String str = "4 / 1 1 /13";
        assertTrue(Month1DayYear2.getPatternMonthDayYear2().matcher(str).matches());
        assertEquals(threeStrings(2013, 4, 11), parseToThreeStrings(str));
    }

    @Test
    public void spaces6() {
        final String str = "4 / 1 1 / 13";
        assertTrue(Month1DayYear2.getPatternMonthDayYear2().matcher(str).matches());
        assertEquals(threeStrings(2013, 4, 11), parseToThreeStrings(str));
    }

    @Test
    public void spaces7() {
        final String str = "4 / 1 1 / 1 3";
        assertTrue(Month1DayYear2.getPatternMonthDayYear2().matcher(str).matches());
        assertEquals(threeStrings(2013, 4, 11), parseToThreeStrings(str));
    }

    @Test
    public void spaces8() {
        final String str = "4 / 1 1 / 1 3 ";
        assertTrue(Month1DayYear2.getPatternMonthDayYear2().matcher(str).matches());
        assertEquals(threeStrings(2013, 4, 11), parseToThreeStrings(str));
    }

    @Test
    public void testSearsMessupIsOkay3() throws Exception{
        assertEquals(threeStrings(2013, 2, 3), parseToThreeStrings("01429 15~ 7913 4606631  2/3/13             6:04P"));
    }

    @Test
    public void dmy2SpacesSimple() throws Exception{
        final String str = "3/5/16";
        log.debug("str="+str);
        assertEquals(threeStrings(2016, 3, 5), parseToThreeStrings(str));
    }

    @Test
    public void dmy2Spaces() throws Exception{
        final String str = "  3 / 5 /16 12 3* 0970 04 0209 2189 ";
//        assertTrue(Month1DayYear2.getPatternMonthDayYear2().matcher(str).matches());//TODO why don't pass?
        assertEquals(threeStrings(2016, 3, 5), parseToThreeStrings(str));
    }

    @Test
    public void dmy2MatchSpaces1() throws Exception{
        final String str = " 3/5/16";
        assertTrue(Month1DayYear2.getPatternMonthDayYear2().matcher(str).matches());
    }

    @Test
    public void dmy2MatchSpaces2() throws Exception{
        final String str = " 3/5/16";
        assertTrue(Month1DayYear2.getPatternMonthDayYear2().matcher(str).matches());
    }

    @Test
    public void dmy2MatchSpaces3() throws Exception{
        final String str = " 3 / 5/16";
        assertTrue(Month1DayYear2.getPatternMonthDayYear2().matcher(str).matches());
    }

    @Test
    public void dmy2MatchSpaces4() throws Exception{
        final String str = " 3 / 5 /16";
        assertTrue(Month1DayYear2.getPatternMonthDayYear2().matcher(str).matches());
    }

    @Test
    public void dmy2MatchSpaces5() throws Exception{
        final String str = " 3 / 5 / 16";
        assertTrue(Month1DayYear2.getPatternMonthDayYear2().matcher(str).matches());
    }

    @Test
    public void dmy2MatchSpaces6() throws Exception{
        final String str = " 3 / 5 / 16 ";
        assertTrue(Month1DayYear2.getPatternMonthDayYear2().matcher(str).matches());
    }

    @Test
    public void dmy2Spaces1() throws Exception{
        final String str = "3 / 5 / 16";
        assertTrue(Month1DayYear2.getPatternMonthDayYear2().matcher(str).matches());
        assertEquals(threeStrings(2016, 3, 5), parseToThreeStrings(str));
    }

    @Test
    public void dmy2Spaces2() throws Exception{
        final String str = "3 / 5 / 16 ";
        assertTrue(Month1DayYear2.getPatternMonthDayYear2().matcher(str).matches());
        assertEquals(threeStrings(2016, 3, 5), parseToThreeStrings(str));
    }

}
