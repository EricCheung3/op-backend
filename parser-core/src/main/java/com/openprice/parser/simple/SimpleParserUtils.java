package com.openprice.parser.simple;

import java.util.List;
import java.util.stream.Collectors;

import com.openprice.parser.ParsedItem;
import com.openprice.parser.ReceiptDataImpl;
import com.openprice.parser.api.StoreParser;
import com.openprice.parser.common.ListCommon;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleParserUtils {

    public static List<ParsedItem> parseItems(
            final MatchedRecord matchedRecord,
            final ReceiptDataImpl receipt,
            final StoreParser parser) throws Exception {
        final int stopLine = (matchedRecord == null) ? receipt.getReceiptLines().size() :
                                                       Math.min(matchedRecord.itemStopLineNumber(), receipt.getReceiptLines().size());
        log.debug("black list size is "+parser.getStoreConfig().getCatalogFilter().getBlackList().size());
        //        parser.getStoreConfig().getCatalogFilter().getBlackList().forEach(line->log.debug(line+"\n"));

        return
                receipt.lines()
                       .filter( line -> {
                           if(matchedRecord==null)
                               return true;
                           return !matchedRecord.isFieldLine(line.getNumber());
                        })
                       .filter( line -> line.getNumber() < stopLine)
                       .filter( line -> !ListCommon.matchList(parser.getStoreConfig().getSkipBefore(), line.getCleanText(),
                                        parser.getStoreConfig().similarityThresholdOfTwoStrings()))
                       .map( line -> parser.parseItemLine(line.getCleanText(), line.getNumber()))
                       .filter( item -> item != null &&
                                        item.getParsedName()!=null &&
                                        !item.getParsedName().isEmpty() &&
                                        !parser.getStoreConfig().getCatalogFilter().matchesBlackList(item.getParsedName())
                        )
                       .collect(Collectors.toList());
        // TODO stop if match skipAfter strings
    }
}
