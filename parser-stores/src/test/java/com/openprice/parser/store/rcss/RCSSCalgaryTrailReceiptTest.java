package com.openprice.parser.store.rcss;

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
public class RCSSCalgaryTrailReceiptTest extends AbstractReceiptParserIntegrationTest {

    @Value("classpath:/testfiles/rcss/calgarytrail/2015_02_01_14_17_01.jpg.henryHuang.txt")
    private Resource sampleRCSS_2015_02_01_14_17_01;

    @Test
    public void testRCSS_2015_02_01_14_17_01() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleRCSS_2015_02_01_14_17_01, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);

        // verify result of items
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyParsedItem(iterator.next(), "rstr instnt ndle", "0.98", "rstr instnt ndle_05719775555", 7);
        verifyParsedItem(iterator.next(), "table salt    mrj", "1.99", null, 8);
        verifyParsedItem(iterator.next(), "garden wafer", "2.56", "garden wafer_08978200269", 11);
        verifyParsedItem(iterator.next(), "rice stick", "1.08", "rice stick_693491804007", 14);
        verifyParsedItem(iterator.next(), "beatrice 1% milk", "4.46", "beatrice 1% milk_06570010028", 16);
        verifyParsedItem(iterator.next(), "cntry hvst brd", "2.98", "cntry hvst brd_06340004440", 20);
        verifyParsedItem(iterator.next(), "(2)9    plastic bags    grq", "0.10", null, 22);
        // verify parsed fields
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",30);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",29);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "ecology fee                                           0.07",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref ~          auth m        resp 001",40);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approved",46);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",56);
//        verifyParsedField(fieldValues, ReceiptFieldType.AddressCity, "edmonton",31);//TODO why didn't find it?
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 01570",72);//TODO two store id strings, selecting?
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh. lou on price",4);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/18",63);//TODO two date strings, select which one?
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "14.48",26);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*******'*'******'****�****''**'*******",74);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "14.47",24);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",3);

    }

    @Value("classpath:/testfiles/rcss/calgarytrail/2015_02_09_13_15_09.jpg.henryHuang.txt")
    private Resource sampleRCSS_2015_02_09_13_15_09;

    @Test
    public void testRCSS_2015_02_09_13_15_09() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleRCSS_2015_02_09_13_15_09, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        //printResult(receipt);
        assertEquals(4, receipt.getItems().size());

        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();

        verifyParsedItem(iterator.next(), "opo squash", "2.80", "opo squash_3141", 5);
        verifyParsedItem(iterator.next(), "chinese cabbage    mrj", "1.43", null, 7);
        verifyParsedItem(iterator.next(), "muffin lemn cran", "4.87", "muffin lemn cran_06038387812", 10);
        verifyParsedItem(iterator.next(), "plastic bags", "0.05", "plastic bags", 12);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "9.15",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",2);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "9.15",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh, low on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",19);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*******~**~*************'********~****",60);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #            auth #   resp 001",27);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/12/3",31);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",42);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approued",33);
        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store: 01570",58);
