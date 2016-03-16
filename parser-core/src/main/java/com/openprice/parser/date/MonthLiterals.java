package com.openprice.parser.date;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.openprice.common.Levenshtein;
import com.openprice.common.StringCommon;

//TODO enum?
//@Slf4j
public class MonthLiterals {

    private Map<String, Integer> monthLiteralToNumber = new HashMap<>();

    public MonthLiterals(){
        monthLiteralToNumber.put("january", 1);
        monthLiteralToNumber.put("february", 2);
        monthLiteralToNumber.put("march", 3);
        monthLiteralToNumber.put("april", 4);
        monthLiteralToNumber.put("may", 5);
        monthLiteralToNumber.put("june", 6);
        monthLiteralToNumber.put("july", 7);
        monthLiteralToNumber.put("august", 8);
        monthLiteralToNumber.put("september", 9);
        monthLiteralToNumber.put("october", 10);
        monthLiteralToNumber.put("november", 11);
        monthLiteralToNumber.put("december", 12);

        monthLiteralToNumber.put("jan", 1);
        monthLiteralToNumber.put("feb", 2);
        monthLiteralToNumber.put("mar", 3);
        monthLiteralToNumber.put("apr", 4);
//        monthLiteralToNumber.put("May", 5);
        monthLiteralToNumber.put("jun", 6);
        monthLiteralToNumber.put("jul", 7);
        monthLiteralToNumber.put("aug", 8);
        monthLiteralToNumber.put("sep", 9);
        monthLiteralToNumber.put("oct", 10);
        monthLiteralToNumber.put("nov", 11);
        monthLiteralToNumber.put("dec", 12);
    }

    public int getMonthNumber(final String literalMonth){
//        log.debug("literalMonth.toLowerCase()="+literalMonth.toLowerCase());
        return monthLiteralToNumber.get(literalMonth.toLowerCase());
    }

    public int sizeOfMonthLiteralMap(){
        return monthLiteralToNumber.size();
    }

    public Set<String> monthLiterals(){
        return monthLiteralToNumber.keySet();
    }

    public double mostSimilarMonthLiteralScore(final String str) {
        final String trimLower = str.trim().toLowerCase();
        return monthLiteralToNumber
                .keySet()
                .stream()
                .map(m -> Levenshtein.compare(m, trimLower))
                .max(Comparator.comparing(s->s))
                .get();
    }

    public String mostSimilarMonthLiteral(final String str){
        final String trimLower = str.trim().toLowerCase();
        final double maxScore = mostSimilarMonthLiteralScore(trimLower);
        if(maxScore > 0.6){
            return monthLiteralToNumber
                    .keySet()
                    .stream()
                    .max(Comparator.comparing(m->Levenshtein.compare(m, trimLower)))
                    .get();
        }
        return StringCommon.EMPTY;
    }
}
