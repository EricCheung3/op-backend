package com.openprice.parser.store;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.util.StringUtils;

import com.openprice.common.Levenshtein;
import com.openprice.common.StringCommon;
import com.openprice.parser.ParsedItem;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.StoreConfig;
import com.openprice.parser.api.ReceiptLine;
import com.openprice.parser.api.StoreParser;
import com.openprice.parser.common.DateParserUtils;
import com.openprice.parser.data.ParsedItemImpl;
import com.openprice.parser.price.PriceParserWithCatalog;
import com.openprice.parser.price.ProductPrice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractStoreParser implements StoreParser {
    protected final StoreConfig config;
    protected final PriceParserWithCatalog priceParserWithCatalog;
    protected final Map<ReceiptFieldType, Function<ReceiptLine, String>> fieldParsers = new HashMap<>();

    public AbstractStoreParser(final StoreConfig config,
            final PriceParserWithCatalog priceParserWithCatalog) {
        this.config = config;
        this.priceParserWithCatalog = priceParserWithCatalog;
    }

    @Override
    public StoreConfig getStoreConfig() {
        return config;
    }

    @Override
    public final String parseField(ReceiptFieldType field, ReceiptLine line) {
        Function<ReceiptLine, String> fieldParser = fieldParsers.get(field);
        if (fieldParser == null) {
            return line.getCleanText();
        } else {
            return fieldParser.apply(line);
        }
    }

    /**
     * get the value string after the header
     * @param lineString receipt line string (clean text)
     * @param header matched header text
     * @return value string
     * @throws Exception
     */
    protected String getValueAtTail(final String lineString, final String header) {
        if(StringUtils.isEmpty(header)) {
            return lineString;
        }

        // check header string in the receipt line
        String headerStringFromReceiptString = StringCommon.firstNonSpaceChars(lineString, header.length());
        if(Levenshtein.compare(header, headerStringFromReceiptString) <= config.similarityThresholdOfTwoStrings()){
            log.warn("Head of receipt string '{}' and header '{}' not similar ", headerStringFromReceiptString, header);
        }
        final String tail = StringCommon.removeFirstNonSpaceChars(lineString, header).trim();
        return tail;
    }

    protected String getTailOnlyDigitsDots(final String lineString, final String header)throws Exception{
        return StringCommon.getOnlyDigitsDots(getValueAtTail(lineString, header));
    }

    protected String parseItemPrice(final String lineString, final String priceTail) {

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

    protected String parseTotal(final String lineString) {
        int last = StringCommon.lastLetter(lineString);
        if (last == -1) {
            log.warn("line '{}' did not find a letter?", lineString);
            return "";
        }
        return StringCommon.formatPrice(lineString.substring(last + 1));
    }

    protected String parseDate(final ReceiptLine line){
        final List<String> origLines=line.getReceipt().getOriginalLines();
        final int currentNumber=line.getNumber();
        return DateParserUtils.findDateStringAfterLine(origLines, currentNumber).getValue();
    }

    @Override
    public ParsedItem parseItemLine(final String lineString, int lineNumber) {
        if (priceParserWithCatalog == null)
            return ParsedItemImpl.fromNameOnly(lineString);
        final ProductPrice pPrice = priceParserWithCatalog.parsePriceLine(lineString);
        if(pPrice==null || pPrice.getName()==null || pPrice.getName().isEmpty())
            return null;
        return ParsedItemImpl.fromProductPriceLineNumber(pPrice, lineNumber);
    }
}
