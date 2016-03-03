package com.openprice.parser.store.arbys;

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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class ArbysTest1 extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testfiles/arbys/2015_10_10_14_55_54.jpg.txt")
    private Resource receipt_2015_10_10_14_55_54;

    @Value("classpath:/testfiles/arbys/2015_10_10_14_57_05.jpg.txt")
    private Resource receipt_2015_10_10_14_57_05;

    @Value("classpath:/testfiles/arbys/2015_10_10_14_57_47.jpg.txt")
    private Resource receipt_2015_10_10_14_57_47;

    @Value("classpath:/testfiles/arbys/2015_10_10_15_11_37.jpg.txt")
    private Resource receipt_2015_10_10_15_11_37;

    @Value("classpath:/testfiles/arbys/2015_10_10_15_12_03.jpg.txt")
    private Resource receipt_2015_10_10_15_12_03;

    @Value("classpath:/testfiles/arbys/2015_10_10_15_17_20.jpg.txt")
    private Resource receipt_2015_10_10_15_17_20;

    @Value("classpath:/testfiles/arbys/2015_10_10_15_18_30.jpg.txt")
    private Resource receipt_2015_10_10_15_18_30;

    @Value("classpath:/testfiles/arbys/2015_10_17_16_53_25.jpg.txt")
    private Resource receipt_2015_10_17_16_53_25;

    @Value("classpath:/testfiles/arbys/2015_10_17_16_56_59.jpg.txt")
    private Resource receipt_2015_10_17_16_56_59;

    @Value("classpath:/testfiles/arbys/2015_10_17_17_03_29.jpg.txt")
    private Resource receipt_2015_10_17_17_03_29;

    @Value("classpath:/testfiles/arbys/2015_10_17_17_04_16.jpg.txt")
    private Resource receipt_2015_10_17_17_04_16;

    @Value("classpath:/testfiles/arbys/2015_10_17_17_04_50.jpg.txt")
    private Resource receipt_2015_10_17_17_04_50;

    @Value("classpath:/testfiles/arbys/2015_10_17_17_04_55.jpg.txt")
    private Resource receipt_2015_10_17_17_04_55;

    @Value("classpath:/testfiles/arbys/2015_10_17_17_17_42.jpg.txt")
    private Resource receipt_2015_10_17_17_17_42;

    @Value("classpath:/testfiles/arbys/2015_10_17_17_25_53.jpg.txt")
    private Resource receipt_2015_10_17_17_25_53;

    @Value("classpath:/testfiles/arbys/2015_10_17_17_31_25.jpg.txt")
    private Resource receipt_2015_10_17_17_31_25;

    @Value("classpath:/testfiles/arbys/2015_10_17_17_31_47.jpg.txt")
    private Resource receipt_2015_10_17_17_31_47;

    @Value("classpath:/testfiles/arbys/2015_10_17_17_34_25.jpg.txt")
    private Resource receipt_2015_10_17_17_34_25;

    @Value("classpath:/testfiles/arbys/2015_10_17_17_35_32.jpg.txt")
    private Resource receipt_2015_10_17_17_35_32;

    @Value("classpath:/testfiles/arbys/2015_10_17_17_35_36.jpg.txt")
    private Resource receipt_2015_10_17_17_35_36;

    @Value("classpath:/testfiles/arbys/2015_10_17_17_36_31.jpg.txt")
    private Resource receipt_2015_10_17_17_36_31;

    @Value("classpath:/testfiles/arbys/2015_10_17_17_45_14.jpg.txt")
    private Resource receipt_2015_10_17_17_45_14;

    @Test
    public void receipt_2015_10_10_14_55_54()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_55_54, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("arbys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(5,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1    beefn ched",  "5.29", null, 5);
        verifyParsedItem(iterator.next(), "1    medium curly",  "2.49", null, 6);
        verifyParsedItem(iterator.next(), "1    med rtbr",  "2.19", null, 7);
        verifyParsedItem(iterator.next(), "1    combo disc.",  "1.68", null, 8);//TODO this is negative price
        verifyParsedItem(iterator.next(), "sbtl",  "8.29", null, 10);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "",-1);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "8.70",13);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                 .41",12);

    }

    @Test
    public void receipt_2015_10_10_14_57_05()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_57_05, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("arbys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_10_10_14_57_47()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_57_47, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("arbys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_10_10_15_11_37()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_15_11_37, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("arbys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_10_10_15_12_03()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_15_12_03, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("arbys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_10_10_15_17_20()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_15_17_20, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("arbys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_10_10_15_18_30()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_15_18_30, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("arbys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_10_17_16_53_25()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_17_16_53_25, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("arbys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_10_17_16_56_59()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_17_16_56_59, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("arbys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_10_17_17_03_29()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_17_17_03_29, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("arbys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_10_17_17_04_16()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_17_17_04_16, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("arbys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_10_17_17_04_50()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_17_17_04_50, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("arbys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_10_17_17_04_55()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_17_17_04_55, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("arbys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_10_17_17_17_42()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_17_17_17_42, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("arbys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_10_17_17_25_53()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_17_17_25_53, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("arbys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_10_17_17_31_25()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_17_17_31_25, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("arbys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_10_17_17_31_47()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_17_17_31_47, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("arbys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_10_17_17_34_25()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_17_17_34_25, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("arbys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_10_17_17_35_32()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_17_17_35_32, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("arbys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_10_17_17_35_36()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_17_17_35_36, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("arbys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_10_17_17_36_31()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_17_17_36_31, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("arbys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_10_17_17_45_14()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_17_17_45_14, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("arbys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }


}
