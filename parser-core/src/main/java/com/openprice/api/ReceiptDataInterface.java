package com.openprice.api;

import com.openprice.parser.ReceiptLine;

public interface ReceiptDataInterface {

    ReceiptLine getLine(final int lineNumber);

}

