package com.openprice.parser.common;

import java.util.List;

import com.openprice.parser.data.ValueLine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ListCommon{

    //merge to one string using the splitter
    public static String oneString(final List<String> list, final String splitter){
        String res="";
        if(list.size()==0) return res;
        for(int i=0;i<list.size()-1;i++){
            res+= list.get(i) + splitter;
        }
        res +=list.get(list.size()-1);
        return res;
    }

    public static void addToList(final List<String> list, final String s){
        if(s==null || s.isEmpty()) return;
        list.add(s);
    }

    public static boolean matchList(final List<String> skip,
            final String str, final double similarityScore) {
        for(int i=0;i<skip.size();i++){
            if(StringCommon.stringMatchesHead(str, skip.get(i),
                    similarityScore)) {
                //System.out.print("matching "+skip.get(i));
                return true;
            }
        }
        return false;
    }

    /*
          sum up values in List
     */
    public static double sumList(final List<ValueLine> rList){
        double total=0.0;
        for(int i=0; i<rList.size();i++){
            try{
                total+=Double.valueOf(StringCommon.formatPrice(rList.get(i).getValue()));
            }catch(Exception e){
                log.warn(rList.get(i).getLine()+ e.getMessage() + ". Make this value numerical.");
            }
        }
        return total;
    }


}