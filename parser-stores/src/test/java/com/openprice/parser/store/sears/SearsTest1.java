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
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "sr boys,tmyhlfgr.outdr,khk",  "34.999", null, 20);
        verifyParsedItem(iterator.next(), "2:sc soar",  "14.009", null, 22);
        verifyParsedItem(iterator.next(), "srboys, tmyhlfgr.outor,khk",  "19.999", null, 26);
        verifyParsedItem(iterator.next(), "4:sfc savings 25%",  "5.009", null, 28);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst registr at ion i 104765698",44);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store reg tran empli    date                              time",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "6.30",32);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2013/2/3",51);
//        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card ~:           ******* 4001",50);
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
//        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card ~          iihjhjhiifh 4001",33);
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
//        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card ~.           hlfhhihh400 1",49);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "28.33",29);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2013/2/3",50);
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
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "tight,bamboo solid,nvy 12",  "2.999", null, 16);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "2.99",18);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/1 1 t reliistrallon i! 104765698",27);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "3.14",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2013/5/19",8);

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
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "srboys,tmyhlfgr,outdr,khk",  "34.999", null, 18);
        verifyParsedItem(iterator.next(), "2:sc soar",  "14.009", null, 20);
        verifyParsedItem(iterator.next(), "srboys,tmyhlfgr,outdr,khk",  "19.999", null, 24);
        verifyParsedItem(iterator.next(), "4:sfc savings 25%",  "5.009", null, 26);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "6.00",28);
