package com.openprice.parser.store.sobeys;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.openprice.common.TextResourceUtils;
import com.openprice.parser.ChainRegistry;
import com.openprice.parser.ParsedField;
import com.openprice.parser.ParsedItem;
import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.store.AbstractReceiptParserIntegrationTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class SobeysTest1 extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testfiles/sobeys/branch_11_111st/2015_04_04_21_31_48.jpg.jingwang.txt")
    private Resource receipt_31_48;

    @Value("classpath:/testfiles/sobeys/branch_11_111st/2015_04_04_21_32_19.jpg.jingwang.txt")
    private Resource receipt_32_19;

    @Value("classpath:/testfiles/sobeys/branch_11_111st/2015_04_04_21_33_42.jpg.jingwang.txt")
    private Resource receipt_33_42;

    @Value("classpath:/testfiles/sobeys/branch_11_111st/2015_04_04_21_41_18.jpg.jingwang.txt")
    private Resource receipt_41_18;

    @Value("classpath:/testfiles/sobeys/branch_11_111st/2015_04_04_21_59_07.jpg.jingwang.txt")
    private Resource receipt_59_07;

    @Value("classpath:/testfiles/sobeys/branch_20_111st/2015_02_09_13_31_53.jpg.henryHuang.txt")
    private Resource receipt_31_53;

    @Value("classpath:/testfiles/sobeys/branch_82_112st/2015_07_03_15_35_54.jpg.hengshuai.txt")
    private Resource receipt_35_54;

    @Value("classpath:/testfiles/sobeys/branch_20_111st/2015_02_09_13_32_01.jpg.henryHuang.txt")
    private Resource receipt_32_01;

    @Value("classpath:/testfiles/sobeys/branch_939_james_mowatt_trail/2015_04_04_21_47_26.jpg.jingwang.txt")
    private Resource receipt_47_26;

    @Value("classpath:/testfiles/sobeys/phone/yuanji_19Feb16.txt")
    private Resource receipt_yuan19Feb16;

    @Value("classpath:/testfiles/sobeys/branch_82_112st/HuFeb24.txt")
    private Resource receipt_HuFeb24;

    @Inject
    private ChainRegistry chainRegistry;

    @Test
    public void receipt_31_48() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_31_48, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        assertEquals("sobeys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(5,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "herb cilantro", "0.99", null, 11);
        verifyParsedItem(iterator.next(), "corn tray 4pk 60'", "4.49", null, 12);
        verifyParsedItem(iterator.next(), "sa1mon nuggets 2430/", "5.97", null, 13);
        verifyParsedItem(iterator.next(), "comp crab", "3.11", null, 14);
        verifyParsedItem(iterator.next(), "jalapeno peppers", "8.99", null, 15);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "5",25);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "23.55",16);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/16",29);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "total tax                      $0.00",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "23.55",18);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "cs t # 8 ~~5 :i8 8738",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval code:           986348",22);

    }

    @Test
    public void receipt_32_19() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_32_19, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        assertEquals("sobeys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "cucumber hh sdls", "1.49", null, 7);
        verifyParsedItem(iterator.next(), "smoked ham", "13.99", null, 8);
        verifyParsedItem(iterator.next(), "bean sprouts 340g", "1.99", null, 9);

        verifyParsedField(fieldValues, ReceiptFieldType.Total, "17.47",12);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "3",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "auth # 005030                    ref # 00000081",27);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst # 89558 8788",5);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/12/5",38);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "17.47",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "served by : gurkirat",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",31);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card mastercard                  rcpt 2051001",24);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "total tax                     $0.00",11);

    }

    @Test
    public void receipt_33_42() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_33_42, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        assertEquals("sobeys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "herb cilantro", "0.89", null, 7);
        verifyParsedItem(iterator.next(), "ginger root", "0.26", null, 9);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "total tax                 $0.00",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval code:         088212",16);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst i# 89558 8788",5);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/10/26",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "served by: bonnieb",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "1.15",12);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "2",19);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "1.15",10);
    }

    @Test
    public void receipt_41_18() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_41_18, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        assertEquals("sobeys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(5,receipt.getItems().size());
        assertEquals(5,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "cabbage greon", "1.60", null, 8);
        verifyParsedItem(iterator.next(), "bananas", "3.36", null, 10);
        verifyParsedItem(iterator.next(), "clementines", "6.99", null, 11);
        verifyParsedItem(iterator.next(), "grapes black sdls", "6.78", null, 13);
        verifyParsedItem(iterator.next(), "blueberries 6oz", "3.99", null, 14);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "served bv : shirley",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "auth # 00933b                      ref # 00000139",29);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/3/9",41);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst # 89558 8788",5);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "22.72",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card mastercan1                    r   c pt 1494000",26);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "22.72",15);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "total tax          $0.00",16);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",33);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "5", 20);

    }

    @Test
    public void receipt_59_07() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_59_07, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        assertEquals("sobeys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "cucumber hh sd1s", "0.99", null, 7);
        verifyParsedItem(iterator.next(), "1oinskewers", "1.999", null, 8);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval code :          478551",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "3.08",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "sarved by : bonni eb",6);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst # 89558 8788",5);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "2",18);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "2.98",9);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "5% gst                   $0.10",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/10/11",20);
    }

    @Test
    public void receipt_31_53() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_31_53, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        assertEquals("sobeys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "grapes green sdls", "9.95", null, 9);
        verifyParsedItem(iterator.next(), "ferrero roch", "9.749", null, 10);
        verifyParsedItem(iterator.next(), "apples mcintosh sm", "5.25", null, 12);
        verifyParsedItem(iterator.next(), "lotto ticket", "10.00", null, 14);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "35.43",17);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst #82~:487119 rt001",5);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "13",25);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "34.94",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval code:          607589",21);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "5% cst                   $0.49",16);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "served by: brayden",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/12/27",29);

    }

    @Test
    public void receipt_32_01() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_32_01, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        assertEquals("sobeys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(10,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "milk 1% jug 4l", "4.65", null, 6);
        verifyParsedItem(iterator.next(), "ferrero r och", "12.99", null, 9);
        verifyParsedItem(iterator.next(), "jui ce frsh press apl", "3.49", null, 10);
        verifyParsedItem(iterator.next(), "almond breeze orig", "3.99", null, 13);
        verifyParsedItem(iterator.next(), "lfcrn/ rsnbrd", "2.99", null, 16);
        verifyParsedItem(iterator.next(), "lfcrn/ rsnbrd", "2.99", null, 18);
        verifyParsedItem(iterator.next(), "sir tip rst", "14.32", null, 20);
        verifyParsedItem(iterator.next(), "juice frsh press apl", "3.49", null, 21);
        verifyParsedItem(iterator.next(), "strawberries 1l b", "6.99", null, 24);
        verifyParsedItem(iterator.next(), "mccain pizza", "7.99", null, 25);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval cod~ :        750067           ,",32);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst #825487119 rt001",4);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "64.16",26);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "10",35);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/12/21",39);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "5% gst                     $0.65",27);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "served by: brayden",5);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "64.81",28);
    }

    @Test
    public void receipt_35_54() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_35_54, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        assertEquals("sobeys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(7,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "croissants 6s", "3.99", null, 7);
        verifyParsedItem(iterator.next(), "avocados hass lrg", "5.97", null, 9);
        verifyParsedItem(iterator.next(), "eggs 12ea", "3.39", null, 10);
        verifyParsedItem(iterator.next(), "camp eggs", "6.59", null, 11);
        verifyParsedItem(iterator.next(), "cheese slices", "3.99", null, 12);
        verifyParsedItem(iterator.next(), "strawberries 1lb", "2.99", null, 13);
        verifyParsedItem(iterator.next(), "2% milk jug", "4.89", null, 14);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 89558 87888",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "total tax                     $0.00",18);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "number of item",22);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/3/25",44);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "cardholder",42);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "32.14",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",36);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "served by: mi chea l f",6);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "32.14",17);

    }


    @Test
    public void receipt_47_26() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_47_26, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        assertEquals("sobeys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "englibay ale", "13.9990", null, 10);
        verifyParsedItem(iterator.next(), "cream ale 341ml 6pk", "13.9990", null, 12);
        verifyParsedItem(iterator.next(), "lager bttl 330ml 6pk", "49.4790", null, 15);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "ser ved by: sabrina",9);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "5",23);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 806832473",8);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "5% gst                       $3.87",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "84.32",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "auth # 009542                      ref # 00000016",30);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",34);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "80.45",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/9",42);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "cardholder", 40);
    }

    @Test
    public void receipt_yuan19Feb16() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_yuan19Feb16, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        assertEquals("sobeys", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "fuzzy peach", "2.699", null, 10);
        verifyParsedItem(iterator.next(), "cake wht", "19.99", null, 12);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "22.68",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "22",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "auth # 036574                            ref # 00000120",41);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",47);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card visa                                rcpt 8645000",38);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "served by: fast lane 3",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "5% gst                                             $0.13",16);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 814443388rt0001",3);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "2",22);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/5",40);
    }

}
