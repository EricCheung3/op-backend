package com.openprice.store;

import org.springframework.util.StringUtils;

public class ProductUtils {

    public static final String SEPARATOR = "_";

    //TODO what if name contains the SEPARATOR?
    public static String generateCatalogCode(final String name, final String number) {
        assert name != null;
        return name.replace(SEPARATOR, " ") + (StringUtils.isEmpty(number)? "" : SEPARATOR + number);
    }

}

