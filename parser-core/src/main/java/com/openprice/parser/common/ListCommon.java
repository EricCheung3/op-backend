package com.openprice.parser.common;

import java.util.List;




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
            final String str, final double similarityScore) throws Exception{
        for(int i=0;i<skip.size();i++){
            if(StringCommon.stringMatchesHead(str, skip.get(i),
                    similarityScore)) {
                //System.out.print("matching "+skip.get(i));
                return true;
            }
        }
        return false;
    }


}