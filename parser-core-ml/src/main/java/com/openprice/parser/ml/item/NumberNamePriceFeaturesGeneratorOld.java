//package com.openprice.parser.ml.item;
//
//import com.openprice.common.StringCommon;
//import com.openprice.parser.ml.structure.NumberNamePriceSplit;
//
//import lombok.Value;
//
///**
// * item features
// */
//@Value
//public class NumberNamePriceFeaturesGeneratorOld {
//
//    public NumberNamePriceFeatures getFeatures(final String line){
//        final NumberNamePriceSplit splitF = new NumberNamePriceSplit(line);
//        final int numHeadingDigits = splitF.getNumHeadingDigits();
//        final int numTrailingDigits = splitF.getNumTrailingDigits();
//
//        final boolean oneDotAtTail = splitF.getParsedPrice().indexOf('.') >=0 &&
//                splitF.getParsedPrice().indexOf('.') == splitF.getParsedPrice().lastIndexOf('.');
//
//        final boolean oneDollarSignAtTail = splitF.getParsedPrice().indexOf('$') >=0 &&
//                splitF.getParsedPrice().indexOf('$') == splitF.getParsedPrice().lastIndexOf('$');
//
//        final int[] digitsChars = StringCommon.countDigitAndChars(splitF.getParsedName());
//        final int numCharsAtMiddle = digitsChars[1];
//
//        return NumberNamePriceFeatures.fromHeadMiddleTailDotDollar(
//                numHeadingDigits,
//                numCharsAtMiddle,
//                numTrailingDigits,
//                oneDotAtTail,
//                oneDollarSignAtTail);
//    }
//}
