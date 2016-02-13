package com.openprice.parser.api;

/**
 * represents a product in a catalog
 *
 */
public interface Product {

    //get product name
    String getName();

    //get product number
    String getNumber();

    String toStringNameFirst();

    String toStringNumberFirst();

    String toCatalogCode();

    boolean isEmpty();

}
