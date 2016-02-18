package com.openprice.parser.store;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.boot.test.SpringApplicationConfiguration;

import com.openprice.parser.ParsedField;
import com.openprice.parser.ParsedItem;
import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.simple.SimpleParser;

@SpringApplicationConfiguration(classes = {StoreParserTestApplication.class})
public class AbstractReceiptParserIntegrationTest {
    @Inject
    protected SimpleParser simpleParser;

    protected void verifyParsedItem(
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

    protected void verifyParsedField(
            final Map<ReceiptFieldType, ParsedField> fieldValues,
            final ReceiptFieldType type,
            final String value,
            final int lineNumber
            ){
        assertEquals(value, fieldValues.get(type).getFieldValue());
        assertEquals(lineNumber, fieldValues.get(type).getLineNumber());
    }

    protected void printResult(ParsedReceipt receipt) {
        System.out.println("Receipt parser result: store chain code='" + receipt.getChainCode() +
                "', branh is '" + receipt.getBranchName() + "'.");
        System.out.println("===================== Items parsed:");
        System.out.println("assertEquals("+receipt.getItems().size()+"," + "receipt.getItems().size());");
        for (ParsedItem item : receipt.getItems()) {
            if(item.getCatalogCode()!=null)
                System.out.println("verifyParsedItem(iterator.next(), \""+item.getParsedName() + "\", \""+
                    item.getParsedBuyPrice()+ "\", \""+ item.getCatalogCode() + "\", "+ item.getLineNumber()+ ");");
            else
                System.out.println("verifyParsedItem(iterator.next(), \""+item.getParsedName() + "\", \""+
                        item.getParsedBuyPrice()+ "\", "+ item.getCatalogCode() + ", "+ item.getLineNumber()+ ");");
        }
        System.out.println("===================== Fields parsed:");
        for (ReceiptFieldType field : receipt.getFields().keySet()) {
            System.out.println("verifyParsedField(fieldValues, ReceiptFieldType."+ field +", \""
                    + receipt.getFields().get(field).getFieldValue() +"\","
                    + receipt.getFields().get(field).getLineNumber()+");");
        }
    }
}
