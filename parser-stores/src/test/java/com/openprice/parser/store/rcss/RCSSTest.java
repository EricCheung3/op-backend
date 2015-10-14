package com.openprice.parser.store.rcss;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.common.TextResourceUtils;
import com.openprice.parser.data.Item;
import com.openprice.parser.data.ReceiptField;
import com.openprice.parser.simple.SimpleParser;
import com.openprice.parser.store.StoreParserTestApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {StoreParserTestApplication.class})
public class RCSSTest {
    @Value("classpath:/testFiles/RCSS/CalgaryTrail/2015_02_01_14_17_01.jpg.henryHuang.txt")
    private Resource sampleRCSS_2015_02_01_14_17_01;

    @Value("classpath:/testFiles/RCSS/CalgaryTrail/2015_02_09_13_15_09.jpg.henryHuang.txt")
    private Resource sampleRCSS_2015_02_09_13_15_09;

    @Inject
    SimpleParser simpleParser;

    @Test
    public void testRCSS_2015_02_01_14_17_01() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleRCSS_2015_02_01_14_17_01, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parse(receiptLines);
//        System.out.println("Items parsed:");
//        for (Item item : receipt.getItems()) {
//            System.out.println(item.getName() + " price:"+ item.getBuyPrice());
//        }
//        System.out.println("\n*******\nFields parsed:");
//        for (ReceiptField field : receipt.getFieldToValueMap().keySet()) {
//            System.out.println(field.name() + " : " + receipt.getFieldToValueMap().get(field).getValue());
//        }

        // verify result of items
        List<Item> items = receipt.getItems();
        assertEquals(8, items.size());
        assertEquals("RSTR INSTNT NDLE", items.get(0).getName());
        assertEquals("0.98", items.get(0).getBuyPrice());
        assertEquals("TABLE SALT", items.get(1).getName());
        assertEquals("1.99", items.get(1).getBuyPrice());
        assertEquals("GARDEN WAFER", items.get(2).getName());
        assertEquals("2.56", items.get(2).getBuyPrice());
        assertEquals("RICE STICK", items.get(3).getName());
        assertEquals("1.08", items.get(3).getBuyPrice());
        assertEquals("BEATRICE 1% MILK", items.get(4).getName());
        assertEquals("4.46", items.get(4).getBuyPrice());
        assertEquals("DEPOSIT 1", items.get(5).getName());
        assertEquals("0.25", items.get(5).getBuyPrice());
        assertEquals("CNTRY HVST BRD", items.get(6).getName());
        assertEquals("2.98", items.get(6).getBuyPrice());
        assertEquals("PLASTIC BAGS", items.get(7).getName());
        assertEquals("0.10", items.get(7).getBuyPrice());
    }

    @Test
    public void testRCSS_2015_02_09_13_15_09() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleRCSS_2015_02_09_13_15_09, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parse(receiptLines);
        System.out.println("Items parsed:");
        for (Item item : receipt.getItems()) {
            System.out.println(item.getName() + " price:"+ item.getBuyPrice());
        }
        System.out.println("\n*******\nFields parsed:");
        for (ReceiptField field : receipt.getFieldToValueMap().keySet()) {
            System.out.println(field.name() + " : " + receipt.getFieldToValueMap().get(field).getValue());
        }

        // verify result of items
        List<Item> items = receipt.getItems();
        assertEquals(4, items.size());
        assertEquals("OPO SQUASH", items.get(0).getName());
        assertEquals("2.80", items.get(0).getBuyPrice());
        assertEquals("CHINESE CABBAGE", items.get(1).getName());
        assertEquals("1.43", items.get(1).getBuyPrice());
        assertEquals("MUFFIN LEMN CRAN", items.get(2).getName());
        assertEquals("4.87", items.get(2).getBuyPrice());
        assertEquals("PLASTIC BAGS", items.get(3).getName());
        assertEquals("0.05", items.get(3).getBuyPrice());
    }

}
