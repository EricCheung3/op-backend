package com.openprice.parser.price;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NonWideSpaceParserImplTest {


    @Test
    public void headHasDigits(){
        final String str = "123abc";
        final int[] boundaries = NonWideSpaceParserImpl.cuttingBoundaries(str);
        assertEquals(3, boundaries[0]);
        assertEquals(str.length()-1, boundaries[1]);
    }

    @Test
    public void tailHasDigits(){
        final String str = "abc123";
        final int[] boundaries = NonWideSpaceParserImpl.cuttingBoundaries(str);
        assertEquals(0, boundaries[0]);
        assertEquals(2, boundaries[1]);
    }

    @Test
    public void headTailBothHaveDigits(){
        final String str = "1abc123";
        final int[] boundaries = NonWideSpaceParserImpl.cuttingBoundaries(str);
        assertEquals(1, boundaries[0]);
        assertEquals(3, boundaries[1]);
    }

    @Test
    public void noDigits1(){
        final String str = "abc";
        final int[] boundaries = NonWideSpaceParserImpl.cuttingBoundaries(str);
        assertEquals(0, boundaries[0]);
        assertEquals(2, boundaries[1]);
    }

    @Test
    public void noDigits2(){
        final String str = "a";
        final int[] boundaries = NonWideSpaceParserImpl.cuttingBoundaries(str);
        assertEquals(0, boundaries[0]);
        assertEquals(0, boundaries[1]);
    }

    @Test
    public void noDigitse3(){
        final String str = "";
        final int[] boundaries = NonWideSpaceParserImpl.cuttingBoundaries(str);
        assertEquals(-1, boundaries[0]);
        assertEquals(-1, boundaries[1]);
    }

}
