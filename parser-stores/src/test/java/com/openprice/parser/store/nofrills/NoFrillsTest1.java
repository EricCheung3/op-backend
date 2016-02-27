package com.openprice.parser.store.nofrills;

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
public class NoFrillsTest1 extends AbstractReceiptParserIntegrationTest{
    @Value("classpath:/testfiles/nofrills/mattashley/2015_02_09_16_17_56.jpg.random.txt")
    private Resource receipt_2015_02_09_16_17_56;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_02_09_16_18_11.jpg.random.txt")
    private Resource receipt_2015_02_09_16_18_11;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_02_09_16_29_10.jpg.random.txt")
    private Resource receipt_2015_02_09_16_29_10;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_02_09_16_59_20.jpg.random.txt")
    private Resource receipt_2015_02_09_16_59_20;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_02_09_16_59_32.jpg.random.txt")
    private Resource receipt_2015_02_09_16_59_32;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_02_09_17_02_36.jpg.random.txt")
    private Resource receipt_2015_02_09_17_02_36;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_02_09_17_07_33.jpg.random.txt")
    private Resource receipt_2015_02_09_17_07_33;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_02_10_00_00_22.jpg.random.txt")
    private Resource receipt_2015_02_10_00_00_22;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_02_10_00_19_03.jpg.random.txt")
    private Resource receipt_2015_02_10_00_19_03;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_02_11_07_42_24.jpg.random.txt")
    private Resource receipt_2015_02_11_07_42_24;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_02_11_07_51_16.jpg.random.txt")
    private Resource receipt_2015_02_11_07_51_16;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_06_14_21_42_08.jpg.dana.txt")
    private Resource receipt_2015_06_14_21_42_08;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_07_02_14_53_16.jpg.random.txt")
    private Resource receipt_2015_07_02_14_53_16;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_07_02_15_02_54.jpg.random.txt")
    private Resource receipt_2015_07_02_15_02_54;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_07_02_15_09_24.jpg.random.txt")
    private Resource receipt_2015_07_02_15_09_24;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_07_02_15_17_48.jpg.random.txt")
    private Resource receipt_2015_07_02_15_17_48;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_07_03_14_09_03.jpg.hengshuai.txt")
    private Resource receipt_2015_07_03_14_09_03;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_07_03_14_27_51.jpg.hengshuai.txt")
    private Resource receipt_2015_07_03_14_27_51;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_07_03_14_39_18.jpg.hengshuai.txt")
    private Resource receipt_2015_07_03_14_39_18;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_07_21_11_09_25.jpg.beata.txt")
    private Resource receipt_2015_07_21_11_09_25;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_07_21_11_09_33.jpg.beata.txt")
    private Resource receipt_2015_07_21_11_09_33;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_07_21_11_10_14.jpg.beata.txt")
    private Resource receipt_2015_07_21_11_10_14;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_07_21_11_14_20.jpg.beata.txt")
    private Resource receipt_2015_07_21_11_14_20;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_07_21_11_35_23.jpg.hengshuai.txt")
    private Resource receipt_2015_07_21_11_35_23;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_07_21_11_42_40.jpg.hengshuai.txt")
    private Resource receipt_2015_07_21_11_42_40;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_07_21_11_48_38.jpg.hengshuai.txt")
    private Resource receipt_2015_07_21_11_48_38;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_07_21_15_21_53.jpg.beata.txt")
    private Resource receipt_2015_07_21_15_21_53;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_07_21_15_22_23.jpg.beata.txt")
    private Resource receipt_2015_07_21_15_22_23;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_07_21_15_22_29.jpg.beata.txt")
    private Resource receipt_2015_07_21_15_22_29;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_07_21_15_25_42.jpg.beata.txt")
    private Resource receipt_2015_07_21_15_25_42;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_07_21_15_25_50.jpg.beata.txt")
    private Resource receipt_2015_07_21_15_25_50;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_07_21_15_29_19.jpg.beata.txt")
    private Resource receipt_2015_07_21_15_29_19;

    @Value("classpath:/testfiles/nofrills/mattashley/2015_07_21_15_29_27.jpg.beata.txt")
    private Resource receipt_2015_07_21_15_29_27;

    @Test
    public void receipt_2015_02_09_16_17_56()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_16_17_56, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(8,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "bistro xprss veg",  "2.57", null, 4);
        verifyParsedItem(iterator.next(), "strbswirl chcake",  "3.88", null, 6);
        verifyParsedItem(iterator.next(), "srln beef pt pie",  "9.99", null, 8);
        verifyParsedItem(iterator.next(), "mushroom wh wht    r",  "1.47", null, 10);
        verifyParsedItem(iterator.next(), "cucumber english",  "1.77", null, 11);
        verifyParsedItem(iterator.next(), "avocado",  "2.54", null, 12);
        verifyParsedItem(iterator.next(), "pomegranate juic    gr",  "1.88", null, 14);
        verifyParsedItem(iterator.next(), "(2)9    plastic bags    ur",  "0.10", null, 18);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst k 60435'-3423 rt0001",35);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "24.41",24);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "24.31",20);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 03405",60);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/17",48);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*************************************",62);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.10",22);
    }

    @Test
    public void receipt_2015_02_09_16_18_11()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_16_18_11, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(9,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "hellmans mayo",  "3.99", null, 4);
        verifyParsedItem(iterator.next(), "schweppes    gr",  "4.77", null, 5);
//        verifyParsedItem(iterator.next(), "recycle",  "0.06", null, 6);
        verifyParsedItem(iterator.next(), "perrier    gr",  "1.67", null, 8);
