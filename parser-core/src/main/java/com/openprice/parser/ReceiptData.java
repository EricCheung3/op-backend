package com.openprice.parser;

import java.util.List;

import com.google.common.collect.ImmutableList;

import lombok.Getter;

/**
 * List of strings to represent receipt data from OCR.
 *
 */
public class ReceiptData {
    // minimum number of lines in a receipt
    public static final int MIN_NUMBER_LINES = 5;

    @Getter
    private final List<String> lines;

    private ReceiptData(final List<String> lines) {
        this.lines = ImmutableList.copyOf(lines);
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

    public String getLine(final int lineNumber) {
        return lines.get(lineNumber);
    }
}
