package com.openprice.parser.store.rcss;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.common.TextResourceUtils;
import com.openprice.parser.data.Item;
import com.openprice.parser.data.ReceiptField;
import com.openprice.parser.simple.SimpleParser;
import com.openprice.parser.store.StoreParserTestApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {StoreParserTestApplication.class})
public class RCSSTest {
    //@Value("classpath:/testFiles/RCSS/CalgaryTrail/2015_02_09_13_15_09.jpg.henryHuang.txt")
    @Value("classpath:/testFiles/RCSS/CalgaryTrail/2015_02_01_14_17_01.jpg.henryHuang.txt")
    private Resource sampleRCSS;

    @Inject
    SimpleParser simpleParser;

    @Test
    public void testRCSS() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(sampleRCSS, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parse(receiptLines);
        System.out.println("Items parsed:");
        for (Item item : receipt.getItems()) {
            System.out.println(item.getName());
        }
        System.out.println("\n*******\nFields parsed:");
        for (ReceiptField field : receipt.getFieldToValueMap().keySet()) {
            System.out.println(field.name() + " : " + receipt.getFieldToValueMap().get(field).getValue());
        }

    }
}
