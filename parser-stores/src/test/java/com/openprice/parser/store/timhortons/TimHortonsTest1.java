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
import com.openprice.parser.store.AbstractReceiptParserIntegrationTest;
import com.openprice.store.StoreChain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class TimHortonsTest1 extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testFiles/TimHortons/branch110_51Ave/2015_02_10_13_25_20.jpg.hengshuai.txt")
    private Resource receipt_25_20;

    @Value("classpath:/testFiles/TimHortons/branch110_51Ave/2015_05_02_21_56_44.jpg.momingzhen159.txt")
    private Resource receipt_56_44;

    @Value("classpath:/testFiles/TimHortons/branch110_51Ave/2015_06_04_21_37_07.jpg.shiZheng.txt")
    private Resource receipt_37_07;

    @Value("classpath:/testFiles/TimHortons/branch110_51Ave/2015_07_03_14_24_24.jpg.hengshuai.txt")
    private Resource receipt_24_24;


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
        final StoreChain chain=chainRegistry.findBestMatchedChain(ReceiptDataImpl.fromContentLines(receiptLines));
        assertNotNull(chain);
        assertEquals("timhortons", chain.getCode());
        log.debug(chain.getHeaderProperties().toString());
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
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "",-1);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "12.25",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card entry:chip                     sequence: 000132",26);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst #892793597rt0001",23);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "11.67",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "auth #:052798                             approved",33);
    }

}
