package com.openprice.parser.simple;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.openprice.parser.ParsedItem;
import com.openprice.parser.ReceiptDataImpl;
import com.openprice.parser.StoreConfigImpl;
import com.openprice.parser.api.MatchedRecord;
import com.openprice.parser.api.ReceiptData;
import com.openprice.parser.api.StoreConfig;
import com.openprice.parser.api.StoreParser;
import com.openprice.parser.generic.GenericParser;
import com.openprice.parser.price.PriceParserWithCatalog;

public class SimpleParserUtilsTest {


    private StoreParser parser;

//    @Test
//    public void test(){
//        MatchFieldsImpl.matchesBlackListForDate("water", 0.67);
//    }

    @Before
    public void init(){
        final Properties prop = new Properties();
        prop.setProperty("TotalHeader", "Total");
        prop.setProperty("TotalSoldHeader", "Total Number Sold Items");
        prop.setProperty("SimilarityThresholdOfTwoStrings", "0.65");
        final StoreConfig config = StoreConfigImpl.fromPropCategorySkipBeforeAfterBlack(
                prop,
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>());
        parser = new GenericParser(config, PriceParserWithCatalog.emptyCatalog());
    }

    @Test
    public void anItemHas3LinesShouldBeFine() throws Exception{
        final MatchedRecord record = new MatchedRecordImpl();
        final List<String> lines = new ArrayList<>();
        lines.add("21-GROCERY");
        lines.add("07323400310 YELW CALROS          RICE   MR-.i");
        lines.add("$24.88 Int 4. $26.38 ea");
        lines.add("2 8 $24.88 list 4                              49.76");
        lines.add("690294490073 K DGON COOK         WINE   MRJ       2.69");
        final ReceiptData receipt = ReceiptDataImpl.fromContentLines(lines);
        final List<ParsedItem> items = SimpleParserUtils.parseItems(record, receipt, parser);
        System.out.println("items.size()="+items.size());
        items.forEach(i->System.out.println("name="+i.getParsedName()+", price="+i.getParsedBuyPrice()));
        assertEquals(2, items.size());
    }
}