//        verifyParsedItem(iterator.next(), "recycling    gr",  "0.06", null, 9);
        verifyParsedItem(iterator.next(), "haagen daz",  "6.97", null, 11);
        verifyParsedItem(iterator.next(), "hd choc icm",  "6.97", null, 12);
        verifyParsedItem(iterator.next(), "dimp bread rye",  "5.49", null, 14);
        verifyParsedItem(iterator.next(), "fm macaroon",  "4.49", null, 15);
        verifyParsedItem(iterator.next(), "pc org arugula",  "1.77", null, 17);
        verifyParsedItem(iterator.next(), "9    plastic bags    gr",  "0.05", null, 19);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "37.32",22);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "36.99",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #           auth #      resp 001",35);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "srore 03405               term 20340503",28);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/16",39);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card # *'*'~*~ **+**3950       exp **f~*",33);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.33",21);
    }

    @Test
    public void receipt_2015_02_09_16_29_10()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_16_29_10, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(17,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "~je 1    #", null, null, 4);
        verifyParsedItem(iterator.next(), "nn pickles    r",  "1.88", null, 7);
        verifyParsedItem(iterator.next(), "pc rice cereal    r",  "3.77", null, 8);
        verifyParsedItem(iterator.next(), "post shred orig    r",  "1.88", null, 9);
        verifyParsedItem(iterator.next(), "beatrice 1% milk    mr",  "4.46", null, 10);
        verifyParsedItem(iterator.next(), "salt and pepper    gr",  "2.88", null, 13);
        verifyParsedItem(iterator.next(), "(2)05621038103 cavn or tempura    r",  "1.76", null, 14);
        verifyParsedItem(iterator.next(), "tropicana apple    gr",  "0.88", null, 18);
//        verifyParsedItem(iterator.next(), "recycling",  "0.03", null, 19);
        verifyParsedItem(iterator.next(), "(2) 04850001 792 trop lemonade    gr",  "1.76", null, 21);
//        verifyParsedItem(iterator.next(), "recycling",  "0.06", null, 23);
        verifyParsedItem(iterator.next(), "kell ng bars    gr",  "1.88", null, 29);
        verifyParsedItem(iterator.next(), "kell ng bars    gr",  "1.88", null, 30);
        verifyParsedItem(iterator.next(), "kell ng bars    r",  "1.88", null, 31);
        verifyParsedItem(iterator.next(), "pc wh extra old    r",  "6.99", null, 32);
        verifyParsedItem(iterator.next(), "baguette ww",  "3.00", null, 34);
        verifyParsedItem(iterator.next(), "pk side rib cov",  "7.31", null, 38);
        verifyParsedItem(iterator.next(), "pear bartlett",  "2.53", null, 40);
        verifyParsedItem(iterator.next(), "romaine heart    r",  "1.88", null, 42);
        verifyParsedItem(iterator.next(), "tomato roma",  "0.63", null, 44);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "52.60",49);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "=gst 5%           9. 28   @   5.000%            0.46",48);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "52.14",46);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "",-1);//no date in this receipt
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "cardft: ***************338*",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.46",48);
    }

    @Test
    public void receipt_2015_02_09_16_59_20()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_16_59_20, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(7,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "coca-cola    gr",  "1.22", null, 4);
//        verifyParsedItem(iterator.next(), "*322    recycling    gr",  "0.07", null, 6);
//        verifyParsedItem(iterator.next(), "*384    deposit",  "0.25", null, 7);
        verifyParsedItem(iterator.next(), "creme egg single    gr",  "0.97", null, 8);
        verifyParsedItem(iterator.next(), "nt th fr desert    gr",  "1.92", null, 9);
        verifyParsedItem(iterator.next(), "beatrice 2% milk",  "4.46", null, 11);
//        verifyParsedItem(iterator.next(), "recycling",  "0.07", null, 12);
        verifyParsedItem(iterator.next(), "krinkle cut salt    gr",  "2.88", null, 14);
        verifyParsedItem(iterator.next(), "snryp frullo",  "1.57", null, 15);
//        verifyParsedItem(iterator.next(), "recycling    gr",  "0.02", null, 16);
        verifyParsedItem(iterator.next(), "snryp frut activ",  "1.57", null, 18);
//        verifyParsedItem(iterator.next(), "recycling    gr",  "0.02", null, 19);
//        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "                    gst w80435-3423 rtoool",34);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "15.83",24);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "15.47",21);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 03405",56);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/16",47);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "********'** ********* ***** *** ****~**** *",58);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.36",23);
    }

    @Test
    public void receipt_2015_02_09_16_59_32()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_16_59_32, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "pc cola    gr",  "0.97", null, 4);
//        verifyParsedItem(iterator.next(), "*322    recycling    gr",  "0.07", null, 5);
//        verifyParsedItem(iterator.next(), "384    deposit",  "0.25", null, 6);
        verifyParsedItem(iterator.next(), "pc gingerale    gr",  "0.97", null, 7);
//        verifyParsedItem(iterator.next(), "*322    recycling    gr",  "0.07", null, 8);
//        verifyParsedItem(iterator.next(), "*384    deposit",  "0.25", null, 9);
        verifyParsedItem(iterator.next(), "(2)06038398138 micro butter pop    r",  "4.98", null, 10);
        verifyParsedItem(iterator.next(), "(3)9    plastic bags    gr",  "0.15", null, 13);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "7.82",17);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "7.71",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "",-1);//nonly have month and day
//        verifyParsedField(fieldValues, ReceiptFieldType.Account, "/ ******************"*",50);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst ~ 80435-3423 rt0001",26);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.11",16);
    }

    @Test
    public void receipt_2015_02_09_17_02_36()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_17_02_36, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "pc cola    gr",  "0.97", null, 4);
//        verifyParsedItem(iterator.next(), "*322    recycling    gr",  "0.07", null, 5);
//        verifyParsedItem(iterator.next(), "*384    deposit",  "0.25", null, 6);
        verifyParsedItem(iterator.next(), "pc gingerale    gr",  "0.97", null, 7);
