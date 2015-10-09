package com.openprice.parser.store;

import com.openprice.parser.ReceiptData;

public interface StoreParserSelector {
    StoreParser selectParser(ReceiptData receipt);
}
