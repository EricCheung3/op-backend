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

import com.openprice.common.TextResourceUtils;
import com.openprice.parser.ParsedField;
import com.openprice.parser.ParsedItem;
import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.ReceiptFieldType;
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

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        //printResult(receipt);

        // verify result of items
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "rstr instnt ndle", "0.98", "rstr instnt ndle_05719775555");
        verifyItemParsedValue(iterator.next(), "table salt    mrj", "1.99", "");
        verifyItemParsedValue(iterator.next(), "garden wafer", "2.56", "garden wafer_08978200269");
        verifyItemParsedValue(iterator.next(), "rice stick", "1.08", "rice stick_693491804007");
        //verifyItemParsedValue(iterator.next(), "deposit 1", "0.25", "deposit 1");
        verifyItemParsedValue(iterator.next(), "cntry hvst brd", "2.98", "cntry hvst brd_06340004440");
        verifyItemParsedValue(iterator.next(), "(2)9    plastic bags    grq", "0.10", "");

        // verify parsed fields
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(fieldValues.get(ReceiptFieldType.Slogan).getFieldValue(), "big on fresh, low on price");
        assertEquals(fieldValues.get(ReceiptFieldType.StoreID).getFieldValue(), "01570");
        assertEquals(fieldValues.get(ReceiptFieldType.AddressLine1).getFieldValue(), "4821 calgary trail");
        assertEquals(fieldValues.get(ReceiptFieldType.AddressCity).getFieldValue(), "edmonton");
        assertEquals(fieldValues.get(ReceiptFieldType.Phone).getFieldValue(), "780-430-2769");
        assertEquals(fieldValues.get(ReceiptFieldType.GstNumber).getFieldValue(), "12223-5922 rt0001");
        assertEquals(fieldValues.get(ReceiptFieldType.SubTotal).getFieldValue(), "14.47");
        //assertEquals(fieldValues.get(ReceiptField.GstAmount).getFieldValue(), "0.01");  // FIXME ??
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getFieldValue(), "14.48");
        assertEquals("2015/1/18", fieldValues.get(ReceiptFieldType.Date).getFieldValue());

    }

    @Value("classpath:/testFiles/RCSS/CalgaryTrail/2015_02_09_13_15_09.jpg.henryHuang.txt")
    private Resource sampleRCSS_2015_02_09_13_15_09;

    @Test
    public void testRCSS_2015_02_09_13_15_09() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleRCSS_2015_02_09_13_15_09, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        // verify result of items
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "opo squash", "2.80", "opo squash_3141");
        verifyItemParsedValue(iterator.next(), "chinese cabbage    mrj", "1.43", "");
        verifyItemParsedValue(iterator.next(), "muffin lemn cran", "4.87", "muffin lemn cran_06038387812");
        verifyItemParsedValue(iterator.next(), "plastic bags", "0.05", "plastic bags");

        // verify parsed fields
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(fieldValues.get(ReceiptFieldType.Slogan).getFieldValue(), "big on fresh, low on price");
        assertEquals(fieldValues.get(ReceiptFieldType.StoreID).getFieldValue(), "01570");
        assertEquals(fieldValues.get(ReceiptFieldType.AddressLine1).getFieldValue(), "4821 calgary trail");
        assertEquals(fieldValues.get(ReceiptFieldType.AddressCity).getFieldValue(), "edmonton");
        assertEquals(fieldValues.get(ReceiptFieldType.Phone).getFieldValue(), "780-430-2769");
        assertEquals(fieldValues.get(ReceiptFieldType.GstNumber).getFieldValue(), "12223-5922 rt0001");
        assertEquals(fieldValues.get(ReceiptFieldType.SubTotal).getFieldValue(), "9.15");
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getFieldValue(), "9.15");
        assertEquals("2014/12/3", fieldValues.get(ReceiptFieldType.Date).getFieldValue());
    }

    @Value("classpath:/testFiles/RCSS/CalgaryTrail/2015_04_04_22_22_32.jpg.jingwang.txt")
    private Resource sampleRCSS_2015_04_04_22_22_32;

    @Test
    public void testRCSS_2015_04_04_22_22_32() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleRCSS_2015_04_04_22_22_32, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        //printResult(receipt);

        // verify result of items
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "orange juice", "3.98", "orange juice_04850001964");
        // verifyItemParsedValue(iterator.next(), "deposit 1", "0.25", "deposit 1");
        //verifyItemParsedValue(iterator.next(), "deposit 1", "0.25", "deposit 1");
        verifyItemParsedValue(iterator.next(), "corn bicolor 4ct", "7.94", "corn bicolor 4ct_03338370121");
        verifyItemParsedValue(iterator.next(), "pep grn swt 4ct", "2.98", "pep grn swt 4ct_03338370178");
        verifyItemParsedValue(iterator.next(), "potato m xd mini", "5.98", "potato m xd mini_06038310510");
        verifyItemParsedValue(iterator.next(), "rooster garlic", "2.48", "rooster garlic_06038388591");
        verifyItemParsedValue(iterator.next(), "wmelon mini sdls", "3.97", "wmelon mini sdls_3421");
        verifyItemParsedValue(iterator.next(), "pepper green swt", "2.02", "pepper green swt_4065");
        verifyItemParsedValue(iterator.next(), "mush crem bulk", "1.64", "mush crem bulk_4648");
        verifyItemParsedValue(iterator.next(), "split chkn wing", "17.58", "split chkn wing_2163820");
        verifyItemParsedValue(iterator.next(), "split chkn wing", "16.65", "split chkn wing_2163820");
        verifyItemParsedValue(iterator.next(), "lamb bonless leg", "24.50", "lamb bonless leg_2174190");
        verifyItemParsedValue(iterator.next(), "sq basa flts", "7.98", "sq basa flts_06038377431");
        verifyItemParsedValue(iterator.next(), "croissant cp", "5.00", "croissant cp_46036330079");

        // verify parsed fields
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(fieldValues.get(ReceiptFieldType.Slogan).getFieldValue(), "big on fresh, low on price");
        assertEquals(fieldValues.get(ReceiptFieldType.StoreID).getFieldValue(), "01570");
        assertEquals(fieldValues.get(ReceiptFieldType.AddressLine1).getFieldValue(), "4821 calgary trail");
        assertEquals(fieldValues.get(ReceiptFieldType.AddressCity).getFieldValue(), "edmonton");
        assertEquals(fieldValues.get(ReceiptFieldType.Phone).getFieldValue(), "780-430-2769");
        assertEquals(fieldValues.get(ReceiptFieldType.GstNumber).getFieldValue(), "12223-5922 rt0001");
        assertEquals(fieldValues.get(ReceiptFieldType.SubTotal).getFieldValue(), "116.71");
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getFieldValue(), "116.71");
        //assertEquals(fieldValues.get(ReceiptField.).getFieldValue(), "");
        assertEquals("2015/4/4", fieldValues.get(ReceiptFieldType.Date).getFieldValue());
    }

    @Value("classpath:/testFiles/RCSS/CalgaryTrail/2015_07_21_10_50_33.jpg.henryHuang.txt")
    private Resource sampleRCSS_2015_07_21_10_50_33;
    @Test
    public void testRCSS_2015_07_21_10_50_33() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleRCSS_2015_07_21_10_50_33, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        final Iterator<ParsedItem> iterator=receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "orange navel 5lb", "5.88", "orange navel 5lb_03338311006");
        verifyItemParsedValue(iterator.next(), "banana", "1.35", "banana_4011");
        verifyItemParsedValue(iterator.next(), "cherries red    mrj", "7.29", "");
        verifyItemParsedValue(iterator.next(), "tc baby powder    gmrj", "0.99", "");
        verifyItemParsedValue(iterator.next(), "baby bar    gmrj", "2.49", "");
        verifyItemParsedValue(iterator.next(), "plastic bags", "0.05", "plastic bags");

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        //TODO: check. will this "supersto re" be returned to front-end?
        assertEquals("supersto re", fieldValues.get(ReceiptFieldType.Chain).getFieldValue());

        assertEquals("2015/6/18", fieldValues.get(ReceiptFieldType.Date).getFieldValue());
        assertEquals("big on fresh, low on price", fieldValues.get(ReceiptFieldType.Slogan).getFieldValue());
        assertEquals("01570", fieldValues.get(ReceiptFieldType.StoreID).getFieldValue());
        assertEquals("4821 calgary trail", fieldValues.get(ReceiptFieldType.AddressLine1).getFieldValue());
        assertEquals("edmonton", fieldValues.get(ReceiptFieldType.AddressCity).getFieldValue());
        assertEquals("780-430-2769", fieldValues.get(ReceiptFieldType.Phone).getFieldValue());
        assertEquals("0.77", fieldValues.get(ReceiptFieldType.GstAmount).getFieldValue());
        assertEquals("12223-5922 rt0001", fieldValues.get(ReceiptFieldType.GstNumber).getFieldValue());
        assertEquals("29.97", fieldValues.get(ReceiptFieldType.SubTotal).getFieldValue());
        assertEquals("30.74", fieldValues.get(ReceiptFieldType.Total).getFieldValue());
        //assertEquals(fieldValues.get(ReceiptField.).getFieldValue(), "");
        assertEquals("2015/6/18", fieldValues.get(ReceiptFieldType.Date).getFieldValue());

    }

}
