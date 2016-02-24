package com.openprice.parser.store.newyorkfries;

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
public class NewYorkFriesTest1 extends AbstractReceiptParserIntegrationTest{
    @Value("classpath:/testFiles/newyorkfries/southgate/2015_10_10_14_51_40.jpg.txt")
    private Resource receipt_2015_10_10_14_51_40;

    @Value("classpath:/testFiles/newyorkfries/southgate/2015_10_10_14_52_30.jpg.txt")
    private Resource receipt_2015_10_10_14_52_30;

    @Value("classpath:/testFiles/newyorkfries/southgate/2015_10_10_14_52_34.jpg.txt")
    private Resource receipt_2015_10_10_14_52_34;

    @Value("classpath:/testFiles/newyorkfries/southgate/2015_10_10_14_52_44.jpg.txt")
    private Resource receipt_2015_10_10_14_52_44;

    @Value("classpath:/testFiles/newyorkfries/southgate/2015_10_10_14_52_48.jpg.txt")
    private Resource receipt_2015_10_10_14_52_48;

    @Value("classpath:/testFiles/newyorkfries/southgate/2015_10_10_14_53_05.jpg.txt")
    private Resource receipt_2015_10_10_14_53_05;

    @Value("classpath:/testFiles/newyorkfries/southgate/2015_10_10_14_53_14.jpg.txt")
    private Resource receipt_2015_10_10_14_53_14;

    @Value("classpath:/testFiles/newyorkfries/southgate/2015_10_10_14_53_23.jpg.txt")
    private Resource receipt_2015_10_10_14_53_23;

    @Value("classpath:/testFiles/newyorkfries/southgate/2015_10_10_14_53_51.jpg.txt")
    private Resource receipt_2015_10_10_14_53_51;

    @Value("classpath:/testFiles/newyorkfries/southgate/2015_10_10_14_54_09.jpg.txt")
    private Resource receipt_2015_10_10_14_54_09;

    @Value("classpath:/testFiles/newyorkfries/southgate/2015_10_10_14_54_33.jpg.txt")
    private Resource receipt_2015_10_10_14_54_33;

    @Value("classpath:/testFiles/newyorkfries/southgate/2015_10_10_14_54_57.jpg.txt")
    private Resource receipt_2015_10_10_14_54_57;

    @Value("classpath:/testFiles/newyorkfries/southgate/2015_10_10_14_55_06.jpg.txt")
    private Resource receipt_2015_10_10_14_55_06;

    @Value("classpath:/testFiles/newyorkfries/southgate/2015_10_10_14_55_43.jpg.txt")
    private Resource receipt_2015_10_10_14_55_43;

    @Value("classpath:/testFiles/newyorkfries/southgate/2015_10_10_14_55_47.jpg.txt")
    private Resource receipt_2015_10_10_14_55_47;

    @Value("classpath:/testFiles/newyorkfries/southgate/2015_10_10_14_56_00.jpg.txt")
    private Resource receipt_2015_10_10_14_56_00;

    @Value("classpath:/testFiles/newyorkfries/southgate/2015_10_10_14_56_13.jpg.txt")
    private Resource receipt_2015_10_10_14_56_13;

    @Value("classpath:/testFiles/newyorkfries/southgate/2015_10_10_14_56_54.jpg.txt")
    private Resource receipt_2015_10_10_14_56_54;

    @Value("classpath:/testFiles/newyorkfries/southgate/2015_10_10_14_57_02.jpg.txt")
    private Resource receipt_2015_10_10_14_57_02;

    @Value("classpath:/testFiles/newyorkfries/southgate/2015_10_10_14_57_14.jpg.txt")
    private Resource receipt_2015_10_10_14_57_14;

    @Value("classpath:/testFiles/newyorkfries/southgate/2015_10_10_14_57_19.jpg.txt")
    private Resource receipt_2015_10_10_14_57_19;

