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
import com.openprice.parser.ParsedReceiptImpl;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.data.Item;
import com.openprice.parser.data.ValueLine;
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

        ParsedReceiptImpl receipt = simpleParser.parse(receiptLines);
        printResult(receipt);

        // verify result of items
        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "1 premium drink", "2.69", "");

        // verify parsed fields
        Map<ReceiptFieldType, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getValue(), "3.01");
        assertEquals("2015/2/1", fieldValues.get(ReceiptFieldType.Date).getValue());

        //TODO: these are not correct yet. branch finding is wrong.
        //        assertEquals("edmonton", fieldValues.get(ReceiptField.AddressCity).getValue());
        //        assertEquals("0.14", fieldValues.get(ReceiptField.GstAmount).getValue());
        //        assertEquals("122977044", fieldValues.get(ReceiptField.GstNumber).getValue());

    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_07_03_16_51_34.jpg.haipeng.txt")
    private Resource sampleEdoJapan_2015_07_03_16_51_34;

    @Test
    public void testEdoJapan_2015_07_03_16_46_11() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_07_03_16_51_34, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceiptImpl receipt = simpleParser.parse(receiptLines);
        printResult(receipt);

        //        verify result of items
        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "1 #5 chicken &beef", "8.59", "");
        //
        //        // verify parsed fields
        Map<ReceiptFieldType, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getValue(), "9.02");
        assertEquals("2015/2/12", fieldValues.get(ReceiptFieldType.Date).getValue());
        assertEquals("edmonton", fieldValues.get(ReceiptFieldType.AddressCity).getValue());

        //TODO: this is not good
        assertEquals("5015", fieldValues.get(ReceiptFieldType.AddressLine1).getValue());
        assertEquals(11, fieldValues.get(ReceiptFieldType.AddressLine1).getLine());//it thinks 2015 matches 5015.

        //TODO: these are not correct yet.
        //        assertEquals("0.43", fieldValues.get(ReceiptField.GstAmount).getValue());
        //        assertEquals("122977044", fieldValues.get(ReceiptField.GstNumber).getValue());
    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_53_28.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_53_28;

    @Test
    public void testEdoJapan_2015_10_10_14_53_28() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_53_28, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceiptImpl receipt = simpleParser.parse(receiptLines);
        printResult(receipt);

        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "1    sizzling shrimp", "9.89", "");
        verifyItemParsedValue(iterator.next(), "1    sub brown rice", "0.49", "");
        verifyItemParsedValue(iterator.next(), "1    chicken", "7.99", "");
        verifyItemParsedValue(iterator.next(), "1    sub brown rice", "0.49", "");

        Map<ReceiptFieldType, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getValue(), "19.80");
        assertEquals("2015/10/8", fieldValues.get(ReceiptFieldType.Date).getValue());
        assertEquals("780-437-3363", fieldValues.get(ReceiptFieldType.Phone).getValue());
        assertEquals("edmonton", fieldValues.get(ReceiptFieldType.AddressCity).getValue());
    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_53_32.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_53_32;

    @Test
    public void testEdoJapan_2015_10_10_14_53_32() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_53_32, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceiptImpl receipt = simpleParser.parse(receiptLines);
        printResult(receipt);

        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "1 kids chicken & noodle", "5.39", "");

        Map<ReceiptFieldType, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getValue(), "5.80");
        assertEquals("2015/10/8", fieldValues.get(ReceiptFieldType.Date).getValue());
        assertEquals("780-437-3363", fieldValues.get(ReceiptFieldType.Phone).getValue());
        assertEquals("edmonton", fieldValues.get(ReceiptFieldType.AddressCity).getValue());
    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_53_56.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_53_56;

    @Test
    public void testEdoJapan_2015_10_10_14_53_56() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_53_56, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceiptImpl receipt = simpleParser.parse(receiptLines);
        printResult(receipt);

        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "1 sizzling shrimp", "9.89", "");
        verifyItemParsedValue(iterator.next(), "1 combo reg pop", "1.89", "");

        Map<ReceiptFieldType, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getValue(), "12.35");
        assertEquals("2015/10/8", fieldValues.get(ReceiptFieldType.Date).getValue());
        assertEquals("780-437-3363", fieldValues.get(ReceiptFieldType.Phone).getValue());
        assertEquals("edmonton", fieldValues.get(ReceiptFieldType.AddressCity).getValue());
    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_54_17.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_54_17;

    @Test
    public void testEdoJapan_2015_10_10_14_54_17() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_54_17, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceiptImpl receipt = simpleParser.parse(receiptLines);
        printResult(receipt);

        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "1 chicken", "7.99", "");
        verifyItemParsedValue(iterator.next(), "1 combo reg pop", "1.89", "");

        Map<ReceiptFieldType, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getValue(), "10.37");
        assertEquals("2015/10/8", fieldValues.get(ReceiptFieldType.Date).getValue());
        assertEquals("780-437-3363", fieldValues.get(ReceiptFieldType.Phone).getValue());
        assertEquals("edmonton", fieldValues.get(ReceiptFieldType.AddressCity).getValue());
    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_55_19.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_55_19;

    @Test
    public void testEdoJapan_2015_10_10_14_55_19() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_55_19, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceiptImpl receipt = simpleParser.parse(receiptLines);
        printResult(receipt);

        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "1 chicken & shrimp", "11.58", "");

        Map<ReceiptFieldType, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getValue(), "11.58");
        assertEquals("2015/10/8", fieldValues.get(ReceiptFieldType.Date).getValue());
        assertEquals("edmonton", fieldValues.get(ReceiptFieldType.AddressCity).getValue());

        //TODO: Why it didn't detect the phone--because it didn't find the right branch.
        //(730) 437-3363
        //        assertEquals("780-437-3363", fieldValues.get(ReceiptField.Phone).getValue());
    }


    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_56_18.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_56_18;

    @Test
    public void testEdoJapan_2015_10_10_14_56_18() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_56_18, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceiptImpl receipt = simpleParser.parse(receiptLines);
        printResult(receipt);

        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "2    regular pop", "4.18", "");
        verifyItemParsedValue(iterator.next(), "1    beef & shrimp bento", "14.38", "");
        verifyItemParsedValue(iterator.next(), "1    beef yakisoba bento", "11.79", "");
        verifyItemParsedValue(iterator.next(), "1    2 spring roll", "2.69", "");

        Map<ReceiptFieldType, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getValue(), "34.70");
        assertEquals("2015/10/8", fieldValues.get(ReceiptFieldType.Date).getValue());
        assertEquals("edmonton", fieldValues.get(ReceiptFieldType.AddressCity).getValue());
        assertEquals("780-437-3363", fieldValues.get(ReceiptFieldType.Phone).getValue());
    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_56_27.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_56_27;

    @Test
    public void testEdoJapan_2015_10_10_14_56_27() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_56_27, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceiptImpl receipt = simpleParser.parse(receiptLines);
        printResult(receipt);

        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "1    beef", "8.09", "");
        verifyItemParsedValue(iterator.next(), "1    kids chicken & noodle", "5.39", "");
        verifyItemParsedValue(iterator.next(), "1    combo reg pop", "1.89", "");

        Map<ReceiptFieldType, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getValue(), "16.30");
        assertEquals("2015/10/8", fieldValues.get(ReceiptFieldType.Date).getValue());
        assertEquals("edmonton", fieldValues.get(ReceiptFieldType.AddressCity).getValue());
        assertEquals("780-437-3363", fieldValues.get(ReceiptFieldType.Phone).getValue());
    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_56_31.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_56_31;

    @Test
    public void testEdoJapan_2015_10_10_14_56_31() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_56_31, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceiptImpl receipt = simpleParser.parse(receiptLines);
        printResult(receipt);

        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "1 chicken", "7.99", "");
        verifyItemParsedValue(iterator.next(), "1 sub brown rice", "0.49", "");

        Map<ReceiptFieldType, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getValue(), "8.90");
        assertEquals("2015/10/8", fieldValues.get(ReceiptFieldType.Date).getValue());
        assertEquals("edmonton", fieldValues.get(ReceiptFieldType.AddressCity).getValue());
        assertEquals("780-437-3363", fieldValues.get(ReceiptFieldType.Phone).getValue());
    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_56_31.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_57_53;

    @Test
    public void testEdoJapan_2015_10_10_14_57_53() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_57_53, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceiptImpl receipt = simpleParser.parse(receiptLines);
        printResult(receipt);

        Iterator<Item> iterator = receipt.getItems().iterator();
        verifyItemParsedValue(iterator.next(), "1 chicken", "7.99", "");
        verifyItemParsedValue(iterator.next(), "1 sub brown rice", "0.49", "");

        Map<ReceiptFieldType, ValueLine> fieldValues = receipt.getFieldToValueMap();
        assertEquals(fieldValues.get(ReceiptFieldType.Total).getValue(), "8.90");
        assertEquals("2015/10/8", fieldValues.get(ReceiptFieldType.Date).getValue());
        assertEquals("edmonton", fieldValues.get(ReceiptFieldType.AddressCity).getValue());
        assertEquals("780-437-3363", fieldValues.get(ReceiptFieldType.Phone).getValue());
    }


}
