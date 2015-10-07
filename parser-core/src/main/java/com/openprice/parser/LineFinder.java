package com.openprice.parser;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.openprice.parser.common.Levenshtein;
import com.openprice.parser.common.StringCommon;
import com.openprice.parser.exception.HeaderOfLineNotFoundException;
import com.openprice.parser.exception.LineNumberOutOfRangeException;
import com.openprice.parser.exception.ReceiptTooShortException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * a line search util class.
 */
@Slf4j
public class LineFinder {
    // minimum number of lines in a receipt
    public static final int MIN_NUMBER_LINES = 5;

    private static final double LEVENSHTEIN_THRESHOLD = 0.4;// threshold for being similar for strings under Levenshtein

    @Getter
    private final List<String> lines;

    private LineFinder(final List<String> lines) {
        this.lines = ImmutableList.copyOf(lines);
    }

    public static LineFinder fromContentLines(final List<String> lines) throws Exception {
        LineFinder f = new LineFinder(lines);
        if (lines.size() < MIN_NUMBER_LINES) {
            throw new ReceiptTooShortException("Receipt is too short, only has " + lines.size() + " lines.");
        }
        return f;
    }

    public static LineFinder fromString(final String allLines) throws Exception {
        String[] lines = allLines.split("\n");
        return fromContentLines(java.util.Arrays.asList(lines));
    }

    public String getLine(final int lineNumber) {
        return lines.get(lineNumber);
    }

    public void lineNumberCheck(int lineNumber) throws Exception {
        if (!(lineNumber >= 0 && lineNumber < getLines().size())) {
            throw new LineNumberOutOfRangeException("line number check fails. line number is " + lineNumber);
        }
    }

    public int byHeaderString(String head, final double threshold) throws HeaderOfLineNotFoundException {
        return byHeaderString(head, threshold, 0);
    }

    // considering lines starting like "GST /HST" "GST/ HST" "GST / HST" should
    // match a given head "GST/HST"
    // multiple line version of StringCommon.stringMatches. considering rewrite
    // using that method
    public int byHeaderString(String head, final double threshold, final int start) throws HeaderOfLineNotFoundException {
        head = head.replaceAll("\\s+", "");
        for (int i = start; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            String lineHead = StringCommon.firstNonSpaceChars(line, head.length());
            if (lineHead.length() != head.length())
                continue;
            if (Levenshtein.compare(lineHead, head) > threshold) {
                return i;
            }
        }
        throw new HeaderOfLineNotFoundException("no line found starting with header '" + head + "'");
    }

    public int byFirstStringIgnoreCase(String s, int start) {
        return byFirstStringIgnoreCase(s, start, lines.size());
    }

    public int byFirstStringIgnoreCase(String s, int start, int end) {
        s = s.toLowerCase();
        // assert start<end && end<=lines.size() && start>=0;
        if (start < 0 || end < 0 || (end <= start) || end > lines.size())
            return Integer.MIN_VALUE;
        int found = Integer.MIN_VALUE;
        double max = Double.NEGATIVE_INFINITY;
        for (int i = start; i < end; i++) {
            String[] tmp = lines.get(i).trim().toLowerCase().split("\\s{5,}");
            double score = Levenshtein.compare(s, StringCommon.getOnlyLettersDigits(tmp[0]));
            if (score > max) {
                max = score;
                found = i;
            }
            if (score == 1.0) {
                break;
            }
        }
        if (max >= LEVENSHTEIN_THRESHOLD)
            log.debug("######found " + s + " line at line: " + lines.get(found));
        else
            found = Integer.MIN_VALUE;
        return found;
    }

    // delimitor is any number of white space
    public int byFirstStringSpace(String s, int start) {
        return byFirstStringSpace(s, 0, lines.size());
    }

    public int byFirstStringSpace(String s, int start, int end) {
        // assert start<end && end<lines.size() && start>=0;
        // assert start>=0 && end>=0;
        if (start < 0 || end < 0 || (end <= start) || end > lines.size())
            return Integer.MIN_VALUE;
        int found = Integer.MIN_VALUE;
        ;
        double max = Double.NEGATIVE_INFINITY;
        for (int i = start; i < end; i++) {
            String[] tmp = lines.get(i).trim().split("\\s+");
            // double score=Levenshtein.compare(s,
            // StringCommon.getOnlyLetters(tmp[0]));
            double score = Levenshtein.compare(s, StringCommon.getOnlyLettersDigits(tmp[0]));
            if (score > max) {
                max = score;
                found = i;
            }
            if (score == 1.0) {
                break;
            }
        }
        if (max >= LEVENSHTEIN_THRESHOLD)
            log.debug("######found " + s + " line at line: " + lines.get(found) + ", score=" + max);
        else
            found = Integer.MIN_VALUE;
        return found;
    }

    public int byFirstString(String s) {
        return byFirstString(s, 0);
    }

    public int byFirstString(String s, int start) {
        return byFirstString(s, start, lines.size());
    }

