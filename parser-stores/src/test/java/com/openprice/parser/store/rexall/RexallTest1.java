package com.openprice.parser.store.rexall;

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
public class RexallTest1 extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testFiles/rexall/branch_110_51Ave/2015_02_12_23_21_33.jpg.random.txt")
    private Resource receipt_2015_02_12_23_21_33;

    @Value("classpath:/testFiles/rexall/branch_110_51Ave/2015_05_02_21_53_20.jpg.momingzhen159.txt")
    private Resource receipt_2015_05_02_21_53_20;

    @Value("classpath:/testFiles/rexall/branch_110_51Ave/2015_06_04_21_14_00.jpg.henryHuang.txt")
    private Resource receipt_2015_06_04_21_14_00;

    @Value("classpath:/testFiles/rexall/branch_110_51Ave/2015_07_02_18_02_01.jpg.henryHuang.txt")
    private Resource receipt_2015_07_02_18_02_01;

    @Value("classpath:/testFiles/rexall/branch_110_51Ave/2015_07_02_18_03_19.jpg.henryHuang.txt")
    private Resource receipt_2015_07_02_18_03_19;

    @Value("classpath:/testFiles/rexall/branch_110_51Ave/2015_07_02_18_14_38.jpg.henryHuang.txt")
    private Resource receipt_2015_07_02_18_14_38;

    @Value("classpath:/testFiles/rexall/branch_110_51Ave/2015_07_18_18_22_28.jpg.fuqian.txt")
    private Resource receipt_2015_07_18_18_22_28;

    @Value("classpath:/testFiles/rexall/branch_110_51Ave/2015_07_21_10_48_04.jpg.henryHuang.txt")
    private Resource receipt_2015_07_21_10_48_04;

    @Value("classpath:/testFiles/rexall/branch_110_51Ave/2015_10_10_14_48_43.jpg.txt")
    private Resource receipt_2015_10_10_14_48_43;

    @Test
    public void receipt_2015_02_12_23_21_33()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_12_23_21_33, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rexall", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "aeroknife",  "14.999", null, 4);
        verifyParsedItem(iterator.next(), "gf f&pl shp",  "2.999", null, 6);
        verifyParsedItem(iterator.next(), "gf f&pl cnd",  "2.999", null, 8);
        verifyParsedItem(iterator.next(), "green fee bag",  "0.059", null, 10);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "items           4",21);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                    $1.05",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "22.07",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card number :                       ***'\"********2811",44);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "21.02",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/12",23);
    }

    @Test
    public void receipt_2015_05_02_21_53_20()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_05_02_21_53_20, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rexall", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "d drops baby 400 i",  "18.499", null, 3);
        verifyParsedItem(iterator.next(), "niver men",  "7.269", null, 6);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                                      $1 . 29",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/22",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "27.04",11);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "25.75",9);
    }

    @Test
    public void receipt_2015_06_04_21_14_00()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_06_04_21_14_00, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rexall", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "neilmed pediatric",  "15.749", null, 4);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "items",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "16.53",9);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                                  $0.79",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "***    cardholder copy                ***",51);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "15.74",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/5/11",17);
    }

    @Test
    public void receipt_2015_07_02_18_02_01()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_02_18_02_01, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rexall", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(11,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "savy home ft 3ply",  "0.599", null, 3);
        verifyParsedItem(iterator.next(), "savy home ft 3ply",  "0.599", null, 5);
        verifyParsedItem(iterator.next(), "savy home ft 3ply",  "0.0", null, 7);
        verifyParsedItem(iterator.next(), "savy home ft 3ply",  "0.599", null, 9);
        verifyParsedItem(iterator.next(), "savy home ft 3ply",  "0.599", null, 11);
        verifyParsedItem(iterator.next(), "savy home ft 3ply",  "0.599", null, 13);
        verifyParsedItem(iterator.next(), "oreo cookies",  "1.99", null, 15);
        verifyParsedItem(iterator.next(), "oreo cookies",  "1.99", null, 17);
        verifyParsedItem(iterator.next(), "skppy pnt btr smt",  "5.49", null, 19);
        verifyParsedItem(iterator.next(), "tide simply refre    6",  "2.0", null, 21);
        verifyParsedItem(iterator.next(), "green fee bag    2",  "0.0", null, 23);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "items       17",34);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                              $1.17",27);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "32.12",28);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "***   cardholder copy               ***",65);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "31.05",26);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/13",36);
    }

    @Test
    public void receipt_2015_07_02_18_03_19()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_02_18_03_19, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rexall", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "hylands cough nas",  "9.999", null, 5);
        verifyParsedItem(iterator.next(), "on line lottery",  "10.00", null, 7);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "20.49",12);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "' gst    ,                                                $0.50",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "***   cardholder copy                     111",50);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "19.99",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/23",18);
    }

    @Test
    public void receipt_2015_07_02_18_14_38()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_02_18_14_38, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rexall", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(6,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "parmalat skim mil",  "4.39", null, 4);
        verifyParsedItem(iterator.next(), "alb btl dep",  "0.25", null, 6);
        verifyParsedItem(iterator.next(), "alb btl levy",  "0.07", null, 8);
        verifyParsedItem(iterator.next(), "neilmed pediatric",  "17.499", null, 10);
        verifyParsedItem(iterator.next(), "green fee bag",  "0.059", null, 12);
        verifyParsedItem(iterator.next(), "on line lottery",  "20.00", null, 14);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "43.13",19);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                                   $0.88",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card numb er.                80 ;7",41);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "42.25",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/27",25);
    }

    @Test
    public void receipt_2015_07_18_18_22_28()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_18_22_28, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rexall", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "nyx auto eyebrow",  "10.389", null, 3);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "items        2",14);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                              $ 0 . 52",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "10.90",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "***    cardholder copy             ***",42);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "10.38",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/7/18",16);
    }

    @Test
    public void receipt_2015_07_21_10_48_04()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_10_48_04, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rexall", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "neilmed pediatric",  "26.249", null, 4);
        verifyParsedItem(iterator.next(), "visine m - s 15ml",  "6.999", null, 6);
        verifyParsedItem(iterator.next(), "canesten top crea",  "11.499", null, 8);
        verifyParsedItem(iterator.next(), "green fee bag",  "0.059", null, 10);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "it ems     5",21);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                         $ 2.24",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "47.01",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "***   cardholder copy          ***",60);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "44.77",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/6/3",23);
    }

    @Test
    public void receipt_2015_10_10_14_48_43()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_48_43, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rexall", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "se cloth",  "5.599", null, 5);
        verifyParsedItem(iterator.next(), "sum eve deo isl",  "6.399", null, 7);
        verifyParsedItem(iterator.next(), "aquafina water",  "1.899", null, 9);
        verifyParsedItem(iterator.next(), "alb btl dep",  "0.10", null, 11);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "items * 3",22);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                 $0.69",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "14.66",16);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "* * * * * * * * * * * * * * * * * * * *",67);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "13.97",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/5",24);
    }


}