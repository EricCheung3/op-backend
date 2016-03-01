package com.openprice.parser.price;

import com.openprice.common.StringCommon;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Value
@Slf4j
public class NumberNameNumberSplitting {

    String str;
    int firstNonDigitSpace;//the index of the first non-digit space char. It should never begger than str.length()-1

  //the index of the last non-digit space char before some digits. e.g., AB12C, it should be 1 ('B')
    int lastNonDigitSpace;//It should never begger than str.length()-1

    int numHeadingDigits;//number of heading digits before firstNonDigitSpace
    int numTrailingDigits;//number of trailing digits after lastNonDigitSpace

    public NumberNameNumberSplitting(final String str){
        this.str = str;
        final int[] boundaries = cuttingBoundaries(str);
        this.firstNonDigitSpace = boundaries[0];
        this.lastNonDigitSpace = boundaries[1];

        final int[] headTailNumbers = computeNumDigits();
        this.numHeadingDigits = headTailNumbers[0];
        this.numTrailingDigits = headTailNumbers[1];
    }

    public String[] getSplits(){
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

        return new String[]{headDigits, middleChars, trailingDigits};
    }

    /**
     * find the cutting boundaries
     * this can be used as features for a ML approach for cutting a string
     * @param str
     * @return
     */
    public static int[] cuttingBoundaries(final String str){
       //detecting all the continuous digits in the beginning; dot and space will not stop
        int firstNonDigitSpace=-1;
        for(int i=0;i<str.length();i++){
            if( str.charAt(i)!=' '
                    && !Character.isDigit(str.charAt(i))
                    && str.charAt(i)!='.'
                    ){
                firstNonDigitSpace=i;
                break;
            }
        }

        //detecting all the continuous digits in the end;
        //space will not stop
        //must have seen digits
        int lastDigit = -1;
        for(int i = str.length()-1; i >= 0; i--){
            if(Character.isDigit(str.charAt(i))){
                lastDigit = i;
                break;
            }
        }

        int lastNonDigitSpace= str.length()-1;
        for(int i = lastDigit - 1; i >= 0; i--){
            if( str.charAt(i)!=' '
                    && !Character.isDigit(str.charAt(i))
                    && str.charAt(i)!='.'
                    && str.charAt(i)!='$'
                    ){
                lastNonDigitSpace=i;
                break;
            }
        }
        return new int[]{firstNonDigitSpace, lastNonDigitSpace};
    }

    public int[] computeNumDigits(){
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

}
