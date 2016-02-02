package com.openprice.parser.store.rcss;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.common.StringCommon;
import com.openprice.parser.common.TextResourceUtils;
import com.openprice.parser.data.Item;
import com.openprice.parser.data.ReceiptField;
import com.openprice.parser.data.ValueLine;
import com.openprice.parser.store.AbstractReceiptParserIntegrationTest;

@RunWith(SpringJUnit4ClassRunner.class)
public class RCSSABBYYTest extends AbstractReceiptParserIntegrationTest {
    @Value("classpath:/testFiles/RCSS/abbyy/2015_11_11_calgarytrail.txt")
    private Resource sampleRCSS_2015_11_11_calgarytrail;

    @Test
    public void testRCSS_2015_02_01_14_17_01TheCommentedItemsAreAllGone() throws Exception {
        final String ocrResult = TextResourceUtils.loadTextResource(sampleRCSS_2015_11_11_calgarytrail);

        ParsedReceipt receipt = simpleParser.parseOCRResults(java.util.Arrays.asList(ocrResult));
        printResult(receipt);

        // verify result of items
        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "yelw calros", "rice", "");
        verifyItemParsedValue(iterator.next(), "k dgon cook    wine    mrj", "2.69", "");
        verifyItemParsedValue(iterator.next(), "rooster garlic", "0.68", "rooster garlic_06038388591");
        verifyItemParsedValue(iterator.next(), "banana", "mftj", "banana_4011");
        verifyItemParsedValue(iterator.next(), "onion green", "067", "onion green_4068");
        verifyItemParsedValue(iterator.next(), "ducks fr7n    mrj", "15.23", "");
        verifyItemParsedValue(iterator.next(), "ducks frzh    mrj", "16.81", "");
        verifyItemParsedValue(iterator.next(), "hairtail", "7.36", "hairtail_77016160104");

        // verify parsed fields
        Map<ReceiptField, ValueLine> fieldValues = receipt.getFieldToValueMap();
        //        assertEquals(fieldValues.get(ReceiptField.Chain).getValue(), "Superstore");
        assertEquals(fieldValues.get(ReceiptField.Slogan).getValue(), "big on fresh, low on price");
        assertEquals(fieldValues.get(ReceiptField.StoreID).getValue(), "01570");
        assertEquals(fieldValues.get(ReceiptField.SubTotal).getValue(), "104.73");
        assertEquals(fieldValues.get(ReceiptField.Total).getValue(), "104.73");
        assertEquals(StringCommon.EMPTY, fieldValues.get(ReceiptField.Date).getValue());//this receipt has no date

    }
}