//        verifyParsedItem(iterator.next(), "*322    hecycling    gr",  "0.07", null, 8);
//        verifyParsedItem(iterator.next(), "384    deposit",  "0.25", null, 9);
        verifyParsedItem(iterator.next(), "micro butter pop",  "4.98", null, 10);
        verifyParsedItem(iterator.next(), "(3)9    plastic bags    gr",  "0.15", null, 13);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst   #   80435-3423 rt0001",27);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "7.82",17);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "7.71",15);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 03405",49);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/17",40);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*******************~***********~******",42);

    }

    @Test
    public void receipt_2015_02_09_17_07_33()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_17_07_33, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "palm dish liquid    gr",  "1.88", null, 6);
        verifyParsedItem(iterator.next(), "medium eggs    r",  "2.57", null, 7);
        verifyParsedItem(iterator.next(), "chinese cabbage",  "3.26", null, 9);
        verifyParsedItem(iterator.next(), "onion green",  "0.57", null, 13);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst # 60435-3423 r10001",40);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "8.37",16);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",33);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "8.28",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #               auth ~       res p 001",29);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 03405",62);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/12/29",53);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "**************************************",64);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.09",15);
    }

    @Test
    public void receipt_2015_02_10_00_00_22()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_10_00_00_22, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(7,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "nn all purpose f    r",  "4.79", null, 6);
        verifyParsedItem(iterator.next(), "ah ess 2x mtn rn    gr",  "2.47", null, 7);
        verifyParsedItem(iterator.next(), "chick dip hummas _.- r",  "0.99", null, 8);
        verifyParsedItem(iterator.next(), "nn rice    r",  "7.69", null, 11);
        verifyParsedItem(iterator.next(), "romaine heart    r",  "3.97", null, 13);
        verifyParsedItem(iterator.next(), "banana",  "3.39", null, 14);
        verifyParsedItem(iterator.next(), "(3)9    plastic bags    gr",  "0.15", null, 18);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "29.07",22);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",40);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "28.94",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #           auth #      resp 001",34);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 03405",69);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/9",60);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "************************'*************",71);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.13",21);
    }

    @Test
    public void receipt_2015_02_10_00_19_03()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_10_00_19_03, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(31,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "nn coleslaw drsg    r",  "3.49", null, 7);
        verifyParsedItem(iterator.next(), "nn liquid honey    r",  "8.88", null, 8);
        verifyParsedItem(iterator.next(), "pcbm granola rai    r",  "3.99", null, 9);
        verifyParsedItem(iterator.next(), "pc bm gran om fr    r",  "3.99", null, 10);
        verifyParsedItem(iterator.next(), "nn ext lrg, ea    r",  "2.89", null, 11);
        verifyParsedItem(iterator.next(), "l2l06570010028 beatrice 1% milk    nr",  "9.08", null, 12);
//        verifyParsedItem(iterator.next(), "'c2l 44000865341 recycling    r",  "0.16", null, 14);
        verifyParsedItem(iterator.next(), "hghlnr fllt    r",  "3.97", null, 18);
        verifyParsedItem(iterator.next(), "rmh ez tin orig",  "6.47", null, 19);
        verifyParsedItem(iterator.next(), "nn jce orange",  "2.15", null, 20);
//        verifyParsedItem(iterator.next(), "recycling",  "0.08", null, 21);
        verifyParsedItem(iterator.next(), "nn apple juice    r",  "2.15", null, 23);
//        verifyParsedItem(iterator.next(), "recycling",  "0.08", null, 24);
        verifyParsedItem(iterator.next(), "c2l06820075015 astro balkan    r",  "3.94", null, 26);
        verifyParsedItem(iterator.next(), "nn rotini    r",  "1.00", null, 28);
        verifyParsedItem(iterator.next(), "nn 24 roll bt    gr",  "3.96", null, 29);
        verifyParsedItem(iterator.next(), "ryl fcl tissue    gr",  "3.97", null, 30);
        verifyParsedItem(iterator.next(), "nn pizza moz 800    r",  "9.47", null, 31);
        verifyParsedItem(iterator.next(), "phil crm cheese    r",  "5.00", null, 32);
        verifyParsedItem(iterator.next(), "c2l06340004050 ch bread ww    r",  "7.94", null, 35);
        verifyParsedItem(iterator.next(), "dital bread ww",  "2.47", null, 38);
        verifyParsedItem(iterator.next(), "ww sausage bun    r",  "2.47", null, 39);
        verifyParsedItem(iterator.next(), "garlic naan 5 pk    r",  "2.47", null, 40);
        verifyParsedItem(iterator.next(), "zabiha halal chi",  "1.84", null, 42);
        verifyParsedItem(iterator.next(), "red del 3lb    r",  "2.97", null, 44);
        verifyParsedItem(iterator.next(), "cucumber mini    r",  "3.77", null, 45);
        verifyParsedItem(iterator.next(), "onion green",  "0.44", null, 46);
        verifyParsedItem(iterator.next(), "cauliflower",  "3.67", null, 48);
        verifyParsedItem(iterator.next(), "parsley italian",  "0.97", null, 50);
        verifyParsedItem(iterator.next(), "tomato roma",  "2.58", null, 51);
        verifyParsedItem(iterator.next(), "pco car bby 2lb    r",  "3.47", null, 53);
        verifyParsedItem(iterator.next(), "(8)9    plastic bags    gr",  "0.40", null, 55);
        verifyParsedItem(iterator.next(), "neck nipples    gr",  "3.79", null, 58);
        verifyParsedItem(iterator.next(), "gerber nipples s    r",  "5.99", null, 59);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "119.73",63);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",83);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "118.82",60);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #          auth #      resp 001",77);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store 03405               term z0340503c",69);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/8",81);//two dates: the other is 02/06
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card # **~'***l+***9301",73);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.91",61);
    }

    @Test
    public void receipt_2015_02_11_07_42_24()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_11_07_42_24, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(9,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "beatrice 2% .milk",  "4.54", null, 21);
        verifyParsedItem(iterator.next(), "nn green peas cp",  "3.97", null, 24);
        verifyParsedItem(iterator.next(), "tetley tea",  "1.88", null, 25);
        verifyParsedItem(iterator.next(), "nn salted butter",  "3.87", null, 26);
        verifyParsedItem(iterator.next(), "bread 100% wholw",  "1.47", null, 28);
        verifyParsedItem(iterator.next(), "golden del",  "r", null, 30);
        verifyParsedItem(iterator.next(), "spinach bunched",  "1.67", null, 34);
        verifyParsedItem(iterator.next(), "pep red swt 4ct    r",  "1.97", null, 35);
        verifyParsedItem(iterator.next(), "onion yellow 3lb    r",  "1.47", null, 36);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst ~ 80435-3423 rt0001",58);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "27.83",38);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "27.83",37);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #              auth #      resp",13);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 03405",80);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/6",71);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "********* * ***************~** *******~**",82);

    }

    @Test
    public void receipt_2015_02_11_07_51_16()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_11_07_51_16, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(7,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "beat choc milk",  "2.49", null, 5);
        verifyParsedItem(iterator.next(), "beat buttermilk",  "2.27", null, 7);
        verifyParsedItem(iterator.next(), "passionate peach",  "2.50", null, 9);
