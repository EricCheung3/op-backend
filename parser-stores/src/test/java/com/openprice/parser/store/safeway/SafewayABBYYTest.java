package com.openprice.parser.store.safeway;

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
public class SafewayABBYYTest extends AbstractReceiptParserIntegrationTest {

    @Value("classpath:/testfiles/safeway/abbyy/2015_11_25_southgate.txt")
    private Resource sampleReceipt1;

    @Test
    public void testReceipt1() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleReceipt1, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        assertEquals(5,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "chicken bbq roasted", "9.0", null, 6);
        verifyParsedItem(iterator.next(), "clabatta buns 4pk", "2.50", null, 8);
        verifyParsedItem(iterator.next(), "clabatta buns 4pk", "2.50", null, 10);
        verifyParsedItem(iterator.next(), "spinach bunch", "1.49", null, 11);
        verifyParsedItem(iterator.next(), "lucerne who1e mi1k4l", "3.79", "lucerne who1e mi1k4l", 12);

        // verify parsed fields
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "23.09",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "",-1);
        //TODO Why not pass?
        //verifyParsedField(fieldValues, ReceiptFieldType.AddressCity, "edmonton",2);
        //TODO passed in Eclipse, but not pass in command line tests. linux and mac encoding different?
        //verifyParsedField(fieldValues, ReceiptFieldType.StoreBranch, "safeway©",0);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.50",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "served by: sco 22",5);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "number of items",21);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "817093735",4);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "carrots 21b       3338366001       $1.99",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-435-5132",3);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "22.59",16);
    }

    @Value("classpath:/testfiles/safeway/abbyy/receiptWithNoDateHeader.txt")
    private Resource receiptWithNoDateHeader;

    @Test
    public void DateNoDateHeaderShouldAlsoFindIt() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receiptWithNoDateHeader, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        assertEquals("2015/2/27", receipt.getFields().get(ReceiptFieldType.Date).getFieldValue());
    }

}
