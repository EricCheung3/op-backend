package com.openprice.parser.store.safeway;

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
public class SafewayTest extends AbstractReceiptParserIntegrationTest {
    @Value("classpath:/testfiles/safeway/southgate/2015_02_27_20_04_24.jpg.dongcui.txt")
    private Resource sampleReceipt1;

    @Value("classpath:/testfiles/safeway/phone/ElisFeb24.txt")
    private Resource receipt_Elis24;

    @Value("classpath:/testfiles/safeway/mgrk_elksey_cole/2014_12_06_22_36_54.txt")
    private Resource receipt_36_54;

    @Value("classpath:/testfiles/safeway/phone/csabaFeb24.txt")
    private Resource receipt_CsabaFeb24;

    @Value("classpath:/testfiles/safeway/phone/reka_March_14.txt")
    private Resource receipt_reka_March_14;

    @Test
    public void testReceipt1TheCommentedItemsAreAllGone() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleReceipt1, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyParsedItem(iterator.next(), "sevengrain salad", "7.199", "sevengrain salad", 8);
        verifyParsedItem(iterator.next(), "hot soup small", "2.999", "hot soup small_2113006680", 9);
        verifyParsedItem(iterator.next(), "for baby summer veg", "1.19", "for baby summer veg", 10);
        verifyParsedItem(iterator.next(), "samosas beef", "2.99", "samosas beef_24586100000", 11);
        verifyParsedItem(iterator.next(), "for baby carrots", "1.19", "for baby carrots", 12);

        // verify parsed fields
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "14.87",16);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "100a 5015",32);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/27",44);
        //TODO why sometimes pass but sometimes not pass?
//        verifyParsedField(fieldValues, ReceiptFieldType.AddressCity, "edmonton",2);
//        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "100a 5015",32);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "served by: sean s",5);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "817093735",4);
//        verifyParsedField(fieldValues, ReceiptFieldType.AddressCountry, "canada",52);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "14.36",14);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "number of items                         5",19);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.51",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/27",44);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "14.87",16);
//        verifyParsedField(fieldValues, ReceiptFieldType.StoreBranch, "safeway southgate",1);//TODO
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "cardholder",42);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",36);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-435-5132",3);

    }


    @Test
    public void testReceipt2TheCommentedItemsAreAllGone() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_36_54, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals(9,receipt.getItems().size());

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyParsedItem(iterator.next(), "danone strawberry", "5.87", "danone strawberry", 6);
        verifyParsedItem(iterator.next(), "iogo nomad drink", "2.199", "iogo nomad drink", 9);
        verifyParsedItem(iterator.next(), "pastry bulk", "1.299", "pastry bulk", 13);
        verifyParsedItem(iterator.next(), "wt    bulk minibits cook", "0.89", "wt    bulk minibits cook", 15);
        verifyParsedItem(iterator.next(), "cucumber", "1.29", "cucumber", 17);
        //TODO this is missed
//        verifyParsedItem(iterator.next(), "6 qty    corn on cob", "3.00", "6 qty    corn on cob", 21);
        verifyParsedItem(iterator.next(), "2 qty    organic avocados", "5.38", "2 qty    organic avocados", 24);
        verifyParsedItem(iterator.next(), "butter lettuce", "3.49", "butter lettuce", 25);
        verifyParsedItem(iterator.next(), "2 qty    organic strawberry", "8.98", "2 qty    organic strawberry", 26);
        verifyParsedItem(iterator.next(), "4\"rose w/hat pick", "6.999", "4\"rose w/hat pick", 28);

        final Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        //Edmonton matched "green onion"
        //TODO it founds the total saving!
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "account number ************6689",34);
//        verifyParsedField(fieldValues, ReceiptFieldType.AddressCity, "edmonton",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "refrig/frozen",5);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "your cashier today was jennifer",43);
//        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "817093735",2);//TODO
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "crf / recycling fee                       0 . 01 g",11);
//        verifyParsedField(fieldValues, ReceiptFieldType.AddressCountry, "canada",54);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "",49);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "author . ii : 05790z",35);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/5/12",38);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "41.88",32);
//        verifyParsedField(fieldValues, ReceiptFieldType.StoreBranch, "safeway",0);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "vf       mastercard                       41 . 88",33);
//        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-435-5132",1);//TODO wrong branch

    }


    @Test
    public void receipt_CsabaFeb24() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_CsabaFeb24, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        printResult(receipt);
        final Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(9,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "lucerne milk 2% 2l",  "3.49", "lucerne milk 2% 2l", 14);
        verifyParsedItem(iterator.next(), "lucerne milk 2% 2l",  "3.49", "lucerne milk 2% 2l", 18);
        verifyParsedItem(iterator.next(), "lucerne milk 2% 2l",  "3.49", "lucerne milk 2% 2l", 22);
        verifyParsedItem(iterator.next(), "lucerne milk 2% 2l",  "3.49", "lucerne milk 2% 2l", 26);
        verifyParsedItem(iterator.next(), "lucerne milk 2% 2l",  "3.0", "lucerne milk 2% 2l", 30);
        verifyParsedItem(iterator.next(), "lucerne milk 2% 2l",  "3.49", "lucerne milk 2% 2l", 33);
        verifyParsedItem(iterator.next(), "lucerne milk 2% 2l",  "3.49", "lucerne milk 2% 2l", 36);
        verifyParsedItem(iterator.next(), "lucerne milk 2% 2l",  "3.49", "lucerne milk 2% 2l", 39);
        verifyParsedItem(iterator.next(), "bread artisan french",  "1.29", "bread artisan french", 46);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",76);
