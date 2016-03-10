package com.openprice.parser.ml.api.predictor;

import com.openprice.parser.ml.data.LineType;

/**
 * predictin a line's content is about date, item, address or noise.
 */
public interface LinePredictor {

    //classify a line
    LineType classify(final String origLine);
}
