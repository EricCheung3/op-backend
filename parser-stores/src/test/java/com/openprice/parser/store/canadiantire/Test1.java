package com.openprice.parser.store.canadiantire;

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
public class Test1 extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testFiles/canadiantire/2015_02_14_09_59_40.jpg.hengshuai.txt")
    private Resource receipt_2015_02_14_09_59_40;

    @Value("classpath:/testFiles/canadiantire/2015_02_14_09_59_51.jpg.hengshuai.txt")
    private Resource receipt_2015_02_14_09_59_51;

    @Value("classpath:/testFiles/canadiantire/2015_02_14_10_00_00.jpg.hengshuai.txt")
    private Resource receipt_2015_02_14_10_00_00;

    @Value("classpath:/testFiles/canadiantire/2015_04_04_21_37_11.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_37_11;

    @Value("classpath:/testFiles/canadiantire/2015_04_04_21_47_19.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_47_19;

    @Value("classpath:/testFiles/canadiantire/2015_04_04_21_52_10.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_52_10;

    @Value("classpath:/testFiles/canadiantire/2015_04_04_21_52_17.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_52_17;

    @Value("classpath:/testFiles/canadiantire/2015_04_04_21_52_24.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_52_24;

    @Value("classpath:/testFiles/canadiantire/2015_04_04_21_52_31.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_52_31;

    @Value("classpath:/testFiles/canadiantire/2015_04_04_21_52_39.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_52_39;

    @Value("classpath:/testFiles/canadiantire/2015_04_04_22_11_08.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_11_08;

    @Value("classpath:/testFiles/canadiantire/2015_04_04_22_17_07.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_17_07;

    @Value("classpath:/testFiles/canadiantire/2015_04_04_22_22_48.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_22_48;

    @Value("classpath:/testFiles/canadiantire/2015_05_02_21_56_51.jpg.momingzhen159.txt")
    private Resource receipt_2015_05_02_21_56_51;

    @Value("classpath:/testFiles/canadiantire/2015_07_02_18_13_23.jpg.henryHuang.txt")
    private Resource receipt_2015_07_02_18_13_23;

    @Value("classpath:/testFiles/canadiantire/2015_07_02_18_14_49.jpg.henryHuang.txt")
    private Resource receipt_2015_07_02_18_14_49;

    @Value("classpath:/testFiles/canadiantire/2015_07_03_15_23_05.jpg.hengshuai.txt")
    private Resource receipt_2015_07_03_15_23_05;

    @Value("classpath:/testFiles/canadiantire/2015_07_03_15_35_46.jpg.hengshuai.txt")
    private Resource receipt_2015_07_03_15_35_46;

    @Value("classpath:/testFiles/canadiantire/2015_07_21_10_49_03.jpg.henryHuang.txt")
    private Resource receipt_2015_07_21_10_49_03;

    @Value("classpath:/testFiles/canadiantire/2015_07_21_10_56_27.jpg.henryHuang.txt")
    private Resource receipt_2015_07_21_10_56_27;

    @Value("classpath:/testFiles/canadiantire/2015_07_21_10_57_34.jpg.henryHuang.txt")
    private Resource receipt_2015_07_21_10_57_34;

    @Test
    public void receipt_2015_02_14_09_59_40()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_14_09_59_40, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "053-1 332-8 tide lq 2x orig $",  "6.99", null, 13);
        verifyParsedItem(iterator.next(), "purex cold 2x 4 $",  "8.99", null, 15);
        verifyParsedItem(iterator.next(), "stk,strt hky 40 $",  "5.99", null, 17);
        verifyParsedItem(iterator.next(), "ministk&ball,cr $",  "9.99", null, 18);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "31.96",19);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store hours 8ah-10ph honda~ to saturda~",66);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst .reg 1139122352",68);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "33.56",22);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/13",27);
    }

    @Test
    public void receipt_2015_02_14_09_59_51()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_14_09_59_51, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "lindor hrt asst    $",  "77.94", null, 10);
        verifyParsedItem(iterator.next(), "lindor amour he    $",  "23.98", null, 12);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "101.92",13);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store hours 8ah-10ph honda~ to saturda~",50);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst.reg 1139122352",52);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "107.02",16);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/13",21);
    }

    @Test
    public void receipt_2015_02_14_10_00_00()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_14_10_00_00, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "lindor hrt asst    $",  "77.94", null, 11);
        verifyParsedItem(iterator.next(), "lindor amour he    $",  "23.98", null, 13);
        verifyParsedItem(iterator.next(), "c-type clamp 3\"    $",  "13.98", null, 15);
        verifyParsedItem(iterator.next(), "c-type clamp 2\"    $",  "19.96", null, 17);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "135.86",18);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store hours 8ah-10ph honday to saturday",67);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst .reg 1139122352",69);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "142.65",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/8",26);
    }

    @Test
    public void receipt_2015_04_04_21_37_11()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_37_11, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_47_19()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_47_19, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_52_10()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_52_10, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_52_17()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_52_17, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_52_24()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_52_24, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_52_31()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_52_31, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_52_39()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_52_39, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_22_11_08()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_11_08, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_22_17_07()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_17_07, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_22_22_48()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_22_48, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_05_02_21_56_51()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_05_02_21_56_51, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_02_18_13_23()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_02_18_13_23, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_02_18_14_49()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_02_18_14_49, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_03_15_23_05()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_03_15_23_05, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_03_15_35_46()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_03_15_35_46, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_21_10_49_03()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_10_49_03, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_21_10_56_27()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_10_56_27, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_21_10_57_34()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_10_57_34, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }



}