    @Test
    public void receipt_2015_10_10_14_51_40()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_51_40, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("newyorkfries", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 the perfect combo",  "0.60", null, 4);
        verifyParsedItem(iterator.next(), "1 xl    fries",  "5.45", null, 5);
        verifyParsedItem(iterator.next(), "1 large p o p",  "2.10", null, 6);
        verifyParsedItem(iterator.next(), "1 hot dog",  "3.75", null, 7);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "order i: 74     date: thu oct 8,2015     time: 12:21:58",19);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 5%:                                           $0.54",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "11.24",11);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "10.70",9);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "transaction approved",30);

    }

    @Test
    public void receipt_2015_10_10_14_52_30()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_52_30, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("newyorkfries", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 reg fries",  "4.25", null, 4);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "order f: 232  date: thu oct 8,2015   time: 15:03:07",15);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 5%:                                          $0.21",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "4.46",8);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "4.25",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",15);

    }

    @Test
    public void receipt_2015_10_10_14_52_34()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_52_34, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("newyorkfries", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(6,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1    the    ultimate combo",  "0.60", null, 4);
        verifyParsedItem(iterator.next(), "1    reg    chicken poutine",  "5.99", null, 5);
        verifyParsedItem(iterator.next(), "1    hot    dog",  "3.75", null, 6);
        verifyParsedItem(iterator.next(), "1    reg    pop",  "1.85", null, 7);
        verifyParsedItem(iterator.next(), "1    hot    dog cheese",  "0.50", null, 8);
        verifyParsedItem(iterator.next(), "1    hot    dog chili",  "0.50", null, 9);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "order i: 211     date: thu oct 8,2015   time: 14:34:35",21);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 5%:                                              $0.60",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "12.59",13);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "11.99",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",21);

    }

    @Test
    public void receipt_2015_10_10_14_52_44()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_52_44, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("newyorkfries", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1    the ultimate combo",  "0.60", null, 4);
        verifyParsedItem(iterator.next(), "1    reg poutine",  "5.99", null, 5);
        verifyParsedItem(iterator.next(), "1    bottle water",  "1.85", null, 6);
        verifyParsedItem(iterator.next(), "1    hot dog",  "3.75", null, 7);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "order i: 260  date: thu oct 8,2015    time: 15:43:53",19);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 5%:                                            $0.55",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "11.54",11);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "10.99",9);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",19);

    }

    @Test
    public void receipt_2015_10_10_14_52_48()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_52_48, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("newyorkfries", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 small fries",  "3.25", null, 4);
        verifyParsedItem(iterator.next(), "1 reg gravy",  "1.00", null, 5);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "order t: 252    date: thu oct 8,2015   time: 15:37:17",16);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 5%:                                               $0.21",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "4.46",9);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "4.25",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",16);

    }

    @Test
    public void receipt_2015_10_10_14_53_05()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_53_05, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("newyorkfries", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 reg poutine",  "5.99", null, 8);
        verifyParsedItem(iterator.next(), "1 bottle juice",  "2.25", null, 9);
//        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "order : 210   date: thu oct 8,2015    time: 14:38:13",21);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 5%:                                                 $0.41",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "8.65",13);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "8.24",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",21);

    }

    @Test
    public void receipt_2015_10_10_14_53_14()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_53_14, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("newyorkfries", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 reg works fries",  "5.99", null, 4);
        verifyParsedItem(iterator.next(), "1 large pop",  "2.10", null, 5);
//        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "order i: 261  date: thu oct 8,2015  time: 15:44:55",16);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 5%:                                           $0.40",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "8.49",9);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "8.09",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",16);

    }

    @Test
    public void receipt_2015_10_10_14_53_23()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_53_23, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("newyorkfries", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 large fries",  "4.95", null, 4);
        verifyParsedItem(iterator.next(), "1 large cheese",  "1.25", null, 5);
        verifyParsedItem(iterator.next(), "1 large pop",  "2.10", null, 6);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "order i: 153   date: thu oct 8.7015   time: 13:27:50",17);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 5%:                                           $0.42",9);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "8.72",10);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "8.30",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "",-1);//Thu Oct 8.7015
    }

    @Test
    public void receipt_2015_10_10_14_53_51()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_53_51, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("newyorkfries", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(6,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1    reg poutine",  "5.99", null, 4);
        verifyParsedItem(iterator.next(), "1    reg poutine",  "5.99", null, 5);
        verifyParsedItem(iterator.next(), "1    hot dog bacon",  "0.50", null, 6);
        verifyParsedItem(iterator.next(), "1    hot dog bacon",  "0.50", null, 7);
        verifyParsedItem(iterator.next(), "1    lg choc milk",  "2.25", null, 8);
        verifyParsedItem(iterator.next(), "1    lg choc milk",  "2.25", null, 9);
//        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "order : 270  date: thu oct 8,2015  time: 15:58:32",21);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 51:                                            $0.87",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "18.35",13);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "17.48",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",21);
    }

    @Test
    public void receipt_2015_10_10_14_54_09()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_54_09, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("newyorkfries", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 large fries",  "4.95", null, 4);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "order i: 101    date: thu oct 8,2015   time: 13:26:54",16);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 5%:                                          $0.25",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "5.20",8);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "4.95",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",16);
    }

    @Test
    public void receipt_2015_10_10_14_54_33()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_54_33, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("newyorkfries", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 reg veggie fries",  "5.99", null, 2);
        verifyParsedItem(iterator.next(), "1 x chili",  "0.50", null, 3);
        verifyParsedItem(iterator.next(), "1 x sour cream",  "0.50", null, 4);
        verifyParsedItem(iterator.next(), "1 large pop",  "2.10", null, 5);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "order i: 131   date: thu oct 8,2015    time: 13:05:10",17);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 5%:                                         $0.45",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "9.54",9);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "9.09",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",17);
    }

    @Test
    public void receipt_2015_10_10_14_54_57()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_54_57, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("newyorkfries", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 reg poutine",  "5.99", null, 5);
        verifyParsedItem(iterator.next(), "1 reg pop",  "1.85", null, 6);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "order t: 121   date: thu oct 8,2015   time: 13:00:53",18);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 5%:                                          $0.39",9);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "8.23",10);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "7.84",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",18);
    }

    @Test
    public void receipt_2015_10_10_14_55_06()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_55_06, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("newyorkfries", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 reg fries",  "4.25", null, 8);
        verifyParsedItem(iterator.next(), "1 reg pop",  "1.85", null, 9);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "order i: 11  date: thu oct 8,2015    time: 12:19:59",22);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 5%:                                          $0.31",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "6.41",13);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "6.10",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",22);
    }

    @Test
    public void receipt_2015_10_10_14_55_43()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_55_43, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("newyorkfries", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1    the ultimate combo",  "0.60", null, 4);
        verifyParsedItem(iterator.next(), "1    reg veggie fries",  "5.99", null, 5);
        verifyParsedItem(iterator.next(), "1    reg pop",  "1.85", null, 6);
        verifyParsedItem(iterator.next(), "1    hot dog",  "3.75", null, 7);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "order #: 361    date: thu oct 8,2015    time: 17:24:13",19);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 5%:                                              $0.55",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "11.54",11);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "10.99",9);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",19);
    }

    @Test
    public void receipt_2015_10_10_14_55_47()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_55_47, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("newyorkfries", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 reg poutine",  "5.99", null, 4);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 5%:                                                $0.30",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "6.29",8);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "5.99",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",18);
    }

    @Test
    public void receipt_2015_10_10_14_56_00()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_56_00, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("newyorkfries", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(5,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1    small fries",  "3.25", null, 4);
        verifyParsedItem(iterator.next(), "1    no salt",  "0.00", null, 5);
        verifyParsedItem(iterator.next(), "1    no salt",  "0.00", null, 6);
        verifyParsedItem(iterator.next(), "1    no salt",  "0.00", null, 7);
        verifyParsedItem(iterator.next(), "1    tap water",  "0.00", null, 8);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "order i: 322     date: thu oct 8,2015 time: 16:39:38",20);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 5%:                                           $0.16",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "3.41",12);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "3.25",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",20);
    }

    @Test
    public void receipt_2015_10_10_14_56_13()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_56_13, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("newyorkfries", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(5,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1    sm veggie fries",  "4.99", null, 4);
        verifyParsedItem(iterator.next(), "1    sm veggie fries",  "4.99", null, 5);
        verifyParsedItem(iterator.next(), "1    sm veggie fries",  "4.99", null, 6);
        verifyParsedItem(iterator.next(), "1    reg pop",  "1.85", null, 7);
        verifyParsedItem(iterator.next(), "1    reg pop",  "1.85", null, 8);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "order i: 353    date: thu oct 8,2015  time: 17:17:15",20);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 5%:                                               $0.93",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "19.60",12);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "13.67",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",20);
    }

    @Test
    public void receipt_2015_10_10_14_56_54()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_56_54, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("newyorkfries", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 reg veggie fries",  "5.99", null, 5);
        verifyParsedItem(iterator.next(), "1 large pop",  "2.10", null, 6);
        verifyParsedItem(iterator.next(), "1 reg poutine",  "5.99", null, 7);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 51:                                            $0.40",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "3.49",13);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "14.08",9);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",21);
    }

    @Test
    public void receipt_2015_10_10_14_57_02()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_57_02, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("newyorkfries", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 reg poutine",  "5.99", null, 4);
        verifyParsedItem(iterator.next(), "1 reg fries",  "4.25", null, 5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 5%:                                                 $0.51",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "10.75",9);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "10.24",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",17);
    }

    @Test
    public void receipt_2015_10_10_14_57_14()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_57_14, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("newyorkfries", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(5,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1    sm poutine",  "4.99", null, 4);
        verifyParsedItem(iterator.next(), "1    sm poutine",  "4.99", null, 5);
        verifyParsedItem(iterator.next(), "1    sm poutine",  "4.99", null, 6);
        verifyParsedItem(iterator.next(), "1    sm poutine",  "4.99", null, 7);
        verifyParsedItem(iterator.next(), "1    large pop",  "2.10", null, 8);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "order i: 332    date: thu oct 8,2015  time: 16:54:43",20);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 5%:                                            $1.10",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "23.16",12);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "22.06",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",20);
    }

    @Test
    public void receipt_2015_10_10_14_57_19()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_57_19, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("newyorkfries", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 reg bacon dbl chz",  "5.99", null, 4);
        verifyParsedItem(iterator.next(), "1 reg pop",  "1.85", null, 5);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "order i: e   date: thu oct 8,2015  time: 17:19:34",17);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 5%:                                          $0.39",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "8.23",9);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "7.84",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",17);
    }



}
