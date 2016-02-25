package com.openprice.parser.store._99supermarket;

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
public class _99SupermarketTest1 extends AbstractReceiptParserIntegrationTest{
    @Value("classpath:/testFiles/_99supermarket/branch_107_99 St/2015_06_04_21_18_17.jpg.henryHuang.txt")
    private Resource receipt_2015_06_04_21_18_17;

    @Value("classpath:/testFiles/_99supermarket/branch_107_99 St/2015_07_03_14_25_18.jpg.hengshuai.txt")
    private Resource receipt_2015_07_03_14_25_18;

    @Value("classpath:/testFiles/_99supermarket/branch_107_99 St/2015_07_03_14_28_31.jpg.hengshuai.txt")
    private Resource receipt_2015_07_03_14_28_31;

    @Value("classpath:/testFiles/_99supermarket/branch_107_99 St/2015_07_03_15_22_13.jpg.hengshuai.txt")
    private Resource receipt_2015_07_03_15_22_13;

    @Test
    public void receipt_2015_06_04_21_18_17()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_06_04_21_18_17, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("_99supermarket", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "sf bm chinese chopstick (1opr)",  "1.191", null, 8);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "25.35",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/5/23",7);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "24.14",10);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst#867832214",6);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "2",15);
    }

    @Test
    public void receipt_2015_07_03_14_25_18()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_03_14_25_18, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("_99supermarket", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(26,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "apple- fuji-china",  "6.88", null, 11);
        verifyParsedItem(iterator.next(), "chen black seasame 170g",  "1.99", null, 13);
        verifyParsedItem(iterator.next(), "chen sesame paste original 13oz",  "4.59", null, 14);
        verifyParsedItem(iterator.next(), "chives chinese (he la)(label)",  "3.02", null, 15);
        verifyParsedItem(iterator.next(), "del honey citron tea 1l",  "5.99", null, 16);
        verifyParsedItem(iterator.next(), "dmdg japanese dried noodle300g",  "1.89", null, 18);
        verifyParsedItem(iterator.next(), "fresh commo    nyam rhizome#237",  "2.53", null, 19);
        verifyParsedItem(iterator.next(), "gai lan (label)",  "2.71", null, 21);
        verifyParsedItem(iterator.next(), "green bean (label)",  "3.71", null, 22);
        verifyParsedItem(iterator.next(), "inidian fou qua( label)",  "2.63", null, 23);
        verifyParsedItem(iterator.next(), "loriz whole wheat bread 565g",  "2.39", null, 24);
        verifyParsedItem(iterator.next(), "mango-philippine (large)",  "5.92", null, 25);
        verifyParsedItem(iterator.next(), "melon - hami - xin jiang #661",  "5.37", null, 28);
        verifyParsedItem(iterator.next(), "mushroom - enoki (bag)",  "0.99", null, 30);
        verifyParsedItem(iterator.next(), "mushroom-king oyster300g",  "2.49", null, 31);
        verifyParsedItem(iterator.next(), "nova peru mandarin",  "4.78", null, 32);
        verifyParsedItem(iterator.next(), "onion - green #750",  "5.00", null, 34);
        verifyParsedItem(iterator.next(), "pears - asian #",  "5.19", null, 37);
        verifyParsedItem(iterator.next(), "produce (label)",  "2.55", null, 39);
        verifyParsedItem(iterator.next(), "ssf beijing noodle 375g",  "3.18", null, 40);
        verifyParsedItem(iterator.next(), "tong ho (label)",  "2.56", null, 42);
        verifyParsedItem(iterator.next(), "tw oolong nd (family pack)",  "6.99", null, 44);
        verifyParsedItem(iterator.next(), "vs frz golden pompan600/800-lb",  "12.01", null, 45);
        verifyParsedItem(iterator.next(), "watercress",  "3.58", null, 48);
        verifyParsedItem(iterator.next(), "yr spinach noodles 600g",  "2.39", null, 50);
        verifyParsedItem(iterator.next(), "yu choy sum [no.1) (bag)#",  "2.15", null, 51);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "103.48",53);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/5/26",7);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst#867832214",6);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "36",57);

    }

    @Test
    public void receipt_2015_07_03_14_28_31()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_03_14_28_31, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("_99supermarket", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(34,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "you saved $5.39",  "8.99", null, 16);
        verifyParsedItem(iterator.next(), "a choy (chinese lettuce) #217",  "2.43", null, 18);
        verifyParsedItem(iterator.next(), "a choy stem #238",  "2.23", null, 20);
        verifyParsedItem(iterator.next(), "beatrice homo milk 4l",  "5.49", null, 22);
        verifyParsedItem(iterator.next(), "bf large brown eggs 12pcs",  "3.79", null, 24);
        verifyParsedItem(iterator.next(), "cabbage-taiwan#201",  "1.31", null, 25);
        verifyParsedItem(iterator.next(), "cabbage-taiwan",  "2.55", null, 27);
        verifyParsedItem(iterator.next(), "crd korean wheat noodle2.2lb",  "4.99", null, 29);
        verifyParsedItem(iterator.next(), "del honey citron tea 1l",  "5.99", null, 30);
        verifyParsedItem(iterator.next(), "dmdg japanese dried noodle300g",  "3.78", null, 32);
        verifyParsedItem(iterator.next(), "eb dried aniseed 70g",  "1.49", null, 34);
        verifyParsedItem(iterator.next(), "fresh commom yam rhizome#237",  "2.61", null, 35);
        verifyParsedItem(iterator.next(), "fresh shimeji mushrooms-b 150g",  "1.99", null, 37);
        verifyParsedItem(iterator.next(), "lgm black bean soy sauce 280g",  "2.49", null, 38);
        verifyParsedItem(iterator.next(), "lkk blk bean garlic sauce 368g",  "4.29", null, 39);
        verifyParsedItem(iterator.next(), "lo bok-green#610",  "1.02", null, 40);
        verifyParsedItem(iterator.next(), "mushroom - enoki (bag) #667",  "0.99", null, 42);
        verifyParsedItem(iterator.next(), "ncz black sesame sauce 200g",  "4.49", null, 43);
        verifyParsedItem(iterator.next(), "oranges-south africa #756",  "2.73", null, 44);
        verifyParsedItem(iterator.next(), "pears - ho sui(chile) #",  "1.70", null, 46);
        verifyParsedItem(iterator.next(), "pears - ho sui(chile) #",  "1.79", null, 48);
        verifyParsedItem(iterator.next(), "pears-fragrant( xin jiang)#82~",  "1.64", null, 50);
        verifyParsedItem(iterator.next(), "produce (label)",  "1.23", null, 52);
        verifyParsedItem(iterator.next(), "sanford kiwi mussels 454g",  "11.98", null, 53);
        verifyParsedItem(iterator.next(), "searay baby shrimp 300g",  "3.89", null, 56);
        verifyParsedItem(iterator.next(), "spinach #951",  "1.49", null, 57);
        verifyParsedItem(iterator.next(), "ssf shanghai noodle340g",  "1.49", null, 58);
        verifyParsedItem(iterator.next(), "tark koo choy (taku choy)#227",  "1.98", null, 59);
        verifyParsedItem(iterator.next(), "tong ho (label)",  "2.07", null, 61);
        verifyParsedItem(iterator.next(), "tw oolong nd(family pack)",  "6.99", null, 63);
        verifyParsedItem(iterator.next(), "vs frz golden pompano 600/800",  "4.91", null, 64);
        verifyParsedItem(iterator.next(), "wong lo kat herbal tea-6pk",  "5.99", null, 67);
        verifyParsedItem(iterator.next(), "yu choy sum (label)",  "3.19", null, 70);
        verifyParsedItem(iterator.next(), "yu choy sum u.s. #230",  "1.83", null, 71);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "116.37",73);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/5/6",7);
//        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "g st8o7832214",6);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "37",77);

    }

    @Test
    public void receipt_2015_07_03_15_22_13()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_03_15_22_13, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("_99supermarket", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "trans:3414 11    terminal",  "030.0", null, 3);
        verifyParsedItem(iterator.next(), "r ype:    purchase", null, null, 11);
        verifyParsedItem(iterator.next(), "acct:    mc",  "70.66", null, 12);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "70.66",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card tm1ber:             ************61 :: ~1",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/4",2);

    }

}