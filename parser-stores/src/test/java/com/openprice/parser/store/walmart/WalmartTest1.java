package com.openprice.parser.store.walmart;

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
public class WalmartTest1 extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2014_12_06_23_13_31.jpg.hengshuai.txt")
    private Resource receipt_2014_12_06_23_13_31;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2014_12_06_23_54_52.jpg.hengshuai.txt")
    private Resource receipt_2014_12_06_23_54_52;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_02_01_14_16_53.jpg.henryHuang.txt")
    private Resource receipt_2015_02_01_14_16_53;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_02_09_13_16_51.jpg.henryHuang.txt")
    private Resource receipt_2015_02_09_13_16_51;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_02_09_13_17_09.jpg.henryHuang.txt")
    private Resource receipt_2015_02_09_13_17_09;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_02_09_15_15_48.jpg.random.txt")
    private Resource receipt_2015_02_09_15_15_48;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_02_09_15_16_02.jpg.random.txt")
    private Resource receipt_2015_02_09_15_16_02;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_02_09_15_20_22.jpg.random.txt")
    private Resource receipt_2015_02_09_15_20_22;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_02_09_23_43_37.jpg.random.txt")
    private Resource receipt_2015_02_09_23_43_37;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_02_11_23_27_15.jpg.random.txt")
    private Resource receipt_2015_02_11_23_27_15;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_02_14_22_27_01.jpg.random.txt")
    private Resource receipt_2015_02_14_22_27_01;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_04_04_21_24_43.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_24_43;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_04_04_21_25_06.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_25_06;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_04_04_21_25_23.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_25_23;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_04_04_21_27_59.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_27_59;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_04_04_21_31_13.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_31_13;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_04_04_21_40_22.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_40_22;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_04_04_21_41_45.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_41_45;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_04_04_21_41_53.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_41_53;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_04_04_21_44_02.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_44_02;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_04_04_21_54_50.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_54_50;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_04_04_21_55_06.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_55_06;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_04_04_21_55_14.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_55_14;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_04_04_21_58_55.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_58_55;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_04_04_21_59_26.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_59_26;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_04_04_21_59_30.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_59_30;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_04_04_21_59_40.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_21_59_40;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_04_04_22_03_34.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_03_34;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_04_04_22_03_43.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_03_43;

    @Value("classpath:/testfiles/walmart/branch_39_calgary/2015_04_04_22_03_51.jpg.jingwang.txt")
    private Resource receipt_2015_04_04_22_03_51;

    @Test
    public void receipt_2014_12_06_23_13_31()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2014_12_06_23_13_31, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(26,receipt.getItems().size());
        //TODO
