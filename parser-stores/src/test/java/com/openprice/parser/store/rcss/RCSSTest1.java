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
    public void multilineItemTest0() throws Exception {
        final List<String> lines = new ArrayList<>();
        lines.add("    4011          BANANA                     MftJ");
        lines.add("0.940 kg 8 $1.73/kg                              1.60");
        lines.add("");
        lines.add("");
        lines.add("");
        ParsedReceipt receipt = simpleParser.parseLines(lines);
        printResult(receipt);
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "banana    mftj",  "1.60", null, 0);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "",-1);

    }

    @Test
    public void multilineItemTest1() throws Exception {
        final List<String> lines = new ArrayList<>();
        lines.add("    4011          BANANA                     MftJ");
        lines.add("0.940 kg 8 $1.73/kg                              1.60");
        lines.add("    4011          FDASFDA                     MftJ");
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
        verifyParsedItem(iterator.next(), "fdasfda    mftj",  "1.60", null, 2);
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
        verifyParsedItem(iterator.next(), "yelw calros    rice",  "49.76", null, 1);
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
        verifyParsedItem(iterator.next(), "banana    mftj",  "1.60", "banana_4011", 18);
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
        assertEquals(23,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "after 8 chocolat    gmrj",  "5.98", null, 6);
        verifyParsedItem(iterator.next(), "nn facial 6x200s    gmrj",  "9.98", null, 7);
        verifyParsedItem(iterator.next(), "nn maria cookies    hrj",  "3.68", null, 8);
        verifyParsedItem(iterator.next(), "kndr choc t6 cs    gmrj",  "4.39", null, 9);
        verifyParsedItem(iterator.next(), "(2)06235650002 cedar beans    mrj",  "3.98", null, 10);
        verifyParsedItem(iterator.next(), "cream puck    hrj",  "2.29", null, 12);
        verifyParsedItem(iterator.next(), "dream \\ljhip    mrj",  "4.04", null, 13);
        verifyParsedItem(iterator.next(), "choc leibniz    mrj",  "4.27", null, 14);
        verifyParsedItem(iterator.next(), "t1erci dark    ghrj",  "4.98", null, 15);
        verifyParsedItem(iterator.next(), "beatrice 1% milk",  "4.54", "beatrice 1% milk_06570010028", 17);
        verifyParsedItem(iterator.next(), "baby cereal    . hrj",  "4.49", null, 21);
        verifyParsedItem(iterator.next(), "oatmeal bby crl    mrj",  "4.49", null, 22);
        verifyParsedItem(iterator.next(), "(2) 06038375883 pco dark choc    . gmrj",  "5.98", null, 23);
        verifyParsedItem(iterator.next(), "liberte yogurt    rq",  "5.99", null, 25);
        verifyParsedItem(iterator.next(), "pco grn onion    hrj",  "1.28", null, 27);
        verifyParsedItem(iterator.next(), "eggplant    mrj",  "4.58", "eggplant_4081", 28);
        verifyParsedItem(iterator.next(), "tomato roma    mrj",  "1.79", "tomato roma_4087", 30);
        verifyParsedItem(iterator.next(), "blues org 125g    hrj",  "3.98", null, 32);
        verifyParsedItem(iterator.next(), "banana organic    hrj",  "3.39", "banana organic_94011", 33);
        verifyParsedItem(iterator.next(), "baby formula",  "23.99", null, 36);
        verifyParsedItem(iterator.next(), "vaseline jelly    ghrj",  "3.28", null, 37);
        verifyParsedItem(iterator.next(), "playgro exp ball    gmrj",  "5.98", null, 40);
        verifyParsedItem(iterator.next(), "sudocrem 60g    gmr j",  "5.19", null, 41);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "ecology fei:                                          0. 07",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on frash, lou on price",2);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",52);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 01570",89);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",51);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #           auth #      resp 001",60);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",1);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "********~*'''*'************'**********",91);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",73);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "2.30",46);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "125.31",48);
        verifyParsedField(fieldValues, ReceiptFieldType.Deposit, "deposit 1                                             0. 25",19);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "123.01",45);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approued",66);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/11/8",64);
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
        assertEquals(7,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "rstr instnt ndle",  "0.98", "rstr instnt ndle_05719775555", 7);
        verifyParsedItem(iterator.next(), "table salt    mrj",  "1.99", null, 8);
        verifyParsedItem(iterator.next(), "garden wafer",  "2.56", "garden wafer_08978200269", 11);
        verifyParsedItem(iterator.next(), "rice stick",  "1.08", "rice stick_693491804007", 14);
        verifyParsedItem(iterator.next(), "beatrice 1% milk",  "4.46", "beatrice 1% milk_06570010028", 16);
        verifyParsedItem(iterator.next(), "cntry hvst brd",  "2.98", "cntry hvst brd_06340004440", 20);
        verifyParsedItem(iterator.next(), "(2)9    plastic bags    grq",  "0.10", null, 22);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 01570",72);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*******'*'******'********''**'*******",74);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.01",25);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",46);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",30);
        verifyParsedField(fieldValues, ReceiptFieldType.Deposit, "deposit 1                                             0.25",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "14.48",26);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref ~          auth m        resp 001",40);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh. lou on price",4);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "14.47",24);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",3);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/18",63);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "ecology fee                                           0.07",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",29);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",56);
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
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "opo squash",  "2.80", "opo squash_3141", 5);
        verifyParsedItem(iterator.next(), "chinese cabbage    mrj",  "1.43", null, 7);
        verifyParsedItem(iterator.next(), "muffin lemn cran",  "4.87", "muffin lemn cran_06038387812", 10);
        verifyParsedItem(iterator.next(), "plastic bags",  "0.05", "plastic bags", 12);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "9.15",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh, low on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",19);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 01570",58);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #            auth #   resp 001",27);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "9.15",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approued",33);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*******~**~*************'********~****",60);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/12/3",31);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",42);
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
        assertEquals(7,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "true almond vani",  "3.98", "true almond vani_02529300168", 6);
        verifyParsedItem(iterator.next(), "earth's own almo",  "3.98", "earth's own almo_62602770001", 11);
        verifyParsedItem(iterator.next(), "chkn wings",  "16.98", "chkn wings_06038369930", 15);
        verifyParsedItem(iterator.next(), "sq cod fillet",  "5.98", "sq cod fillet_06038369124", 17);
        verifyParsedItem(iterator.next(), "ee hanger 24pk",  "6.99", "ee hanger 24pk_05719735541", 19);
        verifyParsedItem(iterator.next(), "beverage glass",  "4.99", "beverage glass_06038312291", 20);
        verifyParsedItem(iterator.next(), "(2)9    plastic bags    grq",  "0.10", null, 21);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "ecology fee                                                0.06",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh, lou on price",4);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",32);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 01570",84);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "~;uperstore",31);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #            auth #    resp 001",40);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",3);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "***'***********'***********h*********",86);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",68);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.60",25);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "44.22",26);
        verifyParsedField(fieldValues, ReceiptFieldType.Deposit, "deposit 1                                                  0.25",13);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "43.62",23);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",46);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/10/11",75);
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
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "activity bag",  "2.00", "activity bag_05870328668", 5);
        verifyParsedItem(iterator.next(), "ninja tur puzzle",  "5.99", "ninja tur puzzle_05956251623", 8);
        verifyParsedItem(iterator.next(), "magazine",  "14.95", "magazine_09256710218", 9);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "24.09",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh, low on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",16);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "j            store: 01570",54);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #          auth #      resp 001",24);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "22.94",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",30);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "~*~~****~**~~***~~~**~~~*~~***********",38);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/12/24",46);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",39);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "1.15",11);
    }

    //no items because there is no wide space. It is from Tesseract. So maybe loosen the criteria of predicting being an item line. don
    //no. no too much heuristics
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
        assertEquals(0,receipt.getItems().size());
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "24.09",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh, low on price",6);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",25);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, ",  store: 01570",75);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",23);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref # auth # resp 001",39);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "22.94",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",5);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card # xxxxwxxwxxxx8017 exp xx/xx",36);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/12/24",45);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",59);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "22.0",17);
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
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "cord trim jungl",  "4.00", "cord trim jungl_05870325083", 5);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "4.20",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh , low on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",12);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store 01570              term z01570l!6c",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #          auth #      resp 001",20);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "4.00",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",26);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "~~~~~~~~~~~~~~~~***~******~~~**~***~~~",34);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/12/24",24);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",36);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.20",7);
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
        assertEquals(7,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "rstr instnt ndle",  "0.98", "rstr instnt ndle_05719775555", 5);
        verifyParsedItem(iterator.next(), "table salt    mrj",  "1.99", null, 6);
        verifyParsedItem(iterator.next(), "garden wafer",  "2.56", "garden wafer_08978200269", 9);
        verifyParsedItem(iterator.next(), "rice stick",  "1.08", "rice stick_693491804007", 12);
        verifyParsedItem(iterator.next(), "beatrice 1% milk",  "4.46", "beatrice 1% milk_06570010028", 14);
        verifyParsedItem(iterator.next(), "cntry hvst brd",  "2.98", "cntry hvst brd_06340004440", 18);
        verifyParsedItem(iterator.next(), "(2)9    plastic bags    gro",  "0.10", null, 20);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "ecology fee                                         0.07",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh. low on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",29);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 01570",68);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",28);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref ~            auth #     resp 001",37);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "**********'*****~*********'***~*******",70);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",52);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.01",23);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "14.48",25);
        verifyParsedField(fieldValues, ReceiptFieldType.Deposit, "deposit 1                                           0.25",16);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "14.47",22);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",43);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/18",59);
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
        assertEquals(15,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "orange juice",  "3.98", "orange juice_04850001964", 9);
        verifyParsedItem(iterator.next(), "fv alv mango cit",  "3.00", "fv alv mango cit_05960007015", 14);
        verifyParsedItem(iterator.next(), "corn bicolor 4ct",  "7.94", "corn bicolor 4ct_03338370121", 18);
        verifyParsedItem(iterator.next(), "pep grn swt 4ct",  "2.98", "pep grn swt 4ct_03338370178", 20);
        verifyParsedItem(iterator.next(), "potato m xd mini",  "5.98", "potato m xd mini_06038310510", 21);
        verifyParsedItem(iterator.next(), "rooster garlic",  "2.48", "rooster garlic_06038388591", 22);
        verifyParsedItem(iterator.next(), "wmelon mini sdls",  "3.97", "wmelon mini sdls_3421", 23);
        verifyParsedItem(iterator.next(), "pepper green swt",  "2.02", "pepper green swt_4065", 26);
        verifyParsedItem(iterator.next(), "mush crem bulk",  "1.64", "mush crem bulk_4648", 28);
        verifyParsedItem(iterator.next(), "bc saus hot fp",  "10.35", "bc saus hot fp_2157230", 33);
        verifyParsedItem(iterator.next(), "split chkn wing",  "17.58", "split chkn wing_2163820", 34);
        verifyParsedItem(iterator.next(), "split chkn wing",  "16.65", "split chkn wing_2163820", 35);
        verifyParsedItem(iterator.next(), "lamb bonless leg",  "24.50", "lamb bonless leg_2174190", 36);
        verifyParsedItem(iterator.next(), "sq basa flts",  "7.98", "sq basa flts_06038377431", 38);
        verifyParsedItem(iterator.next(), "croissant cp",  "5.00", "croissant cp_46036330079", 40);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "ecology fee                                       0.08",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "blg on fresh, lou on price",5);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",46);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 01570 ",84);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",45);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #             auth #     resp 001",54);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",4);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*****'*****~****'**************''*'*'",86);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",68);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "116.71",42);
        verifyParsedField(fieldValues, ReceiptFieldType.Deposit, "deposit 1                                         0.25",16);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "116.71",41);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approueo",60);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/4/4",75);

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
        assertEquals(7,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "xcellence 90% br",  "5.98", "xcellence 90% br_03746604133", 8);
        verifyParsedItem(iterator.next(), "orange navel 5lb",  "3.98", "orange navel 5lb_03338311006", 12);
        verifyParsedItem(iterator.next(), "cucumber english",  "1.47", "cucumber english_03338367101", 13);
        verifyParsedItem(iterator.next(), "chinese cabbage    mrj",  "2.88", null, 14);
        verifyParsedItem(iterator.next(), "longan fruit",  "3.98", "longan fruit_80029363211", 16);
        verifyParsedItem(iterator.next(), "nz goat stew bi",  "12.99", "nz goat stew bi_62891210405", 18);
        verifyParsedItem(iterator.next(), "(5l9    plastic bags    grq",  "0.25", null, 20);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "31.84",24);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh. lo\1 on price",4);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",28);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 01570",67);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",27);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref ~            auth m resp 001",37);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "31.53",22);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approued",43);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",3);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "**'***********''*' *''***",49);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/4/4",58);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",50);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.31",23);
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
        assertEquals(9,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "bea    homo    milk jg",  "4.99", "bea    homo    milk jg_06570010026", 6);
        verifyParsedItem(iterator.next(), "pco european                  ext    ext",  "5.98", "pco european                  ext_06038399721", 10);
        verifyParsedItem(iterator.next(), "pepper green swt    hrj",  "2.08", "pepper green swt_4065", 14);
        verifyParsedItem(iterator.next(), "chi nese cabbage    mrj",  "2.21", null, 16);
        verifyParsedItem(iterator.next(), "tov gh red    hrj",  "2.77", "tov gh red_4664", 18);
        verifyParsedItem(iterator.next(), "cauliflower",  "3.97", "cauliflower_73875120001", 20);
        verifyParsedItem(iterator.next(), "romaine heart    hrj",  "3.48", null, 21);
        verifyParsedItem(iterator.next(), "longan fruit",  "4.48", "longan fruit_80029363211", 22);
        verifyParsedItem(iterator.next(), "plastic bags",  "0.05", "plastic bags", 24);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "ecology fee                                                 0.08",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh. lou on pr i ce",2);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",32);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "s tore: 01570",76);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, ":;uperstore",31);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "fief #            auth #       resp 001",41);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",1);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "\"*\"\"'******'**\"'*****************'*****",78);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",60);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.",26);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "30.64",28);
        verifyParsedField(fieldValues, ReceiptFieldType.Deposit, "deposit 1                                                   0. 25",8);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "30.34",25);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",47);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/4/11",45);
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
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "bread rais cinn",  "2.98", "bread rais cinn", 8);
        verifyParsedItem(iterator.next(), "hug snug dry st5    gmrj",  "20.00", null, 11);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "23.98",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "bi g on fresh, low on pr ies",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",19);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store : 01",69);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "rcss 1570  !1821 calgary tf  lail",1);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card #   ~r8 17          exp z;!,l:loc",26);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/4/6",60);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",53);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "1.00",12);
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
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "gold white rice",  "25.98", "gold white rice_05719711350", 5);
        verifyParsedItem(iterator.next(), "fmost lg egg",  "2.99", "fmost lg egg_06038369504", 7);
        verifyParsedItem(iterator.next(), "dlx mixed nuts",  "6.19", "dlx mixed nuts_63297", 9);
        verifyParsedItem(iterator.next(), "corn bicolor 4ct",  "3.98", "corn bicolor 4ct_03338370121", 12);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "39.45",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "bi g on fresh. low on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",19);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 01570",61);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "uperstore- - - - -           - - --~~",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref m            auth il    resp 001",27);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "39.14",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approued",33);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "~~~***************************~*****",63);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/5/3",50);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",43);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.31",14);
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
        assertEquals(6,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "sour cream",  "8.96", "sour cream_06038311205", 7);
        verifyParsedItem(iterator.next(), "dole italian",  "2.97", "dole italian_07143000819", 11);
        verifyParsedItem(iterator.next(), "banana",  "2.81", "banana_4011", 12);
        verifyParsedItem(iterator.next(), "euro wiener",  "12.00", "euro wiener_06435971319", 15);
        verifyParsedItem(iterator.next(), "one step prg tst",  "6.79", "one step prg tst_06038365392", 19);
        verifyParsedItem(iterator.next(), "ceramic soap",  "7.99", "ceramic soap_05870300175", 23);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "42.26",28);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh , low on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",33);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store : 01!570",80);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",32);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #           auth #      resp 001",41);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "41.52",24);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "appf~oved",47);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card # *~~***~****023 1       exp ~ / ~",39);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/6/2",45);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",65);
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
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "trident wtrmln",  "1.79", "trident wtrmln_05770001013", 5);
        verifyParsedItem(iterator.next(), "garden wafer",  "1.48", "garden wafer_08978200269", 6);
        //TODO one item was omitted
        verifyParsedItem(iterator.next(), "wnat mlti prenat",  "31.96", "wnat mlti prenat_62527303621", 10);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "42.26",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fre!sh , low on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",22);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 01!570",60);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #                 auth #         resp 001",30);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "40.32",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",36);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card # \"'\"'\"'\"'\"'\"'\"'\"'\"'\"'\"'8017      exp \"'\"'/ \"'\"'",28);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/11/18",52);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",45);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "1.94",17);
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
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "ponkan mandarin",  "3.23", "ponkan mandarin_3209", 5);
        verifyParsedItem(iterator.next(), "orange navel lg",  "1.92", "orange navel lg_4012", 7);
        verifyParsedItem(iterator.next(), "lemon    mrj",  "1.54", null, 9);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "6.69",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh, low on pr ice",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",17);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 01570",63);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",16);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #           ruth #     resp 001",25);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "6.69",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",33);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "********** **********************'****",65);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/17",49);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",42);
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
        assertEquals(6,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "r h br flouh",  "3.39", "r h br flouh_05900001484", 5);
        verifyParsedItem(iterator.next(), "bc dx frs wp van",  "2.20", "bc dx frs wp van_06563307781", 6);
        verifyParsedItem(iterator.next(), "pka dot cndl",  "1.59", "pka dot cndl_06620000867", 7);
        verifyParsedItem(iterator.next(), "pc cream cheese",  "5.39", "pc cream cheese_06038302200", 9);
        verifyParsedItem(iterator.next(), "nn chs slice lgt",  "3.39", "nn chs slice lgt_06038373902", 10);
        verifyParsedItem(iterator.next(), "strawberries 2lb",  "5.96", "strawberries 2lb_750301951901", 12);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "23.10",16);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh, low on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",20);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 01!570",58);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #          auth #      resp 001",28);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "23.10",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",34);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card # ~~~~~~~~-~~8017       exp ~~~~~",26);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/25",50);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",43);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.08",14);
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
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "med   lmtd edition",  "3.99", "med   lmtd edition_06568400619", 7);
        verifyParsedItem(iterator.next(), "cubed beef",  "4.50", "cubed beef_2839380", 9);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "8.49",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh, lou on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",16);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 01570",54);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #              auth #       resp 001",24);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "8.49",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approued",28);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*****'*************'*'*''*******'\"*'**",56);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/5/27",26);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",38);
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
//TODO P LUM            RED was missed
        assertEquals(18,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "o6o3s'io&96s    mix    lemon    hrj",  "1.27", null, 9);
        verifyParsedItem(iterator.next(), "arz pick pepper    h",  "3.48", "arz pick pepper_82809606057", 12);
        verifyParsedItem(iterator.next(), "papaya",  "l.0", "papaya", 17);
        verifyParsedItem(iterator.next(), "broccoli",  "2.48", "broccoli_4060", 23);
        verifyParsedItem(iterator.next(), "pepper green swt",  "pepper.0", "pepper green swt_4065", 24);
        verifyParsedItem(iterator.next(), "potato baking",  "11", "potato baking_4042", 26);
        verifyParsedItem(iterator.next(), "6h ~    pc apple    ambrs",  "i.0", null, 28);
        verifyParsedItem(iterator.next(), "banana organic    mrj",  "2.0", "banana organic_94011", 30);
        verifyParsedItem(iterator.next(), "chick gizzrd    h",  "2.15", "chick gizzrd_2838330", 33);
        verifyParsedItem(iterator.next(), "pc free chk drum    h",  "4.31", "pc free chk drum_2852620", 35);
        verifyParsedItem(iterator.next(), "hairtail",  "6.88", "hairtail_77016160104", 38);
        verifyParsedItem(iterator.next(), "kingnish steak",  "11.98", "kingnish steak_77016162201", 40);
        verifyParsedItem(iterator.next(), ":35    deli", null, null, 42);
        verifyParsedItem(iterator.next(), "'4 (:)/6i. ~ v.~>j~14 sr df .er    tofu hrj",  "5.08", null, 43);
        verifyParsedItem(iterator.next(), " ~jo ' 08:j03 fri    tirj",  "1.98", null, 45);
        verifyParsedItem(iterator.next(), "r21.    .~. ic    bags",  "9", null, 47);
        verifyParsedItem(iterator.next(), "43-apparal    .", null, null, 49);
        verifyParsedItem(iterator.next(), "joe cos pad  100s",  "2.00", "joe cos pad  100s_058703020", 50);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "60.67",55);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh, lou on pr ice",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",60);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "s iore: 01570",107);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "supe rstore",59);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref ~               auth ~        resp 001",68);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "60.36",52);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "app roved",72);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*****''\"''''**''*\"**''**'*****'",90);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/6/3",70);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",91);
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
        assertEquals(6,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "aquafina",  "3.49", "aquafina_06900006118", 5);
        verifyParsedItem(iterator.next(), "wmelon red sdls",  "4.98", "wmelon red sdls_4032", 9);
        verifyParsedItem(iterator.next(), "forest ham",  "5.00", "forest ham_06148304020", 11);
        verifyParsedItem(iterator.next(), "flav ham    hrj",  "5.00", null, 12);
        verifyParsedItem(iterator.next(), "hug snug dry st5",  "21.99", "hug snug dry st5_03600040679", 14);
        verifyParsedItem(iterator.next(), "bag med",  "3.29", "bag med_76379593735", 18);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "ecology fee                                           0.24",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh. lou on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",26);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 01570",67);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",25);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "r  ef #           auth #      resp 001",34);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "\"*******' ' . *****\"'*'**'******\"''**' ***",58);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",49);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "1.26",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "46.45",22);
        verifyParsedField(fieldValues, ReceiptFieldType.Deposit, "deposit 1                                              1.20",7);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "45.19",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",40);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/6/21",56);
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
        assertEquals(8,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "orange navel 5lb",  "5.88", "orange navel 5lb_03338311006", 42);
        verifyParsedItem(iterator.next(), "banana",  "1.35", "banana_4011", 43);
        verifyParsedItem(iterator.next(), "cherries red    mrj",  "7.29", null, 45);
        verifyParsedItem(iterator.next(), "tc baby powder    gmrj",  "0.99", null, 48);
        verifyParsedItem(iterator.next(), "j&j baby wash    gmrj",  "7.94", null, 49);
        verifyParsedItem(iterator.next(), "baby bar    gmrj",  "2.49", null, 52);
        verifyParsedItem(iterator.next(), "sudocrem 60g    ghrj",  "3.98", null, 53);
        verifyParsedItem(iterator.next(), "plastic bags",  "0.05", "plastic bags", 55);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "30.74",59);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh, lm1 on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",63);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store 01570                  termz 0157016c",65);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "supersto re",62);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref ~            ruth #        resp 001",71);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "29.97",56);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approued",77);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "***~******'****** ****************'*'**",96);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/6/18",94);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",86);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.77",58);
    }

    @Test
    public void receipt_nullPointer1()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_nullPointer1, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());//TODO ML to detect layout
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
