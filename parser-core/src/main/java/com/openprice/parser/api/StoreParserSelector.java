package com.openprice.parser.api;

public interface StoreParserSelector {

    /**
     * Return proper parser for the receipt. Usually only one parser per store, but for some stores,
     * we have to use different parser for different format of receipts.
     *
     * @param receipt
     * @return
     */
    StoreParser selectParser(ReceiptData receipt);
}
