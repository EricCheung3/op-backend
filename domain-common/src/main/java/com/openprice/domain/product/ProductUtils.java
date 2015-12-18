package com.openprice.domain.product;

import org.springframework.util.StringUtils;

public class ProductUtils {

    public static final String SEPARATOR = "_";

    public static String generateCatalogCode(final String name, final String number) {
        assert name != null;
        return name.replace(SEPARATOR, " ") + (StringUtils.isEmpty(number)? "" : SEPARATOR + number);
    }

}
