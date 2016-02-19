package com.openprice.parser.store;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.openprice.parser.api.ReceiptLine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldParserCommonTest {


    @Test
    public void parseTotal1(){
        final String totalValue = FieldParserCommon.parseTotal("TOTAL                                     &22.ST");
        assertEquals("22", totalValue);
    }

    @Test
    public void parseTotalSold1(){
        final ReceiptLine line= new ReceiptLine(
                null,
                "total number of sold items: 10",
                0,
                null);
       assertEquals("10", FieldParserCommon.parseTotalSold(line));
    }

    @Test
    public void parseTotalSold2(){
        final ReceiptLine line= new ReceiptLine(
                null,
                "total number of sold items10",
                0,
                null);
       assertEquals("10", FieldParserCommon.parseTotalSold(line));
    }

    @Test
    public void parseTotalSold3(){
        final ReceiptLine line= new ReceiptLine(
                null,
                "total number of sold items1 0",
                0,
                null);
       assertEquals("10", FieldParserCommon.parseTotalSold(line));
    }
}
