package com.openprice.parser.store;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.springframework.boot.test.SpringApplicationConfiguration;

import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.data.Item;
import com.openprice.parser.data.ReceiptField;
import com.openprice.parser.simple.SimpleParser;

@SpringApplicationConfiguration(classes = {StoreParserTestApplication.class})
public class AbstractReceiptParserIntegrationTest {
    @Inject
    protected SimpleParser simpleParser;


    protected void verifyItemParsedValue(final Item item, final String name, final String value) {
        assertEquals(name, item.getName());
        assertEquals(value, item.getBuyPrice());
    }

    protected void printResult(ParsedReceipt receipt) {
        for (Item item : receipt.getItems()) {
            System.out.println("verifyItemParsedValue(iterator.next(), \""+item.getName() + "\",\""+ item.getBuyPrice() + "\");");
        }
        System.out.println("\n=====================\nFields parsed:");
        for (ReceiptField field : receipt.getFieldToValueMap().keySet()) {
            System.out.println(field.name() + " : " + receipt.getFieldToValueMap().get(field).getValue());
        }
    }
}
