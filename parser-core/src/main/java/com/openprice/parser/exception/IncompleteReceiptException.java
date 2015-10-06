package com.openprice.parser.exception;

@SuppressWarnings("serial")
public class IncompleteReceiptException extends Exception {
    public IncompleteReceiptException(String message){
        super("Incomplete Receipt Exception when parsing "+message);
    }
}

