package com.openprice.parser.store.rcss;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
public class RCSSSouthCommonReceiptTest extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testFiles/RCSS/SouthCommon/2015_02_09_13_15_25.jpg.henryHuang.txt")
    private Resource sampleRCSS_2015_02_09_13_15_25;

    @Test
    public void testRCSS_2015_02_09_13_15_25() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleRCSS_2015_02_09_13_15_25, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parse(receiptLines);
        //printResult(receipt);

        // verify result of items
        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "rooster rice", "25.78", "rooster rice_05719777953");
        verifyItemParsedValue(iterator.next(), "van bircrm mx", "3.50", "van bircrm mx");
        verifyItemParsedValue(iterator.next(), "cm dis prncs cnd", "2.99", "cm dis prncs cnd");
        verifyItemParsedValue(iterator.next(), "cm scribblers ha", "4.49", "cm scribblers ha");
        verifyItemParsedValue(iterator.next(), "dh fc starter", "2.79", "dh fc starter");
        verifyItemParsedValue(iterator.next(), "after bite kids", "4.99", "after bite kids");
        verifyItemParsedValue(iterator.next(), "c&c astringent", "6.49", "c&c astringent");
        verifyItemParsedValue(iterator.next(), "acne wash", "8.49", "acne wash");
        verifyItemParsedValue(iterator.next(), "st ives vanilla", "2.98", "st ives vanilla");
        verifyItemParsedValue(iterator.next(), "shower creme sof", "3.93", "shower creme sof");
        verifyItemParsedValue(iterator.next(), "wlin cpck dcrtng", "5.04", "wlin cpck dcrtng");
        verifyItemParsedValue(iterator.next(), "hp jar scraper", "5.00", "hp jar scraper");
        verifyItemParsedValue(iterator.next(), "measure cup    gmrj", "4.29", "measure cup    gmrj");

        // verify parsed fields
        Map<ReceiptField, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptField.Slogan).getValue(), "big on fresh, low on price");
        assertEquals(fieldValues.get(ReceiptField.StoreID).getValue(), "01549");
        assertEquals(fieldValues.get(ReceiptField.AddressLine1).getValue(), "9711 23 ave nw");
        assertEquals(fieldValues.get(ReceiptField.AddressCity).getValue(), "edmonton");
        assertEquals(fieldValues.get(ReceiptField.Phone).getValue(), "780-490-3918");
        assertEquals(fieldValues.get(ReceiptField.GstNumber).getValue(), "12223-5922 rt0001");
        assertEquals(fieldValues.get(ReceiptField.SubTotal).getValue(), "80.76");
        assertEquals(fieldValues.get(ReceiptField.Total).getValue(), "82.97");
        assertEquals("2014/8/16", fieldValues.get(ReceiptField.Date).getValue());

    }

    @Value("classpath:/testFiles/RCSS/SouthCommon/2015_04_04_21_28_21.jpg.jingwang.txt")
    private Resource sampleRCSS_2015_04_04_21_28_21;

    @Test
    public void testRCSS_2015_04_04_21_28_21() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleRCSS_2015_04_04_21_28_21, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parse(receiptLines);
        //printResult(receipt);

        // verify result of items
        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "chap ic snde sd", "4.99", "chap ic snde sd_06294200366");
        verifyItemParsedValue(iterator.next(), "shana naan", "1.99", "shana naan_06457970003");
        verifyItemParsedValue(iterator.next(), "shana lacha para", "1.99", "shana lacha para_503003900298");
        verifyItemParsedValue(iterator.next(), "wfz dmp rd rice", "8.97", "wfz dmp rd rice_690761966247");
        verifyItemParsedValue(iterator.next(), "banana", "4.36", "banana_4011");
        verifyItemParsedValue(iterator.next(), "tilapia whole", "8.87", "tilapia whole_2121080");
        verifyItemParsedValue(iterator.next(), "fzn tilapia", "4.01", "fzn tilapia_2863070");

        // verify parsed fields
        Map<ReceiptField, ValueLine> fieldValues = receipt.getFieldToValueMap();
        //assertEquals(fieldValues.get(ReceiptField.Chain).getValue(), "Superstore");
        assertEquals(fieldValues.get(ReceiptField.Slogan).getValue(), "big on fresh, low on price");
        assertEquals(fieldValues.get(ReceiptField.StoreID).getValue(), "01549");
        assertEquals(fieldValues.get(ReceiptField.AddressLine1).getValue(), "9711 23 ave nw");
        assertEquals(fieldValues.get(ReceiptField.AddressCity).getValue(), "edmonton");
        assertEquals(fieldValues.get(ReceiptField.Phone).getValue(), "780-490-3918");
        assertEquals(fieldValues.get(ReceiptField.GstNumber).getValue(), "12223-5922 rt0001");
        assertEquals(fieldValues.get(ReceiptField.SubTotal).getValue(), "35.18");
        assertEquals(fieldValues.get(ReceiptField.Total).getValue(), "35.18");
        assertEquals("2014/9/14", fieldValues.get(ReceiptField.Date).getValue());
    }
}
