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
public class RCSSCalgaryTrailReceiptTest extends AbstractReceiptParserIntegrationTest {


    @Value("classpath:/testFiles/RCSS/CalgaryTrail/2015_02_01_14_17_01.jpg.henryHuang.txt")
    private Resource sampleRCSS_2015_02_01_14_17_01;

    @Test
    public void testRCSS_2015_02_01_14_17_01() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleRCSS_2015_02_01_14_17_01, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parse(receiptLines);

        //printResult(receipt);

        // verify result of items
        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "RSTR INSTNT NDLE","0.98");
        verifyItemParsedValue(iterator.next(), "TABLE SALT","1.99");
        verifyItemParsedValue(iterator.next(), "GARDEN WAFER","2.56");
        verifyItemParsedValue(iterator.next(), "RICE STICK","1.08");
        verifyItemParsedValue(iterator.next(), "DEPOSIT 1","0.25");
        verifyItemParsedValue(iterator.next(), "CNTRY HVST BRD","2.98");
        verifyItemParsedValue(iterator.next(), "PLASTIC BAGS","0.10");

        // verify parsed fields
        Map<ReceiptField, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptField.Chain).getValue(), "RCSS");
        assertEquals(fieldValues.get(ReceiptField.Slogan).getValue(), "big on fresh, low on price");
        assertEquals(fieldValues.get(ReceiptField.StoreID).getValue(), "01570");
        assertEquals(fieldValues.get(ReceiptField.AddressLine1).getValue(), "4821 calgary trail");
        assertEquals(fieldValues.get(ReceiptField.AddressCity).getValue(), "edmonton");
        assertEquals(fieldValues.get(ReceiptField.Phone).getValue(), "780-430-2769");
        assertEquals(fieldValues.get(ReceiptField.GstNumber).getValue(), "12223-5922 rt0001");
        assertEquals(fieldValues.get(ReceiptField.SubTotal).getValue(), "14.47");
        //assertEquals(fieldValues.get(ReceiptField.GstAmount).getValue(), "0.01");  // FIXME ??
        assertEquals(fieldValues.get(ReceiptField.Total).getValue(), "14.48");

    }

    @Value("classpath:/testFiles/RCSS/CalgaryTrail/2015_02_09_13_15_09.jpg.henryHuang.txt")
    private Resource sampleRCSS_2015_02_09_13_15_09;

    @Test
    public void testRCSS_2015_02_09_13_15_09() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleRCSS_2015_02_09_13_15_09, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parse(receiptLines);
        // printResult(receipt);

        // verify result of items
        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "OPO SQUASH","2.80");
        verifyItemParsedValue(iterator.next(), "CHINESE CABBAGE","1.43");
        verifyItemParsedValue(iterator.next(), "MUFFIN LEMN CRAN","4.87");
        verifyItemParsedValue(iterator.next(), "PLASTIC BAGS","0.05");

        // verify parsed fields
        Map<ReceiptField, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptField.Chain).getValue(), "RCSS");
        assertEquals(fieldValues.get(ReceiptField.Slogan).getValue(), "big on fresh, low on price");
        assertEquals(fieldValues.get(ReceiptField.StoreID).getValue(), "01570");
        assertEquals(fieldValues.get(ReceiptField.AddressLine1).getValue(), "4821 calgary trail");
        assertEquals(fieldValues.get(ReceiptField.AddressCity).getValue(), "edmonton");
        assertEquals(fieldValues.get(ReceiptField.Phone).getValue(), "780-430-2769");
        assertEquals(fieldValues.get(ReceiptField.GstNumber).getValue(), "12223-5922 rt0001");
        assertEquals(fieldValues.get(ReceiptField.SubTotal).getValue(), "9.15");
        assertEquals(fieldValues.get(ReceiptField.Total).getValue(), "9.15");
        //assertEquals(fieldValues.get(ReceiptField.).getValue(), "");
    }

    @Value("classpath:/testFiles/RCSS/CalgaryTrail/2015_04_04_22_22_32.jpg.jingwang.txt")
    private Resource sampleRCSS_2015_04_04_22_22_32;

    @Test
    public void testRCSS_2015_04_04_22_22_32() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleRCSS_2015_04_04_22_22_32, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parse(receiptLines);
        // printResult(receipt);

        // verify result of items
        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "ORANGE JUICE","3.98");
        verifyItemParsedValue(iterator.next(), "DEPOSIT 1","0.25");
        verifyItemParsedValue(iterator.next(), "DEPOSIT 1","0.25");
        verifyItemParsedValue(iterator.next(), "CORN BICOLOR 4CT","7.94");
        verifyItemParsedValue(iterator.next(), "PEP GRN SWT 4CT","2.98");
        verifyItemParsedValue(iterator.next(), "POTATO M XD MINI","5.98");
        verifyItemParsedValue(iterator.next(), "ROOSTER GARLIC","2.48");
        verifyItemParsedValue(iterator.next(), "WMELON MINI SDLS","3.97");
        verifyItemParsedValue(iterator.next(), "PEPPER GREEN SWT","2.02");
        verifyItemParsedValue(iterator.next(), "MUSH CREM BULK","1.64");
        verifyItemParsedValue(iterator.next(), "SPLIT CHKN WING","17.58");
        verifyItemParsedValue(iterator.next(), "SPLIT CHKN WING","16.65");
        verifyItemParsedValue(iterator.next(), "LAMB BONLESS LEG","24.50");
        verifyItemParsedValue(iterator.next(), "SQ BASA FLTS","7.98");
        verifyItemParsedValue(iterator.next(), "CROISSANT CP","5.00");


        // verify parsed fields
        Map<ReceiptField, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptField.Chain).getValue(), "RCSS");
        assertEquals(fieldValues.get(ReceiptField.Slogan).getValue(), "big on fresh, low on price");
        assertEquals(fieldValues.get(ReceiptField.StoreID).getValue(), "01570");
        assertEquals(fieldValues.get(ReceiptField.AddressLine1).getValue(), "4821 calgary trail");
        assertEquals(fieldValues.get(ReceiptField.AddressCity).getValue(), "edmonton");
        assertEquals(fieldValues.get(ReceiptField.Phone).getValue(), "780-430-2769");
        assertEquals(fieldValues.get(ReceiptField.GstNumber).getValue(), "12223-5922 rt0001");
        assertEquals(fieldValues.get(ReceiptField.SubTotal).getValue(), "116.71");
        assertEquals(fieldValues.get(ReceiptField.Total).getValue(), "116.71");
        //assertEquals(fieldValues.get(ReceiptField.).getValue(), "");
    }
}
