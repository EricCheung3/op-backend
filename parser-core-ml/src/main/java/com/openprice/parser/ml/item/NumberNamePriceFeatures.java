package com.openprice.parser.ml.item;

import java.util.Map;

import com.openprice.common.StringCommon;
import com.openprice.parser.ml.data.Features;
import com.openprice.parser.ml.data.ItemEntity;
import com.openprice.parser.ml.structure.NumberNamePrice;
import com.openprice.parser.ml.structure.NumberNamePriceSplit;

import lombok.Value;

@Value
public class NumberNamePriceFeatures implements Features{


    String str;
    int numHeadingDigits;
    int numCharsAtMiddle;
    int numTrailingDigits;
    boolean oneDotAtTail;
    boolean oneDollarSignAtTail;

  //the index of the first non-digit and non-space char. It should never bigger than str.length()-1
    int firstNonDigitSpace;
  //the index of the last non-digit space char before some digits. e.g., AB12C, it should be 1 ('B')
    int lastNonDigitSpace;//It should never bigger than str.length()-1
    int length;//length of str

    //other features? number of widespaces? and their index
    public static NumberNamePriceFeatures fromHeadMiddleTailDotDollarFirstLastLength(
            final String str,
            final int headDigits,
            final int middleChars,
            final int trailingDigits,
            final boolean dotAtTail,
            final boolean dollarAtTail,
            final int firstNonDigitSpace,
            final int lastNonDigitSpace,
            final int length){
        return new NumberNamePriceFeatures(
                str,
                headDigits,
                middleChars,
                trailingDigits,
                dotAtTail,
                dollarAtTail,
                firstNonDigitSpace,
                lastNonDigitSpace,
                length);
    }

    public static NumberNamePriceFeatures fromString(final String str){
        final NumberNamePrice numberNamePrice = new NumberNamePrice(str);
        final int[] headTailNumbers = computeHeadingTrailingDigits(str, numberNamePrice.boundary1(), numberNamePrice.boundary2());
        final int numHeadingDigits = headTailNumbers[0];
        final int numTrailingDigits = headTailNumbers[1];

        final Map<ItemEntity, String> map = NumberNamePriceSplit.splitAndRecognize(str);

        //TODO this may use price predictor
        //        final EntityPredictor entityPredictor;
//        entityPredictor.classify(map.get(ItemEntity.Price));
        final boolean oneDotAtPrice = map.get(ItemEntity.Price).indexOf('.') >= 0 &&
                                      map.get(ItemEntity.Price).indexOf('.') == map.get(ItemEntity.Price).lastIndexOf('.');
        final boolean oneDollarSignAtPrice = map.get(ItemEntity.Price).indexOf('$') >=0 &&
                                             map.get(ItemEntity.Price).indexOf('$') == map.get(ItemEntity.Price).lastIndexOf('$');

        final int[] digitsChars = StringCommon.countDigitAndChars(map.get(ItemEntity.Name));
        final int numCharsAtMiddle = digitsChars[1];

        return NumberNamePriceFeatures.fromHeadMiddleTailDotDollarFirstLastLength(
                str,
                numHeadingDigits,
                numCharsAtMiddle,
                numTrailingDigits,
                oneDotAtPrice,
                oneDollarSignAtPrice,
                numberNamePrice.boundary1(),
                numberNamePrice.boundary2(),
                str.length()
                );
    }


    public static int[] computeHeadingTrailingDigits(final String str, final int firstNonDigitSpace, final int lastNonDigitSpace){
        int numHeadingDigits = 0;
        if(firstNonDigitSpace > 0){
            int digitsCharsAtHead[] = StringCommon.countDigitAndChars(str.substring(0, firstNonDigitSpace), ".");
            numHeadingDigits = digitsCharsAtHead[0];
        }

        int numTrailingDigits = 0;
        if(lastNonDigitSpace > 0){
            int digitsCharsAtTail[] = StringCommon.countDigitAndChars(str.substring(lastNonDigitSpace), ".");
            numTrailingDigits = digitsCharsAtTail[0];
        }
        return new int[]{numHeadingDigits, numTrailingDigits};
    }

    @Override
    public int size() {
        return 5;
    }
}
