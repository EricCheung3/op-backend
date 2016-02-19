package com.openprice.parser.api;

import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 *
 */
public interface ReceiptData {

    //get the ReceiptLine for the line given by a line number
    ReceiptLine getLine(final int lineNumber);

    //get all the ReceiptLine objects
    //note the size of the returned result should be same as the largest number in ReceiptLines
    List<ReceiptLine> getReceiptLines();

    //get the original lines in String
    //note the size of the returned result should be the same as getReceiptLines()
    ImmutableList<String> getOriginalLines();
}

