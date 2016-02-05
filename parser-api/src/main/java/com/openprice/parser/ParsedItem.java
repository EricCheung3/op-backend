package com.openprice.parser;

/**
 * Value of receipt item from ReceiptParser result
 */
public interface ParsedItem {

    /**
     * Product item name the parser found out. Must not be empty.
     *
     *
     * @return
     */
    String getParsedName();

    /**
     * Product item buy price the parser found out.
     *
     * @return null if parser cannot find item price.
     */
    String getParsedBuyPrice();

    /**
     * If parser can find matching catalog, return the code.
     *
     * @return null if no catalog product can be found in store chain catalog list, or no matching chain for this receipt.
     */
    String getCatalogCode();

    /**
     * Line number for this item in the original receipt OCR result text.
     * If multiple lines, return the first line number.
     * -1 if not found
     *
     * @return
     */
    int getLineNumber();

}
