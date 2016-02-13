package com.openprice.parser.api;

import java.util.List;

import com.google.common.collect.ImmutableList;

public interface ReceiptData {

    //get the ReceiptLine for the line given by a line number
    ReceiptLine getLine(final int lineNumber);

    //get all the ReceiptLine objects
    List<ReceiptLine> getReceiptLines();

    //get the original lines in String
    ImmutableList<String> getOriginalLines();

    //get the top and bottom lines for finding a chain
    List<ReceiptLine> getTopBottomChainMatchingLines();
}

