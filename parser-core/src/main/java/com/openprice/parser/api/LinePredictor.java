package com.openprice.parser.api;

import com.openprice.parser.LineType;

/**
 * predictin a line's content is about date, item, address or noise.
 */
public interface LinePredictor {

    LineType classify(String line);
}
