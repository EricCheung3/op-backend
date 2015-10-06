package com.openprice.parser.match;

import java.util.List;

import com.openprice.parser.LineFinder;
import com.openprice.parser.data.DoubleFieldPair;
import com.openprice.parser.data.FieldName;
import com.openprice.parser.data.FieldSet;
import com.openprice.parser.data.ValueLine;
import com.openprice.parser.exception.FieldNameNotExistException;
import com.openprice.parser.store.MatchedChain;
import com.openprice.parser.store.StoreBranch;
import com.openprice.parser.store.StoreChain;
import com.openprice.parser.store.StoreMap;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Matching lines to branch to get the fields: build FieldSet and match record
 * according to branch info. reimplemented using map so no need to using setters
 * for FieldSet
 */
@Slf4j
public class MatchToBranch {
    private final LineFinder lineFinder;
    private final StoreChain storeChain;

    // private final HeadLine headLine;
    private final StoreMap storeMap;

    @Getter
    private final MatchedRecord matchRecord = new MatchedRecord();
    @Getter
    private final FieldSet fields = FieldSet.build();


    public MatchToBranch(final LineFinder lineFinder, final StoreBranch storeBranch, final MatchedChain chain) {
        this.lineFinder = lineFinder;
        storeChain = storeBranch.getChain();

        // headLine=HeadLine.fromFieldParser(fP);
        storeMap = StoreMap.fromStore(storeBranch);

        matchRecord.putFieldLine(FieldName.Chain, chain.getMatchedOnLine());
        fields.fieldNameToValueLine().put(FieldName.Chain,
                ValueLine.builder().value(storeChain.getCode()).line(chain.getMatchedOnLine()).build());
    }

    /**
     * flush the fields and matchRecord find the best matching field for each
     * line, call setter to set the fields in fieldParser(), and keep
     * MatchRecord
     */
    public void bestMatchingFields(final double threshold) throws FieldNameNotExistException {
        log.debug("threshold=" + threshold);
        for (int i = 0; i < lineFinder.getLines().size(); i++) {
            final String lineStr = lineFinder.getLines().get(i).trim();
            // logger.debug("\n\n##### line "+i+": "+lineStr);
            if (lineFinder.ignoreLine(i))
                continue;
            DoubleFieldPair pair = storeMap.maxFieldMatchScore(lineStr);
            // logger.debug("the matching score to field "+pair.fieldName()+" is
            // pair.score()="+pair.score());
            if (pair.getScore() > threshold) {
                String value = storeMap.value(pair.getFieldName());
                // logger.debug("the getter value="+value);
                fields.fieldNameToValueLine().put(pair.getFieldName(), ValueLine.builder().value(value).line(i).build());
                matchRecord.putFieldLine(pair.getFieldName(), i);

            }
        }
    }

    /**
     * flush the fields and matchRecord find all the matching field for each
     * line, call setter to set the fields in fieldParser(), and keep
     * MatchRecord
     */
    public void allMatchingFields(final double threshold) throws FieldNameNotExistException {
        log.debug("threshold=" + threshold);
        for (int i = 0; i < lineFinder.getLines().size(); i++) {
            final String lineStr = lineFinder.getLines().get(i).trim();
            // logger.debug("\n\n!!!!!!! line "+i+": "+lineStr);
            if (lineFinder.ignoreLine(i))
                continue;
            List<DoubleFieldPair> allPairs = storeMap.allFieldMatchScores(lineStr, threshold);
            for (DoubleFieldPair pair : allPairs) {
                String value = storeMap.value(pair.getFieldName());
                // logger.debug("the getter value="+value);
                fields.fieldNameToValueLine().put(pair.getFieldName(), ValueLine.builder().value(value).line(i).build());
                matchRecord.putFieldLine(pair.getFieldName(), i);
            }
        }

    }

}