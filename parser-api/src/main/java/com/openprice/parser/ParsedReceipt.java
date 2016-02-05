package com.openprice.parser;

import java.util.List;
import java.util.Map;

/**
 * Returned value of receipt from ReceiptParser result.
 *
 */
public interface ParsedReceipt {

    /**
     * StoreChain code parser found that matches the receipt.
     *
     * @return null if no chain can be found by parser.
     */
    String getChainCode();

    /**
     * ParsedItem list the parser can find in the receipt text.
     *
     * @return might be empty list.
     */
    List<ParsedItem> getItems();

    /**
     * Map of parsed fields the parser can find in the receipt text.
     * @return
     */
    Map<ReceiptFieldType, ParsedField> getFields();

    String getBranchName();

}
