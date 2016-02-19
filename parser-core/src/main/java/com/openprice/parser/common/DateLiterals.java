package com.openprice.parser.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//TODO using Calendar to get the literals?
public class DateLiterals {

    private Map<String, Integer> monthLiteralToNumber = new HashMap<>();

    public DateLiterals(){
        monthLiteralToNumber.put("January", 1);
        monthLiteralToNumber.put("February", 2);
        monthLiteralToNumber.put("March", 3);
        monthLiteralToNumber.put("April", 4);
        monthLiteralToNumber.put("May", 5);
        monthLiteralToNumber.put("June", 6);
        monthLiteralToNumber.put("July", 7);
        monthLiteralToNumber.put("August", 8);
        monthLiteralToNumber.put("September", 9);
        monthLiteralToNumber.put("October", 10);
        monthLiteralToNumber.put("November", 11);
        monthLiteralToNumber.put("December", 12);

        monthLiteralToNumber.put("Jan", 1);
        monthLiteralToNumber.put("Feb", 2);
        monthLiteralToNumber.put("Mar", 3);
        monthLiteralToNumber.put("Apr", 4);
//        monthLiteralToNumber.put("May", 5);
        monthLiteralToNumber.put("Jun", 6);
        monthLiteralToNumber.put("Jul", 7);
        monthLiteralToNumber.put("Aug", 8);
        monthLiteralToNumber.put("Sep", 9);
        monthLiteralToNumber.put("Oct", 10);
        monthLiteralToNumber.put("Nov", 11);
        monthLiteralToNumber.put("Dec", 12);
    }

    public int getMonthNumber(final String literalMonth){
        return monthLiteralToNumber.get(literalMonth);
    }

    public int sizeOfMonthLiteralMap(){
        return monthLiteralToNumber.size();
    }

    public Set<String> monthLiterals(){
        return monthLiteralToNumber.keySet();
    }
}
