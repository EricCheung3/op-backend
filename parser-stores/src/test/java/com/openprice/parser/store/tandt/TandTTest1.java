package com.openprice.parser.store.tandt;

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
public class TandTTest1 extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_02_01_14_10_52.jpg.henryHuang.txt")
    private Resource receipt_2015_02_01_14_10_52;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_02_01_14_15_22.jpg.henryHuang.txt")
    private Resource receipt_2015_02_01_14_15_22;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_02_01_14_17_10.jpg.henryHuang.txt")
    private Resource receipt_2015_02_01_14_17_10;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_02_09_11_36_14.jpg.hengshuai.txt")
    private Resource receipt_2015_02_09_11_36_14;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_02_09_13_15_02.jpg.henryHuang.txt")
    private Resource receipt_2015_02_09_13_15_02;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_02_09_13_15_52.jpg.henryHuang.txt")
    private Resource receipt_2015_02_09_13_15_52;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_02_09_13_16_16.jpg.henryHuang.txt")
    private Resource receipt_2015_02_09_13_16_16;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_02_09_13_16_46.jpg.henryHuang.txt")
    private Resource receipt_2015_02_09_13_16_46;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_02_09_13_21_50.jpg.henryHuang.txt")
    private Resource receipt_2015_02_09_13_21_50;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_02_09_13_23_52.jpg.henryHuang.txt")
    private Resource receipt_2015_02_09_13_23_52;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_02_09_13_33_34.jpg.henryHuang.txt")
    private Resource receipt_2015_02_09_13_33_34;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_02_09_13_37_31.jpg.henryHuang.txt")
    private Resource receipt_2015_02_09_13_37_31;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_02_19_22_58_18.jpg.hengshuai.txt")
    private Resource receipt_2015_02_19_22_58_18;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_02_27_21_51_03.jpg.hengshuai.txt")
    private Resource receipt_2015_02_27_21_51_03;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_04_04_21_25_38.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_25_38;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_04_04_21_28_08.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_28_08;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_04_04_21_31_54.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_31_54;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_04_04_21_32_09.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_32_09;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_04_04_21_33_48.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_33_48;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_04_04_22_12_29.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_12_29;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_04_04_22_17_37.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_17_37;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_05_02_21_48_23.jpg.momingzhen159.txt")
    private Resource receipt_2015_05_02_21_48_23;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_05_02_21_48_33.jpg.momingzhen159.txt")
    private Resource receipt_2015_05_02_21_48_33;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_05_02_22_18_32.jpg.haipeng.txt")
    private Resource receipt_2015_05_02_22_18_32;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_05_02_22_18_50.jpg.haipeng.txt")
    private Resource receipt_2015_05_02_22_18_50;

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_06_04_21_15_50.jpg.henryHuang.txt")
    private Resource receipt_2015_06_04_21_15_50;

    @Value("classpath:/testFiles/TAndT/RCSSsellsTandT/2015_04_04_21_54_42.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_54_42;

    @Value("classpath:/testFiles/TAndT/RCSSsellsTandT/2014_12_06_23_33_53.jpg.hengshuai.txt")
    private Resource receipt_2014_12_06_23_33_53;


    @Test
    public void receipt_2015_02_01_14_10_52()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_01_14_10_52, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(24,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "large bun-red bag", "2.789", null, 7);
        verifyParsedItem(iterator.next(), "one oay old bread-packing", "2.69", null, 9);
        verifyParsedItem(iterator.next(), "pork leg boneless", "9.49", null, 11);
        verifyParsedItem(iterator.next(), "(sald pork side ribs(whole)", "11.18", null, 13);
        verifyParsedItem(iterator.next(), "(sale) chicken legs(back attached)", "8.74", null, 15);
        verifyParsedItem(iterator.next(), "frozen rock fish fillet", "7.61", null, 18);
        //TODO: missing
        //(SALE) CARROT
//        0.360 kg s $2. 18/kg                                   $0.78
        verifyParsedItem(iterator.next(), "cilanntro", "0.79", null, 23);
        verifyParsedItem(iterator.next(), "(sale) garlic sprout(leek bud)", "1.98", null, 24);
        verifyParsedItem(iterator.next(), "napa (short)", "2.67", null, 26);
        verifyParsedItem(iterator.next(), "(sale) tomatoes", "3.69", null, 28);
        verifyParsedItem(iterator.next(), "(sale) don gua -china", "1.92", null, 30);
        verifyParsedItem(iterator.next(), "(sale) you-choy", "4.06", null, 32);
        verifyParsedItem(iterator.next(), "(sale) banana", "3.95", null, 34);
        verifyParsedItem(iterator.next(), "(sale) shinko pear", "6.44", null, 36);
        verifyParsedItem(iterator.next(), "(sale) seedless black grape", "4.67", null, 38);
        verifyParsedItem(iterator.next(), "bok-choy (short)", "2.31", null, 40);
        verifyParsedItem(iterator.next(), "shanghai bok choy", "3.84", null, 42);
        verifyParsedItem(iterator.next(), "crown broccoli", "3.82", null, 44);
        verifyParsedItem(iterator.next(), "water chestnut", "2.58", null, 46);
        verifyParsedItem(iterator.next(), "island farms 1%p.skim hilk", "4.95", null, 49);
        verifyParsedItem(iterator.next(), "veg. & meat buns delta", "4.57", null, 52);
        verifyParsedItem(iterator.next(), "<sale) t&t smooth tofu", "1.49", null, 53);
        verifyParsedItem(iterator.next(), "(sale) golden city plain bun", "3.39", null, 54);
        verifyParsedItem(iterator.next(), "t&t shopping bag", "0.049", null, 56);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                                      $0.14",59);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/17",75);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "100.75",58);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 10010",99);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "100.89",61);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "cardholder a  cknowledges receipt",86);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "27",63);
    }

    @Test
    public void receipt_2015_02_01_14_15_22()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_01_14_15_22, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(20,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "grand maple cooking wine 17.5%", "1.89", null, 7);
        verifyParsedItem(iterator.next(), "beef short rib plate boneless", "9.4", null, 9);
        verifyParsedItem(iterator.next(), "lamb shoulder slice- thin", "9.6", null, 11);
        verifyParsedItem(iterator.next(), "forzen pork jowl- polished", "8.43", null, 13);
        verifyParsedItem(iterator.next(), "cheese flavoured fish cake", "5.81", null, 16);
        verifyParsedItem(iterator.next(), "(sale) chestnuts", "3.51", null, 19);
        verifyParsedItem(iterator.next(), "cilanntro", "1.58", null, 21);
        verifyParsedItem(iterator.next(), "(sale) chinese white radish", "1.46", null, 23);
        verifyParsedItem(iterator.next(), "(sale) enoki mushroom", "3.87", null, 25);
        verifyParsedItem(iterator.next(), "long napa(sui choy)", "2.85", null, 27);
        verifyParsedItem(iterator.next(), "(sale) green onion", "0.79", null, 29);
        verifyParsedItem(iterator.next(), "watercress", "1.99", null, 30);
        verifyParsedItem(iterator.next(), "(sale) seedless black grape", "5.63", null, 31);
        verifyParsedItem(iterator.next(), "bean sprout", "6.42", null, 33);
        verifyParsedItem(iterator.next(), "bean sprout", "1.33", null, 35);
        verifyParsedItem(iterator.next(), "(sale) nagaimo", "4.38", null, 37);
        verifyParsedItem(iterator.next(), "delta rice noodle", "2.69", null, 40);
        verifyParsedItem(iterator.next(), "(sale) honaji deepfry tofu rhs", "1.99", null, 41);
        verifyParsedItem(iterator.next(), "chi mei red bean jam", "5.67", null, 42);
        verifyParsedItem(iterator.next(), "t&t shopping bag", "0.049", null, 44);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/12/25",51);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "29.48",48);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "79.48",46);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "23",50);

    }

    @Test
    public void receipt_2015_02_01_14_17_10()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_01_14_17_10, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(21,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "lkk premium soy sauce", "7.79", null, 7);
        verifyParsedItem(iterator.next(), "large bun-red bag", "1.399", null, 9);
        verifyParsedItem(iterator.next(), "(sale) ground pork-lean", "7.70", null, 11);
        verifyParsedItem(iterator.next(), "(sale) fz king fish steak", "6.60", null, 14);
        verifyParsedItem(iterator.next(), "(sale) carrot", "0.85", null, 17);
        verifyParsedItem(iterator.next(), "garlic", "0.99", null, 19);
        verifyParsedItem(iterator.next(), "(sale) mo qua", "1.57", null, 20);
        verifyParsedItem(iterator.next(), "napa (short)", "2.77", null, 22);
        verifyParsedItem(iterator.next(), "(sale) tomatoes", "2.06", null, 24);
        verifyParsedItem(iterator.next(), "(sale) you -choy", "2.39", null, 26);
        verifyParsedItem(iterator.next(), "(sale) banana", "2.71", null, 28);
        verifyParsedItem(iterator.next(), "(sale) banana", "1.99", null, 30);
        verifyParsedItem(iterator.next(), "(sale) ya-li pear", "4.54", null, 32);
        verifyParsedItem(iterator.next(), "(sale) pacific rose apple", "4.58", null, 34);
        verifyParsedItem(iterator.next(), "(sale) shanghai bok choy", "1.56", null, 36);
        verifyParsedItem(iterator.next(), "(sale) crown broccoli", "3.07", null, 38);
        verifyParsedItem(iterator.next(), "delta plain rice rolls", "2.69", null, 41);
        verifyParsedItem(iterator.next(), "veg. & meat buns delta", "4.57", null, 42);
        verifyParsedItem(iterator.next(), "watson frozen sweet peas", "1.59", null, 43);
        verifyParsedItem(iterator.next(), "(sale) honaji dried special bean curd", "2.09", null, 44);
        verifyParsedItem(iterator.next(), "(sale) shikoku sanuki japanese fz udo", "3.99", null, 45);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                                      $0.07",48);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/10",63);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 10010",86);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "67.49",47);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "carohqlder acknowledges receipt",73);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "67.56",49);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "21",51);

    }

    @Test
    public void receipt_2015_02_09_11_36_14()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_11_36_14, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "dasani water", "1.599", null, 8);
        verifyParsedItem(iterator.next(), "3 items combo meal", "7.999", null, 12);
        verifyParsedItem(iterator.next(), "steam bun-2pc", "3.499", null, 13);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                      $0.65",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/8",6);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "13.19",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "13.84",16);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "cardholder acknowledges receipt",40);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "3",18);
    }

    @Test
    public void receipt_2015_02_09_13_15_02()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_13_15_02, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(26,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "soo soft shredded dried beef", "3.95", null, 6);
        verifyParsedItem(iterator.next(), "tf kong moon rice stick", "1.27", null, 7);
        verifyParsedItem(iterator.next(), "large bun-red bag", "1.399", null, 10);
        verifyParsedItem(iterator.next(), "one day old bread-packing", "2.69", null, 11);
        verifyParsedItem(iterator.next(), "ground pork-lean", "4.62", null, 13);
        verifyParsedItem(iterator.next(), "(sale) pork shoulder butt (boneless)", "6.67", null, 15);
        verifyParsedItem(iterator.next(), "(sale) pork side ribs(whole)", "10.51", null, 17);
        verifyParsedItem(iterator.next(), "(sale) pork side ribs (whole)", "12.46", null, 19);
        verifyParsedItem(iterator.next(), "(sale) carrot", "0.80", null, 22);
        verifyParsedItem(iterator.next(), "celery", "1.85", null, 24);
        verifyParsedItem(iterator.next(), "garlic", "0.99", null, 26);
        verifyParsedItem(iterator.next(), "(sale) napa (short)", "2.83", null, 27);
        verifyParsedItem(iterator.next(), "green papaya", "4.57", null, 29);
        verifyParsedItem(iterator.next(), "tomatoes", "3.61", null, 31);
        verifyParsedItem(iterator.next(), "(sale) don gua -china", "1.44", null, 33);
        verifyParsedItem(iterator.next(), "(sale) you-choy", "3.76", null, 35);
        verifyParsedItem(iterator.next(), "(sale) fuji apple", "4.17", null, 37);
        verifyParsedItem(iterator.next(), "(sale) banana", "2.27", null, 39);
        verifyParsedItem(iterator.next(), "(sale) seedless black grape", "4.57", null, 41);
        verifyParsedItem(iterator.next(), "(sale) kiwi", "3.56", null, 43);
        verifyParsedItem(iterator.next(), "(sale) ya-li pear", "6.32", null, 45);
        verifyParsedItem(iterator.next(), "(sale) shanghai bok choy", "1.44", null, 47);
        verifyParsedItem(iterator.next(), "(sale) crown broccoli", "1.44", null, 49);
        verifyParsedItem(iterator.next(), "(sale) delta plain rice rolls", "2.69", null, 52);
        verifyParsedItem(iterator.next(), "(sale) f.a cabbage &pork dumpling", "8.99", null, 53);
        verifyParsedItem(iterator.next(), "(sale) shendan cooked salted duck egg", "2.99", null, 55);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                                 $0.07",57);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/12/13",72);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "103.64",56);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 10010",95);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "103.71",58);//TODO too big difference from subtotal
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "car dhdlden acknow  ledges receipt",83);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "32",60);

    }

    @Test
    public void receipt_2015_02_09_13_15_52()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_13_15_52, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(25,receipt.getItems().size());
        assertEquals(25,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "topchoice hawthorn jelly candy", "3.439", null, 6);
        verifyParsedItem(iterator.next(), "yulei preserved olive uegetabl", "3.53", null, 7);
        verifyParsedItem(iterator.next(), "jia fu li family egg noodle", "3.09", null, 8);
        //missing: (SALE) MILK BREAD
