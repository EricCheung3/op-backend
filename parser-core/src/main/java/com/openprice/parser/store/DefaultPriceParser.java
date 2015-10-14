package com.openprice.parser.store;

import com.openprice.parser.data.NumberName;
import com.openprice.parser.data.Product;
import com.openprice.parser.data.ProductPrice;
import com.openprice.parser.price.FourStrings;
import com.openprice.parser.price.PriceParser;
import com.openprice.parser.price.PriceParserUtils;
import com.openprice.parser.price.ThreeStrings;
import com.openprice.parser.price.TwoStrings;

public class DefaultPriceParser implements PriceParser {

    @Override
    public ProductPrice fromThreeStrings(final ThreeStrings tri) throws Exception{
        final NumberName numStr=PriceParserUtils.numberNameSplitter(tri.getFirst());
        return ProductPrice.builder()
                           .product(Product.builder()
                                           .number(numStr.getNumber())
                                           .name(numStr.getName())
                                           .build())
                           .price(tri.getThird())
                           .build();
    }

    @Override
    public ProductPrice fromFourStrings(final FourStrings four) throws Exception{
        return ProductPrice.builder()
                           .product(Product.builder()
                                           .number(four.getFirst().trim())
                                           .name(four.getSecond().trim())
                                           .build())
                           .price(four.getFourth().trim())
                           .build();
    }

    @Override
    public ProductPrice fromTwoStrings(final TwoStrings two) throws Exception{
        final NumberName numStr=PriceParserUtils.numberNameSplitter(two.getFirst());
        return ProductPrice.builder()
                           .product(Product.builder()
                                           .number(numStr.getNumber().trim())
                                           .name(numStr.getName().trim())
                                           .build())
                           .price(two.getSecond().trim())
                           .build();
    }

}