//        verifyParsedItem(iterator.next(), "f 0 r    l ess", null, null, 5);
        verifyParsedItem(iterator.next(), "celery bunch 000000004070k",  "1.27", null, 9);
        verifyParsedItem(iterator.next(), "org carrots",  "2.00", null, 10);
        verifyParsedItem(iterator.next(), "nat pistachi",  "6.00", null, 11);
        verifyParsedItem(iterator.next(), "sockeye salm",  "9.27", null, 14);
        verifyParsedItem(iterator.next(), "lean gr pork",  "3.25", null, 15);
        verifyParsedItem(iterator.next(), "rst soya nut",  "2.00", null, 18);
        verifyParsedItem(iterator.next(), "walnuts",  "13.17", null, 19);
        verifyParsedItem(iterator.next(), "walnuts",  "10.33", null, 21);
        verifyParsedItem(iterator.next(), "outdoor bags",  "9.67", null, 23);
        verifyParsedItem(iterator.next(), "ge organic",  "6.37", null, 24);
        verifyParsedItem(iterator.next(), "tide96 free",  "21.78", null, 25);
        verifyParsedItem(iterator.next(), "danactive bp",  "5.97", null, 26);
        verifyParsedItem(iterator.next(), "ab bev crf",  "0.24", null, 27);
        verifyParsedItem(iterator.next(), "lilones yog",  "3.87", null, 29);
        verifyParsedItem(iterator.next(), "mini wtrmeln",  "3.97", null, 30);
        verifyParsedItem(iterator.next(), "mini wtrheln",  "3.97", null, 31);
        verifyParsedItem(iterator.next(), "pomegranate 000000003127k",  "2.54", null, 32);
        verifyParsedItem(iterator.next(), "2 at",  "1.27", null, 33);
        verifyParsedItem(iterator.next(), "sw rnch dip",  "2.97", null, 36);
        verifyParsedItem(iterator.next(), "ab crf",  "0.05", null, 38);
        verifyParsedItem(iterator.next(), "ab dep gable",  "0.25", null, 39);
        verifyParsedItem(iterator.next(), "2 milk",  "4.56", null, 40);
        verifyParsedItem(iterator.next(), "ab bev crf",  "0.07", null, 41);
        verifyParsedItem(iterator.next(), "ab dep milk",  "0.25", null, 42);
        verifyParsedItem(iterator.next(), "mandarins",  "3.97", null, 43);
        verifyParsedItem(iterator.next(), "mandarins",  "3.97", null, 44);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "137.08",47);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "135.51",45);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval # 07319z",60);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2013/11/11",86);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137466199 rt 0001",69);

    }

    @Test
    public void receipt_2014_12_06_23_54_52()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2014_12_06_23_54_52, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(18,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "f 0 r    l ess", null, null, 5);
        verifyParsedItem(iterator.next(), "bananas",  "0.77", null, 9);
        verifyParsedItem(iterator.next(), "demp wt brd",  "3.27", null, 11);
        verifyParsedItem(iterator.next(), "egg golden d",  "4.97", null, 12);
        verifyParsedItem(iterator.next(), "ab crf",  "0.07", null, 14);
        verifyParsedItem(iterator.next(), "danino 6x60g",  "2.97", null, 16);
        verifyParsedItem(iterator.next(), "corn",  "4.97", null, 17);
        verifyParsedItem(iterator.next(), "salmon fill",  "7.98", null, 18);
        verifyParsedItem(iterator.next(), "jmsnathnyham",  "5.27", null, 19);
        verifyParsedItem(iterator.next(), "strawberries",  "1.97", null, 20);
        verifyParsedItem(iterator.next(), "blueberries",  "3.47", null, 21);
        verifyParsedItem(iterator.next(), "bd chdr 500g",  "4.37", null, 22);
        verifyParsedItem(iterator.next(), "savoy",  "4.02", null, 24);
        verifyParsedItem(iterator.next(), "savoy",  "4.02", null, 27);
        verifyParsedItem(iterator.next(), "nappa fd",  "2.68", null, 29);
        verifyParsedItem(iterator.next(), "lttce rmaine 000000004640k",  "2.47", null, 33);
        verifyParsedItem(iterator.next(), "red grape",  "5.83", null, 34);
        verifyParsedItem(iterator.next(), "watermelon",  "3.00", null, 36);
        verifyParsedItem(iterator.next(), "potatoes",  "3.97", null, 37);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "69.55",42);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "69.55",41);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval # 09290z",45);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2013/2/15",63);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137466199 rt 0001",54);

    }

    @Test
    public void receipt_2015_02_01_14_16_53()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_01_14_16_53, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "nn pun:hds e n<~ l e;;;,dr~    m alh s!-- 1ll", null, null, 9);
//        verifyParsedItem(iterator.next(), "mdjor l t~ .",  "-s.0", null, 13);
//        verifyParsedItem(iterator.next(), "? 1.w ek >: o f",  "0.00", null, 14);
//        verifyParsedItem(iterator.next(), "will 1 1lfl~    depe nd",  "0.11", null, 15);
//        verifyParsedItem(iterator.next(), "your store code    ts:",  "1149", null, 25);
        verifyParsedItem(iterator.next(), "apples",  "4.87", null, 41);
        verifyParsedItem(iterator.next(), "large",  "2.67", null, 43);
        verifyParsedItem(iterator.next(), "russ potato",  "2.67", null, 44);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "11.26",47);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "11.21",45);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "***********************************",6);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval # 060221",50);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2016/1/15",68);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/ hst 13716619si rt 0001",58);

    }

    @Test
    public void receipt_2015_02_09_13_16_51()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_13_16_51, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(10,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "supercentre    ..", null, null, 5);
