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

    @Test
    public void numberNameNumberTest1() throws Exception{
        final String number="0           0 0   9 ";
        final String name="AB C ";
        final String price="0.1   ";
        final ProductPrice pp = parser.parse(number+name+price);
        assertEquals(number.trim(), pp.getNumber());
        assertEquals(name.trim(), pp.getName());
        assertEquals(price.trim(), pp.getPrice());
    }

    @Test
    public void nameNumberTest1() throws Exception{
        final String number="";
        final String name="AB C ";
        final String price="0.1   ";
        final ProductPrice pp = parser.parse(number+name+price);
        assertEquals(number.trim(), pp.getNumber());
        assertEquals(name.trim(), pp.getName());
        assertEquals(price.trim(), pp.getPrice());
    }

    @Test
    public void nameNumberDollarTest1() throws Exception{
        final String number="";
        final String name="AB C ";
        final String price="0.1$   ";
        final ProductPrice pp = parser.parse(number+name+price);
        assertEquals(number.trim(), pp.getNumber());
        assertEquals(name.trim(), pp.getName());
        assertEquals("0.1", pp.getPrice());
    }

    @Test
    public void nameNumberDollarTest2() throws Exception{
        final String number="";
        final String name="AB C ";
        final String price="$0.1  ";
        final ProductPrice pp = parser.parse(number+name+price);
        assertEquals(number.trim(), pp.getNumber());
        assertEquals(name.trim(), pp.getName());
        assertEquals("0.1", pp.getPrice());
    }

    //TODO
//    @Test
//    public void fromNameCutTestTrailingCharGInPriceIsChangedTo9() throws Exception{
//        final String number="";
//        final String name="AB C ";
//        final String price="0.1  gc ";
//        final ProductPrice pp = parser.parse(number+name+price);
//        assertEquals(number.trim(), pp.getNumber());
//        assertEquals(name.trim(), pp.getName());
//        assertEquals(price.trim(), pp.getPrice());
//    }

    @Test
    public void test1() throws Exception{
        final ProductPrice pp = parser.parse("20 DISC CONTINENTAL COOKIE                                        2.99");
        assertEquals("", pp.getNumber());
        assertEquals("20 DISC CONTINENTAL COOKIE", pp.getName());
        assertEquals("2.99", pp.getPrice());
    }


    @Test
    public void test2() throws Exception{
        final ProductPrice pp = parser.parse("20 DISC LOGIIX USB CUBE                                          29.99 G");
        assertEquals("", pp.getNumber());
        assertEquals("20 DISC LOGIIX USB CUBE", pp.getName());
        assertEquals("29.99", pp.getPrice());
    }



}
