package com.openprice.parser.price;

import com.openprice.common.StringCommon;

import lombok.Value;

@Value
public class SplittingFeatures {

    String str;
    int firstNonDigitSpace;//the index of the first non-digit space char

  //the index of the last non-digit space char before some digits. e.g., AB12C, it should be 1 ('B')
    int lastNonDigitSpace;

    int numHeadingDigits;//number of heading digits before firstNonDigitSpace
    int numTrailingDigits;//number of trailing digits after lastNonDigitSpace

    public SplittingFeatures(final String str){
        this.str = str;
        final int[] boundaries = cuttingBoundaries(str);
        this.firstNonDigitSpace = boundaries[0];
        this.lastNonDigitSpace = boundaries[1];

        final int[] headTailNumbers = computeNumDigits();
        this.numHeadingDigits = headTailNumbers[0];
        this.numTrailingDigits = headTailNumbers[1];
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
                    ){
                lastNonDigitSpace=i;
                break;
            }
        }
        return new int[]{firstNonDigitSpace, lastNonDigitSpace};
    }

    public int[] computeNumDigits(){
        int digitsCharsAtHead[] = StringCommon.countDigitAndChars(str.substring(0, firstNonDigitSpace), ".");
        int digitsCharsAtTail[] = StringCommon.countDigitAndChars(str.substring(lastNonDigitSpace), ".");
        return new int[]{digitsCharsAtHead[0], digitsCharsAtTail[0]};
    }

}
