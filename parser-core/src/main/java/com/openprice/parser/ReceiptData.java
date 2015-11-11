package com.openprice.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import lombok.Getter;

/**
 * List of strings to represent receipt data from OCR.
 *
 */
public class ReceiptData {
    // minimum number of lines in a receipt
    public static final int MIN_NUMBER_LINES = 5;
    public static final int CHAIN_SEARCH_NUMBER_LINES = 10;

    @Getter
    private final List<String> originalLines;

    @Getter
    private final List<ReceiptLine> receiptLines;

    private ReceiptData(final List<String> lines) {
        originalLines = lines;
        receiptLines = new ArrayList<>();
        int lineNumber = 0;
        for (final String line : lines) {
            receiptLines.add(new ReceiptLine(line, line.trim(), lineNumber));
            lineNumber++;
        }
    }

    public static ReceiptData fromContentLines(final List<String> lines) throws Exception {
        ReceiptData f = new ReceiptData(lines);
        if (lines.size() < MIN_NUMBER_LINES) {
            throw new RuntimeException("Receipt is too short, only has " + lines.size() + " lines.");
        }
        return f;
    }

    public static ReceiptData fromString(final String allLines) throws Exception {
        String[] lines = allLines.split("\n");
        return fromContentLines(java.util.Arrays.asList(lines));
    }

    public ReceiptLine getLine(final int lineNumber) {
        return receiptLines.get(lineNumber);
    }

    public Stream<ReceiptLine> lines() {
        return receiptLines.stream();
    }

    public List<ReceiptLine> getTopBottomChainMatchingLines() {
        final List<ReceiptLine> lines = new ArrayList<>();
        final int size = receiptLines.size();
        final int topEndLineNumber = Math.min(size, CHAIN_SEARCH_NUMBER_LINES);
        lines.addAll(receiptLines.subList(0, topEndLineNumber));
        if (topEndLineNumber < size) {
            final int bottomBeginLineNumber = Math.max(topEndLineNumber, size - CHAIN_SEARCH_NUMBER_LINES);
            lines.addAll(receiptLines.subList(bottomBeginLineNumber, size));
        }
        return lines;
    }

}
