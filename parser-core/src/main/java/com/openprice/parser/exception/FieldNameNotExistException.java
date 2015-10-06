package com.openprice.parser.exception;

@SuppressWarnings("serial")
public class FieldNameNotExistException extends Exception {
    public FieldNameNotExistException(String msg){
        super(msg);
    }
}