//        verifyParsedField(fieldValues, ReceiptFieldType.AddressCity, "edmonton",20);//why not found?
    }

    @Value("classpath:/testfiles/rcss/calgarytrail/2015_04_04_22_22_32.jpg.jingwang.txt")
    private Resource sampleRCSS_2015_04_04_22_22_32;

    @Test
    public void testRCSS_2015_04_04_22_22_32() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleRCSS_2015_04_04_22_22_32, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals(15, receipt.getItems().size());

        // verify result of items
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        verifyParsedItem(iterator.next(), "orange juice", "3.98", "orange juice_04850001964", 9);
        verifyParsedItem(iterator.next(), "fv alv mango cit", "3.00", "fv alv mango cit_05960007015", 14);
        verifyParsedItem(iterator.next(), "corn bicolor 4ct", "7.94", "corn bicolor 4ct_03338370121", 18);
        verifyParsedItem(iterator.next(), "pep grn swt 4ct", "2.98", "pep grn swt 4ct_03338370178", 20);
        verifyParsedItem(iterator.next(), "potato m xd mini", "5.98", "potato m xd mini_06038310510", 21);
        verifyParsedItem(iterator.next(), "rooster garlic", "2.48", "rooster garlic_06038388591", 22);
        verifyParsedItem(iterator.next(), "wmelon mini sdls", "3.97", "wmelon mini sdls_3421", 23);
        verifyParsedItem(iterator.next(), "pepper green swt", "2.02", "pepper green swt_4065", 26);
        verifyParsedItem(iterator.next(), "mush crem bulk", "1.64", "mush crem bulk_4648", 28);
        verifyParsedItem(iterator.next(), "bc saus hot fp", "10.35", "bc saus hot fp_2157230", 33);
        verifyParsedItem(iterator.next(), "split chkn wing", "17.58", "split chkn wing_2163820", 34);
        verifyParsedItem(iterator.next(), "split chkn wing", "16.65", "split chkn wing_2163820", 35);
        verifyParsedItem(iterator.next(), "lamb bonless leg", "24.50", "lamb bonless leg_2174190", 36);
        verifyParsedItem(iterator.next(), "sq basa flts", "7.98", "sq basa flts_06038377431", 38);
        verifyParsedItem(iterator.next(), "croissant cp", "5.00", "croissant cp_46036330079", 40);

        // verify parsed fields
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",46);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "superstore",45);
        verifyParsedField(fieldValues, ReceiptFieldType.Recycle, "ecology fee                                       0.08",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref #             auth #     resp 001",54);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approueo",60);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",68);
//        verifyParsedField(fieldValues, ReceiptFieldType.AddressCity, "edmonton",47);
//        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "01570",85);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "blg on fresh, lou on price",5);
//        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/4/4",58);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/4/4",75);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "116.71",42);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*�****'*****~****'**************''*'*'",86);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "116.71",41);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",4);
    }

    @Value("classpath:/testfiles/rcss/calgarytrail/2015_07_21_10_50_33.jpg.henryHuang.txt")
    private Resource sampleRCSS_2015_07_21_10_50_33;
    @Test
    public void testRCSS_2015_07_21_10_50_33() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleRCSS_2015_07_21_10_50_33, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals(8,receipt.getItems().size());

        final Iterator<ParsedItem> iterator=receipt.getItems().iterator();
        verifyParsedItem(iterator.next(), "orange navel 5lb", "5.88", "orange navel 5lb_03338311006", 42);
        verifyParsedItem(iterator.next(), "banana", "1.35", "banana_4011", 43);
        verifyParsedItem(iterator.next(), "cherries red    mrj", "7.29", null, 45);

        //TODO why sometimes "XXXX    gmrj" keeps gmrj in the name, sometimes doesn't
        verifyParsedItem(iterator.next(), "tc baby powder    gmrj", "0.99", null, 48);
        verifyParsedItem(iterator.next(), "j&j baby wash    gmrj", "7.94", null, 49);
        verifyParsedItem(iterator.next(), "baby bar    gmrj", "2.49", null, 52);
        verifyParsedItem(iterator.next(), "sudocrem 60g    ghrj", "3.98", null, 53);
        verifyParsedItem(iterator.next(), "plastic bags", "0.05", "plastic bags", 55);

        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        verifyParsedField(fieldValues, ReceiptFieldType.AddressLine1, "4821 calgary trail",63);
        verifyParsedField(fieldValues, ReceiptFieldType.Chain, "supersto re",62);
        verifyParsedField(fieldValues, ReceiptFieldType.Author, "ref ~            ruth #        resp 001",71);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approued",77);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "12223-5922 rt0001",86);
//        verifyParsedField(fieldValues, ReceiptFieldType.StoreID, "store 01570",94);//TODO parse store id
//        verifyParsedField(fieldValues, ReceiptFieldType.AddressCity, "edmonton",64);
        verifyParsedField(fieldValues, ReceiptFieldType.GstAmount, "0.77",58);
        verifyParsedField(fieldValues, ReceiptFieldType.Slogan, "big on fresh, lm1 on price",3);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/6/18",94);//TODO there is another date string
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "30.74",59);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "***~******'****** ****************'*'**",96);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "29.97",56);
        verifyParsedField(fieldValues, ReceiptFieldType.Phone, "780-430-2769",2);
//        verifyParsedField(fieldValues, ReceiptFieldType.AddressCountry, "canada",43);

    }

}