//        verifyParsedItem(iterator.next(), "f o-r    l ess", null, null, 8);
        verifyParsedItem(iterator.next(), "2 milk",  "4.46", null, 12);
        verifyParsedItem(iterator.next(), "ab bev crf 000(",  "0.07", null, 13);
        verifyParsedItem(iterator.next(), "ab dep milk",  "0.25", null, 14);
        verifyParsedItem(iterator.next(), "0 milk",  "4.46", null, 15);
        verifyParsedItem(iterator.next(), "ab bev crf 000(",  "0.07", null, 16);
        verifyParsedItem(iterator.next(), "ab dep milk 000(",  "0.25", null, 17);
        verifyParsedItem(iterator.next(), "strawberries",  "2.97", null, 18);
        verifyParsedItem(iterator.next(), "apple turn 9 06n",  "5.00", null, 19);
        verifyParsedItem(iterator.next(), "dlx ham wt 006~:",  "3.87", null, 20);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "21.40",22);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "21.40",21);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval ii 013311",25);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/8",43);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137466199 rt 0001",34);

    }

    @Test
    public void receipt_2015_02_09_13_17_09()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_13_17_09, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "apples",  "4.87", null, 8);
        verifyParsedItem(iterator.next(), "large",  "2.67", null, 10);
        verifyParsedItem(iterator.next(), "russ potato",  "2.67", null, 11);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "11.26",14);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "11.21",12);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval         # 060221",17);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/15",41);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137166199 rt 0001",25);

    }

    @Test
    public void receipt_2015_02_09_15_15_48()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_15_15_48, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
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
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "61.08",27);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "59.27",25);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "sears          **** **** **** 7721 i 2",29);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval i 078662",30);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/12/28",47);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137166199 rt 0001",39);

    }

    @Test
    public void receipt_2015_02_09_15_16_02()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_15_16_02, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(7,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "~-rtr:. ~elllent    offert", null, null, 1);
        verifyParsedItem(iterator.next(), "-    i1 f'ran~ais).", null, null, 2);
        verifyParsedItem(iterator.next(), "hh tomato",  "3.11", null, 16);
        verifyParsedItem(iterator.next(), "bananas",  "1.60", null, 18);
        verifyParsedItem(iterator.next(), "hnts pudd ch",  "0.97", null, 20);
        verifyParsedItem(iterator.next(), "hnts pudd bu",  "0.97", null, 21);
        verifyParsedItem(iterator.next(), "hunts pudd v",  "0.97", null, 22);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "7.62",24);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "7.62",23);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval # 081337",27);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/17",44);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137466199 rt 0001",35);

    }

    @Test
    public void receipt_2015_02_09_15_20_22()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_15_20_22, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(21,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "turnovers",  "5.00", null, 15);
        verifyParsedItem(iterator.next(), "demp wt brd",  "3.17", null, 16);
        verifyParsedItem(iterator.next(), "kaiser wht",  "2.00", null, 18);
        verifyParsedItem(iterator.next(), "dv sh intse",  "6.94", null, 21);
        verifyParsedItem(iterator.next(), "potatoes",  "4.67", null, 22);
        verifyParsedItem(iterator.next(), "peppers 3ct",  "4.97", null, 23);
        verifyParsedItem(iterator.next(), "mini carrots",  "2.47", null, 24);
        verifyParsedItem(iterator.next(), "crackers",  "2.87", null, 25);
        verifyParsedItem(iterator.next(), "becel",  "5.97", null, 26);
        verifyParsedItem(iterator.next(), "deli meat",  "6.77", null, 27);
        verifyParsedItem(iterator.next(), "philly tub",  "4.37", null, 28);
        verifyParsedItem(iterator.next(), "yellow onion 000000001093k",  "0.34", null, 29);
        verifyParsedItem(iterator.next(), "top srln stk",  "13.40", null, 31);
        verifyParsedItem(iterator.next(), "wm hy gar vp",  "10.00", null, 33);
        verifyParsedItem(iterator.next(), "dog spray",  "7.97", null, 38);
        verifyParsedItem(iterator.next(), "knot dtnglr",  "6.47", null, 39);
        verifyParsedItem(iterator.next(), "eggo 16ct",  "3.97", null, 40);
        verifyParsedItem(iterator.next(), "tostito chip",  "2.97", null, 41);
        verifyParsedItem(iterator.next(), "eggs",  "2.47", null, 43);
        verifyParsedItem(iterator.next(), "apples",  "4.87", null, 44);
        verifyParsedItem(iterator.next(), "palm 2 for $5",  "1.00", null, 47);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "135.44",50);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "133.40",48);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/15",74);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137166199 rt 0001",53);

    }

    @Test
    public void receipt_2015_02_09_23_43_37()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_09_23_43_37, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(9,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "purex d24",  "9.88", null, 14);
        verifyParsedItem(iterator.next(), "vs lemontea",  "2.48", null, 15);
        verifyParsedItem(iterator.next(), "ab dep",  "0.60", null, 16);
        verifyParsedItem(iterator.next(), "vs lemontea",  "2.48", null, 17);
        verifyParsedItem(iterator.next(), "ab dep",  "0.60", null, 18);
        verifyParsedItem(iterator.next(), "dole s peach",  "2.50", null, 19);
        verifyParsedItem(iterator.next(), "oranges",  "4.38", null, 20);
        verifyParsedItem(iterator.next(), "coca-cola",  "2.97", null, 22);
        verifyParsedItem(iterator.next(), "ab bev crf",  "0.12", null, 23);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "27.26",27);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "26.61",25);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval # 02088z",30);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/9",48);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137166199 rt 0001",39);

    }

    @Test
    public void receipt_2015_02_11_23_27_15()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_11_23_27_15, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(23,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "the custom    er se r vi ce des k", null, null, 14);
