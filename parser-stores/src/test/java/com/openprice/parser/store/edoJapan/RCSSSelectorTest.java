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

import com.openprice.parser.ReceiptDataImpl;
import com.openprice.parser.api.ReceiptData;
import com.openprice.parser.api.StoreConfig;
import com.openprice.parser.api.StoreParser;
import com.openprice.parser.store.ParserSelectorIntegrationTest;
import com.openprice.parser.store.rcss.RCSSSelector;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class RCSSSelectorTest extends ParserSelectorIntegrationTest{
    @Inject
    protected RCSSSelector selector;

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
        ReceiptData data=ReceiptDataImpl.fromContentLines(atLeast5Lines);
        final StoreParser parser=selector.selectParser(data);
        assertNotNull(parser);
    }

    @Test
    public void storeConfigNotNull() throws Exception{
        ReceiptData data=ReceiptDataImpl.fromContentLines(atLeast5Lines);
        final StoreParser parser=selector.selectParser(data);
        final StoreConfig config=parser.getStoreConfig();
        assertNotNull(config);
    }

    @Test
    public void storeConfigBlackListIsNotEmpty() throws Exception{
        ReceiptData data=ReceiptDataImpl.fromContentLines(atLeast5Lines);
        final StoreParser parser=selector.selectParser(data);
        final StoreConfig config=parser.getStoreConfig();

        log.debug("config.getCatalogFilter().getBlackList().size()="+config.blackListSize());
        assertTrue(config.blackListSize()>0);
    }
}
