package com.openprice.common;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class TextResourceUtilsTest {

    @Test
    public void test1() throws Exception{
        final List<String> list=TextResourceUtils.loadStringArray("/fileTest1.txt");
        assertEquals(3,list.size());
        assertEquals("a",list.get(0));
        assertEquals("b",list.get(1));
        assertEquals("c",list.get(2));
    }
}
