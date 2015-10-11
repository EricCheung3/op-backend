package com.openprice.parser.store.rcss.rcss1;

import com.openprice.parser.ReceiptData;
import com.openprice.parser.StoreConfig;
import com.openprice.parser.common.StringCommon;
import com.openprice.parser.data.ReceiptField;
import com.openprice.parser.store.AbstractStoreParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RCSS1 extends AbstractStoreParser {

    public RCSS1(final StoreConfig config) {
        super(config);
    }

    @Override
    public String parseField(ReceiptField field, ReceiptData receipt, int lineNumber) {

        switch (field) {
        case Card:
            return getValueAtTail(receipt.getLine(lineNumber).getCleanText(), "");

        case GstAmount:
            return parseItemPrice(receipt.getLine(lineNumber).getCleanText(), config.priceTail());

        case SubTotal:
            return parseItemPrice(receipt.getLine(lineNumber).getCleanText(), config.priceTail());

        case Total:
            return parseTotal(receipt.getLine(lineNumber).getCleanText());
        default:
            return receipt.getLine(lineNumber).getCleanText();
        }
    }

    private String parseTotal(final String lineString) {
        int last = StringCommon.lastLetter(lineString);
        if (last == -1) {
            log.warn("line '{}' did not find a letter?", lineString);
            return "";

        }
        return StringCommon.formatPrice(lineString.substring(last + 1));
    }


    private String parseItemPrice(final String lineString, final String priceTail) {

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
//            // throw new Exception("no letter before the dot in this line was
//            // found. lastLet="+lastLet+". lastDot="+lastDot+". line is "+line);
//            warningLogger()
//            .addWarning(startLine,
//                    "this line has no item name. no letter before the dot in this line was found. lastLet="
//                            + lastLet + ". lastDot=" + lastDot,
//                    "probably this line is the price for the last line");
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
            // throw new Exception("price formatPrice error. "+e.getMessage());
        }
        // System.out.println("line="+line+", name="+name+", price="+price);
        //return new String[] { name, price };
        return price;
    }

}
