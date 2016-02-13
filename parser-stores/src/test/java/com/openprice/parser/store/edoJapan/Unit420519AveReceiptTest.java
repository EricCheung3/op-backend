package com.openprice.parser.store.edoJapan;

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
        ////printResult(receipt);

        // verify result of items
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyParsedItem(iterator.next(), "1 premium drink", "2.69", null, 11);

        // verify parsed fields
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "3.01",18);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine2, "19 ave",7);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressCity, "nisku",3);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst       $0.14",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/1",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "order #195",5);

    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_07_03_16_51_34.jpg.haipeng.txt")
    private Resource sampleEdoJapan_2015_07_03_16_51_34;

    @Test
    public void testEdoJapan_2015_07_03_16_46_11() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_07_03_16_51_34, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyParsedItem(iterator.next(), "1 #5 chicken &beef", "8.59", null, 16);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "order #394",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "9.02",20);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "5015",11);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressCity, "edmonton",6);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst        $0.43",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/12",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "trans#: 1017394              serv : cashier",10);
    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_53_28.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_53_28;

    @Test
    public void testEdoJapan_2015_10_10_14_53_28() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_53_28, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        ////printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyParsedItem(iterator.next(), "1    sizzling shrimp", "9.89", null, 14);
        verifyParsedItem(iterator.next(), "1    sub brown rice", "0.49", null, 15);
        verifyParsedItem(iterator.next(), "1    chicken", "7.99", null, 16);
        verifyParsedItem(iterator.next(), "1    sub brown rice", "0.49", null, 17);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "19.80",23);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "5015",10);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressCity, "edmonton",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst       $0.94",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-437-3363",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "trans#: 16555    serv: 100",9);
    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_53_32.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_53_32;

    @Test
    public void testEdoJapan_2015_10_10_14_53_32() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_53_32, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        ////printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyParsedItem(iterator.next(), "1 kids chicken & noodle", "5.39", null, 12);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "5.80",18);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "5015",10);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressCity, "edmonton",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst            $0.28",16);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-437-3363",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "trans#: 16597    serv: 100",9);

    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_53_56.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_53_56;

    @Test
    public void testEdoJapan_2015_10_10_14_53_56() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_53_56, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        ////printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyParsedItem(iterator.next(), "1 sizzling shrimp", "9.89", null, 14);
        verifyParsedItem(iterator.next(), "1 combo reg pop", "1.89", null, 15);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "12.35",22);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "5015",10);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressCity, "edmonton",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst           $0.59",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-437-3363",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "trans#: 16337    serv: 300",9);
    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_54_17.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_54_17;

    @Test
    public void testEdoJapan_2015_10_10_14_54_17() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_54_17, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        ////printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyParsedItem(iterator.next(), "1 chicken", "7.99", null, 14);
        verifyParsedItem(iterator.next(), "1 combo reg pop", "1.89", null, 15);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "10.37",21);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "5015",10);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressCity, "edmonton",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst       $0.49",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-437-3363",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "trans#: 16709    serv: 100",9);
    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_55_19.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_55_19;

    @Test
    public void testEdoJapan_2015_10_10_14_55_19() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_55_19, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        ////printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyParsedItem(iterator.next(), "1 chicken & shrimp", "11.58", null, 14);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "11.58",16);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "10016",22);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressCity, "edmonton",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst         $0.58",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "trans#: 16752    serv: 100",9);

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
        ////printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyParsedItem(iterator.next(), "2    regular pop", "4.18", null, 14);
        verifyParsedItem(iterator.next(), "1    beef & shrimp bento", "14.38", null, 15);
        verifyParsedItem(iterator.next(), "1    beef yakisoba bento", "11.79", null, 16);
        verifyParsedItem(iterator.next(), "1    2 spring roll", "2.69", null, 17);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "34.70",24);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "5015",10);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressCity, "edmonton",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst       $1.65",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-437-3363",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "trans#: 16945    serv: 300",9);
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
        verifyParsedItem(iterator.next(), "1    beef", "8.09", null, 14);
        verifyParsedItem(iterator.next(), "1    kids chicken & noodle", "5.39", null, 15);
        verifyParsedItem(iterator.next(), "1    combo reg pop", "1.89", null, 18);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "16.30",25);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "5015",10);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressCity, "edmonton",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst        $0.78",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-437-3363",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "trans#: 16591    serv: 100",9);
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
        verifyParsedItem(iterator.next(), "1 chicken", "7.99", null, 14);
        verifyParsedItem(iterator.next(), "1 sub brown rice", "0.49", null, 15);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "8.90",21);
        //TODO 5015 is year. branch recognition need to be improved
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "5015",10);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressCity, "edmonton",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst         $0.42",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-437-3363",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/8",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "trans#: 15571    serv: 100",9);
    }

    @Value("classpath:/testFiles/EdoJapan/Unit420519Ave/2015_10_10_14_57_53.jpg.txt")
    private Resource sampleEdoJapan_2015_10_10_14_57_53;

    @Test
    public void testEdoJapan_2015_10_10_14_57_53() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleEdoJapan_2015_10_10_14_57_53, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        ////printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        //TODO: noisy
        verifyParsedItem(iterator.next(), "t a k e", ".0", null, 8);
        verifyParsedItem(iterator.next(), "quan", ".0", null, 12);
        verifyParsedItem(iterator.next(), "1    bottled water", "2.29", null, 14);
        verifyParsedItem(iterator.next(), "1    salmon & shrimp", "10.99", null, 17);
        verifyParsedItem(iterator.next(), "1    sub brown rice", "0.49", null, 18);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "14.60",24);
        //TODO 5015 is not a branch
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "5015",10);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressCity, "edmonton",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst          $0.70",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-437-3363",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/10/5",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "trans#: 15577    serv: 300",9);
    }


}
