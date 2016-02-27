package com.openprice.parser.store.timhortons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import com.openprice.parser.ReceiptDataImpl;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.data.StoreChainFound;
import com.openprice.parser.store.AbstractReceiptParserIntegrationTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class TimHortonsTest1 extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testfiles/timhortons/branch110_51ave/2015_02_10_13_25_20.jpg.hengshuai.txt")
    private Resource receipt_25_20;

    @Value("classpath:/testfiles/timhortons/branch110_51ave/2015_05_02_21_56_44.jpg.momingzhen159.txt")
    private Resource receipt_56_44;

    @Value("classpath:/testfiles/timhortons/branch110_51ave/2015_06_04_21_37_07.jpg.shiZheng.txt")
    private Resource receipt_37_07;

    @Value("classpath:/testfiles/timhortons/branch110_51ave/2015_07_03_14_24_24.jpg.hengshuai.txt")
    private Resource receipt_24_24;

    @Value("classpath:/testFiles/TimHortons/branch_Lessard183/2015_07_03_13_54_48.jpg.hengshuai.txt")
    private Resource receipt_13_54;

    @Value("classpath:/testfiles/timHortons/phone/yuanji_19Feb2016.txt")
    private Resource receipt_phone_yuanji19Feb16;

    @Inject
    private ChainRegistry chainRegistry;

    @Test
    public void test_receipt_25_20_StoreChain() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_25_20, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        assertNotNull(chainRegistry);
        assertTrue(chainRegistry.getStoreChains().size()>0);
        log.debug(chainRegistry.getStoreChains().toString());
        final StoreChainFound chain=chainRegistry.findBestMatchedChain(ReceiptDataImpl.fromContentLines(receiptLines));
        assertNotNull(chain.getChain());
        assertEquals("timhortons", chain.getChain().getCode());
        log.debug(chain.getChain().getHeaderProperties().toString());
    }

    @Test
    public void receipt_25_20() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_25_20, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        assertEquals("timhortons", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

        assertEquals(7,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1    med tea latte", "2.84", null, 5);
        verifyParsedItem(iterator.next(), "1    green tea", "0.00", null, 6);
        verifyParsedItem(iterator.next(), "1    med tea latte", "2.84", null, 7);
        verifyParsedItem(iterator.next(), "1    green tea", "0.00", null, 8);
        verifyParsedItem(iterator.next(), "1    reg crsp chkn c1ub", "5.99", null, 9);
        verifyParsedItem(iterator.next(), "1    1 slice cheddar", "0.00", null, 10);
        verifyParsedItem(iterator.next(), "1    3 pieces bacon", "0.00", null, 11);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "receipt # : 10993894",22);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/9",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "12.25",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card entry:chip                     sequence: 000132",26);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst #892793597rt0001",23);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "11.67",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "auth #:052798                             approved",33);
    }

    @Test
    public void receipt_56_44() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_56_44, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        assertEquals("timhortons", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "med tea latte", "2.84", null, 5);
        verifyParsedItem(iterator.next(), "green tea", "0.00", null, 6);
        verifyParsedItem(iterator.next(), "med latte", "2.59", null, 7);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst #892793597rt0001",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "5.70",10);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "5.43",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/4/21",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "recei pt # : 11400754",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card entry: tap _icc                  sequence: 000188",23);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "auth #: 072052                               approued",31);
    }

    @Test
    public void receipt_37_07() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_37_07, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        assertEquals("timhortons", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "medium iced latte", "2.59", null, 5);
        verifyParsedItem(iterator.next(), "white milk", "0.00", null, 6);
        verifyParsedItem(iterator.next(), "sml latte", "2.00", null, 7);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card entry:tap_icc                    sequence : 000175",25);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "auth u: 035946                               a pproved",32);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "4.82",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "recei pt u : 11049774",21);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst d892793597rt0001",22);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "4.59",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/19",20);

    }

    @Test
    public void receipt_24_24() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_24_24, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        assertEquals("timhortons", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "large kettle chips", "1.89", null, 5);
        verifyParsedItem(iterator.next(), "snall frsmoo yogurt", "2.69", null, 6);
        verifyParsedItem(iterator.next(), "strawberry / banana", "0.00", null, 7);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "4.58",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "4.81",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "aut h n:063498                                approueo",31);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst m 892793597rt  0001",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/6/17",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card entry:chip                        sequence : 000047",24);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "r eceipt ~ : 11761284",20);
    }

    @Test
    public void receipt_13_54() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_13_54, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        assertEquals("timhortons", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "med tea latte", "2.84", null, 4);
        verifyParsedItem(iterator.next(), "green tea", "0.00", null, 5);
        verifyParsedItem(iterator.next(), "med tea latte", "2.84", null, 6);
        verifyParsedItem(iterator.next(), "green tea", "0.00", null, 7);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card entry �r��-",23);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "5.68",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "5.96",10);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst ij 136458304",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "r ecel pt ~ : 9227703",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/6/29",18);
    }

    @Test
    public void receipt_phone_yuanji19Feb16() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_phone_yuanji19Feb16, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        assertEquals("timhortons", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(7,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "2 lg original blend", "3.62", null, 8);
        verifyParsedItem(iterator.next(), "2 double double", "000", null, 9);
        verifyParsedItem(iterator.next(), "1 steak mushroon melt", "6.49", null, 10);
        verifyParsedItem(iterator.next(), "1 white bun /sandwich", "0.00", null, 11);
        verifyParsedItem(iterator.next(), "1 regular pulled pork", "5.49", null, 12);
        verifyParsedItem(iterator.next(), "1 ciabatta /sandwich", "0.00", null, 13);
        verifyParsedItem(iterator.next(), "1 grilled", "50.00", null, 14);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst hr892375478",26);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "15.60",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2016/2/16",24);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "receipt tt : 3238914",25);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card entry:tapjcc                                     sequence:000069",29);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "16.36",17);

    }
}
