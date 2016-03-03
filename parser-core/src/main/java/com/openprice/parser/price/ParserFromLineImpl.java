package com.openprice.parser.price;

/*
 * first segment the line, and then predict each segment is a name, number, price
 * segment mostly according to wide space, but we need prune for boundary if
 * a. widespace is not available;
 * b. name and number are just together.
 *
 * all cases
 * origLine is a
 * name,
 * name number
 * number name price
 * name price
 */
public class ParserFromLineImpl implements ParserFromLine {


    @Override
    public ProductPrice fromOriginalLine(String origLine) {



    }


}
