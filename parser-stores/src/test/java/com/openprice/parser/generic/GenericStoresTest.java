package com.openprice.parser.generic;

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
import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.data.Item;
import com.openprice.parser.data.ValueLine;
import com.openprice.parser.simple.SimpleParser;
import com.openprice.parser.store.AbstractReceiptParserIntegrationTest;

@RunWith(SpringJUnit4ClassRunner.class)
public class GenericStoresTest extends AbstractReceiptParserIntegrationTest {

    @Inject
    SimpleParser simpleParser;

    @Value("classpath:/testFiles/Generic/2015_05_02_21_56_44.jpg.momingzhen159.txt")
    private Resource timHortons1;

    @Test
    public void testReceipt1() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(timHortons1, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parse(receiptLines);
        printResult(receipt);
        assertEquals("timhortons", receipt.getChain().getCode());

        Iterator<Item> iterator = receipt.getItems().iterator();
        assertEquals(3,receipt.getItems().size());
        verifyItemParsedValue(iterator.next(), "med tea latte", "2.84", "");
        verifyItemParsedValue(iterator.next(), "green tea", "0.00", "");
        verifyItemParsedValue(iterator.next(), "med latte", "2.59", "");

        // verify parsed fields
        Map<ReceiptFieldType, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptFieldType.SubTotal).getValue(), "5.43");

        //TODO
//        assertEquals(StringCommon.EMPTY, fieldValues.get(ReceiptField.Date).getValue());//this receipt has no date string
    }

    @Value("classpath:/testFiles/Generic/2015_05_02_21_56_44.jpg.momingzhen160_removeChainName.txt")
    private Resource timHortons2;

    @Test
    public void testReceipt1ChainNameRemovedThenReturnEmptyChainNameAndDateTotol() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(timHortons2, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parse(receiptLines);
        printResult(receipt);
        assertEquals("", receipt.getChain().getCode());

        Iterator<Item> iterator = receipt.getItems().iterator();
        assertEquals(3,receipt.getItems().size());
        verifyItemParsedValue(iterator.next(), "med tea latte", "2.84", "");
        verifyItemParsedValue(iterator.next(), "green tea", "0.00", "");
        verifyItemParsedValue(iterator.next(), "med latte", "2.59", "");

        // verify parsed fields
        Map<ReceiptFieldType, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptFieldType.SubTotal).getValue(), "5.43");

        //TODO
//        assertEquals(StringCommon.EMPTY, fieldValues.get(ReceiptField.Date).getValue());//this receipt has no date string
    }

    @Value("classpath:/testFiles/Generic/2015_06_14_21_42_08.jpg.dana.txt")
    private Resource nofrills1;

    @Test
    public void testReceipt2() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(nofrills1, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parse(receiptLines);
        printResult(receipt);
        assertEquals("nofrills", receipt.getChain().getCode());

        Iterator<Item> iterator = receipt.getItems().iterator();
        assertEquals(7,receipt.getItems().size());

        verifyItemParsedValue(iterator.next(), "no name all purp", "5.99", "");
        verifyItemParsedValue(iterator.next(), "red del. apples", "", "");
        verifyItemParsedValue(iterator.next(), "pear bartlett", "2.43", "");
        verifyItemParsedValue(iterator.next(), "mango red", "1.67", "");
        verifyItemParsedValue(iterator.next(), "pto russet 1olb    r", "3.97", "");
        verifyItemParsedValue(iterator.next(), "onion yellow 3lb    r", "1.47", "");
        verifyItemParsedValue(iterator.next(), "pco crt 2lb", "2.47", "");

        // verify parsed fields
        Map<ReceiptFieldType, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptFieldType.SubTotal).getValue(), "21.43");
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getValue(), "21.43");
        assertEquals("2015/3/5", fieldValues.get(ReceiptFieldType.Date).getValue());//this receipt has no date string
    }

    @Value("classpath:/testFiles/Generic/2015_07_03_16_42_52.jpg.haipeng.txt")
    private Resource mcDonalds1;

    @Test
    public void testReceipt3() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(mcDonalds1, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parse(receiptLines);
        printResult(receipt);
        assertEquals("mcdonald", receipt.getChain().getCode());

        Iterator<Item> iterator = receipt.getItems().iterator();
        assertEquals(2,receipt.getItems().size());

        verifyItemParsedValue(iterator.next(), "1 blueberry muffin", "1.69", "");
        verifyItemParsedValue(iterator.next(), "1 m latte", "1.50", "");

        // verify parsed fields
        Map<ReceiptFieldType, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptFieldType.SubTotal).getValue(), "3.19");
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getValue(), "3.35");
        assertEquals("2015/1/24", fieldValues.get(ReceiptFieldType.Date).getValue());//this receipt has no date string
    }

    @Value("classpath:/testFiles/Generic/2015_10_10_14_53_18.jpg.txt")
    private Resource subway1;
    @Test
    public void testReceipt4() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(subway1, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parse(receiptLines);
        printResult(receipt);
        assertEquals("subway", receipt.getChain().getCode());

        Iterator<Item> iterator = receipt.getItems().iterator();
        assertEquals(1,receipt.getItems().size());

        verifyItemParsedValue(iterator.next(), "2    soup rtu 8oz soup", "5.00", "");

        Map<ReceiptFieldType, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptFieldType.SubTotal).getValue(), "5.00");
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getValue(), "5.25");
        assertEquals("2015/10/8", fieldValues.get(ReceiptFieldType.Date).getValue());//this receipt has no date string
    }

}
