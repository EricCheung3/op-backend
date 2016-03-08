package com.openprice.parser.price;

public interface ParserFromNStrings {
    ProductPrice fromTwoStrings(final TwoStrings four) throws Exception;

    ProductPrice fromThreeStrings(final ThreeStrings tri) throws Exception;

    ProductPrice fromFourStrings(final FourStrings four) throws Exception;


}