//        verifyParsedItem(iterator.next(), "for less    .", null, null, 41);
        verifyParsedItem(iterator.next(), "donatelldelx",  "3.00", null, 46);
        verifyParsedItem(iterator.next(), "donatelldelx",  "3.00", null, 47);
        verifyParsedItem(iterator.next(), "gv med perog",  "1.77", null, 48);
        verifyParsedItem(iterator.next(), "lm pizza kit",  "2.97", null, 49);
        verifyParsedItem(iterator.next(), "skil chk flo",  "4.97", null, 50);
        verifyParsedItem(iterator.next(), "1 milk",  "4.54", null, 51);
        verifyParsedItem(iterator.next(), "ab bev crf",  "0.08", null, 52);
        verifyParsedItem(iterator.next(), "gv mac ch xc",  "0.87", null, 54);
        verifyParsedItem(iterator.next(), "g v yogurt",  "2.57", null, 55);
        verifyParsedItem(iterator.next(), "gv orange",  "1.00", null, 56);
        verifyParsedItem(iterator.next(), "cd gingerale",  "2.97", null, 58);
        verifyParsedItem(iterator.next(), "ab dep can",  "0.60", null, 59);
        verifyParsedItem(iterator.next(), "gv sausage",  "7.67", null, 60);
        verifyParsedItem(iterator.next(), "gv ham",  "2.27", null, 62);
        verifyParsedItem(iterator.next(), "equate des",  "8.97", null, 63);
        verifyParsedItem(iterator.next(), "doritos spic",  "3.17", null, 64);
        verifyParsedItem(iterator.next(), "west cheetos 0060'",  "3.17", null, 65);
        verifyParsedItem(iterator.next(), "bananas",  "2.36", null, 67);
        verifyParsedItem(iterator.next(), "red onions oooooooo'i082k",  "0.68", null, 69);
        verifyParsedItem(iterator.next(), "avocado",  "1.94", null, 71);
        verifyParsedItem(iterator.next(), "2 at",  "0.97", null, 72);
        verifyParsedItem(iterator.next(), "hh tomato",  "0.89", null, 73);
        verifyParsedItem(iterator.next(), "grn leaf",  "2.97", null, 75);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "83.07",78);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "82.16",76);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "***********************************",3);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval i 438158",81);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/8",97);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137466199 rt 0001",89);

    }

    @Test
    public void receipt_2015_02_14_22_27_01()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_02_14_22_27_01, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(9,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "n o purcha se necessa r ~ . h    a th k ti l", null, null, 9);
//        verifyParsedItem(iterator.next(), "t es t ing 9uestion re qutred . o    pen t o", null, null, 10);
        verifyParsedItem(iterator.next(), "frog",  "10.00", null, 46);
        verifyParsedItem(iterator.next(), "bk/br dog",  "10.00", null, 47);
        verifyParsedItem(iterator.next(), "bag mg",  "4.00", null, 48);
        verifyParsedItem(iterator.next(), "wb belg bal",  "8.00", null, 49);
        verifyParsedItem(iterator.next(), "qa cherries",  "2.00", null, 50);
        verifyParsedItem(iterator.next(), "qa cherries",  "2.00", null, 51);
        verifyParsedItem(iterator.next(), "m m speckled",  "5.00", null, 52);
        verifyParsedItem(iterator.next(), "trio jellybn",  "1.78", null, 53);
        verifyParsedItem(iterator.next(), "jf strawby",  "1.78", null, 54);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "54.13",58);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "51.55",56);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "card val      072017362009       $6.99 j",55);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2016/2/14",83);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137166199 rt 0001",61);

    }

    @Test
    public void receipt_2015_04_04_21_24_43()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_24_43, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(5,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "howto    .", null, null, 32);
        verifyParsedItem(iterator.next(), "milk",  "8.97", null, 48);
        verifyParsedItem(iterator.next(), "ab bev crf",  "0.08", null, 49);
        verifyParsedItem(iterator.next(), "ab dep milk",  "0.25", null, 50);
        verifyParsedItem(iterator.next(), "stud sensor",  "24.98", null, 51);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "35.53",54);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "34.28",52);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "***********************************",4);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval i 020566",57);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/3/20",79);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137466199 rt 0001",67);

    }

    @Test
    public void receipt_2015_04_04_21_25_06()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_25_06, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        //TODO why no items?
        assertEquals(0,receipt.getItems().size());
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "13.27",12);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "13.27",11);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval u 039198",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/10/12",33);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gs t/hst 137466199 rt 0001",24);

    }

    @Test
    public void receipt_2015_04_04_21_25_23()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_25_23, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "no purchase necessary . m    ath s k i ll", null, null, 10);
        verifyParsedItem(iterator.next(), "grapes sless 000000001022r",  "5.96", null, 48);
        verifyParsedItem(iterator.next(), "bananas",  "1.86", null, 50);
        verifyParsedItem(iterator.next(), "bananas",  "2.79", null, 52);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "46.25",60);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "46.25",54);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "***********************************",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval i 045922",63);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/7",82);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137466199 rt 0001",72);

    }

    @Test
    public void receipt_2015_04_04_21_27_59()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_27_59, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "the cus tom    er s~r v ice de s k", null, null, 18);
