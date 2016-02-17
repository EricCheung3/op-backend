package com.openprice.parser.simple;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.openprice.common.Levenshtein;
import com.openprice.common.StringCommon;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.api.MatchFields;
import com.openprice.parser.api.MatchedRecord;
import com.openprice.parser.api.ReceiptData;
import com.openprice.parser.api.StoreConfig;
import com.openprice.parser.api.StoreParser;
import com.openprice.parser.data.StringDouble;
import com.openprice.parser.data.StringInt;
import com.openprice.store.StoreBranch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatchFieldsImpl implements MatchFields{

    @Override
    public void matchToBranch(
            final MatchedRecord record,
            final ReceiptData receipt,
            final StoreBranch storeBranch) {
        receipt.getReceiptLines()
               .stream()
               .filter( line -> line.getCleanText().length() > 2 )
               .map( line -> new MatchToBranchImpl().maxFieldMatchScore(storeBranch, line) )
               .filter( lineScore -> lineScore.getScore() > 0.5)
               .forEach( lineScore -> record.putFieldLineValue(lineScore.getField(), lineScore.getReceiptLine().getNumber(), lineScore.getValue()));

//                System.out.println("$$$$$$  After matchToBranch, parsed fields are :");
//                for (ReceiptFieldType field : record.getFieldToValueLine().keySet()) {
//                    System.out.println("'"+ field.name() + "' at line "+record.getFieldToValueLine().get(field).getLine() + " : " + record.getFieldToValueLine().get(field).getValue() );
//                }
    }

    private static StringDouble matchLineToList(final String line, final List<String> headers){
        return  headers
                .stream()
                .map( header -> {
                    double score=StringCommon.matchStringToHeader(line, header);
                    return new StringDouble(header, score);
                })
                .max( Comparator.comparing(score -> score.getValue()) ).get();
    }

    //based on matching characters
    public static StringInt matchLineToListMatchingChars(final String line, final List<String> headers){
        return  headers
                .stream()
                .map( header -> {
                    String lineNoSpaceLower=StringCommon.removeAllSpaces(line).toLowerCase();
                    String headerNoSpaceLower=StringCommon.removeAllSpaces(header).toLowerCase();
                    String lineNoSpaceLowerHead=lineNoSpaceLower;
                    if(lineNoSpaceLower.length() > headerNoSpaceLower.length())
                        lineNoSpaceLowerHead = lineNoSpaceLower.substring(0, headerNoSpaceLower.length());
                    int matchingChars = Levenshtein.matchingChars(lineNoSpaceLowerHead, headerNoSpaceLower);
//                    log.debug("--line header= "+lineNoSpaceLowerHead+", header="+headerNoSpaceLower+", matching chars="+ matchingChars);
                    return new StringInt(header, matchingChars);
                })
                .max( Comparator.comparing(strInt -> strInt.getLine()) ).get();
    }

    /*
     * comparing Total and TotalSold sold separately when necessary.
     * The idea is to compare the length of matching when their levenshtein scores are both bigger than some threshold
     */
    public static Set<Integer> cleanTextToTreated(
            final MatchedRecord record,
            final ReceiptData receipt,
            final StoreParser parser){
        final StoreConfig  config = parser.getStoreConfig();
        final List<String> headersTotal = config.getFieldHeaderMatchStrings(ReceiptFieldType.Total);
        final List<String> headersTotalSold = config.getFieldHeaderMatchStrings(ReceiptFieldType.TotalSold);
        final Set<Integer> result= new HashSet<>();
        receipt
            .getReceiptLines()
            .stream()
            .filter( line -> line.getCleanText().length() > 1)
//            .filter( line -> record.isFieldLine(line.getNumber()))
            //treatment for total line
            .filter(line ->
                       !StringCommon.stringMatchesHead(line.getCleanText().toLowerCase(), "TOTAL DISCOUNTCS", config.similarityThresholdOfTwoStrings())
                    && !StringCommon.stringMatchesHead(line.getCleanText().toLowerCase(), "loyalty offer", config.similarityThresholdOfTwoStrings())
                    && !StringCommon.stringMatchesHead(line.getCleanText().toLowerCase(), "Total Savings Value", config.similarityThresholdOfTwoStrings())
            )
            .forEach( line -> {
                StringDouble scoreTotal = matchLineToList(line.getCleanText(), headersTotal);
                StringDouble scoreTotalSold = matchLineToList(line.getCleanText(), headersTotalSold);
//                log.debug(line.getCleanText()+" @ total score="+scoreTotal.toString());
//                log.debug(line.getCleanText()+" @ totalSold score="+scoreTotalSold.toString());
                if(scoreTotal.getValue() > config.similarityThresholdOfTwoStrings()
                        && scoreTotalSold.getValue() > config.similarityThresholdOfTwoStrings())
                {
//                    log.debug("!!!!total and totalsold. need special treatment for :"+line.getCleanText());
                    StringInt charsTotal= matchLineToListMatchingChars(line.getCleanText(), headersTotal);
                    StringInt charsTotalSold = matchLineToListMatchingChars(line.getCleanText(), headersTotalSold);
//                    log.debug(line.getCleanText()+"@ total matching chars: "+charsTotal.toString());
//                    log.debug(line.getCleanText()+"@ totalSold matching chars: "+charsTotalSold.toString());
                    if(charsTotalSold.getLine() > charsTotal.getLine()){
                        final String totalSoldValue=parser.parseField(ReceiptFieldType.TotalSold, line);
//                        log.debug("parsedValue for total sold =" + totalSoldValue);
                        record.putFieldLineValue(ReceiptFieldType.TotalSold, line.getNumber(), totalSoldValue);
                    }
                    else{
                        final String totalValue=parser.parseField(ReceiptFieldType.Total, line);
                        record.putFieldLineValue(ReceiptFieldType.Total, line.getNumber(), totalValue);
                    }
                    result.add(line.getNumber());
                }
            });
        return result;
    }

    @Override
    public void matchToHeaders(
            final MatchedRecord record,
            final ReceiptData receipt,
            final StoreParser parser) {
        final StoreConfig config = parser.getStoreConfig();
        //totalsold and total are treated already
        final Set<Integer> totalSoldAndTotalAreTreated = cleanTextToTreated(record, receipt, parser);
        for (ReceiptFieldType field : ReceiptFieldType.values()) {
//            log.debug("matchToHeader: field="+field);
            if (record.fieldIsMatched(field)){
                log.debug(field+" is alreday matched: "+record.matchedLinesOfField(field));
                continue;
            }

            final List<String> headerPatterns = config.getFieldHeaderMatchStrings(field);
//            log.debug("headerPatterns="+headerPatterns);
            if (headerPatterns == null || headerPatterns.isEmpty())
                continue;

            receipt.getReceiptLines()
                   .stream()
                   .filter( line -> line.getCleanText().length() > 1 )
                   .filter(line -> !totalSoldAndTotalAreTreated.contains(line.getNumber()))
//                   .filter( line -> {
////                       if(record.isFieldLine(line.getNumber()))
////                           log.debug("line "+line.getOriginalText()+" is already a field line: "+record.matchedFieldsOnLine(line.getNumber()));
//                       return !record.isFieldLine(line.getNumber());
//                    })//just let each field matching to its favourite line
                   .filter(line ->
                              !StringCommon.stringMatchesHead(line.getCleanText().toLowerCase(), "TOTAL DISCOUNTCS", config.similarityThresholdOfTwoStrings())
                           && !StringCommon.stringMatchesHead(line.getCleanText().toLowerCase(), "loyalty offer", config.similarityThresholdOfTwoStrings())
                           && !StringCommon.stringMatchesHead(line.getCleanText().toLowerCase(), "Total Savings Value", config.similarityThresholdOfTwoStrings())
                   ).filter( line -> {
                Optional<Double> maxScore =
                        headerPatterns
                        .stream()
                        .map( header -> {
                            double score=StringCommon.matchStringToHeader(line.getCleanText(), header);
//                            log.debug("--line= "+line.getCleanText()+", header="+header+",score="+score);
                            return score;
                        })
                        .max( Comparator.comparing(score -> score) );
//                log.debug("line="+line.getCleanText()+", score="+maxScore.get());
                return maxScore.isPresent() && maxScore.get() > config.similarityThresholdOfTwoStrings();
            })
            .forEach( line -> {
                String value=parser.parseField(field, line);
//                log.debug("line=>"+line.getCleanText()+", parsed value is "+value);
                record.putFieldLineValue(field, line.getNumber(), value);
                });
        }
    }
}
