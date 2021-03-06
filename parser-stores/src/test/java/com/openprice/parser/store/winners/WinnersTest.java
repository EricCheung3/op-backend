package com.openprice.parser.store.winners;

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
public class WinnersTest extends AbstractReceiptParserIntegrationTest {
    @Value("classpath:/testfiles/winners/2015_02_09_15_27_18.jpg.random.txt")
    private Resource receipt_2015_02_09_15_27_18;

    @Value("classpath:/testfiles/winners/2015_02_09_16_51_42.jpg.random.txt")
    private Resource receipt_2015_02_09_16_51_42;

    @Value("classpath:/testfiles/winners/yuanMarch2.txt")
    private Resource receipt_yuanMarch2;

    @Value("classpath:/testfiles/winners/yuanMarch2_nowinners.txt")
    private Resource receipt_yuanMarch2_nowinners;

    @Test
    public void receipt_2015_02_09_15_27_18()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_15_27_18, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("dollartree", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(9,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "description    qty    price",  "0", null, 7);
        verifyParsedItem(iterator.next(), "bowl 7in    1",  "1.0", null, 8);
        verifyParsedItem(iterator.next(), "dinner fork    1",  "1.0", null, 9);
        verifyParsedItem(iterator.next(), "bowl 7in    1",  "1.0", null, 10);
        verifyParsedItem(iterator.next(), "bowl 7in    1",  "1.0", null, 11);
        verifyParsedItem(iterator.next(), "clear bowl    1",  "1.0", null, 12);
        verifyParsedItem(iterator.next(), "clear bowl    1",  "1.0", null, 13);
        verifyParsedItem(iterator.next(), "sp paprika    1",  "1.0", null, 14);
        verifyParsedItem(iterator.next(), "placemat    1",  "1.0", null, 15);
        verifyParsedItem(iterator.next(), "placemat    1",  "1.0", null, 16);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/16",50);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "10.75",17);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                             $0.49",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "11.24",20);

    }

    @Test
    public void receipt_2015_02_09_16_51_42()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_16_51_42, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("dollartree", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(10,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "description    qty    price",  "0", null, 8);
        verifyParsedItem(iterator.next(), "bridge mix    1",  "1.0", null, 9);
        verifyParsedItem(iterator.next(), "crackerfuls    1",  "0.0", null, 10);
        verifyParsedItem(iterator.next(), "crackerfuls    1",  "0.0", null, 11);
        verifyParsedItem(iterator.next(), "m&m candle aplcinn    1",  "1.0", null, 12);
        verifyParsedItem(iterator.next(), "cs party mix    1",  "1.0", null, 13);
        verifyParsedItem(iterator.next(), "diet coke 1l    1",  "1.0", null, 14);
        verifyParsedItem(iterator.next(), "diet coke 1l    1",  "1.0", null, 16);
        verifyParsedItem(iterator.next(), "sprkling sprng wtr    1",  "1.0", null, 18);
        verifyParsedItem(iterator.next(), "sprkling sprng wtr    1",  "1.0", null, 20);
        verifyParsedItem(iterator.next(), "diet coke 1l    1",  "1.0", null, 22);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/17",58);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "10.50",24);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                              $0.53",25);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "11.03",26);
    }

    @Test
    public void receipt_yuanMarch2()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_yuanMarch2, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("winners", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(5,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "40 - bov's 8 - 18",  "16.999", null, 9);
        verifyParsedItem(iterator.next(), "40 - boy's 8 - 18",  "16.999", null, 10);
        verifyParsedItem(iterator.next(), "40 - boy's 8 - 18",  "19.999", null, 11);
        verifyParsedItem(iterator.next(), "40 - boy's 8 - 18",  "14.999", null, 12);
        verifyParsedItem(iterator.next(), "93 - gifts and gear",  "29.999", null, 13);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "accordance with issuers agreement with",29);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card #:                                        ************2944",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2016/2/21",25);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "98.95",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "auth #:026224                                 sequence #:000131",23);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst no. 86032 6255 rt0001 ca #                              07043",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "103.90",16);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "trans:purchase                                   amount:$103.90",22);
 }

    @Test
    public void receipt_yuanMarch2_nowinnersIsFineBecauseIdentifySlogan()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_yuanMarch2_nowinners, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("winners", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(5,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "40 - bov's 8 - 18",  "16.999", null, 9);
        verifyParsedItem(iterator.next(), "40 - boy's 8 - 18",  "16.999", null, 10);
        verifyParsedItem(iterator.next(), "40 - boy's 8 - 18",  "19.999", null, 11);
        verifyParsedItem(iterator.next(), "40 - boy's 8 - 18",  "14.999", null, 12);
        verifyParsedItem(iterator.next(), "93 - gifts and gear",  "29.999", null, 13);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "accordance with issuers agreement with",29);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card #:                                        ************2944",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2016/2/21",25);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "98.95",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "auth #:026224                                 sequence #:000131",23);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst no. 86032 6255 rt0001 ca #                              07043",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "103.90",16);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "trans:purchase                                   amount:$103.90",22);

    }

}