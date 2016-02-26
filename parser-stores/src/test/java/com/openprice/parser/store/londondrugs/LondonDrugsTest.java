package com.openprice.parser.store.londondrugs;

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
public class LondonDrugsTest extends AbstractReceiptParserIntegrationTest {

    @Value("classpath:/testfiles/londondrugs/branch_14951StonyPlain/2015_07_18_18_33_38.jpg.natalie.txt")
    private Resource receipt_2015_07_18_18_33_38;

    @Value("classpath:/testfiles/londondrugs/branch_14951StonyPlain/2015_07_18_18_35_05.jpg.natalie.txt")
    private Resource receipt_2015_07_18_18_35_05;

    @Value("classpath:/testfiles/londondrugs/branch_14951StonyPlain/2015_07_18_18_35_15.jpg.natalie.txt")
    private Resource receipt_2015_07_18_18_35_15;

    @Value("classpath:/testfiles/londondrugs/branch_14951StonyPlain/2015_07_18_18_35_24.jpg.natalie.txt")
    private Resource receipt_2015_07_18_18_35_24;

    @Value("classpath:/testfiles/londondrugs/branch_14951StonyPlain/2015_07_18_18_36_12.jpg.natalie.txt")
    private Resource receipt_2015_07_18_18_36_12;

    @Value("classpath:/testfiles/londondrugs/branch_14951StonyPlain/2015_07_18_18_36_20.jpg.natalie.txt")
    private Resource receipt_2015_07_18_18_36_20;

    @Value("classpath:/testfiles/londondrugs/branch_14951StonyPlain/2015_07_18_18_36_29.jpg.natalie.txt")
    private Resource receipt_2015_07_18_18_36_29;

    @Value("classpath:/testfiles/londondrugs/branch_14951StonyPlain/2015_07_18_18_36_38.jpg.natalie.txt")
    private Resource receipt_2015_07_18_18_36_38;

    @Value("classpath:/testfiles/londondrugs/branch_14951StonyPlain/2015_07_18_18_38_11.jpg.natalie.txt")
    private Resource receipt_2015_07_18_18_38_11;

    @Value("classpath:/testfiles/londondrugs/branch_14951StonyPlain/2015_07_18_18_38_20.jpg.natalie.txt")
    private Resource receipt_2015_07_18_18_38_20;

    @Value("classpath:/testfiles/londondrugs/branch_14951StonyPlain/2015_07_18_18_38_29.jpg.natalie.txt")
    private Resource receipt_2015_07_18_18_38_29;

    @Value("classpath:/testfiles/londondrugs/branch_14951StonyPlain/2015_07_18_18_38_38.jpg.natalie.txt")
    private Resource receipt_2015_07_18_18_38_38;

    @Value("classpath:/testfiles/londondrugs/branch_14951StonyPlain/2015_07_18_18_38_48.jpg.natalie.txt")
    private Resource receipt_2015_07_18_18_38_48;

    @Value("classpath:/testfiles/londondrugs/branch_14951StonyPlain/2015_07_18_18_38_52.jpg.natalie.txt")
    private Resource receipt_2015_07_18_18_38_52;

    @Value("classpath:/testfiles/londondrugs/branch_14951StonyPlain/2015_07_18_18_39_02.jpg.natalie.txt")
    private Resource receipt_2015_07_18_18_39_02;

    @Value("classpath:/testfiles/londondrugs/branch_14951StonyPlain/2015_07_18_19_53_40.jpg.natalie.txt")
    private Resource receipt_2015_07_18_19_53_40;

    @Value("classpath:/testfiles/londondrugs/branch_14951StonyPlain/2015_07_18_19_53_50.jpg.natalie.txt")
    private Resource receipt_2015_07_18_19_53_50;

    @Value("classpath:/testfiles/londondrugs/branch_14951StonyPlain/2015_07_18_19_53_59.jpg.natalie.txt")
    private Resource receipt_2015_07_18_19_53_59;

    @Value("classpath:/testfiles/londondrugs/branch_14951StonyPlain/2015_07_18_19_55_53.jpg.natalie.txt")
    private Resource receipt_2015_07_18_19_55_53;

