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
        //printResult(receipt);

        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "sevengrain salad", "7.199", "sevengrain salad");
        verifyItemParsedValue(iterator.next(), "hot soup small", "2.999", "hot soup small_2113006680");
        verifyItemParsedValue(iterator.next(), "for baby summer veg", "1.19", "for baby summer veg");
        verifyItemParsedValue(iterator.next(), "samosas beef", "2.99", "samosas beef_24586100000");
        verifyItemParsedValue(iterator.next(), "for baby carrots -", "1.19", "for baby carrots -");
        verifyItemParsedValue(iterator.next(), "=>free item", "1.19", "=>free item");

        // verify parsed fields
        Map<ReceiptField, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptField.AddressLine1).getValue(), "100a 5015");
        //assertEquals(fieldValues.get(ReceiptField.AddressCity).getValue(), "edmonton");
        assertEquals(fieldValues.get(ReceiptField.Phone).getValue(), "780-435-5132");
        assertEquals(fieldValues.get(ReceiptField.GstNumber).getValue(), "817093735");
        assertEquals(fieldValues.get(ReceiptField.SubTotal).getValue(), "14.36");
        assertEquals(fieldValues.get(ReceiptField.Total).getValue(), "14.87");

    }

    @Value("classpath:/testFiles/Safeway/MGRKELSEYCOLE/2014_12_06_22_36_54.txt")
    private Resource sampleReceipt2;

    @Test
    public void testReceipt2() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleReceipt2, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parse(receiptLines);
        printResult(receipt);

        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "danone strawberry", "5.87", "danone strawberry");
        verifyItemParsedValue(iterator.next(), "deposit", "0.80", "deposit");
        verifyItemParsedValue(iterator.next(), "logo nomad drink", "2.199", "logo nomad drink");
        verifyItemParsedValue(iterator.next(), "deposit", "0.10", "deposit");
        verifyItemParsedValue(iterator.next(), "pastry bulk", "1.299", "pastry bulk");
        verifyItemParsedValue(iterator.next(), "wt    bulk minibits cook", "0.89", "wt    bulk minibits cook");
        verifyItemParsedValue(iterator.next(), "cucumber", "1.29", "cucumber");
        verifyItemParsedValue(iterator.next(), "regprice", ".38", "regprice");
        verifyItemParsedValue(iterator.next(), "savings", ".38", "savings");
        verifyItemParsedValue(iterator.next(), "6 qty    corn on cob", "3.00", "6 qty    corn on cob");
        verifyItemParsedValue(iterator.next(), "regprice", "5.31", "regprice");
        verifyItemParsedValue(iterator.next(), "savings", "2.31", "savings");
        verifyItemParsedValue(iterator.next(), "2 qty    organic avocados", "5.38", "2 qty    organic avocados");
        verifyItemParsedValue(iterator.next(), "butter lettuce", "3.49", "butter lettuce");
        verifyItemParsedValue(iterator.next(), "2 qty    organic strawberry", "8.98", "2 qty    organic strawberry");
        verifyItemParsedValue(iterator.next(), "4\"rose w/hat pick", "6.999", "4\"rose w/hat pick");
        verifyItemParsedValue(iterator.next(), "regprice", "7.99", "regprice");
        verifyItemParsedValue(iterator.next(), "savings", "1.00", "savings");
        verifyItemParsedValue(iterator.next(), "change", "0.00", "change");
        verifyItemParsedValue(iterator.next(), "b.", "", "b._5/12/14 12:55 0877 08 029l");
        verifyItemParsedValue(iterator.next(), "savings", "3.72", "savings");

    }
}
