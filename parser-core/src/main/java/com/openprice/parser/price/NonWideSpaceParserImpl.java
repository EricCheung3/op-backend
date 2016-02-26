package com.openprice.parser.price;

import com.openprice.common.StringCommon;
import com.openprice.parser.api.NonWideSpaceParser;
import com.openprice.parser.data.ProductImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * parser for lines that don't have a wide space
 */
@Slf4j
public class NonWideSpaceParserImpl implements NonWideSpaceParser{

    @Override
    public ProductPrice parse(final String str){
        final SplittingFeatures features = new SplittingFeatures(str);
        String number = StringCommon.EMPTY;
        String name = StringCommon.EMPTY;
        String price = StringCommon.EMPTY;

        number = str.substring(0, features.getFirstNonDigitSpace());
        name = str.substring(features.getFirstNonDigitSpace(), features.getLastNonDigitSpace()+1);
        price = str.substring(features.getLastNonDigitSpace()+1);

        name = name.trim();
        if(name.endsWith("$"))
            name = name.substring(0, name.length()-1).trim();
        return new ProductPrice(ProductImpl.fromNameNumber(name, number.trim()), price.trim());

    }

}
