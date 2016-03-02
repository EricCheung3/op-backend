package com.openprice.parser.store.rcss;

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
public class RCSSTest1 extends AbstractReceiptParserIntegrationTest{
    @Value("classpath:/testFiles/rcss/abbyy/2015_11_11_calgarytrail.txt")
    private Resource receipt_2015_11_11_calgarytrail;

    @Value("classpath:/testFiles/rcss/calgarytrail/2014_12_06_22_13_28.txt")
    private Resource receipt_2014_12_06_22_13_28;

    @Value("classpath:/testFiles/rcss/calgarytrail/2015_02_01_14_17_01.jpg.henryHuang.txt")
    private Resource receipt_2015_02_01_14_17_01;

    @Value("classpath:/testFiles/rcss/calgarytrail/2015_02_09_13_15_09.jpg.henryHuang.txt")
    private Resource receipt_2015_02_09_13_15_09;

    @Value("classpath:/testFiles/rcss/calgarytrail/2015_02_09_13_15_42.jpg.henryHuang.txt")
    private Resource receipt_2015_02_09_13_15_42;

    @Value("classpath:/testFiles/rcss/calgarytrail/2015_02_09_13_16_07.jpg.henryHuang.txt")
    private Resource receipt_2015_02_09_13_16_07;

    @Value("classpath:/testFiles/rcss/calgarytrail/2015_02_09_13_16_07.jpg.henryHuang_train.txt")
    private Resource receipt_2015_02_09_13_16_07_train;

    @Value("classpath:/testFiles/rcss/calgarytrail/2015_02_09_13_16_22.jpg.henryHuang.txt")
    private Resource receipt_2015_02_09_13_16_22;

    @Value("classpath:/testFiles/rcss/calgarytrail/2015_02_09_13_17_18.jpg.henryHuang.txt")
    private Resource receipt_2015_02_09_13_17_18;

