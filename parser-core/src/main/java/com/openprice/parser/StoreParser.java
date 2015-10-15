package com.openprice.parser;

import com.openprice.parser.data.Item;
import com.openprice.parser.data.ReceiptField;

public interface StoreParser {

    /**
     * Gets config properties for the store.
     * @return
     */
    StoreConfig getStoreConfig();

    String parseField(ReceiptField field, ReceiptLine receiptLine);

    Item parseItemLine(String lineString);
}
