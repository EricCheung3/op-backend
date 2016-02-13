package com.openprice.store;

public class FieldUtils {

    public static String cleanField(String str) {
        if (str == null) {
            return null;
        }
        str = str.trim();
        return str.replaceAll(MetadataConstants.PRODUCT_LINE_SPLITTER, "");
    }
}
