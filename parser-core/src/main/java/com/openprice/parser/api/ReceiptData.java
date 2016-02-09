package com.openprice.parser.api;

import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;

public interface ReceiptData {


    Stream<ReceiptLine> lines();

    ReceiptLine getLine(final int lineNumber);

    ImmutableList<String> getOriginalLines();
}

