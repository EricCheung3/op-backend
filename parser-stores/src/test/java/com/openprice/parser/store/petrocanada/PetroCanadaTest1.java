package com.openprice.parser.store.petrocanada;

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
public class PetroCanadaTest1 extends AbstractReceiptParserIntegrationTest{

    @Value("classpath:/testFiles/petrocanada/2014_12_06_23_11_47.jpg.hengshuai.txt")
    private Resource receipt_2014_12_06_23_11_47;

    @Test
    public void receipt_2014_12_06_23_11_47()  throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(receipt_2014_12_06_23_11_47, (line)-> receiptLines.add(line));
        assertTrue(receiptLines.size() > 0);
        ParsedReceipt receipt = simpleParser.parseLines(receiptLines);
        printResult(receipt);
        assertEquals("petrocanada", receipt.getChainCode());
        Iterator<ParsedItem> iterator = receipt.getItems().iterator();
        Map<ReceiptFieldType, ParsedField> fieldValues = receipt.getFields();
        assertEquals(2,receipt.getItems().size());
        verifyParsedItem(iterator.next(), "pump",  "04", null, 15);
        verifyParsedItem(iterator.next(), "fuel sales",  "40.10", null, 19);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/11/14",13);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*******~********** **",52);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "litres         l 38.598",17);
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "* gst incl. $      1.91",25);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "40.10",20);
    }



}