//        verifyParsedItem(iterator.next(), "recycling",  "0.08", null, 10);
        verifyParsedItem(iterator.next(), "dimp bread rye",  "8.98", null, 13);
        verifyParsedItem(iterator.next(), "schn franks",  "4.47", null, 16);
        verifyParsedItem(iterator.next(), "juicy jumbos bf",  "4.47", null, 17);
        verifyParsedItem(iterator.next(), "cucumber english r",  "1.97", null, 19);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "36.62",25);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",47);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "36.62",24);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref ~           auth #      resp 001",41);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 0340",79);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/8",70);
//        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*"*********************~**************",72);

    }

    @Test
    public void receipt_2015_06_14_21_42_08()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_06_14_21_42_08, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(7,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "no name all purp",  "5.99", null, 7);
        verifyParsedItem(iterator.next(), "red del. apples", null, null, 9);
        verifyParsedItem(iterator.next(), "pear bartlett",  "2.43", null, 11);
        verifyParsedItem(iterator.next(), "mango red",  "1.67", null, 13);
        verifyParsedItem(iterator.next(), "pto russet 1olb    r",  "3.97", null, 14);
        verifyParsedItem(iterator.next(), "onion yellow 3lb    r",  "1.47", null, 15);
        verifyParsedItem(iterator.next(), "pco crt 2lb",  "2.47", null, 17);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst # 82066-5537 rt0001",46);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "21.43",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approued",38);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "21.43",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #            auth #     resp 001",32);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 03422",68);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/3/5",36);
//        verifyParsedField(fieldValues, ReceiptFieldType.Account, "**************************************",70);

    }

    @Test
    public void receipt_2015_07_02_14_53_16()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_02_14_53_16, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(15,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "bea skim milk",  "1.97", null, 5);
        verifyParsedItem(iterator.next(), "rb pecan hal raw",  "7.00", null, 7);
        verifyParsedItem(iterator.next(), "pc grk yog plain",  "6.99", null, 8);
        verifyParsedItem(iterator.next(), "pc chev goat",  "4.48", null, 10);
        verifyParsedItem(iterator.next(), "artchk asiago",  "2.97", null, 11);
        verifyParsedItem(iterator.next(), "muffins",  "4.00", null, 13);
        verifyParsedItem(iterator.next(), "40:!3    grape red sdl cs",  "7.28", null, 15);
        verifyParsedItem(iterator.next(), "peach yellow",  "3.34", null, 17);
        verifyParsedItem(iterator.next(), "pco fld grns sld",  "4.47", null, 21);
        verifyParsedItem(iterator.next(), "orange navel med",  "3.42", null, 22);
        verifyParsedItem(iterator.next(), "grapefruit red",  "2.54", null, 24);
        verifyParsedItem(iterator.next(), "carrot 2lb",  "1.79", null, 26);
        verifyParsedItem(iterator.next(), "(2)9    plastic bags    gr",  "0.10", null, 28);
        verifyParsedItem(iterator.next(), "salad macaroni    gr",  "1.97", null, 31);
        verifyParsedItem(iterator.next(), "toothpaste    gr",  "2.42", null, 33);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "59.00",36);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "58.78",34);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "",-1);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.22",35);

    }

    @Test
    public void receipt_2015_07_02_15_02_54()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_02_15_02_54, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(7,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "pc orange    gr",  "0.97", null, 5);
        verifyParsedItem(iterator.next(), "ps fs fudge bar    gr",  "4.77", null, 8);
        verifyParsedItem(iterator.next(), "fmost icecrm",  "3.49", null, 9);
        verifyParsedItem(iterator.next(), "lays wavy org    gr",  "3.17", null, 10);
        verifyParsedItem(iterator.next(), "cheetos crunchy    gr",  "2.77", null, 11);
        verifyParsedItem(iterator.next(), "phil dip onion",  "2.97", null, 12);
        verifyParsedItem(iterator.next(), "(2)9    plastic bags    gr",  "0.10", null, 14);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst ~ 82066 -5537 rt0001",29);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "19.16",18);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "18.57",16);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 03422",54);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/5/29",45);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "**************************************",56);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.59",17);

    }

    @Test
    public void receipt_2015_07_02_15_09_24()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_02_15_09_24, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(13,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "clvf tuna thai", null, null, 5);
        verifyParsedItem(iterator.next(), "cl leaf tuna",  "r", null, 6);
        verifyParsedItem(iterator.next(), "cl leaf tuna lt",  "r", null, 7);
        verifyParsedItem(iterator.next(), "tuna thai chili",  "1.39", null, 10);
        verifyParsedItem(iterator.next(), "flaked light tun",  "1.39", null, 11);
        verifyParsedItem(iterator.next(), "flaked light tun",  "1.39", null, 12);
        verifyParsedItem(iterator.next(), "gs tuna itln",  "1.39", null, 13);
        verifyParsedItem(iterator.next(), "maille dijon mdu",  "4.97", null, 14);
        verifyParsedItem(iterator.next(), "ltnt whip crm",  "3.19", null, 15);
        verifyParsedItem(iterator.next(), "nn basmati rice",  "10.99", null, 17);
        verifyParsedItem(iterator.next(), "nn pprni chub",  "2.99", null, 19);
        verifyParsedItem(iterator.next(), "onion red",  "0.82", null, 21);
        verifyParsedItem(iterator.next(), "9    plastic bags    gr",  "0.05", null, 24);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "33.17",28);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "33.17",25);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store : 03422",63);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/6/8",54);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "**************************************",65);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.00",27);
    }

    @Test
    public void receipt_2015_07_02_15_17_48()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_02_15_17_48, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(6,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "medium eggs",  "2.57", null, 5);
        verifyParsedItem(iterator.next(), "beat 2% milk",  "1.19", null, 6);
        verifyParsedItem(iterator.next(), "mango red",  "1.56", null, 9);
        verifyParsedItem(iterator.next(), "baby bok jr",  "5.49", null, 11);
        verifyParsedItem(iterator.next(), "lettuce iceberg",  "1.77", null, 13);
        verifyParsedItem(iterator.next(), "banana",  "0.77", null, 15);
