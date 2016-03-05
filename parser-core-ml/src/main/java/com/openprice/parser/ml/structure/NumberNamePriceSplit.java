package com.openprice.parser.ml.structure;

import java.util.HashMap;
import java.util.Map;

import com.openprice.common.StringCommon;
import com.openprice.parser.ml.api.predictor.LineParser;
import com.openprice.parser.ml.data.ItemEntity;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Value
@Slf4j
public class NumberNamePriceSplit implements LineParser {


    @Override
    public Map<ItemEntity, String> splitAndRecognize(final String str) {
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




}
