package com.openprice.parser.simple;

import java.util.List;
import java.util.stream.Collectors;

import com.openprice.common.StringCommon;
import com.openprice.parser.ParsedItem;
import com.openprice.parser.api.MatchedRecord;
import com.openprice.parser.api.ReceiptData;
import com.openprice.parser.api.StoreParser;
import com.openprice.parser.data.ParsedItemImpl;
import com.openprice.parser.price.PriceParserConstant;
import com.openprice.parser.price.PriceParserFromStringTuple;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleParserUtils {

    public static List<ParsedItem> parseItems(
            final MatchedRecord matchedRecord,
            final ReceiptData receipt,
            final StoreParser parser) throws Exception {
        final int stopLine = (matchedRecord == null) ? receipt.getReceiptLines().size() :
                                                       Math.min(matchedRecord.itemStopLineNumber(), receipt.getReceiptLines().size());

        //multiline items (means the price is not the in the same line with name) unAdjusted;
        final List<ParsedItem> itemsWithMultilineUnAdjusted =
                receipt.getReceiptLines()
                       .stream()
                       .filter( line -> {
                           if(matchedRecord==null)
                               return true;
                           if(matchedRecord.isFieldLine(line.getNumber()))
                               log.debug("line "+line.getCleanText()+" is a field line:"+ matchedRecord.matchedFieldsOnLine(line.getNumber()));
                           return !matchedRecord.isFieldLine(line.getNumber());
                        })
                       .filter( line -> line.getNumber() < stopLine)
                       .filter( line -> {
                             if(parser.getStoreConfig().matchesSkipBefore(line.getCleanText(), parser.getStoreConfig().similarityThresholdOfTwoStrings()))
                                 log.debug(line.getCleanText()+" matches skipBefore");
                             if(parser.getStoreConfig().matchesSkipAfter(line.getCleanText(), parser.getStoreConfig().similarityThresholdOfTwoStrings()))
                                 log.debug(line.getCleanText()+" matches skipAfter");
                                           return !parser.getStoreConfig().matchesSkipBefore(line.getCleanText(), parser.getStoreConfig().similarityThresholdOfTwoStrings())
                                        && !parser.getStoreConfig().matchesSkipAfter(line.getCleanText(), parser.getStoreConfig().similarityThresholdOfTwoStrings());
                        })
                       .map( line -> {
                           log.debug("line.getCleanText()="+line.getCleanText());
                           return parser.parseItemLine(line.getCleanText(), line.getNumber());
                       })
                       .filter( item -> item != null &&
                                        item.getParsedName()!=null &&
                                        !item.getParsedName().isEmpty() &&
                                        !parser.getStoreConfig().matchesBlackList(item.getParsedName())
                        )
                       .collect(Collectors.toList());
        // TODO stop if match skipAfter strings

        //now adjust multi-line items' price
        final List<ParsedItem> adjusted = getPriceFromNextLine(itemsWithMultilineUnAdjusted);
        return adjusted.stream()
        .filter(item->
              PriceParserFromStringTuple.isItemName(item.getParsedName()) &&
              !(item.getParsedName().contains("kg") && item.getParsedName().contains("@")) &&
              StringCommon.countChars(item.getParsedName()) > PriceParserConstant.MIN_ITEM_NAME_LETTERS
        ).collect(Collectors.toList());

    }

    /**
     *
     * @param rawItems
     * @return
     */
    public static List<ParsedItem> getPriceFromNextLine(final List<ParsedItem> rawItems){
        for(int i=0; i<rawItems.size() -1; i++){
            final ParsedItem item = rawItems.get(i);
            final ParsedItem next = rawItems.get(i+1);
            log.debug("item="+item.getParsedName()+ ", price= "+ item.getParsedBuyPrice());
            log.debug("next="+next.getParsedName());
            final int[] digitsLetters = StringCommon.countDigitAndChars(item.getParsedName());
            if(item.getParsedBuyPrice() == null || item.getParsedBuyPrice().isEmpty() || digitsLetters[0] <= 0){
                log.debug("item price is empty, next.getParsedBuyPrice()="+next.getParsedBuyPrice());
                if(isUnitPriceLine(next.getParsedName()) && next.getParsedBuyPrice() != null
                        && !next.getParsedBuyPrice().isEmpty()
                        ){
                    System.out.println("adjusting!");
                    ParsedItem newItem = ParsedItemImpl.fromNamePriceCodeLine(
                            item.getParsedName(),
                            next.getParsedBuyPrice(),
                            item.getCatalogCode(),
                            item.getLineNumber());
                    rawItems.set(i, newItem);
                }
            }
        }
        return rawItems;
    }

    public static boolean isUnitPriceLine(final String line){
        final int[] digitsLetters = StringCommon.countDigitAndAlphabets(line);
        return line.contains("kg") && digitsLetters[0] >= 4;
    }
}
