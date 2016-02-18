package com.openprice.parser.simple;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.api.MatchedRecord;
import com.openprice.parser.data.StringInt;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * quick look up fieldName and line matching relationships. it's actually like
 * pair, (1, field1), (1, field2). So may be you can construct MatchRecord using
 * a list of pairs. two fields can also be mapped to the same line; two lines
 * Can be mapped to the same field --- a field can appear in multiple lines.
 */
@Slf4j
public class MatchedRecordImpl implements MatchedRecord{


    // mapping line number to FieldNameAddressLines that is matched by the line
    @Getter
    private final Map<Integer, Set<ReceiptFieldType>> lineToField = new HashMap<Integer, Set<ReceiptFieldType>>();

    // reverse mapping of the Map<Integer, FieldNameAddressLines>
    @Getter
    private final Map<ReceiptFieldType, Set<Integer>> fieldToLine = new HashMap<ReceiptFieldType, Set<Integer>>();

    //save the many field members in a map
    @Getter
    private final Map<ReceiptFieldType, StringInt> fieldToValueLine = new HashMap<ReceiptFieldType, StringInt>();

    //TODO: can you ensure all the maps are consistent? You have getters. Probably you should remove getters.
    @Override
    public boolean isFieldLine(int line) {
        return lineToField.containsKey(line);
    }

    @Override
    public boolean fieldIsMatched(final ReceiptFieldType f) {
        return fieldToLine.containsKey(f);
    }

    /**
     * get the last/maximum line number of all fields. It is the last field
     * line.
     *
     * @return
     */
    @Override
    //TODO lambda
    public int lastFieldLine() {
        int max = -1;
        for (int i : lineToField.keySet()) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    @Override
    public int itemStopLineNumber() {
        Optional<StringInt> stopLine =
                Stream.of(ReceiptFieldType.GstAmount, ReceiptFieldType.Total, ReceiptFieldType.SubTotal)
                      .map( field -> fieldToValueLine.get(field) )
                      .filter( value -> value != null )
                      .min( Comparator.comparing(StringInt::getLine) );
        //the results will be contain lines in the footer--usually noisy
        if(!stopLine.isPresent())
            log.warn("no stop line (total, gst, subtotal) detected. results are likely noisy");

        return stopLine.isPresent()? stopLine.get().getLine() : Integer.MAX_VALUE;
    }

    @Override
    public Set<ReceiptFieldType> matchedFieldsOnLine(final int line) {
        return lineToField.get(line);
    }

    @Override
    public Set<Integer> matchedLinesOfField(ReceiptFieldType type) {
        return fieldToLine.get(type);
    }

    @Override
    public StringInt valueOfField(ReceiptFieldType type) {
        return fieldToValueLine.get(type);
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
    @Override
    public void putFieldLineValue(final ReceiptFieldType fName, final int lineNumber, final String value) {
        putFieldLine(fName, lineNumber);
        fieldToValueLine.put(fName,  new StringInt(value, lineNumber));
    }
    public void putFieldLine(final ReceiptFieldType fName, final StringInt valueLine) {
        putFieldLineValue(fName, valueLine.getLine(), valueLine.getValue());
    }
}