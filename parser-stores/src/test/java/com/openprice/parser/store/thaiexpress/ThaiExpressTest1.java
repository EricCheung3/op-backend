package com.openprice.parser.store.thaiexpress;

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
public class ThaiExpressTest1 extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testfiles/thaiexpress/2015_10_10_14_52_39.jpg.txt")
    private Resource receipt_2015_10_10_14_52_39;

    @Value("classpath:/testfiles/thaiexpress/2015_10_10_14_52_52.jpg.txt")
    private Resource receipt_2015_10_10_14_52_52;

    @Value("classpath:/testfiles/thaiexpress/2015_10_10_14_53_09.jpg.txt")
    private Resource receipt_2015_10_10_14_53_09;

    @Value("classpath:/testfiles/thaiexpress/2015_10_10_14_53_37.jpg.txt")
    private Resource receipt_2015_10_10_14_53_37;

    @Value("classpath:/testfiles/thaiexpress/2015_10_10_14_53_41.jpg.txt")
    private Resource receipt_2015_10_10_14_53_41;

    @Value("classpath:/testfiles/thaiexpress/2015_10_10_14_54_05.jpg.txt")
    private Resource receipt_2015_10_10_14_54_05;

    @Value("classpath:/testfiles/thaiexpress/2015_10_10_14_55_01.jpg.txt")
    private Resource receipt_2015_10_10_14_55_01;

    @Value("classpath:/testfiles/thaiexpress/2015_10_10_14_55_11.jpg.txt")
    private Resource receipt_2015_10_10_14_55_11;

    @Value("classpath:/testfiles/thaiexpress/2015_10_10_14_55_15.jpg.txt")
    private Resource receipt_2015_10_10_14_55_15;

    @Value("classpath:/testfiles/thaiexpress/2015_10_10_14_56_04.jpg.txt")
    private Resource receipt_2015_10_10_14_56_04;

    @Value("classpath:/testfiles/thaiexpress/2015_10_10_14_56_44.jpg.txt")
    private Resource receipt_2015_10_10_14_56_44;

    @Value("classpath:/testfiles/thaiexpress/2015_10_10_14_56_57.jpg.txt")
    private Resource receipt_2015_10_10_14_56_57;

    @Value("classpath:/testfiles/thaiexpress/2015_10_10_14_57_23.jpg.txt")
    private Resource receipt_2015_10_10_14_57_23;

    @Value("classpath:/testfiles/thaiexpress/2015_10_10_14_57_45.jpg.txt")
    private Resource receipt_2015_10_10_14_57_45;

    @Test
    public void receipt_2015_10_10_14_52_39()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_52_39, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("thaiexpress", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 b. general green",  "9.48", null, 15);
        verifyParsedItem(iterator.next(), "1 k-dn noddle",  "0.70", null, 16);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                   0.51",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "10.69",20);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "10.18",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",11);

    }

    @Test
    public void receipt_2015_10_10_14_52_52()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_52_52, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("thaiexpress", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 b.b. beef pad sew",  "8.98", null, 15);
        verifyParsedItem(iterator.next(), "1 k-spicy",  "0.00", null, 16);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                      0.45",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "9.43",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",11);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "8.98",17);

    }

    @Test
    public void receipt_2015_10_10_14_53_09()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_53_09, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("thaiexpress", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 i.c mini tom yum chi",  "4.50", null, 15);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                       0.23",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "4.73",19);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "4.50",16);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",11);

    }

    @Test
    public void receipt_2015_10_10_14_53_37()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_53_37, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("thaiexpress", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1    a tom yum shrimp",  "10.38", null, 15);
        verifyParsedItem(iterator.next(), "1 take out",  "0.00", null, 16);
        verifyParsedItem(iterator.next(), "1 cold shrimp roll",  "3.75", null, 17);
        verifyParsedItem(iterator.next(), "1 cold shrimp roll",  "3.75", null, 18);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                0.89",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "18.77",22);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "17.88",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",11);

    }

    @Test
    public void receipt_2015_10_10_14_53_41()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_53_41, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("thaiexpress", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 5.c thai chicken",  "8.98", null, 14);
        verifyParsedItem(iterator.next(), "1 little",  "0.00", null, 15);
        verifyParsedItem(iterator.next(), "1 btl juice",  "2.25", null, 16);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                   0.56",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "11.79",20);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "11.23",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",10);
    }

    @Test
    public void receipt_2015_10_10_14_54_05()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_54_05, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("thaiexpress", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "i hf ckr t", null, null, 13);
        verifyParsedItem(iterator.next(), "1 b.c cashew chicken",  "8.98", null, 15);
        verifyParsedItem(iterator.next(), "1 medium",  "0.00", null, 16);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                        0.45",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "9.43",20);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "8.98",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",11);
    }

    @Test
    public void receipt_2015_10_10_14_55_01()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_55_01, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("thaiexpress", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1    shrimp pad thai",  "10.38", null, 15);
        verifyParsedItem(iterator.next(), "1 20 oz drink",  "2.00", null, 16);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "13.00",20);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "12.38",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",11);
    }

    @Test
    public void receipt_2015_10_10_14_55_11()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_55_11, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("thaiexpress", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(6,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1    b.b beef pad sew",  "8.98", null, 15);
        verifyParsedItem(iterator.next(), "1    no egg",  "0.00", null, 16);
        verifyParsedItem(iterator.next(), "i    take out",  "0.00", null, 17);
        verifyParsedItem(iterator.next(), "i    7.b beef pad thai",  "8.98", null, 18);
        verifyParsedItem(iterator.next(), "i    take out",  "0.00", null, 19);
        verifyParsedItem(iterator.next(), "1    btl juice",  "2.25", null, 20);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "21.22",24);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "20.21",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",11);
    }

    @Test
    public void receipt_2015_10_10_14_55_15()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_55_15, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("thaiexpress", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "    i i el-", null, null, 13);
        verifyParsedItem(iterator.next(), "1 7.d vege pad thai",  "8.68", null, 15);
        verifyParsedItem(iterator.next(), "1 little",  "0.00", null, 16);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                             0.43",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "9.11",20);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "8.68",17);
    }

    @Test
    public void receipt_2015_10_10_14_56_04()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_56_04, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("thaiexpress", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 gb promo natiional",  "9.98", null, 15);
        verifyParsedItem(iterator.next(), "1 btl juice",  "2.25", null, 16);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                     0.61",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "12.84",20);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "12.23",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",11);
    }

    @Test
    public void receipt_2015_10_10_14_56_44()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_56_44, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("thaiexpress", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 b.c chicken pad sew",  "8.98", null, 15);
        verifyParsedItem(iterator.next(), "1 btl juice",  "2.25", null, 16);
        verifyParsedItem(iterator.next(), "1 btl juice",  "2.25", null, 17);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                 0.67",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "14.15",21);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "13.48",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",11);
    }

    @Test
    public void receipt_2015_10_10_14_56_57()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_56_57, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("thaiexpress", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 7.a shrimp pad thai",  "10.38", null, 15);
        verifyParsedItem(iterator.next(), "1 medium",  "0.00", null, 16);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                         0.52",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "10.90",20);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "10.38",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",11);
    }

    @Test
    public void receipt_2015_10_10_14_57_23()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_57_23, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("thaiexpress", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 5.b thai beef",  "8.98", null, 15);
        verifyParsedItem(iterator.next(), "1 take out",  "0.00", null, 16);
        verifyParsedItem(iterator.next(), "1 btl juice    ^",  "2.25", null, 17);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                    0.56",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "11.79",21);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "11.23",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",11);
    }

    @Test
    public void receipt_2015_10_10_14_57_45()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_57_45, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("thaiexpress", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 5.c basil chicken",  "8.98", null, 15);
        verifyParsedItem(iterator.next(), "1 take out",  "0.00", null, 16);
        verifyParsedItem(iterator.next(), "1 little",  "0.00", null, 17);
        verifyParsedItem(iterator.next(), "1 btl juice",  "2.25", null, 18);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst                        0.56",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "11.79",22);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "11.23",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2004/5/10",11);
    }


}
