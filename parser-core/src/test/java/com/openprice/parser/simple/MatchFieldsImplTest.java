package com.openprice.parser.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.junit.Test;

import com.openprice.parser.ReceiptDataImpl;
import com.openprice.parser.ReceiptFieldType;
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
    public void cleanTextToTreated() throws Exception{

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
        prop.setProperty(ReceiptFieldType.Total.toString(), "Total");
        prop.setProperty(ReceiptFieldType.TotalSold.toString(), "Total Number Sold Items");
        final StoreConfig config = StoreConfigImpl.fromPropCategorySkipBeforeAfterBlack(
                prop,
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>());
        final StoreParser parser = new GenericParser(config, PriceParserWithCatalog.emptyCatalog());
        final Set<Integer> treateLines=MatchFieldsImpl.cleanTextToTreated(record, receipt, parser);
        log.debug(treateLines.toString());
    }
}
