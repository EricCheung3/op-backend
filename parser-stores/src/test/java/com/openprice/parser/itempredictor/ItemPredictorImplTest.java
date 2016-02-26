package com.openprice.parser.itempredictor;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ItemPredictorImplTest {

    final private ItemPredictorImpl predictor = new ItemPredictorImpl();

    @Test
    public void test1(){
        assertTrue(predictor.isItemLine("7040054391580 RIDER INSULATE $179.99 16 "));
    }
}
