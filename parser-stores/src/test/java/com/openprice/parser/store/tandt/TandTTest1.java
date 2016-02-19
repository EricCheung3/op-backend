package com.openprice.parser.store.tandt;

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
public class TandTTest1 extends AbstractReceiptParserIntegrationTest{

    @Inject
    private ChainRegistry chainRegistry;

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

    @Value("classpath:/testFiles/TAndT/branch_88_170st/2015_04_04_21_54_42.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_54_42;

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
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "100.89",80);
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
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "67.56",68);
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
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "13.84",34);
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
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "1037",77);//TODO too big difference from subtotal
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
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "92.60",82);
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
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "67.56",67);
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
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "100.89",78);
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
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "130.56",87);
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

    }

    @Test
    public void receipt_2015_04_04_21_54_42()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_54_42, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

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

    }

    @Test
    public void receipt_2014_12_06_23_33_53()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2014_12_06_23_33_53, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("tandt", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }






}
