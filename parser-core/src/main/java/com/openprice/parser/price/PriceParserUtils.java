package com.openprice.parser.price;

import java.util.Comparator;
import java.util.List;

import com.openprice.parser.common.StringCommon;
import com.openprice.parser.data.NumberName;
import com.openprice.parser.data.Product;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PriceParserUtils {


    /*
     * split strings like
     * 05719777953 ROOSTER RICE
     * 057197 77953 ROOSTER RICE
     * into 05719777953 and ROOSTER RICE
     */
    public static NumberName numberNameSplitter(final String str){
        String[] words=str.trim().split("\\s+");
        int boundary=-1;
        for(int i=0;i<words.length;i++){
            String w= words[i];
            int[] count=StringCommon.countDigitAndChars(w);
            if((double)count[0]/w.length()<0.7){
                boundary=i;
                break;
            }
        }
        if(boundary<0){
            return NumberName.builder().number(StringCommon.EMPTY).name(str).build();
        }
        String num="";
        for(int i=0;i<boundary;i++){
            num += words[i];
        }

        String name="";
        for(int i=boundary;i<words.length;i++){
            name += " " + words[i];
        }
        return NumberName.builder().number(num.trim()).name(name.trim()).build();
    }

    /**
     * match a line to products from the catalog
     * @return
     */
    public static Product matchLineToCatalog(final String line, final List<Product> catalog){
        final Comparator<Product> comp=(p1, p2)->Double.compare(
                StringCommon.matchHeadScore(line.replaceAll("\\s+", ""),
                        p1.toStringNumberFirst().replaceAll("\\s+", "")),
                StringCommon.matchHeadScore(line.replaceAll("\\s+", ""),
                        p2.toStringNumberFirst().replaceAll("\\s+", ""))
                );
        final Product matched=catalog.stream().max(comp).get();
        //        log.debug("\nThe most promissing product is "+matched.toStringNumberFirst());
        final double score=StringCommon.matchHeadScore(
                line.replaceAll("\\s+", ""),
                matched.toStringNumberFirst().replace("\\s+", ""));
        //        log.debug("score="+score);
        if(score > 0.7)
            return matched;

        return Product.emptyProduct();
    }
}
