package com.openprice.parser.store.subway;

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
public class SubwayTest extends AbstractReceiptParserIntegrationTest {

    @Value("classpath:/testfiles/subway/2015_10_10_14_53_18.jpg.txt")
    private Resource receipt_2015_10_10_14_53_18;

    @Value("classpath:/testfiles/subway/2015_10_10_14_57_10.jpg.txt")
    private Resource receipt_2015_10_10_14_57_10;

    @Value("classpath:/testfiles/subway/2015_10_10_14_57_37.jpg.txt")
    private Resource receipt_2015_10_10_14_57_37;

    @Value("classpath:/testfiles/subway/2015_10_10_15_03_25.jpg.txt")
    private Resource receipt_2015_10_10_15_03_25;

    @Value("classpath:/testfiles/subway/2015_10_10_15_04_21.jpg.txt")
    private Resource receipt_2015_10_10_15_04_21;

    @Value("classpath:/testfiles/subway/2015_10_10_15_04_48.jpg.txt")
    private Resource receipt_2015_10_10_15_04_48;

    @Value("classpath:/testfiles/subway/2015_10_10_15_05_02.jpg.txt")
    private Resource receipt_2015_10_10_15_05_02;

    @Value("classpath:/testfiles/subway/2015_10_10_15_08_33.jpg.txt")
    private Resource receipt_2015_10_10_15_08_33;

    @Value("classpath:/testfiles/subway/2015_10_10_15_08_42.jpg.txt")
    private Resource receipt_2015_10_10_15_08_42;

    @Value("classpath:/testfiles/subway/2015_10_10_15_08_53.jpg.txt")
    private Resource receipt_2015_10_10_15_08_53;

    @Value("classpath:/testfiles/subway/2015_10_10_15_09_25.jpg.txt")
    private Resource receipt_2015_10_10_15_09_25;

    @Value("classpath:/testfiles/subway/2015_10_10_15_12_29.jpg.txt")
    private Resource receipt_2015_10_10_15_12_29;

    @Value("classpath:/testfiles/subway/2015_10_10_15_12_34.jpg.txt")
    private Resource receipt_2015_10_10_15_12_34;

    @Value("classpath:/testfiles/subway/2015_10_10_15_16_46.jpg.txt")
    private Resource receipt_2015_10_10_15_16_46;

    @Value("classpath:/testfiles/subway/2015_10_10_15_17_51.jpg.txt")
    private Resource receipt_2015_10_10_15_17_51;

    @Value("classpath:/testfiles/subway/2015_10_10_15_20_19.jpg.txt")
    private Resource receipt_2015_10_10_15_20_19;

    @Value("classpath:/testfiles/subway/2015_10_17_16_54_00.jpg.txt")
    private Resource receipt_2015_10_17_16_54_00;

    @Value("classpath:/testfiles/subway/2015_10_17_16_56_39.jpg.txt")
    private Resource receipt_2015_10_17_16_56_39;

    @Value("classpath:/testfiles/subway/2015_10_17_16_58_41.jpg.txt")
    private Resource receipt_2015_10_17_16_58_41;

    @Value("classpath:/testfiles/subway/2015_10_17_16_58_55.jpg.txt")
    private Resource receipt_2015_10_17_16_58_55;

