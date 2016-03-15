package com.openprice.parser.store.mcdonalds;


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
public class McDonaldsTest extends AbstractReceiptParserIntegrationTest {

    @Value("classpath:/testfiles/mcdonalds/branch_1203parsons/2015_07_03_16_42_52.jpg.haipeng.txt")
    private Resource receipt_2015_07_03_16_42_52;

    @Value("classpath:/testfiles/mcdonalds/branch_61_109st/2015_07_03_16_52_34.jpg.haipeng.txt")
    private Resource receipt_2015_07_03_16_52_34;

    @Value("classpath:/testfiles/mcdonalds/branch_64_50st/2015_02_09_15_06_44.jpg.random.txt")
    private Resource receipt_2015_02_09_15_06_44;

    @Value("classpath:/testfiles/mcdonalds/branch_64_50st/2015_02_09_15_07_41_compression3.jpg.random.txt")
    private Resource receipt_2015_02_09_15_07_41_compression3;

    @Test
    public void receipt_2015_07_03_16_42_52()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_03_16_42_52, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("mcdonalds", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 blueberry muffin",  "1.69", null, 15);
        verifyParsedItem(iterator.next(), "1 m latte",  "1.50", null, 16);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "3.35",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/24",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "order 16",13);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "3.19",18);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst: 812012680rt0001",29);
    }

    @Test
    public void receipt_2015_07_03_16_52_34()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_03_16_52_34, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("mcdonalds", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "2 big mac & m fry",  "15.78", null, 31);
        verifyParsedItem(iterator.next(), "2 m latte",  "2.00", null, 32);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "18.67",37);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/28",24);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "17.78",34);

    }

    @Test
    public void receipt_2015_02_09_15_06_44()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_15_06_44, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("mcdonalds", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 bacon mcwrap grilled",  "5.49", null, 22);
        verifyParsedItem(iterator.next(), "1 hm 4nug s-fry yog",  "4.29", null, 23);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "10.27",30);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, ",1\\cct: fl. ~~ll def~,u- t                        :~      10.27",43);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/17",47);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "9.78",28);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "order# :         1700711",50);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                                           0.49",29);

    }

    @Test
    public void receipt_2015_02_09_15_07_41_compression3()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_15_07_41_compression3, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("mcdonalds", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 bacon mcwrap grilled",  "5.49", null, 19);
        verifyParsedItem(iterator.next(), "1 hm 4nug s-fry yog",  "4.29", null, 20);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "10.27",28);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/17",48);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "9.78",25);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                                               0.49",27);

    }



}
