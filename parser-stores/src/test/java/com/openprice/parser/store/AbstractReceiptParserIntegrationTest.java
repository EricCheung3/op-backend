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
        assertEquals(removeNonPrintableChars(name), removeNonPrintableChars(item.getParsedName()));
        assertEquals(removeNonPrintableChars(value), removeNonPrintableChars(item.getParsedBuyPrice()));
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

    private String removeNonPrintableChars(final String str){
        if(str==null)
            return null;
        return str.replaceAll("\\P{Print}", "");
    }

    protected void printResult(ParsedReceipt receipt) {
        System.out.println("Receipt parser result: store chain code='" + receipt.getChainCode() +
                "', branch is '" + receipt.getBranchName() + "'.");
        System.out.println("===================== Items parsed:");
        System.out.println("assertEquals("+receipt.getItems().size()+"," + "receipt.getItems().size());");
        for (ParsedItem item : receipt.getItems()) {
            String name = removeNonPrintableChars(item.getParsedName()).replace("\"", "\\\"");
            String code=null;
            if(item.getCatalogCode()!=null)
                code="\""+ item.getCatalogCode()+"\"";
            String price=null;
            if(item.getParsedBuyPrice()!=null)
                price=" \""+removeNonPrintableChars(item.getParsedBuyPrice())+ "\"";
            System.out.println("verifyParsedItem(iterator.next(), \""+ name
            + "\", " + price +", " + code +", " +  item.getLineNumber()+ ");");
        }

        for (ReceiptFieldType field : receipt.getFields().keySet()) {
            final String value = removeNonPrintableChars(receipt.getFields().get(field).getFieldValue()).replace("\"", "\\\"");
            System.out.println("verifyParsedField(fieldValues, ReceiptFieldType."+ field +", \""
                    +  value +"\","
                    + receipt.getFields().get(field).getLineNumber()+");");
        }
    }
}
