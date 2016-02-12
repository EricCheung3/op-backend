package com.openprice.parser.store.rcss;

import java.util.Iterator;
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
public class RCSSABBYYTest extends AbstractReceiptParserIntegrationTest {
    @Value("classpath:/testFiles/RCSS/abbyy/2015_11_11_calgarytrail.txt")
    private Resource sampleRCSS_2015_11_11_calgarytrail;

    @Test
    public void testRCSS_2015_02_01_14_17_01TheCommentedItemsAreAllGone() throws Exception {
        final String ocrResult = TextResourceUtils.loadTextResource(sampleRCSS_2015_11_11_calgarytrail);

        ParsedReceipt receipt = simpleParser.parseReceiptOcrResult(java.util.Arrays.asList(ocrResult));
        printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyParsedItem(iterator.next(), "yelw calros", "rice", null, 8);
        verifyParsedItem(iterator.next(), "k dgon cook    wine    mrj", "2.69", null, 11);
        verifyParsedItem(iterator.next(), "rooster garlic", "0.68", "rooster garlic_06038388591", 17);
        verifyParsedItem(iterator.next(), "banana", "mftj", "banana_4011", 18);
        verifyParsedItem(iterator.next(), "onion green", "067", "onion green_4068", 20);
        verifyParsedItem(iterator.next(), "ducks fr7n    mrj", "15.23", null, 22);
        verifyParsedItem(iterator.next(), "ducks frzh    mrj", "16.81", null, 23);
        verifyParsedItem(iterator.next(), "hairtail", "7.36", "hairtail_77016160104", 25);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "",-1);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "01570",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "ecology fee                                      0.08",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "104.73",29);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh, low on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "104.73",27);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card#;",5);
    }
}
