package com.openprice.parser.structure;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.openprice.common.StringCommon;
import com.openprice.parser.ml.structure.NumberNamePriceSplit;

public class NumberNamePriceSplitTest {
    @Test
    public void noDigits2GetSplits(){
        final String str = "a";
        final NumberNamePriceSplit splitF = new NumberNamePriceSplit(str);
        assertEquals(StringCommon.EMPTY, splitF.getParsedNumber());
        assertEquals("a", splitF.getParsedName());
        assertEquals(StringCommon.EMPTY, splitF.getParsedPrice());
    }
}
