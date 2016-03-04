package com.openprice.parser.price;

import com.openprice.common.StringCommon;
import com.openprice.parser.api.NonWideSpaceParser;
import com.openprice.parser.data.ProductImpl;
import com.openprice.parser.ml.data.PriceParserConstant;
import com.openprice.parser.ml.structure.NumberNamePriceFeatures;

/**
 * parser for lines that don't have a wide space
 * right now this parser works well except for
 * name number price
 */
public class NonWideSpaceParserImpl implements NonWideSpaceParser{

    @Override
    public ProductPrice parse(String str){
        str =  str.trim();
        if(str.endsWith("$"))
            str = str.substring(0, str.length()-1);
        final NumberNamePriceFeatures features = new NumberNamePriceFeatures(str);
        String number = StringCommon.EMPTY;
        String name = StringCommon.EMPTY;
        String price = StringCommon.EMPTY;

        if(features.getNumHeadingDigits() >= PriceParserConstant.MIN_ITEM_NUMBER_LENGTH){
            number = str.substring(0, features.getFirstNonDigitSpace());
            name = str.substring(features.getFirstNonDigitSpace(), features.getLastNonDigitSpace()+1);
        }else{
          //the whole string is the name
            number = StringCommon.EMPTY;
            name = str.substring(0, features.getLastNonDigitSpace()+1);
        }

        price = str.substring(features.getLastNonDigitSpace()+1);

        name = name.trim();
        if(name.endsWith("$"))
            name = name.substring(0, name.length()-1).trim();
        return new ProductPrice(ProductImpl.fromNameNumber(
                name,
                number.trim()),
                StringCommon.formatPrice(price));

    }

}
