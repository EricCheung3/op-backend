package com.openprice.parser.generic;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.openprice.parser.ReceiptData;

public class GenericParserTest {


    @Test
    public void genericParserNotNull() throws Exception{
        final List<String> lines=new ArrayList<String>();
        lines.add("1");
        lines.add("1");
        lines.add("1");
        lines.add("1");
        lines.add("1");
        final ReceiptData data=ReceiptData.fromContentLines(lines);
        assertNotNull(GenericParser.selectParser(data));
    }
}
