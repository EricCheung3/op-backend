package com.openprice.parser.item;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.openprice.parser.ml.api.predictor.NumberNamePriceLine;
import com.openprice.parser.ml.item.SimpleNumberNamePriceLine;

public class ItemPredictorImplTest {

    private static final NumberNamePriceLine predictor = new SimpleNumberNamePriceLine();

    @Test
    public void normalInput(){
        final String line = "7040054391580 RIDER INSULATE $179.99 16 ";
        assertTrue(predictor.isNumberNamePriceFormat(line));
    }

    @Test
    public void donotSupportWithoutHeadingItemNumberNow(){
        final String line = "RIDER INSULATE $179.99 16 ";
        assertTrue(!predictor.isNumberNamePriceFormat(line));
    }

    @Test
    public void donotSupportWithoutTrailingPriceNow(){
        final String line = "RIDER INSULATE $17999 16 ";
        assertTrue(!predictor.isNumberNamePriceFormat(line));
    }
}
