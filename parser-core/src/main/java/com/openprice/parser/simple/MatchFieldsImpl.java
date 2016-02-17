package com.openprice.parser.simple;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

                System.out.println("$$$$$$  After matchToBranch, parsed fields are :");
                for (ReceiptFieldType field : record.getFieldToValueLine().keySet()) {
                    System.out.println("'"+ field.name() + "' at line "+record.getFieldToValueLine().get(field).getLine() + " : " + record.getFieldToValueLine().get(field).getValue() );
                }
    }

    public static StringDouble matchLineToList(final String line, final List<String> headers){
        return  headers
                .stream()
                .map( header -> {
                    double score=StringCommon.matchStringToHeader(line, header);
                    log.debug("--line= "+line+", header="+header+",score="+score);
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
                    log.debug("--line header= "+lineNoSpaceLowerHead+", header="+headerNoSpaceLower+", matching chars="+ matchingChars);
                    return new StringInt(header, matchingChars);
                })
                .max( Comparator.comparing(strInt -> strInt.getLine()) ).get();
    }

    public static Map<String, Boolean> cleanTextToTreated(
            final MatchedRecord record,
            final ReceiptData receipt,
            final StoreConfig config,
            final StoreParser parser){
        //comparing Total and TotalSold sold separately when necessary.
        //The idea is to compare the length of matching when their levenshtein scores are both bigger than some threshold
        final List<String> headersTotal = config.getFieldHeaderMatchStrings(ReceiptFieldType.Total);
        final List<String> headersTotalSold = config.getFieldHeaderMatchStrings(ReceiptFieldType.TotalSold);

        Map<String, Boolean> result= new HashMap<>();
        receipt
            .getReceiptLines()
            .stream()
            .filter( line -> line.getCleanText().length() > 1 )
            .filter( line -> record.isFieldLine(line.getNumber()))
            .filter(line -> !StringCommon.stringMatchesHead(line.getCleanText().toLowerCase(), "loyalty offer", config.similarityThresholdOfTwoStrings()))//otherwide this could match the total line
            .forEach( line -> {
                StringDouble scoreTotal = matchLineToList(line.getCleanText(), headersTotal);
                StringDouble scoreTotalSold = matchLineToList(line.getCleanText(), headersTotalSold);
                if(scoreTotal.getValue() > config.similarityThresholdOfTwoStrings()
                        && scoreTotalSold.getValue() > config.similarityThresholdOfTwoStrings())
                {
                    //need special treatment for this line
                    StringInt charsTotal= matchLineToListMatchingChars(line.getCleanText(), headersTotal);
                    StringInt charsTotalSold = matchLineToListMatchingChars(line.getCleanText(), headersTotalSold);
                    if(charsTotalSold.getLine() > charsTotal.getLine()){
                        record.putFieldLineValue(ReceiptFieldType.TotalSold,
                                line.getNumber(), parser.parseField(ReceiptFieldType.TotalSold, line));
                    }
                    else{
                        record.putFieldLineValue(ReceiptFieldType.Total,
                                line.getNumber(), parser.parseField(ReceiptFieldType.Total, line));
                    }
                    result.put(line.getCleanText(), true);
                }else{
                    result.put(line.getCleanText(), false);
                }
            });
        return result;
    }

    @Override
    public void matchToHeaders(
            final MatchedRecord record,
            final ReceiptData receipt,
            final StoreConfig config,
            final StoreParser parser) {

        //totalsold and total are treated already
        Map<String, Boolean> totalSoldAndTotalAreTreated = cleanTextToTreated(record, receipt, config, parser);
        for (ReceiptFieldType field : ReceiptFieldType.values()) {
            log.debug("matchToHeader: field="+field);
            if (record.fieldIsMatched(field)){
                log.debug(field+" is alreday matched: "+record.matchedLinesOfField(field));
                continue;
            }

            final List<String> headerPatterns = config.getFieldHeaderMatchStrings(field);
            log.debug("headerPatterns="+headerPatterns);
            if (headerPatterns == null || headerPatterns.isEmpty())
                continue;

            receipt.getReceiptLines()
                   .stream()
                   .filter( line -> line.getCleanText().length() > 1 )
                   .filter(line -> !totalSoldAndTotalAreTreated.get(line.getCleanText()))
                   .filter( line -> {
//                       if(record.isFieldLine(line.getNumber()))
//                           log.debug("line "+line.getOriginalText()+" is already a field line: "+record.matchedFieldsOnLine(line.getNumber()));
                       return !record.isFieldLine(line.getNumber());
                    })
                   .filter(line -> !StringCommon.stringMatchesHead(line.getCleanText().toLowerCase(), "loyalty offer", config.similarityThresholdOfTwoStrings()))//otherwide this could match the total line
                   .filter( line -> {
                Optional<Double> maxScore =
                        headerPatterns
                        .stream()
                        .map( header -> {
                            double score=StringCommon.matchStringToHeader(line.getCleanText(), header);
                            log.debug("--line= "+line.getCleanText()+", header="+header+",score="+score);
                            return score;
                        })
                        .max( Comparator.comparing(score -> score) );
                log.debug("line="+line.getCleanText()+", score="+maxScore.get());
                return maxScore.isPresent() && maxScore.get() > config.similarityThresholdOfTwoStrings();
            })
            .forEach( line -> {
                String value=parser.parseField(field, line);
                log.debug("line=>"+line.getCleanText()+", parsed value is "+value);
                record.putFieldLineValue(field, line.getNumber(), value);
                });
        }
    }
}
