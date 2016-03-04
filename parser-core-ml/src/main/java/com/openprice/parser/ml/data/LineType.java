package com.openprice.parser.ml.data;

/**
 * a line content type
 */
public enum LineType {
    Address,
    Category, //like "31-Home" is a category string
    Date,
    Item,
    Footer, //like "TEll US HOW WE DID TODAY!" is a footer
    Noise,
    Phone,
    UnitPrice,
    WeightPrice,
    Unpredictable
}


