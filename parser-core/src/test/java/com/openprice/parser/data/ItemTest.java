package com.openprice.parser.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ItemTest {

    @Test
    public void testNotNull(){
        final Item item=Item.fromNameProductCategoryBuyPUnitPWeightCategory(
                "name",
                "productCategory",
                "unitPrice",
                "weight",
                "category");
        assertNotNull(item);
    }

    @Test
    public void test1(){
        final Item item=Item.fromNameProductCategoryBuyPUnitPWeightCategory(
                "name",
                "productCategory",
                "unitPrice",
                "weight",
                "category");
        assertEquals("name", item.getProduct().getName());
        assertEquals("", item.getProduct().getNumber());
        assertEquals("", item.getBuyPrice());
        assertEquals("productCategory", item.getProductCategory());
        assertEquals("unitPrice", item.getUnitPrice());
        assertEquals("weight", item.getWeight());
        assertEquals("category", item.getCategory());
        assertEquals("", item.getRegPrice());
        assertEquals("", item.getSaving());
    }
}
