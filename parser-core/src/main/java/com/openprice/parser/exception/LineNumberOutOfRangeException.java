package com.openprice.parser.exception;

@SuppressWarnings("serial")
public class LineNumberOutOfRangeException extends Exception {
    public LineNumberOutOfRangeException(String msg){
        super(msg);
    }
}
