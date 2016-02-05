package com.openprice.parser.store.rcss;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.openprice.common.StringCommon;
import com.openprice.common.TextResourceUtils;
import com.openprice.parser.ParsedField;
import com.openprice.parser.ParsedItem;
import com.openprice.parser.ParsedReceiptImpl;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.store.AbstractReceiptParserIntegrationTest;

@RunWith(SpringJUnit4ClassRunner.class)
public class RCSSABBYYTest extends AbstractReceiptParserIntegrationTest {
    @Value("classpath:/testFiles/RCSS/abbyy/2015_11_11_calgarytrail.txt")
    private Resource sampleRCSS_2015_11_11_calgarytrail;

    @Test
    public void testRCSS_2015_02_01_14_17_01TheCommentedItemsAreAllGone() throws Exception {
        final String ocrResult = TextResourceUtils.loadTextResource(sampleRCSS_2015_11_11_calgarytrail);

        ParsedReceiptImpl receipt = simpleParser.parseOCRResults(java.util.Arrays.asList(ocrResult));
        printResult(receipt);

        // verify result of items
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "yelw calros", "rice", "");
        verifyItemParsedValue(iterator.next(), "k dgon cook    wine    mrj", "2.69", "");
        verifyItemParsedValue(iterator.next(), "rooster garlic", "0.68", "rooster garlic_06038388591");
        verifyItemParsedValue(iterator.next(), "banana", "mftj", "banana_4011");
        verifyItemParsedValue(iterator.next(), "onion green", "067", "onion green_4068");
        verifyItemParsedValue(iterator.next(), "ducks fr7n    mrj", "15.23", "");
        verifyItemParsedValue(iterator.next(), "ducks frzh    mrj", "16.81", "");
        verifyItemParsedValue(iterator.next(), "hairtail", "7.36", "hairtail_77016160104");

        // verify parsed fields
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        //        assertEquals(fieldValues.get(ReceiptField.Chain).getFieldValue(), "Superstore");
        assertEquals(fieldValues.get(ReceiptFieldType.Slogan).getFieldValue(), "big on fresh, low on price");
        assertEquals(fieldValues.get(ReceiptFieldType.StoreID).getFieldValue(), "01570");
        assertEquals(fieldValues.get(ReceiptFieldType.SubTotal).getFieldValue(), "104.73");
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getFieldValue(), "104.73");
        assertEquals(StringCommon.EMPTY, fieldValues.get(ReceiptFieldType.Date).getFieldValue());//this receipt has no date

    }
}
