package com.openprice.parser.ml.api;

import com.openprice.parser.api.StoreConfig;

/**
 * predictin a line's content is about date, item, address or noise.
 */
public interface LinePredictor {

    //classify a line with a store config
    LineType classify(String str, StoreConfig config);

    LineType classify(String str);
}
