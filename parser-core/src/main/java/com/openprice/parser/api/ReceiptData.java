package com.openprice.parser.api;

import java.util.List;

import com.google.common.collect.ImmutableList;

public interface ReceiptData {


    //Stream<ReceiptLine> lines();

    ReceiptLine getLine(final int lineNumber);

    List<ReceiptLine> getReceiptLines();

    ImmutableList<String> getOriginalLines();
}

