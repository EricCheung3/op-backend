package com.openprice.parser.store.lucky97;

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
public class Lucky97Test1 extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testfiles/lucky97/branch_107_97St/2014_12_06_22_53_32.jpg.hengshuai.txt")
    private Resource receipt_2014_12_06_22_53_32;

    @Value("classpath:/testfiles/lucky97/branch_107_97St/2015_02_10_13_25_35.jpg.hengshuai.txt")
    private Resource receipt_2015_02_10_13_25_35;

    @Value("classpath:/testfiles/lucky97/branch_107_97St/2015_05_02_22_19_07.jpg.haipeng.txt")
    private Resource receipt_2015_05_02_22_19_07;

    @Value("classpath:/testfiles/lucky97/branch_107_97St/2015_07_03_16_51_02.jpg.haipeng.txt")
    private Resource receipt_2015_07_03_16_51_02;

    @Value("classpath:/testfiles/lucky97/branch_107_97St/2015_07_16_19_59_17.jpg.momingzhen159.txt")
    private Resource receipt_2015_07_16_19_59_17;

    @Test
    public void receipt_2014_12_06_22_53_32()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2014_12_06_22_53_32, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("lucky97", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(17,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "chinese celery can tau",  "3.71", null, 8);
        verifyParsedItem(iterator.next(), "chives leaf la he",  "3.54", null, 9);
        verifyParsedItem(iterator.next(), "tvi shrimp fish ball",  "5.85", null, 10);
        verifyParsedItem(iterator.next(), "om 20/40 baby octopus",  "4.29", null, 11);
        verifyParsedItem(iterator.next(), "small taro root",  "7.01", null, 13);
        verifyParsedItem(iterator.next(), "dh shang hai red beancurd",  "3.39", null, 14);
        verifyParsedItem(iterator.next(), "don qua cut",  "2.87", null, 16);
        verifyParsedItem(iterator.next(), "beans sprout",  "1.04", null, 18);
        verifyParsedItem(iterator.next(), "ginger root",  "0.89", null, 20);
        verifyParsedItem(iterator.next(), "tong ho tang o",  "4.17", null, 21);
        verifyParsedItem(iterator.next(), "ataulfo mangoes",  "2.79", null, 23);
        verifyParsedItem(iterator.next(), "green lo bok",  "1.51", null, 26);
        verifyParsedItem(iterator.next(), "wholesale pork leg",  "5.01", null, 27);
        verifyParsedItem(iterator.next(), "wholesale pork leg",  "4.58", null, 29);
        verifyParsedItem(iterator.next(), "wholesale pork leg",  "7.17", null, 31);
        verifyParsedItem(iterator.next(), "pork neck bone",  "4.93", null, 33);
        verifyParsedItem(iterator.next(), "fish live tlapia black",  "6.40", null, 34);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "item count                             17",39);