    @Test
    public void receipt_2015_10_10_14_53_18()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_53_18, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("subway", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "2    soup rtu 8oz soup",  "5.00", null, 12);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "5.00",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 128 127 324",9);
    }

    @Test
    public void receipt_2015_10_10_14_57_10()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_57_10, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("subway", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 12\" chicken teriyaki sub",  "9.00", null, 12);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "9.00",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 128 127 324",9);
    }

    @Test
    public void receipt_2015_10_10_14_57_37()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_14_57_37, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("subway", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 12\" steak & chse sub",  "9.00", null, 12);
        verifyParsedItem(iterator.next(), "1    bottled carbonated drink $2.30", null, null, 13);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "11.30",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/6",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 128 127 324",9);
    }

    @Test
    public void receipt_2015_10_10_15_03_25()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_15_03_25, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("subway", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1    12\" pizza sub sub",  "7.35", null, 12);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "7.35",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/4",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 128 127 324",9);
    }

    @Test
    public void receipt_2015_10_10_15_04_21()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_15_04_21, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("subway", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 12\" ham sub",  "7.35", null, 12);
        verifyParsedItem(iterator.next(), "-bacon addft",  "1.60", null, 13);
        verifyParsedItem(iterator.next(), "1    21oz fountain    drink 21fnt",  "2.00", null, 14);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/4",5);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "10.95",16);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 128 127 324",9);
    }

    @Test
    public void receipt_2015_10_10_15_04_48()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_15_04_48, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("subway", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 12\" ham sub",  "7.35", null, 12);
        verifyParsedItem(iterator.next(), "-bacon addft",  "1.60", null, 13);
        verifyParsedItem(iterator.next(), "1    21oz fountain drink 21fnt $2.00", null, null, 14);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "10.95",16);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/4",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 128 127 324",9);
    }

    @Test
    public void receipt_2015_10_10_15_05_02()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_15_05_02, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("subway", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1    6\" tuna sub",  "5.25", null, 12);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "5.25",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/4",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 128 127 324",9);
    }

    @Test
    public void receipt_2015_10_10_15_08_33()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_15_08_33, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("subway", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 12\" egg & cheese bkfst flatbd",  "6.00", null, 12);
        verifyParsedItem(iterator.next(), "coffee",  "1.60", null, 13);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "7.60",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/3",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 128 12/ 324",9);
    }

    @Test
    public void receipt_2015_10_10_15_08_42()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_15_08_42, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("subway", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 12\" chicken teriyaki sub",  "9.00", null, 10);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "9.00",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/3",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 128 127 324",8);
    }

    @Test
    public void receipt_2015_10_10_15_08_53()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_15_08_53, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("subway", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1    6\" subway club sub",  "6.00", null, 12);
        verifyParsedItem(iterator.next(), "1    -can - fresh value meal",  "3.70", null, 13);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "9.70",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/3",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 128 127 324",9);

    }

    @Test
    public void receipt_2015_10_10_15_09_25()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_15_09_25, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("subway", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 12\" chicken teriyaki sub",  "9.00", null, 12);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "9.00",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/3",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 128 127 324",9);
    }

    @Test
    public void receipt_2015_10_10_15_12_29()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_15_12_29, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("subway", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1    6\" chicken bcn ranch sub",  "6.95", null, 12);
        verifyParsedItem(iterator.next(), "-cheese add6in",  "0.50", null, 13);
        verifyParsedItem(iterator.next(), "1    -can - fresh value meal $3.10", null, null, 14);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "10.55",18);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 128 127 324",9);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/3",5);
    }

    @Test
    public void receipt_2015_10_10_15_12_34()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_15_12_34, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("subway", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1    6\" cold cut combo sub",  "4.35", null, 12);
        verifyParsedItem(iterator.next(), "1    21oz fountain drink 21fnt",  "2.00", null, 13);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "6.35",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/3",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 128 127 324",9);
    }

    @Test
    public void receipt_2015_10_10_15_16_46()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_15_16_46, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("subway", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 12\" meatball sub",  "7.35", null, 12);
        verifyParsedItem(iterator.next(), "1    -can - fresh value meal",  "2.70", null, 13);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "10.05",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/5",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 128 127 324",9);
    }

    @Test
    public void receipt_2015_10_10_15_17_51()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_15_17_51, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("subway", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "m    customer receipt", null, null, 8);
        verifyParsedItem(iterator.next(), "1    12\" rst chicken sub",  "9.00", null, 12);
        verifyParsedItem(iterator.next(), "1    21oz fountain drink 21fnt $2.00", null, null, 13);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "11.00",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/4",5);
    }

    @Test
    public void receipt_2015_10_10_15_20_19()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_10_15_20_19, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("subway", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1    chicken teriyaki salad",  "8.00", null, 12);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "8.00",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/4",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 128 127 324",9);
    }

    @Test
    public void receipt_2015_10_17_16_54_00()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_17_16_54_00, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("subway", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "2 12\" veggie delite sub",  "12.00", null, 12);
        verifyParsedItem(iterator.next(), "2    bottled water",  "4.60", null, 13);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "16.60",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/16",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 128 127 324",9);
    }

    @Test
    public void receipt_2015_10_17_16_56_39()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_17_16_56_39, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("subway", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1 12\" chickn strips sub",  "9.00", null, 12);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "9.00",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/11",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 128 127 324",9);
    }

    @Test
    public void receipt_2015_10_17_16_58_41()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_17_16_58_41, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("subway", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1    6\" cold cut combo sub",  "4.35", null, 12);
        verifyParsedItem(iterator.next(), "1    -can - fresh value meal",  "2.70", null, 13);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "7.05",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/11",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 128 127 324",9);
    }

    @Test
    public void receipt_2015_10_17_16_58_55()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_10_17_16_58_55, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("subway", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "1    6\" cold cut combo sub",  "4.35", null, 12);
        verifyParsedItem(iterator.next(), "1    16oz fountain drink 16fnt $1.70", null, null, 13);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "6.05",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/11",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 128 '77 3?a",9);
    }


}
