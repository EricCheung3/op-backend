package com.openprice.parser.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.openprice.parser.FieldName;
import com.openprice.parser.ParserUtils;
import com.openprice.parser.common.StringCommon;
import com.openprice.parser.data.Address;
import com.openprice.parser.data.DoubleFieldPair;

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
    public static final int NUMFIELDS = 11; // why public?
    private static final String SPLITTER = ",";

    private final Address address;
    private final String branchName;// store branch name
    private final String phone;
    private final String ID;// store number
    private final String gstNumber;
    private final String slogan;

    private final Map<FieldName, String> fieldToValue = new HashMap<FieldName, String>();


    public StoreBranch(final Address address, final String branchName,
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
        addGroundTruthValue(FieldName.AddressLine1, address.getLine1());
        addGroundTruthValue(FieldName.AddressLine2, address.getLine2());
        addGroundTruthValue(FieldName.AddressCity, address.getCity());
        addGroundTruthValue(FieldName.AddressProv, address.getProv());
        addGroundTruthValue(FieldName.AddressPost, address.getPost());
        addGroundTruthValue(FieldName.StoreBranch, branchName);
        addGroundTruthValue(FieldName.GstNumber, gstNumber);
        addGroundTruthValue(FieldName.Phone, phone);
        addGroundTruthValue(FieldName.Slogan, slogan);

    }
    private void addGroundTruthValue(final FieldName fieldName, final String value) {
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
     * compute the match score of this store to the given lines
     *
     * @param lines
     * @return
     */
    public double matchScore(final List<String> lines) {
        double sum = 0.0;
        for (int i = 0; i < lines.size(); i++) {
            double score = matchToALineByFieldScoreSum(lines.get(i), i);
            sum += score;
        }
        return sum;
    }


    // match this store's fields to the given string
    // TODO: should use max match like AddressMatching?
    public double matchToALineByFieldScoreSum(final String s, final int lineNumber) {
        if (s.isEmpty())
            return 0.0;
        double sum = 0.0;
        double tmp = 0.0;
        if (!getAddress().getLine1().isEmpty()) {
            ;
            tmp = StringCommon.bestSliceMatchingWithNumberPenalized(s, getAddress().getLine1());
            if (tmp > THRESHOLD) {
                sum += tmp;
                log.debug("matching line1=" + getAddress().getLine1() + " to string '" + s + "' at line " + lineNumber + ", score="
                        + tmp);
            }
        }

        if (!getAddress().getLine2().isEmpty()) {
            tmp = StringCommon.bestSliceMatchingWithNumberPenalized(s, getAddress().getLine2());
            if (tmp > THRESHOLD) {
                sum += tmp;
                log.debug("matching line2=" + getAddress().getLine2() + " to string '" + s + "' at line " + lineNumber + ", score="
                        + tmp);
            }
        }

        if (!getAddress().getCity().isEmpty()) {
            tmp = StringCommon.bestSliceMatchingWithNumberPenalized(s, getAddress().getCity());
            if (tmp > THRESHOLD) {
                sum += tmp;
                log.debug(
                        "matching city=" + getAddress().getCity() + " to string '" + s + "' at line " + lineNumber + ", score=" + tmp);
            }
        }

        if (!getAddress().getPost().isEmpty()) {
            tmp = StringCommon.bestSliceMatchingWithNumberPenalized(s, getAddress().getPost());
            if (tmp > THRESHOLD) {
                sum += tmp;
                log.debug(
                        "matching post=" + getAddress().getPost() + " to string '" + s + "' at line " + lineNumber + ", score=" + tmp);
            }
        }

        if (!getGstNumber().isEmpty()) {
            tmp = StringCommon.bestSliceMatchingWithNumberPenalized(s, getGstNumber());
            if (tmp > THRESHOLD) {
                sum += tmp;
                log.debug("matching gstNumber=" + getGstNumber() + " to string '" + s + "' at line " + lineNumber
                        + ", score=" + tmp);
            }
        }

        if (!getPhone().isEmpty()) {
            tmp = StringCommon.bestSliceMatchingWithNumberPenalized(s, getPhone());
            if (tmp > THRESHOLD) {
                sum += tmp;
                log.debug("matching phone=" + getPhone() + " to string '" + s + "' at line " + lineNumber + ", score="
                        + tmp);
            }
        }

        if (!getBranchName().isEmpty()) {
            tmp = StringCommon.bestSliceMatchingWithNumberPenalized(s, getBranchName());
            if (tmp > THRESHOLD) {
                sum += tmp;
                log.debug("matching branchName=" + getBranchName() + " to string '" + s + "' at line " + lineNumber
                        + ", score=" + tmp);
            }
        }

        return sum;
    }

    // match this store's fields to the given string by max matching of field
    // FIXME NO USAGE???
//    public double matchToALineByFieldScoreMax(final String s, final int lineNumber) {
//        if (s.isEmpty())
//            return 0.0;
//        double tmp = 0.0;
//        List<Double> scores = new ArrayList<Double>();
//
//        if (!getAddress().getLine1().isEmpty()) {
//            ;
//            tmp = StringCommon.bestSliceMatching(s, getAddress().getLine1());
//            if (tmp > THRESHOLD) {
//                scores.add(tmp);
//                log.debug("matching line1=" + getAddress().getLine1() + " to string '" + s + "' at line " + lineNumber + ", score="
//                        + tmp);
//            }
//        }
//
//        if (!getAddress().getLine2().isEmpty()) {
//            tmp = StringCommon.bestSliceMatching(s, getAddress().getLine2());
//            if (tmp > THRESHOLD) {
//                scores.add(tmp);
//                log.debug("matching line2=" + getAddress().getLine2() + " to string '" + s + "' at line " + lineNumber + ", score="
//                        + tmp);
//            }
//        }
//
//        if (!getAddress().getCity().isEmpty()) {
//            // sum+=Levenshtein.compare(getAddress().getCity(), s);
//            tmp = StringCommon.bestSliceMatching(s, getAddress().getCity());
//
//            if (tmp > THRESHOLD) {
//                scores.add(tmp);
//                log.debug(
//                        "matching city=" + getAddress().getCity() + " to string '" + s + "' at line " + lineNumber + ", score=" + tmp);
//            }
//        }
//
//        if (!getAddress().getPost().isEmpty()) {
//            tmp = StringCommon.bestSliceMatching(s, getAddress().getPost());
//
//            if (tmp > THRESHOLD) {
//                scores.add(tmp);
//                log.debug(
//                        "matching post=" + getAddress().getPost() + " to string '" + s + "' at line " + lineNumber + ", score=" + tmp);
//            }
//        }
//
//        if (!getGstNumber().isEmpty()) {
//            tmp = StringCommon.bestSliceMatching(s, getGstNumber());
//
//            if (tmp > THRESHOLD) {
//                scores.add(tmp);
//                log.debug("matching gstNumber=" + getGstNumber() + " to string '" + s + "' at line " + lineNumber
//                        + ", score=" + tmp);
//            }
//        }
//
//        if (!getPhone().isEmpty()) {
//            tmp = StringCommon.bestSliceMatching(s, getPhone());
//
//            if (tmp > THRESHOLD) {
//                scores.add(tmp);
//                log.debug("matching phone=" + getPhone() + " to string '" + s + "' at line " + lineNumber + ", score="
//                        + tmp);
//            }
//        }
//
//        if (scores.isEmpty())
//            return 0;
//
//        final double scoreMax = Collections.max(scores);
//        log.debug(">>>>>>max field matching score is " + scoreMax);
//        return scoreMax;
//    }

    /**
     * Compare the input text line, return all fields with a match score bigger than the given threshold.
     *
     * YUAN : copied from legacy code StoreMap.allFieldMatchScores()
     *
     * @param lineStr
     * @param threshold
     * @return
     */
    public List<DoubleFieldPair> allFieldMatchScores(final String lineStr, final double threshold) {
        final List<DoubleFieldPair> list=new ArrayList<DoubleFieldPair>();
        final String lowerStr = lineStr.toLowerCase();
        for (FieldName fName : fieldToValue.keySet()) {
            final String value = fieldToValue.get(fName);
            final double score = StringCommon.bestSliceMatching(lowerStr, value);
            if (score > threshold) {
                list.add(new DoubleFieldPair(score, fName, value));
            }
        }
        return list;
    }

    /**
     * get the max score that matches the given string across all the fields
     *
     * YUAN : copied from legacy code StoreMap.maxFieldMatchScore()
     *
     * @param  lineStr given string
     * @return         [description]
     */
    public DoubleFieldPair maxFieldMatchScore(final String lineStr){
        double scoreMax=-1;
        FieldName matchedField=null;
        final String lowerStr=lineStr.toLowerCase();
        for(FieldName fName : fieldToValue.keySet()) {
            double score= StringCommon.bestSliceMatching(lowerStr, fieldToValue.get(fName)); //Levenshtein.compare(lowerStr, fieldToValue.get(fName));
            if(score>scoreMax){
                scoreMax=score;
                matchedField=fName;
            }
        }
        return new DoubleFieldPair(scoreMax, matchedField, fieldToValue.get(matchedField));
    }

}
