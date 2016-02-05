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

import com.openprice.common.StringCommon;
import com.openprice.common.TextResourceUtils;
import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.data.Item;
import com.openprice.parser.data.ValueLine;
import com.openprice.parser.simple.SimpleParser;
import com.openprice.parser.store.AbstractReceiptParserIntegrationTest;

@RunWith(SpringJUnit4ClassRunner.class)
public class SafewayABBYYTest extends AbstractReceiptParserIntegrationTest {
    @Value("classpath:/testFiles/Safeway/abbyy/2015_11_25_southgate.txt")
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
        assertEquals(5,receipt.getItems().size());
        verifyItemParsedValue(iterator.next(), "chicken bbq roasted", "9.0", "");
        verifyItemParsedValue(iterator.next(), "clabatta buns 4pk", "2.50", "");
        verifyItemParsedValue(iterator.next(), "clabatta buns 4pk", "2.50", "");
        verifyItemParsedValue(iterator.next(), "spinach bunch", "1.49", "");
        verifyItemParsedValue(iterator.next(), "lucerne who1e mi1k4l", "3.79", "lucerne who1e mi1k4l");

        // verify parsed fields
        Map<ReceiptFieldType, ValueLine> fieldValues = receipt.getFieldToValueMap();
        //        assertEquals(fieldValues.get(ReceiptField.AddressLine1).getValue(), "100a 5015");
        //        assertEquals(fieldValues.get(ReceiptField.AddressCity).getValue(), "edmonton");
        assertEquals(fieldValues.get(ReceiptFieldType.Phone).getValue(), "780-435-5132");
        assertEquals(fieldValues.get(ReceiptFieldType.GstNumber).getValue(), "817093735");
        assertEquals(fieldValues.get(ReceiptFieldType.SubTotal).getValue(), "22.59");
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getValue(), "23.09");
        assertEquals(StringCommon.EMPTY, fieldValues.get(ReceiptFieldType.Date).getValue());//this receipt has no date string
    }

    @Value("classpath:/testFiles/Safeway/abbyy/receiptWithNoDateHeader.txt")
    private Resource receiptWithNoDateHeader;
    @Test
    public void testDateNoDateHeaderShouldAlsoFindIt() throws Exception{
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receiptWithNoDateHeader, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parse(receiptLines);
        assertEquals("2015/2/27", receipt.getFieldToValueMap().get(ReceiptFieldType.Date).getValue());
    }

}
