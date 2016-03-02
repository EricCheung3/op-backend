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
        //TODO Why no items?
        assertEquals(0,receipt.getItems().size());
//        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "ecdluby fee 0.08",12);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "",27);
        verifyParsedField(fieldValues, ReceiptFieldType.Deposit, " deposit 1 0.25",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "cardkz *$k*****#$***",5);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "",-1);
    }

    //TODO why no items?
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
        assertEquals(13,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "rooster rice",  "25.78", "rooster rice_05719777953", 5);
        verifyParsedItem(iterator.next(), "van bircrm mx",  "3.50", "van bircrm mx", 7);
        verifyParsedItem(iterator.next(), "cm dis prncs cnd",  "2.99", "cm dis prncs cnd", 8);
        verifyParsedItem(iterator.next(), "cm scribblers ha",  "4.49", "cm scribblers ha", 9);
        verifyParsedItem(iterator.next(), "dh fc starter",  "2.79", "dh fc starter", 10);
        verifyParsedItem(iterator.next(), "after bite kids",  "4.99", "after bite kids", 12);
        verifyParsedItem(iterator.next(), "c&c astringent",  "6.49", "c&c astringent", 14);
        verifyParsedItem(iterator.next(), "acne wash",  "8.49", "acne wash", 15);
        verifyParsedItem(iterator.next(), "st ives vanilla",  "2.98", "st ives vanilla", 16);
        verifyParsedItem(iterator.next(), "shower creme sof",  "3.93", "shower creme sof", 17);
        verifyParsedItem(iterator.next(), "wlin cpck dcrtng",  "5.04", "wlin cpck dcrtng", 19);
        verifyParsedItem(iterator.next(), "hp jar scraper",  "5.00", "hp jar scraper", 20);
        verifyParsedItem(iterator.next(), "measure cup    gmrj",  "4.29", null, 21);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "82.97",25);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh, low on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "9711 23 ave nw",29);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store : 01549",71);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",28);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #          auth #      resp 001",37);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "80.76",23);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "pproved",43);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-490-3918",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card # ~~~~~~~~~~~8017       exp ~~~~~",35);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/16",61);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",54);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "2.21",24);
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
        assertEquals(2,receipt.getItems().size());
        //TODO negative price
        verifyParsedItem(iterator.next(), "van btrcrm mx",  "3.50", "van btrcrm mx_r06563345804", 6);
        verifyParsedItem(iterator.next(), "van btrcrm mx",  "3.50", "van btrcrm mx_r06563345804", 7);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "7.00",9);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh, lou on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "9711 23 ave nw",13);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store : 01549",48);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #            auth#       resp 000",21);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "7.00",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approueo",27);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-490-3918",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "**************************************",50);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/16",39);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",32);

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
        assertEquals(19,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "pep red swt 4ct",  "2.96", "pep red swt 4ct_03338370119", 6);
        verifyParsedItem(iterator.next(), "pep grn swt 4ct",  "2.96", "pep grn swt 4ct_03338370178", 7);
        verifyParsedItem(iterator.next(), "eggplant",  "3.37", "eggplant_4081", 8);
        verifyParsedItem(iterator.next(), "potato white",  "3.98", "potato white_4083", 10);
        verifyParsedItem(iterator.next(), "chinese cabbage    hrj",  "2.06", null, 12);
        verifyParsedItem(iterator.next(), "don qua",  "3.08", "don qua_668", 14);
        verifyParsedItem(iterator.next(), "pork loin ccut",  "7.56", "pork loin ccut_2114990", 17);
        verifyParsedItem(iterator.next(), "pork loin chop",  "9.33", "pork loin chop_2180330", 18);
        verifyParsedItem(iterator.next(), "sr xfirm tofu",  "3.98", "sr xfirm tofu_05786400008", 20);
        verifyParsedItem(iterator.next(), "fresh tofu",  "1.79", "fresh tofu_05786400009", 22);
        verifyParsedItem(iterator.next(), "toothpicks",  "1.79", "toothpicks_62565980312", 24);
        verifyParsedItem(iterator.next(), "leathr vst",  "8.94", "leathr vst_06366414260", 26);
        verifyParsedItem(iterator.next(), "opp jean",  "3.94", "opp jean_06366415825", 27);
        verifyParsedItem(iterator.next(), "opp jean",  "3.94", "opp jean_06366415825", 28);
        verifyParsedItem(iterator.next(), "opp jean",  "3.94", "opp jean_06366415825", 29);
        verifyParsedItem(iterator.next(), "opp jean",  "3.94", "opp jean_06366415825", 30);
        verifyParsedItem(iterator.next(), "gathr yoke",  "3.94", "gathr yoke_06366421664", 31);
        verifyParsedItem(iterator.next(), "rb fl st p",  "6.94", "rb fl st p_06366433269", 32);
        verifyParsedItem(iterator.next(), "micro set",  "4.94", "micro set_06366436582", 33);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "85.50",36);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh. lou on price",4);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "9711 23 ave nw",40);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 01549",81);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",39);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "re f#            auth #     resp 001",48);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "83.38",34);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "ap p rov ed",55);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-490-3918",3);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "********************************'*****",83);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/21",71);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",64);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "2.12",35);
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
        assertEquals(7,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "chap ic snde sd",  "4.99", "chap ic snde sd_06294200366", 8);
        verifyParsedItem(iterator.next(), "shana naan",  "1.99", "shana naan_06457970003", 9);
        verifyParsedItem(iterator.next(), "shana lacha para",  "1.99", "shana lacha para_503003900298", 10);
        verifyParsedItem(iterator.next(), "wfz dmp rd rice",  "8.97", "wfz dmp rd rice_690761966247", 11);
        verifyParsedItem(iterator.next(), "banana",  "4.36", "banana_4011", 14);
        verifyParsedItem(iterator.next(), "tilapia whole",  "8.87", "tilapia whole_2121080", 17);
        verifyParsedItem(iterator.next(), "fzn tilapia",  "4.01", "fzn tilapia_2863070", 18);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "35.18",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh. lou on price",5);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "9711 23 ave nw",24);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 01549",70);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "supersto re",23);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #            auth #      resp 001",33);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "35.18",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",39);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-490-3918",4);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "'' ******\"'****~****~*'*********~*''",72);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/9/14",56);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",49);

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
        assertEquals(5,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "cocon yogo ice m",  "1.48", "cocon yogo ice m_955643700364", 6);
        verifyParsedItem(iterator.next(), "ccn pudding",  "5.97", "ccn pudding_955643700584", 7);
        verifyParsedItem(iterator.next(), "wfz dmp mixed",  "11.96", "wfz dmp mixed_690761966252", 10);
        verifyParsedItem(iterator.next(), "squid tentacles",  "11.96", "squid tentacles_77016150318", 13);
        verifyParsedItem(iterator.next(), "plastic bags",  "0.05", "plastic bags", 16);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "31.50",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh. low on price",4);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "9711 23 ave nw",23);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 01549",62);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",22);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "31.42",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approued",37);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-490-3918",3);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "~~******'******'*******'*****",64);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/10/11",53);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",46);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.08",18);
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
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "cll05844952001 np rice puffs    hrj",  "8.97", null, 7);
        verifyParsedItem(iterator.next(), "organic 2% milk",  "7.98", "organic 2% milk_06870030942", 9);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "ecology fee                                       0.08",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh, lou on price",4);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "9711 23 ave nw",19);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 01549",61);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "rcss - 1549     9711-23ave",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref \"              auth #      resp 001",29);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-490-3918",3);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "******'****'**'*'********************",63);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",45);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "17.28",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Deposit, "deposit 1                                         0.25",13);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "17.28",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",35);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/19",52);
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
        assertEquals(21,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "banana",  "3.93", "banana_4011", 6);
        verifyParsedItem(iterator.next(), "orange navel lg",  "4.07", "orange navel lg_4012", 8);
        verifyParsedItem(iterator.next(), "zucchini green",  "7.43", "zucchini green_4067", 10);
        verifyParsedItem(iterator.next(), "onion green",  "1.34", "onion green_4068", 12);
        verifyParsedItem(iterator.next(), "tomato roma",  "3.08", "tomato roma_4087", 14);
        verifyParsedItem(iterator.next(), "spinach bunched    mrj",  "3.34", null, 16);
        verifyParsedItem(iterator.next(), "ginger root",  "1.84", "ginger root_4612", 18);
        verifyParsedItem(iterator.next(), "cilantro",  "0.87", "cilantro_4889", 20);
        verifyParsedItem(iterator.next(), "8.2540144065 pep grn swt 4ct    mrj",  "2.98", null, 21);
        verifyParsedItem(iterator.next(), "nn wieners chkn",  "1.67", "nn wieners chkn_06038301737", 23);
        verifyParsedItem(iterator.next(), "blue cod fillets",  "10.00", "blue cod fillets_06038387647", 25);
        verifyParsedItem(iterator.next(), "tilapia whole",  "8.75", "tilapia whole_2121080", 26);
        verifyParsedItem(iterator.next(), "czl7701f,j503 16 squid tentacles    hrj",  "13.96", null, 27);
        verifyParsedItem(iterator.next(), "sr xfirm tofu",  "1.99", "sr xfirm tofu_05786400008", 30);
        verifyParsedItem(iterator.next(), "fresh tofu",  "1.79", "fresh tofu_05786400009", 31);
        verifyParsedItem(iterator.next(), "chinese ssg",  "4.48", "chinese ssg_06673657688", 32);
        verifyParsedItem(iterator.next(), "battery 9v",  "4.99", "battery 9v_04133311601", 34);
        verifyParsedItem(iterator.next(), "jkw hula hoop",  "2.37", "jkw hula hoop_05870321931", 35);
        verifyParsedItem(iterator.next(), "chalk ast",  "0.94", "chalk ast_06365236250", 36);
        verifyParsedItem(iterator.next(), "lacepencil",  "6.94", "lacepencil_06366447081", 38);
        verifyParsedItem(iterator.next(), "ladies sportwear",  "4.94", "ladies sportwear_*8092", 39);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "90.69",45);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "e:ig on fresh, lm1 on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "9711 23 ave nw",49);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store coupon savings (2)                                            2.02",69);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "sup:3rsto r e!",48);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #                auth #     re sp 001",58);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "89.68",43);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approv ed",64);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-490-3918",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "***\"****~'*******************************",72);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/3/21",86);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",79);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "1.01",44);
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
        assertEquals(21,receipt.getItems().size());
        //TODO items are missing because having 5+ widespaces
        verifyParsedItem(iterator.next(), "ajishima r sea'n",  "3.28", "ajishima r sea'n_0111522231", 6);
        verifyParsedItem(iterator.next(), "pocky",  "3.99", "pocky_073141551811", 7);
        verifyParsedItem(iterator.next(), "pei pa kao syr",  "5.98", "pei pa kao syr_08136436369", 8);
        verifyParsedItem(iterator.next(), "t&t pork floss",  "7.99", "t&t pork floss_77670324991", 9);
        verifyParsedItem(iterator.next(), "pc bm brown eggs",  "3.48", "pc bm brown eggs_6038375010", 11);
        verifyParsedItem(iterator.next(), "organic 2% milk",  "8.89", "organic 2% milk_06870030942", 21);
        verifyParsedItem(iterator.next(), "eo alm bev og",  "2.49", "eo alm bev og_62602770031", 24);
        verifyParsedItem(iterator.next(), "ffo apl swt pto",  "4.49", "ffo apl swt pto_63463300023", 26);
        verifyParsedItem(iterator.next(), "ffo bna kale",  "4.49", "ffo bna kale_63463300024", 27);
        verifyParsedItem(iterator.next(), "lc pr kale peas",  "1.89", "lc pr kale peas_85886000105", 28);
        verifyParsedItem(iterator.next(), "lc apl btr sq cr",  "1.89", "lc apl btr sq cr_85886000106", 29);
        verifyParsedItem(iterator.next(), "blues pints",  "3.88", "blues pints_03338322201", 31);
        verifyParsedItem(iterator.next(), "eggplant lng",  "2.25", "eggplant lng_3089", 32);
        verifyParsedItem(iterator.next(), "grape red sol cs",  "7.22", "grape red sol cs_4023", 34);
        verifyParsedItem(iterator.next(), "onion green",  "1.34", "onion green_4068", 36);
        verifyParsedItem(iterator.next(), "cilantro",  "0.87", "cilantro_4889", 38);
        verifyParsedItem(iterator.next(), "strawberries 2lb",  "5.94", "strawberries 2lb_74007500085", 39);
        verifyParsedItem(iterator.next(), "banana organic",  "4.56", "banana organic_94011", 40);
        verifyParsedItem(iterator.next(), "gerber grad lilt",  "6.49", "gerber grad lilt_86513178887", 43);
        verifyParsedItem(iterator.next(), "colouring book",  "5.23", "colouring book_978037586363", 45);
        verifyParsedItem(iterator.next(), "colouring book",  "5.23", "colouring book_978037586363", 46);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "ecology fee                                               0.08",22);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh, low on price",4);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "9711 23 ave nw",55);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store 01549              terti z0154914c",57);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",54);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref d          auth #      resp 001",64);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-490-3918",3);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*'**'*'***'******'**'''*'*\"*''***'*'",89);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",79);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "1.15",49);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "105.70",51);
        verifyParsedField(fieldValues, ReceiptFieldType.Deposit, "deposit 1                                                 0.30",25);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "104.55",47);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approued",70);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/28",87);

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
        assertEquals(9,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "choco pie 12s",  "2.98", "choco pie 12s_880111780763", 5);
        verifyParsedItem(iterator.next(), "wfz dmp rd rice",  "5.98", "wfz dmp rd rice_690761966247", 7);
        verifyParsedItem(iterator.next(), "vege chick steam",  "5.99", "vege chick steam_79878232414", 9);
        verifyParsedItem(iterator.next(), "eggplant lng",  "0.92", "eggplant lng_3089", 11);
        verifyParsedItem(iterator.next(), "banana",  "4.21", "banana_4011", 13);
        verifyParsedItem(iterator.next(), "onion green",  "0.57", "onion green_4068", 15);
        verifyParsedItem(iterator.next(), "fresh garlic",  "1.02", "fresh garlic_4610", 16);
        verifyParsedItem(iterator.next(), "whl catfish",  "16.22", "whl catfish_2860330", 19);
        verifyParsedItem(iterator.next(), "frzn bay scallps",  "7.98", "frzn bay scallps_81526101197", 20);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "45.87",22);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh. low on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "9711 23 ave nw",26);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 01549",67);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",25);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref ~           auth ~       resp 001",34);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "45.87",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",40);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-490-3918",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "******\"~*******************~** ***'**",69);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/9/6",57);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",49);

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
        assertEquals(14,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "sesame oil",  "6.99", "sesame oil_01282202202", 5);
        verifyParsedItem(iterator.next(), "np rice puffs",  "8.04", "np rice puffs_05844962001", 7);
        verifyParsedItem(iterator.next(), "gb zesty orange",  "5.99", "gb zesty orange_83463900001", 9);
        verifyParsedItem(iterator.next(), "eggplant lng",  "1.60", "eggplant lng_3089", 11);
        verifyParsedItem(iterator.next(), "banana",  "2.41", "banana_4011", 13);
        verifyParsedItem(iterator.next(), "broccoli",  "2.27", "broccoli_4060", 15);
        verifyParsedItem(iterator.next(), "onion green",  "0.67", "onion green_4068", 16);
        verifyParsedItem(iterator.next(), "cabbage green    hrj",  "3.46", null, 17);
        verifyParsedItem(iterator.next(), "cilantro",  "0.87", "cilantro_4889", 19);
        verifyParsedItem(iterator.next(), "organic cauliflo",  "5.48", "organic cauliflo_94079", 20);
        verifyParsedItem(iterator.next(), "tilapia whole",  "7.00", "tilapia whole_2121080", 22);
        verifyParsedItem(iterator.next(), "sr medium tofu",  "1.77", "sr medium tofu_05786400001", 24);
        verifyParsedItem(iterator.next(), "firm tofu",  "1.99", "firm tofu_05786400011", 25);
        verifyParsedItem(iterator.next(), "egg tube tofu",  "1.59", "egg tube tofu_06638709305", 26);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "50.43",29);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh. lowon price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "9711 23 ave nw",33);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 01549",72);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",32);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #            ruth #      resp 001",41);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "50.13",27);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",47);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-490-3918",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "'**h**~***                      ************'*",74);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/3/14",63);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",56);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.30",28);
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
        assertEquals(12,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "pc udon noodles",  "1.69", "pc udon noodles_06038389752", 6);
        verifyParsedItem(iterator.next(), "poppan crackers",  "1.99", "poppan crackers_08978201939", 9);
        verifyParsedItem(iterator.next(), "wfz   dmp     juju mng",  "5.98", "wfz   dmp     juju mng_690761966245", 11);
        verifyParsedItem(iterator.next(), "blues pints",  "3.88", "blues pints_03338322201", 14);
        verifyParsedItem(iterator.next(), "strawberries 2lb",  "5.97", "strawberries 2lb_07143001105", 15);
        verifyParsedItem(iterator.next(), "banana organic",  "5.44", "banana organic_94011", 16);
        verifyParsedItem(iterator.next(), "opp fl mit",  "3.94", "opp fl mit_06366423936", 19);
        verifyParsedItem(iterator.next(), "rb fl st p",  "6.94", "rb fl st p_06366433269", 20);
        verifyParsedItem(iterator.next(), "dptslp set",  "6.94", "dptslp set_06366442879", 21);
        verifyParsedItem(iterator.next(), "men's sportwear",  "0.94", "men's sportwear_8093", 22);
        verifyParsedItem(iterator.next(), "inerwr/slp/hosry",  "0.94", "inerwr/slp/hosry_*8097", 23);
        verifyParsedItem(iterator.next(), "pc charity",  "1.00", null, 25);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "46.64",28);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh. lou on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "9711 23 ave nw",32);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "stobe:     0 15~",78);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",31);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref n           auth n               resp 001",40);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "45.65",26);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",46);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-490-3918",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*'\"***********************'*****n~***",80);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/22",66);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",59);

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
        assertEquals(6,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "heinz ket sqz",  "4.87", "heinz ket sqz_05700006308", 5);
        verifyParsedItem(iterator.next(), "stky rice dmplng",  "6.99", "stky rice dmplng_061163601187", 9);
        verifyParsedItem(iterator.next(), "wfz   dmp     red bn",  "2.99", "wfz   dmp     red bn_690761966243", 10);
        verifyParsedItem(iterator.next(), "wfz dmp rd rice",  "2.99", "wfz dmp rd rice_690761966247", 11);
        verifyParsedItem(iterator.next(), "wfz dmp mixed",  "2.99", "wfz dmp mixed_690761966252", 12);
        verifyParsedItem(iterator.next(), "rd sdls grapes    hrj",  "3.96", null, 14);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "24.79",16);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh. lmj on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "9711 23 ave nw",20);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store : 01549",59);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref ii          auth n     resp 001",28);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "24.79",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "appro ued",34);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-490-3918",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*************~************************",61);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/11/23",50);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",43);

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
        assertEquals(9,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "mj gnt pnd chclt",  "4.28", "mj gnt pnd chclt_888607710207", 5);
        verifyParsedItem(iterator.next(), "wfz   dmp     red bn",  "2.99", "wfz   dmp     red bn_690761966243", 7);
        verifyParsedItem(iterator.next(), "wfz dmp rd rice",  "5.98", "wfz dmp rd rice_690761966247", 8);
        verifyParsedItem(iterator.next(), "wfz dmp mixed",  "5.98", "wfz dmp mixed_690761966252", 10);
        verifyParsedItem(iterator.next(), "tt pancake sesme",  "3.59", "tt pancake sesme_77670302223", 12);
        verifyParsedItem(iterator.next(), "raspberries pint    hrj",  "3.48", null, 14);
        verifyParsedItem(iterator.next(), "rd sdls grapes",  "4.88", "rd sdls grapes_85495700132", 15);
        verifyParsedItem(iterator.next(), "crb flvrd flakes",  "2.69", "crb flvrd flakes_06038368835", 17);
        verifyParsedItem(iterator.next(), "whl catfish",  "16.60", "whl catfish_2860330", 18);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "50.68",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh, low on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "9711 23 ave nw",25);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store : 01549",66);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",24);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "50.47",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approueid",39);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-490-3918",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "               ***********~ ** '\"",49);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/11/16",57);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",50);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.21",20);

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
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "raspberries pint",  "4.96", "raspberries pint_71575610003", 8);
        verifyParsedItem(iterator.next(), "french bread",  "1.00", "french bread_46038347442", 10);
        verifyParsedItem(iterator.next(), "chery strudl",  "4.99", "chery strudl_46038373178", 11);
        verifyParsedItem(iterator.next(), "focaccia triangl",  "6.00", "focaccia triangl_62855307101", 12);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "16.95",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh. lou on price",4);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "9711 23 ave nw",19);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 0 49",58);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "f!ef #               ruth #       resp 001",27);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "16.95",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approued",31);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-490-3918",3);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "\"**'**'\"******************'********'*",60);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/4/14",29);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",42);

    }


}
