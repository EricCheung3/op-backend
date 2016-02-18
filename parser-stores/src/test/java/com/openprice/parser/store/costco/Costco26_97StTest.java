package com.openprice.parser.store.costco;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.openprice.common.TextResourceUtils;
import com.openprice.parser.ChainRegistry;
import com.openprice.parser.ParsedField;
import com.openprice.parser.ParsedItem;
import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.ReceiptDataImpl;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.store.AbstractReceiptParserIntegrationTest;
import com.openprice.store.StoreChain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class Costco26_97StTest extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_02_11_22_51_51.jpg.hengshuai.txt")
    private Resource receipt_51_51;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_02_27_22_17_07.jpg.random.txt")
    private Resource receipt_17_07;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_10_54.jpg.jingwang.txt")
    private Resource receipt_10_54;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_11_01.jpg.jingwang.txt")
    private Resource receipt_11_01;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_11_07.jpg.jingwang.txt")
    private Resource receipt_11_07;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_12_51.jpg.jingwang.txt")
    private Resource receipt_12_51;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_13_05.jpg.jingwang.txt")
    private Resource receipt_13_05;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_14_29.jpg.jingwang.txt")
    private Resource receipt_14_29;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_14_36.jpg.jingwang.txt")
    private Resource receipt_14_36;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_14_43.jpg.jingwang.txt")
    private Resource receipt_14_43;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_16_53.jpg.jingwang.txt")
    private Resource receipt_16_53;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_17_00.jpg.jingwang.txt")
    private Resource receipt_17_00;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_17_07.jpg.jingwang.txt")
    private Resource receipt_21_17_07;

    @Value("classpath:/testFiles/Costco/branch_2616_91st/2015_04_04_21_17_15.jpg.jingwang.txt")
    private Resource receipt_17_15;

    @Inject
    private ChainRegistry chainRegistry;

    @Test
    public void test_receipt_51_51_StoreChain() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_51_51, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        assertNotNull(chainRegistry);
        assertTrue(chainRegistry.getStoreChains().size()>0);
        log.debug(chainRegistry.getStoreChains().toString());
        final StoreChain chain=chainRegistry.findBestMatchedChain(ReceiptDataImpl.fromContentLines(receiptLines));
        assertNotNull(chain);
        assertEquals("costco", chain.getCode());
        log.debug(chain.getHeaderProperties().toString());
    }

    @Test
    public void test_receipt_51_51() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_51_51, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);

        //printResult(receipt);
        assertEquals("costco", receipt.getChainCode());

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
//        assertEquals(13,receipt.getItems().size());
        assertEquals(15,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "ks ff women", "12.999", null, 7);
        verifyParsedItem(iterator.next(), "tpd/forhula", "3.009", null, 8);
        verifyParsedItem(iterator.next(), "materna 140$", "18.999", null, 9);
        verifyParsedItem(iterator.next(), "ks ff women", "12.999", null, 10);
        verifyParsedItem(iterator.next(), "tpd/formula", "3.009", null, 11);
        verifyParsedItem(iterator.next(), "970949 materna", "18.999", null, 12);
        verifyParsedItem(iterator.next(), "ks ff women", "12.999", null, 13);
        verifyParsedItem(iterator.next(), "tpd/formula", "3.009", null, 14);
        verifyParsedItem(iterator.next(), "ks f.f. men", "12.999", null, 15);
        verifyParsedItem(iterator.next(), "tpd/formula", "3.009", null, 16);
        verifyParsedItem(iterator.next(), "970949 materna", "18.999", null, 17);
        verifyParsedItem(iterator.next(), "ks f.f. hen", "12.999", null, 18);
        verifyParsedItem(iterator.next(), "tpd/formula", "3.009", null, 19);
        verifyParsedItem(iterator.next(), "k5 f.f. men", "12.999", null, 20);
        verifyParsedItem(iterator.next(), "tpd/formula", "3.009", null, 21);
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "116.91",22);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst =12147632 9rt",51);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/8",28);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*** cardholder copy ***",42);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "122.76",38);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "9",45);
    }

    @Test
    public void test_receipt_17_07() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_17_07, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);

        printResult(receipt);
        assertEquals("costco", receipt.getChainCode());

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(30,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "ckn/veg dump", "13.49", null, 7);
        verifyParsedItem(iterator.next(), "bananas", "1.89", null, 9);
        verifyParsedItem(iterator.next(), "globe grapes", "6.99", null, 10);
        verifyParsedItem(iterator.next(), "mini peppers", "5.99", null, 11);
        verifyParsedItem(iterator.next(), "yel. potates", "5.99", null, 12);
        verifyParsedItem(iterator.next(), "danactive", "8.79", null, 13);
        verifyParsedItem(iterator.next(), "blueberries", "5.69", null, 16);
        verifyParsedItem(iterator.next(), "raspberries", "4.99", null, 17);
        verifyParsedItem(iterator.next(), "cracker cut", "13.99", null, 18);
        verifyParsedItem(iterator.next(), "oz spinach", "3.99", null, 19);
        verifyParsedItem(iterator.next(), "spring mix", "3.99", null, 20);
        verifyParsedItem(iterator.next(), "pld carrots", "6.99", null, 21);
        verifyParsedItem(iterator.next(), "sweet corn", "8.99", null, 22);
        verifyParsedItem(iterator.next(), "broccolette", "7.59", null, 23);
        verifyParsedItem(iterator.next(), "lamb leg", "29.20", null, 24);
        verifyParsedItem(iterator.next(), "strawberries", "5.19", null, 25);
        verifyParsedItem(iterator.next(), "green beans", "4.99", null, 26);
        verifyParsedItem(iterator.next(), "avocados 5ct", "6.99", null, 27);
        verifyParsedItem(iterator.next(), "k.s. almonds", "19.99", null, 28);
        verifyParsedItem(iterator.next(), "158 milk 2%", "4.54", null, 29);
        verifyParsedItem(iterator.next(), "mini cukes", "4.99", null, 32);
        verifyParsedItem(iterator.next(), "mini babybel", "10.89", null, 33);
        verifyParsedItem(iterator.next(), "pork siderib", "25.29", null, 34);
        verifyParsedItem(iterator.next(), "beef patties", "22.84", null, 35);
        verifyParsedItem(iterator.next(), "split wing", "18.80", null, 36);
        verifyParsedItem(iterator.next(), "salmon filet", "19.91", null, 37);
        verifyParsedItem(iterator.next(), ".5 dz eggs", "8.69", null, 38);
        verifyParsedItem(iterator.next(), ".5 dz eggs", "8.69", null, 39);
        verifyParsedItem(iterator.next(), "tropics", "8.49", null, 40);
        verifyParsedItem(iterator.next(), "ks bath 30**", "14.999", null, 43);

        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*** cardhold  er copy ***",64);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "315.13",60);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst=121'1 76329rt",72);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.75",45);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/18",69);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "30",67);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "314.38",44);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "costc o # 258",52);
    }

    @Test
    public void test_receipt_10_54() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_10_54, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);

        printResult(receipt);
        assertEquals("costco", receipt.getChainCode());

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(0,receipt.getItems().size());//TODO there is an item there, but no price. forgivable
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "22.58",10);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "1.15",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "24.13",23);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "costco # 2~8",18);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst ; 12 1 '1 76 32 9rt",32);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/6/24",29);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "2",27);
    }

    @Test
    public void test_receipt_11_01() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_11_01, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);

        printResult(receipt);
        assertEquals("costco", receipt.getChainCode());

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

        assertEquals(8,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "watermelon", "6.19", null, 6);
        verifyParsedItem(iterator.next(), "red grapes", "8.99", null, 7);
        verifyParsedItem(iterator.next(), "blueberries", "4.89", null, 8);
        verifyParsedItem(iterator.next(), "blueberries", "4.89", null, 9);
        verifyParsedItem(iterator.next(), "458 milk 2%", "4.54", null, 10);
        verifyParsedItem(iterator.next(), "blackberries", "3.99", null, 13);
        verifyParsedItem(iterator.next(), "spinach", "2.99", null, 14);
        verifyParsedItem(iterator.next(), "pork tender", "30.18", null, 15);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/1",37);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gs t=121\"1 76329 rt",40);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "66.98",29);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "costco # 258",22);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*** cardholder copy ***",33);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "8",35);
    }

    @Test
    public void test_receipt_11_07() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_11_07, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);

        printResult(receipt);
        assertEquals("costco", receipt.getChainCode());

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "376863 sectional", null, null, 6);//TODO the price  1,599.99 G is not parsed
        verifyParsedItem(iterator.next(), "u shapd desk", "359.999", null, 7);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "98.00",9);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "1959.98",8);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "2",27);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst;121.q76329rt",33);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "2057.98",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/7/25",29);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "costco # 258",16);
    }

    @Test
    public void test_receipt_12_51() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_12_51, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);

        printResult(receipt);
        assertEquals("costco", receipt.getChainCode());

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

        assertEquals(11,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "ks olive oil", "29.99", null, 6);
        verifyParsedItem(iterator.next(), "pork tender", "26.32", null, 7);
        verifyParsedItem(iterator.next(), "pork siderib", "25.34", null, 8);
        verifyParsedItem(iterator.next(), "strawberries", "4.99", null, 9);
        verifyParsedItem(iterator.next(), "red grapes", "7.89", null, 10);
        verifyParsedItem(iterator.next(), "clementines", "7.99", null, 11);
        verifyParsedItem(iterator.next(), "grape tomato", "4.59", null, 12);
        verifyParsedItem(iterator.next(), "blueberries", "4.99", null, 13);
        verifyParsedItem(iterator.next(), "duster 24", "18.799", null, 14);
        verifyParsedItem(iterator.next(), "roti chicken", "7.999", null, 16);
        verifyParsedItem(iterator.next(), "avocado oil", "12.49", null, 17);

        verifyParsedField(fieldValues, ReceiptFieldType.Total, "146.53",36);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "1.16",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "costco # 258",27);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "145.37",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*** cardholder copy ***",40);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst=121'1 76329rt",49);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/24",45);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "11",43);
    }

    @Test
    public void test_receipt_13_05() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_13_05, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);

        printResult(receipt);
        assertEquals("costco", receipt.getChainCode());

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(12,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "watermelon", "6.99", null, 3);
        verifyParsedItem(iterator.next(), "milk 2%", "4.54", null, 4);
        verifyParsedItem(iterator.next(), "oatmeal rts", "12.69", null, 9);
        verifyParsedItem(iterator.next(), "ckn/veg dump", "12.79", null, 12);
        verifyParsedItem(iterator.next(), "31/40 shrimp", "21.99", null, 14);
        verifyParsedItem(iterator.next(), "clementines", "7.99", null, 15);
        verifyParsedItem(iterator.next(), "lamb leg", "27.83", null, 16);
        verifyParsedItem(iterator.next(), "short ribs", "28.59", null, 17);
        verifyParsedItem(iterator.next(), "salmon filet", "24.42", null, 18);
        verifyParsedItem(iterator.next(), "lhath cardig", "18.999", null, 19);
        verifyParsedItem(iterator.next(), "tpd/cardigan", "5.009", null, 20);//TODO this is a discount? price is negative
        verifyParsedItem(iterator.next(), "veggie tray", "13.999", null, 21);

        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "177.52",23);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/22",46);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "178.92",37);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "costco # 258",32);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "12",44);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gs t=121476329r t",49);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*** cardholder copy ***",41);
    }

    @Test
    public void test_receipt_14_29() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_14_29, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);

        printResult(receipt);
        assertEquals("costco", receipt.getChainCode());

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(7,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "flour 10kg", "5.99", null, 7);
        verifyParsedItem(iterator.next(), "biscotti", "9.99", null, 8);
        verifyParsedItem(iterator.next(), "458 milk 2%", "4.54", null, 10);
        verifyParsedItem(iterator.next(), "430 x-large eggs", "6.69", null, 13);
        verifyParsedItem(iterator.next(), "w cfl 6pk", "10.999", null, 14);
        verifyParsedItem(iterator.next(), "brickmaster", "16.999", null, 15);
        verifyParsedItem(iterator.next(), "gala apples", "5.99", null, 16);

        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "1.40",18);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "59.00",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "costco # 258",25);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "60.40",33);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/12/13",42);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*** cardholder copy ***",37);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "7",40);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst= 121'1763 29rt",45);
    }

    @Test
    public void test_receipt_14_36() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_14_36, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);

        printResult(receipt);
        assertEquals("costco", receipt.getChainCode());

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(6,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "grape tomato", "4.59", null, 6);
        verifyParsedItem(iterator.next(), "pumpkin/ gran", "8.99", null, 7);
        verifyParsedItem(iterator.next(), "458 milk 2%", "4.54", null, 9);
        verifyParsedItem(iterator.next(), "snow tube", "69.999", null, 12);
        verifyParsedItem(iterator.next(), "casio px150", "599.999", null, 13);
        verifyParsedItem(iterator.next(), "mixed pepper", "5.89", null, 15);

        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "26.00",18);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "542.31",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "costco # 258",27);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "568.31",38);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/11/29",47);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*** cardholder copy ***",42);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "6",45);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst= 12 14 7632 9rt",50);
    }

    @Test
    public void test_receipt_14_43() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_14_43, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);

        printResult(receipt);
        assertEquals("costco", receipt.getChainCode());

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(8,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "girls costum", "19.979", null, 6);
        verifyParsedItem(iterator.next(), "red grapes", "7.79", null, 7);
        verifyParsedItem(iterator.next(), "peppers", "6.49", null, 8);
        verifyParsedItem(iterator.next(), "bananas", "1.69", null, 9);
        verifyParsedItem(iterator.next(), "458 milk 2%", "4.54", null, 10);
        verifyParsedItem(iterator.next(), "led lighting", "19.979", null, 13);
        verifyParsedItem(iterator.next(), "/40 shrimp i", "19.99", null, 14);
        verifyParsedItem(iterator.next(), "grape tomato", "4.49", null, 15);

        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "costco # 258",28);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "8",41);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "85.25",18);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst=1214763 29r t",46);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*** cardholder copy ***",39);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "87.25",34);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/10/25",43);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "2.00",19);
    }

    @Test
    public void test_receipt_16_53() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_16_53, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);

        printResult(receipt);
        assertEquals("costco", receipt.getChainCode());

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(21,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "costume", "22.999", null, 7);
        verifyParsedItem(iterator.next(), "ks cutlery", "13.999", null, 8);
        verifyParsedItem(iterator.next(), "ks olive oil", "26.79", null, 9);
        verifyParsedItem(iterator.next(), "bulk carrots", "5.49", null, 10);
        verifyParsedItem(iterator.next(), "mini cukes", "4.99", null, 11);
        verifyParsedItem(iterator.next(), "mixed pepper", "5.99", null, 16);
        verifyParsedItem(iterator.next(), "organic corn", "7.99", null, 17);
        verifyParsedItem(iterator.next(), "clementines", "8.99", null, 18);
        verifyParsedItem(iterator.next(), "cherry tomat", "5.49", null, 19);
        verifyParsedItem(iterator.next(), "green envy", "15.999", null, 20);
        verifyParsedItem(iterator.next(), "st. louis rb", "22.49", null, 23);
        verifyParsedItem(iterator.next(), "golden melon", "4.69", null, 24);
        verifyParsedItem(iterator.next(), "131    lamb leg", "40.56", null, 25);
        verifyParsedItem(iterator.next(), "77~i7 9 moon cake", "10.88", null, 26);
        verifyParsedItem(iterator.next(), "8 milk 2%", "4.54", null, 27);
        verifyParsedItem(iterator.next(), "58 milk 2%", "4.54", null, 30);
        verifyParsedItem(iterator.next(), "head gloves", "9.999", null, 33);
        verifyParsedItem(iterator.next(), "sp k van alm", "8.29", null, 34);
        verifyParsedItem(iterator.next(), "lamb chops", "20.49", null, 35);
        verifyParsedItem(iterator.next(), "salmon filet", "30.80", null, 36);
        verifyParsedItem(iterator.next(), "watermelon", "6.19", null, 37);

        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "costco # 258",46);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "21",57);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "282.91",38);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst=121476329rt",63);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*** cardholder copy ***",55);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "286.06",51);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/8",59);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "3.15",39);
    }

    @Test
    public void test_receipt_17_00() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_17_00, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);

        printResult(receipt);
        assertEquals("costco", receipt.getChainCode());

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(8,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "npl35 w/deal", "3.99", null, 8);
        verifyParsedItem(iterator.next(), "wg ladder 21", "133.999", null, 11);
        verifyParsedItem(iterator.next(), "watermelon", "6.99", null, 13);
        verifyParsedItem(iterator.next(), "458 milk 2%", "4.54", null, 14);
        verifyParsedItem(iterator.next(), "strawberries", "3.79", null, 17);
        verifyParsedItem(iterator.next(), "cherries", "7.99", null, 18);
        verifyParsedItem(iterator.next(), "strawberries", "3.79", null, 19);
        verifyParsedItem(iterator.next(), "large omega3", "9.99", null, 20);

        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "costco # 258",32);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "8",42);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "144.24",21);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst=121'l7 6329rt",48);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "149.19",37);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/7/2",44);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "4.95",22);
    }

    @Test
    public void test_receipt_21_17_07() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_21_17_07, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);

        printResult(receipt);
        assertEquals("costco", receipt.getChainCode());

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(5,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "ra lyn night", "199.999", null, 8);
        verifyParsedItem(iterator.next(), "ra lyn night", "199.999", null, 9);
        verifyParsedItem(iterator.next(), "table lamps", "84.999", null, 10);
        verifyParsedItem(iterator.next(), "cantaloupe", "3.29", null, 15);
        verifyParsedItem(iterator.next(), "mixed pepper", "5.99", null, 16);

        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "costco # 2:j8",25);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "6",34);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "514.24",17);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst=12147 6329rt",40);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "539.49",30);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/7/12",36);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "25.25",18);
    }

    @Test
    public void test_receipt_17_15() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_17_15, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);

        printResult(receipt);
        assertEquals("costco", receipt.getChainCode());

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

        assertEquals(19,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "/25 rw shp", "18.49", null, 9);
        verifyParsedItem(iterator.next(), "chkn burrito", "9.999", null, 10);
        verifyParsedItem(iterator.next(), "brownie bite", "8.49", null, 11);
        verifyParsedItem(iterator.next(), "large omega3", "9.99", null, 12);
        verifyParsedItem(iterator.next(), "gala apples", "8.99", null, 13);
        verifyParsedItem(iterator.next(), "458 milk 2%", "4.54", null, 14);
        verifyParsedItem(iterator.next(), "clfrnia roll", "7.999", null, 17);
        verifyParsedItem(iterator.next(), "strawberries", "3.79", null, 18);
        verifyParsedItem(iterator.next(), "strawberries", "3.79", null, 19);
        verifyParsedItem(iterator.next(), "ataulfo mang", "8.99", null, 20);
        verifyParsedItem(iterator.next(), "cherries", "7.99", null, 21);
        verifyParsedItem(iterator.next(), "green grapes", "8.99", null, 22);
        verifyParsedItem(iterator.next(), "green grapes", "8.99", null, 24);
        verifyParsedItem(iterator.next(), "grfen grapes", "8.99", null, 25);
        verifyParsedItem(iterator.next(), "brownie bite", "8.49", null, 27);
        verifyParsedItem(iterator.next(), "hc grk sandw", "9.999", null, 28);
        verifyParsedItem(iterator.next(), "clementines", "8.99", null, 30);
        verifyParsedItem(iterator.next(), "cucumbers", "2.99", null, 31);
        verifyParsedItem(iterator.next(), "537    slicedloaves", "9.99", null, 32);

        verifyParsedField(fieldValues, ReceiptFieldType.Total, "123.12",49);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/7/12",56);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst =121476329rt",60);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "costco # 258",43);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "15",54);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "121.82",34);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "1.30",35);
    }
}
