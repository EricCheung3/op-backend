package com.openprice.parser.simple;

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

import com.openprice.common.TextResourceUtils;
import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.data.Item;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {StoreParserTestApplication.class})
public class CheapParserTest {
    @Value("classpath:/testFiles/unknown/2015_02_09_11_34_51.jpg.hengshuai.txt")
    private Resource edoJapan1;

    @Inject
    SimpleParser simpleParser;

    @Test
    public void testUnknown_EdoJapan() throws Exception {
        final List<String> receiptLines = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(edoJapan1, (line)-> receiptLines.add(line));

        assertTrue(receiptLines.size() > 0);

        ParsedReceipt receipt = simpleParser.parse(receiptLines);
        assertTrue(receipt.getItems().size() > 0);
        System.out.println("Items parsed:");
        for (Item item : receipt.getItems()) {
            System.out.println(item.getProduct());
        }

        receipt.getFieldToValueMap()
               .entrySet()
               .forEach(e->System.out.println(e.getKey()+"->"+e.getValue()));

    }

}
