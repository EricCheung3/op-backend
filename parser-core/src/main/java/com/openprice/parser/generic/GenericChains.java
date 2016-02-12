package com.openprice.parser.generic;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openprice.common.Levenshtein;
import com.openprice.common.StringCommon;
import com.openprice.common.TextResourceUtils;
import com.openprice.parser.data.StringDouble;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
public class GenericChains {

    // use a large values to search all lines from the end
    private static final int NUM_SEARCHED_LINES_AT_END = 6;
    private static final int NUM_SEARCHED_LINES_AT_BEGIN = 6;

    // splitter used in a line in file chain.list
    private final static String CHAINLINE_SPLITTER = ":";
    private final static String SECONDLEVEL_SPLITTER = ",";// the splitter used inside a field between :

    private static final double CHAIN_SIMILARITY_SCORE=0.7;

    final List<String> chainLines;

    public GenericChains(final List<String> chainList){
        this.chainLines=chainList;
    }

    public GenericChains(final String resourceFile){
        this(TextResourceUtils.loadStringArray(resourceFile));
    }

    //TODO "Costco" appears in the middle of receipt
    public String findChain(final List<String> lines){
        if (lines == null || lines.isEmpty())
            return StringCommon.EMPTY;

        // find the meaningful lines in the beginning and end
        int begin = -1;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            int[] counts = StringCommon.countDigitAndChars(line);
            if (counts[1] > 2) {
                begin = i;
                break;
            }
        }

        // fast mode: searching a few number of lines from beginning.
        StringDouble chainBegin = chainNameSearch(lines, begin, begin + NUM_SEARCHED_LINES_AT_BEGIN);
        if (chainBegin.getValue() > 0.75)
            return chainBegin.getStr();

        int end = -1;
        for (int i = lines.size() - 1; i >= 0; i--) {
            String line = lines.get(i).trim();
            int[] counts = StringCommon.countDigitAndChars(line);
            if (counts[1] > 2) {
                end = i;
                break;
            }
        }
        StringDouble chainEnd = chainNameSearch(lines, end - NUM_SEARCHED_LINES_AT_END, end);
        log.debug("#####searching from head: chain=" + chainBegin.getStr() + ", score=" + chainBegin.getValue());
        log.debug("#####searching from End: chain=" + chainEnd.getStr() + ", score=" + chainEnd.getValue());

        //prefer finding in the begin and end, and then middle
        if (chainEnd.getValue() > chainBegin.getValue() &&
            chainEnd.getValue() > CHAIN_SIMILARITY_SCORE)
            return chainEnd.getStr();

        if (chainBegin.getValue() > chainEnd.getValue() &&
            chainBegin.getValue() > CHAIN_SIMILARITY_SCORE)
            return chainBegin.getStr();

        StringDouble chainMiddle = chainNameSearch(lines, begin + NUM_SEARCHED_LINES_AT_BEGIN + 1, end- NUM_SEARCHED_LINES_AT_END - 1);
        log.debug("#####searching in the middle: chain=" + chainMiddle.getStr() + ", score=" + chainMiddle.getValue());
        if (chainMiddle.getValue() > CHAIN_SIMILARITY_SCORE){
            return chainMiddle.getStr();
        }
        return StringCommon.EMPTY;
    }

    /*
     * detect which chain the store between begin and end
     * @param begin the begin line number
     * @param end the end line number
     * @return an StringDouble object, the first is matched chain name, the second is the score
     */
    private StringDouble chainNameSearch(final List<String> lines, final int begin, final int end) {
        double maxScore = -1;
        String chainName = "";
        for (int i = Math.max(0, begin); i <= Math.min(lines.size() - 1, end); i++) {
            final String line = lines.get(i).trim();
            int[] counts = StringCommon.countDigitAndChars(line);
            if (counts[1] < 2)
                continue;

            for (int c = 0; c < chainLines.size(); c++) {
                ChainLine chainLine = null;
                try {
                    chainLine=matchedIdentityName(chainLines.get(c), line);
                } catch(Exception ex) {
                    log.warn(ex.getMessage()+" for chain line "+chainLines.get(c));
                }
                String cha = chainLine.identityField();
                double score = chainLine.matchScore();
                if (score > maxScore) {
                    maxScore = score;
                    chainName = chainLine.parserClassName(); //chainLine.chainName();
                }

                //return early to speedup
                if (Math.abs(1.0 - score) < 0.02) {
                    log.debug("line='" + line + "', match identity field ='" + cha + "', leven score=" + score);
                    final StringDouble matchedChain = new StringDouble(chainName, score);
                    log.debug("matched chain:\n" + matchedChain);
                    return matchedChain;
                }
            }
        }
        return new StringDouble(chainName, maxScore);
    }

    /**
     * find the matched "name" from a line of format chainID(int): name1(chain
     * Name),name2,...:category:parserName 97: RCSS,RCSS Superstore:Grocery:RCSS
     *
     * @param chainLine
     *            raw chain line string
     * @param receiptLine
     *            raw receiptLine
     * @return a ChainLine object
     */
    private static ChainLine matchedIdentityName(final String chainLine, final String receiptLine) throws Exception {
        String[] chainLineFields = chainLine.split(CHAINLINE_SPLITTER);
        if (chainLineFields.length != 4)
            throw new Exception("chainLineFields should have 4 fields, but it's " + chainLineFields.length);

        String[] names = chainLineFields[1].trim().split(SECONDLEVEL_SPLITTER);
        double maxScore = -1, score = 0;
        String matchedIdentityName = "";
        for (int i = 0; i < names.length; i++) {
            String rLine = receiptLine.toLowerCase();
            String cName = names[i].toLowerCase();
            if (rLine.contains(cName)) {
                matchedIdentityName = names[i];
                score = 1.0;
                maxScore = 1.0;
                break;
            } else
                score = Levenshtein.compare(rLine, cName);
            if (score > maxScore) {
                maxScore = score;
                matchedIdentityName = names[i];
            }
        }
        return new ChainLine(chainLineFields[0].trim(), names[0].trim(), matchedIdentityName.trim(), maxScore,
                chainLineFields[2].trim(), chainLineFields[3].trim());
    }

    @Value
    static class StoreChainData {
        String code;
        String name;
        String identity;
        String category;
    }

    public static void main(String[] args) throws Exception {
        List<StoreChainData> storeChainData = new ArrayList<>();

        GenericChains genericChains = new GenericChains("/config/Generic/chain.list");
        for (String line : genericChains.chainLines) {
            String[] chainLineFields = line.split(CHAINLINE_SPLITTER);
            if (chainLineFields.length != 4)
                throw new Exception("chainLineFields should have 4 fields, but it's " + chainLineFields.length);

            String[] names = chainLineFields[1].trim().split(SECONDLEVEL_SPLITTER);
            storeChainData.add(
                    new StoreChainData(chainLineFields[3].trim().toLowerCase(),
                                       names[0],
                                       chainLineFields[1].trim(),
                                       chainLineFields[2].trim()));
        }

        ObjectMapper mapper = new ObjectMapper();
        final String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(storeChainData);
        System.out.println(jsonInString);
    }
}
