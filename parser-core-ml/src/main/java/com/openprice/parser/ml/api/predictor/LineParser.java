package com.openprice.parser.ml.api.predictor;

public interface LineParser {

    //get the parsed name from line
    String getParsedName();

    String getParsedNumber();

    String getParsedPrice();

}
