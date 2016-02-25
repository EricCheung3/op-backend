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
        assertEquals(0,receipt.getItems().size());
        verifyParsedField(fieldValues, ReceiptFieldType.GstNumber, "* gst incl. $      1.91",25);
        verifyParsedField(fieldValues, ReceiptFieldType.Total, "40.10",20);
        verifyParsedField(fieldValues, ReceiptFieldType.Account, "*******~********** **",52);
        verifyParsedField(fieldValues, ReceiptFieldType.Cashier, "pump                   04",15);
        verifyParsedField(fieldValues, ReceiptFieldType.UnitPrice, "1.039",18);
        verifyParsedField(fieldValues, ReceiptFieldType.Date, "2014/11/14",13);
        verifyParsedField(fieldValues, ReceiptFieldType.TotalSold, "38.598",17);
        verifyParsedField(fieldValues, ReceiptFieldType.SubTotal, "40.10",19);
    }



}