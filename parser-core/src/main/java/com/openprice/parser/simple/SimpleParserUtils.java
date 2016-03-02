package com.openprice.parser.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.openprice.common.StringCommon;
import com.openprice.parser.ParsedItem;
import com.openprice.parser.api.MatchedRecord;
import com.openprice.parser.api.ReceiptData;
import com.openprice.parser.api.StoreConfig;
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
                                        !item.getParsedName().isEmpty()
                        )
                       .collect(Collectors.toList());
        // TODO stop if match skipAfter strings

        //now adjust multi-line items' price
        log.debug("before adjusting multiline: items are");
        itemsWithMultilineUnAdjusted.forEach(item-> System.out.println(item.getParsedName()+": "+ item.getParsedBuyPrice()));
        final List<ParsedItem> adjusted = getPriceFromNextLines(itemsWithMultilineUnAdjusted, parser.getStoreConfig());
        return adjusted.stream()
                .filter(item-> {
                            if(! isGoodItem(item, parser.getStoreConfig()))
                                log.debug("item "+ item.getParsedName()+" is considered Not good.");
                            return isGoodItem(item, parser.getStoreConfig());
            })
            .collect(Collectors.toList());

    }

    public static boolean isGoodItem(final ParsedItem item, final StoreConfig config){
        return PriceParserFromStringTuple.isItemName(item.getParsedName()) &&
                !(item.getParsedName().contains("kg") && item.getParsedName().contains("@")) &&
                StringCommon.countChars(item.getParsedName()) >= PriceParserConstant.MIN_ITEM_NAME_LETTERS &&
                !config.matchesBlackList(item.getParsedName());
    }

    /**
     *
     * @param rawItems
     * @return
     */
    public static List<ParsedItem> getPriceFromNextLines(final List<ParsedItem> rawItems, final StoreConfig config){
        final List<ParsedItem> newItems = new ArrayList<ParsedItem>();
        for(int i=0; i<rawItems.size() -1; i++){
            final ParsedItem item = rawItems.get(i);
            log.debug("i = "+ i+ "," + "item="+item.getParsedName()+ ", price="+ item.getParsedBuyPrice());
            if(item.getParsedBuyPrice() != null){
                final int[] digitsLetters = StringCommon.countDigitAndChars(item.getParsedBuyPrice());
                if(!item.getParsedBuyPrice().isEmpty() && digitsLetters[0] > 0){
                    log.debug("digitsLetters[0]= " +digitsLetters[0] + "item is good. no need to adjust. just add. ");
                    newItems.add(item);
                    continue;
                }
            }

            //find the delayed price at a later line that is not a good item
            int increment = i+1;
            ParsedItem next = null;
            for(; increment < rawItems.size(); increment ++ ){
                next = rawItems.get(increment);
                if(next != null && isGoodItem(next, config))//stop at the first good item or end of list
                    break;
            }
            next = rawItems.get(increment-1);//roll back to the previous un-good item
            if(next.equals(item))
                log.debug("No next good item is. No adjusting. ");
            else
                log.debug("found delayed good price at line "+next.getParsedName());

            log.debug("item price is empty, next.getParsedBuyPrice()="+next.getParsedBuyPrice());
            if(next.getParsedBuyPrice() != null && !next.getParsedBuyPrice().isEmpty()){
                System.out.println("adjusting!");
                final String name = item.getParsedBuyPrice() == null ?
                        item.getParsedName() :
                        item.getParsedName() + StringCommon.WIDE_SPACES + item.getParsedBuyPrice();//the getParsedBuyPrice could be part of name
                ParsedItem newItem = ParsedItemImpl.fromNamePriceCodeLine(
                        name,
                        next.getParsedBuyPrice(),
                        item.getCatalogCode(),
                        item.getLineNumber());
                newItems.add(newItem);
            }else{
                //no meaningful delayed price found
                newItems.add(item);
            }
        }
        //add the last item without any adjusting
        if(rawItems.size() > 0)
            newItems.add(rawItems.get(rawItems.size()-1));
        log.debug("newItems.size="+newItems.size());
        return newItems;
    }
}