    @Value("classpath:/testfiles/londondrugs/branch_14951StonyPlain/2015_07_18_19_58_30.jpg.natalie.txt")
    private Resource receipt_2015_07_18_19_58_30;

    @Value("classpath:/testfiles/londondrugs/branch_88_170St/2015_04_04_21_31_37.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_31_37;

    @Value("classpath:/testfiles/londondrugs/branch_88_170St/2015_04_04_21_47_34.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_47_34;

    @Value("classpath:/testfiles/londondrugs/branch_88_170St/2015_06_29_16_20_29.jpg.random.txt")
    private Resource receipt_2015_06_29_16_20_29;

    @Value("classpath:/testfiles/londondrugs/branch_88_170St/2015_07_02_18_14_57.jpg.henryHuang.txt")
    private Resource receipt_2015_07_02_18_14_57;

    @Value("classpath:/testfiles/londondrugs/branch_88_170St/2015_07_02_18_15_05.jpg.henryHuang.txt")
    private Resource receipt_2015_07_02_18_15_05;

    @Value("classpath:/testfiles/londondrugs/branch_88_170St/2015_07_03_16_51_11.jpg.haipeng.txt")
    private Resource receipt_2015_07_03_16_51_11;



    @Test
    public void receipt_2015_07_18_18_33_38()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_18_33_38, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "20 disc logiix usb cube",  "29.999", null, 6);
        verifyParsedItem(iterator.next(), "20 disc continental cookie",  "2.99", null, 7);
        verifyParsedItem(iterator.next(), "20 disc model f purse kit",  "6.999", null, 8);
        verifyParsedItem(iterator.next(), "tax    1.48    bal",  "33.45", null, 10);//TODO not header matching
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "33.45",35);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/5/7",40);
    }

    @Test
    public void receipt_2015_07_18_18_35_05()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_18_35_05, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "avene scrub",  "22.409", null, 6);
        verifyParsedItem(iterator.next(), "20 disc kinder bueno",  "1.699", null, 7);
        verifyParsedItem(iterator.next(), "tax    1.19    bal",  "24.94", null, 9);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "24.94",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/7",2);
    }

    @Test
    public void receipt_2015_07_18_18_35_15()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_18_35_15, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(9,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "20 disc campbells soup",  "2.98", null, 5);
        verifyParsedItem(iterator.next(), "pepsi diet",  "2.999", null, 6);
        verifyParsedItem(iterator.next(), "20 disc always p/liners",  "4.999", null, 9);
        verifyParsedItem(iterator.next(), "fruit 2c",  "1.199", null, 10);
        verifyParsedItem(iterator.next(), "fruit 2c",  "1.199", null, 13);
        verifyParsedItem(iterator.next(), "20 disc airplus hug/heels",  "6.999", null, 16);
        verifyParsedItem(iterator.next(), "20 disc airplus insole",  "12.999", null, 17);
        verifyParsedItem(iterator.next(), "20 disc matador jerky",  "8.999", null, 18);
        //TODO missing item
