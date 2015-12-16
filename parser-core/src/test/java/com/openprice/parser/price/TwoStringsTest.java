package com.openprice.parser.price;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.openprice.parser.price.TwoStrings;

public class TwoStringsTest {


    @Test
    public void emptyTest1() throws Exception{
        assertTrue(TwoStrings.emptyValue().isEmpty());
    }
}
