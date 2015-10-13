package com.openprice.parser.store.rcss;

import com.openprice.parser.data.NumberName;
import com.openprice.parser.data.Product;
import com.openprice.parser.data.ProductPrice;
import com.openprice.parser.price.FourStrings;
import com.openprice.parser.price.PriceParser;
import com.openprice.parser.price.PriceParserUtils;
import com.openprice.parser.price.ThreeStrings;
import com.openprice.parser.price.TwoStrings;

import lombok.extern.slf4j.Slf4j;

/**
 * a typical RCSS line is like
 *   06340004440     CNTRY HVST BRD                    HRJ       2.98
 *   05719777953 ROOSTER RICE                          HRJ      49.76
 *     03600040679 HUG SNUG DRY ST5                      GHRJ     29.97
 *        05870325083 CORD TRIM JUNGL GMRJ         4.00
 *
 */
@Slf4j
public class RCSSPriceParser implements PriceParser{

    @Override
    public ProductPrice fromThreeStrings(final ThreeStrings tri) throws Exception{
        final NumberName numStr=PriceParserUtils.numberNameSplitter(tri.getFirst());
        return ProductPrice
                .builder()
                .product(Product.builder()
                        .number(numStr.getNumber())
                        .name(numStr.getName()).build())
                .price(tri.getThird())
                .build();
    }

    @Override
    public ProductPrice fromFourStrings(final FourStrings four) throws Exception{
        return ProductPrice
                .builder()
                .product(Product.builder()
                        .number(four.getFirst().trim())
                        .name(four.getSecond().trim()).build())
                .price(four.getFourth().trim())
                .build();
    }

    @Override
    public ProductPrice fromTwoStrings(final TwoStrings two) throws Exception{
        final NumberName numStr=PriceParserUtils.numberNameSplitter(two.getFirst());
        return ProductPrice
                .builder()
                .product(Product.builder()
                        .number(numStr.getNumber().trim())
                        .name(numStr.getName().trim()).build())
                .price(two.getSecond().trim())
                .build();
    }
}