//        verifyParsedItem(iterator.next(), "20 disc shopkins mrkt g",  "200919.999", null, 19);
        verifyParsedItem(iterator.next(), "tax    2.43    bal",  "54.23", null, 21);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "54.23",22);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/5/16",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Saving, "employee discount       11.40-",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "recycling fee             .02 g",14);

    }

    @Test
    public void receipt_2015_07_18_18_35_24()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_18_35_24, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(6,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "20 disc l/look puffs",  "4.999", null, 5);
        verifyParsedItem(iterator.next(), "20 disc dare vinia",  "3.69", null, 6);
        verifyParsedItem(iterator.next(), "tetley tea",  "3.99", null, 7);
        verifyParsedItem(iterator.next(), "20 disc secret leggings",  "10.999", null, 8);
        verifyParsedItem(iterator.next(), "welchs juice",  "3.79", null, 9);
        verifyParsedItem(iterator.next(), "tax    .64    bal",  "24.48", null, 13);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "24.48",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/28",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "recycling fee             .08",10);

    }

    @Test
    public void receipt_2015_07_18_18_36_12()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_18_36_12, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(7,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "itunes $100",  "100.00", null, 5);
        verifyParsedItem(iterator.next(), "20 disc cheerios",  "4.98", null, 6);
        verifyParsedItem(iterator.next(), "atkins endulge",  "5.999", null, 7);
        verifyParsedItem(iterator.next(), "20 disc twizzler",  "3.799", null, 8);
        verifyParsedItem(iterator.next(), "20 disc o'keeffe's cream",  "9.999", null, 9);
        verifyParsedItem(iterator.next(), "20 disc vitamin water",  "2.299", null, 10);
        verifyParsedItem(iterator.next(), "tax    .94    bal",  "123.87", null, 14);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "recycling fee              .01 g",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "123.87",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/15",2);
    }

    @Test
    public void receipt_2015_07_18_18_36_20()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_18_36_20, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "20 disc olay m/sculp",  "32.999", null, 5);
        verifyParsedItem(iterator.next(), "20 disc olay m/sculp",  "32.999", null, 6);
        verifyParsedItem(iterator.next(), "tax    2.64    bal",  "55.42", null, 8);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "55.42",9);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/22",2);
    }

    @Test
    public void receipt_2015_07_18_18_36_29()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_18_36_29, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(6,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "always p/liners",  "3.999", null, 5);
        verifyParsedItem(iterator.next(), "m&m's",  "3.799", null, 6);
        verifyParsedItem(iterator.next(), "m&m's",  "3.219", null, 8);
        verifyParsedItem(iterator.next(), "20 disc webber b 6 100mg",  "6.999", null, 9);
        verifyParsedItem(iterator.next(), "20 disc ocean spray juice",  "4.78", null, 10);
        verifyParsedItem(iterator.next(), "tax    .83    bal",  "21.95", null, 14);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "recycling                  .12",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "21.95",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/4/11",2);
    }

    @Test
    public void receipt_2015_07_18_18_36_38()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_18_36_38, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(6,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "20 disc space bag set",  "22.999", null, 9);
        verifyParsedItem(iterator.next(), "20 disc smartfood",  "1.499", null, 10);
        verifyParsedItem(iterator.next(), "20 disc lepage fun-tak blu",  "2.999", null, 11);
        verifyParsedItem(iterator.next(), "20 disc kleenex tissue",  "3.999", null, 12);
        verifyParsedItem(iterator.next(), "20 disc liqui",  "4.999", null, 13);
        verifyParsedItem(iterator.next(), "20 disc liquid plumr",  "4.999", null, 14);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "34.80",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/3/7",49);
    }

    @Test
    public void receipt_2015_07_18_18_38_11()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_18_38_11, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(16,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "20 disc l/prem remvr",  "3.999", null, 5);
        verifyParsedItem(iterator.next(), "child's book",  "4.499", null, 6);
        verifyParsedItem(iterator.next(), "book",  "4.499", null, 7);
        verifyParsedItem(iterator.next(), "20 disc sensodyne",  "4.989", null, 8);
        verifyParsedItem(iterator.next(), "wonka hearts",  "1.699", null, 9);
        verifyParsedItem(iterator.next(), "hershey heart",  "1.099", null, 10);
        verifyParsedItem(iterator.next(), "hershey heart",  "1.099", null, 11);
        verifyParsedItem(iterator.next(), "hershey heart",  "1.099", null, 12);
        verifyParsedItem(iterator.next(), "hershey heart",  "1.099", null, 13);
        verifyParsedItem(iterator.next(), "20 disc mayb mascara",  "9.499", null, 14);
        verifyParsedItem(iterator.next(), "womka sweetarts",  "2.299", null, 15);
        verifyParsedItem(iterator.next(), "womka sweetarts",  "1.719", null, 17);
        verifyParsedItem(iterator.next(), "mo nice & easy",  "5.999", null, 18);
        verifyParsedItem(iterator.next(), "mo nice & easy",  "5.999", null, 19);
        verifyParsedItem(iterator.next(), "nestea",  "1.799", null, 20);
        verifyParsedItem(iterator.next(), "tax    2.38    bal",  "50.06", null, 24);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "recycling fee             .02 g",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "50.06",25);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/13",2);
    }

    @Test
    public void receipt_2015_07_18_18_38_20()  throws Exception {
        log.debug("receipt_2015_07_18_18_38_20:");
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_18_38_20, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        //Iterator<ParsedItem> iterator = receipt.getItems().iterator();
       // Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
//        assertEquals(8,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "calbee snepea",  "2.299", null, 11);
//        verifyParsedItem(iterator.next(), "calbee snapea",  "1.719", null, 13);
//        verifyParsedItem(iterator.next(), "20 disc revlon nailclip",  "5.999", null, 14);
//        verifyParsedItem(iterator.next(), "calbee snapea",  "2.299", null, 15);
//        verifyParsedItem(iterator.next(), "cl pm2 calbee snapea",  "2.299", null, 16);
//        verifyParsedItem(iterator.next(), "20 disc ice melter jug",  "5.999", null, 17);
//        verifyParsedItem(iterator.next(), "nestle nesfruta",  "2.999", null, 18);
//        verifyParsedItem(iterator.next(), "nestle nesfruta",  "2.999", null, 19);
//        verifyParsedField(fieldValues, ReceiptFieldType.Total, "20.54",41);
//        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "(g)st .93",27);
//        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/26",46);
    }

    @Test
    public void receipt_2015_07_18_18_38_29()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_18_38_29, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "20 disc cubita coffee",  "4.49", null, 8);
        verifyParsedItem(iterator.next(), "20 disc conair dryer",  "29.999", null, 9);
        verifyParsedItem(iterator.next(), "20 disc smartfood",  "1.499", null, 13);
        verifyParsedItem(iterator.next(), "mott's lice bars",  "4.999", null, 14);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "recycling fee           .02 g",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "37.31",41);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/6/18",46);
    }

    @Test
    public void receipt_2015_07_18_18_38_38()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_18_38_38, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(5,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "waterbridge gums",  "2.299", null, 14);
        verifyParsedItem(iterator.next(), "waterbridge gums",  "1.719", null, 16);
        verifyParsedItem(iterator.next(), "nestle smarties",  "0.999", null, 18);
        verifyParsedItem(iterator.next(), "cottonelle cloths",  "0.999", null, 19);
        verifyParsedItem(iterator.next(), "cottonelle cloths",  "0.999", null, 20);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "18.23",22);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/6/12",43);
    }

    @Test
    public void receipt_2015_07_18_18_38_48()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_18_38_48, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(12,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "rf pr i/design sink stop",  "5.49", null, 6);
        verifyParsedItem(iterator.next(), "20 disc purell",  "1.99", null, 7);
        verifyParsedItem(iterator.next(), "20 disc ziploc space bag",  "12.49", null, 8);
        verifyParsedItem(iterator.next(), "20 disc vim cream",  "3.99", null, 9);
        verifyParsedItem(iterator.next(), "r/stover nsa",  "1.69", null, 10);
        verifyParsedItem(iterator.next(), "r/stover nsa",  "1.31", null, 12);
        verifyParsedItem(iterator.next(), "20 disc finish let dry",  "5.99", null, 13);
        verifyParsedItem(iterator.next(), "20 disc werter's",  "2.89", null, 14);
        verifyParsedItem(iterator.next(), "20 disc london gourmet",  "4.999", null, 15);
        verifyParsedItem(iterator.next(), "20 disc london gourmet",  "4.999", null, 16);
        verifyParsedItem(iterator.next(), "hawkins cheezies",  "3.999", null, 17);
        verifyParsedItem(iterator.next(), "20 disc corelle bowl",  "8.999", null, 18);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "40.42",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/6/11",47);
    }

    @Test
    public void receipt_2015_07_18_18_38_52()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_18_38_52, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(8,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "rf pe restoralax",  "29.999", null, 6);
        verifyParsedItem(iterator.next(), "haagen dazs",  "4.99", null, 7);
        verifyParsedItem(iterator.next(), "20 disc snickers bites",  "3.299", null, 8);
        verifyParsedItem(iterator.next(), "fruit",  "0.999", null, 9);
        verifyParsedItem(iterator.next(), "fruit",  "0.999", null, 12);
        verifyParsedItem(iterator.next(), "fruit",  "0.99", null, 15);
        verifyParsedItem(iterator.next(), "rf pr restoralax",  "29.996", null, 18);
        verifyParsedItem(iterator.next(), "20 disc restoralax",  "29.999", null, 20);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "recycling fee                     .02 g",16);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "13.96",22);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "chrd bhlrnce:           13.96",33);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/5/29",35);
    }

    @Test
    public void receipt_2015_07_18_18_39_02()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_18_39_02, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(11,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "lentil chips",  "2.799", null, 7);
        verifyParsedItem(iterator.next(), "kerr's candy",  "1.799", null, 8);
        verifyParsedItem(iterator.next(), "20 disc g.h.cretors",  "3.999", null, 9);
        verifyParsedItem(iterator.next(), "20 disc smith bros drops",  "2.999", null, 10);
        verifyParsedItem(iterator.next(), "20 disc regal caramels",  "1.499", null, 11);
        verifyParsedItem(iterator.next(), "20 disc planters peanuts",  "3.999", null, 12);
        verifyParsedItem(iterator.next(), "20 disc restoralax",  "29.999", null, 13);
        verifyParsedItem(iterator.next(), "20 disc p/s ear plugs",  "7.999", null, 14);
        verifyParsedItem(iterator.next(), "skittles",  "2.219", null, 18);
        verifyParsedItem(iterator.next(), "skittles",  "2.799", null, 19);
        verifyParsedItem(iterator.next(), "rowntree tots",  "2.999", null, 20);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "55.56",46);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/3/14",51);
    }

    @Test
    public void receipt_2015_07_18_19_53_40()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_19_53_40, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(8,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "rf pr kuschel slippers",  "11.999", null, 8);
        verifyParsedItem(iterator.next(), "rf pr kuschel slippers",  "11.999", null, 9);
        verifyParsedItem(iterator.next(), "20 disc kuschel slippers",  "11.999", null, 11);
        verifyParsedItem(iterator.next(), "20 disc kuschel slippers",  "1.999", null, 13);
        verifyParsedItem(iterator.next(), "20 disc modella cosm. bag",  "5.999", null, 15);
        verifyParsedItem(iterator.next(), "twizzlers candy",  "1.499", null, 16);
        verifyParsedItem(iterator.next(), "20 disc micro/pedi unit",  "49.999", null, 17);
        verifyParsedItem(iterator.next(), "20 disc razer ferox mini s",  "69.999", null, 18);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "77.18",45);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/11/15",50);

    }

    @Test
    public void receipt_2015_07_18_19_53_50()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_19_53_50, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "ld ja sper gate s", null, null, 5);
        verifyParsedItem(iterator.next(), "m/ bloks barbie",  "19.999", null, 13);
        verifyParsedItem(iterator.next(), "barbie convertible",  "14.999", null, 14);
        verifyParsedItem(iterator.next(), "vitamin water",  "1.999", null, 15);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "recycling fee                      .01 g",16);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "38.93",24);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "��� cardhold er copy ���",66);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/11/17",54);
    }

    @Test
    public void receipt_2015_07_18_19_53_59()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_19_53_59, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "20 disc o'keeffes cream",  "9.999", null, 11);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "8.39",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/16",43);
    }

    //TODO: unicode in not-catlog-
