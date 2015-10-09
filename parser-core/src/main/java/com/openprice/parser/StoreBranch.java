package com.openprice.parser;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.openprice.parser.common.ParserUtils;
import com.openprice.parser.common.StringCommon;
import com.openprice.parser.data.Address;
import com.openprice.parser.data.DoubleFieldPair;
import com.openprice.parser.data.ReceiptField;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * this combines StoreNoAddress and Store2
 */
@Slf4j
@Data
@Builder
public class StoreBranch {
    private static final double THRESHOLD = 0.5;
    private static final int NUMFIELDS = 11;
    private static final String SPLITTER = ",";

    private final Address address;
    private final String branchName;// store branch name
    private final String phone;
    private final String ID;// store number
    private final String gstNumber;
    private final String slogan;

    private final Map<ReceiptField, String> fieldToValue = new HashMap<ReceiptField, String>();

    /*
     * Private constructor, so can only get StoreBranch from builder or static builder method.
     */
    private StoreBranch(final Address address, final String branchName,
            final String phone, final String ID, final String gstNumber, final String slogan) {
        this.address = address;
        this.branchName = ParserUtils.cleanField(branchName);
        this.phone = ParserUtils.cleanField(phone);
        this.ID = ParserUtils.cleanField(ID);
        this.gstNumber = ParserUtils.cleanField(gstNumber);
        this.slogan = slogan;
    }

    private void init() {
        // save store branch ground truth data into a map
        addGroundTruthValue(ReceiptField.AddressLine1, address.getLine1());
        addGroundTruthValue(ReceiptField.AddressLine2, address.getLine2());
        addGroundTruthValue(ReceiptField.AddressCity, address.getCity());
        addGroundTruthValue(ReceiptField.AddressProv, address.getProv());
        addGroundTruthValue(ReceiptField.AddressPost, address.getPost());
        addGroundTruthValue(ReceiptField.StoreBranch, branchName);
        addGroundTruthValue(ReceiptField.GstNumber, gstNumber);
        addGroundTruthValue(ReceiptField.Phone, phone);
        addGroundTruthValue(ReceiptField.Slogan, slogan);
    }

    private void addGroundTruthValue(final ReceiptField fieldName, final String value) {
        if (value != null) {
            final String cleanedValue = value.trim();
            if (cleanedValue.length() > 0) {
                fieldToValue.put(fieldName, value.toLowerCase());
            }
        }
    }

    /**
     * Create a StoreBranch object from a text string. Usually it is from branch.csv file
     */
    public static StoreBranch fromString(String line, final String slogan) {
        line = line + " ";// handle the last char being comma
        final String[] w = line.split(SPLITTER);
        if (w.length != NUMFIELDS) {
            for (int i = 0; i < w.length; i++) {
                System.out.println(w[i]);
            }
            throw new RuntimeException("this line is expected to have " + NUMFIELDS + " fields separated by " + SPLITTER
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
                .ID(w[2].trim())
                .gstNumber(w[3].trim())
                .branchName(w[4].trim())
                .slogan(slogan)
                .build();
        storeBranch.init();
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
                            .map(line -> matchBranchFieldScoreSum(line))
                            .reduce(0.0, (acc, element) -> acc + element);
        return sum;
    }

    private double matchBranchFieldScoreSum(final String lineString) {
        double sum = 0.0;
        sum += matchFieldScore(ReceiptField.AddressLine1, lineString);
        sum += matchFieldScore(ReceiptField.AddressLine2, lineString);
        sum += matchFieldScore(ReceiptField.AddressCity, lineString);
        sum += matchFieldScore(ReceiptField.AddressPost, lineString);
        sum += matchFieldScore(ReceiptField.GstNumber, lineString);
        sum += matchFieldScore(ReceiptField.Phone, lineString);
        sum += matchFieldScore(ReceiptField.StoreBranch, lineString);
        return sum;
    }

    private double matchFieldScore(final ReceiptField field, final String lineString) {
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
    public DoubleFieldPair maxFieldMatchScore(final String lineStr) {
        double scoreMax=-1;
        ReceiptField matchedField=null;
        final String lowerStr=lineStr.toLowerCase();
        for(ReceiptField fName : fieldToValue.keySet()) {
            double score= StringCommon.bestSliceMatching(lowerStr, fieldToValue.get(fName));
            if(score>scoreMax){
                scoreMax=score;
                matchedField=fName;
            }
        }
        return new DoubleFieldPair(scoreMax, matchedField, fieldToValue.get(matchedField));
    }

}
