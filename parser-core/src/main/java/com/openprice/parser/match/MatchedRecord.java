package com.openprice.parser.match;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.openprice.parser.data.FieldName;
import com.openprice.parser.data.FieldSet;
import com.openprice.parser.data.FileNameIntPair;

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

    public Map<Integer, Set<FieldName>> lineToField() {
        return lineToField;
    }

    public Map<FieldName, Set<Integer>> fieldToLine() {
        return fieldToLine;
    }

    // generate a Match object from a set of pairs
    public static MatchedRecord fromPairs(final Set<FileNameIntPair> set) {
        final MatchedRecord record = new MatchedRecord();
        for (FileNameIntPair p : set) {
            record.putFieldLine(p.getField(), p.getIntV());
        }
        return record;
    }

    /**
     * generate set of pairs
     *
     * @return
     */
    public Set<FileNameIntPair> setOfPairs() {
        final Set<FileNameIntPair> set = new HashSet<FileNameIntPair>();
        for (int line : lineToField.keySet()) {
            for (FieldName field : lineToField().get(line))
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
        return lineToField().get(line);
    }

    // maintain
    public void putFieldLine(final FieldName fName, final int lineNumber) {
        if (!fieldToLine.containsKey(fName)) {
            // first time need inserting a new list;
            fieldToLine().put(fName, new HashSet<Integer>());
        }
        fieldToLine().get(fName).add(lineNumber);
        if (!lineToField.containsKey(lineNumber)) {
            lineToField.put(lineNumber, new HashSet<FieldName>());
        }
        lineToField().get(lineNumber).add(fName);

    }

    // number of matched lines
    public int numMatchedLines() {
        return lineToField().size();
    }

    public int numMatchedFields() {
        return fieldToLine().size();
    }

    /**
     * get the last/maximum line number of all fields. It is the last field
     * line.
     *
     * @return [description]
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

    /**
     * add(merge) two records together to get the "union" in the two key sets.
     *
     * @param primary
     *            primary operand
     * @param r2
     *            operand 2
     * @return the result of adding two operations; it will prefer primary if
     *         there are intersection between the two key sets of the operands.
     *         Note it will throw a key conflict exception if the two operands
     *         have a same fieldName no exception will be thrown if the two
     *         operands have a same line
     */
    public static MatchedRecord add(final MatchedRecord primary, final MatchedRecord r2) throws Exception {
        Set<FileNameIntPair> set = primary.setOfPairs();
        set.addAll(r2.setOfPairs());
        return MatchedRecord.fromPairs(set);
    }

    // @Override:
    // untested
    public boolean myEquals(final MatchedRecord that) {
        return lineToField().equals(that.lineToField()) && fieldToLine().equals(that.fieldToLine());
    }

    public boolean isEmpty() {
        return lineToField().isEmpty() && fieldToLine().isEmpty();
    }

    public String toString(final List<String> lines, final FieldSet fSet) {
        String s = "";
        for (int i : lineToField().keySet()) {
            s += "\n@line " + i + ":" + lines.get(i) + "\n";
            for (FieldName fName : lineToField.get(i)) {
                s += "----" + fName + ": value in fields: " + fSet.fieldNameToValueLine().get(fName).getValue() + "\n";
            }
        }
        return s;
    }

    @Override
    public String toString() {
        String s = "";
        final Set<FileNameIntPair> set = setOfPairs();
        for (FileNameIntPair element : set)
            s += element.toString() + "\n";
        return s;
    }

}