//        verifyParsedItem(iterator.next(), "f 0 r    l e <i s", null, null, 37);
        verifyParsedItem(iterator.next(), "enforcement",  "4.92", null, 41);
        verifyParsedItem(iterator.next(), "enforcement",  "4.92", null, 42);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "10.33",45);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "9.84",43);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "***********************************",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval # 010314",48);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/5/10",66);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137466199 rt 0001",57);

    }

    @Test
    public void receipt_2015_04_04_21_31_13()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_31_13, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(4,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "to",  "ente-.0", null, 1);
//        verifyParsedItem(iterator.next(), "no pur chase necessar~ . m    ath skill", null, null, 7);
//        verifyParsedItem(iterator.next(), "wi thi n 2 w    eeks of t o da~ . odds of", null, null, 11);
        verifyParsedItem(iterator.next(), "fa r b",  "34.98", null, 39);
        verifyParsedItem(iterator.next(), "micro combo",  "24.00", null, 40);
        verifyParsedItem(iterator.next(), "sparkplug",  "3.96", null, 41);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "66.09",44);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "62.94",42);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "***********************************",4);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval. # 035705",47);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/7/26",67);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137166199 rt 0001",56);

    }

    @Test
    public void receipt_2015_04_04_21_40_22()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_40_22, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "enter for a",  "ci.0", null, 2);
//        verifyParsedItem(iterator.next(), "we want    i(now how", null, null, 8);
//        verifyParsedItem(iterator.next(), "we'    i ng!", null, null, 9);
//        verifyParsedItem(iterator.next(), "no pur ehe    ~ ar- ~. mcot h s k til", null, null, 10);
//        verifyParsedItem(iterator.next(), "testing q,,    required . opt: n l u", null, null, 11);
//        verifyParsedItem(iterator.next(), "m a.ior i 1. surve!ol m    usi be t aken", null, null, 13);
//        verifyParsedItem(iterator.next(), "within ~ w    eeku of toda!ol . odds of", null, null, 14);
//        verifyParsedItem(iterator.next(), "yo\"    ., ion counis", null, null, 25);
//        verifyParsedItem(iterator.next(), "( le son(;", null, null, 26);
        verifyParsedItem(iterator.next(), "mg pm 8.8l",  "3.97", null, 39);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "4.17",42);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "3.97",40);
//        verifyParsedField(fieldValues, ReceiptFieldType.Account, "**********************************",7);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval i 0'16804",45);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/9/21",64);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst /hst 1374661 9~j rt 0001",55);

    }

    @Test
    public void receipt_2015_04_04_21_41_45()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_41_45, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "to    en t ~r . p l~ane c o~p i ete d surve~", null, null, 4);
//        verifyParsedItem(iterator.next(), "about    tod~y 'n s tore visit at :", null, null, 5);
//        verifyParsedItem(iterator.next(), "we    i~an t",  "0.0", null, 9);
//        verifyParsedItem(iterator.next(), "no purchase necessary . h    ath skill", null, null, 11);
//        verifyParsedItem(iterator.next(), "(le    nonda9e ent lg~le~ent offert", null, null, 28);
        verifyParsedItem(iterator.next(), "imp mul atf",  "7.47", null, 46);
//        verifyParsedItem(iterator.next(), "env.h.charge 00 787'1:~",  "0.09", null, 47);
        verifyParsedItem(iterator.next(), "funnel",  "2.00", null, 48);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "10.04",51);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "9.56",49);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/7/20",69);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137'166199 rt 0001",54);

    }

    @Test
    public void receipt_2015_04_04_21_41_53()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_41_53, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(8,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "m ajori t~ . surve~ m    ust be tak en", null, null, 10);
