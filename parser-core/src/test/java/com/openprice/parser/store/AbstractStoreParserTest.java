package com.openprice.parser.store;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AbstractStoreParserTest {


    @Test
    public void testDate1(){
        final List<String> lines=new ArrayList<String>();
        lines.add("DATE            TIME            AMOUNT");
        lines.add("01/18/2015      17:26:22        $        14.48");
        lines.add("APPROVED");
        lines.add("No Signature Required");
        assertEquals("01/18/2015", AbstractStoreParser.findDateStringAfterLine(lines, 0));
    }

    @Test
    public void testDate2(){
        final List<String> lines=new ArrayList<String>();
        lines.add("DATE 03/ 06/ 2015                TIME 14 :49:48");
        lines.add("AUTH # 00509Z                    REF # 00000062");
        assertEquals("03/06/2015", AbstractStoreParser.findDateStringAfterLine(lines, 0));
    }

}
