package com.openprice.parser.price;

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

}
