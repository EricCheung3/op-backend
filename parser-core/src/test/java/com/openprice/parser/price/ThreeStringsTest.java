package com.openprice.parser.price;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ThreeStringsTest {


    @Test
    public void emptyTest1() throws Exception{
        assertTrue(ThreeStrings.emptyValue().isEmpty());
    }
}
