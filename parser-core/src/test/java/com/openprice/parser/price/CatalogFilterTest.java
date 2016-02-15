package com.openprice.parser.price;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.openprice.parser.api.CatalogFilter;

public class CatalogFilterTest {

    @Test
    public void notCatalogItemNameTest1() throws Exception{
        final CatalogFilter filter=CatalogFilterImpl.emptyFilter();
        assertTrue(filter.matchesBlackList(" "));
        assertTrue(filter.matchesBlackList(" "));
        assertTrue(filter.matchesBlackList(""));
        assertTrue(filter.matchesBlackList("1"));
        assertTrue(filter.matchesBlackList("1233343"));
        assertTrue(filter.matchesBlackList("a"));
        assertTrue(filter.matchesBlackList("b"));
        assertTrue(filter.matchesBlackList("~~~"));

        assertTrue(!filter.matchesBlackList("ab"));
        assertTrue(!filter.matchesBlackList("abc"));
        assertTrue(!filter.matchesBlackList("a b "));
        assertTrue(!filter.matchesBlackList("WA"));
        assertTrue(!filter.matchesBlackList("W@A"));
        assertTrue(!filter.matchesBlackList("W@@!!A"));
        assertTrue(!filter.matchesBlackList("WT    ORGANIC ZUCCHINI"));
    }


}