//        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst " 62066 -5537 rtoool",33);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "9.81",18);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "9.81",17);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 03422",57);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/6/8",48);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "s at !:j  ******* ~ ** ***.f **~ **",36);

    }

    @Test
    public void receipt_2015_07_03_14_09_03()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_03_14_09_03, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(19,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "lkk sauce",  "2.48", null, 7);
        verifyParsedItem(iterator.next(), "t&t s laver    gr",  "1.79", null, 8);
        verifyParsedItem(iterator.next(), "pc free run lrg",  "4.79", null, 9);
        verifyParsedItem(iterator.next(), "rb banana chips    gr",  "4.00", null, 10);
        verifyParsedItem(iterator.next(), "ww santa fe",  "2.47", null, 11);
        verifyParsedItem(iterator.next(), "pc sc angus beef",  "2.99", null, 12);
        verifyParsedItem(iterator.next(), "nn meat lasgna",  "5.49", null, 13);
        verifyParsedItem(iterator.next(), "oj 100% w/pulp",  "4.77", null, 14);
//        verifyParsedItem(iterator.next(), "recycling",  "0.08", null, 15);
        verifyParsedItem(iterator.next(), "pork piece",  "14.87", null, 18);
        verifyParsedItem(iterator.next(), "pk side rib cov",  "16.17", null, 20);
        verifyParsedItem(iterator.next(), "fm mini cuke 8    r",  "1.97", null, 22);
        verifyParsedItem(iterator.next(), "corn bicolor",  "1.50", null, 23);
        verifyParsedItem(iterator.next(), "oriental yam",  "1.76", null, 25);
        verifyParsedItem(iterator.next(), "blueberries",  "1.00", null, 27);
        verifyParsedItem(iterator.next(), "blkbry 1/2 pt",  "1.00", null, 28);
        verifyParsedItem(iterator.next(), "brusselsprou",  "0.70", null, 29);
        verifyParsedItem(iterator.next(), "lemon",  "0.97", null, 31);
        verifyParsedItem(iterator.next(), "banana",  "0.92", null, 32);
        verifyParsedItem(iterator.next(), "(2)9    plastic bags    gr",  "0.10", null, 35);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "65.90",41);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approv ed",62);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "65.61",37);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #              aufh n       resp 001",56);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/6/20",60);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card # ****~*'***~*61 25",51);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.29",39);
    }

    @Test
    public void receipt_2015_07_03_14_27_51()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_03_14_27_51, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(14,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "no frills 40th a    ve", null, null, 5);
//        verifyParsedItem(iterator.next(), "ve nw", null, null, 6);
//        verifyParsedItem(iterator.next(), "not    cot~pleted", null, null, 21);
        verifyParsedItem(iterator.next(), "pc bm white eggs    r",  "3.97", null, 27);
        verifyParsedItem(iterator.next(), "org gr onions    mr",  "1.27", null, 28);
        verifyParsedItem(iterator.next(), "iogo probio 2.5%    r",  "5.97", null, 30);
        verifyParsedItem(iterator.next(), "(2j0634 35711 46 ryle fcl tis    gr",  "9.94", null, 31);
        verifyParsedItem(iterator.next(), "grp grn sdls",  "7.50", null, 34);
        verifyParsedItem(iterator.next(), "corn bicolor",  "3.00", null, 36);
        verifyParsedItem(iterator.next(), "avocado",  "3.34", null, 38);
        verifyParsedItem(iterator.next(), "long beans",  "2.91", null, 40);
        verifyParsedItem(iterator.next(), "chinese cabbage",  "2.49", null, 42);
        verifyParsedItem(iterator.next(), "rasp 1/2 pint",  "1.97", null, 46);
        verifyParsedItem(iterator.next(), "onion green",  "0.77", null, 47);
        verifyParsedItem(iterator.next(), "banana",  "1.50", null, 48);
        verifyParsedItem(iterator.next(), "9    plastic bags    gr",  "0.05", null, 51);
        verifyParsedItem(iterator.next(), "grbr grds blubry",  "2.67", null, 53);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "47.21",56);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approued",79);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "46.71",54);
//        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref              auth       resp 001",73);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store 03422                 term z0342203c",63);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/6/8",77);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "****************************************",86);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.50",55);
    }

    @Test
    public void receipt_2015_07_03_14_39_18()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_03_14_39_18, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(17,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "cvdh onon pates",  "4.29", null, 9);
        verifyParsedItem(iterator.next(), "iogo trio asrtd",  "6.29", null, 10);
        verifyParsedItem(iterator.next(), "bean green",  "2.36", null, 12);
        verifyParsedItem(iterator.next(), "mush crem bulk    hr",  "2.87", null, 14);
        verifyParsedItem(iterator.next(), "avocado bag",  "3.97", null, 18);
        verifyParsedItem(iterator.next(), "broccoli",  "2.47", null, 19);
        verifyParsedItem(iterator.next(), "lettuce red leaf",  "2.67", null, 20);
        verifyParsedItem(iterator.next(), "radish bunch",  "0.77", null, 21);
        verifyParsedItem(iterator.next(), "grapefruit red",  "1.27", null, 22);
        verifyParsedItem(iterator.next(), "fm toms red bag",  "3.56", null, 23);
        verifyParsedItem(iterator.next(), "squash acorn",  "3.13", null, 25);
        verifyParsedItem(iterator.next(), "turnip",  "0.84", null, 27);
        verifyParsedItem(iterator.next(), "(2)9    plastic bags    gr",  "0.10", null, 31);
        verifyParsedItem(iterator.next(), "burpee bean gold",  "2.29", null, 34);
        verifyParsedItem(iterator.next(), "burpee organic s    gr",  "2.79", null, 35);
        verifyParsedItem(iterator.next(), "burpee bean stri    gr",  "2.49", null, 36);
        verifyParsedItem(iterator.next(), "burpee beet dakr    gr",  "1.99", null, 37);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "44.63",41);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approued",62);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "44.15",38);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "' ref n           auth #      resp 001",56);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "stoke: 03422",95);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/5/29",86);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*~******~****************************~",88);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.48",40);
    }

    //TODO why homedepot?
    @Test
    public void receipt_2015_07_21_11_09_25()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_11_09_25, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(6,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "qua life org",  "2.47", null, 4);
        verifyParsedItem(iterator.next(), "pc mlk choc br    gr",  "2.97", null, 5);
        verifyParsedItem(iterator.next(), "pc btrswh drk    gr",  "2.97", null, 6);
        verifyParsedItem(iterator.next(), "bea homo milk",  "2.07", null, 7);
        verifyParsedItem(iterator.next(), "sour cream",  "3.79", null, 9);
        verifyParsedItem(iterator.next(), "plastic bags    gr",  "0.05", null, 11);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst d 82066-5537 rtoooi",45);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "14.72",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approu eo",37);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "14.42",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref d            auth d resp 001",31);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 03422",67);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/6/13",35);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "**************************************",69);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.30",14);
    }

    @Test
    public void receipt_2015_07_21_11_09_33()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_11_09_33, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(5,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "grade a brown",  "3.17", null, 6);
        verifyParsedItem(iterator.next(), "fm unslt but",  "4.97", null, 7);
        verifyParsedItem(iterator.next(), "pork dice should",  "6.07", null, 9);
        verifyParsedItem(iterator.next(), "pork dice should",  "5.83", null, 10);
        verifyParsedItem(iterator.next(), "pork dice should",  "5.30", null, 11);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst i 82066-5537 rt0001",44);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "25.34",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",30);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "25.34",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #                auth#        resp 001",26);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 03422",68);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/6/6",28);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "**************************************",70);

    }

    @Test
    public void receipt_2015_07_21_11_10_14()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_11_10_14, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(17,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "rc spring water",  "1.97", null, 11);
