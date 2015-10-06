package com.openprice.parser.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.openprice.parser.ParserUtils;
import com.openprice.parser.common.FileCommonT;
import com.openprice.parser.common.StringCommon;
import com.openprice.parser.data.Address;

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
    // private final String chainName;//like "Safeway
    private final StoreChain chain;
    private final String phone;
    private final String ID;// store number
    private final String gstNumber;
    private final String slogan;

    public StoreBranch(final Address address, final String branchName, final StoreChain chain,
            final String phone, final String ID, final String gstNumber, final String slogan) {
        this.address = address;
        this.branchName = ParserUtils.cleanField(branchName);
        this.chain = chain;
        this.phone = ParserUtils.cleanField(phone);
        this.ID = ParserUtils.cleanField(ID);
        this.gstNumber = ParserUtils.cleanField(gstNumber);
        this.slogan = slogan;
    }

    /*
     * @param line is usually a line from file
     */
    public static StoreBranch fromString(String line, final String slogan) throws Exception {
        line = line + " ";// handle the last char being comma
        final String[] w = line.split(SPLITTER);
        if (w.length != NUMFIELDS) {
            for (int i = 0; i < w.length; i++) {
                System.out.println(w[i]);
            }
            throw new Exception("this line is expected to have " + NUMFIELDS + " fields separated by " + SPLITTER
                    + ", but w.length=" + w.length + ", line is " + line);
        }
        Address add = Address.builder().line1(w[5].trim()).line2(w[6].trim()).city(w[7].trim()).prov(w[8].trim())
                .country(w[9].trim()).post(w[10].trim()).build();
        // System.out.println("add="+add);
        StoreBranch st =
                StoreBranch
                .builder()
                .address(add)
                // .chainName(w[0].trim())
                .phone(w[1].trim()).ID(w[2].trim()).gstNumber(w[3].trim()).branchName(w[4].trim()).slogan(slogan)
                .build();
        return st;
    }

    public static List<StoreBranch> fromFile(final String dir, final String fileName, final String slogan)
            throws Exception {
        List<String> lines = FileCommonT.readLinesFromResourceSkipEmpty(dir, fileName);
        List<StoreBranch> list = new ArrayList<StoreBranch>();
        for (int i = 0; i < lines.size(); i++) {
            if (!lines.get(i).isEmpty() && !lines.get(i).startsWith("#"))
                list.add(StoreBranch.fromString(lines.get(i), slogan));
        }
        return list;
    }

    /**
     * compute the match score of this store to the given lines
     *
     * @param lines
     * @return
     */
    public double matchScore(final List<String> lines) {
        double sum = 0.0;
        // int count=0;
        for (int i = 0; i < lines.size(); i++) {
            double score = matchToALineByFieldScoreSum(lines.get(i), i);
            // double score=matchToALineByFieldScoreMax(lines.get(i), i);
            // if(score>0.3)
            // log.debug("\n big score. ---for line "+s+" score is "+score);
            sum += score;
            // count++;

            // if(count>=4)
            // break;
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
            // sum+=Levenshtein.compare(getAddress().getLine1(), s);
            // tmp=StringCommon.bestSliceMatching(s, getAddress().getLine1());
            tmp = StringCommon.bestSliceMatchingWithNumberPenalized(s, getAddress().getLine1());
            if (tmp > THRESHOLD) {
                sum += tmp;
                log.debug("matching line1=" + getAddress().getLine1() + " to string s=" + s + " at line " + lineNumber + ", score="
                        + tmp);
            }
        }

        if (!getAddress().getLine2().isEmpty()) {
            // sum+=Levenshtein.compare(getAddress().getLine2(), s);
            // tmp=StringCommon.bestSliceMatching(s, getAddress().getLine2());
            tmp = StringCommon.bestSliceMatchingWithNumberPenalized(s, getAddress().getLine2());
            if (tmp > THRESHOLD) {
                sum += tmp;
                log.debug("matching line2=" + getAddress().getLine2() + " to string s=" + s + " at line " + lineNumber + ", score="
                        + tmp);
            }
        }

        if (!getAddress().getCity().isEmpty()) {
            // sum+=Levenshtein.compare(getAddress().getCity(), s);
            // tmp=StringCommon.bestSliceMatching(s, getAddress().getCity());
            tmp = StringCommon.bestSliceMatchingWithNumberPenalized(s, getAddress().getCity());
            if (tmp > THRESHOLD) {
                sum += tmp;
                log.debug(
                        "matching city=" + getAddress().getCity() + " to string s=" + s + " at line " + lineNumber + ", score=" + tmp);
            }
        }

        if (!getAddress().getPost().isEmpty()) {
            // tmp=StringCommon.bestSliceMatching(s, getAddress().getPost());
            tmp = StringCommon.bestSliceMatchingWithNumberPenalized(s, getAddress().getPost());
            if (tmp > THRESHOLD) {
                sum += tmp;
                log.debug(
                        "matching post=" + getAddress().getPost() + " to string s=" + s + " at line " + lineNumber + ", score=" + tmp);
            }
        }
        // if(!prov().isEmpty())
        // sum+=Levenshtein.compare(getAddress().getCity(), s);

        if (!getGstNumber().isEmpty()) {
            // sum+=Levenshtein.compare(getGstNumber(), s);
            // tmp=StringCommon.bestSliceMatching(s, getGstNumber());
            tmp = StringCommon.bestSliceMatchingWithNumberPenalized(s, getGstNumber());
            if (tmp > THRESHOLD) {
                sum += tmp;
                log.debug("matching gstNumber=" + getGstNumber() + " to string s=" + s + " at line " + lineNumber
                        + ", score=" + tmp);
            }
        }

        if (!getPhone().isEmpty()) {
            // sum+=Levenshtein.compare(getPhone(), s);
            // tmp=StringCommon.bestSliceMatching(s, getPhone());
            tmp = StringCommon.bestSliceMatchingWithNumberPenalized(s, getPhone());
            if (tmp > THRESHOLD) {
                sum += tmp;
                log.debug("matching phone=" + getPhone() + " to string s=" + s + " at line " + lineNumber + ", score="
                        + tmp);
            }
        }

        if (!getBranchName().isEmpty()) {
            // sum+=Levenshtein.compare(getPhone(), s);
            // tmp=StringCommon.bestSliceMatching(s, branchName());
            tmp = StringCommon.bestSliceMatchingWithNumberPenalized(s, getBranchName());
            log.debug("\n\nbranchName is " + getBranchName() + "\ns=" + s + ", score=" + tmp);
            if (tmp > THRESHOLD) {
                sum += tmp;
                log.debug("matching branchName=" + getBranchName() + " to string s=" + s + " at line " + lineNumber
                        + ", score=" + tmp);
            }
        }

        return sum;
    }

    // match this store's fields to the given string by max matching of field
    public double matchToALineByFieldScoreMax(final String s, final int lineNumber) {
        if (s.isEmpty())
            return 0.0;
        double tmp = 0.0;
        List<Double> scores = new ArrayList<Double>();

        if (!getAddress().getLine1().isEmpty()) {
            ;
            // sum+=Levenshtein.compare(getAddress().getLine1(), s);
            tmp = StringCommon.bestSliceMatching(s, getAddress().getLine1());
            if (tmp > THRESHOLD) {
                scores.add(tmp);
                log.debug("matching line1=" + getAddress().getLine1() + " to string s=" + s + " at line " + lineNumber + ", score="
                        + tmp);
            }
        }

        if (!getAddress().getLine2().isEmpty()) {
            // sum+=Levenshtein.compare(getAddress().getLine2(), s);
            tmp = StringCommon.bestSliceMatching(s, getAddress().getLine2());
            if (tmp > THRESHOLD) {
                scores.add(tmp);
                log.debug("matching line2=" + getAddress().getLine2() + " to string s=" + s + " at line " + lineNumber + ", score="
                        + tmp);
            }
        }

        if (!getAddress().getCity().isEmpty()) {
            // sum+=Levenshtein.compare(getAddress().getCity(), s);
            tmp = StringCommon.bestSliceMatching(s, getAddress().getCity());

            if (tmp > THRESHOLD) {
                scores.add(tmp);
                log.debug(
                        "matching city=" + getAddress().getCity() + " to string s=" + s + " at line " + lineNumber + ", score=" + tmp);
            }
        }

        if (!getAddress().getPost().isEmpty()) {
            tmp = StringCommon.bestSliceMatching(s, getAddress().getPost());

            if (tmp > THRESHOLD) {
                scores.add(tmp);
                log.debug(
                        "matching post=" + getAddress().getPost() + " to string s=" + s + " at line " + lineNumber + ", score=" + tmp);
            }
        }
        // if(!prov().isEmpty())
        // sum+=Levenshtein.compare(getAddress().getCity(), s);

        if (!getGstNumber().isEmpty()) {
            // sum+=Levenshtein.compare(getGstNumber(), s);
            tmp = StringCommon.bestSliceMatching(s, getGstNumber());

            if (tmp > THRESHOLD) {
                scores.add(tmp);
                log.debug("matching gstNumber=" + getGstNumber() + " to string s=" + s + " at line " + lineNumber
                        + ", score=" + tmp);
            }
        }

        if (!getPhone().isEmpty()) {
            // sum+=Levenshtein.compare(getPhone(), s);
            tmp = StringCommon.bestSliceMatching(s, getPhone());

            if (tmp > THRESHOLD) {
                scores.add(tmp);
                log.debug("matching phone=" + getPhone() + " to string s=" + s + " at line " + lineNumber + ", score="
                        + tmp);
            }
        }

        if (scores.isEmpty())
            return 0;

        final double scoreMax = Collections.max(scores);
        log.debug(">>>>>>max field matching score is " + scoreMax);
        return scoreMax;
    }
}
