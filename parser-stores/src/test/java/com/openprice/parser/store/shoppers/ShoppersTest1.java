package com.openprice.parser.store.shoppers;

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
public class ShoppersTest1 extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testFiles/Shoppers/branch_10907_82_AVE/2015_10_10_15_20_43.jpg.txt")
    private Resource receipt_2015_10_10_15_20_43;

    @Value("classpath:/testFiles/Shoppers/branch_10907_82_AVE/2015_10_17_17_47_20.jpg.txt")
    private Resource receipt_2015_10_17_17_47_20;

    @Value("classpath:/testFiles/Shoppers/branch_10907_82_AVE/2015_10_17_17_48_12.jpg.txt")
    private Resource receipt_2015_10_17_17_48_12;

    @Value("classpath:/testFiles/Shoppers/branch_62_CITY_CENTRE_EAST/2015_04_04_21_32_01.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_32_01;

    @Value("classpath:/testFiles/Shoppers/branch_62_CITY_CENTRE_EAST/2015_04_04_21_47_13.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_47_13;

    @Value("classpath:/testFiles/Shoppers/branch_87_AVE_156 ST/2015_06_04_21_22_16.jpg.henryHuang.txt")
    private Resource receipt_2015_06_04_21_22_16;

    @Value("classpath:/testFiles/Shoppers/branch_87_AVE_156 ST/2015_07_21_10_48_35.jpg.henryHuang.txt")
    private Resource receipt_2015_07_21_10_48_35;

    @Value("classpath:/testFiles/Shoppers/branch_87_AVE_156 ST/2015_07_21_10_57_01.jpg.henryHuang.txt")
    private Resource receipt_2015_07_21_10_57_01;



    @Test
    public void receipt_2015_10_10_15_20_43()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_15_20_43, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("shoppers", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "klondike breyers",  "9", null, 13);
        verifyParsedItem(iterator.next(), "pc decadnt cookies",  "n", null, 14);
        verifyParsedItem(iterator.next(), "loacker wafer    n",  "2.79", null, 16);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst #: 84447 0567 rt0002",47);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card number:           ************4767",71);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "acct     : chequing                 $      17.88",67);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "17.26",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "invoice number:        10098230",75);
        //TODO date is wrong
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2012/5/9",72);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "author. #:             062387",74);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "17.88",21);

    }

    @Test
    public void receipt_2015_10_17_17_47_20()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_17_17_47_20, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("shoppers", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_10_17_17_48_12()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_17_17_48_12, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("shoppers", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_32_01()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_32_01, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("shoppers", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_47_13()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_47_13, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("shoppers", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_06_04_21_22_16()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_06_04_21_22_16, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("shoppers", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_21_10_48_35()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_10_48_35, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("shoppers", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_21_10_57_01()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_10_57_01, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("shoppers", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }



}
