package com.openprice.parser.simple;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import com.openprice.common.StringCommon;
import com.openprice.parser.ReceiptDataImpl;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.StoreConfig;
import com.openprice.parser.StoreParser;
import com.openprice.parser.data.ValueLine;
import com.openprice.store.StoreBranch;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * quick look up fieldName and line matching relationships. it's actually like
 * pair, (1, field1), (1, field2). So may be you can construct MatchRecord using
 * a list of pairs. two fields can also be mapped to the same line; two lines
 * Can be mapped to the same field --- a field can appear in multiple lines.
 */
@Data
@Slf4j
public class MatchedRecord {
    // mapping line number to FieldNameAddressLines that is matched by the line
    private final Map<Integer, Set<ReceiptFieldType>> lineToField = new HashMap<Integer, Set<ReceiptFieldType>>();

    // reverse mapping of the Map<Integer, FieldNameAddressLines>
    private final Map<ReceiptFieldType, Set<Integer>> fieldToLine = new HashMap<ReceiptFieldType, Set<Integer>>();

    //save the many field members in a map
    private final Map<ReceiptFieldType, ValueLine> fieldToValueLine = new HashMap<ReceiptFieldType, ValueLine>();


    // whether a line is matched
    public boolean isFieldLine(int line) {
        return lineToField.containsKey(line);
    }

    // whether a field is matched
    public boolean fieldNameIsMatched(final ReceiptFieldType f) {
        return fieldToLine.containsKey(f);
    }

    public boolean fieldIsMatched(final ReceiptFieldType f) {
        return fieldNameIsMatched(f);
    }

    public Set<ReceiptFieldType> matchedFields(final int line) {
        return lineToField.get(line);
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
                Stream.of(ReceiptFieldType.GstAmount, ReceiptFieldType.Total, ReceiptFieldType.SubTotal)
                      .map( field -> fieldToValueLine.get(field) )
                      .filter( value -> value != null )
                      .min( Comparator.comparing(ValueLine::getLine) );
        //the results will be contain lines in the footer--usually noisy
        if(!stopLine.isPresent())
            log.warn("no stop line (total, gst, subtotal) detected. results are likely noisy");

        return stopLine.isPresent()? stopLine.get().getLine() : Integer.MAX_VALUE;
    }

    public void putFieldLine(final ReceiptFieldType fName, final int lineNumber) {
        if ( !fieldToLine.containsKey(fName))
            fieldToLine.put(fName, new HashSet<Integer>());
        fieldToLine.get(fName).add(lineNumber);
        if ( !lineToField.containsKey(lineNumber))
            lineToField.put(lineNumber, new HashSet<ReceiptFieldType>());
        lineToField.get(lineNumber).add(fName);
    }

    /**
     * Put matched field with line and parsed value.
     * YUAN  merge MatchedRecord with FieldSet
     *
     * @param fName
     * @param lineNumber
     * @param value
     */
    public void putFieldLine(final ReceiptFieldType fName, final int lineNumber, final String value) {
        putFieldLine(fName, lineNumber);
        fieldToValueLine.put(fName,  new ValueLine(value, lineNumber));
    }
    public void putFieldLine(final ReceiptFieldType fName, final ValueLine valueLine) {
        putFieldLine(fName, valueLine.getLine(), valueLine.getValue());
    }

    public void matchToBranch(final ReceiptDataImpl receipt, final StoreBranch storeBranch) {
        receipt.lines()
               .filter( line -> line.getCleanText().length() > 2 )
               .map( line -> new MatchToBranchImpl().maxFieldMatchScore(storeBranch, line) )
               .filter( lineScore -> lineScore.getScore() > 0.5)
               .forEach( lineScore -> putFieldLine(lineScore.getField(), lineScore.getReceiptLine().getNumber(), lineScore.getValue()));

//                System.out.println("$$$$$$  After matchToBranch, parsed fields are :");
//                for (ReceiptFieldType field : fieldToValueLine.keySet()) {
//                    System.out.println("'"+ field.name() + "' at line "+fieldToValueLine.get(field).getLine() + " : " + fieldToValueLine.get(field).getValue() );
//                }

    }

    public void matchToHeader(final ReceiptDataImpl receipt, final StoreConfig config, final StoreParser parser) {
        for (ReceiptFieldType field : ReceiptFieldType.values()) {
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
                   .filter(line -> !StringCommon.stringMatchesHead(line.getCleanText().toLowerCase(), "loyalty offer", config.similarityThresholdOfTwoStrings()))//otherwide this could match the total line
                   .filter( line -> {
                Optional<Double> maxScore =
                        headerPatterns
                        .stream()
                        .map( header -> StringCommon.matchStringToHeader(line.getCleanText(), header) )
                        .max( Comparator.comparing(score -> score) );
                return maxScore.isPresent() && maxScore.get() > config.similarityThresholdOfTwoStrings();
            })
            .forEach( line -> putFieldLine(field, line.getNumber(), parser.parseField(field, line)))
            ;
        }
    }

}