package com.openprice.parser.api;

import com.openprice.parser.price.ProductPrice;

/**
 * a parser that parse for lines which don't have any wide spaces
 */
public interface NonWideSpaceParser {

    ProductPrice parse(String lineWithNoWideSpace);

}

