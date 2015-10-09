package com.openprice.parser.store.rcss.rcss1;

import com.openprice.parser.ReceiptData;
import com.openprice.parser.ReceiptField;
import com.openprice.parser.StoreConfig;
import com.openprice.parser.store.AbstractStoreParser;

public class RCSS1 extends AbstractStoreParser {

    public RCSS1(final StoreConfig config) {
        super(config);
    }

    @Override
    public String parseField(ReceiptField field, ReceiptData receipt, int line) {
        // TODO Auto-generated method stub
        return receipt.getLine(line);
    }

}
