package com.openprice.parser.store.safeway;

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

import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.common.TextResourceUtils;
import com.openprice.parser.data.Item;
import com.openprice.parser.data.ReceiptField;
import com.openprice.parser.data.ValueLine;
import com.openprice.parser.simple.SimpleParser;
import com.openprice.parser.store.AbstractReceiptParserIntegrationTest;

@RunWith(SpringJUnit4ClassRunner.class)
public class SafewayTest extends AbstractReceiptParserIntegrationTest {
    @Value("classpath:/testFiles/Safeway/Southgate/2015_02_27_20_04_24.jpg.dongcui.txt")
    private Resource sampleReceipt1;

    @Inject
    SimpleParser simpleParser;

    @Test
    public void testReceipt1() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleReceipt1, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parse(receiptLines);
        printResult(receipt);

        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "sevengrain salad", "$7. 19 GC");
        verifyItemParsedValue(iterator.next(), "Hot Soup Small", "$2.99 GC");
        verifyItemParsedValue(iterator.next(), "for baby summer veg", "$1.19 c");
        verifyItemParsedValue(iterator.next(), "Samosas Beef", "$2 .99 c");
        verifyItemParsedValue(iterator.next(), "for baby carrots", "$1.19 c");
        verifyItemParsedValue(iterator.next(), "=>FREE item", "-$1.19 c");

        // verify parsed fields
        Map<ReceiptField, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptField.AddressLine1).getValue(), "100a 5015");
        //assertEquals(fieldValues.get(ReceiptField.AddressCity).getValue(), "edmonton");
        assertEquals(fieldValues.get(ReceiptField.Phone).getValue(), "780-435-5132");
        assertEquals(fieldValues.get(ReceiptField.GstNumber).getValue(), "817093735");
        assertEquals(fieldValues.get(ReceiptField.SubTotal).getValue(), "14.36");
        assertEquals(fieldValues.get(ReceiptField.Total).getValue(), "14.87");

    }

//    @Value("classpath:/testFiles/Safeway/MGRKELSEYCOLE/2014_12_06_22_36_54.txt")
//    private Resource sampleReceipt2;
//
//    @Test
//    public void testReceipt2() throws Exception {
//        final List<String> receiptLines = new ArrayList<>();
//        TextResourceUtils.loadFromTextResource(sampleReceipt2, (line)-> receiptLines.add(line));
//
//        assertTrue(receiptLines.size() > 0);
//
//        ParsedReceipt receipt = simpleParser.parse(receiptLines);
//        printResult(receipt);
//    }
}
