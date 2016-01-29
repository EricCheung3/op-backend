package com.openprice.parser.store.edoJapan;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.openprice.parser.ReceiptData;
import com.openprice.parser.StoreConfig;
import com.openprice.parser.StoreParser;
import com.openprice.parser.store.ParserSelectorIntegrationTest;
import com.openprice.parser.store.edojapan.EdoJapanSelector;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class EdoJapanSelectorTest extends ParserSelectorIntegrationTest{

    @Inject
    protected EdoJapanSelector edoSelector;

    private final List<String> atLeast5Lines=new ArrayList<String>();

    @Before
    public void init(){
        atLeast5Lines.add("1");
        atLeast5Lines.add("1");
        atLeast5Lines.add("1");
        atLeast5Lines.add("1");
        atLeast5Lines.add("1");
    }

    @Test
    public void parserNotNull() throws Exception{
        ReceiptData data=ReceiptData.fromContentLines(atLeast5Lines);
        final StoreParser parser=edoSelector.selectParser(data);
        assertNotNull(parser);
    }

    @Test
    public void storeConfigNotNull() throws Exception{
        ReceiptData data=ReceiptData.fromContentLines(atLeast5Lines);
        final StoreParser parser=edoSelector.selectParser(data);
        final StoreConfig config=parser.getStoreConfig();
        assertNotNull(config);
    }


    @Test
    public void storeConfigBlackListIsNotEmpty() throws Exception{
        ReceiptData data=ReceiptData.fromContentLines(atLeast5Lines);
        final StoreParser parser=edoSelector.selectParser(data);
        final StoreConfig config=parser.getStoreConfig();

        log.debug("config.getCatalogFilter().getBlackList().size()="+config.getCatalogFilter().getBlackList().size());
        assertTrue(config.getCatalogFilter().getBlackList().size()>0);
    }
}