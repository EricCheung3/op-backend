package com.openprice.parser.store.ikea;

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
public class IkeaTest1 extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testFiles/ikea/2015_04_04_21_25_13.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_25_13;

    @Value("classpath:/testFiles/ikea/2015_04_04_21_28_27.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_28_27;

    @Value("classpath:/testFiles/ikea/2015_04_04_21_31_43.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_31_43;

    @Value("classpath:/testFiles/ikea/2015_04_04_21_33_31.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_33_31;

    @Value("classpath:/testFiles/ikea/2015_04_04_21_33_37.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_33_37;

    @Value("classpath:/testFiles/ikea/2015_04_04_21_44_13.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_44_13;

    @Value("classpath:/testFiles/ikea/2015_04_04_21_58_34.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_58_34;

    @Value("classpath:/testFiles/ikea/2015_04_04_22_11_23.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_11_23;

    @Value("classpath:/testFiles/ikea/2015_04_04_22_11_28.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_11_28;

    @Test
    public void receipt_2015_04_04_21_25_13()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_25_13, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("ikea", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(7,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "fjadrar inner",  "14.009", null, 7);
        verifyParsedItem(iterator.next(), "rissla desk org",  "12.999", null, 10);
        verifyParsedItem(iterator.next(), "sparka soft toy",  "7.989", null, 12);
        verifyParsedItem(iterator.next(), "nyttja frm",  "4.999", null, 15);
        verifyParsedItem(iterator.next(), "article 40 2<~61 56", null, null, 16);
        verifyParsedItem(iterator.next(), "rissla desk pad",  "29.999", null, 17);
        verifyParsedItem(iterator.next(), "lack tv bnch",  "19.999", null, 19);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "94.44",26);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "total items:               8",27);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst ff r102453032",38);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/13",36);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "89.94",21);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "4.50",24);
    }

    @Test
    public void receipt_2015_04_04_21_28_27()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_28_27, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("ikea", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(9,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "ravenea pint 25",  "14.99", null, 9);
        verifyParsedItem(iterator.next(), "saxnas frm",  "11.98", null, 11);
        verifyParsedItem(iterator.next(), "guzmania. pint",  "14.99", null, 14);
        verifyParsedItem(iterator.next(), "racka curtain",  "2.99", null, 16);
        verifyParsedItem(iterator.next(), "orrnas handle",  "10.99", null, 18);
        verifyParsedItem(iterator.next(), "ar' ti c l", null, null, 19);
        verifyParsedItem(iterator.next(), "betydlig    lf3t181",  "3.00", null, 20);
        verifyParsedItem(iterator.next(), "artic l e 502j71tl2", null, null, 22);
        verifyParsedItem(iterator.next(), "raffig fi",  "2.00", null, 23);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "60.94",25);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst # 1{1024530:32",41);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "63.99",29);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/8",39);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "total items:                               9",31);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "3.05",27);

    }

    @Test
    public void receipt_2015_04_04_21_31_43()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_31_43, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("ikea", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "sams nem n 10", null, null, 5);
        verifyParsedItem(iterator.next(), "kvartal",  "9.00", null, 8);
        verifyParsedItem(iterator.next(), "ingamaj panel",  "18.00", null, 10);
        verifyParsedItem(iterator.next(), "ledare led bulb",  "8.99", null, 12);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "37.79",18);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "total items:                        -3",20);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst li rl02453032",31);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/7/20",29);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "35.99",13);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "1.80",16);

    }

    @Test
    public void receipt_2015_04_04_21_33_31()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_33_31, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("ikea", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        //TODO item number
        verifyParsedItem(iterator.next(), "article 4~ 88 11 00", null, null, 5);
        verifyParsedItem(iterator.next(), "kolon n floor",  "24.999", null, 6);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "24.99",7);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                5.00 %          1.25",9);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "26.24",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/7/20",18);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "total items :",11);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "1.25",9);

    }

    @Test
    public void receipt_2015_04_04_21_33_37()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_33_37, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("ikea", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "~1 r.llt\"fr  i    109    sat 10  9",  "sun.0", null, 4);
        verifyParsedItem(iterator.next(), "papaja pint pot",  "3.99", null, 10);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "16.98",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "17.83",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "kardemumma n                        12.99           g",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/8",30);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.85",13);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst         5.00 %                  0.85",13);

    }

    @Test
    public void receipt_2015_04_04_21_44_13()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_44_13, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("ikea", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(5,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "besta nn push",  "4.009", null, 8);
        verifyParsedItem(iterator.next(), "besta susp rl",  "16.009", null, 10);
        verifyParsedItem(iterator.next(), "besta vara door",  "30.009", null, 13);
        verifyParsedItem(iterator.next(), "besta shlf",  "57.009", null, 16);
        verifyParsedItem(iterator.next(), "kallax shelving",  "224.979", null, 18);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "348.57",23);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card number :                   1841",41);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "tota items:                              9",24);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst # r102453032",63);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/3/7",61);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "331.97",20);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "16.60",22);

    }

    @Test
    public void receipt_2015_04_04_21_58_34()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_58_34, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("ikea", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "sams return id", null, null, 2);
//        verifyParsedItem(iterator.next(), "retun1",  "43016.0", null, 3);
        verifyParsedItem(iterator.next(), "gommaren univ",  "39.98", null, 5);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "41.98",11);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "total items :                           -2",12);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst # r1 02453032",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/7/5",19);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "39.98",8);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "2.00",10);

    }

    @Test
    public void receipt_2015_04_04_22_11_23()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_11_23, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("ikea", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "article ~~3~    \\/j",  "128", null, 4);
        verifyParsedItem(iterator.next(), "hemnes n desk",  "299.00", null, 7);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "470.40",11);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "total items:                             2",12);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst ii r102453032",29);
        verifyParsedField(fieldValues, ReceiptFieldType.Time, "time:",38);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/7/12",28);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "448.00",8);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "22.40",10);

    }

    @Test
    public void receipt_2015_04_04_22_11_28()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_11_28, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("ikea", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "ml>n-fr i 109",  ".106", null, 2);
//        verifyParsedItem(iterator.next(), "retw-n", null, null, 5);
        verifyParsedItem(iterator.next(), "leksvik desk",  "149.00", null, 6);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "156.45",11);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "total items :                          -1",13);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst # r1024530a2",22);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/7/19",20);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "149.00",8);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "7.45",10);

    }


}