package com.openprice.parser.simple;

import java.util.ArrayList;
import java.util.List;

import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.common.StringCommon;
import com.openprice.parser.data.Item;
import com.openprice.parser.data.Product;

/**
 * A very simple receipt parser to parse items from receipt lines.
 * Stop when reached "Total" or "Subtotal" line.
 *
 */
public class GenericParser {

    public ParsedReceipt parse(final List<String> lines) throws Exception {
        final List<Item> items = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String name = lines.get(i).trim();
            String lower = name.toLowerCase();

            // stop items when reaching the total line
            if (StringCommon.stringMatchesHead(lower, "total", 0.6)) {
                break;
            }

            if (StringCommon.stringMatchesHead(lower, "subtotal", 0.6)) {
                break;
            }

            if (isItem(name))
                items.add(Item.builder()
                        .product(Product.builder()
                                .name(name).build()).build());
        }
        return ParsedReceipt.builder().items(items).build();
    }

    public static boolean isItem(final String name) {
        final String noSpace = StringCommon.removeAllSpaces(name);
        if (noSpace.length() <= 1)
            return false;

        int[] count = StringCommon.countDigitAndAlphabets(noSpace);
        if (count[1] < 1) {
            return false;
        }
        return !StringCommon.containsOneOnlyOneLetter(noSpace);
    }

}
