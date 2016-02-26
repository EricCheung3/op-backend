package com.openprice.parser.price;

import java.util.List;

import com.openprice.common.StringCommon;
import com.openprice.parser.api.NonWideSpaceParser;
import com.openprice.parser.api.Product;
import com.openprice.parser.data.ProductImpl;

/**
 * parser for lines that don't have a wide space
 */
public class NonWideSpaceParserImpl implements NonWideSpaceParser{

    private static int MIN_STRING_LENGTH_TO_CONTAIN_ITEM = 15;

    @Override
    public ProductPrice parse(final String line){
        //allow item from a long line with no widepaces
        if(line.trim().length()>=MIN_STRING_LENGTH_TO_CONTAIN_ITEM ){
            final List<String> words = pruneAndSplitLongString(line);

        }
    }

    /*
     * get item number by cutting from name
     */
    public Product cut(final String str){
        final int [] boundaries = cuttingBoundaries(str);
        String name = StringCommon.EMPTY;
        String number = StringCommon.EMPTY;

        number=str.substring(0, boundaries[1]);
        int counts[] = StringCommon.countDigitAndChars(number);
        if(counts[0]>=PriceParserConstant.MIN_ITEM_NUMBER_LENGTH){
            name=str.substring(boundaries[1]);
        }


        try{
            final String[]  nameNumber=cutTailItemNumber(itemName);
            name = nameNumber[0];
            number = nameNumber[1];
        }catch(Exception e1){
            try{
                final String[]  numberName=cutHeadItemNumber(itemName);
                number=numberName[0];
                name=numberName[1];
            }catch(Exception e2){
                //item name is unchanged
                //                    log.debug("item number is not detected in name. item number will remain empty.");
            }
        }
        return ProductImpl.fromNameNumber(name, number);
    }


    /**
     * cut heading digits in a string
     * if no item number found, the input is put into the first out string.
     * @param str
     * @return
     */
    public static String[] cutHeadItemNumber(final String str) throws Exception{
        final String trim=str.trim();

        //detecting all the continuous digits in the beginning; space will not stop
        int digitEnd=-1;
        for(int i=0;i<trim.length();i++){
            if( trim.charAt(i)!=' ' && !Character.isDigit(trim.charAt(i))){
                digitEnd=i;
                break;
            }
        }

        if(digitEnd>0){
            final String number=trim.substring(0, digitEnd);
            int counts[] = StringCommon.countDigitAndChars(number);
            if(counts[0]>=PriceParserConstant.MIN_ITEM_NUMBER_LENGTH){
                final String name=trim.substring(digitEnd);
                return new String[]{number.trim(), name.trim()};
            }
        }
        throw new Exception("No heading item number found");
    }
    /**
     * similar but from the tails: @see cutHeadItemNumber
     * @param str
     * @return
     * @throws Exception
     */
    public static String[] cutTailItemNumber(final String str) throws Exception{
        final String trim=str.trim();
        int digitBegin=-1;
        for(int i=trim.length()-1;i>=0;i--){
            if( trim.charAt(i)!=' ' && !Character.isDigit(trim.charAt(i))){
                digitBegin=i;
                break;
            }
        }
        if(digitBegin>=0){
            final String number=trim.substring(digitBegin+1);
            int counts[] = StringCommon.countDigitAndChars(number);
            if(counts[0]>=PriceParserConstant.MIN_ITEM_NUMBER_LENGTH){
                final String name=trim.substring(0, digitBegin+1);
                return new String[]{name.trim(), number.trim()};
            }
        }
        throw new Exception("No tail item number found");
    }
}
