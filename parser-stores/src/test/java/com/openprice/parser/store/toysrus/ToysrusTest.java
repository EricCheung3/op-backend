package com.openprice.parser.store.toysrus;

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
public class ToysrusTest extends AbstractReceiptParserIntegrationTest {

    @Value("classpath:/testFiles/toysrus/branch_3521_SOUTHPARK/2014_12_06_22_32_51.jpg.hengshuai.txt")
    private Resource receipt_2014_12_06_22_32_51;

    @Value("classpath:/testFiles/toysrus/branch_3521_SOUTHPARK/2015_02_09_13_31_48.jpg.henryHuang.txt")
    private Resource receipt_2015_02_09_13_31_48;

    @Value("classpath:/testFiles/toysrus/branch_3521_SOUTHPARK/2015_07_03_14_09_29.jpg.hengshuai.txt")
    private Resource receipt_2015_07_03_14_09_29;

    @Value("classpath:/testFiles/toysrus/branch_3521_SOUTHPARK/2015_10_10_14_49_14.jpg.txt")
    private Resource receipt_2015_10_10_14_49_14;

    @Test
    public void receipt_2014_12_06_22_32_51()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2014_12_06_22_32_51, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("toysrus", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "h - orajel    toothpas",  "3.999", null, 15);
        verifyParsedItem(iterator.next(), "baby's first tooth",  "9.999", null, 16);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "2",23);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "your cashier was : rose c.",24);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card number mastercard                     14.68",21);
//        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "pproved 0 6 6 ;4 z",59);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "13.98",17);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "5% gst                                             .70",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2013/2/15",66);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "14.68",19);
    }

    @Test
    public void receipt_2015_02_09_13_31_48()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_13_31_48, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("toysrus", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "animal alley sleep",  "14.999", null, 19);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "total count of items:",27);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "refunds with receipt only, within 45",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 10533 6788 rtoool",48);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "your cashier was : gloria",28);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card number      ***************8017",24);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved 07267i",60);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "14.99",20);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "5% gst                                .75",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/12/21",66);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "15.74",22);
    }

    @Test
    public void receipt_2015_07_03_14_09_29()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_03_14_09_29, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("toysrus", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(6,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "widescreen light d",  "39.999", null, 16);
        verifyParsedItem(iterator.next(), "price guarantee",  "15.00", null, 17);
        verifyParsedItem(iterator.next(), "widescreen light d",  "39.999", null, 18);
        verifyParsedItem(iterator.next(), ", price guarantee",  "15.00", null, 19);
        verifyParsedItem(iterator.next(), "widescreen light d",  "39.999", null, 20);
        verifyParsedItem(iterator.next(), "price guarantee",  "15.00", null, 21);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "total cou nt of item s :",35);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "refunds w   it h receipt on ly , wi thi n 45",4);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gstn1 0533 67 88 rt0 00 1",56);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "your cashier wa s: pam l.",36);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved 030018",66);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "24.99",22);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "5% gst                                             1.25",23);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/6/30",72);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "26.24",24);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "au thori ze d 9655 m o 088930",29);

    }

    @Test
    public void receipt_2015_10_10_14_49_14()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_49_14, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("toysrus", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "assisted sales car 135437    .01",  "0.00", null, 21);
        verifyParsedItem(iterator.next(), "ex-fisher pricespa 028293    79.99",  "79.99", null, 22);
        verifyParsedItem(iterator.next(), "modware toddler ut 861073    12.99",  "12.99", null, 23);
        verifyParsedItem(iterator.next(), "ex-explora feeding 667099    5.99",  "5.99", null, 24);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "98.97",27);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card number      ****** * ********7403",31);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "5%     gst                                                4.95",28);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/1",90);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved 003582",83);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "your cashier was:                     emi ly m.",52);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "103.92",30);
    }


}