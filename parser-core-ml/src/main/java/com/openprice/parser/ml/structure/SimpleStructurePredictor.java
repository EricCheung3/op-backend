//package com.openprice.parser.ml.structure;
//
//import java.util.Map;
//
//import com.openprice.parser.ml.api.predictor.StructurePredictor;
//import com.openprice.parser.ml.data.Features;
//import com.openprice.parser.ml.data.ItemLineStructure;
//
//public class SimpleStructurePredictor implements StructurePredictor{
//
//    private static final StructureFeatureGenerator generator = new StructureFeatureGenerator();
//
//    @Override
//    public Map<ItemLineStructure, String> split(String origLine) {
//        Map<ItemLineStructure, Features> features = generator.getFeatures(origLine);
//        final Map<ItemLineStructure, String> result = null;
//
//        return result;
//    }
//
//}
