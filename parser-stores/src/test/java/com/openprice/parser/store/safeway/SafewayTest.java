package com.openprice.parser.store.safeway;

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
public class SafewayTest extends AbstractReceiptParserIntegrationTest {
    @Value("classpath:/testFiles/Safeway/Southgate/2015_02_27_20_04_24.jpg.dongcui.txt")
    private Resource sampleReceipt1;

    @Inject
    SimpleParser simpleParser;

    @Test
    public void testReceipt1TheCommentedItemsAreAllGone() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleReceipt1, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyParsedItem(iterator.next(), "sevengrain salad", "7.199", "sevengrain salad", 8);
        verifyParsedItem(iterator.next(), "hot soup small", "2.999", "hot soup small_2113006680", 9);
        verifyParsedItem(iterator.next(), "for baby summer veg", "1.19", "for baby summer veg", 10);
        verifyParsedItem(iterator.next(), "samosas beef", "2.99", "samosas beef_24586100000", 11);
        verifyParsedItem(iterator.next(), "for baby carrots", "1.19", "for baby carrots", 12);

        // verify parsed fields
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "14.87",16);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "100a 5015",32);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/27",44);
        //TODO why no match?
        // verifyParsedField(fieldValues, ReceiptFieldType.AddressCity, "edmonton",2);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreBranch, "safewaycanada .survey.market force .com",52);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.51",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "served by: sean s",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "817093735",4);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "cardholder",42);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "number of items                         5",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",36);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-435-5132",3);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "14.36",14);

    }

    @Value("classpath:/testFiles/Safeway/MGRKELSEYCOLE/2014_12_06_22_36_54.txt")
    private Resource sampleReceipt2;

    @Test
    public void testReceipt2TheCommentedItemsAreAllGone() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleReceipt2, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyParsedItem(iterator.next(), "danone strawberry", "5.87", "danone strawberry", 6);
        verifyParsedItem(iterator.next(), "iogo nomad drink", "2.199", "iogo nomad drink", 9);
        verifyParsedItem(iterator.next(), "pastry bulk", "1.299", "pastry bulk", 13);
        verifyParsedItem(iterator.next(), "wt    bulk minibits cook", "0.89", "wt    bulk minibits cook", 15);
        verifyParsedItem(iterator.next(), "cucumber", "1.29", "cucumber", 17);
        verifyParsedItem(iterator.next(), "6 qty    corn on cob", "3.00", "6 qty    corn on cob", 21);
        verifyParsedItem(iterator.next(), "2 qty    organic avocados", "5.38", "2 qty    organic avocados", 24);
        verifyParsedItem(iterator.next(), "butter lettuce", "3.49", "butter lettuce", 25);
        verifyParsedItem(iterator.next(), "2 qty    organic strawberry", "8.98", "2 qty    organic strawberry", 26);
        verifyParsedItem(iterator.next(), "4\"rose w/hat pick", "6.999", "4\"rose w/hat pick", 28);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "3.72",48);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/5/12",38);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "refrig/frozen",5);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "crf / recycling fee                       0 . 01 g",11);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressCity, "edmonton",18);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreBranch, "safewa~canada .s urve~ . ~arketforce . co~",54);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "",49);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "your cashier today was jennifer",43);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "817093735",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "vf       mastercard                       41 . 88",33);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "author . ii : 05790z",35);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-435-5132",1);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "account number ************6689",34);
    }
}