//        verifyParsedField(fieldValues, ReceiptFieldType.Author, "auth # 093472                                                                     ref  00000224",75);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "phone. 780.435.5132",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "serve# by: sco 20",9);
//        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.00",51);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "27.012",52);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "27.02",50);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card mastercard                                                                   rcpt 3321000",72);
//        verifyParsedField(fieldValues, ReceiptFieldType.StoreBranch, "?                                    safeway i\",0);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "500",75);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "number of items                                                                                      10",55);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2016/2/23",74);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 817093735",8);
    }

    //TODO too much noise
    @Test
    public void receipt_reka_March_14() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_reka_March_14, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        printResult(receipt);
        final Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(22,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "3 qty sfy psta sce tom",  "5.97", null, 9);
        verifyParsedItem(iterator.next(), "2 qty plugrr btr u/s eur .",  "5.58", null, 11);
        verifyParsedItem(iterator.next(), "french bread",  "1.99", null, 17);
        verifyParsedItem(iterator.next(), "fj extra lean fres",  "4.99", null, 21);
        verifyParsedItem(iterator.next(), "pork sirln chop vp",  "7.45", null, 22);
        verifyParsedItem(iterator.next(), "pk tndr uh",  "10.60", null, 23);
        verifyParsedItem(iterator.next(), "s farms ckn brst",  "8.76", null, 24);
        verifyParsedItem(iterator.next(), "er ckn thighs",  "9.51", null, 25);
        verifyParsedItem(iterator.next(), "er ckn thighs",  "8.19", null, 26);
        verifyParsedItem(iterator.next(), "gizzard/hrts",  "2.75", null, 27);
        verifyParsedItem(iterator.next(), "gizzard/hrts",  "2.72", null, 28);
        verifyParsedItem(iterator.next(), "ut    bananas",  "1.09", null, 33);
        verifyParsedItem(iterator.next(), "1.60 lb @ $1.49 /lb",  "2.38", null, 34);
        verifyParsedItem(iterator.next(), "cucumber", null, "cucumber", 37);
        verifyParsedItem(iterator.next(), "7.79 lb @ $1.29 /lb",  "10.05", null, 38);
        verifyParsedItem(iterator.next(), "1    22 lb @ $1.99 /lb",  "2.43", null, 40);
        verifyParsedItem(iterator.next(), "ut    gala apples", null, null, 41);
        verifyParsedItem(iterator.next(), "2    46 lb @ $1.99 /lb",  "4.90", null, 42);
        verifyParsedItem(iterator.next(), "jt'    yellou sut 0niqns+", null, null, 43);
        verifyParsedItem(iterator.next(), "0 41 lb @ $0.99 /lb",  "0.41", null, 44);
        verifyParsedItem(iterator.next(), "jj    carrots",  "2.99", null, 45);
        verifyParsedItem(iterator.next(), "i orgnc tomatoes +",  "94.76", null, 46);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "500",51);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreBranch, "?                  safeway o",0);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card savinas    2^00-",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Ref, "refrig/frozen",10);
        verifyParsedField(fieldValues, ReceiptFieldType.Saving, "card savinas    2^00-",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "94.76",47);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2016/3/12",51);
    }

    @Test
    public void receipt_Elis24() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_CsabaFeb24, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        printResult(receipt);
        final Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(9,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "lucerne milk 2% 2l",  "3.49", "lucerne milk 2% 2l", 14);
        verifyParsedItem(iterator.next(), "lucerne milk 2% 2l",  "3.49", "lucerne milk 2% 2l", 18);
        verifyParsedItem(iterator.next(), "lucerne milk 2% 2l",  "3.49", "lucerne milk 2% 2l", 22);
        verifyParsedItem(iterator.next(), "lucerne milk 2% 2l",  "3.49", "lucerne milk 2% 2l", 26);
        verifyParsedItem(iterator.next(), "lucerne milk 2% 2l",  "3.0", "lucerne milk 2% 2l", 30);
        verifyParsedItem(iterator.next(), "lucerne milk 2% 2l",  "3.49", "lucerne milk 2% 2l", 33);
        verifyParsedItem(iterator.next(), "lucerne milk 2% 2l",  "3.49", "lucerne milk 2% 2l", 36);
        verifyParsedItem(iterator.next(), "lucerne milk 2% 2l",  "3.49", "lucerne milk 2% 2l", 39);
        verifyParsedItem(iterator.next(), "bread artisan french",  "1.29", "bread artisan french", 46);
        //TODO missing banana because it is multi-line
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 817093735",8);
        verifyParsedField(fieldValues, ReceiptFieldType.Card, "card mastercard                                                                   rcpt 3321000",72);
//        verifyParsedField(fieldValues, ReceiptFieldType.Author, "auth # 093472                                                                     ref  00000224",75);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "?ehc                                                                                           $0.08 r",40);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "27.02",50);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",76);
//        verifyParsedField(fieldValues, ReceiptFieldType.StoreBranch, "?                                    safeway i\",0);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "serve# by: sco 20",9);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2016/2/23",74);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "500",75);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "27.012",52);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "number of items                                                                                      10",55);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "phone. 780.435.5132",7);

    }
}
