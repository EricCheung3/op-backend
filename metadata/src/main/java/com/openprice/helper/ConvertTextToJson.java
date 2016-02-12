package com.openprice.helper;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openprice.common.TextResourceUtils;

public class ConvertTextToJson {

    public static void main(String[] args) throws Exception{
        final List<String> orig=TextResourceUtils.loadStringArray("/rcss/not-catalog-item-names.txt");
        final List<String> list=removeCommentEmptyLines(orig);
//        toJsonForDataDoNotNeedEscape(list);
        toJsonForDataThatNeedEscape(list);
    }

    public static List<String> removeCommentEmptyLines(final List<String> orig){
        return orig
                .stream()
                .filter(str -> (!str.startsWith("#") && !str.trim().isEmpty()) )
                .collect(Collectors.toList());
    }

    //The goodness of this one is that it breaks line at comma
    public static void toJsonForDataDoNotNeedEscape(final List<String> list) throws Exception{
        System.out.println("[");
        for(int i=0; i<list.size(); i++){
            String strNoDoubleQuote=list.get(i).replace("\"", "\"");
            if(i == list.size()-1)
                System.out.println("\""+strNoDoubleQuote+"\"");
            else
                System.out.println("\""+strNoDoubleQuote+"\",");
        }
        System.out.println("]");
    }

    //this one doesn't break line at comma. Improve?
    public static void toJsonForDataThatNeedEscape(final List<String> list) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(list);
        System.out.println(jsonInString);
    }

}
