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
import com.openprice.parser.ParsedField;
import com.openprice.parser.ParsedItem;
import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.ReceiptFieldType;
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

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        //printResult(receipt);
        assertEquals("timhortons", receipt.getChainCode());

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "med tea latte", "2.84", null, 5);
        verifyParsedItem(iterator.next(), "green tea", "0.00", null, 6);
        verifyParsedItem(iterator.next(), "med latte", "2.59", null, 7);

        // verify parsed fields
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(fieldValues.get(ReceiptFieldType.SubTotal).getFieldValue(), "5.43");

        //TODO
//        assertEquals(StringCommon.EMPTY, fieldValues.get(ReceiptField.Date).getFieldValue());//this receipt has no date string
    }

    @Value("classpath:/testFiles/Generic/2015_05_02_21_56_44.jpg.momingzhen160_removeChainName.txt")
    private Resource timHortons2;

//TODO use some other store's receipt, because we now have tim hortons parser.
//    @Test
//    public void testReceipt1ChainNameRemovedThenReturnEmptyChainNameAndDateTotol() throws Exception {
//        final List<String> receiptLines = new ArrayList<>();
//        TextResourceUtils.loadFromTextResource(timHortons2, (line)-> receiptLines.add(line));
//
//        assertTrue(receiptLines.size() > 0);
//
//        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
//        //printResult(receipt);
//        assertEquals(null, receipt.getChainCode());
//
//        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
//        assertEquals(3,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "med tea latte", "2.84", null, 5);
//        verifyParsedItem(iterator.next(), "green tea", "0.00", null, 6);
//        verifyParsedItem(iterator.next(), "med latte", "2.59", null, 7);
//
//        // verify parsed fields
//        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
//        assertEquals(fieldValues.get(ReceiptFieldType.SubTotal).getFieldValue(), "5.43");
//
//        //TODO
////        assertEquals(StringCommon.EMPTY, fieldValues.get(ReceiptField.Date).getFieldValue());//this receipt has no date string
//    }

    @Value("classpath:/testFiles/Generic/2015_06_14_21_42_08.jpg.dana.txt")
    private Resource nofrills1;

    @Test
    public void testReceipt2() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(nofrills1, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        //printResult(receipt);
        assertEquals("nofrills", receipt.getChainCode());

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        assertEquals(7,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "no name all purp", "5.99", null, 7);
        verifyParsedItem(iterator.next(), "red del. apples", null, null, 9);
        verifyParsedItem(iterator.next(), "pear bartlett", "2.43", null, 11);
        verifyParsedItem(iterator.next(), "mango red", "1.67", null, 13);
        verifyParsedItem(iterator.next(), "pto russet 1olb    r", "3.97", null, 14);
        verifyParsedItem(iterator.next(), "onion yellow 3lb    r", "1.47", null, 15);
        verifyParsedItem(iterator.next(), "pco crt 2lb", "2.47", null, 17);

        // verify parsed fields
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(fieldValues.get(ReceiptFieldType.SubTotal).getFieldValue(), "21.43");
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getFieldValue(), "21.43");
        assertEquals("2015/3/5", fieldValues.get(ReceiptFieldType.Date).getFieldValue());//this receipt has no date string
    }

    @Value("classpath:/testFiles/Generic/2015_07_03_16_42_52.jpg.haipeng.txt")
    private Resource mcDonalds1;

    @Test
    public void testReceipt3() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(mcDonalds1, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        //printResult(receipt);
        assertEquals("mcdonalds", receipt.getChainCode());

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        assertEquals(2,receipt.getItems().size());

        verifyParsedItem(iterator.next(), "1 blueberry muffin", "1.69", null, 15);
        verifyParsedItem(iterator.next(), "1 m latte", "1.50", null, 16);

        // verify parsed fields
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals("3.19", fieldValues.get(ReceiptFieldType.SubTotal).getFieldValue());
        assertEquals("3.35", fieldValues.get(ReceiptFieldType.Total).getFieldValue());
        assertEquals("2015/1/24", fieldValues.get(ReceiptFieldType.Date).getFieldValue());//this receipt has no date string
    }

}