//        verifyParsedItem(iterator.next(), "recycle    fl",  "0.24", null, 12);
        verifyParsedItem(iterator.next(), "cad dark fam    gr",  "2.49", null, 14);
        verifyParsedItem(iterator.next(), "wrgly 5 rain mtp    gr",  "3.93", null, 15);
        verifyParsedItem(iterator.next(), "glico choco pky    gr",  "1.39", null, 16);
        verifyParsedItem(iterator.next(), "glico pocky",  "1.39", null, 17);
        verifyParsedItem(iterator.next(), "hom milk 3.25%",  "5.99", null, 18);
//        verifyParsedItem(iterator.next(), "recycling",  "0.08", null, 19);
        verifyParsedItem(iterator.next(), "zig swiss",  "10.99", null, 22);
        verifyParsedItem(iterator.next(), "(2) 055641 01236 ib crustybuns    hr", null, null, 24);
        verifyParsedItem(iterator.next(), "white pita",  "3.19", null, 26);
        verifyParsedItem(iterator.next(), "0&760300002 wwheat pita",  "3.19", null, 27);
        verifyParsedItem(iterator.next(), "bkr boy cn roll",  "5.99", null, 28);
        verifyParsedItem(iterator.next(), "schn sausage", null, null, 30);
        verifyParsedItem(iterator.next(), "org gala apple",  "r", null, 33);
        verifyParsedItem(iterator.next(), "dole classic mix",  "1.47", null, 35);
        verifyParsedItem(iterator.next(), "berry smoothe    r",  "4.97", null, 36);
        verifyParsedItem(iterator.next(), "pep red swt 4ct    r",  "3.47", null, 37);
        verifyParsedItem(iterator.next(), "s ubhhal",  "80.34", null, 38);
//        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst " 82066 -5537 rtoooi",73);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "80.73",41);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",58);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref *               authm         resp 001",54);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store 03422            teri1 z0342204c",48);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/7/3",56);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "**************************************",88);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.39",40);
    }

    @Test
    public void receipt_2015_07_21_11_14_20()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_11_14_20, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(37,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "craisins orig    gr",  "3.88", null, 7);
        verifyParsedItem(iterator.next(), "&52 rh flour all    r",  "13.99", null, 8);
        verifyParsedItem(iterator.next(), "nn cocoa, cp",  "5.49", null, 9);
        verifyParsedItem(iterator.next(), "sugar 10 kg",  "10.59", null, 10);
        verifyParsedItem(iterator.next(), "table salt",  "3.79", null, 11);
        verifyParsedItem(iterator.next(), "cavn or battered",  "8.99", null, 16);
        verifyParsedItem(iterator.next(), "nn whl kern crn",  "3.97", null, 17);
        verifyParsedItem(iterator.next(), "(2)06036386701 nn cut green bea    r", null, null, 18);
        verifyParsedItem(iterator.next(), "lbrt grk lemon    r",  "2.97", null, 20);
        verifyParsedItem(iterator.next(), "lbrt grk vanila",  "2.97", null, 21);
        verifyParsedItem(iterator.next(), "unico spghet    r",  "2.59", null, 24);
        verifyParsedItem(iterator.next(), "nn maria cookies    r",  "2.49", null, 25);
        verifyParsedItem(iterator.next(), "phila soft plain    r",  "3.97", null, 28);
        verifyParsedItem(iterator.next(), "brd crumbs italn    r",  "2.89", null, 30);
        verifyParsedItem(iterator.next(), "chkn brst sknls",  "14.18", null, 34);
        verifyParsedItem(iterator.next(), "chicken brst",  "13.43", null, 35);
        verifyParsedItem(iterator.next(), "chicken thighs",  "15.22", null, 36);
        verifyParsedItem(iterator.next(), "chicken thighs",  "14.17", null, 37);
        verifyParsedItem(iterator.next(), "med ground pork",  "6.59", null, 38);
        verifyParsedItem(iterator.next(), "pork back ribs c",  "12.63", null, 39);
        verifyParsedItem(iterator.next(), "pork back ribs c",  "12.50", null, 40);
        verifyParsedItem(iterator.next(), "pork tenderloin",  "13.90", null, 41);
        verifyParsedItem(iterator.next(), "pork tenderloin",  "13.77", null, 42);
        verifyParsedItem(iterator.next(), "cucumber english",  "1.27", null, 44);
        verifyParsedItem(iterator.next(), "pc mshrms wjhite",  "3.77", null, 45);
        verifyParsedItem(iterator.next(), "grp grn sols", null, null, 46);
        verifyParsedItem(iterator.next(), "peach yellow", null, null, 48);
        verifyParsedItem(iterator.next(), "cucumber englis h",  "1.27", null, 50);
        verifyParsedItem(iterator.next(), "pineapple",  "2.47", null, 51);
        verifyParsedItem(iterator.next(), "cherries red",  "r", null, 52);
        verifyParsedItem(iterator.next(), "strawberries 1lb",  "1.77", null, 54);
        verifyParsedItem(iterator.next(), "radish 1lb",  "1.87", null, 55);
        verifyParsedItem(iterator.next(), "lettuce grn leaf",  "2.97", null, 56);
        verifyParsedItem(iterator.next(), "dole coleslaw    r",  "1.47", null, 57);
        verifyParsedItem(iterator.next(), "fm toms red bag",  "r", null, 58);
        verifyParsedItem(iterator.next(), "potato red 10lb",  "5.77", null, 60);
        verifyParsedItem(iterator.next(), "banana", null, null, 61);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "273.40",65);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "appnoved",86);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "273.21",63);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #           auth a                   resp 001",80);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store 03422                             term20342204",71);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/6/15",84);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card ~ *'***'******0882",77);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.19",64);
    }

    @Test
    public void receipt_2015_07_21_11_35_23()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_11_35_23, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "bea homo milk jg    mr",  "4.99", null, 4);
