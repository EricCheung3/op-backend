package com.openprice.parser.exception;


/*
    an exception for weighted items, buy price should match weight * unit price.
 */
@SuppressWarnings("serial")
public class WeightedItemsPriceMismatchException extends Exception {
    public WeightedItemsPriceMismatchException(String message){
        super(message);
    }

}
