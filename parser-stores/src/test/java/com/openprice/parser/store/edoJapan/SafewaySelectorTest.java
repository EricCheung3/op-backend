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
import com.openprice.parser.StoreConfig;
import com.openprice.parser.api.StoreParser;
import com.openprice.parser.store.ParserSelectorIntegrationTest;
import com.openprice.parser.store.safeway.SafewaySelector;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class SafewaySelectorTest extends ParserSelectorIntegrationTest{
    @Inject
    protected SafewaySelector selector;

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
        ReceiptDataImpl data=ReceiptDataImpl.fromContentLines(atLeast5Lines);
        final StoreParser parser=selector.selectParser(data);
        assertNotNull(parser);
    }

    @Test
    public void storeConfigNotNull() throws Exception{
        ReceiptDataImpl data=ReceiptDataImpl.fromContentLines(atLeast5Lines);
        final StoreParser parser=selector.selectParser(data);
        final StoreConfig config=parser.getStoreConfig();
        assertNotNull(config);
    }


    @Test
    public void storeConfigBlackListIsNotEmpty() throws Exception{
        ReceiptDataImpl data=ReceiptDataImpl.fromContentLines(atLeast5Lines);
        final StoreParser parser=selector.selectParser(data);
        final StoreConfig config=parser.getStoreConfig();

        log.debug("config.getCatalogFilter().getBlackList().size()="+config.getCatalogFilter().getBlackList().size());
        assertTrue(config.getCatalogFilter().getBlackList().size()>0);
    }

    @Test
    public void configHeaderAndPropertiesTest() throws Exception{
        ReceiptDataImpl data=ReceiptDataImpl.fromContentLines(atLeast5Lines);
        final StoreParser parser=selector.selectParser(data);
        final StoreConfig config=parser.getStoreConfig();
        //it's not in the config files, so it's null
        //System.out.println("config.refExample()"+config.refExample());

        //System.out.println("config.getProp().entrySet().size()"+config.getProp().entrySet().size());
        //System.out.println("config.similarityOfTwoStrings="+config.similarityThresholdOfTwoStrings());
    }
}
