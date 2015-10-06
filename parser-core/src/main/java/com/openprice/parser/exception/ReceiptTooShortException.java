package com.openprice.parser.exception;

@SuppressWarnings("serial")
public class ReceiptTooShortException extends Exception {
    public   ReceiptTooShortException(String msg){
        super(msg);
    }
}
