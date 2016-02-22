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

@RunWith(SpringJUnit4ClassRunner.class)
public class ShoppersTest1 extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testFiles/Shoppers/branch_10907_82_AVE/2015_10_10_15_20_43.jpg.txt")
    private Resource receipt_2015_10_10_15_20_43;

    @Value("classpath:/testFiles/Shoppers/branch_10907_82_AVE/2015_10_10_15_20_43_noDigitDate.jpg.txt")
    private Resource receipt_2015_10_10_15_20_43_noDigitDate;

    @Value("classpath:/testFiles/Shoppers/branch_10907_82_AVE/2015_10_10_15_20_43_noLiteralDate.jpg.txt")
    private Resource receipt_2015_10_10_15_20_43_noLiteralDate;

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
        //TODO two dates are available, which do you want to keep?
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/9/12",72);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "author. #:             062387",74);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "17.88",21);

    }

    @Test
    public void receipt_2015_10_10_15_20_43_noDigitDate() throws Exception{
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_15_20_43_noDigitDate, (line)-> receiptLines.add(line));
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
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/9/12",63);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "author. #:             062387",74);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "17.88",21);

    }

    @Test
    public void receipt_2015_10_10_15_20_43_noLiteralDate() throws Exception{
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_15_20_43_noLiteralDate, (line)-> receiptLines.add(line));
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
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/9/12",72);
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
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "nn eggs",  "n", null, 13);
        verifyParsedItem(iterator.next(), "loacker wfr hlznut    n",  "2.79", null, 14);
        verifyParsedItem(iterator.next(), "mr,noodles bowl    n",  "2.49", null, 15);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card number:                    x x x x x x x x x x x *4767",74);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "acct        : chequing                           $         18.26",70);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "invoice number:                 10087470",78);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst #: 84447 0567 rt0002",49);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "17.76",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "author. #:                      063600",77);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "18.26",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/14",75);

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
        assertEquals(6,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "fudgee-o cookies",  "n", null, 11);
        verifyParsedItem(iterator.next(), "pc popcorn",  "9", null, 12);
        verifyParsedItem(iterator.next(), "hershey almond mtp    g",  "4.99", null, 13);
        verifyParsedItem(iterator.next(), "klondike breyers    g",  "7.49", null, 14);
        verifyParsedItem(iterator.next(), "mr,noodles bowl    n",  "2.49", null, 15);
        verifyParsedItem(iterator.next(), "dairyland milk    n",  "2.49", null, 16);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card number:        ************4767",75);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "acct   : chequing                $      23.63",71);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "invoice number:     10087690",79);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst       84447 0567 rt0002",50);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "22.86",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "author. #:          038927",78);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "23.63",24);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/10",76);

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
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "senior bus pass    nx",  "14.00", null, 11);
        verifyParsedItem(iterator.next(), "child bus tickets    nx",  "21.00", null, 12);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card n  umber:         'k'k'k'k u 'k'k'k'l<n 1833",39);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "acct : mastercard                  $          35.00",37);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "invoice number:        10097670",43);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst #: 80474 6220 rt0001",16);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "35.00",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "autho  r. #:           0191'30",42);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "35.00",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/12/19",40);
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
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "carlton everyday    g",  "3.99", null, 10);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "acct        : mastercard                             4.19",44);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "3.99",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/6/18",47);
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
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "band-aid bandages    g",  "7.99", null, 11);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card n  umber :  ttllltlltttl801 7",76);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "acct    : visa                             $            8.39",73);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "transaction not completed",61);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "invoice number : 10105671",80);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst # : 85877 9820 rt0001",28);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "7.99",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "author . # :     01145i",79);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "8.39",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/10",77);

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
        assertEquals(0,receipt.getItems().size());
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card    *******\"*'* \"8017",5);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "receipt number",9);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "auth# 024481             01-027",22);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "1.26",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/6/16",7);
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
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "motrin child    g",  "8.99", null, 12);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card numbefi �        **** ****** **8017",54);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "acct        visa                                 $       9.44",51);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "invoh?e n umber       10120640",58);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst u� 85877 9820 rt0001",27);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "8.99",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "author. u�            027661",57);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "9.44",16);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/7/4",55);
    }



}
