package com.openprice.parser.store.rcss;

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
public class RCSSSouthCommonReceiptTest extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testFiles/RCSS/SouthCommon/2015_02_09_13_15_25.jpg.henryHuang.txt")
    private Resource sampleRCSS_2015_02_09_13_15_25;

    @Test
    public void testRCSS_2015_02_09_13_15_25() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleRCSS_2015_02_09_13_15_25, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        //printResult(receipt);

        // verify result of items
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyParsedItem(iterator.next(), "rooster rice", "25.78", "rooster rice_05719777953", 5);
        verifyParsedItem(iterator.next(), "van bircrm mx", "3.50", "van bircrm mx", 7);
        verifyParsedItem(iterator.next(), "cm dis prncs cnd", "2.99", "cm dis prncs cnd", 8);
        verifyParsedItem(iterator.next(), "cm scribblers ha", "4.49", "cm scribblers ha", 9);
        verifyParsedItem(iterator.next(), "dh fc starter", "2.79", "dh fc starter", 10);
        verifyParsedItem(iterator.next(), "after bite kids", "4.99", "after bite kids", 12);
        verifyParsedItem(iterator.next(), "c&c astringent", "6.49", "c&c astringent", 14);
        verifyParsedItem(iterator.next(), "acne wash", "8.49", "acne wash", 15);
        verifyParsedItem(iterator.next(), "st ives vanilla", "2.98", "st ives vanilla", 16);
        verifyParsedItem(iterator.next(), "shower creme sof", "3.93", "shower creme sof", 17);
        verifyParsedItem(iterator.next(), "wlin cpck dcrtng", "5.04", "wlin cpck dcrtng", 19);
        verifyParsedItem(iterator.next(), "hp jar scraper", "5.00", "hp jar scraper", 20);
        verifyParsedItem(iterator.next(), "measure cup    gmrj", "4.29", null, 21);

        // verify parsed fields
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "9711 23 ave nw",29);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressCity, "edmonton",30);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "2.21",24);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",54);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "pproved",43);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card # ~~~~�~~~~~~~8017       exp ~~~~~",35);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",28);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "82.97",25);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/16",61);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh, low on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #          auth #      resp 001",37);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-490-3918",2);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "80.76",23);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "01549",72);


    }

    @Value("classpath:/testFiles/RCSS/SouthCommon/2015_04_04_21_28_21.jpg.jingwang.txt")
    private Resource sampleRCSS_2015_04_04_21_28_21;

    @Test
    public void testRCSS_2015_04_04_21_28_21() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleRCSS_2015_04_04_21_28_21, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        //printResult(receipt);

        // verify result of items
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyParsedItem(iterator.next(), "chap ic snde sd", "4.99", "chap ic snde sd_06294200366", 8);
        verifyParsedItem(iterator.next(), "shana naan", "1.99", "shana naan_06457970003", 9);
        verifyParsedItem(iterator.next(), "shana lacha para", "1.99", "shana lacha para_503003900298", 10);
        verifyParsedItem(iterator.next(), "wfz dmp rd rice", "8.97", "wfz dmp rd rice_690761966247", 11);
        verifyParsedItem(iterator.next(), "banana", "4.36", "banana_4011", 14);
        verifyParsedItem(iterator.next(), "tilapia whole", "8.87", "tilapia whole_2121080", 17);
        verifyParsedItem(iterator.next(), "fzn tilapia", "4.01", "fzn tilapia_2863070", 18);

        // verify parsed fields
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "supersto re",23);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "35.18",20);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "9711 23 ave nw",24);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/9/14",56);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressCity, "edmonton",25);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",49);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh, low on price",5);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #            auth #      resp 001",33);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",39);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-490-3918",4);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "35.18",19);
        //verifyParsedField(fieldValues, ReceiptFieldType.Account, "'' ******"'****~****~*'********���*�~*'�'",72);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "01549",71);
    }
}
