package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.junit.Test;

import com.openprice.parser.price.ThreeStrings;


public class DayMonthYear2Test {

    private final DayMonthYear2 dmy2 = new DayMonthYear2();

    public static ThreeStrings threeStrings(final LocalDate date){
        return new ThreeStrings(date.getYear()+"", date.getMonthValue()+"", date.getDayOfMonth()+"");
    }
    public static ThreeStrings threeStrings(final int y, final int m, final int d){
        return new ThreeStrings(y+"", m+"", d+"");
    }

    public ThreeStrings parseToThreeStrings(final String line) throws Exception {
        if(dmy2.parseWithSpaces(line)==null)
            throw new Exception("parsed result is null");
        return threeStrings(dmy2.parseWithSpaces(line).getDate());
    }

    @Test
    public void testPatternShouldMatch(){
        final String str="7117 00058 77840     DATE: 25/01/15 TIME: 11:30 AM";
        final Matcher match=DayMonthYear2.pattern.matcher(str);
        final List<String> allMatches=new ArrayList<>();
        while(match.find()){
            allMatches.add(match.group());
        }
        assertEquals(1, allMatches.size());
        assertEquals(" 25/01/15 ", allMatches.get(0));
    }

    @Test
    public void test1() throws Exception {
        assertEquals(threeStrings(2015, 5, 3), parseToThreeStrings("03/05/15 sdfs "));
    }

    @Test
    public void spaceBefore1() throws Exception {
        assertEquals(threeStrings(2015, 5, 3), parseToThreeStrings("  03/05/15 sdfs "));
    }

    @Test
    public void noSpaceBeforeA() throws Exception {
        assertEquals(threeStrings(2015, 5, 13), parseToThreeStrings("113/05/15 sdfs "));
    }

    @Test
    public void noSpaceBefore() throws Exception {
        assertEquals(threeStrings(2015, 5, 3), parseToThreeStrings("1103/05/15 sdfs "));
    }

    @Test
    public void oneSpaceBefore() throws Exception {
        assertEquals(threeStrings(2015, 5, 3), parseToThreeStrings("11 03/05/15 sdfs "));
    }

    @Test
    public void twoSpacesBefore() throws Exception {
        assertEquals(threeStrings(2015, 5, 3), parseToThreeStrings("11  03/05/15 sdfs "));
    }

    @Test
    public void threeSpacesBefore() throws Exception {
        assertEquals(threeStrings(2015, 5, 3), parseToThreeStrings("11   03/05/15 sdfs "));
    }

    @Test
    public void fourSpacesBefore() throws Exception {
        assertEquals(threeStrings(2015, 5, 3), parseToThreeStrings("11    03/05/15 sdfs "));
    }

    @Test
    public void fourSpacesAfter() throws Exception {
        assertEquals(threeStrings(2015, 5, 3), parseToThreeStrings("11    03/05/15    13 "));
    }

    @Test
    public void threeSpacesAfter() throws Exception {
        assertEquals(threeStrings(2015, 5, 3), parseToThreeStrings("11    03/05/15   13 "));
    }

    @Test
    public void twoSpacesAfter() throws Exception {
        assertEquals(threeStrings(2015, 5, 3), parseToThreeStrings("11    03/05/15  13 "));
    }

    @Test
    public void oneSpacesAfter() throws Exception {
        assertEquals(threeStrings(2015, 5, 3), parseToThreeStrings("11    03/05/15 13 "));
    }

    @Test
    public void noSpacesAfter() throws Exception {
        assertEquals(threeStrings(2015, 5, 3), parseToThreeStrings("11    03/05/1513 "));
    }

    @Test
    public void spaceInDateString1() throws Exception {
        assertEquals(threeStrings(2015, 5, 13), parseToThreeStrings("1 1 3/0 5/1    5 sdfs "));
    }

    @Test
    public void spaceInDateString2() throws Exception {
        assertEquals(threeStrings(2015, 5, 13), parseToThreeStrings("1 1 3  /0  5/ 1    5 sdfs "));
    }
}
