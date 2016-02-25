package com.openprice.parser.store.homedepot;

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
public class HomeDepotTest extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testFiles/HomeDepot/2015_04_04_21_27_51.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_27_51;

    @Value("classpath:/testFiles/HomeDepot/2015_04_04_21_31_22.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_31_22;

    @Value("classpath:/testFiles/HomeDepot/2015_04_04_21_31_30.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_31_30;

    @Value("classpath:/testFiles/HomeDepot/2015_04_04_21_41_11.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_41_11;

    @Value("classpath:/testFiles/HomeDepot/2015_04_04_21_55_38.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_55_38;

    @Value("classpath:/testFiles/HomeDepot/2015_04_04_22_05_59.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_05_59;

    @Value("classpath:/testFiles/HomeDepot/2015_04_04_22_06_06.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_06_06;

    @Value("classpath:/testFiles/HomeDepot/2015_04_04_22_07_29.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_07_29;

    @Value("classpath:/testFiles/HomeDepot/2015_04_04_22_10_24.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_10_24;

    @Value("classpath:/testFiles/HomeDepot/2015_04_04_22_10_33.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_10_33;

    @Value("classpath:/testFiles/HomeDepot/2015_06_04_21_18_40.jpg.henryHuang.txt")
    private Resource receipt_2015_06_04_21_18_40;

    @Value("classpath:/testFiles/HomeDepot/2015_07_02_18_04_36.jpg.henryHuang.txt")
    private Resource receipt_2015_07_02_18_04_36;

    @Value("classpath:/testFiles/HomeDepot/2015_07_02_18_13_46.jpg.henryHuang.txt")
    private Resource receipt_2015_07_02_18_13_46;

    @Value("classpath:/testFiles/HomeDepot/2015_07_21_10_57_49.jpg.henryHuang.txt")
    private Resource receipt_2015_07_21_10_57_49;

    @Test
    public void receipt_2015_04_04_21_27_51()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_27_51, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "the home depot    more saving.", null, null, 2);
        verifyParsedItem(iterator.next(), "deck bench <a>",  "98.25", null, 8);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/19",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "103.16",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card by completing a brief survey about",43);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "98.25",10);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst          4.91",11);

    }

    @Test
    public void receipt_2015_04_04_21_31_22()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_31_22, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "shelf supprt <a>",  "1.87", null, 7);
        verifyParsedField(fieldValues, ReceiptFieldType.Time, "time:",58);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "1.96",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/1/8",5);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card by completing a brief survey about",42);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "1.87",8);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst          0.09",9);
    }

    @Test
    public void receipt_2015_04_04_21_31_30()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_31_30, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "gift card xxxxxxxx9018 <a,u >",  "100.00", null, 9);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/10/30",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "100.00",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card by completing a brief survey about",51);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "100.00",11);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst / hst           0.00",12);

    }

    @Test
    public void receipt_2015_04_04_21_41_11()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_41_11, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "tank lever <a>",  "8.79", null, 9);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/3/15",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "9.23",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card by completing a brief survey about",47);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "8.79",10);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst                       0.44",11);

    }

    @Test
    public void receipt_2015_04_04_21_55_38()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_55_38, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "wall prot . <a>",  "2.97", null, 9);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/28",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "3.12",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card by completing a brief survey about",46);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "2.97",10);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst                      0.15",11);

    }

    @Test
    public void receipt_2015_04_04_22_05_59()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_05_59, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "gal brute",  "32.86", null, 8);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/1/8",5);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "34.50",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card!",25);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "32.86",9);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst / hst              -1.64",10);

    }

    @Test
    public void receipt_2015_04_04_22_06_06()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_06_06, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        //TODO HINGE was missing
        verifyParsedItem(iterator.next(), "-6.75    subtotal",  "15.06", null, 11);
        verifyParsedItem(iterator.next(), "pst/ost",  "0.00", null, 13);
        verifyParsedField(fieldValues, ReceiptFieldType.Time, "time:",49);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "15.82",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/7/20",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card by completing a brief survey about",32);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst             -0.76",12);

    }

    @Test
    public void receipt_2015_04_04_22_07_29()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_07_29, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "hdx suv mat <a>",  "12.98", null, 10);
        verifyParsedItem(iterator.next(), "gal brute <a>",  "32.86", null, 11);
        verifyParsedItem(iterator.next(), "hinge <a>",  "8.31", null, 12);
        verifyParsedItem(iterator.next(), "hinge <a>",  "6.75", null, 13);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/7/19",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "116.42",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card by com pleting a bri ef sur vey about",57);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "110.88",16);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/ hst                        5.54",17);

    }

    @Test
    public void receipt_2015_04_04_22_10_24()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_10_24, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "-    oij", null, null, 3);
        verifyParsedItem(iterator.next(), "dryw tape",  "5.14", null, 19);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/24",14);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "5.40",23);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card by camp 1et i ng a brief su,-vey about",42);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "5.14",20);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst / hst            -0.26",21);

    }

    @Test
    public void receipt_2015_04_04_22_10_33()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_10_33, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(6,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "50l storage <a>",  "24.00", null, 8);
        verifyParsedItem(iterator.next(), "clearshoebox <a>",  "3.88", null, 10);
        verifyParsedItem(iterator.next(), "dryw tape <a",  "5.14", null, 11);
        verifyParsedItem(iterator.next(), "dwfldretrac <a",  "14.99", null, 12);
        verifyParsedItem(iterator.next(), "6x11/4 crs <a",  "11.22", null, 13);
        //TODO price is in the next line
        verifyParsedItem(iterator.next(), "ul1/2\"x4'x8' <a>", null, null, 14);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/3/1",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "75.82",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card by compl eting a br ief survey about",50);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "72.21",16);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/ h st                     3.61",17);

    }

    @Test
    public void receipt_2015_06_04_21_18_40()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_06_04_21_18_40, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "key blank <a>",  "2.99", null, 8);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/5/29",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "3.14",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card by completing a brief survey about",43);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "2.99",9);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/ hst                         0.15",10);

    }

    @Test
    public void receipt_2015_07_02_18_04_36()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_02_18_04_36, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "ajd . flapper <a>",  "10.48", null, 8);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/22",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "11.00",12);
//        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card by comp 1et i ng a br i ef sur-vey about",42);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "10.48",9);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst / hst                          0.52",10);

    }

    @Test
    public void receipt_2015_07_02_18_13_46()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_02_18_13_46, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "caulk <-a",  "4.99", null, 6);
        verifyParsedItem(iterator.next(), "mono ultra <a>",  "4.97", null, 7);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/25",4);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "10.46",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card by camp 1et i ng a i)r i ef survey about",44);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "9.96",8);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst                      0.50",9);

    }

    @Test
    public void receipt_2015_07_21_10_57_49()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_21_10_57_49, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("homedepot", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "~more    doing,\"", null, null, 3);
//        verifyParsedItem(iterator.next(), "brent boyd", null, null, 6);
        verifyParsedItem(iterator.next(), "dim univ la <a>",  "19.98", null, 9);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/3/7",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "20.98",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card by comp 1at i ng a b ~i ef su~v.ey about",43);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "19.98",10);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst / hst                    1.00",11);

    }

}