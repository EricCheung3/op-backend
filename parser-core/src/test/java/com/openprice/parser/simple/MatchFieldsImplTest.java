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
import com.openprice.parser.api.ReceiptLine;
import com.openprice.parser.api.StoreConfig;
import com.openprice.parser.api.StoreParser;
import com.openprice.parser.data.StringInt;
import com.openprice.parser.generic.GenericParser;
import com.openprice.parser.price.PriceParserWithCatalog;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatchFieldsImplTest {

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
        final Set<Integer> treatedLines = MatchFieldsImpl.totalTotalSoldTreatedLines(record, receipt, parser);
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

    @Test
    public void twoTotalLinesShouldSelectFirstLine() throws Exception{
        final MatchedRecord record = new MatchedRecordImpl();
        final List<String> lines = new ArrayList<>();
        lines.add("fish A: $3.0");
        lines.add("fish B: $1.0");
        lines.add("C: $1.0");
        lines.add("Total : $8.0");
        lines.add("D: $1.0");
        lines.add("Total: $10");
        final ReceiptData receipt = ReceiptDataImpl.fromContentLines(lines);
        final MatchFields match = new MatchFieldsImpl();
        match.matchToHeaders(record, receipt, parser);
        log.debug("match to header results: ");
        record.getFieldToValueLine().entrySet().forEach(e->log.debug(e.getKey()+"->"+e.getValue()));
        assertEquals(1, record.getFieldToValueLine().size());
        assertEquals(new StringInt("8.0", 3), record.getFieldToValueLine().get(ReceiptFieldType.Total));
    }

    @Test
    public void selectTotalLineAndParseTest1(){
        final MatchedRecord record = new MatchedRecordImpl();
        final ReceiptLine r1=new ReceiptLine(null, "Total", 2, null);
        final ReceiptLine r2=new ReceiptLine(null, "Total $3.0", 10, null);
        final List<ReceiptLine> linesOfTotal = new ArrayList<>();
        linesOfTotal.add(r1);
        linesOfTotal.add(r2);
        MatchFieldsImpl.selectTotalLineAndParse(record, parser, linesOfTotal);
        assertEquals(new StringInt("3.0", 10), record.getFieldToValueLine().get(ReceiptFieldType.Total));
    }

    @Test
    public void selectTotalLineAndParseTest2(){
        final MatchedRecord record = new MatchedRecordImpl();
        final ReceiptLine r1=new ReceiptLine(null, "Total", 2, null);
        final ReceiptLine r2=new ReceiptLine(null, "Total $3.0", 10, null);
        final List<ReceiptLine> linesOfTotal = new ArrayList<>();
        linesOfTotal.add(r2);
        linesOfTotal.add(r1);
        MatchFieldsImpl.selectTotalLineAndParse(record, parser, linesOfTotal);
        assertEquals(new StringInt("3.0", 10), record.getFieldToValueLine().get(ReceiptFieldType.Total));
    }


    @Test
    public void selectTotalLineAndParseTest3MoreNoisyLines(){
        final MatchedRecord record = new MatchedRecordImpl();
        final ReceiptLine r1=new ReceiptLine(null, "Total", 2, null);
        final ReceiptLine r2=new ReceiptLine(null, "Total $3.0", 10, null);
        final ReceiptLine r3=new ReceiptLine(null, "abadfvd dsafsddsds", 20, null);
        final List<ReceiptLine> linesOfTotal = new ArrayList<>();
        linesOfTotal.add(r1);
        linesOfTotal.add(r2);
        linesOfTotal.add(r3);
        MatchFieldsImpl.selectTotalLineAndParse(record, parser, linesOfTotal);
        assertEquals(new StringInt("3.0", 10), record.getFieldToValueLine().get(ReceiptFieldType.Total));
    }

    @Test
    public void selectTotalLineAndParseTest3OnlyOneGoodLine(){
        final MatchedRecord record = new MatchedRecordImpl();
        final ReceiptLine r2=new ReceiptLine(null, "Total $3.0", 10, null);
        final List<ReceiptLine> linesOfTotal = new ArrayList<>();
        linesOfTotal.add(r2);
        MatchFieldsImpl.selectTotalLineAndParse(record, parser, linesOfTotal);
        assertEquals(new StringInt("3.0", 10), record.getFieldToValueLine().get(ReceiptFieldType.Total));
    }

    @Test
    public void selectTotalLineAndParseTest4TwoGoodLinesShouldReturnTheOneWithSmallerLineNumber(){
        final MatchedRecord record = new MatchedRecordImpl();
        final ReceiptLine r1=new ReceiptLine(null, "Total", 2, null);
        final ReceiptLine r2=new ReceiptLine(null, "Total $3.0", 10, null);
        final ReceiptLine r3=new ReceiptLine(null, "abadfvd dsafsddsds", 20, null);
        final ReceiptLine r4=new ReceiptLine(null, "Total $4.0", 4, null);
        final List<ReceiptLine> linesOfTotal = new ArrayList<>();
        linesOfTotal.add(r1);
        linesOfTotal.add(r2);
        linesOfTotal.add(r3);
        linesOfTotal.add(r4);
        MatchFieldsImpl.selectTotalLineAndParse(record, parser, linesOfTotal);
        assertEquals(new StringInt("4.0", 4), record.getFieldToValueLine().get(ReceiptFieldType.Total));
    }
}
