package com.openprice.parser.store.homedepot;

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
public class HomeDepotTest extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testFiles/homedepot/2015_04_04_21_27_51.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_27_51;

    @Value("classpath:/testFiles/homedepot/2015_04_04_21_31_22.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_31_22;

    @Value("classpath:/testFiles/homedepot/2015_04_04_21_31_30.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_31_30;

    @Value("classpath:/testFiles/homedepot/2015_04_04_21_41_11.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_41_11;

    @Value("classpath:/testFiles/homedepot/2015_04_04_21_55_38.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_55_38;

    @Value("classpath:/testFiles/homedepot/2015_04_04_22_05_59.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_05_59;

    @Value("classpath:/testFiles/homedepot/2015_04_04_22_06_06.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_06_06;

    @Value("classpath:/testFiles/homedepot/2015_04_04_22_07_29.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_07_29;

    @Value("classpath:/testFiles/homedepot/2015_04_04_22_10_24.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_10_24;

    @Value("classpath:/testFiles/homedepot/2015_04_04_22_10_33.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_10_33;

    @Value("classpath:/testFiles/homedepot/2015_06_04_21_18_40.jpg.henryHuang.txt")
    private Resource receipt_2015_06_04_21_18_40;

    @Value("classpath:/testFiles/homedepot/2015_07_02_18_04_36.jpg.henryHuang.txt")
    private Resource receipt_2015_07_02_18_04_36;

    @Value("classpath:/testFiles/homedepot/2015_07_02_18_13_46.jpg.henryHuang.txt")
    private Resource receipt_2015_07_02_18_13_46;

    @Value("classpath:/testFiles/homedepot/2015_07_21_10_57_49.jpg.henryHuang.txt")
    private Resource receipt_2015_07_21_10_57_49;

    @Test
    public void receipt_2015_04_04_21_27_51()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_27_51, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_31_22()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_31_22, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_31_30()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_31_30, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_41_11()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_41_11, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_55_38()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_55_38, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_22_05_59()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_05_59, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_22_06_06()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_06_06, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_22_07_29()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_07_29, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_22_10_24()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_10_24, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_22_10_33()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_10_33, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_06_04_21_18_40()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_06_04_21_18_40, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_02_18_04_36()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_02_18_04_36, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "ajd . flapper <a>",  "10.48", null, 8);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2022/2/15",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "11.00",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card by comp 1et i ng a br i ef sur-vey about",42);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "10.48",9);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst / hst                          0.52",10);

    }

    @Test
    public void receipt_2015_07_02_18_13_46()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_02_18_13_46, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "caulk <-a",  "4.99", null, 6);
        verifyParsedItem(iterator.next(), "mono ultra <a>",  "4.97", null, 7);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2025/1/15",4);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "10.46",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card by camp 1et i ng a i)r i ef survey about",44);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "9.96",8);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst                      0.50",9);

    }

    @Test
    public void receipt_2015_07_21_10_57_49()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_10_57_49, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "~more    doing,\"", null, null, 3);
//        verifyParsedItem(iterator.next(), "brent boyd", null, null, 6);
        verifyParsedItem(iterator.next(), "dim univ la <a>",  "19.98", null, 9);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/3/7",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "20.98",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card by comp 1at i ng a b ~i ef su~v.ey about",43);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "19.98",10);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst / hst                    1.00",11);

    }

}