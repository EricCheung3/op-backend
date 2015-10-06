package com.openprice.parser;

import com.openprice.parser.data.ReceiptDebug;

public interface Parser {

    ReceiptDebug parseGeneral() throws Exception;
}
