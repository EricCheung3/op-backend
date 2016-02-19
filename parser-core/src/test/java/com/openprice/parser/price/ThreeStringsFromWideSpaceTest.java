package com.openprice.parser.price;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ThreeStringsFromWideSpaceTest {


    //empty parsing result if there is no wide spaces splitting into tripple strings
    @Test
    public void test6() throws Exception{
        final ThreeStrings tri= StringsFromWideSpace.trippleStrings("0 00      B C E");
        assertEquals(ThreeStrings.emptyValue(), tri);
        assertTrue(tri.isEmpty());
    }

    @Test
    public void test7() throws Exception{
        final ThreeStrings tri= StringsFromWideSpace.trippleStrings("0 0 0       B C E");
        assertEquals(ThreeStrings.emptyValue(), tri);
        assertTrue(tri.isEmpty());
    }

    @Test
    public void test8() throws Exception{
        final ThreeStrings tri= StringsFromWideSpace.trippleStrings("0 0 0       B C E");
        assertEquals(ThreeStrings.emptyValue(), tri);
        assertTrue(tri.isEmpty());
    }

    @Test
    public void test9() throws Exception{
        final ThreeStrings tri= StringsFromWideSpace.trippleStrings("05719775555 RSTR INSTNT NDLE                   MRJ       0.98");
        assertEquals("05719775555 RSTR INSTNT NDLE", tri.getFirst());
        assertEquals("MRJ", tri.getSecond());
        assertEquals("0.98", tri.getThird());
    }

    @Test
    public void test10() throws Exception{
        final ThreeStrings tri= StringsFromWideSpace.trippleStrings("  06601007023 TABLE SALT                         MRJ       1.99");
        assertEquals("06601007023 TABLE SALT", tri.getFirst());
        assertEquals("MRJ", tri.getSecond());
        assertEquals("1.99", tri.getThird());
    }

    @Test
    public void test11() throws Exception{
        final ThreeStrings tri= StringsFromWideSpace.trippleStrings("  06340004440 CNTRY HVST BRD                     MR.J     2.98");
        assertEquals("06340004440 CNTRY HVST BRD", tri.getFirst());
        assertEquals("MR.J", tri.getSecond());
        assertEquals("2.98", tri.getThird());
    }
}
