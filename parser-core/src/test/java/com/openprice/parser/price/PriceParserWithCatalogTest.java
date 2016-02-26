package com.openprice.parser.price;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
    public void noItemNumberIsOkay(){
        final ProductPrice pp= parser.parsePriceLine(" RIDER INSULATE $179.99 16  ");
        assertEquals("RIDER INSULATE", pp.getName());
        assertEquals("", pp.getNumber());
        assertEquals("179.9916", pp.getPrice());
    }
//
//    @Test
//    public void headingNumberShouldNotBeInPrice(){
//        final ProductPrice pp= parser.parsePriceLine(" 20 DISC SHOPKINS MRKT GAME 19.99 G  ");
//        assertEquals("20 DISC SHOPKINS MRKT GAME", pp.getName());
//        assertEquals("", pp.getNumber());
//        assertEquals("19.99", pp.getPrice());
//    }

    @Test
    public void test12dfsf(){
        final String line="BOXTHORN FRUIT FRUCTU       S LYCIL $2.79 *";
        final ProductPrice pp= PriceParserWithCatalog.emptyCatalog().parsePriceLine(line);
//        assertEquals("BOXTHORN FRUIT FRUCTU    S LYCIL",pp.getName());
        assertEquals("BOXTHORN FRUIT FRUCTU    S LYCIL $2.79 *",pp.getName());
        //TODO
//        assertEquals("2.79",pp.getPrice());
        assertEquals("",pp.getPrice());
        assertEquals("",pp.getNumber());
    }

    @Test
    public void nameHasTrailingNumberTest1(){
        final String line = "380447 DUSTER 24         18.79 G";
        final ProductPrice pp= PriceParserWithCatalog.emptyCatalog().parsePriceLine(line);
        assertEquals("DUSTER 24",pp.getName());
        assertEquals("18.79",pp.getPrice());
        assertEquals("380447",pp.getNumber());
    }

    @Test
    public void nameHasTrailingNumberTest2(){
        final String line = "704012 WELCH'                         ; 50CT                9.99    G";
        final ProductPrice pp= PriceParserWithCatalog.emptyCatalog().parsePriceLine(line);
        assertEquals("WELCH'    ; 50CT",pp.getName());
        assertEquals("9.99",pp.getPrice());
        assertEquals("704012",pp.getNumber());
    }

    @Test
    public void nameHasTrailingNumberTest3(){
        final String line = "458 MILK 2%            4.54";
        final ProductPrice pp= PriceParserWithCatalog.emptyCatalog().parsePriceLine(line);
        assertEquals("458 MILK 2%",pp.getName());
        assertEquals("4.54",pp.getPrice());
        assertEquals("",pp.getNumber());
    }

    @Test
    public void rcssTest1(){
        final String line = "05719775555 RSTR INSTNT NDLE                   MRJ       0.98";
        final ProductPrice pp= PriceParserWithCatalog.emptyCatalog().parsePriceLine(line);
        assertEquals("RSTR INSTNT NDLE    MRJ",pp.getName());
        assertEquals("0.98",pp.getPrice());
        assertEquals("05719775555",pp.getNumber());
    }

    @Test
    public void rcssTest2(){
        final String line = "PLASTIC BAGS               GRO    0.05";
        final ProductPrice pp= PriceParserWithCatalog.emptyCatalog().parsePriceLine(line);
        assertEquals("PLASTIC BAGS    GRO",pp.getName());
        assertEquals("0.05",pp.getPrice());
        assertEquals("",pp.getNumber());
    }




//    @Test
//    public void noWideSpacesIsOkay(){
//        final ProductPrice pp= parser.parsePriceLine("7040054391580 RIDER INSULATE $179.99 16  ");
//        assertEquals("RIDER INSULATE", pp.getName());
//        assertEquals("7040054391580", pp.getNumber());
//        assertEquals("179.9916", pp.getPrice());
//    }
//
//    @Test
//    public void noWideSpacesIsOkay2(){
//        final ProductPrice pp= parser.parsePriceLine("RIDER INSULATE $179.99 16  ");
//        System.out.println(StringCommon.formatPrice("RIDER INSULATE $179.99 16  "));
//
//        assertEquals("RIDER INSULATE", pp.getName());
//        assertEquals("", pp.getNumber());
//        assertEquals("179.9916", pp.getPrice());
//    }
}