//        verifyParsedItem(iterator.next(), "http : // survey . wal~a r t    .ca", null, null, 17);
        verifyParsedItem(iterator.next(), "preschool",  "2.00", null, 37);
        verifyParsedItem(iterator.next(), "baste brush",  "1.97", null, 38);
        verifyParsedItem(iterator.next(), "ms skewer",  "3.00", null, 39);
        verifyParsedItem(iterator.next(), "ms skewer",  "3.00", null, 40);
        verifyParsedItem(iterator.next(), "ruler",  "1.00", null, 41);
        verifyParsedItem(iterator.next(), "adhesive",  "3.56", null, 42);
        verifyParsedItem(iterator.next(), "bubb stick",  "0.75", null, 43);
        verifyParsedItem(iterator.next(), "bubb stick",  "0.75", null, 44);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "16.83",47);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "16.03",45);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "***********************************",4);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval i 009698",50);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/9",69);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137166199 rt 0001",59);

    }

    @Test
    public void receipt_2015_04_04_21_44_02()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_44_02, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(5,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "no purlhase necessary . m    at h skill", null, null, 7);
//        verifyParsedItem(iterator.next(), "within 2 w    eeks or todaw. odds or", null, null, 11);
//        verifyParsedItem(iterator.next(), "purposes of co~pleting    '", null, null, 20);
        verifyParsedItem(iterator.next(), "dkjasmine",  "9.94", null, 39);
        verifyParsedItem(iterator.next(), "tote",  "6.94", null, 42);
        verifyParsedItem(iterator.next(), "tote",  "6.94", null, 43);
        verifyParsedItem(iterator.next(), "tote",  "6.94", null, 44);
        verifyParsedItem(iterator.next(), "tote",  "6.94", null, 45);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "72.44",54);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "69.46",47);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "***********************************",4);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval i 013176",57);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/6/21",76);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137466199 rt 0001",66);

    }

    @Test
    public void receipt_2015_04_04_21_54_50()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_54_50, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(8,receipt.getItems().size());
        //TODO noisy: maybe should start from "Walmart" line
        verifyParsedItem(iterator.next(), ";~ l ) oui    i od<t!:! ' s. s t t>re vi s ! t at:", null, null, 7);
        verifyParsedItem(iterator.next(), "no i' u",  "rchaptwcessarj.0", null, 13);
        verifyParsedItem(iterator.next(), "r~ a j or-ti , .",  "-.0", null, 16);
        verifyParsedItem(iterator.next(), "ht l p , / /s. u r v e ~j",  "-almart.0", null, 23);
//        verifyParsedItem(iterator.next(), "ple ~ se r et ~ i n    t his r ece i pt for t he", null, null, 24);
        verifyParsedItem(iterator.next(), "pui''pcl :ii ets    of c i>~ip 1e t i n 9", null, null, 25);
        verifyParsedItem(iterator.next(), "(lt!' so n d ii ~l tt    hs1 e!hilel'lent offert", null, null, 29);
        verifyParsedItem(iterator.next(), "m    f ren!'~tis).", null, null, 31);
//        verifyParsedItem(iterator.next(), "pfwud to be ll part o    f", null, null, 39);
        verifyParsedItem(iterator.next(), "rose js rice 0779139d931~r",  "12.88", null, 44);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "25.76",53);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "25.76",45);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 1 37~66 199 rt 0001",65);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "2",68);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "appro val i 00~) 0 0",50);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/10/12",77);

    }

    @Test
    public void receipt_2015_04_04_21_55_06()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_55_06, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "no purchase necessary . m    at h s kill", null, null, 7);
//        verifyParsedItem(iterator.next(), "q]i g lb]q ent r ies rec ei ve d .    ~ul l", null, null, 13);
        verifyParsedItem(iterator.next(), "magicjack",  "69.96", null, 38);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "73.46",41);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "***********************************",4);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "69.96",39);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137'166199 rt 0001",53);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "1",56);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval # 0'1'1118",44);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/1/24",64);

    }

    @Test
    public void receipt_2015_04_04_21_55_14()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_55_14, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(11,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "red goodness 007146401710r",  "3.47", null, 11);
        verifyParsedItem(iterator.next(), "bh juice",  "3.47", null, 12);
        verifyParsedItem(iterator.next(), "abbev.dep",  "0.10", null, 13);
        verifyParsedItem(iterator.next(), "cf 680g",  "4.97", null, 17);
        verifyParsedItem(iterator.next(), "mw straw",  "4.94", null, 18);
        verifyParsedItem(iterator.next(), "yellow onion 000000004093r",  "1.96", null, 22);
        verifyParsedItem(iterator.next(), "3 at",  "0.57", null, 26);
        verifyParsedItem(iterator.next(), "red grape",  "7.14", null, 27);
        verifyParsedItem(iterator.next(), "mushroom",  "1.77", null, 29);
        verifyParsedItem(iterator.next(), "mushroom",  "1.77", null, 30);
        verifyParsedItem(iterator.next(), "plant",  "9.88", null, 32);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "71.46",42);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "70.27",33);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137466199 rt 0001",54);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "21",57);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval # 042427",45);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2015/2/1",68);

    }

    @Test
    public void receipt_2015_04_04_21_58_55()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_58_55, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(1,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "-    - \"'llllllllllllllllllluillllllluill", null, null, 1);
