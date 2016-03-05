package com.openprice.parser.ml.structure;

import java.util.HashMap;
import java.util.Map;

import com.openprice.common.StringCommon;
import com.openprice.parser.ml.api.predictor.LineParser;
import com.openprice.parser.ml.data.ItemEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
/**
 * split according to the order of "number name price"
 */
public class NumberNamePriceSplit implements LineParser {

    private final Map<ItemEntity, String> entityToValue;

    public NumberNamePriceSplit(final String str){
        entityToValue = splitAndRecognize(str);
    }

    /**
     * split a line and Recognize each token to give structure values of the line
     * @param origLine original line from a receipt
     * @return the map, key being ItemEntity, value being the value of ItemEntity
     */
    public static Map<ItemEntity, String> splitAndRecognize(final String str) {
        final NumberNamePrice features = new NumberNamePrice(str);
        final int firstNonDigitSpace = features.boundary1();
        final int lastNonDigitSpace = features.boundary2();

        log.debug("firstNonDigitSpace="+firstNonDigitSpace+", lastNonDigitSpace="+lastNonDigitSpace);

        final String headDigits = firstNonDigitSpace > 0?
                str.substring(0, firstNonDigitSpace): StringCommon.EMPTY;
        log.debug("headDigits = "+ headDigits);

        final String middleChars = lastNonDigitSpace >= 0?
                str.substring(Math.max(0, firstNonDigitSpace), lastNonDigitSpace+1) : StringCommon.EMPTY;
        log.debug("middle chars = "+ middleChars);

        final String trailingDigits = lastNonDigitSpace > 0?
                str.substring(lastNonDigitSpace+1) : StringCommon.EMPTY;
        log.debug("trailingDigits = "+ trailingDigits);

        final Map<ItemEntity, String> map = new HashMap<>();
        map.put(ItemEntity.Number, headDigits);
        map.put(ItemEntity.Name, middleChars);
        map.put(ItemEntity.Price, trailingDigits);
        return map;
    }


    @Override
    public String getParsedName(){
        return entityToValue.get(ItemEntity.Name);
    }

    @Override
    public String getParsedNumber() {
        return entityToValue.get(ItemEntity.Number);
    }

    @Override
    public String getParsedPrice() {
        return entityToValue.get(ItemEntity.Price);
    }


}
