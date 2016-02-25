package com.openprice.parser.store.rona;

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
public class RonaTest1 extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testFiles/rona/2015_02_12_23_37_52.jpg.random.txt")
    private Resource receipt_2015_02_12_23_37_52;

    @Value("classpath:/testFiles/rona/2015_07_02_18_14_02.jpg.henryHuang.txt")
    private Resource receipt_2015_07_02_18_14_02;

    @Value("classpath:/testFiles/rona/2015_07_03_15_25_11.jpg.hengshuai.txt")
    private Resource receipt_2015_07_03_15_25_11;

    @Value("classpath:/testFiles/rona/2015_07_21_15_26_45.jpg.beata.txt")
    private Resource receipt_2015_07_21_15_26_45;

    //Note this is RCLS receipt contains "CORONA"
    @Test
    public void receipt_2015_02_12_23_37_52ShouldFindRCLS_Not_RONA()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_12_23_37_52, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rcls", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(6,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "peller cab/merlo    ghrj",  "38.29", null, 4);
        verifyParsedItem(iterator.next(), "red creek merlot    ghrj",  "7.99", null, 9);
        verifyParsedItem(iterator.next(), "redwood cab    ghrj",  "7.99", null, 13);
        verifyParsedItem(iterator.next(), "rw creek malbec    gmrj",  "7.99", null, 17);
        verifyParsedItem(iterator.next(), "corona 12pk    ghrj",  "24.29", null, 21);
        verifyParsedItem(iterator.next(), "plastic bags    grq",  "0.05", null, 26);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "88.35",27);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/8",62);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #          auth #      resp 001",44);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst # 12223-5922 rt0001",58);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 01649",71);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "92.68",30);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",50);
//        verifyParsedField(fieldValues, ReceiptFieldType.Account, "~****'*******'****"*****'l*"******",64);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "4.33",28);

    }

    @Test
    public void receipt_2015_07_02_18_14_02()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_02_18_14_02, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rona", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "ve .", null, null, 2);
//        verifyParsedItem(iterator.next(), "item    qty    price",  "0", null, 11);
        verifyParsedItem(iterator.next(), "vinyl sheet pre cut stone 6x9'",  "35.999", null, 14);
        verifyParsedItem(iterator.next(), "original price:",  "39.99", null, 15);
        verifyParsedItem(iterator.next(), "you    saved    today :",  "4.00", null, 19);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "40.48",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/25",44);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "42.50",24);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst              ~   103039624",33);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "carpet tape d.face 38mx12m                          4.49g",17);

    }

    @Test
    public void receipt_2015_07_03_15_25_11()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_03_15_25_11, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rona", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(10,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "item    qty    price",  "0.", null, 9);
        verifyParsedItem(iterator.next(), "seed vegetables packet",  "1.699", null, 12);
        verifyParsedItem(iterator.next(), "seed vegetables packet",  "1.899", null, 14);
        verifyParsedItem(iterator.next(), "seed vegetables packet",  "1.699", null, 16);
        verifyParsedItem(iterator.next(), "seed vegetables packet",  "1.699", null, 18);
        verifyParsedItem(iterator.next(), "deluxe 9- function water wand",  "9.999", null, 20);
        verifyParsedItem(iterator.next(), "hose garden ld 1/2\"x50' green",  "17.999", null, 22);
        verifyParsedItem(iterator.next(), "transplanter 31cm bk",  "6.999", null, 24);
        verifyParsedItem(iterator.next(), "rake bow 59 \" gn .",  "12.999", null, 26);
        verifyParsedItem(iterator.next(), "$ manure cow 30lt",  "3.599", null, 28);
        verifyParsedItem(iterator.next(), "$ manure cow 30lt",  "3.599", null, 30);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst / hst ~ 1030 39624",47);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "62.10",33);
        verifyParsedField(fieldValues, ReceiptFieldType.Time, "time: 16:55",59);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/4/17",58);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "65.21",36);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "acct1 * ************80 17",38);

    }

    @Test
    public void receipt_2015_07_21_15_26_45()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_15_26_45, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rona", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "ed~o n ton, ab",  "6.64", null, 3);
//        verifyParsedItem(iterator.next(), "ileh    qty    price",  "01", null, 8);
        verifyParsedItem(iterator.next(), "tape thread sealing 1/2x480\"wh",  "0.779", null, 12);
        verifyParsedItem(iterator.next(), "faucet kitchen 1h.lever 8\" cr",  "72.999", null, 14);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "73.76",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/3/2",42);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "77.45",20);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gs t/ hst # 103039624",31);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "acctu ************8426",22);

    }


}