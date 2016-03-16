package com.openprice.parser.store.canadiantire;

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
public class CanadianTireTest1 extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testfiles/canadiantire/2015_02_14_09_59_40.jpg.hengshuai.txt")
    private Resource receipt_2015_02_14_09_59_40;

    @Value("classpath:/testfiles/canadiantire/2015_02_14_09_59_51.jpg.hengshuai.txt")
    private Resource receipt_2015_02_14_09_59_51;

    @Value("classpath:/testfiles/canadiantire/2015_02_14_10_00_00.jpg.hengshuai.txt")
    private Resource receipt_2015_02_14_10_00_00;

    @Value("classpath:/testfiles/canadiantire/2015_04_04_21_37_11.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_37_11;

    @Value("classpath:/testfiles/canadiantire/2015_04_04_21_47_19.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_47_19;

    @Value("classpath:/testfiles/canadiantire/2015_04_04_21_52_10.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_52_10;

    @Value("classpath:/testfiles/canadiantire/2015_04_04_21_52_17.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_52_17;

    @Value("classpath:/testfiles/canadiantire/2015_04_04_21_52_24.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_52_24;

    @Value("classpath:/testfiles/canadiantire/2015_04_04_21_52_31.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_52_31;

    @Value("classpath:/testfiles/canadiantire/2015_04_04_21_52_39.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_52_39;

    @Value("classpath:/testfiles/canadiantire/2015_04_04_22_11_08.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_11_08;

    @Value("classpath:/testfiles/canadiantire/2015_04_04_22_17_07.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_17_07;

    @Value("classpath:/testfiles/canadiantire/2015_04_04_22_22_48.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_22_48;

    @Value("classpath:/testfiles/canadiantire/2015_05_02_21_56_51.jpg.momingzhen159.txt")
    private Resource receipt_2015_05_02_21_56_51;

    @Value("classpath:/testfiles/canadiantire/2015_07_02_18_13_23.jpg.henry_huang.txt")
    private Resource receipt_2015_07_02_18_13_23;

    @Value("classpath:/testfiles/canadiantire/2015_07_02_18_14_49.jpg.henry_huang.txt")
    private Resource receipt_2015_07_02_18_14_49;

    @Value("classpath:/testfiles/canadiantire/2015_07_03_15_23_05.jpg.hengshuai.txt")
    private Resource receipt_2015_07_03_15_23_05;

    @Value("classpath:/testfiles/canadiantire/2015_07_03_15_35_46.jpg.hengshuai.txt")
    private Resource receipt_2015_07_03_15_35_46;

    @Value("classpath:/testfiles/canadiantire/2015_07_21_10_49_03.jpg.henry_huang.txt")
    private Resource receipt_2015_07_21_10_49_03;

    @Value("classpath:/testfiles/canadiantire/2015_07_21_10_56_27.jpg.henry_huang.txt")
    private Resource receipt_2015_07_21_10_56_27;

    @Value("classpath:/testfiles/canadiantire/2015_07_21_10_57_34.jpg.henry_huang.txt")
    private Resource receipt_2015_07_21_10_57_34;

    @Test
    public void receipt_2015_02_14_09_59_40()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_14_09_59_40, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "053-1 332-8 tide lq 2x orig $",  "6.99", null, 13);
        verifyParsedItem(iterator.next(), "purex cold 2x 4 $",  "8.99", null, 15);
        verifyParsedItem(iterator.next(), "stk,strt hky 40 $",  "5.99", null, 17);
        verifyParsedItem(iterator.next(), "ministk&ball,cr $",  "9.99", null, 18);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "31.96",19);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store hours 8ah-10ph honda~ to saturda~",66);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst .reg 1139122352",68);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "33.56",22);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/13",27);
    }

    @Test
    public void receipt_2015_02_14_09_59_51()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_14_09_59_51, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "lindor hrt asst    $",  "77.94", null, 10);
        verifyParsedItem(iterator.next(), "lindor amour he    $",  "23.98", null, 12);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "101.92",13);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store hours 8ah-10ph honda~ to saturda~",50);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst.reg 1139122352",52);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "107.02",16);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/13",21);
    }

    @Test
    public void receipt_2015_02_14_10_00_00()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_14_10_00_00, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "lindor hrt asst    $",  "77.94", null, 11);
        verifyParsedItem(iterator.next(), "lindor amour he    $",  "23.98", null, 13);
        verifyParsedItem(iterator.next(), "c-type clamp 3\"    $",  "13.98", null, 15);
        verifyParsedItem(iterator.next(), "c-type clamp 2\"    $",  "19.96", null, 17);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "135.86",18);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store hours 8ah-10ph honday to saturday",67);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst .reg 1139122352",69);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "142.65",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/8",26);
    }

    @Test
    public void receipt_2015_04_04_21_37_11()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_37_11, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(5,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "set, titn 230pc $",  "64.99", null, 8);
        verifyParsedItem(iterator.next(), "met sl fl ms m6 $",  "1.50", null, 11);
        verifyParsedItem(iterator.next(), "m/s ph-ph ss 8- $",  "1.00", null, 13);
        verifyParsedItem(iterator.next(), "s/m ph- sd ss 14 $",  "1.50", null, 15);
        verifyParsedItem(iterator.next(), "ss lag 1/4x2    $",  "1.80", null, 17);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst. reg #139888598",64);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card expires : 2 august",24);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "70.79",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/1",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "74.33",21);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store hours 8ah- 10ph monday to satur day",62);
    }

    @Test
    public void receipt_2015_04_04_21_47_19()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_47_19, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "south park cen    tre", null, null, 2);
        verifyParsedItem(iterator.next(), "1x073-4880- 6 ccm floor pump    $",  "8.99", null, 8);
