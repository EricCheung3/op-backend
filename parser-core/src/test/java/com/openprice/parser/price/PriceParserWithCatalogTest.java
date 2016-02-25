package com.openprice.parser.price;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.openprice.common.StringCommon;

public class PriceParserWithCatalogTest {

    private final PriceParserWithCatalog parser =PriceParserWithCatalog.emptyCatalog();

    @Test
    public void withWideSpacesIsOkay(){
        final ProductPrice pp= parser.parsePriceLine("pepsi 591ml         6900000991                                   $2.39 GC");
        assertEquals("pepsi 591ml", pp.getName());
        assertEquals("6900000991", pp.getNumber());
        assertEquals("2.39", pp.getPrice());
    }

    @Test
    public void noWideSpacesIsOkay(){
        final ProductPrice pp= parser.parsePriceLine("7040054391580 RIDER INSULATE $179.99 16  ");
        assertEquals("RIDER INSULATE", pp.getName());
        assertEquals("7040054391580", pp.getNumber());
        assertEquals("179.9916", pp.getPrice());
    }

    @Test
    public void noWideSpacesIsOkay2(){
        final ProductPrice pp= parser.parsePriceLine("RIDER INSULATE $179.99 16  ");
        System.out.println(StringCommon.formatPrice("RIDER INSULATE $179.99 16  "));

        assertEquals("RIDER INSULATE", pp.getName());
        assertEquals("", pp.getNumber());
        assertEquals("179.9916", pp.getPrice());
    }
}
