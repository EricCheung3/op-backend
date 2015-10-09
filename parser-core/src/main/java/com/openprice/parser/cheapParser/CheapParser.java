package com.openprice.parser.cheapParser;

import java.util.ArrayList;
import java.util.List;

import com.openprice.parser.LineFinder;
import com.openprice.parser.ParserUtils;
import com.openprice.parser.common.ListCommon;
import com.openprice.parser.common.StringCommon;
import com.openprice.parser.data.Item;
import com.openprice.parser.data.Skip;
import com.openprice.parser.store.MatchedRecord;

import lombok.extern.slf4j.Slf4j;

/**
 * A cheap parser that can be used for all stores that we don't have a specific
 * parser yet.
 */
@Slf4j
public class CheapParser {
    private final LineFinder lineFinder;
    private final MatchedRecord matchedRecord;
    private final Skip skip;

    private static final double similarityThresholdOfTwoStrings = 0.65; // FIXME get from config

    public CheapParser(final LineFinder lineFinder,
                       final MatchedRecord matchedRecord,
                       final Skip skip) {
        this.lineFinder = lineFinder;
        this.matchedRecord = matchedRecord;
        this.skip = skip;
    }

    public List<Item> parseGeneral() throws Exception {
        final List<Item> items = new ArrayList<Item>();
        final int stopLine = matchedRecord.itemStopLine();
        if (stopLine >= 0 && stopLine < lineFinder.getLines().size())
            log.debug("\n@@@@@last field line is " + stopLine + ", content is " + lineFinder.getLine(stopLine) );

        for (int i = 0; i < lineFinder.getLines().size(); i++) {
            if (matchedRecord.isFieldLine(i))
                continue;

            // stop if this is the last field line
            if (stopLine >= 0 && i >= stopLine)
                break;

            String name = lineFinder.getLine(i).trim();
            String lower = name.toLowerCase();

            if (ListCommon.matchList(skip.getSkipBefore(), name, similarityThresholdOfTwoStrings)) {
                log.debug("skipping " + name + ", becasue it is in skipBefore");
                continue;
            }

            if (ParserUtils.containsSubString(skip.getSkipSubstring(), name))
                continue;

            if (ListCommon.matchList(skip.getSkipAfter(), name, similarityThresholdOfTwoStrings)) {
                log.debug("skipping " + name + " becasue it is in skipAfter");
                log.debug("!!!!!!!itemsFinished=true!!! matched skip After");
                break;
            }

            // stop items when reaching the total line
            if (StringCommon.stringMatchesHead(lower, "total", 0.6)) {
                break;
            }

            if (StringCommon.stringMatchesHead(lower, "subtotal", 0.6)) {
                break;
            }

            if (ParserUtils.isItem(name))
                items.add(new Item(name, i));
        }
        return items;
    }
}
