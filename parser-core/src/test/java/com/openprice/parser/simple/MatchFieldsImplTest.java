package com.openprice.parser.simple;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.junit.Test;

import com.openprice.parser.ReceiptDataImpl;
import com.openprice.parser.StoreConfigImpl;
import com.openprice.parser.api.MatchedRecord;
import com.openprice.parser.api.ReceiptData;
import com.openprice.parser.api.StoreConfig;
import com.openprice.parser.api.StoreParser;
import com.openprice.parser.generic.GenericParser;
import com.openprice.parser.price.PriceParserWithCatalog;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatchFieldsImplTest {

    @Test
    public void onlyFirstLineNeedToBeTreatedForTotalAndTotalSold() throws Exception{

        final MatchedRecord record = new MatchedRecordImpl();
        final List<String> lines = new ArrayList<>();
        lines.add("Total Number of Sold Items: 10");
        lines.add("fish A: $3.0");
        lines.add("fish B: $1.0");
        lines.add("C: $1.0");
        lines.add("D: $1.0");
        lines.add("Total: $10.0");
        final ReceiptData receipt = ReceiptDataImpl.fromContentLines(lines);

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
        final StoreParser parser = new GenericParser(config, PriceParserWithCatalog.emptyCatalog());
        final Set<Integer> treatedLines = MatchFieldsImpl.cleanTextToTreated(record, receipt, parser);
        assertEquals(1, treatedLines.size());
        assertTrue(treatedLines.contains(0));

    }
}
