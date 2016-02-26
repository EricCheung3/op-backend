package com.openprice.parser.itempredictor;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ItemPredictorImplTest {

    final private ItemPredictorImpl predictor = new ItemPredictorImpl();

    @Test
    public void normalInput(){
        final String line = "7040054391580 RIDER INSULATE $179.99 16 ";
        assertTrue(predictor.isItemLine(line));
    }

    @Test
    public void donotSupportWithoutHeadingItemNumberNow(){
        final String line = "RIDER INSULATE $179.99 16 ";
        assertTrue(!predictor.isItemLine(line));
    }

    @Test
    public void donotSupportWithoutTrailingPriceNow(){
        final String line = "RIDER INSULATE $17999 16 ";
        assertTrue(!predictor.isItemLine(line));
    }
}
