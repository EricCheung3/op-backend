package com.openprice.parser.store;

import java.util.List;

import org.springframework.util.StringUtils;

import com.openprice.common.Levenshtein;
import com.openprice.common.StringCommon;
import com.openprice.parser.api.ReceiptLine;
import com.openprice.parser.common.DateParserUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * commonly shared field parser utils by store parsers
 */
@Slf4j
public class FieldParserCommon {

    /**
     * get the value string after the header
     * @param lineString receipt line string (clean text)
     * @param header matched header text
     * @return value string
     * @throws Exception
     */
    public static String getValueAtTail(final String lineString, final String header, final double similarityThresholdOfTwoStrings) {
        if(StringUtils.isEmpty(header)) {
            return lineString;
        }

        // check header string in the receipt line
        String headerStringFromReceiptString = StringCommon.firstNonSpaceChars(lineString, header.length());
        if(Levenshtein.compare(header, headerStringFromReceiptString) <= similarityThresholdOfTwoStrings){
            log.warn("Head of receipt string '{}' and header '{}' not similar ", headerStringFromReceiptString, header);
        }
        final String tail = StringCommon.removeFirstNonSpaceChars(lineString, header).trim();
        return tail;
    }

    public static String getTailOnlyDigitsDots(final String lineString, final String header, final double similarityThresholdOfTwoStrings)throws Exception{
        return StringCommon.getOnlyDigitsDots(getValueAtTail(lineString, header, similarityThresholdOfTwoStrings));
    }

    public static String parseItemPrice(final String lineString, final String priceTail) {

        int lastDot = lineString.lastIndexOf(".");
        if (lastDot < 0) {
            lastDot = lineString.lastIndexOf("'");
            if (lastDot < 0) {
                lastDot = lineString.lastIndexOf("-");
                if (lastDot < 0) {
                    lastDot = lineString.lastIndexOf("~");
                    if (lastDot < 0) {
                        lastDot = lineString.length();
                        // throw new ItemPriceLineException("No '.' found on
                        // line "+line);
                    }
                }
            }
        }
        final int lastLet = StringCommon.lastLetter2(lineString, lastDot - 1);
        // System.out.println("lastLet="+lastLet+", lastDot="+lastDot);
        assert lastDot - 1 >= lastLet;
        if (lastLet < 0) {
            return StringCommon
                    .formatPrice(StringCommon.removeTrailingChars(lineString.substring(lastLet + 1), priceTail));
        }

        //String name = lineString.substring(0, lastLet + 1);
        String price = "";
        try {
            price = StringCommon.formatPrice(StringCommon.removeTrailingChars(lineString.substring(lastLet + 1), priceTail));
        } catch (Exception e) {
            System.out.println(
                    "lastLet=" + lastLet + ", lastDot=" + lastDot + ", line=" + lineString + ", priceTail=" + priceTail);
        }
        return price;
    }

    public static String parseTotal(final String lineString) {
        int last = StringCommon.lastLetter(lineString);
        if (last == -1) {
            log.warn("line '{}' did not find a letter?", lineString);
            return "";
        }
        String priceString=lineString.substring(last + 1);
        log.debug("priceString="+priceString);
        if(priceString.isEmpty())
            priceString = StringCommon.getOnlyDigits(lineString);
        return StringCommon.formatPrice(priceString);
    }

    public static String parseTotalSold(final ReceiptLine line) {
        final String lineString = line.getCleanText();
        log.debug("lineString="+lineString);
        try{
            int last = StringCommon.lastLetter(lineString);
            log.debug("last="+last);
            if (last == -1) {
                log.warn("line '{}' did not find a letter?", lineString);
                return "";
            }
            final String intString=StringCommon.getOnlyDigits(lineString.substring(last + 1));
            log.debug("intString is "+intString);
            return Integer.valueOf(intString)+ StringCommon.EMPTY;
        }catch(Exception e){
            log.warn(e.getMessage());
            return lineString;
        }
    }

    public static String parseDate(final ReceiptLine line){
        final List<String> origLines=line.getReceipt().getOriginalLines();
        final int currentNumber=line.getNumber();
        return DateParserUtils.findDateStringAfterLine(origLines, currentNumber).getValue();
    }
}
