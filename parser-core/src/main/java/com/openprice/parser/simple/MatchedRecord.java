package com.openprice.parser.simple;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import com.openprice.parser.ReceiptData;
import com.openprice.parser.StoreBranch;
import com.openprice.parser.StoreConfig;
import com.openprice.parser.StoreParser;
import com.openprice.parser.common.StringCommon;
import com.openprice.parser.data.ReceiptField;
import com.openprice.parser.data.ValueLine;

import lombok.Data;

/**
 * quick look up fieldName and line matching relationships. it's actually like
 * pair, (1, field1), (1, field2). So may be you can construct MatchRecord using
 * a list of pairs. two fields can also be mapped to the same line; two lines
 * Can be mapped to the same field --- a field can appear in multiple lines.
 */
@Data
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

    public int itemStopLineNumber() {
        Optional<ValueLine> stopLine =
                Stream.of(ReceiptField.GstAmount, ReceiptField.Total, ReceiptField.SubTotal)
                .map( field -> fieldToValueLine.get(field) )
                .filter( value -> value != null )
                .min( Comparator.comparing(ValueLine::getLine) );
        return stopLine.isPresent()? stopLine.get().getLine() : Integer.MAX_VALUE;
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
    public void putFieldLine(final ReceiptField fName, final ValueLine valueLine) {
        putFieldLine(fName, valueLine.getLine(), valueLine.getValue());
    }

    public void matchToBranch(final ReceiptData receipt, final StoreBranch storeBranch) {
        receipt.lines()
        .filter( line -> line.getCleanText().length() > 2 )
        .map( line -> storeBranch.maxFieldMatchScore(line) )
        .filter( lineScore -> lineScore.getScore() > 0.5)
        .forEach( lineScore -> putFieldLine(lineScore.getField(), lineScore.getReceiptLine().getNumber(), lineScore.getValue()));
        ;
        //
        //        System.out.println("After matchToBranch, parsed fields are :");
        //        for (ReceiptField field : fieldToValueLine.keySet()) {
        //            System.out.println(field.name() + " at line "+fieldToValueLine.get(field).getLine() + " : " + fieldToValueLine.get(field).getValue() );
        //        }

    }

    public void matchToHeader(final ReceiptData receipt, final StoreConfig config, final StoreParser parser) {
        for (ReceiptField field : ReceiptField.values()) {
            if (fieldNameIsMatched(field)) {
                continue;
            }
            final List<String> headerPatterns = config.getFieldHeaderMatchStrings(field);
            if (headerPatterns == null || headerPatterns.isEmpty()) {
                continue;
            }

            receipt.lines()
            .filter( line -> line.getCleanText().length() > 1 )
            .filter( line -> !isFieldLine(line.getNumber()) )
            .filter( line -> {
                Optional<Double> maxScore =
                        headerPatterns.stream()
                        .map( header -> StringCommon.matchStringToHeader(line.getCleanText(), header) )
                        .max( Comparator.comparing(score -> score) );
                return maxScore.isPresent() && maxScore.get() > config.similarityThresholdOfTwoStrings();
            })
            .forEach( line -> putFieldLine(field, line.getNumber(), parser.parseField(field, line)))
            ;

        }
    }

}