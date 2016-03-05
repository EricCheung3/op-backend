package com.openprice.parser.item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.openprice.common.StringCommon;
import com.openprice.parser.ml.item.NumberNamePriceFeatures;

public class NumberNamePriceFeaturesTest {

    @Test
    public void normalInput(){
        final String str = "7040054391580 RIDER INSULATE $179.99 16 ";
        final NumberNamePriceFeatures features = NumberNamePriceFeatures.fromString(str);
        assertEquals("7040054391580".length(), features.getNumHeadingDigits());
        assertEquals(StringCommon.removeAllSpaces(" RIDER INSULATE").length(), features.getNumCharsAtMiddle());
        assertEquals("179.9916".length(), features.getNumTrailingDigits());//"." is counted
        assertTrue(features.isOneDollarSignAtTail());
        assertTrue(features.isOneDotAtTail());
    }
}
