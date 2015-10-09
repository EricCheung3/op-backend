package com.openprice.parser.store;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.openprice.parser.FieldName;
import com.openprice.parser.LineFinder;
import com.openprice.parser.common.StringCommon;
import com.openprice.parser.data.DoubleFieldPair;
import com.openprice.parser.data.FileNameIntPair;
import com.openprice.parser.data.ValueLine;

/**
 * quick look up fieldName and line matching relationships. it's actually like
 * pair, (1, field1), (1, field2). So may be you can construct MatchRecord using
 * a list of pairs. two fields can also be mapped to the same line; two lines
 * Can be mapped to the same field --- a field can appear in multiple lines.
 */
public class MatchedRecord {
    // mapping line number to FieldNameAddressLines that is matched by the line
    private final Map<Integer, Set<FieldName>> lineToField = new HashMap<Integer, Set<FieldName>>();

    // reverse mapping of the Map<Integer, FieldNameAddressLines>
    private final Map<FieldName, Set<Integer>> fieldToLine = new HashMap<FieldName, Set<Integer>>();

    //save the many field members in a map
    private final Map<FieldName, ValueLine> fieldToValueLine = new HashMap<FieldName, ValueLine>();

    /**
     * generate set of pairs
     *
     * @return
     */
    public Set<FileNameIntPair> setOfPairs() {
        final Set<FileNameIntPair> set = new HashSet<FileNameIntPair>();
        for (int line : lineToField.keySet()) {
            for (FieldName field : lineToField.get(line))
                set.add(FileNameIntPair.builder().intV(line).field(field).build());
        }
        return set;
    }

    // whether a line is matched
    public boolean isFieldLine(int line) {
        return lineToField.containsKey(line);
    }

    // whether a field is matched
    public boolean fieldNameIsMatched(final FieldName f) {
        return fieldToLine.containsKey(f);
    }

    public boolean fieldIsMatched(final FieldName f) {
        return fieldNameIsMatched(f);
    }

    // the smallest valid Line number
    public int firstMatchedLine(final FieldName f) {
        int minLine = Integer.MAX_VALUE;
        Iterator<Integer> it = fieldToLine.get(f).iterator();
        while (it.hasNext()) {
            int line = it.next();
            if (line >= 0 && line < minLine) {
                minLine = line;
            }
        }
        return minLine;
    }

    public Set<FieldName> matchedFields(final int line) {
        return lineToField.get(line);
    }

    // maintain
    public void putFieldLine(final FieldName fName, final int lineNumber) {
        if (!fieldToLine.containsKey(fName)) {
            fieldToLine.put(fName, new HashSet<Integer>());
        }
        fieldToLine.get(fName).add(lineNumber);

        if (!lineToField.containsKey(lineNumber)) {
            lineToField.put(lineNumber, new HashSet<FieldName>());
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

//    /**
//     * add(merge) two records together to get the "union" in the two key sets.
//     *
//     * @param primary
//     *            primary operand
//     * @param r2
//     *            operand 2
//     * @return the result of adding two operations; it will prefer primary if
//     *         there are intersection between the two key sets of the operands.
//     *         Note it will throw a key conflict exception if the two operands
//     *         have a same fieldName no exception will be thrown if the two
//     *         operands have a same line
//     */
//    public static MatchedRecord add(final MatchedRecord primary, final MatchedRecord r2) throws Exception {
//        Set<FileNameIntPair> set = primary.setOfPairs();
//        set.addAll(r2.setOfPairs());
//        return MatchedRecord.fromPairs(set);
//    }

    //------------------- added by YUAN

    public int itemStopLine() {
        int stopLine = Math.max(gstFieldValueLineNumber(FieldName.GstAmount),
                Math.max(gstFieldValueLineNumber(FieldName.Total), gstFieldValueLineNumber(FieldName.SubTotal)));
        if(stopLine<0) stopLine=Integer.MAX_VALUE;
        return stopLine;

    }

    int gstFieldValueLineNumber(FieldName fieldName) {
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
    public void putFieldLine(final FieldName fName, final int lineNumber, final String value) {
        if (!fieldToLine.containsKey(fName)) {
            fieldToLine.put(fName, new HashSet<Integer>());
        }
        fieldToLine.get(fName).add(lineNumber);

        if (!lineToField.containsKey(lineNumber)) {
            lineToField.put(lineNumber, new HashSet<FieldName>());
        }
        lineToField.get(lineNumber).add(fName);

        fieldToValueLine.put(fName,  ValueLine.builder().line(lineNumber).value(value).build());
    }

    public void matchToBranch(final LineFinder lineFinder, final StoreBranch storeBranch) {
        for (int i = 0; i < lineFinder.getLines().size(); i++) {
            final String lineStr = lineFinder.getLines().get(i).trim();
            if (lineFinder.ignoreLine(i))
                continue;
            DoubleFieldPair pair = storeBranch.maxFieldMatchScore(lineStr);
            if (pair.getScore() > 0.5) {
                putFieldLine(pair.getFieldName(), i, pair.getValue());
            }
        }
    }

    public void matchToHeader(final LineFinder lineFinder, final StoreConfig config, final StoreParser parser) {
        for (int i = 0; i < lineFinder.getLines().size(); i++) {
            if (!isFieldLine(i)) {
                for (FieldName fieldName : FieldName.values()) {
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