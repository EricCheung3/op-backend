package com.openprice.helper;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openprice.common.TextResourceUtils;

/** Converting not-catalog-item-name.txt to json
 *  Use this class because when editing not-catalog-item names,
 *  it is common to see characters that need to be escaped in json.
 * see https://github.com/opgt/op-backend/wiki/Meta-data
 */
public class ConvertTextToJson {

    public static void main(String[] args) throws Exception{
        final String chainCode = "sears";

        final List<String> orig=TextResourceUtils.loadStringArray("/"+chainCode+"/not-catalog-item-names.txt");
//        final List<String> orig=TextResourceUtils.loadStringArray("/shoppers/not-catalog-item-names.txt");
        final List<String> list=removeCommentEmptyLines(orig);
        toJsonForDataThatNeedEscape(list);
    }

    public static List<String> removeCommentEmptyLines(final List<String> orig){
        return orig
                .stream()
                .filter(str -> (!str.startsWith("#") && !str.trim().isEmpty()) )
                .collect(Collectors.toList());
    }

    public static void toJsonForDataThatNeedEscape(final List<String> list) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
        System.out.println(jsonInString);
    }

}
