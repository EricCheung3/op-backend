package com.openprice.helper;

import com.openprice.store.ChainConfigFiles;
import com.openprice.store.MetadataLoader;
import com.openprice.store.data.ProductData;

/**
 * prune for duplicates in a store's catalog
 */

public class CatalogPrune {

    public static void main(String[] args) throws Exception{
        final String chainCode = "safeway";
        print(chainCode);
    }

    public static void print(final String chainCode){
        final ProductData[] data = MetadataLoader.loadArrayFromJsonResource(ChainConfigFiles.getCatalog(chainCode), ProductData[].class);
        System.out.println("data.length="+data.length);
        for(int i=0; i< data.length; i++){
            System.out.println(data[i].toString());
        }
    }
}
