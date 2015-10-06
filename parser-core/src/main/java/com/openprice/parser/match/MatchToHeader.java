package com.openprice.parser.match;

import java.util.List;
import java.util.Map;

import com.openprice.parser.LineFinder;
import com.openprice.parser.common.StringCommon;
import com.openprice.parser.data.FieldName;
import com.openprice.parser.data.FieldSet;
import com.openprice.parser.data.ValueLine;
import com.openprice.parser.store.Config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * matching header and setting fields and recording what is matched
 *
 * fields generation is not right. it keep overridden with one value.
 */
@Slf4j
public class MatchToHeader {
    private final Config config;
    private final LineFinder lineFinder;
    private final MatchedRecord excludes;// record that we don't want to consider matching again in this class

    // records of field parsing results
    @Getter
    private final FieldSet fields = FieldSet.build();
    @Getter
    private final MatchedRecord matchRecord = new MatchedRecord();


    // shorthand
    public Map<FieldName, ValueLine> fieldNameToValueLine() {
        return fields.fieldNameToValueLine();
    }

    public MatchToHeader(final Config config, final LineFinder lineFinder, final MatchedRecord ex) {
        this.config = config;
        this.lineFinder = lineFinder;
        excludes = ex;
        matchAllFieldsToAllLines(excludes);
    }

    /**
     * match all lines to get the fields that are not matched by MatchToBranch
     *
     * @throws Exception
     *             [description]
     */
    public void matchAllFieldsToAllLines(final MatchedRecord excludes) {
        for (int i = 0; i < lineFinder.getLines().size(); i++) {
            // logger.debug("\n\nline "+i);
            if (!excludes.isFieldLine(i)) {
                // selectBestFieldForALine(i);//no. it will not combine all of
                // them, but just keeps the lastest piece
                allMatchingFields(i, config.similarityThresholdOfTwoStrings());
            } else {
                // logger.debug("excludes already matched line "+i+" with
                // fieldName "+excludes.matchedField(i));
            }
        }
    }


    /**
     * find all the matching field for each line bigger than a matching
     * threshold
     */
    public void allMatchingFields(final int lineNumber, final double threshold) {
        for (FieldName fieldName : FieldName.values()) {
            if (!excludes.fieldNameIsMatched(fieldName)) {
                double maxScore =  findBiggestMatch(lineFinder.getLine(lineNumber), config.getFieldHeaderMatchStrings(fieldName));
                if (maxScore > threshold) {
                    //fieldNameToValueLine().put(fieldName, parser.parseField(fieldName)); FIXME
                    matchRecord.putFieldLine(fieldName, lineNumber);
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
            // double score=StringCommon.StringCommon.matchHeadScore(lineStr,
            // headers.get(i));
            double score = StringCommon.matchHeadScore(lineStr, headers.get(i));
            if (score > scoreMax) {
                scoreMax = score;
            }
        }
        log.debug("lineStr="+lineStr+", scoreMax="+scoreMax);
        return scoreMax;
    }

}