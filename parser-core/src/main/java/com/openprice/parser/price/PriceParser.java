package com.openprice.parser.price;

import com.openprice.parser.data.ProductPrice;

public interface PriceParser {
    ProductPrice fromTwoStrings(final TwoStrings four) throws Exception;

    ProductPrice fromThreeStrings(final ThreeStrings tri) throws Exception;

    ProductPrice fromFourStrings(final FourStrings four) throws Exception;

}
