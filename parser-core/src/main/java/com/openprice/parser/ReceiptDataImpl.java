package com.openprice.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.openprice.parser.api.ReceiptDataInterface;
import com.openprice.parser.api.ReceiptLine;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * List of strings to represent receipt data from OCR.
 *
 */
@Slf4j
public class ReceiptDataImpl implements ReceiptDataInterface{
    // minimum number of lines in a receipt
    public static final int MIN_NUMBER_LINES = 5;

    //limit on the number of lines that are searched when looking for chain
    public static final int CHAIN_SEARCH_NUMBER_LINES = 10;

    @Getter
    private final List<ReceiptLine> receiptLines;

    private ReceiptDataImpl(final List<String> originalLines) {
        receiptLines = new ArrayList<>();
        int origLineNumber = 0;
        for (final String origLine : originalLines) {
            receiptLines.add(new ReceiptLine(origLine, origLine.trim().toLowerCase(), origLineNumber, this));
            origLineNumber++;
        }
    }

    public static ReceiptDataImpl fromContentLines(final List<String> lines) throws Exception {
        ReceiptDataImpl f = new ReceiptDataImpl(lines);
        if (lines.size() < MIN_NUMBER_LINES) {//TODO: we should only allow non-empty lines
            throw new Exception("Receipt is too short, only has " + lines.size() + " lines.");
        }
        return f;
    }

    public static ReceiptDataImpl fromString(final String allLines) throws Exception {
        String[] lines = allLines.split("\n");
        return fromContentLines(java.util.Arrays.asList(lines));
    }

    public static ReceiptDataImpl fromOCRResults(final List<String> ocrTextList) throws Exception {
        log.debug("get {} ocr Text.", ocrTextList.size());
        final List<String> allLines = new ArrayList<>();
        for (final String ocrText : ocrTextList) {
            //log.debug("ocr result:\n" + ocrText);
            allLines.addAll(java.util.Arrays.asList(ocrText.split("\n")));
        }
        log.debug("get {} lines of text from ocr result.", allLines.size());
        return fromContentLines(allLines);
    }

    @Override
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
