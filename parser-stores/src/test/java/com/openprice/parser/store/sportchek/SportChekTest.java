package com.openprice.parser.store.sportchek;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.openprice.common.TextResourceUtils;
import com.openprice.parser.ParsedField;
import com.openprice.parser.ParsedItem;
import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.store.AbstractReceiptParserIntegrationTest;

@RunWith(SpringJUnit4ClassRunner.class)
public class SportChekTest extends AbstractReceiptParserIntegrationTest {


    @Value("classpath:/testFiles/sportchek/fromPhone/HuFeb24.txt")
    private Resource receipt_HuFeb24;

    @Value("classpath:/testFiles/sportchek/fromPhone/YuanFeb24.txt")
    private Resource receipt_YuanFeb24;

    @Test
    public void receipt_HuFeb24()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_HuFeb24, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sportchek", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "nike team trg",  "55.00", null, 13);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "55.00",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "cashier:20051 breanna c",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/5/11",6);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 869618785",45);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "57.75",17);
    }

    @Test
    public void receipt_YuanFeb24()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_YuanFeb24, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sportchek", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(0,receipt.getItems().size());
        //TODO missing an item because no widepace? use NonWideSpaceParserImpl, see NonWideSpaceParserImplTest
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "179.99",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "188.99",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/11/8",5);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "cashier:24713 sylvi l",6);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 869618785",45);
    }


}
