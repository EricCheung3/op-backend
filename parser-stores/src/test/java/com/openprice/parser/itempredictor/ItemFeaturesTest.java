package com.openprice.parser.itempredictor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.openprice.common.StringCommon;

public class ItemFeaturesTest {

    @Test
    public void normalInput(){
        final String str = "7040054391580 RIDER INSULATE $179.99 16 ";
        final ItemFeatures features = new ItemFeatures(str);
        assertEquals("7040054391580".length(), features.getNumHeadingDigits());
        assertEquals(StringCommon.removeAllSpaces(" RIDER INSULATE").length(), features.getNumCharsAtMiddle());
        assertEquals("179.9916".length(), features.getNumTrailingDigits());//"." is counted
        assertTrue(features.isOneDollarSignAtTail());
        assertTrue(features.isOneDotAtTail());
    }
}
