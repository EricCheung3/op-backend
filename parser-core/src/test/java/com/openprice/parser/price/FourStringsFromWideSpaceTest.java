package com.openprice.parser.price;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FourStringsFromWideSpaceTest {

    @Test
    public void test1() throws Exception{
        final FourStrings four= StringsFromWideSpace.fourStrings("000  A  MRJ  1.0");
        assertEquals("A", four.getSecond());
        assertEquals("000", four.getFirst());
        assertEquals("1.0", four.getFourth());
    }

    @Test
    public void test2() throws Exception{
        final FourStrings four= StringsFromWideSpace.fourStrings("000  B CE  RJ  1.0");
        assertEquals("B CE", four.getSecond());
        assertEquals("000", four.getFirst());
        assertEquals("1.0", four.getFourth());
    }

    @Test
    public void test3() throws Exception{
        final FourStrings four= StringsFromWideSpace.fourStrings("000  B C E  MR  1.0");
        assertEquals("B C E", four.getSecond());
        assertEquals("000", four.getFirst());
        assertEquals("1.0", four.getFourth());
    }

    @Test
    public void test4() throws Exception{
        final FourStrings four= StringsFromWideSpace.fourStrings("0 00  B C E  RQ  1. 0");
        assertEquals("B C E", four.getSecond());
        assertEquals("0 00", four.getFirst());
        assertEquals("1. 0", four.getFourth());
    }

    @Test
    public void test5() throws Exception{
        final FourStrings four= StringsFromWideSpace.fourStrings("0 00      B C E   MRQ  1. 0");
        assertEquals("B C E", four.getSecond());
        assertEquals("0 00", four.getFirst());
        assertEquals("1. 0", four.getFourth());
    }


    //empty parsing result if there is no wide spaces splitting into tripple strings
    @Test
    public void test6() throws Exception{
        final FourStrings four= StringsFromWideSpace.fourStrings("0 00      B C E");
        assertEquals(FourStrings.emptyValue(), four);
        assertTrue(four.getFourth().isEmpty());
    }

    @Test
    public void test7() throws Exception{
        final FourStrings four= StringsFromWideSpace.fourStrings("0 0 0       B C E");
        assertEquals(FourStrings.emptyValue(), four);
        assertTrue(four.getFourth().isEmpty());
    }

    @Test
    public void test8() throws Exception{
        final FourStrings four= StringsFromWideSpace.fourStrings("05719775555 RSTR INSTNT NDLE                   MRJ       0.98");

        assertEquals(FourStrings.emptyValue(), four);
        assertTrue(four.getFourth().isEmpty());
    }

    @Test
    public void test9() throws Exception{
        final FourStrings four= StringsFromWideSpace.fourStrings("  06601007023 TABLE SALT                         MRJ       1.99");
        assertEquals(FourStrings.emptyValue(), four);
        assertTrue(four.getFourth().isEmpty());
    }

    @Test
    public void test10() throws Exception{
        final FourStrings four= StringsFromWideSpace.fourStrings("  06570010028      BEATRICE 1% MILK              RQ       4.46");
        assertEquals("BEATRICE 1% MILK", four.getSecond());
        assertEquals("06570010028", four.getFirst());
        assertEquals("4.46", four.getFourth());
    }

}
