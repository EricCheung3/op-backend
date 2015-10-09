package com.openprice.parser.simple;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.openprice.parser.LineFinder;
import com.openprice.parser.cheapParser.CheapParser;
import com.openprice.parser.data.Item;
import com.openprice.parser.store.ChainRecognizer;
import com.openprice.parser.store.ChainRegistry;
import com.openprice.parser.store.MatchedChain;
import com.openprice.parser.store.MatchedRecord;
import com.openprice.parser.store.ParsedReceipt;
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

    public ParsedReceipt parse(final List<String> lines) throws Exception {
        LineFinder lineFinder = LineFinder.fromContentLines(lines);

        // find chain first
        final ChainRecognizer recognizer = new ChainRecognizer(this.chainRegistry);
        MatchedChain matchedChain = recognizer.findChain(lines);

        // get store branch
        StoreBranch branch = matchBranchByScoreSum(lines, matchedChain.getChain().getBranches());

        // get store parser
        StoreParserSelector selector = matchedChain.getChain().getSelector();
        StoreParser parser = selector.selectParser();
        MatchedRecord matchedRecord = new MatchedRecord();
        matchedRecord.matchToBranch(lineFinder, branch);
        matchedRecord.matchToHeader(lineFinder, parser.getStoreConfig(), parser);

        CheapParser receiptParser = new CheapParser(lineFinder, matchedRecord, parser.getStoreConfig().getSkip());
        List<Item> items = receiptParser.parseGeneral();

        return ParsedReceipt.builder().branch(branch).items(items).build();
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