//        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card ~:            llthiltlltllhh4001",48);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "6.30",30);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2013/2/3",49);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store reg tran empl #    date                           time",7);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst / hst registration # 104765698",42);

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
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "tight,bamboo solid,nvy 12",  "2.999", null, 16);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "2.99",18);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/1 1 t registralion # 10h65698",27);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "3.14",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2013/5/19",9);

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
        assertEquals(18,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "pmprs bd e",  "33.33", null, 6);
        verifyParsedItem(iterator.next(), "dark sugar",  "2.47", null, 7);
        verifyParsedItem(iterator.next(), "tropicana",  "4.98", null, 8);
        verifyParsedItem(iterator.next(), "ab crf",  "0.07", null, 9);
        verifyParsedItem(iterator.next(), "1 milk",  "4.46", null, 11);
        verifyParsedItem(iterator.next(), "ab bev crf",  "0.07", null, 12);
        verifyParsedItem(iterator.next(), "ab dep milk",  "0.25", null, 13);
        verifyParsedItem(iterator.next(), "1 milk",  "4.46", null, 14);
        verifyParsedItem(iterator.next(), "ab bev crf",  "0.07", null, 15);
        verifyParsedItem(iterator.next(), "ab dep milk",  "0.25", null, 16);
        verifyParsedItem(iterator.next(), "ap sw pot ph",  "1.08", null, 17);
        verifyParsedItem(iterator.next(), "ba pr mgo ph",  "1.08", null, 18);
        verifyParsedItem(iterator.next(), "ap br pea ph",  "1.08", null, 19);
        verifyParsedItem(iterator.next(), "snack",  "0.97", null, 20);
        verifyParsedItem(iterator.next(), "pol 887ml ab",  "1.88", null, 21);
        verifyParsedItem(iterator.next(), "str carrots",  "0.84", null, 22);
        verifyParsedItem(iterator.next(), "str mix veg",  "0.84", null, 23);
        verifyParsedItem(iterator.next(), "str corn",  "0.84", null, 24);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "59.27",25);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval i 078662",30);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "sears          **** **** **** 7721 i 2",29);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "61.08",27);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/12/28",47);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137166199 rt 0001",39);

    }

    //TODO this is a noFrills receipt!
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
        assertEquals(7,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "nn all purpose f    r",  "4.79", null, 6);
        verifyParsedItem(iterator.next(), "ah ess 2x mtn rn    gr",  "2.47", null, 7);
        verifyParsedItem(iterator.next(), "chick dip hummas _.- r",  "0.99", null, 8);
        verifyParsedItem(iterator.next(), "nn rice    r",  "7.69", null, 11);
        verifyParsedItem(iterator.next(), "romaine heart    r",  "3.97", null, 13);
        verifyParsedItem(iterator.next(), "banana",  "3.39", null, 14);
        verifyParsedItem(iterator.next(), "(3)9    plastic bags    gr",  "0.15", null, 18);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.13",21);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "28.94",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",40);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "************************'*************",71);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "29.07",22);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/9",60);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 03405",69);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #           auth #      resp 001",34);

    }

    //TODO this is a no frills
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
        assertEquals(34,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "nn coleslaw drsg    r",  "3.49", null, 7);
        verifyParsedItem(iterator.next(), "nn liquid honey    r",  "8.88", null, 8);
        verifyParsedItem(iterator.next(), "pcbm granola rai    r",  "3.99", null, 9);
        verifyParsedItem(iterator.next(), "pc bm gran om fr    r",  "3.99", null, 10);
        verifyParsedItem(iterator.next(), "nn ext lrg, ea    r",  "2.89", null, 11);
        verifyParsedItem(iterator.next(), "l2l06570010028 beatrice 1% milk    nr",  "9.08", null, 12);
        verifyParsedItem(iterator.next(), "'c2l 44000865341 recycling    r",  "0.16", null, 14);
        verifyParsedItem(iterator.next(), "hghlnr fllt    r",  "3.97", null, 18);
        verifyParsedItem(iterator.next(), "rmh ez tin orig",  "6.47", null, 19);
        verifyParsedItem(iterator.next(), "nn jce orange",  "2.15", null, 20);
        verifyParsedItem(iterator.next(), "recycling",  "0.08", null, 21);
        verifyParsedItem(iterator.next(), "nn apple juice    r",  "2.15", null, 23);
        verifyParsedItem(iterator.next(), "recycling",  "0.08", null, 24);
        verifyParsedItem(iterator.next(), "c2l06820075015 astro balkan    r",  "3.94", null, 26);
        verifyParsedItem(iterator.next(), "nn rotini    r",  "1.00", null, 28);
        verifyParsedItem(iterator.next(), "nn 24 roll bt    gr",  "3.96", null, 29);
        verifyParsedItem(iterator.next(), "ryl fcl tissue    gr",  "3.97", null, 30);
        verifyParsedItem(iterator.next(), "nn pizza moz 800    r",  "9.47", null, 31);
        verifyParsedItem(iterator.next(), "phil crm cheese    r",  "5.00", null, 32);
        verifyParsedItem(iterator.next(), "c2l06340004050 ch bread ww    r",  "7.94", null, 35);
        verifyParsedItem(iterator.next(), "dital bread ww",  "2.47", null, 38);
        verifyParsedItem(iterator.next(), "ww sausage bun    r",  "2.47", null, 39);
        verifyParsedItem(iterator.next(), "garlic naan 5 pk    r",  "2.47", null, 40);
        verifyParsedItem(iterator.next(), "zabiha halal chi",  "1.84", null, 42);
        verifyParsedItem(iterator.next(), "red del 3lb    r",  "2.97", null, 44);
        verifyParsedItem(iterator.next(), "cucumber mini    r",  "3.77", null, 45);
        verifyParsedItem(iterator.next(), "onion green",  "0.44", null, 46);
        verifyParsedItem(iterator.next(), "cauliflower",  "3.67", null, 48);
        verifyParsedItem(iterator.next(), "parsley italian",  "0.97", null, 50);
        verifyParsedItem(iterator.next(), "tomato roma",  "2.58", null, 51);
        verifyParsedItem(iterator.next(), "pco car bby 2lb    r",  "3.47", null, 53);
        verifyParsedItem(iterator.next(), "(8)9    plastic bags    gr",  "0.40", null, 55);
        verifyParsedItem(iterator.next(), "neck nipples    gr",  "3.79", null, 58);
        verifyParsedItem(iterator.next(), "gerber nipples s    r",  "5.99", null, 59);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",83);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "119.73",63);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card # **~'***l+***9301",73);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "118.82",60);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/8",81);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store 03405               term z0340503c",69);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.91",61);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #          auth #      resp 001",77);

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
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "jkt,parka,ebony,buffalo",  "204.999", null, 16);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "204.99",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card u         llhh ii iiiii *4001",35);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "215.24",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/7",36);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store reg tran empl~    date       time",8);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst registration u 104765698",28);

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
        assertEquals(2,receipt.getItems().size());

        //TODO noise:
        verifyParsedItem(iterator.next(), "1:    1 9s0~6 aclounr", null, null, 21);
        verifyParsedItem(iterator.next(), "watch repair",  "5.719", null, 22);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "5.71",23);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "6.00",25);
        verifyParsedField(fieldValues, ReceiptFieldType.Time, "time:",1);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/5/31",0);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store reg tran emplu    date     time",9);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/h  st registrat ion u 104765698",33);

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
        assertEquals(5,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "sears mc club pts reompt n",  "20.00", null, 15);
        verifyParsedItem(iterator.next(), "top , multifloralprint ,r",  "14.999", null, 20);
        verifyParsedItem(iterator.next(), "3.alt soar",  "4.509", null, 22);
        verifyParsedItem(iterator.next(), "nb, drss,multi , flrl, wtrclr",  "24.999", null, 25);
        verifyParsedItem(iterator.next(), "5 : alt soar",  "7.509", null, 27);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "7.98",29);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/ hst regi stration i 10q765698",45);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "9.38",31);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "storl r eg tran empl i    date     time",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/7/19",9);
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
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "50 ml moisture surge",  "44.00", null, 22);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "44.00",23);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card #           * *******h~* 11 9 ;'",39);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "46.20",25);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/28",40);
//        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store reg tran empl    date    th1 e:",7);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hs t reg istration a 104765698",33);

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
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "dress, keyhole bltd mlnt",  "99.999", null, 17);
        verifyParsedItem(iterator.next(), "dress,prpl,empwaist,pleat",  "59.99", null, 19);
        verifyParsedItem(iterator.next(), "dress, cap slv, fiery red",  "99.999", null, 21);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "phone ( 403)225-8536",5);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "259.97",22);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card u          ************7098",39);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "272.97",24);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/19",40);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store reg tran empl u                 date           ttme",7);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst / hst registrati on u 104765698",33);

    }

    //TODO interesting: there are two dates not consitent.
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
        assertEquals(10,receipt.getItems().size());
        //TODO noise
        verifyParsedItem(iterator.next(), "d os    dock    p t c l<",  "up", null, 6);
        verifyParsedItem(iterator.next(), "r et ai n for c",  "0m.0", null, 14);
        verifyParsedItem(iterator.next(), "dos r    equ est id : 54020464 0605 /", null, null, 19);
        verifyParsedItem(iterator.next(), "or der ed from d    o s faci lity :", null, null, 20);
        verifyParsedItem(iterator.next(), "cu sto meroonifcui: d    iad", null, null, 21);
        verifyParsedItem(iterator.next(), "f1ddre ss:    122 st 48 a    ver",  "412", null, 22);
        verifyParsedItem(iterator.next(), "po stal cd: t6h",  ".45", null, 25);
        verifyParsedItem(iterator.next(), "kenmore, he tl washer, wht",  "499.959", null, 36);
        verifyParsedItem(iterator.next(), "2: buy more sav 5%",  "25.009", null, 39);
        verifyParsedItem(iterator.next(), "3 yr, washer pa",  "121.99", null, 42);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gs t/ hst regi stra tio n i 104765698",57);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store, reg tran empltt   date      time",28);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "phone :       780-328- 3076",26);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "626.79",45);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/5/1",64);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "c a rd tt",63);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "596.94",43);

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
        assertEquals(8,receipt.getItems().size());
        //TODO noise
        verifyParsedItem(iterator.next(), "re ta i n for compariso    n ljlill ~!fin i ll!\\", null, null, 5);
        verifyParsedItem(iterator.next(), "cotn, antrct.shrt set",  "11.99", null, 18);
        verifyParsedItem(iterator.next(), "dri - fit tee, gym red",  "6.99", null, 22);
        verifyParsedItem(iterator.next(), "glbl ftbl, tee,vivd blu",  "6.99", null, 25);
        verifyParsedItem(iterator.next(), "wvn , blk, ply, pant",  "9.99", null, 28);
        verifyParsedItem(iterator.next(), "wvn, blk, ply, pant",  "9.99", null, 31);
        verifyParsedItem(iterator.next(), "wvn, blk, ply, pant",  "9.99", null, 33);
        verifyParsedItem(iterator.next(), "backpack, spiderman, red/bl",  "9.97", null, 35);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "65.91",38);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "ca  r   du                 **** **ha in11 400 1",56);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "69.21",40);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/6/15",57);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store reg tran pl u -   date       time",8);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/ hst reg istra tion u                1 0 '1 /6~~ ~ p",48);

    }

    //TODO : item "      SKECHERS.LTWT , WALK. CHR/BL 63 .74 G" is missed
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
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "f~ tf ;~ in f(ll~    compar i son    ~j j",  "fh.0", null, 4);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/5/31",47);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "slore reg tran ~ m pl u dat e   ti me",7);
//        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gs t/hst regi stra ll on                     d 1 j  l7o~ 6 9 h",39);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "63.74",23);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "66.93",25);

    }



}
