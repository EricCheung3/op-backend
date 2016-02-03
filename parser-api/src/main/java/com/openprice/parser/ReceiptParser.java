package com.openprice.parser;

import java.util.List;

public interface ReceiptParser {

    ParsedReceipt parseReceiptOcrResult(List<String> ocrTextList);
}