    public int byFirstString(String s, int start, int end) {
        // assert start<end && end<=lines.size() && start>=0;
        if (start < 0 || end < 0 || (end <= start) || end > lines.size())
            return Integer.MIN_VALUE;
        int found = Integer.MIN_VALUE;
        double max = Double.NEGATIVE_INFINITY;
        for (int i = start; i < end; i++) {
            String[] tmp = lines.get(i).trim().split("\\s{5,}");
            System.out.println("line is " + lines.get(i));
            for (String strtmp : tmp)
                System.out.println(strtmp);

            // double score=Levenshtein.compare(s,
            // StringCommon.getOnlyLetters(tmp[0]));
            double score = Levenshtein.compare(s, StringCommon.getOnlyLettersDigits(tmp[0]));
            if (score > max) {
                max = score;
                found = i;
            }
            if (score == 1.0) {
                break;
            }
        }
        if (max >= LEVENSHTEIN_THRESHOLD)
            log.debug("######found " + s + " line at line: " + lines.get(found));
        else
            found = Integer.MIN_VALUE;
        return found;
    }

    public int byFirstTwoStrings(String s) {
        return byFirstTwoStrings(s, 0);
    }

    public int byFirstTwoStrings(String s, int start) {
        // assert start<lines.size() && start>=0;
        if (start < 0 || start > lines.size())
            return Integer.MIN_VALUE;
        int found = Integer.MIN_VALUE;
        double max = Double.NEGATIVE_INFINITY;
        for (int i = start; i < lines.size(); i++) {
            String[] tmp = lines.get(i).trim().split("\\s{5,}");
            if (tmp.length < 2)
                continue;
            // double score=Levenshtein.compare(s,
            // StringCommon.getOnlyLetters(tmp[0]+tmp[1]));
            double score = Levenshtein.compare(s, StringCommon.getOnlyLettersDigits(tmp[0] + tmp[1]));
            if (score > max) {
                max = score;
                found = i;
                if (score == 1.0) {
                    break;
                }
            }
        }
        if (max >= LEVENSHTEIN_THRESHOLD)
            log.debug("######found " + s + " line at line: " + lines.get(found));
        return found;
    }

    public int byFirstTwoStringsAnd(String s, String symbol) {
        return byFirstTwoStringsAnd(s, symbol, 0);
    }

    public int byFirstTwoStringsAnd(String s, String symbol, int start) {
        // assert start<lines.size() && start>=0;
        if (start < 0)
            return Integer.MIN_VALUE;
        int found = Integer.MIN_VALUE;
        ;
        double max = Double.NEGATIVE_INFINITY;
        for (int i = start; i < lines.size(); i++) {
            String[] tmp = lines.get(i).trim().split("\\s{5,}");
            if (tmp.length < 2)
                continue;
            // double score=Levenshtein.compare(s,
            // StringCommon.getOnlyLettersAnd(tmp[0]+tmp[1], symbol));
            double score = Levenshtein.compare(s, StringCommon.getOnlyLettersDigitsAnd(tmp[0] + tmp[1], symbol));
            if (score > max) {
                max = score;
                found = i;
                if (score == 1.0) {
                    break;
                }
            }
        }
        // log.debug("found "+ s+" line at line: "+lines.get(found));
        if (max >= LEVENSHTEIN_THRESHOLD)
            log.debug("######found " + s + " line at line: " + lines.get(found));
        return found;
    }

    public int findSubtotalLine() {
        return findSubtotalLine(0);
    }

    public int findSubtotalLine(int start) {
        return byFirstString("SUBTOTAL", start);
    }

    public int findSubtotalLineIgnoreCase() {
        return findSubtotalLineIgnoreCase(0);
    }

    public int findSubtotalLineIgnoreCase(int start) {
        return byFirstStringIgnoreCase("Subtotal", start);
    }

    public int findTotalLine() {
        return findTotalLine(0);
    }

    public int findTotalLine(int start) {
        return byFirstString("TOTAL", start);
    }

    public int findTotalLineIgnoreCase() {
        return findTotalLineIgnoreCase(0);
    }

    public int findTotalLineIgnoreCase(int start) {
        return byFirstStringIgnoreCase("Total", start);
    }

    /*
     * get store info from head lines of receipt
     */
    public List<String> headLineFromReceipt(final int numHeadLines) {
        List<String> h = new ArrayList<String>();
        for (int i = 0; i < getLines().size() && h.size() < numHeadLines; i++) {
            // log.debug("line:"+lines.get(i));
            if (ignoreLine(i))
                continue;
            String tmp = getLines().get(i).trim();
            int[] counts = StringCommon.countDigitAndChars(tmp);
            if (counts[1] > 3) {
                h.add(tmp);
                // log.debug("this line is valid");
            }
        }
        return h;
    }

    /*
     * ignore this line if too few meaningful letters and digits
     */
    public boolean ignoreLine(final int lineNumber) {
        String str = getLines().get(lineNumber).replaceAll("\\s+", "");
        if (str.length() < 5)
            return true;
        String sub = StringCommon.getOnlyLettersDigits(str);
        if (sub.length() < 5)
            return true;

        return false;
    }

}
