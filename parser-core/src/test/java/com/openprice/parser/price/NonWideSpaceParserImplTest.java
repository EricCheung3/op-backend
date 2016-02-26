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
    public void fromNameFirstTest1() throws Exception{
        final String number="0           0 0   9 ";
        final String name="AB C ";
        final String price="0.1   ";
        final String itemName=number+name;
        final SplittingFeatures splitF = new SplittingFeatures(name+number+price);

        assertEquals(number.trim(), pPrice.getNumber());
        assertEquals(name.trim(), pPrice.getName());
        assertEquals(price.trim(), pPrice.getPrice());
    }

    @Test
    public void fromNameFirstTest2() throws Exception{
        final String number="0           0 0   9 ";
        final String name="AB C ";
        final String price="0.1   ";
        final String itemName=number+name;
        final ProductPrice pPrice=ProductPrice.fromNameCut(itemName, "", price);
        assertEquals(number.trim(), pPrice.getNumber());
        assertEquals(name.trim(), pPrice.getName());
        assertEquals(price.trim(), pPrice.getPrice());
    }

    //This shows that name cut won't be applied if there is already a number provided
    @Test
    public void fromNameFirstTest3() throws Exception{
        final String number="0           0 0   9 ";
        final String number2="00001 ";
        final String name="AB C ";
        final String price="0.1   ";
        final String itemName=number+name;
        final ProductPrice pPrice=ProductPrice.fromNameCut(itemName, number2, price);
        assertEquals(number2.trim(), pPrice.getNumber());
        assertEquals(itemName.trim(), pPrice.getName());
        assertEquals(price.trim(), pPrice.getPrice());
    }

    @Test
    public void fromNameFirstTest4() throws Exception{
        final String number="0           0 0  ";
        final String name="AB C ";
        final String price="0.1   ";
        final String itemName=number+name;
        final ProductPrice pPrice=ProductPrice.fromNameCut(itemName, "", price);
        assertEquals("", pPrice.getNumber());
        assertEquals(itemName.trim(), pPrice.getName());
        assertEquals(price.trim(), pPrice.getPrice());
    }

    @Test
    public void fromNameFirstTest5() throws Exception{
        final String number="    ";
        final String name="AB C ";
        final String price="0.1   ";
        final String itemName=number+name;
        final ProductPrice pPrice=ProductPrice.fromNameCut(itemName, "", price);
        assertEquals("", pPrice.getNumber());
        assertEquals(itemName.trim(), pPrice.getName());
        assertEquals(price.trim(), pPrice.getPrice());
    }

    @Test
    public void fromNameCutTestTrailingCharGInPriceIsChangedTo9() throws Exception{
        final String number="    ";
        final String name="AB C ";
        final String price="0.1 gc   ";
        final String itemName=number+name;
        final ProductPrice pPrice=ProductPrice.fromNameCut(itemName, "", price);
        assertEquals("", pPrice.getNumber());
        assertEquals(itemName.trim(), pPrice.getName());
        assertEquals("0.19", pPrice.getPrice());
    }

    @Test
    public void fromNameCutTestTrailingCharGInPriceShouldRemoved() throws Exception{
        final String number="    ";
        final String name="AB C ";
        final String price="0.1 G   ";
        final String itemName=number+name;
        final ProductPrice pPrice=ProductPrice.fromNameCut(itemName, "", price);
        assertEquals("", pPrice.getNumber());
        assertEquals(itemName.trim(), pPrice.getName());
        assertEquals("0.1", pPrice.getPrice());
    }

    @Test
    public void fromNameCutTestTrailingCharInPriceShouldRemoved() throws Exception{
        final String number="    ";
        final String name="AB C ";
        final String price="0.1 c   ";
        final String itemName=number+name;
        final ProductPrice pPrice=ProductPrice.fromNameCut(itemName, "", price);
        assertEquals("", pPrice.getNumber());
        assertEquals(itemName.trim(), pPrice.getName());
        assertEquals("0.1", pPrice.getPrice());
    }


    @Test
    public void fromNameCutTesDollarInPriceShouldRemoved() throws Exception{
        final String number="    ";
        final String name="AB C ";
        final String price="$0.1 c   ";
        final String itemName=number+name;
        final ProductPrice pPrice=ProductPrice.fromNameCut(itemName, "", price);
        assertEquals("", pPrice.getNumber());
        assertEquals(itemName.trim(), pPrice.getName());
        assertEquals("0.1", pPrice.getPrice());
    }

    @Test
    public void fromNameCutTesDollarInPriceShouldRemoved2() throws Exception{
        final String number="    ";
        final String name="AB C ";
        final String price=" $ 0.1   ";
        final String itemName=number+name;
        final ProductPrice pPrice=ProductPrice.fromNameCut(itemName, "", price);
        assertEquals("", pPrice.getNumber());
        assertEquals(itemName.trim(), pPrice.getName());
        assertEquals("0.1", pPrice.getPrice());
    }

    @Test
    public void fromNameCutTesDollarInPriceShouldRemoved3() throws Exception{
        final String number="    ";
        final String name="AB C ";
        final String price=" $ 7.84 GC  ";
        final String itemName=number+name;
        final ProductPrice pPrice=ProductPrice.fromNameCut(itemName, "", price);
        assertEquals("", pPrice.getNumber());
        assertEquals(itemName.trim(), pPrice.getName());
        assertEquals("7.84", pPrice.getPrice());
    }


    @Test
    public void fromNameFirstTest6() throws Exception{
        final String number=" 00   ";
        final String name="AB C ";
        final String price="0.1   ";
        final String itemName=number+name;
        final ProductPrice pPrice=ProductPrice.fromNameCut(itemName, "", price);
        assertEquals("", pPrice.getNumber());
        assertEquals(itemName.trim(), pPrice.getName());
        assertEquals(price.trim(), pPrice.getPrice());
    }

    @Test
    public void fromNameFirstTest7() throws Exception{
        final String number=" 00 00   00    000 ";
        final String name="AB C ";
        final String price=" 0.1   ";
        final String itemName=number+name;
        final ProductPrice pPrice=ProductPrice.fromNameCut(itemName, "", price);
        assertEquals(number.trim(), pPrice.getNumber());
        assertEquals(name.trim(), pPrice.getName());
        assertEquals(price.trim(), pPrice.getPrice());
    }

}
