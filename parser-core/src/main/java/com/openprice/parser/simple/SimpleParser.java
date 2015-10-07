package com.openprice.parser.simple;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.openprice.parser.LineFinder;
import com.openprice.parser.Parser;
import com.openprice.parser.cheapParser.CheapParser;
import com.openprice.parser.data.FieldSet;
import com.openprice.parser.data.ReceiptDebug;
import com.openprice.parser.data.Skip;
import com.openprice.parser.match.MatchToBranch;
import com.openprice.parser.match.MatchToHeader;
import com.openprice.parser.match.MatchedRecord;
import com.openprice.parser.store.ChainRecognizer;
import com.openprice.parser.store.ChainRegistry;
import com.openprice.parser.store.MatchedChain;
import com.openprice.parser.store.StoreBranch;
import com.openprice.parser.store.StoreParser;
import com.openprice.parser.store.StoreParserSelector;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SimpleParser {
    private final ChainRegistry chainRegistry;

    @Inject
    public SimpleParser(final ChainRegistry chainRegistry) {
        this.chainRegistry = chainRegistry;
    }

    public ReceiptDebug parse(final List<String> lines) throws Exception {
        LineFinder lineFinder = LineFinder.fromContentLines(lines);

        // find chain first
        final ChainRecognizer recognizer = new ChainRecognizer(this.chainRegistry);
        MatchedChain matchedChain = recognizer.findChain(lines);

        // get store branch
        StoreBranch branch = matchBranchByScoreSum(lines, matchedChain.getChain().getBranches());

        // get store parser
        StoreParserSelector selector = matchedChain.getChain().getSelector();
        StoreParser parser = selector.selectParser();

        MatchToBranch matchToBranch = new MatchToBranch(lineFinder, branch, matchedChain);
        matchToBranch.allMatchingFields(0.5);

        FieldSet fSetFromBranch = matchToBranch.getFields();
        MatchedRecord matchFromBranch = matchToBranch.getMatchRecord();

        MatchToHeader matchToHeader = new MatchToHeader(parser.storeConfig(), lineFinder, matchFromBranch);
        FieldSet fSetFromHeader = matchToHeader.getFields();
        MatchedRecord matchFromHeader = matchToHeader.getMatchRecord();

        FieldSet fields = FieldSet.addPrefer(fSetFromBranch, fSetFromHeader);
        MatchedRecord matchedRecord = MatchedRecord.add(matchFromBranch, matchFromHeader);

        Skip skip = new Skip(new ArrayList<>(), new ArrayList<>()); // FIXME get skip from config
        Parser receiptParser = new CheapParser(lineFinder, fields, matchedRecord, skip);
        return receiptParser.parseGeneral();
    }

    public StoreBranch matchBranchByScoreSum(final List<String> lines, final List<StoreBranch> branches) {
        double scoreMax=0;
        StoreBranch matchBranch=null;
        for(StoreBranch branch : branches){
            double score=branch.matchScore(lines);
            log.debug("@@@@@for branch " + branch.getBranchName()+", the score="+score+"\n");
            if(score>scoreMax){
                scoreMax=score;
                matchBranch=branch;
            }
        }
        return matchBranch;
    }

}
