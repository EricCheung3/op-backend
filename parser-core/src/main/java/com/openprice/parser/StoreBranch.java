package com.openprice.parser;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.openprice.common.StringCommon;
import com.openprice.parser.common.ParserUtils;
import com.openprice.parser.data.Address;
import com.openprice.parser.data.ReceiptLineWithScore;

import lombok.Builder;
import lombok.Data;

/**
 * Contains store branch ground truth info.
 *
 */
@Data
@Builder
public class StoreBranch {
    private static final double THRESHOLD = 0.5;

    private final Address address;
    private final String branchName;// store branch name
    private final String phone;
    private final String storeId;// store number
    private final String gstNumber;
    private final String slogan;

    private final Map<ReceiptFieldType, String> fieldToValue = new HashMap<ReceiptFieldType, String>();

    public static StoreBranch EmptyStoreBranch(){
        return StoreBranch.builder()
                .address(Address.defaultAddress())
                .branchName(StringCommon.EMPTY)
                .phone(StringCommon.EMPTY)
                .storeId(StringCommon.EMPTY)
                .gstNumber(StringCommon.EMPTY)
                .slogan(StringCommon.EMPTY)
                .build();
    }

    /*
     * Private constructor, so can only get StoreBranch from builder or static builder method.
     */
    private StoreBranch(final Address address, final String branchName,
            final String phone, final String storeId, final String gstNumber, final String slogan) {
        this.address = address;
        this.branchName = ParserUtils.cleanField(branchName);
        this.phone = ParserUtils.cleanField(phone);
        this.storeId = ParserUtils.cleanField(storeId);
        this.gstNumber = ParserUtils.cleanField(gstNumber);
        this.slogan = slogan;

        // save store branch ground truth data into a map
        if (address != null) {
            addGroundTruthValue(ReceiptFieldType.AddressLine1, address.getLine1());
            addGroundTruthValue(ReceiptFieldType.AddressLine2, address.getLine2());
            addGroundTruthValue(ReceiptFieldType.AddressCity, address.getCity());
            //addGroundTruthValue(ReceiptField.AddressProv, address.getProv()); // province code is too short, may cause parsing error
            addGroundTruthValue(ReceiptFieldType.AddressPost, address.getPost());
        }
        addGroundTruthValue(ReceiptFieldType.StoreID, storeId);
        addGroundTruthValue(ReceiptFieldType.GstNumber, gstNumber);
        addGroundTruthValue(ReceiptFieldType.Phone, phone);
        addGroundTruthValue(ReceiptFieldType.Slogan, slogan);
    }

    private void addGroundTruthValue(final ReceiptFieldType fieldName, final String value) {
        if (value != null) {
            final String cleanedValue = value.trim();
            if (cleanedValue.length() > 2) {
                fieldToValue.put(fieldName, value.toLowerCase());
            }
        }
    }

    /**
     * Create a StoreBranch object from a text string. Usually it is from branch.csv file
     * 0 - Chain Code
     * 1 - Phone
     * 2 - Store ID
     * 3 - GST Number
     * 4 - Branch name
     * 5 - Address Line 1
     * 6 - Address Line 2
     * 7 - Address City
     * 8 - Address Province
     * 9 - Address Country
     * 10 - Address Postal Code
     */
    public static StoreBranch fromString(String line, final String slogan) {
        line = line + " ";// handle the last char being comma
        final String[] w = line.split(StoreBranchConstants.SPLITTER);
        if (w.length != StoreBranchConstants.NUMFIELDS) {
            for (int i = 0; i < w.length; i++) {
                System.out.println(w[i]);
            }
            throw new RuntimeException("this line is expected to have " + StoreBranchConstants.NUMFIELDS + " fields separated by " + StoreBranchConstants.SPLITTER
                    + ", but w.length=" + w.length + ", line is " + line);
        }
        Address add =
                Address
                .builder()
                .line1(w[5].trim())
                .line2(w[6].trim())
                .city(w[7].trim())
                .prov(w[8].trim())
                .country(w[9].trim())
                .post(w[10].trim())
                .build();

        StoreBranch storeBranch =
                StoreBranch
                .builder()
                .address(add)
                .phone(w[1].trim())
                .storeId(w[2].trim())
                .gstNumber(w[3].trim())
                .branchName(w[4].trim())
                .slogan(slogan)
                .build();
        return storeBranch;
    }

    /**
     * compute the match score of this store branch to the given receipt
     *
     * @param receipt
     * @return
     */
    public double matchScore(final ReceiptData receipt) {
        double sum = receipt.lines()
                .map(line -> matchBranchFieldScoreSum(line.getCleanText()))
                .reduce(0.0, (acc, element) -> acc + element);
        return sum;
    }

    private double matchBranchFieldScoreSum(final String lineString) {
        double sum = 0.0;
        sum += matchFieldScore(ReceiptFieldType.AddressLine1, lineString);
        sum += matchFieldScore(ReceiptFieldType.AddressLine2, lineString);
        sum += matchFieldScore(ReceiptFieldType.AddressCity, lineString);
        sum += matchFieldScore(ReceiptFieldType.AddressPost, lineString);
        sum += matchFieldScore(ReceiptFieldType.GstNumber, lineString);
        sum += matchFieldScore(ReceiptFieldType.Phone, lineString);
        sum += matchFieldScore(ReceiptFieldType.StoreBranch, lineString);
        return sum;
    }

    private double matchFieldScore(final ReceiptFieldType field, final String lineString) {
        final String fieldValue = fieldToValue.get(field);
        if (StringUtils.isEmpty(fieldValue) || StringUtils.isEmpty(lineString)) {
            return 0.0;
        }
        final double score = StringCommon.bestSliceMatchingWithNumberPenalized(lineString, fieldValue);
        return (score > THRESHOLD) ? score : 0.0;
    }

    /**
     * get the max score that matches the given string across all the fields
     *
     * YUAN : copied from legacy code StoreMap.maxFieldMatchScore()
     *
     * @param  lineStr given string
     * @return         [description]
     */
    public ReceiptLineWithScore maxFieldMatchScore(final ReceiptLine line) {
        double scoreMax=-1;
        ReceiptFieldType matchedField=null;
        final String lowerStr=line.getCleanText().toLowerCase();
        for(ReceiptFieldType fName : fieldToValue.keySet()) {
            double score= StringCommon.matchStringToSubString(lowerStr, fieldToValue.get(fName));
            if(score>scoreMax){
                scoreMax=score;
                matchedField=fName;
            }
        }
        return new ReceiptLineWithScore(scoreMax, matchedField, line, fieldToValue.get(matchedField));
    }

}
