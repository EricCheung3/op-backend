package com.openprice.parser.price;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.openprice.parser.data.ProductImpl;

public class ProductPriceTest {

    //test default for boolean is false
    @Test
    public void test1() throws Exception{
        ProductPrice pPrice=new ProductPrice(ProductImpl.emptyProduct(), "1.0", false);
        assertTrue(!pPrice.isProductIsInCatalog());
    }

    @Test
    public void cutHeadItemNumberTest1() throws Exception{
        final String number="0000";
        final String name="ABC";
        final String[] words=ProductPrice.cutHeadItemNumber(number+name);
        assertEquals(number, words[0]);
        assertEquals(name, words[1]);
    }

    @Test
    public void cutHeadItemNumberTest2() throws Exception{
        final String number="001500";
        final String name="ABC";
        final String[] words=ProductPrice.cutHeadItemNumber(number+name);
        assertEquals(number, words[0]);
        assertEquals(name, words[1]);
    }

    //TODO: this case may make sense to return an item number
    @Test(expected=Exception.class)
    public void cutHeadItemNumberTest3() throws Exception{
        final String number="001S00";
        final String name="ABC";
        ProductPrice.cutHeadItemNumber(number+name);
    }

    @Test(expected=Exception.class)
    public void cutHeadItemNumberTest4() throws Exception{
        final String number="0";
        final String name="ABC";
        ProductPrice.cutHeadItemNumber(number+name);
    }


    @Test(expected=Exception.class)
    public void cutHeadItemNumberTest5() throws Exception{
        final String number="";
        final String name="ABC";
        ProductPrice.cutHeadItemNumber(number+name);
    }


    @Test(expected=Exception.class)
    public void cutHeadItemNumberTest6() throws Exception{
        final String number="00";
        final String name="ABC";
        ProductPrice.cutHeadItemNumber(number+name);
    }

    @Test
    public void cutHeadItemNumberTest7() throws Exception{
        final String number="00900099500";
        final String name="ABC";
        final String[] words=ProductPrice.cutHeadItemNumber(number+name);
        assertEquals(number, words[0]);
        assertEquals(name, words[1]);
    }

    @Test
    public void cutHeadItemNumberTest8() throws Exception{
        final String number="00         900099    500";
        final String name="ABC";
        final String[] words=ProductPrice.cutHeadItemNumber(number+name);
        assertEquals(number, words[0]);
        assertEquals(name, words[1]);
    }

    @Test
    public void cutHeadItemNumberTest9() throws Exception{
        final String number="   00         900099    500    ";
        final String name="ABC ";
        final String[] words=ProductPrice.cutHeadItemNumber(number+name);
        assertEquals(number.trim(), words[0]);
        assertEquals(name.trim(), words[1]);
    }

    @Test
    public void cutHeadItemNumberTest10() throws Exception{
        final String number="   00 0 1   ";
        final String name="ABC ";
        final String[] words=ProductPrice.cutHeadItemNumber(number+name);
        assertEquals(number.trim(), words[0]);
        assertEquals(name.trim(), words[1]);
    }

    @Test(expected=Exception.class)
    public void cutHeadItemNumberTest11() throws Exception{
        final String number=" 00 ";
        final String name="A B C";
        ProductPrice.cutHeadItemNumber(number+name);
    }

    @Test
    public void cutTailItemNumberTest1() throws Exception{
        final String number="00900099500";
        final String name="ABC";
        final String[] words=ProductPrice.cutTailItemNumber(name+number);
        assertEquals(name, words[0]);
        assertEquals(number, words[1]);
    }

    @Test
    public void cutTailItemNumberTest2() throws Exception{
        final String number="0090";
        final String name="ABC";
        final String[] words=ProductPrice.cutTailItemNumber(name+number);
        assertEquals(name, words[0]);
        assertEquals(number, words[1]);
    }

    @Test(expected=Exception.class)
    public void cutTailItemNumberTest3() throws Exception{
        final String number="009";
        final String name="ABC";
        ProductPrice.cutTailItemNumber(name+number);
    }

    @Test(expected=Exception.class)
    public void cutTailItemNumberTest4() throws Exception{
        final String number="0";
        final String name="ABC";
        ProductPrice.cutTailItemNumber(name+number);
    }

    @Test(expected=Exception.class)
    public void cutTailItemNumberTest5() throws Exception{
        final String number="";
        final String name="ABC";
        ProductPrice.cutTailItemNumber(name+number);
    }

    @Test
    public void cutTailItemNumberTest6() throws Exception{
        final String number="0 0 0 9";
        final String name="ABC";
        final String[] words=ProductPrice.cutTailItemNumber(name+number);
        assertEquals(name, words[0]);
        assertEquals(number, words[1]);
    }

    @Test
    public void cutTailItemNumberTest7() throws Exception{
        final String number="0 0 0 9 ";
        final String name="ABC";
        final String[] words=ProductPrice.cutTailItemNumber(name+number);
        assertEquals(name, words[0]);
        assertEquals(number.trim(), words[1]);
    }

    @Test
    public void cutTailItemNumberTest8() throws Exception{
        final String number="0           0 0   9 ";
        final String name="ABC";
        final String[] words=ProductPrice.cutTailItemNumber(name+number);
        assertEquals(name, words[0]);
        assertEquals(number.trim(), words[1]);
    }

    @Test(expected=Exception.class)
    public void cutTailItemNumberTest10() throws Exception{
        final String number="0             0    9";
        final String name="ABC";
        ProductPrice.cutTailItemNumber(name+number);
    }

    @Test
    public void fromNameFirstTest1() throws Exception{
        final String number="0           0 0   9 ";
        final String name="AB C ";
        final String price="0.1   ";
        final String itemName=number+name;
        final ProductPrice pPrice=ProductPrice.fromNameCut(itemName, "", price);
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
