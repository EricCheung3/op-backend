package com.openprice.parser.ml.api.predictor;

import com.openprice.parser.ml.data.LineStructure;

/**
 * predict the structure of a line
 *
 */
public interface StructurePredictor {

    LineStructure classify(String origLine);
}
