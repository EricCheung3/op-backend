package com.openprice.parser.store.sears;

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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class SearsTest1 extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testFiles/Sears/southgateEdmonton/2014_12_06_22_22_29.jpg.hengshuai.txt")
    private Resource receipt_2014_12_06_22_22_29;

    @Value("classpath:/testFiles/Sears/southgateEdmonton/2014_12_06_22_24_27.jpg.hengshuai.txt")
    private Resource receipt_2014_12_06_22_24_27;

    @Value("classpath:/testFiles/Sears/southgateEdmonton/2014_12_06_22_29_55.jpg.hengshuai.txt")
    private Resource receipt_2014_12_06_22_29_55;

    @Value("classpath:/testFiles/Sears/southgateEdmonton/2014_12_06_22_55_01.jpg.hengshuai.txt")
    private Resource receipt_2014_12_06_22_55_01;

    @Value("classpath:/testFiles/Sears/southgateEdmonton/2014_12_06_22_59_35.jpg.hengshuai.txt")
    private Resource receipt_2014_12_06_22_59_35;

    @Value("classpath:/testFiles/Sears/southgateEdmonton/2014_12_06_23_23_58.jpg.hengshuai.txt")
    private Resource receipt_2014_12_06_23_23_58;

    @Value("classpath:/testFiles/Sears/southgateEdmonton/2014_12_06_23_39_28.jpg.hengshuai.txt")
    private Resource receipt_2014_12_06_23_39_28;

    @Value("classpath:/testFiles/Sears/southgateEdmonton/2014_12_06_23_39_56.jpg.hengshuai.txt")
    private Resource receipt_2014_12_06_23_39_56;

    @Value("classpath:/testFiles/Sears/southgateEdmonton/2014_12_07_00_16_33.jpg.hengshuai.txt")
    private Resource receipt_2014_12_07_00_16_33;

    @Value("classpath:/testFiles/Sears/southgateEdmonton/2014_12_07_00_17_03.jpg.hengshuai.txt")
    private Resource receipt_2014_12_07_00_17_03;

    @Value("classpath:/testFiles/Sears/southgateEdmonton/2014_12_07_00_26_16.jpg.hengshuai.txt")
    private Resource receipt_2014_12_07_00_26_16;

    @Value("classpath:/testFiles/Sears/southgateEdmonton/2015_02_09_15_15_48.jpg.random.txt")
    private Resource receipt_2015_02_09_15_15_48;

    @Value("classpath:/testFiles/Sears/southgateEdmonton/2015_02_10_00_00_22.jpg.random.txt")
    private Resource receipt_2015_02_10_00_00_22;

    @Value("classpath:/testFiles/Sears/southgateEdmonton/2015_02_10_00_19_03.jpg.random.txt")
    private Resource receipt_2015_02_10_00_19_03;

    @Value("classpath:/testFiles/Sears/southgateEdmonton/2015_02_10_13_25_05.jpg.hengshuai.txt")
    private Resource receipt_2015_02_10_13_25_05;

    @Value("classpath:/testFiles/Sears/southgateEdmonton/2015_04_04_21_34_04.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_34_04;

    @Value("classpath:/testFiles/Sears/southgateEdmonton/2015_04_04_21_40_29.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_40_29;

    @Value("classpath:/testFiles/Sears/southgateEdmonton/2015_05_02_21_48_09.jpg.momingzhen159.txt")
    private Resource receipt_2015_05_02_21_48_09;

    @Value("classpath:/testFiles/Sears/southgateEdmonton/2015_07_02_10_46_12.jpg.random.txt")
    private Resource receipt_2015_07_02_10_46_12;

    @Value("classpath:/testFiles/Sears/southgateEdmonton/2015_07_03_14_38_35.jpg.hengshuai.txt")
    private Resource receipt_2015_07_03_14_38_35;

    @Value("classpath:/testFiles/Sears/southgateEdmonton/2015_07_03_15_22_32.jpg.hengshuai.txt")
    private Resource receipt_2015_07_03_15_22_32;

    @Value("classpath:/testFiles/Sears/southgateEdmonton/2015_07_18_18_15_16.jpg.fuqian.txt")
    private Resource receipt_2015_07_18_18_15_16;

    @Test
    public void receipt_2014_12_06_22_22_29()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2014_12_06_22_22_29, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sears", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(5,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "statement or for return o    r exc hange", null, null, 9);
        verifyParsedItem(iterator.next(), "sr boys,tmyhlfgr.outdr,khk",  "34.999", null, 20);
        verifyParsedItem(iterator.next(), "2:sc soar",  "14.009", null, 22);
        verifyParsedItem(iterator.next(), "srboys, tmyhlfgr.outor,khk",  "19.999", null, 26);
        verifyParsedItem(iterator.next(), "4:sfc savings 25%",  "5.009", null, 28);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst registr at ion i 104765698",44);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store reg tran empli    date                              time",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "6.30",32);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2013/2/3",51);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card ~:           ******* 4001",50);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "6.00",30);
    }

    @Test
    public void receipt_2014_12_06_22_24_27()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2014_12_06_22_24_27, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sears", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "kids, tckletoes,softshoe, n",  "13.999", null, 15);
        verifyParsedItem(iterator.next(), "2:sc soar",  "5.609", null, 17);
        verifyParsedItem(iterator.next(), "srboys,tmyhlfgr,outdr,khk",  "34.999", null, 20);
        verifyParsedItem(iterator.next(), "4:sc soar",  "14.009", null, 22);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst registration 8 104765698",39);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store reg tran empl#    date                          time",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "30.85",26);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2013/1/7",47);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card b            ************4001",46);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "29.38",24);

    }

    @Test
    public void receipt_2014_12_06_22_29_55()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2014_12_06_22_29_55, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sears", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "srboys,tmyhlfgr,outdr,khk",  "34.999", null, 17);
        verifyParsedItem(iterator.next(), "z:sc soar",  "14.009", null, 19);
        verifyParsedItem(iterator.next(), "srboys,tmyhlfgr,outor,khk",  "19.999", null, 23);
        verifyParsedItem(iterator.next(), "4:sfc savings 25%",  "5.009", null, 25);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst registration tt 104765698",41);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store reg tran empltt   date                               time",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "6.30",29);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2013/2/3",48);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card ~:          iiihiiii'i!h 4q01",47);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "6.00",27);
    }

    @Test
    public void receipt_2014_12_06_22_55_01()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2014_12_06_22_55_01, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sears", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "ib,2pk striped socks,nvy",  "1.999", null, 17);
        verifyParsedItem(iterator.next(), "ib,2pk striped socks, nvy",  "1.999", null, 20);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst registration u 104765698",32);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store reg tran empl#    date    time",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "4.18",24);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2013/5/19",39);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card 11        ************4001",38);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "3.98",22);
    }

    @Test
    public void receipt_2014_12_06_22_59_35()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2014_12_06_22_59_35, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sears", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "ig,2pc rsh guard st,zebra",  "22.00", null, 23);
        verifyParsedItem(iterator.next(), "essntl,obsdn,rvrsbl,shrt",  "9.99", null, 25);
        verifyParsedItem(iterator.next(), "b,7pk sock day of wk",  "4.99", null, 28);
        verifyParsedItem(iterator.next(), "b,navy shark print hat",  "12.00", null, 31);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst regi stration # 104765698",42);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store reg tran empltt   date                            time",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "51.43",34);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/7/7",49);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card u           ************4001",48);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "48.98",32);
    }

    @Test
    public void receipt_2014_12_06_23_23_58()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2014_12_06_23_23_58, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sears", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "srboys,tmyhlfgr,outdr,khk",  "19.999", null, 15);
        verifyParsedItem(iterator.next(), "2:sfc savings 25%",  "5.009", null, 17);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst / hst registration # 104765698",27);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store reg tran empl#    date   time",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "15.74",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2013/4/11",34);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card ~          iihjhjhiifh 4001",33);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "14.99",19);
    }

    @Test
    public void receipt_2014_12_06_23_39_28()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2014_12_06_23_39_28, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sears", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "tight,bamboo solid,nvy 12",  "2.999", null, 13);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2013/5/19",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "3.14",17);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/ ii t reliismalion u 104765698",24);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "2.99",15);

    }

    @Test
    public void receipt_2014_12_06_23_39_56()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2014_12_06_23_39_56, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sears", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "kids, boot, sorel, windsor",  "21.999", null, 18);
        verifyParsedItem(iterator.next(), "2:sfc savings 25%",  "5.509", null, 20);
        verifyParsedItem(iterator.next(), "terry pant,whlle gardenia",  "13.999", null, 23);
        verifyParsedItem(iterator.next(), "4:sfc savings 25%",  "3.509", null, 25);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "26.98",27);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card ~.           hlfhhihh400 1",49);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "28.33",29);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2003/3/2",50);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store reg tran empli    date               time",8);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst registration u 104765698",43);

    }

    @Test
    public void receipt_2014_12_07_00_16_33()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2014_12_07_00_16_33, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sears", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2014_12_07_00_17_03()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2014_12_07_00_17_03, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sears", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2014_12_07_00_26_16()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2014_12_07_00_26_16, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sears", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_02_09_15_15_48()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_15_15_48, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sears", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_02_10_00_00_22()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_10_00_00_22, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sears", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_02_10_00_19_03()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_10_00_19_03, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sears", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_02_10_13_25_05()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_10_13_25_05, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sears", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_34_04()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_34_04, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sears", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_04_04_21_40_29()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_40_29, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sears", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_05_02_21_48_09()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_05_02_21_48_09, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sears", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_02_10_46_12()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_02_10_46_12, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sears", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_03_14_38_35()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_03_14_38_35, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sears", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_03_15_22_32()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_03_15_22_32, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sears", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_18_18_15_16()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_18_18_15_16, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("sears", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }



}
