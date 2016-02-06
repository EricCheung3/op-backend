package com.openprice.parser.store.edoJapan;

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
public class Unit420519AveReceiptTest extends AbstractReceiptParserIntegrationTest {


    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_02_09_11_34_51.jpg.hengshuai.txt")
    private Resource sampleEdoJapan_2015_02_09_11_34_51;

    @Test
    public void testEdoJapan_2015_02_09_11_34_51() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_02_09_11_34_51, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        //printResult(receipt);

        // verify result of items
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "1 premium drink", "2.69", null, 11);

        // verify parsed fields
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getFieldValue(), "3.01");
        assertEquals("2015/2/1", fieldValues.get(ReceiptFieldType.Date).getFieldValue());

        //TODO: these are not correct yet. branch finding is wrong.
        //        assertEquals("edmonton", fieldValues.get(ReceiptField.AddressCity).getFieldValue());
        //        assertEquals("0.14", fieldValues.get(ReceiptField.GstAmount).getFieldValue());
        //        assertEquals("122977044", fieldValues.get(ReceiptField.GstNumber).getFieldValue());

    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_07_03_16_51_34.jpg.haipeng.txt")
    private Resource sampleEdoJapan_2015_07_03_16_51_34;

    @Test
    public void testEdoJapan_2015_07_03_16_46_11() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_07_03_16_51_34, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        //printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "1 #5 chicken &beef", "8.59", null, 16);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getFieldValue(), "9.02");
        assertEquals("2015/2/12", fieldValues.get(ReceiptFieldType.Date).getFieldValue());
        assertEquals("edmonton", fieldValues.get(ReceiptFieldType.AddressCity).getFieldValue());

        //TODO: this is not good
        assertEquals("5015", fieldValues.get(ReceiptFieldType.AddressLine1).getFieldValue());
        assertEquals(11, fieldValues.get(ReceiptFieldType.AddressLine1).getLineNumber());//it thinks 2015 matches 5015.

        //TODO: these are not correct yet.
        //        assertEquals("0.43", fieldValues.get(ReceiptField.GstAmount).getFieldValue());
        //        assertEquals("122977044", fieldValues.get(ReceiptField.GstNumber).getFieldValue());
    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_53_28.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_53_28;

    @Test
    public void testEdoJapan_2015_10_10_14_53_28() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_53_28, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        //printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "1    sizzling shrimp", "9.89", null, 14);
        verifyItemParsedValue(iterator.next(), "1    sub brown rice", "0.49", null, 15);
        verifyItemParsedValue(iterator.next(), "1    chicken", "7.99", null, 16);
        verifyItemParsedValue(iterator.next(), "1    sub brown rice", "0.49", null, 17);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getFieldValue(), "19.80");
        assertEquals("2015/10/8", fieldValues.get(ReceiptFieldType.Date).getFieldValue());
        assertEquals("780-437-3363", fieldValues.get(ReceiptFieldType.Phone).getFieldValue());
        assertEquals("edmonton", fieldValues.get(ReceiptFieldType.AddressCity).getFieldValue());
    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_53_32.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_53_32;

    @Test
    public void testEdoJapan_2015_10_10_14_53_32() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_53_32, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        //printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "1 kids chicken & noodle", "5.39", null, 12);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getFieldValue(), "5.80");
        assertEquals("2015/10/8", fieldValues.get(ReceiptFieldType.Date).getFieldValue());
        assertEquals("780-437-3363", fieldValues.get(ReceiptFieldType.Phone).getFieldValue());
        assertEquals("edmonton", fieldValues.get(ReceiptFieldType.AddressCity).getFieldValue());
    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_53_56.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_53_56;

    @Test
    public void testEdoJapan_2015_10_10_14_53_56() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_53_56, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        //printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "1 sizzling shrimp", "9.89", null, 14);
        verifyItemParsedValue(iterator.next(), "1 combo reg pop", "1.89", null, 15);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getFieldValue(), "12.35");
        assertEquals("2015/10/8", fieldValues.get(ReceiptFieldType.Date).getFieldValue());
        assertEquals("780-437-3363", fieldValues.get(ReceiptFieldType.Phone).getFieldValue());
        assertEquals("edmonton", fieldValues.get(ReceiptFieldType.AddressCity).getFieldValue());
    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_54_17.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_54_17;

    @Test
    public void testEdoJapan_2015_10_10_14_54_17() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_54_17, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        //printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "1 chicken", "7.99", null, 14);
        verifyItemParsedValue(iterator.next(), "1 combo reg pop", "1.89", null, 15);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getFieldValue(), "10.37");
        assertEquals("2015/10/8", fieldValues.get(ReceiptFieldType.Date).getFieldValue());
        assertEquals("780-437-3363", fieldValues.get(ReceiptFieldType.Phone).getFieldValue());
        assertEquals("edmonton", fieldValues.get(ReceiptFieldType.AddressCity).getFieldValue());
    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_55_19.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_55_19;

    @Test
    public void testEdoJapan_2015_10_10_14_55_19() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_55_19, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "1 chicken & shrimp", "11.58", null, 14);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getFieldValue(), "11.58");
        assertEquals("2015/10/8", fieldValues.get(ReceiptFieldType.Date).getFieldValue());
        assertEquals("edmonton", fieldValues.get(ReceiptFieldType.AddressCity).getFieldValue());

        //TODO: Why it didn't detect the phone--because it didn't find the right branch.
        //(730) 437-3363
        //        assertEquals("780-437-3363", fieldValues.get(ReceiptField.Phone).getFieldValue());
    }


    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_56_18.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_56_18;

    @Test
    public void testEdoJapan_2015_10_10_14_56_18() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_56_18, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        //printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "2    regular pop", "4.18", null, 14);
        verifyItemParsedValue(iterator.next(), "1    beef & shrimp bento", "14.38", null, 15);
        verifyItemParsedValue(iterator.next(), "1    beef yakisoba bento", "11.79", null, 16);
        verifyItemParsedValue(iterator.next(), "1    2 spring roll", "2.69", null, 17);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getFieldValue(), "34.70");
        assertEquals("2015/10/8", fieldValues.get(ReceiptFieldType.Date).getFieldValue());
        assertEquals("edmonton", fieldValues.get(ReceiptFieldType.AddressCity).getFieldValue());
        assertEquals("780-437-3363", fieldValues.get(ReceiptFieldType.Phone).getFieldValue());
    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_56_27.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_56_27;

    @Test
    public void testEdoJapan_2015_10_10_14_56_27() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_56_27, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        ////printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "1    beef", "8.09", null, 14);
        verifyItemParsedValue(iterator.next(), "1    kids chicken & noodle", "5.39", null, 15);
        verifyItemParsedValue(iterator.next(), "1    combo reg pop", "1.89", null, 18);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getFieldValue(), "16.30");
        assertEquals("2015/10/8", fieldValues.get(ReceiptFieldType.Date).getFieldValue());
        assertEquals("edmonton", fieldValues.get(ReceiptFieldType.AddressCity).getFieldValue());
        assertEquals("780-437-3363", fieldValues.get(ReceiptFieldType.Phone).getFieldValue());
    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_56_31.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_56_31;

    @Test
    public void testEdoJapan_2015_10_10_14_56_31() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_56_31, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        ////printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "1 chicken", "7.99", null, 14);
        verifyItemParsedValue(iterator.next(), "1 sub brown rice", "0.49", null, 15);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals("8.90", fieldValues.get(ReceiptFieldType.Total).getFieldValue());
        assertEquals("2015/10/8", fieldValues.get(ReceiptFieldType.Date).getFieldValue());
        assertEquals("edmonton", fieldValues.get(ReceiptFieldType.AddressCity).getFieldValue());
        assertEquals("780-437-3363", fieldValues.get(ReceiptFieldType.Phone).getFieldValue());
    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_56_31.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_57_53;

    @Test
    public void testEdoJapan_2015_10_10_14_57_53() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_57_53, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        ////printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "1 chicken", "7.99", null, 14);
        verifyItemParsedValue(iterator.next(), "1 sub brown rice", "0.49", null, 15);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals("8.90", fieldValues.get(ReceiptFieldType.Total).getFieldValue());
        assertEquals("2015/10/8", fieldValues.get(ReceiptFieldType.Date).getFieldValue());
        assertEquals("edmonton", fieldValues.get(ReceiptFieldType.AddressCity).getFieldValue());
        assertEquals("780-437-3363", fieldValues.get(ReceiptFieldType.Phone).getFieldValue());
    }


}