//        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card      : ******* -~  *3783 c",45);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "69.75",36);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "69.75",37);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 83687-7662-rt-0001",4);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2013/4/21",49);
//        verifyParsedField(fieldValues, ReceiptFieldType.Saving, "t1a rkdown : $1.32",30);

    }

    @Test
    public void receipt_2015_02_10_13_25_35()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_10_13_25_35, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("lucky97", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(33,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "lucerine 2% milk carton",  "2.95", null, 7);
        verifyParsedItem(iterator.next(), "lucerlne 2% milk carton",  "2.95", null, 10);
        verifyParsedItem(iterator.next(), "trop juice orange original",  "5.98", null, 13);
        verifyParsedItem(iterator.next(), "oyster mushrooms",  "4.95", null, 17);
        verifyParsedItem(iterator.next(), "bry conn quail eggs fresh",  "3.99", null, 18);
        verifyParsedItem(iterator.next(), "gold v eggs white large",  "3.79", null, 20);
        verifyParsedItem(iterator.next(), "fresh cilantro",  "1.58", null, 22);
        verifyParsedItem(iterator.next(), "emf disposable bam chopstick",  "2.999", null, 24);
        verifyParsedItem(iterator.next(), "gold v eggs white large",  "3.79", null, 25);
        verifyParsedItem(iterator.next(), "ginger root",  "1.51", null, 27);
        verifyParsedItem(iterator.next(), "watercress",  "3.76", null, 30);
        verifyParsedItem(iterator.next(), "navel oranges large",  "2.72", null, 32);
        verifyParsedItem(iterator.next(), "bunched spinach",  "2.48", null, 34);
        verifyParsedItem(iterator.next(), "green onions",  "1.00", null, 37);
        verifyParsedItem(iterator.next(), "sh plastic cups old fashione",  "1.799", null, 39);
        verifyParsedItem(iterator.next(), "foco coconut juice",  "1.499", null, 40);
        verifyParsedItem(iterator.next(), "fogo coconut juice",  "1.499", null, 43);
        verifyParsedItem(iterator.next(), "jx noodle sichuan dandan",  "5.29", null, 46);
        verifyParsedItem(iterator.next(), "short suey choy",  "1.92", null, 48);
        verifyParsedItem(iterator.next(), "large taro root",  "4.13", null, 53);
        verifyParsedItem(iterator.next(), "persimmons - fuyu",  "8.51", null, 56);
        verifyParsedItem(iterator.next(), "large tomatoes",  "4.23", null, 59);
        verifyParsedItem(iterator.next(), "sweet potato purple skin yam",  "5.06", null, 62);
        verifyParsedItem(iterator.next(), "white lo bok",  "1.41", null, 64);
        verifyParsedItem(iterator.next(), "green lo bok",  "1.48", null, 67);
        verifyParsedItem(iterator.next(), "chinese mandarins",  "4.30", null, 70);
        verifyParsedItem(iterator.next(), "pork back rib",  "10.80", null, 72);
        verifyParsedItem(iterator.next(), "pork feet",  "3.83", null, 74);
        verifyParsedItem(iterator.next(), "pork ear",  "1.44", null, 75);
        verifyParsedItem(iterator.next(), "pork feet",  "5.13", null, 76);
        verifyParsedItem(iterator.next(), "pork feet",  "3.72", null, 77);
        verifyParsedItem(iterator.next(), "bottle sales",  "0.95", null, 80);
        //TODO why this was not a field?
        verifyParsedItem(iterator.next(), "total sales",  "119.11", null, 82);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "119.11",83);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/6",5);
        verifyParsedField(fieldValues, ReceiptFieldType.Saving, "markdown: $2.29",73);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst [$7.84]                       $0.39",79);


    }

    @Test
    public void receipt_2015_05_02_22_19_07()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_05_02_22_19_07, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("lucky97", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(5,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "-97 street edmonton ab t",  "5.29", null, 1);
        verifyParsedItem(iterator.next(), "lettuce - head",  "1.98", null, 6);
        verifyParsedItem(iterator.next(), "lettuce - head",  "1.98", null, 8);
        verifyParsedItem(iterator.next(), "dw arrow root starch",  "3.29", null, 12);
        verifyParsedItem(iterator.next(), "0. non foods",  "4.999", null, 13);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "item count                             6",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card     :   ************9072 c",26);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "14.27",16);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "14.27",17);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst [$4 .99]                       $0 .25",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/4/6",30);
        verifyParsedField(fieldValues, ReceiptFieldType.Saving, "markdown: $1. 30",9);

    }

    @Test
    public void receipt_2015_07_03_16_51_02()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_03_16_51_02, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("lucky97", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "roma tomatoes",  "1.78", null, 10);
        verifyParsedItem(iterator.next(), "sugar< mandarins baby",  "0.77", null, 13);
        verifyParsedItem(iterator.next(), "short suey choy",  "3.76", null, 16);
        verifyParsedItem(iterator.next(), "cock brand black bean    400g",  "1.49", null, 20);
//        verifyParsedField(fieldValues, ReceiptFieldType.Account, "gard                     *****!0" 2 p",36);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "11.49",24);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 83687< 61 ,2-rt -o  l )1",5);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/31",7);
    }

    @Test
    public void receipt_2015_07_16_19_59_17()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_07_16_19_59_17, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("lucky97", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(19,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "pork shoulder no skin",  "14.69", null, 8);
        verifyParsedItem(iterator.next(), "beef tripe", null, null, 9);
        verifyParsedItem(iterator.next(), "bittermelon foo qua",  "3.57", null, 10);
        verifyParsedItem(iterator.next(), "sweet yellow corn",  "4.95", null, 12);
        verifyParsedItem(iterator.next(), "yellow bananas",  "1.88", null, 15);
        verifyParsedItem(iterator.next(), "lk ching po leung", null, null, 16);
        verifyParsedItem(iterator.next(), "choy - yu [cal ngot]",  "3.07", null, 18);
        verifyParsedItem(iterator.next(), "no name bean sprouts",  "1.19", null, 20);
        verifyParsedItem(iterator.next(), "boxthorn fruit fructu    s lycil", "2.79", null, 21);
        verifyParsedItem(iterator.next(), "choy - yu [cal ngot]",  "2.80", null, 23);
        verifyParsedItem(iterator.next(), "bunched spinach",  "3.36", null, 26);
        verifyParsedItem(iterator.next(), "greenonions",  "1.38", null, 29);
        verifyParsedItem(iterator.next(), "celery stalk whole",  "1.88", null, 31);
        verifyParsedItem(iterator.next(), "green cooking pa    paya",  "3.79", null, 35);
        verifyParsedItem(iterator.next(), "sunrise tofu puffs c    hinese s $3.19 *", null, null, 37);
        verifyParsedItem(iterator.next(), "clementine mandari n    s",  "6.31", null, 39);
        verifyParsedItem(iterator.next(), "navel oranges larg",  "3.44", null, 41);
        verifyParsedItem(iterator.next(), "/ lb", null, null, 43);
        verifyParsedItem(iterator.next(), "tomatoes on the vine",  "6.18", null, 44);
//        verifyParsedItem(iterator.next(), "net sales",  "87.60", null, 46);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "item count                             26",51);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "car d : ************1192 c",56);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "87.60",47);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "87.60",48);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst# 83687-7662-rt -0001",3);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/5/16",60);

    }


}