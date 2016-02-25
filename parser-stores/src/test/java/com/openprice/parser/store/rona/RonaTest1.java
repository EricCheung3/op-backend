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

    @Test
    public void receipt_2015_02_12_23_37_52()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_12_23_37_52, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("rona", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

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
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "ed~o n ton, ab",  "6.64", null, 3);
        verifyParsedItem(iterator.next(), "ileh    qty    price",  "01", null, 8);
        verifyParsedItem(iterator.next(), "tape thread sealing 1/2x480\"wh",  "0.779", null, 12);
        verifyParsedItem(iterator.next(), "faucet kitchen 1h.lever 8\" cr",  "72.999", null, 14);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "73.76",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/3/2",42);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "77.45",20);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gs t/ hst # 103039624",31);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "acctu ************8426",22);

    }


}