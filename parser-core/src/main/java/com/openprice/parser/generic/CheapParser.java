package com.openprice.parser.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.StoreBranch;
import com.openprice.parser.StoreChain;
import com.openprice.parser.common.StringCommon;
import com.openprice.parser.data.Item;
import com.openprice.parser.data.ReceiptField;
import com.openprice.parser.data.ValueLine;

/**
 * A generic receipt parser to parse items from receipt lines.
 * 1. Stop when reached "Total" or "Subtotal" line.
 * 2. It should detect Date, Total/subtotal/Gst, phone, address
 * 3. it should be able parse item and prices
 *
 */
//TODO add date and total recognition?
public class CheapParser {

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
                items.add(Item.fromNameOnly(name));
        }
        return ParsedReceipt.fromChainItemsMapBranch(
                StoreChain.genericStoreChain(StringCommon.EMPTY),
                items,
                new HashMap<ReceiptField, ValueLine>(),
                StoreBranch.EmptyStoreBranch());
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