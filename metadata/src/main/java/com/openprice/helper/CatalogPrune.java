package com.openprice.helper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.openprice.common.Levenshtein;
import com.openprice.common.StringCommon;
import com.openprice.store.ChainConfigFiles;
import com.openprice.store.MetadataLoader;
import com.openprice.store.data.ProductData;

/**
 * prune for duplicates in a store's catalog
 */

public class CatalogPrune {

    private static final double THRESHOLD = 0.75;

    public static void main(String[] args) throws Exception{
        final String chainCode = "rcss";// "safeway";
        printSimilarNaturalName(chainCode);
    }

    //print products that have similar natural names
    public static void printSimilarNaturalName(final String chainCode){
        final ProductData[] data = MetadataLoader.loadArrayFromJsonResource(ChainConfigFiles.getCatalog(chainCode), ProductData[].class);
        final List<ProductData> all = Arrays.asList(data);

        final Set<ProductData> alreadyPrinted = new HashSet<>();
        all
        .stream()
        .filter(p -> !alreadyPrinted.contains(p))
        .forEach(p->{
            all
            .stream()
            .filter(q -> !q.equals(p))
            .forEach( q->{
                double score = Levenshtein.compare(StringCommon.removeAllSpaces(p.getNaturalName().toLowerCase()),
                        StringCommon.removeAllSpaces(q.getNaturalName().toLowerCase()));
                if(score > THRESHOLD){
                    System.out.println(p.getNaturalName()+", "+q.getNaturalName() +": score="+score);
                    alreadyPrinted.add(p);
                    alreadyPrinted.add(q);
                }
            });
        });
    }
}
