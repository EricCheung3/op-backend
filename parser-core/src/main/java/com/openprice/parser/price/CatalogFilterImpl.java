package com.openprice.parser.price;

import java.util.ArrayList;
import java.util.List;

import com.openprice.common.Levenshtein;
import com.openprice.common.StringCommon;
import com.openprice.parser.api.CatalogFilter;

import lombok.Value;

@Value
public class CatalogFilterImpl implements CatalogFilter{

    List<String> blackList;
    static final double BLACKLIST_THRESHOLD=0.7;

    private CatalogFilterImpl(final List<String> blackList){
        this.blackList=blackList;
    }

    public static CatalogFilter fromList(final List<String> bk){
        return new CatalogFilterImpl(StringCommon.sortByStringLength(bk));
    }

    public static CatalogFilter emptyFilter(){
        return fromList(new ArrayList<String>());
    }

    //TODO have a threshold parameter?
    @Override
    public boolean matchesBlackList(final String str){
        if( !PriceParserFromStringTuple.isItemName(str))
            return true;
        if(StringCommon.removeAllSpaces(str).length()<=5)
            return lowercaseNoSpaceMatchList(str, 0.95);
        try{
            return lowercaseNoSpaceMatchList(str, BLACKLIST_THRESHOLD);
        }catch(Exception e){
            return blackList.contains(str);
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
