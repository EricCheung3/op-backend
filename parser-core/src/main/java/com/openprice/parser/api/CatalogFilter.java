package com.openprice.parser.api;

public interface CatalogFilter {

    //this string matches a "blacklist"
    boolean matchesBlackList(String str);

}
