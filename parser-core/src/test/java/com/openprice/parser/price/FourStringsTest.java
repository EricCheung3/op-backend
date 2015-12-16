package com.openprice.parser.price;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.openprice.parser.price.FourStrings;

public class FourStringsTest {


    @Test
    public void emptyTest1() throws Exception{
        assertTrue(FourStrings.emptyValue().isEmpty());
    }
}