//        verifyParsedItem(iterator.next(), "reg: s",  "17.0", null, 9);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "8.99",12);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store hours 8ah- 10ph hondah to saturdah",56);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card expires: 11 september 2014",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "9.44",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/9/6",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst . reg #139888598",58);
    }

    @Test
    public void receipt_2015_04_04_21_52_10()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_52_10, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "- 1x073-1880- 6 ccm floor pump    $",  "8.99", null, 10);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst . reg #139888598",49);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "9.44",14);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store hours 8ah- 10ph honda~ to satur da~",47);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "8.99",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/9/6",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card expires: 7 september 2015",19);
    }

    @Test
    public void receipt_2015_04_04_21_52_17()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_52_17, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "ccm floor pump",  "8.99", null, 9);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "9.44",15);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "st ore hours 8am- 1()jm monday to saturday",58);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "8.99",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "refund card balance :          $ 84.59",17);//TODO wrong match
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/1",6);
    }

    @Test
    public void receipt_2015_04_04_21_52_24()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_52_24, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "orig trn", null, null, 8);
        verifyParsedItem(iterator.next(), "-1x046- 2652- 8 grpst.bro.eth.j    $",  "89.93", null, 11);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst . reg 1139888598",52);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "94.43",16);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store hours 8ah- 10ph honda~ to saturda~",50);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "89.93",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "refund card issu ed               $       94.03",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/1",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card expires : 2 august 2015",21);
    }

    @Test
    public void receipt_2015_04_04_21_52_31()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_52_31, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "orig trn", null, null, 8);
        verifyParsedItem(iterator.next(), "- 1x088- 0888- 0 offset umb. sut    $",  "159.99", null, 10);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "167.99",14);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "159.99",11);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst . reg #139888598",57);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "authorization # : 328518",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/1",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "ref # : 66026'130 0010010011 c",20);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store hours 8ah-10ph honda~ to saturda~",55);
    }

    @Test
    public void receipt_2015_04_04_21_52_39()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_52_39, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "- 1x046- 2652-8 grpst ,bro, eth,j    $",  "89.93", null, 10);
        verifyParsedItem(iterator.next(), "- 1x016- 2617-1 lvrst, br lgn e    $",  "35.93", null, 11);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "authorization #: 3'11771",23);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "132.15",16);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "125.86",13);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst . reg #139888598",61);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "st ore hours 8rh 10pm monday to saturday",59);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/1",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "ref #: 66026430 0010010011 c",22);
    }

    @Test
    public void receipt_2015_04_04_22_11_08()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_11_08, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "med duty corn b",  "7.79", null, 8);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst . reg    #13~8885~8",56);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "8.18",14);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store hours 8ah- 10ph hondaw to saturda~",54);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "7.79",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "refund card balance : $   2.38",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Saving, "(saved $   6.20)",9);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/1",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card expires: 2 augus",18);
    }

    @Test
    public void receipt_2015_04_04_22_17_07()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_17_07, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(7,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "brkly fusion sp $",  "49.99", null, 8);
        verifyParsedItem(iterator.next(), "liteup twilites $",  "20.93", null, 9);
        verifyParsedItem(iterator.next(), "pwrbait,nat wor $",  "5.49", null, 11);
        verifyParsedItem(iterator.next(), "rig jackfish    $",  "23.03", null, 13);
        verifyParsedItem(iterator.next(), "ldr wire 30lb 1 $",  "11.96", null, 15);
        verifyParsedItem(iterator.next(), "snkr bass 3/40z $",  "3.98", null, 17);
        verifyParsedItem(iterator.next(), "curlytail 2\"pmk $",  "4.49", null, 18);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "authorization # : 016752",31);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "125.86",22);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "119.87",19);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst .reg #139122352",74);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store hours 8rh- 10ph monday to saturda~",72);
        verifyParsedField(fieldValues, ReceiptFieldType.Saving, "<saved $ 9.06}",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/4/4",27);
//        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "ref # : 66026130 0010010011 c     .-m    15",29);
    }

    @Test
    public void receipt_2015_04_04_22_22_48()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_22_48, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "charcoal,lump    $",  "19.98", null, 10);
        verifyParsedItem(iterator.next(), "starter fluid",  "3.29", null, 11);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "24.43",16);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "23.27",13);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst.reg #139122352",65);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "authorization 1: 0:23126",23);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/4/4",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "ref 1: 66026430 0010010011 c",22);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "s tore hours 8am- l(fm monday to saturday",63);
    }

    @Test
    public void receipt_2015_05_02_21_56_51()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_05_02_21_56_51, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "black super spr s",  "12.99", null, 6);
        verifyParsedItem(iterator.next(), "ken 26x1 .25/1 .5 s",  "9.99", null, 7);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "24.13",11);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store hours 8ah-10ph honda~ to saturda~",52);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "22.98",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "ref 1 : 66026430 0010010011 c",17);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst.reg 1139122352",54);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "authorization 1: 05091b",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/5/1",4);

    }

    @Test
    public void receipt_2015_07_02_18_13_23()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_02_18_13_23, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_02_18_14_49()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_02_18_14_49, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_03_15_23_05()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_03_15_23_05, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_03_15_35_46()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_03_15_35_46, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_21_10_49_03()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_10_49_03, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_21_10_56_27()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_10_56_27, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }

    @Test
    public void receipt_2015_07_21_10_57_34()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_10_57_34, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("canadiantire", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

    }



}
