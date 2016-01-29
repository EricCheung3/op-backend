package com.openprice.parser.store;

import org.springframework.boot.test.SpringApplicationConfiguration;

@SpringApplicationConfiguration(classes = {StoreParserTestApplication.class})
public class ParserSelectorIntegrationTest {


//    protected void verifyItemParsedValue(final Item item, final String name, final String value, final String catalogCode) {
//        assertEquals(name, item.getProduct().getName());
//        assertEquals(value, item.getBuyPrice());
//        assertEquals(catalogCode, item.getProduct().toCatalogCode());
//    }
//
//    protected void printResult(ParsedReceipt receipt) {
//        for (Item item : receipt.getItems()) {
//            System.out.println("verifyItemParsedValue(iterator.next(), \""+item.getProduct().getName() + "\", \""+
//                    item.getBuyPrice()+ "\", \""+ item.getProduct().toCatalogCode() + "\");");
//        }
//        System.out.println("\n=====================\nFields parsed:");
//        for (ReceiptField field : receipt.getFieldToValueMap().keySet()) {
//            System.out.println(field.name() + " : " + receipt.getFieldToValueMap().get(field).getValue());
//        }
//    }
}
