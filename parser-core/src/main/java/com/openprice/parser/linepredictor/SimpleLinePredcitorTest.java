package com.openprice.parser.linepredictor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.openprice.parser.LineType;

public class SimpleLinePredcitorTest {
    final SimpleLinePredcitor pred = new SimpleLinePredcitor();

    @Test
    public void isItemNameTest1() throws Exception{
        assertEquals(LineType.Unpredictable, pred.classify("aa"));
    }

    @Test
    public void isItemNameTest2() throws Exception{
        assertEquals(LineType.Unpredictable, pred.classify("a a"));
    }

    @Test
    public void isItemNameTest3() throws Exception{
        assertEquals(LineType.Unpredictable, pred.classify(""));
    }

    @Test
    public void isItemNameTest4() throws Exception{
        assertEquals(LineType.Unpredictable, pred.classify("a a a"));
    }

    @Test
    public void isItemNameTest5() throws Exception{
        assertEquals(LineType.Unpredictable, pred.classify("a1 a a"));
    }

    @Test
    public void isItemNameTest6() throws Exception{
        assertEquals(LineType.Unpredictable, pred.classify("F2000"));
    }

    @Test
    public void isItemNameTest7() throws Exception{
        assertEquals(LineType.Unpredictable, pred.classify("2oo0"));
    }

}
