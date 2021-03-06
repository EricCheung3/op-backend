package com.openprice.parser.simple;

import org.springframework.util.StringUtils;

import com.openprice.common.StringCommon;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.api.MatchToBranch;
import com.openprice.parser.api.ReceiptData;
import com.openprice.parser.api.ReceiptLine;
import com.openprice.parser.data.ReceiptLineWithScore;
import com.openprice.store.StoreBranch;

import lombok.Data;

/**
 * Contains store branch ground truth info.
 *
 */
@Data
public class MatchToBranchImpl implements  MatchToBranch{
    private static final double THRESHOLD = 0.5;

    /**
     * get the max score that matches the given string across all the fields
     *
     * YUAN : copied from legacy code StoreMap.maxFieldMatchScore()
     *
     * @param  lineStr given string
     * @return         [description]
     */
    public ReceiptLineWithScore maxFieldMatchScore(final StoreBranch branch, final ReceiptLine line) {
        double scoreMax=-1;
        ReceiptFieldType matchedField=null;
        final String lowerStr = line.getCleanText().toLowerCase();
        final BranchFieldLookupImpl lookup=new BranchFieldLookupImpl(branch);
        for(ReceiptFieldType fName : lookup.getFieldToValue().keySet()) {
            //ignore state, postcode, country field (banana will match Canada)
            if(fName==ReceiptFieldType.AddressCountry||
               fName==ReceiptFieldType.AddressCity||
               fName==ReceiptFieldType.AddressPost||
               fName==ReceiptFieldType.AddressState||
               fName==ReceiptFieldType.StoreID)
                continue;
            double score= StringCommon.matchStringToSubString(lowerStr, lookup.getFieldToValue().get(fName));
            if (score > scoreMax) {
                scoreMax = score;
                matchedField = fName;
            }
        }
        return new ReceiptLineWithScore(
                line,
                matchedField,
                scoreMax,
                lookup.getFieldToValue().get(matchedField)
                );
    }

    /**
     * compute the match score of this store branch to the given receipt
     *
     * @param receipt
     * @return
     */
    @Override
    public double matchScore(final StoreBranch storeBranch, final ReceiptData receipt) {
        double sum = receipt
                .getReceiptLines()
                .stream()
                .map(line -> matchBranchFieldScoreSum(new BranchFieldLookupImpl(storeBranch), line.getCleanText()))
                .reduce(0.0, (acc, element) -> acc + element);
        return sum;
    }

    private static double matchBranchFieldScoreSum(final BranchFieldLookupImpl lookup, final String lineString) {
        double sum = 0.0;
        sum += matchFieldScore(lookup.valueOf(ReceiptFieldType.AddressLine1), lineString);
        sum += matchFieldScore(lookup.valueOf(ReceiptFieldType.AddressLine2), lineString);
//        sum += matchFieldScore(lookup.valueOf(ReceiptFieldType.AddressCity), lineString);
//        sum += matchFieldScore(lookup.valueOf(ReceiptFieldType.AddressPost), lineString);
//        sum += matchFieldScore(lookup.valueOf(ReceiptFieldType.GstNumber), lineString);
        sum += matchFieldScore(lookup.valueOf(ReceiptFieldType.Phone), lineString);
        sum += matchFieldScore(lookup.valueOf(ReceiptFieldType.StoreBranch), lineString);
        return sum;
    }

    private static double matchFieldScore(final String fieldValue, final String lineString) {
        //TODO what about the two are both empty?
        if (StringUtils.isEmpty(fieldValue) || StringUtils.isEmpty(lineString)) {
            return 0.0;
        }
        final double score = StringCommon.bestSliceMatchingWithNumberPenalized(lineString, fieldValue);
        return (score > THRESHOLD) ? score : 0.0;
    }
}
