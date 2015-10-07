package com.openprice.parser.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringCommonTest {

    @Test
    public void getOnlyNumbersAndLetters_ShouldReturnOnlyNumberAndLetter() throws Exception {
        final String test1 = "test &*test  ";
        assertEquals("testtest", StringCommon.getOnlyNumbersAndLetters(test1));

        final String test2 = "123 &*test  TEST()==_";
        assertEquals("123testTEST", StringCommon.getOnlyNumbersAndLetters(test2));
    }
}
