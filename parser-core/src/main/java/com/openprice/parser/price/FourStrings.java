package com.openprice.parser.price;

import com.openprice.parser.common.StringCommon;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FourStrings {
    String first;
    String second;
    String third;
    String fourth;

    public static FourStrings fromArray(String[] words) throws Exception{
        if(words.length!=4){
            throw new Exception("words length should be 4, but it's "+words.length);
        }
        return FourStrings.builder()
                          .first(words[0])
                          .second(words[1])
                          .third(words[2])
                          .fourth(words[3]).build();
    }

    public static FourStrings emptyValue(){
        return FourStrings.builder()
                          .first(StringCommon.EMPTY)
                          .second(StringCommon.EMPTY)
                          .third(StringCommon.EMPTY)
                          .fourth(StringCommon.EMPTY)
                          .build();
    }

    public boolean isEmpty(){
        return first.isEmpty() && second.isEmpty()
                && third.isEmpty() && fourth.isEmpty();
    }
}
