package com.openprice.parser.store.costco;

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
public class Costco26_97StTest extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_02_11_22_51_51.jpg.hengshuai.txt")
    private Resource receipt_51_51;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_02_27_22_17_07.jpg.random.txt")
    private Resource receipt_17_07;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_10_54.jpg.jingwang.txt")
    private Resource receipt_10_54;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_11_01.jpg.jingwang.txt")
    private Resource receipt_11_01;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_11_07.jpg.jingwang.txt")
    private Resource receipt_11_07;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_12_51.jpg.jingwang.txt")
    private Resource receipt_12_51;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_13_05.jpg.jingwang.txt")
    private Resource receipt_13_05;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_14_29.jpg.jingwang.txt")
    private Resource receipt_14_29;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_14_36.jpg.jingwang.txt")
    private Resource receipt_14_36;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_14_43.jpg.jingwang.txt")
    private Resource receipt_14_43;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_16_53.jpg.jingwang.txt")
    private Resource receipt_16_53;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_17_00.jpg.jingwang.txt")
    private Resource receipt_17_00;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_17_07.jpg.jingwang.txt")
    private Resource receipt_22_17_07;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_17_15.jpg.jingwang.txt")
    private Resource receipt_17_15;

    @Inject
    private ChainRegistry chainRegistry;

    @Test
    public void test_receipt_51_51_StoreChain() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_51_51, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        assertNotNull(chainRegistry);
        assertTrue(chainRegistry.getStoreChains().size()>0);
        log.debug(chainRegistry.getStoreChains().toString());
        final StoreChain chain=chainRegistry.findBestMatchedChain(ReceiptDataImpl.fromContentLines(receiptLines));
        assertNotNull(chain);
        assertEquals("costco", chain.getCode());
        log.debug(chain.getHeaderProperties().toString());
    }

    @Test
    public void test_receipt_51_51() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_51_51, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);

        printResult(receipt);
        assertEquals("costco", receipt.getChainCode());

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
//        assertEquals(13,receipt.getItems().size());
        assertEquals(15,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "ks ff women", "12.999", null, 7);
        verifyParsedItem(iterator.next(), "tpd/forhula", "3.009", null, 8);
        verifyParsedItem(iterator.next(), "materna 140$", "18.999", null, 9);
        verifyParsedItem(iterator.next(), "ks ff women", "12.999", null, 10);
        verifyParsedItem(iterator.next(), "tpd/formula", "3.009", null, 11);
        verifyParsedItem(iterator.next(), "970949 materna", "18.999", null, 12);
        verifyParsedItem(iterator.next(), "ks ff women", "12.999", null, 13);
        verifyParsedItem(iterator.next(), "tpd/formula", "3.009", null, 14);
        verifyParsedItem(iterator.next(), "ks f.f. men", "12.999", null, 15);
        verifyParsedItem(iterator.next(), "tpd/formula", "3.009", null, 16);
        verifyParsedItem(iterator.next(), "970949 materna", "18.999", null, 17);
        verifyParsedItem(iterator.next(), "ks f.f. hen", "12.999", null, 18);
        verifyParsedItem(iterator.next(), "tpd/formula", "3.009", null, 19);
        verifyParsedItem(iterator.next(), "k5 f.f. men", "12.999", null, 20);
        verifyParsedItem(iterator.next(), "tpd/formula", "3.009", null, 21);
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "116.91",22);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst =12147632 9rt",51);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/8",28);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*** cardholder copy ***",42);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "122.76",45);
    }

}
