package com.openprice.parser.price;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PriceParserWithCatalogTest {

    PriceParserWithCatalog parser =PriceParserWithCatalog.emptyCatalog();

    @Test
    public void withWideSpacesIsOkay(){
        final String line="pepsi 591ml         6900000991                                   $2.39 GC";
        final ProductPrice pp= parser.parsePriceLine(line);
        assertEquals("pepsi 591ml", pp.getName());
        assertEquals("6900000991", pp.getNumber());
        assertEquals("2.39", pp.getPrice());
    }

    @Test
    public void noWideSpacesIsOkay(){
        final String line="7040054391580 RIDER INSULATE $179.99 16  ";
        final ProductPrice pp= parser.parsePriceLine(line);
        assertEquals("RIDER INSULATE", pp.getName());
        assertEquals("7040054391580", pp.getNumber());
        assertEquals("179.9916", pp.getPrice());
    }

    @Test
    public void noWideSpacesIsOkay2(){
        final String line="RIDER INSULATE $179.99 16  ";
        final ProductPrice pp= parser.parsePriceLine(line);
        assertEquals("RIDER INSULATE", pp.getName());
        assertEquals("", pp.getNumber());
        assertEquals("179.9916", pp.getPrice());
    }
}
