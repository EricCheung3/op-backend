package com.openprice.parser.cheapParser;

import java.util.ArrayList;
import java.util.List;

import com.openprice.parser.LineFinder;
import com.openprice.parser.Parser;
import com.openprice.parser.ParserUtils;
import com.openprice.parser.common.StringCommon;
import com.openprice.parser.data.FieldSet;
import com.openprice.parser.data.Item;
import com.openprice.parser.data.ReceiptDebug;

/**
 * A cheap parser that can be used for all stores that we don't have a specific
 * parser yet.
 */
public class CheapParser implements Parser {
    private LineFinder lineFinder;
    private List<Item> items;
    private final FieldSet fields = FieldSet.build();

    public CheapParser(final LineFinder lineFinder) {
        this.lineFinder = lineFinder;
        items = new ArrayList<Item>();
    }

    @Override
    public ReceiptDebug parseGeneral() throws Exception {
        for (String line : lineFinder.getLines()) {
            String name = line.trim();
            String lower = name.toLowerCase();

            // stop items when reaching the total line
            if (StringCommon.stringMatchesHead(lower, "total", 0.6)) {
                break;
            }

            if (StringCommon.stringMatchesHead(lower, "subtotal", 0.6)) {
                break;
            }

            if (ParserUtils.isItem(name))
                items.add(new Item(name, -1, "", "", "", ""));
        }
        return new ReceiptDebug(fields, items, null); //warningLogger().warning());
    }
}
