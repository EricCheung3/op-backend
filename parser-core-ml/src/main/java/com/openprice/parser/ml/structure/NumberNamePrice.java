package com.openprice.parser.ml.structure;

import com.openprice.parser.ml.api.predictor.BoundaryPredictor;

/**
 * predict that the boundary according to the order of $number $name $price strings.
 *
 */
public class NumberNamePrice implements BoundaryPredictor {

    final String str;

    //the index of the first non-digit and non-space char. It should never bigger than str.length()-1
    int firstNonDigitSpace;
  //the index of the last non-digit space char before some digits. e.g., AB12C, it should be 1 ('B')
    int lastNonDigitSpace;//It should never bigger than str.length()-1

    public NumberNamePrice(final String str){
        this.str=str;
        final int[] boundaries = cuttingBoundaries(str);
        this.firstNonDigitSpace = boundaries[0];
        this.lastNonDigitSpace = boundaries[1];
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

    @Override
    public int boundary1() {
        return firstNonDigitSpace;
    }

    @Override
    public int boundary2() {
        return lastNonDigitSpace;
    }
}
