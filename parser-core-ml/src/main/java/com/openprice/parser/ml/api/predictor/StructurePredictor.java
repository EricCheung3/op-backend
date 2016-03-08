package com.openprice.parser.ml.api.predictor;

import com.openprice.parser.ml.data.LineStructure;

/**
 * predict the structure of a line
 * TODO this may overlap with LineParser.
 * LineParser and StructurePredictor are actually in the same process
 *
 */
public interface StructurePredictor {

    LineStructure classify(String origLine);
}