    @Value("classpath:/testFiles/rcss/calgarytrail/2015_04_04_22_22_32.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_22_32;

    @Value("classpath:/testFiles/rcss/calgarytrail/2015_04_04_22_22_40.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_22_40;

    @Value("classpath:/testFiles/rcss/calgarytrail/2015_05_02_22_18_59.jpg.haipeng.txt")
    private Resource receipt_2015_05_02_22_18_59;

    @Value("classpath:/testFiles/rcss/calgarytrail/2015_06_04_21_23_19.jpg.henryHuang.txt")
    private Resource receipt_2015_06_04_21_23_19;

    @Value("classpath:/testFiles/rcss/calgarytrail/2015_06_04_21_25_41.jpg.henryHuang.txt")
    private Resource receipt_2015_06_04_21_25_41;

    @Value("classpath:/testFiles/rcss/calgarytrail/2015_06_14_21_42_17.jpg.dana.txt")
    private Resource receipt_2015_06_14_21_42_17;

    @Value("classpath:/testFiles/rcss/calgarytrail/2015_07_02_18_03_26.jpg.henryHuang.txt")
    private Resource receipt_2015_07_02_18_03_26;

    @Value("classpath:/testFiles/rcss/calgarytrail/2015_07_02_18_03_47.jpg.henryHuang.txt")
    private Resource receipt_2015_07_02_18_03_47;

    @Value("classpath:/testFiles/rcss/calgarytrail/2015_07_02_18_13_07.jpg.henryHuang.txt")
    private Resource receipt_2015_07_02_18_13_07;

    @Value("classpath:/testFiles/rcss/calgarytrail/2015_07_18_18_22_04.jpg.fuqian.txt")
    private Resource receipt_2015_07_18_18_22_04;

    @Value("classpath:/testFiles/rcss/calgarytrail/2015_07_18_18_26_52.jpg.fuqian.txt")
    private Resource receipt_2015_07_18_18_26_52;

    @Value("classpath:/testFiles/rcss/calgarytrail/2015_07_21_10_49_11.jpg.henryHuang.txt")
    private Resource receipt_2015_07_21_10_49_11;

    @Value("classpath:/testFiles/rcss/calgarytrail/2015_07_21_10_50_33.jpg.henryHuang.txt")
    private Resource receipt_2015_07_21_10_50_33;

    @Value("classpath:/testFiles/rcss/noisy/nullPointer1.txt")
    private Resource receipt_nullPointer1;

    @Value("classpath:/testFiles/rcss/noisy/rcssPhone1.txt")
    private Resource receipt_rcssPhone1;

    @Value("classpath:/testFiles/rcss/southcommon/2015_02_09_13_15_25.jpg.henryHuang.txt")
    private Resource receipt_2015_02_09_13_15_25;

    @Value("classpath:/testFiles/rcss/southcommon/2015_02_09_13_32_16.jpg.henryHuang.txt")
    private Resource receipt_2015_02_09_13_32_16;

    @Value("classpath:/testFiles/rcss/southcommon/2015_04_04_21_25_02.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_25_02;

    @Value("classpath:/testFiles/rcss/southcommon/2015_04_04_21_28_21.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_28_21;

    @Value("classpath:/testFiles/rcss/southcommon/2015_04_04_21_34_12.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_34_12;

    @Value("classpath:/testFiles/rcss/southcommon/2015_04_04_21_41_31.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_41_31;

    @Value("classpath:/testFiles/rcss/southcommon/2015_04_04_21_44_23.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_44_23;

    @Value("classpath:/testFiles/rcss/southcommon/2015_04_04_21_54_42.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_54_42;

    @Value("classpath:/testFiles/rcss/southcommon/2015_04_04_21_54_58.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_54_58;

    @Value("classpath:/testFiles/rcss/southcommon/2015_04_04_21_55_47.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_55_47;

    @Value("classpath:/testFiles/rcss/southcommon/2015_04_04_21_59_17.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_59_17;

    @Value("classpath:/testFiles/rcss/southcommon/2015_04_04_22_03_18.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_03_18;

    @Value("classpath:/testFiles/rcss/southcommon/2015_04_04_22_03_26.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_03_26;

    @Value("classpath:/testFiles/rcss/southcommon/2015_07_21_15_19_03.jpg.beata.txt")
    private Resource receipt_2015_07_21_15_19_03;

    @Test
    public void multilineItemTest1() throws Exception {
        final List<String> lines = new ArrayList<>();
        lines.add("    4011          BANANA                     MftJ");
        lines.add("0.940 kg 8 $1.73/kg                              1.60");
        lines.add("    4011          BANANA                     MftJ");
        lines.add("0.940 kg @ $1.73/kg                              1.60");//should not contain this line
        lines.add("4068            ONION GREN                MRJ      0,67");
        lines.add("4068            ONION GREN                MRJ      0,67");
        lines.add("31-MEA1S");
        lines.add("2021000          DUCKS FR7N                MRJ      15.23");

        ParsedReceipt receipt = simpleParser.parseLines(lines);
        printResult(receipt);
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

        assertEquals(5,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "banana    mftj",  "1.60", null, 0);
        verifyParsedItem(iterator.next(), "banana",  "1.60", null, 2);
        verifyParsedItem(iterator.next(), "onion gren    mrj",  "067", null, 4);//TODO price formatter
        verifyParsedItem(iterator.next(), "onion gren    mrj",  "067", null, 5);
        verifyParsedItem(iterator.next(), "ducks fr7n    mrj",  "15.23", null, 7);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "",-1);

    }

    @Test
    public void multilineItemTest2() throws Exception {
        final List<String> lines = new ArrayList<>();
        lines.add("       21-GROCERY");
        lines.add("07323400310 YELW CALROS          RICE   MR-.i");
        lines.add("    $24.88 Int 4. $26.38 ea");
        lines.add("2 8 $24.88 list 4                              49.76");//TODO hard to detect this is not an item line; so no fix applied to the previous item price
        lines.add("total 10");

        ParsedReceipt receipt = simpleParser.parseLines(lines);
        printResult(receipt);
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "yelw calros",  "rice", null, 1);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "",-1);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "10",4);

    }
    @Test
    public void receipt_2015_11_11_calgarytrail()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_11_11_calgarytrail, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(9,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "yelw calros    rice",  "49.76", null, 8);//TODO why this is different from multilineItemTest2
        verifyParsedItem(iterator.next(), "k dgon cook    wine    mrj",  "2.69", null, 11);
        verifyParsedItem(iterator.next(), "organic 2% milk",  "8.98", "organic 2% milk_06870030942", 13);
        verifyParsedItem(iterator.next(), "rooster garlic",  "0.68", "rooster garlic_06038388591", 17);
        verifyParsedItem(iterator.next(), "banana",  "1.60", "banana_4011", 18);
        verifyParsedItem(iterator.next(), "onion green",  "067", "onion green_4068", 20);
        verifyParsedItem(iterator.next(), "ducks fr7n    mrj",  "15.23", null, 22);
        verifyParsedItem(iterator.next(), "ducks frzh    mrj",  "16.81", null, 23);
        verifyParsedItem(iterator.next(), "hairtail",  "7.36", "hairtail_77016160104", 25);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "",-1);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big uri fresh, law on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "104.73",29);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "rcss 1570 - 4821 ca.garv mil",1);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",1);
        verifyParsedField(fieldValues, ReceiptFieldType.Deposit, "deposii 1                                        0.25",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "ecology fee                                      0.08",14);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "104.73",27);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card#;",5);
    }

    @Test
    public void receipt_2014_12_06_22_13_28()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2014_12_06_22_13_28, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_02_01_14_17_01()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_01_14_17_01, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_02_09_13_15_09()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_13_15_09, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_02_09_13_15_42()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_13_15_42, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_02_09_13_16_07()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_13_16_07, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_02_09_13_16_07_train()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_13_16_07_train, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_02_09_13_16_22()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_13_16_22, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_02_09_13_17_18()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_13_17_18, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_22_22_32()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_22_32, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_22_22_40()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_22_40, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_05_02_22_18_59()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_05_02_22_18_59, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_06_04_21_23_19()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_06_04_21_23_19, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_06_04_21_25_41()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_06_04_21_25_41, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_06_14_21_42_17()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_06_14_21_42_17, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_02_18_03_26()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_02_18_03_26, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_02_18_03_47()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_02_18_03_47, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_02_18_13_07()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_02_18_13_07, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_18_18_22_04()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_18_22_04, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_18_18_26_52()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_18_26_52, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_21_10_49_11()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_10_49_11, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_21_10_50_33()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_10_50_33, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_nullPointer1()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_nullPointer1, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_rcssPhone1()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_rcssPhone1, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_02_09_13_15_25()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_13_15_25, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_02_09_13_32_16()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_13_32_16, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_25_02()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_25_02, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_28_21()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_28_21, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_34_12()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_34_12, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_41_31()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_41_31, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_44_23()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_44_23, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_54_42()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_54_42, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_54_58()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_54_58, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_55_47()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_55_47, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_59_17()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_59_17, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_22_03_18()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_03_18, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_22_03_26()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_03_26, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_21_15_19_03()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_15_19_03, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }


}
