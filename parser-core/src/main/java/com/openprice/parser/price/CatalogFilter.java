package com.openprice.parser.price;

import java.util.List;

import com.openprice.parser.common.Levenshtein;
import com.openprice.parser.common.StringCommon;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CatalogFilter {

    List<String> blackList;
    static final double BLACKLIST_THRESHOLD=0.7;

    //not an item name
    public boolean matchesBlackList(final String name){
        if( !PriceParserFromStringTuple.isItemName(name))
            return true;
        if(StringCommon.removeAllSpaces(name).length()<=5)
            return lowercaseNoSpaceMatchList(name, 0.95);
        try{
            return lowercaseNoSpaceMatchList(name, BLACKLIST_THRESHOLD);
        }catch(Exception e){
            return blackList.contains(name);
        }
    }

    /**
     * match the blacklist in the comparison mode of "lowercased" and "no spaces"
     * @param skip
     * @param str
     * @param threshold
     * @return
     */
    public boolean lowercaseNoSpaceMatchList(final String str, final double threshold){
        final String strLowNoSpace=StringCommon.lowerCaseNoSpaces(str);
        for(int i=0;i<blackList.size();i++){
            final String skipStr=StringCommon.lowerCaseNoSpaces(blackList.get(i));
            final double score=Levenshtein.compare(strLowNoSpace,skipStr);
            if(score > threshold)
                return true;
        }
        return false;
    }
}
