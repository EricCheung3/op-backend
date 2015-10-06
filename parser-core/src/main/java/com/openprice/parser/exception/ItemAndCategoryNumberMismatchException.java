package com.openprice.parser.exception;


/*
    The number of Items And the number of Categories do not match
 */
@SuppressWarnings("serial")
public class ItemAndCategoryNumberMismatchException extends Exception {
    public ItemAndCategoryNumberMismatchException(String message){
        super(message);
    }

}
