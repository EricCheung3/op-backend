package com.openprice.parser.itempredictor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ItemFeaturesTest {

    @Test
    public void test(){
        final String str = "7040054391580 RIDER INSULATE $179.99 16 ";
        final ItemFeatures features = new ItemFeatures(str);

        assertEquals("7040054391580".length(), features.getNumHeadingDigits());
        assertEquals(" RIDER INSULATE $".length(), features.getNumCharsAtMiddle());
        assertEquals("1799916".length(), features.getNumTrailingDigits());
    }
}
