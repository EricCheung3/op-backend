package com.openprice.parser.generic;

import java.util.List;

import com.openprice.parser.common.Levenshtein;
import com.openprice.parser.common.StringCommon;
import com.openprice.parser.common.TextResourceUtils;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
public class GenericChains {

    // use a large values to search all lines from the end
    private static final int MAX_SEARCHED_LINES_END = 6;
    private static final int MAX_SEARCHED_LINES_BEGIN = 6;

//  // splitter used in a line in file chain.list
    private final static String CHAINLINE_SPLITTER = ":";
    private final static String SECONDLEVEL_SPLITTER = ",";// the splitter used inside a field between :

    final List<String> chainList;

    private GenericChains(final List<String> chainList){
        this.chainList=chainList;
    }

    public GenericChains(final String resourceFile){
        this(TextResourceUtils.loadStringArray(resourceFile));
    }

  public String findChain(final List<String> lines) throws Exception {
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
      StringDouble chainBegin = chainNameSearch(lines, begin, begin + MAX_SEARCHED_LINES_BEGIN);
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
      StringDouble chainEnd = chainNameSearch(lines, end - MAX_SEARCHED_LINES_END, end);
      log.debug("#####searching from head: chain=" + chainBegin.getStr() + ", score=" + chainBegin.getValue());
      log.debug("#####searching from End: chain=" + chainEnd.getStr() + ", score=" + chainEnd.getValue());
      if (chainEnd.getValue() > chainBegin.getValue())
          return chainEnd.getStr();
      else
          return chainBegin.getStr();
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
    public StringDouble chainNameSearch(final List<String> lines, final int begin, final int end) throws Exception {
        double maxScore = -1;
        String matchedIdentityName = "";
        String matchedLine = "";
        String chainName = "";
        for (int i = Math.max(0, begin); i <= Math.min(lines.size() - 1, end); i++) {
            final String line = lines.get(i).trim();
            int[] counts = StringCommon.countDigitAndChars(line);
            if (counts[1] < 2)
                continue;

            for (int c = 0; c < chainList.size(); c++) {
                // nSCP is short for nameScoreCatParserFound
                final ChainLine chainLine = matchedIdentityName(chainList.get(c), line);
                String cha = chainLine.identityField();
                double score = chainLine.matchScore();
                if (score > maxScore) {
                    maxScore = score;
                    matchedIdentityName = cha;
                    matchedLine = line;
                    chainName = chainLine.chainName();
                }

                //return early to speedup
                if (Math.abs(1.0 - score) < 0.02) {
                    log.debug("line=" + line + "match identity field =" + cha + ",leven score=" + score);
                    final StringDouble matchedChain=new StringDouble(chainName, score);
                    log.debug("matched chain:\n" + matchedChain);
                    return matchedChain;
                }
            }
        }
        // logger.debug("approximate chain found is "+matched+",
        // score="+maxScore
        // +", line is "+lines.get(found));
        final StringDouble chain = new StringDouble(chainName, maxScore);
        log.debug("matched line=" + matchedLine + "match identity field =" + matchedIdentityName + ",leven score="
                + maxScore);
        log.debug("matched chain:\n" + chain);
        return chain;
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
  public static ChainLine matchedIdentityName(final String chainLine, final String receiptLine) throws Exception{
      String[] chainLineFields = chainLine.split(CHAINLINE_SPLITTER);
      if(chainLineFields.length != 4)
          throw new Exception("chainLineFields should have 4 fields");

      String[] names = chainLineFields[1].trim().split(SECONDLEVEL_SPLITTER);
      double maxScore = -1, score = 0;
      String matchedIdentityName = "";//
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
}
