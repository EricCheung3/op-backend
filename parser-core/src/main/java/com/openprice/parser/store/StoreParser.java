package com.openprice.parser.store;

import com.openprice.parser.FieldName;
import com.openprice.parser.LineFinder;

public interface StoreParser {

    /**
     * Gets config properties for the store.
     * @return
     */
    StoreConfig getStoreConfig();

    String parseField(FieldName fieldName, LineFinder lineFinder, int line);
}
