package com.openprice.parser.simple;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.openprice.common.StringCommon;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.api.MatchFields;
import com.openprice.parser.api.MatchedRecord;
import com.openprice.parser.api.ReceiptData;
import com.openprice.parser.api.StoreConfig;
import com.openprice.parser.api.StoreParser;
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

    @Override
    public void matchToHeaders(
            final MatchedRecord record,
            final ReceiptData receipt,
            final StoreConfig config,
            final StoreParser parser) {
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
                   .filter( line -> {
                       if(record.isFieldLine(line.getNumber()))
                           log.debug("line "+line.getOriginalText()+" is a field line: "+record.matchedFieldsOnLine(line.getNumber()));
                       return !record.isFieldLine(line.getNumber());
                    })
                   .filter(line -> !StringCommon.stringMatchesHead(line.getCleanText().toLowerCase(), "loyalty offer", config.similarityThresholdOfTwoStrings()))//otherwide this could match the total line
                   .filter( line -> {
                Optional<Double> maxScore =
                        headerPatterns
                        .stream()
                        .map( header -> StringCommon.matchStringToHeader(line.getCleanText(), header) )
                        .max( Comparator.comparing(score -> score) );
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
