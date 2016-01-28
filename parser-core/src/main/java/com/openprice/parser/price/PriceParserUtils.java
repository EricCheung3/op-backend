package com.openprice.parser.price;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.openprice.parser.common.StringCommon;
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
    //TODO: this is not used yet.
    public static TwoStrings numberNameSplitter(final String str){
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
            return new TwoStrings(StringCommon.EMPTY, str);
        }
        String num="";
        for(int i=0;i<boundary;i++){
            num += words[i];
        }

        String name="";
        for(int i=boundary;i<words.length;i++){
            name += " " + words[i];
        }
        return new TwoStrings(num.trim(), name.trim());
    }

    /**
     * match a line to products from the catalog
     * @return
     */
    public static Product matchLineToCatalog(final String line, final List<Product> catalog){
        final Comparator<Product> comp=(p1, p2)->Double.compare(
                StringCommon.matchStringToHeader(line.replaceAll("\\s+", ""),
                        p1.toStringNumberFirst().replaceAll("\\s+", "")),
                StringCommon.matchStringToHeader(line.replaceAll("\\s+", ""),
                        p2.toStringNumberFirst().replaceAll("\\s+", ""))
                );
        final Product matched=catalog.stream().max(comp).get();
        //        log.debug("\nThe most promissing product is "+matched.toStringNumberFirst());
        final double score=StringCommon.matchStringToHeader(
                line.replaceAll("\\s+", ""),
                matched.toStringNumberFirst().replace("\\s+", ""));
        //        log.debug("score="+score);
        if(score > 0.7)
            return matched;

        return Product.emptyProduct();
    }

    /**
     * match a line to products from the catalog. it will automatically decides to use name or number first format
     * it is important to put lineNoSpacesLower in the second parameter of matchHeadScore because we don't want
     * a line "pepsi 591ml ..." to match a product "pepsi" (without 519ml)
     * we want to know the edit distance to change the line to the product
     * @return
     */
    //TODO count the number of times name is first so that you can decide spam or not
    public static Product matchLineToCatalog(final String originalLine, final Set<Product> catalog){
        if(catalog.isEmpty()) return Product.emptyProduct();
        final String lineNoSpacesLower = StringCommon.removeAllSpaces(originalLine.toLowerCase());

        final Comparator<Product> compNumberFirst = (p1, p2)->
        {
            return Double.compare(
                    StringCommon.matchStringToHeader(lineNoSpacesLower, StringCommon.lowerCaseNoSpaces(p1.toStringNumberFirst())),
                    StringCommon.matchStringToHeader(lineNoSpacesLower, StringCommon.lowerCaseNoSpaces(p2.toStringNumberFirst()))
                    );
        };

        final Product matchedNumberFirst=catalog.stream().max(compNumberFirst).get();
        final double scoreNumberFirst=StringCommon.matchStringToHeader(lineNoSpacesLower,
                StringCommon.lowerCaseNoSpaces(matchedNumberFirst.toStringNumberFirst()));

        final Comparator<Product> compNameFirst = (p1, p2)->
        {
            return Double.compare(
                    StringCommon.matchStringToHeader(lineNoSpacesLower, StringCommon.lowerCaseNoSpaces(p1.toStringNameFirst())),
                    StringCommon.matchStringToHeader(lineNoSpacesLower, StringCommon.lowerCaseNoSpaces(p2.toStringNameFirst()))
                    );
        };
        final Product matchedNameFirst=catalog.stream().max(compNameFirst).get();
        final double scoreNameFirst=StringCommon.matchStringToHeader(lineNoSpacesLower, StringCommon.lowerCaseNoSpaces(matchedNameFirst.toStringNameFirst()));

        double scoreMax=scoreNumberFirst;
        Product matched=matchedNumberFirst;
        if(scoreNameFirst>scoreMax){
            scoreMax=scoreNameFirst;
            matched=matchedNameFirst;
        }
        log.debug("scoreMax="+scoreMax+"\n");
        if(scoreMax > 0.7)
            return matched;
        return Product.emptyProduct();
    }

}
