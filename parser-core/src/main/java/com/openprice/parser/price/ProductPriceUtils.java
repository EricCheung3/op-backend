package com.openprice.parser.price;

import com.openprice.common.StringCommon;
import com.openprice.parser.api.Product;
import com.openprice.parser.data.ProductImpl;
import com.openprice.parser.ml.data.PriceParserConstant;

/**
 * TODO this class should be removed
 */
public class ProductPriceUtils {

    public static ProductPrice fromNameCut(String itemName,
            String itemNumber, final String price){
        if(itemNumber.isEmpty()){
            try{
                final String[]  itemNameNumberNew=cutTailItemNumber(itemName);
                itemName=itemNameNumberNew[0];
                itemNumber=itemNameNumberNew[1];
            }catch(Exception e1){
                try{
                    final String[]  itemNameNumberNew=cutHeadItemNumber(itemName);
                    itemNumber=itemNameNumberNew[0];
                    itemName=itemNameNumberNew[1];
                }catch(Exception e2){
                    //item name is unchanged
                    //                    log.debug("item number is not detected in name. item number will remain empty.");
                }
            }
        }

        final Product product=ProductImpl.fromNameNumber(itemName.trim(), itemNumber.trim());
        try{
            return new ProductPrice(product, StringCommon.formatPrice(price.trim()));
        }catch(Exception e){
            return new ProductPrice(product, price.trim());
        }
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
