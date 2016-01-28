package com.openprice.parser.generic;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.common.TextResourceUtils;
import com.openprice.parser.simple.SimpleParser;
import com.openprice.parser.store.AbstractReceiptParserIntegrationTest;

@RunWith(SpringJUnit4ClassRunner.class)
public class UnknownStoreTest extends AbstractReceiptParserIntegrationTest {
    @Value("classpath:/testFiles/Generic/2015_05_02_21_56_44.jpg.momingzhen159.txt")
    private Resource timHortons1;

    @Inject
    SimpleParser simpleParser;

    @Test
    public void testReceipt1() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(timHortons1, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parse(receiptLines);
        printResult(receipt);
        System.out.println("parsed chain is "+receipt.getChain());

//        Iterator<Item> iterator = receipt.getItems().iterator();
//        assertEquals(5,receipt.getItems().size());
//        verifyItemParsedValue(iterator.next(), "chicken bbq roasted", "9.0", "chicken bbq roasted");
//        verifyItemParsedValue(iterator.next(), "clabatta buns 4pk", "2.50", "clabatta buns 4pk");
//        verifyItemParsedValue(iterator.next(), "clabatta buns 4pk", "2.50", "clabatta buns 4pk");
//        verifyItemParsedValue(iterator.next(), "spinach bunch", "1.49", "spinach bunch_4090");
//        verifyItemParsedValue(iterator.next(), "lucerne who1e mi1k4l", "3.79", "lucerne who1e mi1k4l");
//        //verifyItemParsedValue(iterator.next(), "+ehc", "0.08", "+ehc_76910 dp");
//        //     verifyItemParsedValue(iterator.next(), "♦deposit", "0.25", "♦deposit_96460 dp");
//
//        // verify parsed fields
//        Map<ReceiptField, ValueLine> fieldValues = receipt.getFieldToValueMap();
//        //        assertEquals(fieldValues.get(ReceiptField.AddressLine1).getValue(), "100a 5015");
//        //        assertEquals(fieldValues.get(ReceiptField.AddressCity).getValue(), "edmonton");
//        assertEquals(fieldValues.get(ReceiptField.Phone).getValue(), "780-435-5132");
//        assertEquals(fieldValues.get(ReceiptField.GstNumber).getValue(), "817093735");
//        assertEquals(fieldValues.get(ReceiptField.SubTotal).getValue(), "22.59");
//        assertEquals(fieldValues.get(ReceiptField.Total).getValue(), "23.09");
//        assertEquals(StringCommon.EMPTY, fieldValues.get(ReceiptField.Date).getValue());//this receipt has no date string
    }


}
