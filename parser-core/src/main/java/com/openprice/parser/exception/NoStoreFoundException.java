package com.openprice.parser.exception;

@SuppressWarnings("serial")
public class NoStoreFoundException extends Exception {
    public NoStoreFoundException(String msg){
        super(msg);
    }
}
