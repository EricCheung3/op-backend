package com.openprice.parser.simple;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.openprice.parser.ReceiptData;
import com.openprice.parser.StoreBranch;
import com.openprice.parser.StoreConfig;
import com.openprice.parser.StoreParser;
import com.openprice.parser.common.ParserUtils;
import com.openprice.parser.common.StringCommon;
import com.openprice.parser.data.DoubleFieldPair;
import com.openprice.parser.data.ReceiptField;
import com.openprice.parser.data.ValueLine;

/**
 * quick look up fieldName and line matching relationships. it's actually like
 * pair, (1, field1), (1, field2). So may be you can construct MatchRecord using
 * a list of pairs. two fields can also be mapped to the same line; two lines
 * Can be mapped to the same field --- a field can appear in multiple lines.
 */
public class MatchedRecord {
    // mapping line number to FieldNameAddressLines that is matched by the line
    private final Map<Integer, Set<ReceiptField>> lineToField = new HashMap<Integer, Set<ReceiptField>>();

    // reverse mapping of the Map<Integer, FieldNameAddressLines>
    private final Map<ReceiptField, Set<Integer>> fieldToLine = new HashMap<ReceiptField, Set<Integer>>();

    //save the many field members in a map
    private final Map<ReceiptField, ValueLine> fieldToValueLine = new HashMap<ReceiptField, ValueLine>();


    // whether a line is matched
    public boolean isFieldLine(int line) {
        return lineToField.containsKey(line);
    }

    // whether a field is matched
    public boolean fieldNameIsMatched(final ReceiptField f) {
        return fieldToLine.containsKey(f);
    }

    public boolean fieldIsMatched(final ReceiptField f) {
        return fieldNameIsMatched(f);
    }

    public Set<ReceiptField> matchedFields(final int line) {
        return lineToField.get(line);
    }

    // maintain
    public void putFieldLine(final ReceiptField fName, final int lineNumber) {
        if (!fieldToLine.containsKey(fName)) {
            fieldToLine.put(fName, new HashSet<Integer>());
        }
        fieldToLine.get(fName).add(lineNumber);

        if (!lineToField.containsKey(lineNumber)) {
            lineToField.put(lineNumber, new HashSet<ReceiptField>());
        }
        lineToField.get(lineNumber).add(fName);

    }

    /**
     * get the last/maximum line number of all fields. It is the last field
     * line.
     *
     * @return
     */
    public int lastFieldLine() {
        int max = -1;
        for (int i : lineToField.keySet()) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    public int itemStopLine() {
        int stopLine = Math.max(getFieldValueLineNumber(ReceiptField.GstAmount),
                Math.max(getFieldValueLineNumber(ReceiptField.Total), getFieldValueLineNumber(ReceiptField.SubTotal)));
        if(stopLine<0) stopLine=Integer.MAX_VALUE;
        return stopLine;

    }

    int getFieldValueLineNumber(ReceiptField fieldName) {
        ValueLine valueLine = fieldToValueLine.get(fieldName);
        if (valueLine == null) {
            return -1;
        } else {
            return valueLine.getLine();
        }
    }


    /**
     * Put matched field with line and parsed value.
     * YUAN  merge MatchedRecord with FieldSet
     *
     * @param fName
     * @param lineNumber
     * @param value
     */
    public void putFieldLine(final ReceiptField fName, final int lineNumber, final String value) {
        if (!fieldToLine.containsKey(fName)) {
            fieldToLine.put(fName, new HashSet<Integer>());
        }
        fieldToLine.get(fName).add(lineNumber);

        if (!lineToField.containsKey(lineNumber)) {
            lineToField.put(lineNumber, new HashSet<ReceiptField>());
        }
        lineToField.get(lineNumber).add(fName);

        fieldToValueLine.put(fName,  ValueLine.builder().line(lineNumber).value(value).build());
    }

    public void matchToBranch(final ReceiptData lineFinder, final StoreBranch storeBranch) {
        for (int i = 0; i < lineFinder.getLines().size(); i++) {
            final String lineStr = lineFinder.getLines().get(i).trim();
            if (ParserUtils.ignoreLine(lineStr))
                continue;
            DoubleFieldPair pair = storeBranch.maxFieldMatchScore(lineStr);
            if (pair.getScore() > 0.5) {
                putFieldLine(pair.getFieldName(), i, pair.getValue());
            }
        }
    }

    public void matchToHeader(final ReceiptData lineFinder, final StoreConfig config, final StoreParser parser) {
        for (int i = 0; i < lineFinder.getLines().size(); i++) {
            if (!isFieldLine(i)) {
                for (ReceiptField fieldName : ReceiptField.values()) {
                    if (!fieldNameIsMatched(fieldName)) {
                        double maxScore =  findBiggestMatch(lineFinder.getLine(i), config.getFieldHeaderMatchStrings(fieldName));
                        if (maxScore > config.similarityThresholdOfTwoStrings()) {
                            putFieldLine(fieldName, i, parser.parseField(fieldName, lineFinder, i));
                        }
                    }
                }
            }
        }
    }

    private double findBiggestMatch(final String lineStr, final List<String> headers) {
        if (headers == null)
            return -1.0;
        if (headers.isEmpty() || lineStr.isEmpty())
            return -1.0;

        double scoreMax = -1;
        for (int i = 0; i < headers.size(); i++) {
            double score = StringCommon.matchHeadScore(lineStr, headers.get(i));
            if (score > scoreMax) {
                scoreMax = score;
            }
        }
        return scoreMax;
    }

}