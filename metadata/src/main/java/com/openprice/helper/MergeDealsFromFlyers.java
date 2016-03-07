package com.openprice.helper;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.openprice.common.StringCommon;
import com.openprice.common.TextResourceUtils;
import com.openprice.store.data.ProductData;

/**
 * merge deals from flyers
 */
public class MergeDealsFromFlyers {

    public static void main(String[] args) throws Exception{
        final String extractedLocalFile = "/Users/dongcui/Downloads/safewayflyers/simple_deals_extracted1.txt";
        final List<String> list = TextResourceUtils.loadStringArrayFromAbolutePath(Paths.get(extractedLocalFile));
        System.out.println("list.size="+list.size());
        final List<ProductData> cat = naturalNameAndPriceOnly(list);
        cat.forEach(p->System.out.println(p.getNaturalName()+","+p.getPrice()));
    }

    public static List<ProductData> naturalNameAndPriceOnly(final List<String> namePrices){
        return namePrices
        .stream()
        .map(line -> {
            String[] words2 = line.split("\\t");
            if(words2.length != 2){
                throw new RuntimeException("words2 = "+words2.length+" for line ="+line);
            }
            return naturalNameAndPrice(words2[1], words2[0]);
        })
        .collect(Collectors.toList());
    }

    public static ProductData naturalNameAndPrice(final String naturalName, final String price){
        return new ProductData(
                naturalName,
                StringCommon.EMPTY,
                price,
                naturalName,
                StringCommon.EMPTY,
                StringCommon.EMPTY);
    }

}
