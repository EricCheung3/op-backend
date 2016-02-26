package com.openprice.parser.price;

import com.openprice.common.StringCommon;
import com.openprice.parser.api.Product;
import com.openprice.parser.data.ProductImpl;

import lombok.Data;

@Data
public class ProductPrice {

    Product product=ProductImpl.emptyProduct();
    String price=StringCommon.EMPTY;
    boolean productIsInCatalog=false; //from a matched product in catalog or not

    public ProductPrice(final Product product, final String price){
        this.product=product;
        this.price=price;
    }

    public static ProductPrice fromNameOnly(final String name){
        return new ProductPrice(ProductImpl.fromNameOnly(name), StringCommon.EMPTY);
    }

    public ProductPrice(final Product product, final String price,
            final boolean productIsInCatalog){
        this(product, price);
        this.productIsInCatalog=productIsInCatalog;
    }

    public static ProductPrice emptyValue(){
        return new ProductPrice(ProductImpl.emptyProduct(), StringCommon.EMPTY, false);
    }

    public String getName(){return product.getName();}
    public String getNumber(){return product.getNumber();}

    public boolean isEmpty(){
        return product.isEmpty() && price.isEmpty();
    }

    public String toCatalogCode(){
        if(productIsInCatalog)
            return product.toCatalogCode();
        return StringCommon.EMPTY;
    }
}
