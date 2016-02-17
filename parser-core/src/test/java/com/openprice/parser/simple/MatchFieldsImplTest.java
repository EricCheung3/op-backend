package com.openprice.parser.simple;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.openprice.parser.ReceiptDataImpl;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.StoreConfigImpl;
import com.openprice.parser.api.MatchFields;
import com.openprice.parser.api.MatchedRecord;
import com.openprice.parser.api.ReceiptData;
import com.openprice.parser.api.StoreConfig;
import com.openprice.parser.api.StoreParser;
import com.openprice.parser.data.StringInt;
import com.openprice.parser.generic.GenericParser;
import com.openprice.parser.price.PriceParserWithCatalog;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatchFieldsImplTest {

    private StoreParser parser;

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
        final Set<Integer> treatedLines = MatchFieldsImpl.cleanTextToTreated(record, receipt, parser);
        assertEquals(1, treatedLines.size());
        assertTrue(treatedLines.contains(0));
    }

    @Test
    public void totalAndTotalSoldAreBothCorrect() throws Exception{
        final MatchedRecord record = new MatchedRecordImpl();
        final List<String> lines = new ArrayList<>();
        lines.add("Total Number of Sold Items: 10");
        lines.add("fish A: $3.0");
        lines.add("fish B: $1.0");
        lines.add("C: $1.0");
        lines.add("D: $1.0");
        lines.add("Total: $3.0");
        final ReceiptData receipt = ReceiptDataImpl.fromContentLines(lines);
        final MatchFields match = new MatchFieldsImpl();
        match.matchToHeaders(record, receipt, parser);
        log.debug("match to header results: ");
        record.getFieldToValueLine().entrySet().forEach(e->log.debug(e.getKey()+"->"+e.getValue()));
        assertEquals(2, record.getFieldToValueLine().size());
        assertEquals(new StringInt("10", 0), record.getFieldToValueLine().get(ReceiptFieldType.TotalSold));
        assertEquals(new StringInt("3.0", 5), record.getFieldToValueLine().get(ReceiptFieldType.Total));
    }

    @Test
    public void totalAndTotalSoldAreBothCorrect2() throws Exception{
        final MatchedRecord record = new MatchedRecordImpl();
        final List<String> lines = new ArrayList<>();
        lines.add("Total Number Sold Ite: 10");
        lines.add("fish A: $3.0");
        lines.add("fish B: $1.0");
        lines.add("C: $1.0");
        lines.add("D: $1.0");
        lines.add("Total: $3.0");
        final ReceiptData receipt = ReceiptDataImpl.fromContentLines(lines);
        final MatchFields match = new MatchFieldsImpl();
        match.matchToHeaders(record, receipt, parser);
        log.debug("match to header results: ");
        record.getFieldToValueLine().entrySet().forEach(e->log.debug(e.getKey()+"->"+e.getValue()));
        assertEquals(2, record.getFieldToValueLine().size());
        assertEquals(new StringInt("10", 0), record.getFieldToValueLine().get(ReceiptFieldType.TotalSold));
        assertEquals(new StringInt("3.0", 5), record.getFieldToValueLine().get(ReceiptFieldType.Total));
    }

    @Test
    public void totalAndTotalSoldAreBothCorrect3() throws Exception{
        final MatchedRecord record = new MatchedRecordImpl();
        final List<String> lines = new ArrayList<>();
        lines.add("Total Number Sold Items h: 10");
        lines.add("fish A: $3.0");
        lines.add("fish B: $1.0");
        lines.add("C: $1.0");
        lines.add("D: $1.0");
        lines.add("Total: $3.0");
        final ReceiptData receipt = ReceiptDataImpl.fromContentLines(lines);
        final MatchFields match = new MatchFieldsImpl();
        match.matchToHeaders(record, receipt, parser);
        log.debug("match to header results: ");
        record.getFieldToValueLine().entrySet().forEach(e->log.debug(e.getKey()+"->"+e.getValue()));
        assertEquals(2, record.getFieldToValueLine().size());
        assertEquals(new StringInt("10", 0), record.getFieldToValueLine().get(ReceiptFieldType.TotalSold));
        assertEquals(new StringInt("3.0", 5), record.getFieldToValueLine().get(ReceiptFieldType.Total));
    }
}