//        2 @2/$3. 68                                            $3.68
        verifyParsedItem(iterator.next(), "pork sirloin", "5.70", null, 13);
        verifyParsedItem(iterator.next(), "ground pork-lean", "4.36", null, 15);
        verifyParsedItem(iterator.next(), "live tilapia", "9.06", null, 18);
        verifyParsedItem(iterator.next(), "fz walleye fish steak", "7.60", null, 20);
        verifyParsedItem(iterator.next(), "(sale) carrot", "0.88", null, 23);
        verifyParsedItem(iterator.next(), "(sale) ginger", "1.63", null, 25);
        verifyParsedItem(iterator.next(), "(sale) lotus root", "4.11", null, 27);
        verifyParsedItem(iterator.next(), "(sale) tomatoes", "2.83", null, 29);
        verifyParsedItem(iterator.next(), "(sale) don gua -china", "1.59", null, 31);
        verifyParsedItem(iterator.next(), "(sale) you-choy", "2.03", null, 33);
        verifyParsedItem(iterator.next(), "chinese golden pear", "7.82", null, 35);
        verifyParsedItem(iterator.next(), "(sale) banana", "3.23", null, 37);
        verifyParsedItem(iterator.next(), "(sale) seedless black grape", "3.99", null, 39);
        verifyParsedItem(iterator.next(), "bok-choy (short)", "3.02", null, 41);
        verifyParsedItem(iterator.next(), "(sale) shanghai bok choy", "1.94", null, 43);
        verifyParsedItem(iterator.next(), "crown broccoli", "2.73", null, 45);
        verifyParsedItem(iterator.next(), "(sale) korean king oyster mushroom", "1.99", null, 47);
        verifyParsedItem(iterator.next(), "(sale) fuji apple(china)", "4.17", null, 48);
        verifyParsedItem(iterator.next(), "(sale) sunrise traditional fresh tofu", "1.89", null, 51);
        verifyParsedItem(iterator.next(), "veg. & meat buns delta", "4.57", null, 52);
        verifyParsedItem(iterator.next(), "(sale) golden city plain bun", "3.39", null, 53);
        verifyParsedItem(iterator.next(), "t&t shopping bag", "0.169", null, 55);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                                      $0.18",60);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "92.60",62);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "92.42",59);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/11/29",77);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "10",64);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "cardholder acknowledges receipt",89);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 10010",105);


    }

    @Test
    public void receipt_2015_02_09_13_16_16()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_13_16_16, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(21,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "lkk premium soy sauce", "7.79", null, 6);
        verifyParsedItem(iterator.next(), "large bun-red bag", "1.399", null, 8);
        verifyParsedItem(iterator.next(), "(sale) ground pork-lean", "7.70", null, 10);
        verifyParsedItem(iterator.next(), "(sale) fz king fish steak", "6.60", null, 13);
        verifyParsedItem(iterator.next(), "!sale) carrot", "0.85", null, 16);
        verifyParsedItem(iterator.next(), "garlic", "0.99", null, 18);
        verifyParsedItem(iterator.next(), "(sale) mo qua", "1.57", null, 19);
        verifyParsedItem(iterator.next(), "napa (short)", "2.77", null, 21);
        verifyParsedItem(iterator.next(), "(sale) tomatoes", "2.06", null, 23);
        verifyParsedItem(iterator.next(), "(sale) you-choy", "2.39", null, 25);
        verifyParsedItem(iterator.next(), "(sale) banana", "2.71", null, 27);
        verifyParsedItem(iterator.next(), "(sale) banana", "1.99", null, 29);
        verifyParsedItem(iterator.next(), "(sale) ya-li pear", "4.54", null, 31);
        verifyParsedItem(iterator.next(), "(sale) pacific rose apple", "4.58", null, 33);
        verifyParsedItem(iterator.next(), "(sale) shanghai bok choy", "1.56", null, 35);
        verifyParsedItem(iterator.next(), "(sale) crown broccoli", "3.07", null, 37);
        verifyParsedItem(iterator.next(), "delta plain rice rolls", "2.69", null, 40);
        verifyParsedItem(iterator.next(), "veg. &meat buns delta", "4.57", null, 41);
        verifyParsedItem(iterator.next(), "watson frozen sweet peas", "1.59", null, 42);
        verifyParsedItem(iterator.next(), "(sale) honaji dried special bean curd", "2.09", null, 43);
        verifyParsedItem(iterator.next(), "!sale) shikoku sanuki japanese fz udo", "3.99", null, 44);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 10010",86);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/10",62);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "67.49",46);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "67.56",48);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "cardhdlder acknowledges receipt",72);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                                $0.07",47);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "21",50);


    }

    @Test
    public void receipt_2015_02_09_13_16_46()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_13_16_46, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(24,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "large bun-red bag",  "2.789", null, 6);
        verifyParsedItem(iterator.next(), "one day old bread-packing",  "2.69", null, 8);
        verifyParsedItem(iterator.next(), "pork leg boneless",  "9.49", null, 10);
        verifyParsedItem(iterator.next(), "(sale) pork side ribs (whole)",  "11.18", null, 12);
        verifyParsedItem(iterator.next(), "(sale) chicken legs (back attached)",  "8.74", null, 14);
        verifyParsedItem(iterator.next(), "frozen rock fish fillet",  "7.61", null, 17);
        verifyParsedItem(iterator.next(), "(sale) carrot",  "0.78", null, 20);
        verifyParsedItem(iterator.next(), "cilanntro    $0.79   $    (sale) garlic sprout (leek bud)",  "1.98", null, 22);
        verifyParsedItem(iterator.next(), "napa(short)",  "2.67", null, 24);
        verifyParsedItem(iterator.next(), "(sale) tomatoes",  "3.69", null, 26);
        verifyParsedItem(iterator.next(), "(sale) don gua -china",  "1.92", null, 28);
        verifyParsedItem(iterator.next(), "(sale) you-choy",  "4.06", null, 30);
        verifyParsedItem(iterator.next(), "(sale) banana",  "3.95", null, 32);
        verifyParsedItem(iterator.next(), "(sale) shinko pear",  "6.44", null, 34);
        verifyParsedItem(iterator.next(), "(sale) seedless black grape",  "4.67", null, 36);
        verifyParsedItem(iterator.next(), "bok-choy (short)",  "2.31", null, 38);
        verifyParsedItem(iterator.next(), "shanghai bok choy",  "3.84", null, 40);
        verifyParsedItem(iterator.next(), "crown broccoli",  "3.82", null, 42);
        verifyParsedItem(iterator.next(), "water chestnut",  "2.58", null, 44);
        verifyParsedItem(iterator.next(), "island farms 1%p.skim milk",  "4.95", null, 47);
        verifyParsedItem(iterator.next(), "veg. &meat buns delta",  "4.57", null, 50);
        verifyParsedItem(iterator.next(), "(sale) t&t smooth tofu",  "1.49", null, 51);
        verifyParsedItem(iterator.next(), "(sale) golden city plain bun",  "3.39", null, 52);
        verifyParsedItem(iterator.next(), "t&tshopping_bag    $0.04 gp gp", null, null, 54);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/17",73);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                   $0.14 $",57);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "100.75",56);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "100.0",59);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "cardholder a c knoi~ledge s receip i",84);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 10010",96);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "27",61);

    }

    @Test
    public void receipt_2015_02_09_13_21_50()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_13_21_50, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(20,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "grand maple cooking wine 17.5%",  "1.0", null, 6);
        verifyParsedItem(iterator.next(), "beef short rib plate boneless",  "9.0", null, 8);
        verifyParsedItem(iterator.next(), "lamb shoulder slice- thin",  "9.0", null, 10);
        verifyParsedItem(iterator.next(), "forzen pork jowl- polished",  "8.43", null, 12);
        verifyParsedItem(iterator.next(), "cheese flavoured fish cake",  "5.81", null, 15);
        verifyParsedItem(iterator.next(), "(sale) chestnuts",  "3.51", null, 18);
        verifyParsedItem(iterator.next(), "cilanntro",  "1.58", null, 20);
        verifyParsedItem(iterator.next(), "(sale) chinese white radish",  "1.46", null, 22);
        verifyParsedItem(iterator.next(), "(sale) enoki mushroom",  "3.87", null, 24);
        verifyParsedItem(iterator.next(), "long napa (sui choy)",  "2.85", null, 26);
        verifyParsedItem(iterator.next(), "(sale) green onion",  "0.79", null, 28);
        verifyParsedItem(iterator.next(), "watercress",  "1.99", null, 29);
        verifyParsedItem(iterator.next(), "(sale) seedless black grape",  "5.63", null, 30);
        verifyParsedItem(iterator.next(), "bean sprout",  "6.42", null, 32);
        verifyParsedItem(iterator.next(), "bean sprout",  "1.33", null, 34);
        verifyParsedItem(iterator.next(), "(sale) nagaimo",  "4.38", null, 36);
        verifyParsedItem(iterator.next(), "delta rice noodle",  "2.69", null, 39);
        verifyParsedItem(iterator.next(), "(sale) honaji deepfrv tofu rhs",  "199", null, 40);
        verifyParsedItem(iterator.next(), "chi mei red bean jam",  "5.67", null, 41);
        verifyParsedItem(iterator.next(), "t&t shopping bag",  "0.049", null, 43);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/12/25",50);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "29.48",47);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "79.48",45);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "23",49);

    }

    @Test
    public void receipt_2015_02_09_13_23_52()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_13_23_52, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(31,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "dh dongguan ice stick",  "1.17", null, 6);
        verifyParsedItem(iterator.next(), "jia fu li abalotie flavor noodl",  "3.09", null, 7);
        verifyParsedItem(iterator.next(), "dim sum $3.50",  "7.009", null, 9);
        verifyParsedItem(iterator.next(), "(sale) pork shoulder butt (boneless)",  "6.66", null, 12);
        verifyParsedItem(iterator.next(), "(sale) pork side ribs (whole)",  "8.94", null, 14);
        verifyParsedItem(iterator.next(), "(sale) pork side ribs (whole)",  "10.15", null, 16);
        verifyParsedItem(iterator.next(), "(sale) pork side ribs (whole)",  "9.54", null, 18);
        verifyParsedItem(iterator.next(), "(sale) pork side ribs (whole)",  "10.43", null, 20);
        verifyParsedItem(iterator.next(), "pork neckbone",  "2.35", null, 22);
        verifyParsedItem(iterator.next(), "chicken legs (back attached)",  "9.53", null, 24);
        verifyParsedItem(iterator.next(), "rock fish head",  "2.93", null, 27);
        verifyParsedItem(iterator.next(), "grand maple fz basa fillet",  "4.98", null, 29);
        verifyParsedItem(iterator.next(), "(sale) napa(short)",  "2.75", null, 32);
        verifyParsedItem(iterator.next(), "(sale) l.tomatoes",  "1.11", null, 34);
        verifyParsedItem(iterator.next(), "don gua -china",  "5.10", null, 36);
        verifyParsedItem(iterator.next(), "(sale) you -choy",  "2.53", null, 38);
        verifyParsedItem(iterator.next(), "arrow root",  "2.71", null, 40);
        verifyParsedItem(iterator.next(), "banana squash",  "2.33", null, 42);
        verifyParsedItem(iterator.next(), "(sale) red delicious apple",  "5.47", null, 44);
        verifyParsedItem(iterator.next(), "banana",  "1.32", null, 46);
        verifyParsedItem(iterator.next(), "banana",  "1.96", null, 48);
        verifyParsedItem(iterator.next(), "(sale) seedless black grape",  "4.04", null, 50);
        verifyParsedItem(iterator.next(), "(sale) navel oranges",  "4.87", null, 52);
        verifyParsedItem(iterator.next(), "(sale) shanghai bok chov",  "2.23", null, 54);
        verifyParsedItem(iterator.next(), "(sale) crown broccoli",  "1.99", null, 56);
        verifyParsedItem(iterator.next(), "bean sprout",  "1.35", null, 58);
        verifyParsedItem(iterator.next(), "(sale) strawberry",  "2.99", null, 60);
        verifyParsedItem(iterator.next(), "delta rice noodle",  "2.69", null, 62);
        verifyParsedItem(iterator.next(), "veg. &meat buns delta",  "4.57", null, 63);
        verifyParsedItem(iterator.next(), "(sale) gol. gen citv plain bun",  "3.39", null, 64);
        verifyParsedItem(iterator.next(), "t&t shopping bag",  "0.049", null, 66);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/17",84);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                                 $0.35",68);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "130.21",67);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "130.56",70);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "32",72);

    }

    @Test
    public void receipt_2015_02_09_13_33_34()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_13_33_34, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(22,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "(sale) sun hing luncheon meat",  "2.89", null, 6);
        verifyParsedItem(iterator.next(), "eravan tapioca flour",  "2.18", null, 7);
        verifyParsedItem(iterator.next(), "(sale) pork sirloin",  "7.05", null, 10);
        verifyParsedItem(iterator.next(), "(sale) ground pork (regular)",  "3.01", null, 12);
        verifyParsedItem(iterator.next(), "chicken legs (back attached)",  "6.07", null, 14);
        verifyParsedItem(iterator.next(), "(sale) frozen rock fish fillet",  "6.53", null, 17);
        verifyParsedItem(iterator.next(), "(sale) ginger",  "2.37", null, 20);
        verifyParsedItem(iterator.next(), "jicama",  "2.69", null, 22);
        verifyParsedItem(iterator.next(), "tomatoes",  "4.27", null, 24);
        verifyParsedItem(iterator.next(), "(sale) don gua -china",  "2.95", null, 26);
        verifyParsedItem(iterator.next(), "(sale)you-choy sum",  "2.28", null, 28);
        verifyParsedItem(iterator.next(), "(sale)arrow root",  "3.10", null, 30);
        verifyParsedItem(iterator.next(), "(sale) fuji apple",  "3.77", null, 32);
        verifyParsedItem(iterator.next(), "(sale) banana",  "3.86", null, 34);
        verifyParsedItem(iterator.next(), "(sale) ya- li pear",  "3.70", null, 36);
        verifyParsedItem(iterator.next(), "(sale) bok-choy (short)",  "2.24", null, 38);
        verifyParsedItem(iterator.next(), "(sale) shanghai bok choy",  "1.57", null, 40);
        verifyParsedItem(iterator.next(), "(sale) crown broccoli",  "2.70", null, 42);
        verifyParsedItem(iterator.next(), "bean sprout",  "1.88", null, 44);
        verifyParsedItem(iterator.next(), "(sale) delta rice noodle",  "2.69", null, 47);
        verifyParsedItem(iterator.next(), "veg. & meat buns delta",  "4.57", null, 48);
        verifyParsedItem(iterator.next(), "(sale) honaji dried beancu",  "1.99", null, 49);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/3",66);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "73.81",52);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "cardholder acktlouledges receipt",77);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store : 10010",90);

    }

    @Test
    public void receipt_2015_02_09_13_37_31()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_13_37_31, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(24,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "(sale9 kingo dried beancurd",  "1.49", null, 6);
        verifyParsedItem(iterator.next(), "sau tao n/fried buckwheat ndle",  "4.27", null, 7);
        verifyParsedItem(iterator.next(), "milk bread",  "2.50", null, 10);
        verifyParsedItem(iterator.next(), "large bun-red bag",  "2.789", null, 11);
        verifyParsedItem(iterator.next(), "ground pork-lean",  "5.11", null, 14);
        verifyParsedItem(iterator.next(), "pork shoulder butt (boneless)",  "11.03", null, 16);
        verifyParsedItem(iterator.next(), "chicken legs (back attached)",  "6.57", null, 18);
        verifyParsedItem(iterator.next(), "(sale) carrot",  "0.84", null, 23);
        verifyParsedItem(iterator.next(), "(sale) lotus root",  "1.83", null, 25);
        verifyParsedItem(iterator.next(), "potatoes",  "0.95", null, 27);
        verifyParsedItem(iterator.next(), "(sale) italian squash (zucchinis)",  "3.56", null, 29);
        verifyParsedItem(iterator.next(), "(sale) roma tomatoes",  "2.42", null, 31);
        verifyParsedItem(iterator.next(), "(sale) don gua -china",  "2.20", null, 33);
        verifyParsedItem(iterator.next(), "(sale) you-choy",  "1.82", null, 35);
        verifyParsedItem(iterator.next(), "(sale) banana",  "2.91", null, 37);
        verifyParsedItem(iterator.next(), "(sale) d*anjou pear",  "3.63", null, 39);
        verifyParsedItem(iterator.next(), "(sale) bok-choy (short)",  "2.31", null, 41);
        verifyParsedItem(iterator.next(), "(sale) crown broccoli",  "1.56", null, 43);
        verifyParsedItem(iterator.next(), "(sale) fuji apple (china)",  "2.19", null, 45);
        verifyParsedItem(iterator.next(), "island farms skim milk",  "4.95", null, 48);
        verifyParsedItem(iterator.next(), "isalej sunrise traditional fresh tofu",  "1.59", null, 51);
        verifyParsedItem(iterator.next(), "veg. & meat buns delta",  "4.57", null, 52);
        verifyParsedItem(iterator.next(), "(sale) taro mini steamed bun jewel",  "3.99", null, 53);
        verifyParsedItem(iterator.next(), "t&t shopping bag",  "0.209", null, 55);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/12/28",66);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "60.54",63);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "80.54",59);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "31",65);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                            $0.15",58);

    }

    @Test
    public void receipt_2015_02_19_22_58_18()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_19_22_58_18, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(18,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "pork shoulder butt shabu shabu",  "6.27", null, 8);
        verifyParsedItem(iterator.next(), "lamb shoulder slice- thin",  "9.73", null, 10);
        verifyParsedItem(iterator.next(), "beef blade chuck roll shabu sh",  "8.45", null, 12);
        verifyParsedItem(iterator.next(), "(sale) fish tofu",  "5.01", null, 15);
        verifyParsedItem(iterator.next(), "hot pot fish ball-assort",  "6.95", null, 17);
        verifyParsedItem(iterator.next(), "korean enoki mushroom",  "1.99", null, 20);
        verifyParsedItem(iterator.next(), "(sale) chinese white radish",  "1.22", null, 21);
        verifyParsedItem(iterator.next(), "chun ho",  "4.66", null, 23);
        verifyParsedItem(iterator.next(), "(sale) chinese lettuce",  "2.09", null, 25);
        verifyParsedItem(iterator.next(), "(sale) n    apa(shortl",  "2.30", null, 27);
        verifyParsedItem(iterator.next(), "(sale ) watercress",  "1.69", null, 29);
        verifyParsedItem(iterator.next(), "korean melon",  "5.06", null, 30);
        verifyParsedItem(iterator.next(), "(sale) korean fu yu persimmon",  "4.69", null, 32);
        verifyParsedItem(iterator.next(), "(salel nagaimo",  "5.25", null, 34);
        verifyParsedItem(iterator.next(), "(sale ) organic oyster mus    hroom",  "3.69", null, 36);
        verifyParsedItem(iterator.next(), "white beech mushroom",  "2.49", null, 37);
        verifyParsedItem(iterator.next(), "(sale ) samlip frozen noodle",  "5.99", null, 39);
        verifyParsedItem(iterator.next(), "t&t shopping bag",  "0.129", null, 42);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/19",61);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "77.65",44);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store : 10010                                   .",83);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "77.66",47);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "cardholder acknowledges receipt",71);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "21",49);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                    $0.01",45);

    }

    @Test
    public void receipt_2015_02_27_21_51_03()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_27_21_51_03, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(18,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "pork shoulder butt(shabu shabu",  "6.27", null, 8);
        verifyParsedItem(iterator.next(), "lamb shoulder slice- thin",  "9.73", null, 10);
        verifyParsedItem(iterator.next(), "beef blade chuck roll(shabu sh",  "8.45", null, 12);
        verifyParsedItem(iterator.next(), "(sale) fish tofu",  "5.01", null, 15);
        verifyParsedItem(iterator.next(), "hot pot fish ball-assort",  "6.95", null, 17);
        verifyParsedItem(iterator.next(), "korean enoki mushroom",  "1.99", null, 20);
        verifyParsedItem(iterator.next(), "(salej chinese white radish",  "1.22", null, 21);
        verifyParsedItem(iterator.next(), "chun ho",  "4.66", null, 23);
        verifyParsedItem(iterator.next(), "isalei chinese lettuce",  "2.09", null, 25);
        verifyParsedItem(iterator.next(), "(salej napa (short)",  "2.30", null, 27);
        verifyParsedItem(iterator.next(), "(salei w atercres    s",  "1.69", null, 29);
        verifyParsedItem(iterator.next(), "korean melon",  "5.06", null, 30);
        verifyParsedItem(iterator.next(), "(sale) korean fuyu persimmon",  "4.69", null, 32);
        verifyParsedItem(iterator.next(), "isalej nagaimo",  "5.25", null, 34);
        verifyParsedItem(iterator.next(), "isalej organic oyster mus hroom",  "3.69", null, 36);
        verifyParsedItem(iterator.next(), "white beech mushroom",  "2.49", null, 37);
        verifyParsedItem(iterator.next(), "is alej samlip frozen noodle",  "5.99", null, 39);
        verifyParsedItem(iterator.next(), "t&t shopping bag",  "0.129", null, 42);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/19",61);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "77.65",44);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 10010                              .",89);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "77.66",47);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "cardholder acknowledges receipt",71);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "21",49);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                   $0.01",45);

    }

    @Test
    public void receipt_2015_04_04_21_25_38()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_25_38, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(11,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "sweet egg bread.",  "5.00", null, 11);
        verifyParsedItem(iterator.next(), "rice snack",  "8.989", null, 13);
        verifyParsedItem(iterator.next(), "ginger",  "0.15", null, 16);
        verifyParsedItem(iterator.next(), "garlic",  "0.99", null, 18);
        verifyParsedItem(iterator.next(), "(sale) hot house tomatoes (l)",  "3.23", null, 19);
        verifyParsedItem(iterator.next(), "(sale) satsuma",  "2.02", null, 21);
        verifyParsedItem(iterator.next(), "(sale) crown broccoli",  "1.34", null, 23);
        verifyParsedItem(iterator.next(), "bean sprout",  "1.35", null, 25);
        verifyParsedItem(iterator.next(), "chi mei sesame bun",  "5.67", null, 28);
        verifyParsedItem(iterator.next(), "chi mei taro bun",  "5.67", null, 29);
        verifyParsedItem(iterator.next(), "t&t shopping bag",  "0.049", null, 31);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/7/11",8);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "34.44",32);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "34.89",34);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "cardholder a  cknoiile[iges receipt",58);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "13",36);

    }

    @Test
    public void receipt_2015_04_04_21_28_08()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_28_08, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(18,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "evian natural spring water",  "1.79", null, 9);
        verifyParsedItem(iterator.next(), "dasani water",  "1.59", null, 12);
        verifyParsedItem(iterator.next(), "aquafina 1litre",  "1.67", null, 15);
        verifyParsedItem(iterator.next(), "six-f sweet bean paste",  "3.95", null, 18);
        verifyParsedItem(iterator.next(), "(sale) tk cubic pastry new year box",  "37.76", null, 19);
        verifyParsedItem(iterator.next(), "greatwall beans starch sheet",  "4.14", null, 21);
        verifyParsedItem(iterator.next(), "one tang noodles - henan slyle",  "9.18", null, 23);
        verifyParsedItem(iterator.next(), "fz basa fillet chemical free",  "3.30", null, 26);
        verifyParsedItem(iterator.next(), "fz basa fillet chemical free",  "3.21", null, 28);
        verifyParsedItem(iterator.next(), "wild hairtail sabre segment",  "17.98", null, 30);
        verifyParsedItem(iterator.next(), "(sale) rose brand fz. bay scallops",  "11.98", null, 32);
        verifyParsedItem(iterator.next(), "cilanntro",  "1.58", null, 35);
        verifyParsedItem(iterator.next(), "(sale) chinese white radish",  "1.39", null, 37);
        verifyParsedItem(iterator.next(), "(sale) ginger",  "2.60", null, 39);
        verifyParsedItem(iterator.next(), "(sale) garlic sprout(leek bud)",  "3.87", null, 41);
        verifyParsedItem(iterator.next(), "ataulfo mangoes",  "3.96", null, 43);
        verifyParsedItem(iterator.next(), "bean sprout",  "1.36", null, 45);
        verifyParsedItem(iterator.next(), "king oyster mushroom",  "2.76", null, 47);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/7",67);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "114.43",49);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 10010",93);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "114.60",51);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "cardholder acknowled  ges receipt",77);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "6",53);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                               $0.17",50);

    }

    @Test
    public void receipt_2015_04_04_21_31_54()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_31_54, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "dinner at t&t combo meal-",  "13.999", null, 9);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/1",29);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "13.99",10);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                                    $0.70",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "14.69",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "cardholder a   cknowledges receipt",38);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "1",14);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store : 100 10",52);
        verifyParsedField(fieldValues, ReceiptFieldType.Time, "time: 6:13:05 pm                                     lenie",16);

    }

    @Test
    public void receipt_2015_04_04_21_32_09()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_32_09, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(14,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "bf picked cabbage fish",  "2.35", null, 10);
        verifyParsedItem(iterator.next(), "natural world cunin powder",  "1.33", null, 11);
        verifyParsedItem(iterator.next(), "korean large pop corn",  "4.999", null, 13);
        verifyParsedItem(iterator.next(), "happy pig custard bun - cold",  "6.88", null, 14);
        verifyParsedItem(iterator.next(), "t&t pineapple coconut bread",  "3.60", null, 15);
        verifyParsedItem(iterator.next(), "chocolate steaned bun 6pc cold",  "4.99", null, 16);
        verifyParsedItem(iterator.next(), "(sale)cauliflower",  "3.14", null, 18);
        verifyParsedItem(iterator.next(), "kabocha",  "6.20", null, 20);
        verifyParsedItem(iterator.next(), "sweet potatoes (yam)",  "2.17", null, 22);
        verifyParsedItem(iterator.next(), "(sale) hot house tomatoes (l)",  "5.11", null, 24);
        verifyParsedItem(iterator.next(), "(sale) honey tangerine",  "6.76", null, 26);
        verifyParsedItem(iterator.next(), "flowering chive    bud",  "3.99", null, 28);
        verifyParsedItem(iterator.next(), "chi mei taru bun",  "5.67", null, 30);
        verifyParsedItem(iterator.next(), "kc linseed clay oven rolls",  "8.54", null, 31);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/9/7",52);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "67.72",33);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                      $0.25",34);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "67.97",37);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "15",39);
        verifyParsedField(fieldValues, ReceiptFieldType.Time, "time: 4:11:06 pm                        king",41);

    }

    @Test
    public void receipt_2015_04_04_21_33_48()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_33_48, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "dinner at t&t combo meal",  "19.999", null, 8);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/11/7",25);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "19.99",9);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                                 $1.00",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "20.99",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "cardholder acknowledges receipt",34);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "1",13);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store : 10010",49);

    }

    @Test
    public void receipt_2015_04_04_22_12_29()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_12_29, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(12,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "amoy zj spare ribs marinade",  "4.35", null, 8);
        verifyParsedItem(iterator.next(), "(sale) vinamit jackfruit chips",  "3.599", null, 9);
        verifyParsedItem(iterator.next(), "happy pig custard bun- cold",  "6.88", null, 11);
        verifyParsedItem(iterator.next(), "milk egg swirl roll (cold)",  "5.99", null, 12);
        verifyParsedItem(iterator.next(), "chocolate milk bread",  "2.30", null, 13);
        verifyParsedItem(iterator.next(), "cilanntro",  "0.79", null, 17);
        verifyParsedItem(iterator.next(), "(sale) green onion",  "0.33", null, 22);
        verifyParsedItem(iterator.next(), "(sale) chung-li pan fried grn onion c",  "1.70", null, 34);
        verifyParsedItem(iterator.next(), "(sale) chung-li pan fried red bean pa",  "4.29", null, 35);
        verifyParsedItem(iterator.next(), "(sale) heiwa hiyahsi wakame",  "3.99", null, 36);
        verifyParsedItem(iterator.next(), "dried pickled uegetable bun",  "3.19", null, 37);
        verifyParsedItem(iterator.next(), "vegetable mushroom bun",  "3.19", null, 38);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/1",55);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "59.51",39);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                                 $0.18",40);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "59.69",41);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "cardholder a  c knowledges receipt",64);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "21",43);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store : 100 10",77);

    }

    @Test
    public void receipt_2015_04_04_22_17_37()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_17_37, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(5,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "s&b wasabi paste in tube",  "2.35", null, 9);
        verifyParsedItem(iterator.next(), "kyd hua tiao chiew sted 9.5% a",  "2.87", null, 10);
        verifyParsedItem(iterator.next(), "garlic",  "1.98", null, 12);
        verifyParsedItem(iterator.next(), "burnbrae far extra large white",  "6.98", null, 15);
        verifyParsedItem(iterator.next(), "t&t shopping bag",  "0.089", null, 18);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/3/14",38);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "14.26",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "14.26",23);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "8",25);

    }

    @Test
    public void receipt_2015_05_02_21_48_23()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_05_02_21_48_23, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "t&t thai jasmine rice 18lb",  "20.33", null, 6);
        verifyParsedItem(iterator.next(), "(sale) grand maple seafood mix",  "3.88", null, 15);
        verifyParsedItem(iterator.next(), "(salel 1& t smooth tofu",  "1.69", null, 38);
        verifyParsedItem(iterator.next(), "(sale l superior sweetened soy drink",  "4.99", null, 39);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store : 10010",90);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/3/6",4);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "67.15",46);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "67.16",49);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "c ardh  oldera cknouledges receipt",78);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                                   $0.01",47);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "24",51);

    }

    @Test
    public void receipt_2015_05_02_21_48_33()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_05_02_21_48_33, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(6,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "3~l e j    lou sughr rile cake",  "4.71", null, 12);
        verifyParsedItem(iterator.next(), "(sale) garlic sprout(lee k bud)",  "1.28", null, 29);
        verifyParsedItem(iterator.next(), "green onion",  "0.99", null, 32);
        verifyParsedItem(iterator.next(), "korean sov bean sprout",  "1.69", null, 45);
        verifyParsedItem(iterator.next(), "island farms sk im milk",  "4.99", null, 47);
        verifyParsedItem(iterator.next(), "(sale) t&t traditiotlal tofu",  "1.89", null, 50);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store : 1oc",97);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/21",61);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "111.38",56);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "111.88",58);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                                                     $0.50",57);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "31",60);

    }

    @Test
    public void receipt_2015_05_02_22_18_32()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_05_02_22_18_32, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(7,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "(sale) shhlhosoymn    paste",  "3.38", null, 8);
        verifyParsedItem(iterator.next(), "shinho soybean paste",  "4.83", null, 9);
        verifyParsedItem(iterator.next(), "tasty bread",  "4.99", null, 11);
        verifyParsedItem(iterator.next(), "(sale ) green onion",  "0.79", null, 21);
        verifyParsedItem(iterator.next(), "gala apple bag",  "3.99", null, 26);
        verifyParsedItem(iterator.next(), "dsi tofu dessert",  "2.49", null, 28);
        verifyParsedItem(iterator.next(), "(sale) heiwa sushi ginger",  "3.79", null, 29);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 10010",71);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/3/19",5);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "47.77",33);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "47.82",35);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "c ardholder ac knou   le oges receipt",58);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                                                           $0.05",34);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "35",37);

    }

    @Test
    public void receipt_2015_05_02_22_18_50()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_05_02_22_18_50, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(9,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "mayushan pluh syrup",  "3.97", null, 6);
        verifyParsedItem(iterator.next(), "itden organic green tea",  "10.69", null, 7);
        verifyParsedItem(iterator.next(), "gp chin kiang vinegar",  "1.79", null, 8);
        verifyParsedItem(iterator.next(), "va sheng chrysanthemum",  "1.79", null, 9);
        verifyParsedItem(iterator.next(), "beioahuang organic crushed cor",  "1.79", null, 10);
        verifyParsedItem(iterator.next(), "gh wild hairtail segment",  "8.99", null, 17);
        verifyParsedItem(iterator.next(), "fz wild yellow croaker",  "3.99", null, 18);
        verifyParsedItem(iterator.next(), "<sale> fz squ id tentacle",  "2.99", null, 19);
        verifyParsedItem(iterator.next(), "ocean pearl salted seaweed tie",  "3.73", null, 34);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 10010",78);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/3/11",4);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "",39);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "72.69",40);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "cardholder acknowledg  es receipt",64);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "23",42);

    }

    @Test
    public void receipt_2015_06_04_21_15_50()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_06_04_21_15_50, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(33,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "(sale) sun hing luncheon meat",  "2.69", null, 21);
        verifyParsedItem(iterator.next(), "soo preserved black beans",  "11.10", null, 22);
        verifyParsedItem(iterator.next(), "jia fu li pork ribs noodle",  "3.53", null, 24);
        verifyParsedItem(iterator.next(), "(sale) milk bread",  "3.68", null, 26);
        verifyParsedItem(iterator.next(), "one day old bread-packing",  "2.69", null, 28);
        verifyParsedItem(iterator.next(), "ground pork (regular)",  "4.66", null, 30);
        verifyParsedItem(iterator.next(), "(sale) pork side ribs (whole)",  "12.94", null, 32);
        verifyParsedItem(iterator.next(), "(sale) pork side ribs (whole)",  "9.78", null, 34);
        verifyParsedItem(iterator.next(), "(sale) pork side ribs (whole)",  "14.92", null, 36);
        verifyParsedItem(iterator.next(), "fz walleye fish steak",  "9.57", null, 39);
        verifyParsedItem(iterator.next(), "cauliflower",  "4.26", null, 42);
        verifyParsedItem(iterator.next(), "(sale) korean enoki mushroom",  "1.98", null, 44);
        verifyParsedItem(iterator.next(), "cilanntro",  "0.79", null, 46);
        verifyParsedItem(iterator.next(), "celery",  "1.75", null, 47);
        verifyParsedItem(iterator.next(), "(sale) green onion",  "0.79", null, 49);
        verifyParsedItem(iterator.next(), "(sale) tomatoes",  "3.62", null, 50);
        verifyParsedItem(iterator.next(), "don gua -china",  "4.06", null, 52);
        verifyParsedItem(iterator.next(), "(sale) you-choy",  "1.73", null, 54);
        verifyParsedItem(iterator.next(), "(sale) fuji apple",  "5.65", null, 56);
        verifyParsedItem(iterator.next(), "chinese golden pear",  "5.87", null, 58);
        verifyParsedItem(iterator.next(), "(sale) banana",  "2.31", null, 60);
        verifyParsedItem(iterator.next(), "(sale) kiwi",  "2.10", null, 62);
        verifyParsedItem(iterator.next(), "(sale) navel oranges",  "4.99", null, 64);
        verifyParsedItem(iterator.next(), "(sale) shanghai bok choy",  "3.11", null, 66);
        verifyParsedItem(iterator.next(), "(sale) crown broccoli",  "2.66", null, 68);
        verifyParsedItem(iterator.next(), "(sale) korean king oyster mushroom",  "1.99", null, 70);
        verifyParsedItem(iterator.next(), "(sale) gray squash",  "2.27", null, 71);
        verifyParsedItem(iterator.next(), "long gobo",  "2.26", null, 73);
        verifyParsedItem(iterator.next(), "water chestnut",  "2.54", null, 75);
        verifyParsedItem(iterator.next(), "(sale) t&t smooth tofu",  "1.49", null, 78);
        verifyParsedItem(iterator.next(), "(sale) grand maple frz boiled soybean",  "3.69", null, 79);
        verifyParsedItem(iterator.next(), "gh egg custard steamed buns",  "3.37", null, 81);
        verifyParsedItem(iterator.next(), "t&t shopping bag",  "0.089", null, 83);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/5/17",100);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "139.12",85);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "139.12",86);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "c  ar dho  ldera cknowledges rec ~ ipt",111);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "43",88);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 'ip. lo",118);

    }

    @Test
    public void receipt_2015_04_04_21_54_42ThisIsRCSSReceiptSellsATAndTProduct()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_54_42, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(21,receipt.getItems().size());
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
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",54);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref d          auth #      resp 001",64);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store 01549              terti z0154914c",57);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/28",87);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "9711 23 ave nw",55);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "104.55",47);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",79);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approued",70);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "105.70",51);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "ecology fee                                               0.08",22);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*'**'*'***'**�*�***'**'''*'*\"*''***'*'",89);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-490-3918",3);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "1.15",49);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh, low on price",4);

    }

    @Test
    public void receipt_2014_12_06_23_33_53RCSSReceiptSellsTandTProduct()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2014_12_06_23_33_53, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcss", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(35,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "(2)05680028105 dn dnactiv stw/b    r", null, null, 8);
        verifyParsedItem(iterator.next(), "(1j 05680043077 danactive straw    r", null, null, 13);
        verifyParsedItem(iterator.next(), "h40003492:i3 deposit    r",  "0.80", null, 15);
        verifyParsedItem(iterator.next(), "3 ~ su9 lqt 4",  "14.97", null, 17);
        verifyParsedItem(iterator.next(), "no name all purp",  "6.39", "no name all purp_06038301376", 19);
        verifyParsedItem(iterator.next(), "t&t rice",  "4.88", null, 22);
        verifyParsedItem(iterator.next(), "heiwa rice",  "11.99", null, 25);
        verifyParsedItem(iterator.next(), "pc bm white eggs",  "3.48", "pc bm white eggs_06038374581", 26);
        verifyParsedItem(iterator.next(), "boston cake",  "12.99", null, 28);
        verifyParsedItem(iterator.next(), "salmon fillet",  "8.02", null, 30);
        verifyParsedItem(iterator.next(), "pco app gala 3lb    r",  "4.98", null, 32);
        verifyParsedItem(iterator.next(), "pco apple rd del    r",  "4.98", null, 33);
        verifyParsedItem(iterator.next(), "�l6    pear d'anjou",  "2.74", null, 34);
        verifyParsedItem(iterator.next(), "grp grn sdls",  "5.70", null, 36);
        verifyParsedItem(iterator.next(), "grape red sol cs",  "6.16", "grape red sol cs_4023", 38);
        verifyParsedItem(iterator.next(), "bean green",  "1.12", "bean green_4066", 40);
        verifyParsedItem(iterator.next(), "zucchini green",  "1.67", "zucchini green_4067", 42);
        verifyParsedItem(iterator.next(), "pco avocado",  "4.98", null, 44);
        verifyParsedItem(iterator.next(), "rooster garlic",  "2.98", "rooster garlic_06038388591", 45);
        verifyParsedItem(iterator.next(), "dragon, fruit",  "3.00", null, 46);
        verifyParsedItem(iterator.next(), "eggplant lng",  "4.13", "eggplant lng_3089", 49);
        verifyParsedItem(iterator.next(), "pomegranate",  "1.92", null, 51);
        verifyParsedItem(iterator.next(), "ginger root",  "0.50", "ginger root_4612", 53);
        verifyParsedItem(iterator.next(), "rasp 1/2 pint    r",  "2.98", null, 55);
        verifyParsedItem(iterator.next(), "wmelon mini sdls",  "2.96", "wmelon mini sdls_3421", 56);
        verifyParsedItem(iterator.next(), "blueberries",  "2.47", null, 57);
        verifyParsedItem(iterator.next(), "onion green",  "0.94", "onion green_4068", 58);
        verifyParsedItem(iterator.next(), "celery stalks",  "1.18", null, 60);
        verifyParsedItem(iterator.next(), "spinach bunched",  "1.47", "spinach bunched_4090", 62);
        verifyParsedItem(iterator.next(), "(3)1)3338365592 tomato grp org    r",  "11.07", null, 63);
        verifyParsedItem(iterator.next(), "(2) 06602200200 mandarin japan    r",  "15.96", null, 65);
        verifyParsedItem(iterator.next(), "fm toms red bag",  "2.29", "fm toms red bag_64664", 68);
        verifyParsedItem(iterator.next(), "banana organic",  "2.42", "banana organic_94011", 70);
        verifyParsedItem(iterator.next(), "corelle bowl    gr",  "7.98", null, 73);
        verifyParsedItem(iterator.next(), "systane ultra cl    gr",  "10.99", null, 76);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "rcss 1570 - 4821 calgary frail",2);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.95",83);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "",-1);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "blg on fresh. lou on prlce",4);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",2);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "165.43",82);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "166.38",85);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card#:      *hh***********",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",3);

    }






}
