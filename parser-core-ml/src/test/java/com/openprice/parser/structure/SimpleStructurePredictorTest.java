package com.openprice.parser.structure;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.openprice.parser.ml.data.LineStructure;
import com.openprice.parser.ml.structure.SimpleStructurePredictor;

public class SimpleStructurePredictorTest {

    private static final SimpleStructurePredictor predictor = new SimpleStructurePredictor();

    @Test
    public void normalInput(){
        final String line = "7040054391580 RIDER INSULATE $179.99 16 ";
        assertTrue(predictor.classify(line) == LineStructure.NumberNamePrice);
    }

    @Test
    public void donotSupportWithoutHeadingItemNumberNow(){
        final String line = "RIDER INSULATE $179.99 16 ";
        assertTrue(predictor.classify(line) != LineStructure.NumberNamePrice);
    }

    @Test
    public void donotSupportWithoutTrailingPriceNow(){
        final String line = "RIDER INSULATE $17999 16 ";
        assertTrue(predictor.classify(line) != LineStructure.NumberNamePrice);
    }
}
