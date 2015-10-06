package com.openprice.parser;

import com.openprice.parser.data.FieldSet;
import com.openprice.parser.data.ValueLine;

/**
 * store-dependent parsing functions.
 */

public interface FieldParser {

    // check if fields that should appear in this receipts are present
    void checkFields(final FieldSet fields)throws Exception;

    ValueLine parseAddress(int lineNumber) throws Exception;

    ValueLine parseAccount(int lineNumber) throws Exception;

    ValueLine parseAuthor(int lineNumber) throws Exception;

    ValueLine parseApproved(int lineNumber) throws Exception;

    ValueLine parseCard(int lineNumber) throws Exception;

    ValueLine parseChain(int lineNumber) throws Exception;

    ValueLine parseChainID(int lineNumber) throws Exception;

    ValueLine parseCashier(int lineNumber) throws Exception;

    ValueLine parseDate(int lineNumber) throws Exception;

    ValueLine parseDate2(int lineNumber) throws Exception;

    ValueLine parseDeposit(int lineNumber) throws Exception;

    ValueLine parseGstAmount(int gstLineNumber) throws Exception;

    ValueLine parseGstNumber(int gstLineNumber) throws Exception;

    /**
     * parse item and price given a item-price line and a possible price tailing string. startLine is just useless.
     * @param startLine
     * @param line
     * @param priceTail
     * @return
     * @throws Exception
     */
    String[] parseItemPrice(final int startLine, final String line,
            final String priceTail) throws Exception;

    ValueLine parsePhone(int line) throws Exception;

    ValueLine parseRef(final int lineNumber)throws Exception;

    ValueLine parseRecycle(int number) throws Exception;

    ValueLine parseSaving(int number, final String matchHead) throws Exception;

    ValueLine parseStoreBranch(int number) throws Exception;

    ValueLine parseStoreID(int lineNumber) throws Exception;

    ValueLine parseSubTotal(int line) throws Exception;

    ValueLine parseTotal(int line)throws Exception;

    ValueLine parseTotalSold(int line) throws Exception;

    // second effort to find the date line
    int findDateLine(final FieldSet fields);

    int findCashierLine(final FieldSet fields);

    /**
        meaningful receipt info finished. the remaining lines of the receipt from the line starting with
        fieldHead will be ignored.
        @param fieldHead a field header string
     */
    boolean receiptFinished(String fieldHead);

    boolean notBlockHeader(int line);
}
