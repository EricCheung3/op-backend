package com.openprice.parser.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DateLiteralsTest {

    private DateLiterals dateLiterals=new DateLiterals();

    @Test
    public void monthsShouldBe23(){
        assertEquals(23, dateLiterals.sizeOfMonthLiteralMap());
    }

    @Test
    public void testMapping(){
        assertEquals(1, dateLiterals.getMonthNumber("Jan"));
        assertEquals(1, dateLiterals.getMonthNumber("January"));

        assertEquals(2, dateLiterals.getMonthNumber("Feb"));
        assertEquals(2, dateLiterals.getMonthNumber("February"));

        assertEquals(3, dateLiterals.getMonthNumber("March"));
        assertEquals(3, dateLiterals.getMonthNumber("Mar"));

        assertEquals(4, dateLiterals.getMonthNumber("April"));
        assertEquals(4, dateLiterals.getMonthNumber("Apr"));

        assertEquals(5, dateLiterals.getMonthNumber("May"));

        assertEquals(6, dateLiterals.getMonthNumber("Jun"));
        assertEquals(6, dateLiterals.getMonthNumber("June"));

        assertEquals(7, dateLiterals.getMonthNumber("Jul"));
        assertEquals(7, dateLiterals.getMonthNumber("July"));

        assertEquals(8, dateLiterals.getMonthNumber("August"));
        assertEquals(8, dateLiterals.getMonthNumber("Aug"));

        assertEquals(9, dateLiterals.getMonthNumber("Sep"));
        assertEquals(9, dateLiterals.getMonthNumber("September"));

        assertEquals(10, dateLiterals.getMonthNumber("October"));
        assertEquals(10, dateLiterals.getMonthNumber("Oct"));

        assertEquals(11, dateLiterals.getMonthNumber("Nov"));
        assertEquals(11, dateLiterals.getMonthNumber("November"));

        assertEquals(12, dateLiterals.getMonthNumber("Dec"));
        assertEquals(12, dateLiterals.getMonthNumber("December"));
    }
}
