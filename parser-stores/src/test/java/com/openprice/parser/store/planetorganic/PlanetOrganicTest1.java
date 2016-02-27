package com.openprice.parser.store.planetorganic;

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
public class PlanetOrganicTest1 extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testfiles/planetorganic/branch_79_calgarytrail/2015_02_09_16_11_30.jpg.random.txt")
    private Resource receipt_2015_02_09_16_11_30;

    @Value("classpath:/testfiles/planetorganic/branch_79_calgarytrail/2015_02_09_23_44_46.jpg.random.txt")
    private Resource receipt_2015_02_09_23_44_46;

    @Value("classpath:/testfiles/planetorganic/branch_79_calgarytrail/2015_02_09_23_59_57.jpg.random.txt")
    private Resource receipt_2015_02_09_23_59_57;

    @Value("classpath:/testfiles/planetorganic/branch_79_calgarytrail/2015_02_10_23_40_56.jpg.random.txt")
    private Resource receipt_2015_02_10_23_40_56;

    @Value("classpath:/testfiles/planetorganic/branch_79_calgarytrail/2015_07_03_14_48_22.jpg.hengshuai.txt")
    private Resource receipt_2015_07_03_14_48_22;

    @Test
    public void receipt_2015_02_09_16_11_30()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_16_11_30, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("planetorganic", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(5,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "clerk: katrina",  "0.", null, 10);
//        verifyParsedItem(iterator.next(), "terminal:",  "01", null, 11);
        verifyParsedItem(iterator.next(), "fiji natural",  "3.99", null, 12);
        verifyParsedItem(iterator.next(), "fs roasted red",  "4.99", null, 14);
        verifyParsedItem(iterator.next(), "vegetarian meal",  "7.99", null, 16);
        verifyParsedItem(iterator.next(), "vegetarian meal",  "7.99", null, 18);
        verifyParsedItem(iterator.next(), "vegetarian t4eal",  "7.99", null, 20);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/17",9);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "account:          4000000747146",32);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "32.95",23);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "32.95",22);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst #809623663",8);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "32.95",28);
    }

    @Test
    public void receipt_2015_02_09_23_44_46()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_23_44_46, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("planetorganic", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(32,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "clerk: tlichelle",  "0.", null, 10);
//        verifyParsedItem(iterator.next(), "termina 1:",  "06", null, 11);
        verifyParsedItem(iterator.next(), "~ 1281 organic strawberry",  "5.79", null, 12);
        verifyParsedItem(iterator.next(), "! 1986 salted butter 454g",  "9.99", null, 13);
        verifyParsedItem(iterator.next(), "( 1894 honey bunny bbq",  "7.29", null, 14);
        verifyParsedItem(iterator.next(), "!!016 brown rice cacao",  "3.50", null, 16);
        verifyParsedItem(iterator.next(), "~ io 16 brown rice cocao",  "3.50", null, 18);
        verifyParsedItem(iterator.next(), "( 1328 maison orphee",  "12.29", null, 20);
        verifyParsedItem(iterator.next(), "( 1328 maison orphee",  "12.29", null, 22);
        verifyParsedItem(iterator.next(), "( 1328 maison orphee",  "12.29", null, 24);
        verifyParsedItem(iterator.next(), "~~i311 almond breeze",  "2.99", null, 26);
        verifyParsedItem(iterator.next(), "yves org whole gold",  "2.99", null, 28);
        verifyParsedItem(iterator.next(), "~1117 raincoast tc",  "6.69", null, 30);
        verifyParsedItem(iterator.next(), ": '003 bellissima roasted",  "4.29", null, 32);
        verifyParsedItem(iterator.next(), "( 12 40 yves org whole gold",  "2.99", null, 34);
        verifyParsedItem(iterator.next(), "( 1959 vortex 9.5 high",  "42.96", null, 36);
        verifyParsedItem(iterator.next(), "( 1020 neg bath salt",  "18.99", null, 39);
        verifyParsedItem(iterator.next(), "( 1235 under the weather",  "5.99", null, 41);
        verifyParsedItem(iterator.next(), "( 1235 under the weather",  "5.99", null, 43);
        verifyParsedItem(iterator.next(), "( 1019 alkabath energy",  "29.99", null, 45);
        verifyParsedItem(iterator.next(), "(11 00 ak nutrition silver",  "25.99", null, 47);
        verifyParsedItem(iterator.next(), "; ~650 earthbound farms",  "5.99", null, 49);
        verifyParsedItem(iterator.next(), "~ 1002 carrots 5lb bag",  "9.99", null, 51);
        verifyParsedItem(iterator.next(), "tomato beefsteak",  "8.50", null, 52);
        verifyParsedItem(iterator.next(), "(1115 strawberry 1lb",  "14.99", null, 54);
        verifyParsedItem(iterator.next(), "roasted veggie",  "7.26", null, 55);
        verifyParsedItem(iterator.next(), "beef marinara",  "10.37", null, 58);
        verifyParsedItem(iterator.next(), "veggie dipper large",  "5.99", null, 61);
        verifyParsedItem(iterator.next(), "\"368 wt pork brkfst",  "6.99", null, 62);
        verifyParsedItem(iterator.next(), "d6meatno-ax open meat no tax",  "14.69", null, 64);
        verifyParsedItem(iterator.next(), "top grass beef lean",  "8.80", null, 67);
        verifyParsedItem(iterator.next(), "bag fee",  "0.10", null, 70);
        verifyParsedItem(iterator.next(), "tetra 0 - 1l (milk",  "0.10", null, 71);
        verifyParsedItem(iterator.next(), "tetra 0 - 1l (milk",  "0.00", null, 73);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/8",9);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "317.07",77);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "310.57",75);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst #809623663",8);

    }

    @Test
    public void receipt_2015_02_09_23_59_57()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_23_59_57, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("planetorganic", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(9,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "clerk: ebrah im",  "0", null, 10);
//        verifyParsedItem(iterator.next(), "tt::rrninal:",  "05", null, 11);
        verifyParsedItem(iterator.next(), "rg mystic mango",  "4.29", null, 13);
        verifyParsedItem(iterator.next(), "~7017f.3 raspberry chia",  "4.29", null, 15);
        verifyParsedItem(iterator.next(), "alatfia grass",  "41.99", null, 17);
        verifyParsedItem(iterator.next(), "bl kamut bread", null, null, 19);
        verifyParsedItem(iterator.next(), "bl spelt yeast", null, null, 20);
        verifyParsedItem(iterator.next(), "bl sprouted", null, null, 22);
        verifyParsedItem(iterator.next(), "vb p",  "3.49", null, 24);
        //TODO this price parsing is wrong
        verifyParsedItem(iterator.next(), "-    glass",  "5000.10", null, 26);
        verifyParsedItem(iterator.next(), "glass 0 - 500m 1 rf",  "0.06", null, 28);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/12/14",9);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "76.50",32);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "74.19",30);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst #809b23663",8);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.",34);

    }

    @Test
    public void receipt_2015_02_10_23_40_56()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_10_23_40_56, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("planetorganic", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(11,receipt.getItems().size());
        //TODO why not detected as cashier?
        verifyParsedItem(iterator.next(), "c erk: james",  "0", null, 12);
//        verifyParsedItem(iterator.next(), "termina 1:",  "05", null, 13);
        verifyParsedItem(iterator.next(), "/22430230177 org raw. kbcha third",  "3.00", null, 14);
        verifyParsedItem(iterator.next(), "\\cjoo 179 org guava goddess",  "3.00", null, 16);
        verifyParsedItem(iterator.next(), "0/5~0~388581 gs mineral water",  "1.99", null, 18);
        verifyParsedItem(iterator.next(), "9b i 4~0tj00069    fentiman's",  "2.69", null, 20);
        verifyParsedItem(iterator.next(), "6u22    glass 0 - 500ml",  "0.10", null, 22);
        verifyParsedItem(iterator.next(), "glass 0 - 500ml rf",  "0.06", null, 24);
        verifyParsedItem(iterator.next(), "glass 0 - 500ml",  "0.10", null, 25);
        verifyParsedItem(iterator.next(), "b222    glass 0 - 500ml rf",  "0.06", null, 27);
        verifyParsedItem(iterator.next(), "glass 501ml - 1l",  "0.10", null, 28);
        verifyParsedItem(iterator.next(), "glass 501ml - 1l rf",  "0.06", null, 30);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/8",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "11.31",33);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "11.16",31);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst #809623663date",10);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "11.31",35);

    }

    @Test
    public void receipt_2015_07_03_14_48_22()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_03_14_48_22, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("planetorganic", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(13,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "fdrnon-t ojl    cl",  "i.0", null, 6);
//        verifyParsedItem(iterator.next(), "edmonton, ab t",  "6e.0", null, 8);
//        verifyParsedItem(iterator.next(), "term ina 1:",  "03", null, 14);
        verifyParsedItem(iterator.next(), "bag fee",  "0.10", null, 16);
        verifyParsedItem(iterator.next(), "sevnth diapers stg",  "13.99", null, 17);
        verifyParsedItem(iterator.next(), "silver bread macks",  "4.99", null, 19);
        verifyParsedItem(iterator.next(), "prnat wild rose",  "13.99", null, 21);
        verifyParsedItem(iterator.next(), "prnat wild rose",  "13.99", null, 23);
        verifyParsedItem(iterator.next(), "hu l2 badger sun cream",  "18.98", null, 25);
        verifyParsedItem(iterator.next(), "banana",  "1.54", null, 27);
        verifyParsedItem(iterator.next(), "spinach bunched",  "7.79", null, 29);
        verifyParsedItem(iterator.next(), "strawberries llb",  "4.99", null, 30);
        verifyParsedItem(iterator.next(), "carrots rainbow",  "4.19", null, 31);
        verifyParsedItem(iterator.next(), "beef 'n onion pizza",  "6.49", null, 33);
        verifyParsedItem(iterator.next(), "deli meal deal",  "9.99", null, 35);
        verifyParsedItem(iterator.next(), "farmfnt eggs lg",  "5.49", null, 36);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/4/16",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "cl erk: tyler                              store:                 edt1c",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "110.40",40);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "106.52",38);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "110.40",42);

    }


}