//        verifyParsedItem(iterator.next(), "recycling",  "0.08", null, 5);
        verifyParsedItem(iterator.next(), "ssg chdr smkd",  "2.00", null, 8);
        verifyParsedItem(iterator.next(), "fm toms red bag", null, null, 11);
        verifyParsedItem(iterator.next(), "9    plastic bags    gr",  "0.05", null, 17);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst m 82066-5537 rt0001",29);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "12.56",20);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "12.56",18);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store : 03422",52);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/5/10",42);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "************************************~*",54);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.00",19);
    }

    @Test
    public void receipt_2015_07_21_11_42_40()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_11_42_40, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(25,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "matt &    ashley 's nofrills", null, null, 2);
        verifyParsedItem(iterator.next(), "rc spr wtr",  "2.67", null, 7);
        verifyParsedItem(iterator.next(), "recycle",  "0.48", null, 8);
        verifyParsedItem(iterator.next(), "smoothie mango    gr",  "4.97", null, 10);
        verifyParsedItem(iterator.next(), "frozyog strbrban    r",  "6.49", null, 11);
        verifyParsedItem(iterator.next(), "pc free run lrg    r",  "4.79", null, 12);
        verifyParsedItem(iterator.next(), "pc bm white eggs    r",  "3.97", null, 13);
        verifyParsedItem(iterator.next(), "so good original    r",  "3.97", null, 14);
//        verifyParsedItem(iterator.next(), "recycling",  "0.08", null, 15);
        verifyParsedItem(iterator.next(), "dc str/pch/ch/mn    r",  "4.97", null, 17);
        verifyParsedItem(iterator.next(), "iogo nand yg rsp    r",  "2.97", null, 20);
        verifyParsedItem(iterator.next(), "sit",  "0.60", null, 22);
        verifyParsedItem(iterator.next(), "(2)06132880216 scotties 2ply    gr",  "7.94", null, 23);
        verifyParsedItem(iterator.next(), "dital bread ww",  "3.37", null, 26);
        verifyParsedItem(iterator.next(), "dital buns ssge",  "3.47", null, 27);
        verifyParsedItem(iterator.next(), "med ground pork",  "5.48", null, 29);
        verifyParsedItem(iterator.next(), "bc saus h-gar fp",  "6.50", null, 30);
        verifyParsedItem(iterator.next(), "pco apple rd del",  "4.97", null, 32);
        verifyParsedItem(iterator.next(), "avocado",  "5.01", null, 33);
        verifyParsedItem(iterator.next(), "rasp 1/2 pint",  "2.00", null, 35);
        verifyParsedItem(iterator.next(), "strawberries 1lb    r",  "3.47", null, 36);
        verifyParsedItem(iterator.next(), "orange navel med",  "5.21", null, 37);
        verifyParsedItem(iterator.next(), "lemon",  "0.67", null, 39);
        verifyParsedItem(iterator.next(), "fm carrt min 1lb",  "0.50", null, 41);
        verifyParsedItem(iterator.next(), "banana organic",  "2.20", null, 42);
        verifyParsedItem(iterator.next(), "grbr grds blubry",  "2.77", null, 45);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst   m 82066 -5537     rtoool",83);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "98.48",48);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "app rou ed",68);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "97.83",46);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref m           auth m      resp 001",62);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store 03422              term z0342202c",56);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/5/10",66);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "at   ** ***'*** ** ****",87);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.65",47);
    }

    @Test
    public void receipt_2015_07_21_11_48_38()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_11_48_38, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(10,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "slip ~",  "9.2", null, 10);
//        verifyParsedItem(iterator.next(), "retai~ this copyfor you",  "rrec", null, 11);
//        verifyParsedItem(iterator.next(), "*'    chip", null, null, 14);
//        verifyParsedItem(iterator.next(), "ref a    a", null, null, 17);
//        verifyParsedItem(iterator.next(), "uthn    resp", null, null, 18);
        verifyParsedItem(iterator.next(), "unico spgtni",  "2.69", null, 30);
        verifyParsedItem(iterator.next(), "oj 100% w/pulp",  "4.99", null, 32);
        verifyParsedItem(iterator.next(), "pepper green swt",  "2.94", null, 36);
        verifyParsedItem(iterator.next(), "potato baking",  "0.87", null, 38);
        verifyParsedItem(iterator.next(), "melons honeydew",  "4.49", null, 40);
        verifyParsedItem(iterator.next(), "mush crem bulk",  "3.71", null, 41);
        verifyParsedItem(iterator.next(), "pork sausage cp",  "13.40", null, 46);
        verifyParsedItem(iterator.next(), "ww sausage bun",  "2.77", null, 48);
        verifyParsedItem(iterator.next(), "(2l9    plastic bags    grq",  "0.10", null, 50);
        verifyParsedItem(iterator.next(), "49    other",  "10.59", null, 53);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst   #   82066-5537 rt0001",92);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "46.89",56);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "ecology fee                                                 0.08",33);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",77);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "46.88",54);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #           a uth a      resp 001",71);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store 03422               lerh z0342202",62);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/7/18",107);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*********~***'************************",100);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.01",55);
    }

    @Test
    public void receipt_2015_07_21_15_21_53()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_15_21_53, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "uhy pay more?..... s    hop at", null, null, 1);
