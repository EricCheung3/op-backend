package com.openprice.parser.store;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.springframework.boot.test.SpringApplicationConfiguration;

import com.openprice.parser.ParsedItem;
import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.simple.SimpleParser;

@SpringApplicationConfiguration(classes = {StoreParserTestApplication.class})
public class AbstractReceiptParserIntegrationTest {
    @Inject
    protected SimpleParser simpleParser;

    protected void verifyItemParsedValue(
            final ParsedItem item,
            final String name,
            final String value,
            final String catalogCode,
            final int lineNumber) {
        assertEquals(name, item.getParsedName());
        assertEquals(value, item.getParsedBuyPrice());
        assertEquals(catalogCode, item.getCatalogCode());
        assertEquals(lineNumber, item.getLineNumber());
    }

    protected void printResult(ParsedReceipt receipt) {
        for (ParsedItem item : receipt.getItems()) {
            if(item.getCatalogCode()!=null)
                System.out.println("verifyItemParsedValue(iterator.next(), \""+item.getParsedName() + "\", \""+
                    item.getParsedBuyPrice()+ "\", \""+ item.getCatalogCode() + "\", "+ item.getLineNumber()+ ");");
            else
                System.out.println("verifyItemParsedValue(iterator.next(), \""+item.getParsedName() + "\", \""+
                        item.getParsedBuyPrice()+ "\", "+ item.getCatalogCode() + ", "+ item.getLineNumber()+ ");");
        }
        System.out.println("\n=====================\nFields parsed:");
        for (ReceiptFieldType field : receipt.getFields().keySet()) {
            System.out.println(field.name() + " : " + receipt.getFields().get(field).getFieldValue());
        }
    }
}
