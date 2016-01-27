package com.openprice.parser.generic;

import java.util.List;

import com.openprice.parser.StoreChain;
import com.openprice.parser.common.StringCommon;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenericChains {

 // use a large values to search all lines from the end
  private static final int MAX_SEARCHED_LINES_END = 6;
  private static final int MAX_SEARCHED_LINES_BEGIN = 6;


    //Load chain Names
    public List<StoreChain> loadChainNamesFromFile(final String resourceFile){

    }

  public String findChain(final List<String> chainList, final List<String> lines) throws Exception {
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
      MatchedChain chainBegin = chainNameSearch(begin, begin + MAX_SEARCHED_LINES_BEGIN);
      if (chainBegin.getMatchedScore() > 0.75)
          return chainBegin;

      int end = -1;
      for (int i = lines.size() - 1; i >= 0; i--) {
          String line = lines.get(i).trim();
          int[] counts = StringCommon.countDigitAndChars(line);
          if (counts[1] > 2) {
              end = i;
              break;
          }
      }
      String chainEnd = chainNameSearch(end - MAX_SEARCHED_LINES_END, end);
      log.debug("#####searching from head: chain=" + chainBegin.getName() + ", score=" + chainBegin.getMatchedScore());
      log.debug("#####searching from End: chain=" + chainEnd.getName() + ", score=" + chainEnd.getMatchedScore());
      if (chainEnd.getMatchedScore() > chainBegin.getMatchedScore())
          return chainEnd;
      else
          return chainBegin;
  }

  /*
 * detect which chain the store between begin and end
 *
 * @param begin the begin line number
 *
 * @param end the end line number
 *
 * @return a Chain object, the first is matched chain name, the second is
 * the score, the third is the line number matched.
 */
    public String chainNameSearch(final int begin, final int end) throws Exception {
        double maxScore = -1;
        int matchedID = -1;
        String matchedIdentityName = "";
        String matchedLine = "";
        String chainName = "";
        int found = -1;
        ChainLine matchedIdentityNameLine = null;
        for (int i = Math.max(0, begin); i <= Math.min(lines.size() - 1, end); i++) {
            final String line = lines.get(i).trim();
            int[] counts = StringCommon.countDigitAndChars(line);
            if (counts[1] < 2)
                continue;

            for (int c = 0; c < chainList.size(); c++) {
                // nSCP is short for nameScoreCatParserFound
                final ChainLine chainLine = matchedIdentityName(chainList.get(c), line);
                int id = Integer.valueOf(chainLine.chainID());
                String cha = chainLine.identityField();
                double score = chainLine.matchScore();
                if (score > maxScore) {
                    maxScore = score;
                    matchedIdentityName = cha;
                    matchedID = id;
                    found = i;
                    matchedIdentityNameLine = chainLine;
                    matchedLine = line;
                    chainName = chainLine.chainName();
                }
                if (Math.abs(1.0 - score) < 0.02) {
                    log.debug("line=" + line + "match identity field =" + cha + ",leven score=" + score);
                    final MatchedChain chain = MatchedChain.builder().id(matchedID).name(chainName)
                            .matchedField(matchedIdentityName).matchedScore(maxScore).matchedOnLine(found)
                            .categories(matchedIdentityNameLine.category())
                            .parserClassName(matchedIdentityNameLine.parserClassName()).build();
                    log.debug("matched chain:\n" + chain);
                    return chain;
                }
            }
        }
        // logger.debug("approximate chain found is "+matched+",
        // score="+maxScore
        // +", line is "+lines.get(found));
        if (matchedIdentityNameLine == null)
            throw new ChainNotFoundException("no chain found between line " + begin + " and line " + end);
        final MatchedChain chain = MatchedChain.builder().id(matchedID).name(chainName).matchedField(matchedIdentityName)
                .matchedScore(maxScore).matchedOnLine(found).categories(matchedIdentityNameLine.category())
                .parserClassName(matchedIdentityNameLine.parserClassName()).build();
        log.debug("matched line=" + matchedLine + "match identity field =" + matchedIdentityName + ",leven score="
                + maxScore);
        log.debug("matched chain:\n" + chain);
        return chain;
    }
    }