//        verifyParsedItem(iterator.next(), "no fril ls 40th ave    r", null, null, 5);
//        verifyParsedItem(iterator.next(), "slip m",  "9.2", null, 9);
        verifyParsedItem(iterator.next(), "beatrice 2% milk",  "4.54", null, 25);
//        verifyParsedItem(iterator.next(), "recycling",  "0.08", null, 26);
        verifyParsedItem(iterator.next(), "refried bean",  "2.87", null, 28);
        verifyParsedItem(iterator.next(), "tst cntn xthn    gr",  "3.17", null, 29);
        verifyParsedItem(iterator.next(), "nn taquitos",  "r", null, 31);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst # 82066-5537 rt0001",64);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "23.05",36);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approu ed",56);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "22.89",33);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #           auth #      resp 001",50);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store : 03422",86);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/3/31",54);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*********************** **************~",88);

    }

    @Test
    public void receipt_2015_07_21_15_22_23()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_15_22_23, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(5,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "uhy p    ry mo", null, null, 11);
        verifyParsedItem(iterator.next(), "beatrice milk 2%",  "r", null, 17);
        verifyParsedItem(iterator.next(), "recycling",  "r", null, 20);
        verifyParsedItem(iterator.next(), ":?36 ib crusty buns    mr",  "3.27", null, 25);
        verifyParsedItem(iterator.next(), "bkr boy cn roll    r",  "5.99", null, 26);
        verifyParsedItem(iterator.next(), "plastic bags    gr",  "0.05", null, 29);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst i 82066-5537 rt0001",61);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "16.71",33);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approued",50);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "16.71",30);
//        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref i               auth         resp 001",46);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 03422",83);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/4/9",48);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*******************************",85);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.00",32);
    }

    @Test
    public void receipt_2015_07_21_15_22_29()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_15_22_29, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1l lactantia",  "whi", null, 5);
        verifyParsedItem(iterator.next(), "apple",  "spartan", null, 10);
        verifyParsedItem(iterator.next(), "9    plastic bags    gr",  "0.05", null, 13);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "16.54",17);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst i 82066-5537 rtoool",26);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "16.54",14);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 03422",48);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/4/23",39);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "**************************************",50);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.00",16);
    }

    @Test
    public void receipt_2015_07_21_15_25_42()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_15_25_42, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(8,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "lndt br coco    gr",  "3.49", null, 6);
        verifyParsedItem(iterator.next(), "pc btrswt drk    gr",  "3.99", null, 7);
        verifyParsedItem(iterator.next(), "cadbury chocolat    gr",  "2.49", null, 8);
        verifyParsedItem(iterator.next(), "cad dark fam    gr",  "2.49", null, 9);
        verifyParsedItem(iterator.next(), "pc hand dish    gr",  "1.97", null, 10);
        verifyParsedItem(iterator.next(), "nn reg blech    gr",  "2.49", null, 11);
        verifyParsedItem(iterator.next(), "(2) 06038366417 grade a brown    r", null, null, 12);
        verifyParsedItem(iterator.next(), "nn shortbred    r",  "1.99", null, 14);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #              auth a       resp 001",30);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "26.10",17);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 03422",67);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "25.25",15);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst m 82066-5537 rt0001",41);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "**************************************",69);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.85",16);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/23",32);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approued",34);
    }

    @Test
    public void receipt_2015_07_21_15_25_50()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_15_25_50, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(6,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "nn maria cookies",  "2.49", null, 9);
        verifyParsedItem(iterator.next(), "klnx mnl ply fcl    gr",  "5.99", null, 10);
        verifyParsedItem(iterator.next(), "nn lgt mzrl p sk    r",  "9.47", null, 11);
        verifyParsedItem(iterator.next(), "mozz cheese",  "11.97", null, 12);
        verifyParsedItem(iterator.next(), "ldale ov rst chk",  "8.97", null, 16);
        verifyParsedItem(iterator.next(), "pc charity",  "2.00", null, 18);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst # 82066-5537 rt0001",46);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "58.11",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approued",39);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "57.81",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #              auth #       resp 001",35);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 03422",68);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/26",37);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "**************************************",70);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.30",20);
    }

    @Test
    public void receipt_2015_07_21_15_29_19()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_15_29_19, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(6,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "hellmanns lt 00    r",  "3.97", null, 6);
        verifyParsedItem(iterator.next(), "garlic brd slc",  "4.29", null, 12);
        verifyParsedItem(iterator.next(), "pc bm bun wg",  "3.28", null, 13);
        verifyParsedItem(iterator.next(), "bc beef burger",  "13.99", null, 15);
        verifyParsedItem(iterator.next(), "celery stalks",  "1.67", null, 17);
        verifyParsedItem(iterator.next(), "onion yellow 3lb",  "0.97", null, 18);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "~      ,                   gst # 82066 -5537 rt0001",49);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "33.19",22);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approv ed",42);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "33.19",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #      '        auth #      resp 001",38);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 0342;",72);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/21",40);
//        verifyParsedField(fieldValues, ReceiptFieldType.Account, "~~~*****'***' *****~ '*******" ** **'*** **",74);

    }

    @Test
    public void receipt_2015_07_21_15_29_27()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_15_29_27, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(7,receipt.getItems().size());
        //TODO if so simiar to chain name, should not be an item
//        verifyParsedItem(iterator.next(), "nofrills    '", null, null, 2);
        verifyParsedItem(iterator.next(), "rh flour all    r",  "11.97", null, 10);
        verifyParsedItem(iterator.next(), "rrf med brown eg    mr",  "5.99", null, 11);
        verifyParsedItem(iterator.next(), "mozz cheese    r",  "11.97", null, 12);
        verifyParsedItem(iterator.next(), "fm unslt but    r",  "4.97", null, 13);
        verifyParsedItem(iterator.next(), "chkn brst strips",  "5.00", null, 15);
        verifyParsedItem(iterator.next(), "peach yellow",  "r", null, 17);
        verifyParsedItem(iterator.next(), "orange navel    3lb",  "2.97", null, 19);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst     #   82066-5537 rt0001",45);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "51.26",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approued",38);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "51.26",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #              auth #       resp 001",34);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store 03422           terh z0342201c.-",28);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/17",36);
//        verifyParsedField(fieldValues, ReceiptFieldType.Account, "~<:; **************",70);

    }




}
