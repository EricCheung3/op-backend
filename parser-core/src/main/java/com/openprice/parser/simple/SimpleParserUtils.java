package com.openprice.parser.simple;

import java.util.List;
import java.util.stream.Collectors;

import com.openprice.common.StringCommon;
import com.openprice.parser.ParsedItem;
import com.openprice.parser.api.MatchedRecord;
import com.openprice.parser.api.ReceiptData;
import com.openprice.parser.api.StoreParser;
import com.openprice.parser.price.PriceParserConstant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleParserUtils {

    public static List<ParsedItem> parseItems(
            final MatchedRecord matchedRecord,
            final ReceiptData receipt,
            final StoreParser parser) throws Exception {
        final int stopLine = (matchedRecord == null) ? receipt.getReceiptLines().size() :
                                                       Math.min(matchedRecord.itemStopLineNumber(), receipt.getReceiptLines().size());

        return
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
                       .map( line -> parser.parseItemLine(line.getCleanText(), line.getNumber()))
                       .filter( item -> item != null &&
                                        item.getParsedName()!=null &&
                                        !item.getParsedName().isEmpty() &&
                                        StringCommon.countChars(item.getParsedName()) > PriceParserConstant.MIN_ITEM_NAME_LETTERS &&
                                        !parser.getStoreConfig().matchesBlackList(item.getParsedName())
                        )
                       .collect(Collectors.toList());
        // TODO stop if match skipAfter strings
    }
}
