package com.openprice.parser.store.rcss.rcss1;

import com.openprice.parser.FieldName;
import com.openprice.parser.LineFinder;
import com.openprice.parser.store.AbstractStoreParser;
import com.openprice.parser.store.StoreChain;
import com.openprice.parser.store.StoreConfig;

public class RCSS1 extends AbstractStoreParser {

    public RCSS1(final StoreConfig config,
                 final StoreChain chain) {
        super(config, chain);
    }




    @Override
    public String parseField(FieldName fieldName, LineFinder lineFinder, int line) {
        // TODO Auto-generated method stub
        return lineFinder.getLine(line);
    }

}