//        verifyParsedItem(iterator.next(), "w    gift cards", null, null, 6);
//        verifyParsedItem(iterator.next(), "no purchase necessar ~ired",  "math", null, 16);
//        verifyParsedItem(iterator.next(), ". open    to", null, null, 17);
//        verifyParsedItem(iterator.next(), "winn ln9 depe .    r ecei ved . ful l", null, null, 22);
//        verifyParsedItem(iterator.next(), "please retel~ of    receipt for the", null, null, 29);
//        verifyParsedItem(iterator.next(), "en tren..    ", null, null, 35);
//        verifyParsedItem(iterator.next(), "enter for a chance to win 1 o",  "3", null, 41);
//        verifyParsedItem(iterator.next(), "general mdse total",  "25.17", null, 59);
//        verifyParsedItem(iterator.next(), "ref #", null, null, 63);
        //TODO why missed?
        verifyParsedItem(iterator.next(), "acc kit",  "39.98", null, 77);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "110.63",80);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "105.36",78);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst 5%                             $5.27",79);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval #                           0:~2693",61);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2011/9/13",69);

    }

    @Test
    public void receipt_2015_04_04_21_59_26()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_59_26, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(10,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "to enter, please co~plete a    s urve~", null, null, 1);
//        verifyParsedItem(iterator.next(), "about tode~'s store visit    ~+ :", null, null, 2);
//        verifyParsedItem(iterator.next(), "majori t ~. survey m    ust be taken", null, null, 11);
//        verifyParsedItem(iterator.next(), "winning depe nd on t he num    ber of", null, null, 13);
        verifyParsedItem(iterator.next(), "princs appl",  "15.74", null, 37);
        verifyParsedItem(iterator.next(), "metalc beads 0061550615&3",  "9.97", null, 38);
        verifyParsedItem(iterator.next(), "cil woodcare",  "29.96", null, 39);
        verifyParsedItem(iterator.next(), "ab ecofee",  "0.75", null, 40);
        verifyParsedItem(iterator.next(), "cil woodcare",  "29.96", null, 41);
        verifyParsedItem(iterator.next(), "ab ecofee",  "0.75", null, 42);
        verifyParsedItem(iterator.next(), "paint",  "16.96", null, 43);
        verifyParsedItem(iterator.next(), "ab ecofee",  "0.25", null, 44);
        verifyParsedItem(iterator.next(), "paint",  "16.96", null, 45);
        verifyParsedItem(iterator.next(), "ab ecofee",  "0.25", null, 46);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "145.39",50);
//        verifyParsedField(fieldValues, ReceiptFieldType.Account, "***************************** *****",5);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "38.47",48);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137166199 rt 0001",62);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "11",65);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval # 030316",53);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/30",71);

    }

    @Test
    public void receipt_2015_04_04_21_59_30()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_59_30, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(0,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "ijal-hart st",  "0re.0", null, 8);
//        verifyParsedItem(iterator.next(), "general hose total",  "8.34", null, 13);
//        verifyParsedItem(iterator.next(), "walmart hc", null, null, 14);
//        verifyParsedItem(iterator.next(), "ref #", null, null, 16);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval i                      078709",15);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/8/1",23);
        //TODO what is general hose total? it is not total.
//        verifyParsedField(fieldValues, ReceiptFieldType.Total, "8.34",13);
    }

    @Test
    public void receipt_2015_04_04_21_59_40()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_21_59_40, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(13,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "~ iiiiiiix k~    ",  "-i.0", null, 1);
