package com.openprice.parser.price;

import com.openprice.parser.common.StringCommon;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TwoStrings {
    String first;
    String second;

    public static TwoStrings fromArray(String[] words) throws Exception{
        if(words.length!=2){
            throw new Exception("words length should be 2, but it's "+words.length);
        }
        return TwoStrings.builder()
                .first(words[0])
                .second(words[1])
                .build();
    }

    public static TwoStrings emptyValue(){
        return TwoStrings.builder()
                .first(StringCommon.EMPTY)
                .second(StringCommon.EMPTY)
                .build();
    }

    public boolean isEmpty(){
        return first.isEmpty() && second.isEmpty();
    }
}
