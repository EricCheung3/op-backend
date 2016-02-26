package com.openprice.parser.price;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NonWideSpaceParserImplTest {

    private NonWideSpaceParserImpl parser = new NonWideSpaceParserImpl();

    @Test
    public void numberNamePrice1(){
        final ProductPrice pp = parser.parse("1333 abc 1.3 ");
        assertEquals("1333", pp.getNumber());
        assertEquals("abc", pp.getName());
        assertEquals("1.3", pp.getPrice());
    }

    @Test
    public void numberNamePrice2(){
        final ProductPrice pp = parser.parse("13 abc 1.3 ");
        assertEquals("13", pp.getNumber());
        assertEquals("abc", pp.getName());
        assertEquals("1.3", pp.getPrice());
    }

    @Test
    public void namePrice1(){
        final ProductPrice pp = parser.parse("abc 1.3 ");
        assertEquals("", pp.getNumber());
        assertEquals("abc", pp.getName());
        assertEquals("1.3", pp.getPrice());
    }

    @Test
    public void numberNamePriceFromSportChek(){
        final ProductPrice pp = parser.parse("7040054391580 RIDER INSULATE $179.99 16 ");
        assertEquals("7040054391580", pp.getNumber());
        assertEquals("RIDER INSULATE", pp.getName());
        assertEquals("179.99 16", pp.getPrice());
    }

    @Test
    public void namePrice2(){
        final ProductPrice pp = parser.parse("RIDER INSULATE $179.99 16 ");
        assertEquals("", pp.getNumber());
        assertEquals("RIDER INSULATE", pp.getName());
        assertEquals("179.99 16", pp.getPrice());
    }

    @Test
    public void numberName1(){
        final ProductPrice pp = parser.parse("7040054391580 RIDER INSULATE ");
        assertEquals("7040054391580", pp.getNumber());
        assertEquals("RIDER INSULATE", pp.getName());
        assertEquals("", pp.getPrice());
    }

}