//        verifyParsedItem(iterator.next(), "enter for a chanc i:f hj tjl rf 1 of",  "0.5", null, 2);
//        verifyParsedItem(iterator.next(), "s1000 cdn wal- mart g",  "ift.0", null, 3);
//        verifyParsedItem(iterator.next(), "no pur c hase nec ess ar",  "0.1", null, 10);
//        verifyParsedItem(iterator.next(), "i e s ! 1nq '~ u e s iloi1 i e 4 11 11 !d    l_ l, 't' ll ~ ~", null, null, 11);
//        verifyParsedItem(iterator.next(), "ca11ad jd1 r ~~ ~~j ..,.. ,,i t. .    r", null, null, 12);
//        verifyParsedItem(iterator.next(), "wt nn t n<j de1'e11d r)n fil l- :: ..,,:, ..    i", null, null, 16);
//        verifyParsedItem(iterator.next(), "the",  "l.0", null, 19);
//        verifyParsedItem(iterator.next(), "proud to b    e a part of", null, null, 39);
        verifyParsedItem(iterator.next(), "slr boy/girl 00451 02447b7r",  "37.00", null, 43);
        verifyParsedItem(iterator.next(), "cherries",  "4.47", null, 47);
        verifyParsedItem(iterator.next(), "wand",  "15.00", null, 50);
        verifyParsedItem(iterator.next(), "window panel 00731b10291jr",  "12.00", null, 54);
        verifyParsedItem(iterator.next(), "square",  "9.98", null, 55);
        verifyParsedItem(iterator.next(), "tool set",  "9.96", null, 56);
        verifyParsedItem(iterator.next(), "ky hk esp",  "8.96", null, 57);
        verifyParsedItem(iterator.next(), "robe hook",  "6.96", null, 58);
        verifyParsedItem(iterator.next(), "aw candle    00bl3388~6lar",  "7.94", null, 59);
        verifyParsedItem(iterator.next(), "pliers",  "13.88", null, 60);
        verifyParsedItem(iterator.next(), "footwear",  "9.00", null, 61);
        verifyParsedItem(iterator.next(), "footwear",  "9.00", null, 63);
        verifyParsedItem(iterator.next(), "aj comp",  "3.47", null, 65);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "322.54",74);
//        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*****~***********~** * * *h w ki m ~~* k*",7);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "307.56",66);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137'166199 rt 0001",86);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "#        items sold",89);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval # 038797",77);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/7/25",96);

    }

    @Test
    public void receipt_2015_04_04_22_03_34()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_03_34, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "within 2 w    eeks of todaw . odds or", null, null, 14);
        verifyParsedItem(iterator.next(), "2 milk",  "4.54", null, 40);
        verifyParsedItem(iterator.next(), "red grape",  "4.43", null, 43);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "12.44",48);
//        verifyParsedField(fieldValues, ReceiptFieldType.Account, "**********************************",7);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "12.29",46);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137466199 rt 0001",60);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "5",63);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval i 034244",51);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/10/12",69);

    }

    @Test
    public void receipt_2015_04_04_22_03_43()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_03_43, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(3,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "4.5m all crd 006296ot\"f265otr",  "12.96", null, 43);
        verifyParsedItem(iterator.next(), "6 outlt 6ft 0078693ot1ot13r",  "11.98", null, 44);
        verifyParsedItem(iterator.next(), "strainer",  "6.97", null, 45);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "66.43",52);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "***********************************",4);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "63.27",50);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137ot66199 rt 0001",64);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "8",67);
        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approval i 079819",55);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/12/9",72);

    }

    @Test
    public void receipt_2015_04_04_22_03_51()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2015_04_04_22_03_51, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("walmart", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        //TODO receipts are ocr-ed to break lines
        assertEquals(2,receipt.getItems().size());
//        verifyParsedItem(iterator.next(), "****~uu~****wnkm** m ~~*i    **********", null, null, 3);
//        verifyParsedItem(iterator.next(), "cdn    al- hart    gi~t",  "cards", null, 5);
//        verifyParsedItem(iterator.next(), "to t!nh r, pj. ,eene",  "c.0", null, 6);
//        verifyParsedItem(iterator.next(), "we",  "wf-n.0", null, 10);
//        verifyParsedItem(iterator.next(), "ple~ ~ e    reta in t hi s receipt for t he", null, null, 24);
//        verifyParsedItem(iterator.next(), "p    rpos ~s of cw~p l e ting", null, null, 25);
        verifyParsedItem(iterator.next(), "rubber flrmt 00 3:129 ~12 1 57 d",  "6.97", null, 43);
        verifyParsedItem(iterator.next(), "acc kit",  "39.98", null, 53);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "5110.63",56);//TODO $ was mistakenly recognized as 5, use subtotal to correct it
//        verifyParsedField(fieldValues, ReceiptFieldType.Account, "**** ~ ** * ***~~ ***** ***************",9);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "105.36",54);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "gst/hst 137466 199 rt 0001",69);
//        verifyParsedField(fieldValues, ReceiptFieldType.Approved, "approviil i 0011()41",60);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "",-1);

    }


}
