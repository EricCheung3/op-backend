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
        verifyItemParsedValue(iterator.next(), "rstr instnt ndle", "0.98", "rstr instnt ndle_05719775555");
        verifyItemParsedValue(iterator.next(), "table salt    mrj", "1.99", "table salt    mrj_06601007023");
        verifyItemParsedValue(iterator.next(), "garden wafer", "2.56", "garden wafer_08978200269");
        verifyItemParsedValue(iterator.next(), "rice stick", "1.08", "rice stick_693491804007");
        verifyItemParsedValue(iterator.next(), "deposit 1", "0.25", "deposit 1");
        verifyItemParsedValue(iterator.next(), "cntry hvst brd", "2.98", "cntry hvst brd_06340004440");
        verifyItemParsedValue(iterator.next(), "(2)9    plastic bags    grq", "0.10", "(2)9    plastic bags    grq");

        // verify parsed fields
        Map<ReceiptField, ValueLine> fieldValues = receipt.getFieldToValueMap();
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
        //printResult(receipt);

        // verify result of items
        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "opo squash    mrj", "2.80", "opo squash    mrj_3141");
        verifyItemParsedValue(iterator.next(), "chinese cabbage    mrj", "1.43", "chinese cabbage    mrj_4552");
        verifyItemParsedValue(iterator.next(), "muffin lemn cran", "4.87", "muffin lemn cran_06038387812");
        verifyItemParsedValue(iterator.next(), "plastic bags    gro", "0.05", "plastic bags    gro");

        // verify parsed fields
        Map<ReceiptField, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptField.Slogan).getValue(), "big on fresh, low on price");
        assertEquals(fieldValues.get(ReceiptField.StoreID).getValue(), "01570");
        assertEquals(fieldValues.get(ReceiptField.AddressLine1).getValue(), "4821 calgary trail");
        assertEquals(fieldValues.get(ReceiptField.AddressCity).getValue(), "edmonton");
        assertEquals(fieldValues.get(ReceiptField.Phone).getValue(), "780-430-2769");
        assertEquals(fieldValues.get(ReceiptField.GstNumber).getValue(), "12223-5922 rt0001");
        assertEquals(fieldValues.get(ReceiptField.SubTotal).getValue(), "9.15");
        assertEquals(fieldValues.get(ReceiptField.Total).getValue(), "9.15");
    }

    @Value("classpath:/testFiles/RCSS/CalgaryTrail/2015_04_04_22_22_32.jpg.jingwang.txt")
    private Resource sampleRCSS_2015_04_04_22_22_32;

    @Test
    public void testRCSS_2015_04_04_22_22_32() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleRCSS_2015_04_04_22_22_32, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parse(receiptLines);
        //printResult(receipt);

        // verify result of items
        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "orange juice", "3.98", "orange juice_04850001964");
        verifyItemParsedValue(iterator.next(), "deposit 1", "0.25", "deposit 1");
        verifyItemParsedValue(iterator.next(), "deposit 1", "0.25", "deposit 1");
        verifyItemParsedValue(iterator.next(), "corn bicolor 4ct", "7.94", "corn bicolor 4ct_03338370121");
        verifyItemParsedValue(iterator.next(), "pep grn swt 4ct", "2.98", "pep grn swt 4ct_03338370178");
        verifyItemParsedValue(iterator.next(), "potato m xd mini", "5.98", "potato m xd mini_06038310510");
        verifyItemParsedValue(iterator.next(), "rooster garlic", "2.48", "rooster garlic_06038388591");
        verifyItemParsedValue(iterator.next(), "wmelon mini sdls", "3.97", "wmelon mini sdls_3421");
        verifyItemParsedValue(iterator.next(), "pepper green swt", "2.02", "pepper green swt_4065");
        verifyItemParsedValue(iterator.next(), "mush crem bulk    hrj", "1.64", "mush crem bulk    hrj_4648");
        verifyItemParsedValue(iterator.next(), "split chkn wing", "17.58", "split chkn wing_2163820");
        verifyItemParsedValue(iterator.next(), "split chkn wing    hrj", "16.65", "split chkn wing    hrj_216382c");
        verifyItemParsedValue(iterator.next(), "lamb bonless leg", "24.50", "lamb bonless leg_2174190");
        verifyItemParsedValue(iterator.next(), "sq basa flts", "7.98", "sq basa flts_06038377431");
        verifyItemParsedValue(iterator.next(), "croissant cp", "5.00", "croissant cp_46036330079");

        // verify parsed fields
        Map<ReceiptField, ValueLine> fieldValues = receipt.getFieldToValueMap();
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
