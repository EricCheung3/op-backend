package com.openprice.parser.price;

import com.openprice.parser.common.StringCommon;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ThreeStrings {
    String first;
    String second;
    String third;

    public static ThreeStrings fromArray(String[] words) throws Exception{
        if(words.length!=3){
            throw new Exception("words length should be 3, but it's "+words.length);
        }
        return ThreeStrings.builder()
                .first(words[0])
                .second(words[1])
                .third(words[2]).build();
    }

    public static ThreeStrings emptyValue(){
        return ThreeStrings.builder()
                .first(StringCommon.EMPTY)
                .second(StringCommon.EMPTY)
                .third(StringCommon.EMPTY)
                .build();
    }

    public boolean isEmpty(){
        return first.isEmpty() && second.isEmpty() && third.isEmpty();
    }
}
