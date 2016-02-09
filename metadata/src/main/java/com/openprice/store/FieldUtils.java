package com.openprice.store;

public class FieldUtils {

    public static final String SPLITTER = ",";

    public static String cleanField(String str) {
        if (str == null) {
            return null;
        }
        str = str.trim();
        return str.replaceAll(SPLITTER, "");
    }
}
