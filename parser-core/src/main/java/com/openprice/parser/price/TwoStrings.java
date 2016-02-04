package com.openprice.parser.price;

import com.openprice.common.StringCommon;

import lombok.Value;

@Value
public class TwoStrings {
    String first;
    String second;

    public TwoStrings(final String first, final String second){
        this.first=first;
        this.second=second;
    }

    public static TwoStrings fromArray(String[] words) throws Exception{
        if(words.length!=2){
            throw new Exception("words length should be 2, but it's "+words.length);
        }
        return new TwoStrings(words[0], words[1]);
    }

    public static TwoStrings emptyValue(){
        return new TwoStrings(StringCommon.EMPTY, StringCommon.EMPTY);
    }

    public boolean isEmpty(){
        return first.isEmpty() && second.isEmpty();
    }
}
