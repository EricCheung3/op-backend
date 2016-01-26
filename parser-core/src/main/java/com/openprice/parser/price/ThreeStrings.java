package com.openprice.parser.price;

import com.openprice.parser.common.StringCommon;

import lombok.Value;

@Value
public class ThreeStrings {
    String first;
    String second;
    String third;

    public ThreeStrings(final String first, final String second, final String third){
        this.first=first;
        this.second=second;
        this.third=third;
    }

    public static ThreeStrings fromArray(String[] words) throws Exception{
        if(words.length!=3){
            throw new Exception("words length should be 3, but it's "+words.length);
        }
        return new ThreeStrings(words[0], words[1], words[2]);
    }

    public static ThreeStrings emptyValue(){
        return new ThreeStrings(StringCommon.EMPTY,StringCommon.EMPTY, StringCommon.EMPTY);
    }

    public boolean isEmpty(){
        return first.isEmpty() && second.isEmpty() && third.isEmpty();
    }
}
