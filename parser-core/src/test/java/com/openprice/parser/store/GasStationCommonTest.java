package com.openprice.parser.store;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GasStationCommonTest {

    @Test
    public void shouldRemoveHeaderAndDollar(){
        final List<String> headers = new ArrayList<>();
        headers.add("price/l");
        assertEquals("1.039", GasStationCommon.parseUnitPrice("price/l        $ 1.039", headers));
    }

    @Test
    public void shouldRemoveHeaderAndDollarMoreHeadersArefine(){
        final List<String> headers = new ArrayList<>();
        headers.add("price/l");
        headers.add("price/l agafdsfd");
        assertEquals("1.039", GasStationCommon.parseUnitPrice("price/l        $ 1.039", headers));
    }
}
