package com.openprice.parser.exception;

@SuppressWarnings("serial")
public class HeaderOfLineNotFoundException extends Exception {
    public HeaderOfLineNotFoundException(String msg){
        super(msg);
    }
}