//    @Test
//    public void receipt_2015_07_18_19_55_53()  throws Exception {
//        log.debug("receipt_2015_07_18_19_55_53");
//        final List<String> receiptLines = new ArrayList<>();
//        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_19_55_53, (line)-> receiptLines.add(line));
//        assertTrue(receiptLines.size() > 0);
//        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
//        printResult(receipt);
//        assertEquals("londondrugs", receipt.getChainCode());
//        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
//        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
//        assertEquals(10,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "20 disc comet eggs",  "2.499", null, 9);
//        verifyParsedItem(iterator.next(), "20 disc cold-fx",  "24.989", null, 10);
//        verifyParsedItem(iterator.next(), "kleenex tissues",  "5.999", null, 11);
//        verifyParsedItem(iterator.next(), "lipsosic drops",  "7.999", null, 13);
//        verifyParsedItem(iterator.next(), "20 disc p/s pill boxes",  "0.999", null, 14);
//        verifyParsedItem(iterator.next(), "20 disc bayer aspirin",  "13.999", null, 15);
//        verifyParsedItem(iterator.next(), "mo cold-fx coupon",  "2.00", null, 16);
//        verifyParsedItem(iterator.next(), "mo manufacturer",  "5.00", null, 17);
//        verifyParsedItem(iterator.next(), "mo manufacturer",  "5.00", null, 18);
//        verifyParsedItem(iterator.next(), "mo manufacturer",  "1.00", null, 19);
//        verifyParsedField(fieldValues, ReceiptFieldType.Total, "50.76",21);
//        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/21",48);
//    }

    @Test
    public void receipt_2015_07_18_19_58_30()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_19_58_30, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(13,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "lit",  "1.", null, 3);
        verifyParsedItem(iterator.next(), "maynards",  "2.299", null, 11);
        verifyParsedItem(iterator.next(), "maynards",  "1.719", null, 13);
        verifyParsedItem(iterator.next(), "everpure shampoo",  "7.999", null, 14);
        verifyParsedItem(iterator.next(), "everpure shampoo",  "7.999", null, 15);
        verifyParsedItem(iterator.next(), "nice & easy",  "5.999", null, 16);
        verifyParsedItem(iterator.next(), "nice & easy",  "5.999", null, 17);
        verifyParsedItem(iterator.next(), "nice & easy",  "5.999", null, 18);
        verifyParsedItem(iterator.next(), "hershey choc",  "2.299", null, 19);
        verifyParsedItem(iterator.next(), "hershey choc",  "1.719", null, 21);
        verifyParsedItem(iterator.next(), "twizzler licorice",  "1.299", null, 22);
        verifyParsedItem(iterator.next(), "twizzler",  "2.799", null, 23);
        verifyParsedItem(iterator.next(), "twizzler",  "2.219", null, 25);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "50.65",27);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/3/13",52);
    }

    @Test
    public void receipt_2015_04_04_21_31_37()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_31_37, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "clorox bleach",  "1.799", null, 7);
        verifyParsedItem(iterator.next(), "hoover 2x proplus",  "21.999", null, 8);
        verifyParsedItem(iterator.next(), "vileda hh gloves",  "3.599", null, 9);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "28.74",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*** card hold er copy ***",53);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/6/29",40);
    }

    @Test
    public void receipt_2015_04_04_21_47_34()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_47_34, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "icelandic water",  "2.29", null, 6);
        verifyParsedItem(iterator.next(), "marcelle liner",  "10.99", null, 9);
        verifyParsedItem(iterator.next(), "pre stige pencil",  "3.49", null, 10);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "recycling fee                    .02",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "17.61",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*** cardholder cop y ���",58);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/6",40);
    }

    @Test
    public void receipt_2015_06_29_16_20_29()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_06_29_16_20_29, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(8,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "l o pure honey",  "10.99", null, 5);
        verifyParsedItem(iterator.next(), "l o pure honey",  "10.99", null, 6);
        verifyParsedItem(iterator.next(), "hershey hugs",  "3.999", null, 7);
        verifyParsedItem(iterator.next(), "hershey hugs",  "3.999", null, 8);
        verifyParsedItem(iterator.next(), "hershey hugs",  "3.999", null, 9);
        verifyParsedItem(iterator.next(), "hershey hugs",  "3.999", null, 10);
        verifyParsedItem(iterator.next(), "sunkist vit c",  "3.999", null, 11);
        verifyParsedItem(iterator.next(), "sunkist vit c",  "3.999", null, 12);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "47.12",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "***    card holder co py***",63);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/6",45);
    }

    @Test
    public void receipt_2015_07_02_18_14_57()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_02_18_14_57, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "hydrasense",  "13.999", null, 4);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "14.69",30);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "(g )st         .70",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*** card holder copy ***",51);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/21",34);

    }

    @Test
    public void receipt_2015_07_02_18_15_05()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_02_18_15_05, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "otrivin sallne",  "9.999", null, 5);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "10.49",6);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "(g)st           . 50",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*** card holder copy �* *",52);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "",-1);
    }

    @Test
    public void receipt_2015_07_03_16_51_11()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_03_16_51_11, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("londondrugs", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "ld biometric photo",  "11.99", null, 4);
        verifyParsedItem(iterator.next(), "ld biometric digit",  "1.99", null, 5);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "14.68",31);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*** cardholder copy ***",52);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/10/15",13);
    }


}
