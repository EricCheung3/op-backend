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
    public void testRCSS_2015_02_01_14_17_01() throws Exception {
        final String ocrResult = TextResourceUtils.loadTextResource(sampleRCSS_2015_11_11_calgarytrail);

        ParsedReceipt receipt = simpleParser.parseOCRResults(java.util.Arrays.asList(ocrResult));
        //printResult(receipt);

        // verify result of items
        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "yelw calros", "rice", "yelw calros_07323400310");
        verifyItemParsedValue(iterator.next(), "2 8 $24.88 list 4", "49.76", "2 8 $24.88 list 4");
        verifyItemParsedValue(iterator.next(), "k dgon cook    wine    mrj", "2.69", "k dgon cook    wine    mrj_690294490073");
        verifyItemParsedValue(iterator.next(), "deposii 1", "0.25", "deposii 1");
        verifyItemParsedValue(iterator.next(), "rooster garlic", "0.68", "rooster garlic_06038388591");
        verifyItemParsedValue(iterator.next(), "banana", "mftj", "banana_4011");
        verifyItemParsedValue(iterator.next(), "0.940 kg 8 $1.73/kg", "1.60", "0.940 kg 8 $1.73/kg");
        verifyItemParsedValue(iterator.next(), "onion gren    mrj", "067", "onion gren    mrj_4068");
        verifyItemParsedValue(iterator.next(), "ducks fr7n    mrj", "15.23", "ducks fr7n    mrj_2021000");
        verifyItemParsedValue(iterator.next(), "ducks frzh    mrj", "16.81", "ducks frzh    mrj_2021000");
        verifyItemParsedValue(iterator.next(), "hairtail", "7.36", "hairtail_77016160104");

        // verify parsed fields
        Map<ReceiptField, ValueLine> fieldValues = receipt.getFieldToValueMap();
//        assertEquals(fieldValues.get(ReceiptField.Chain).getValue(), "Superstore");
        assertEquals(fieldValues.get(ReceiptField.Slogan).getValue(), "big on fresh, low on price");
        assertEquals(fieldValues.get(ReceiptField.StoreID).getValue(), "01570");
        assertEquals(fieldValues.get(ReceiptField.SubTotal).getValue(), "104.73");
        assertEquals(fieldValues.get(ReceiptField.Total).getValue(), "104.73");

    }
}
