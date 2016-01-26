package com.openprice.parser.price;

import com.openprice.parser.common.StringCommon;

import lombok.Value;

@Value
public class FourStrings {
    String first;
    String second;
    String third;
    String fourth;

    public FourStrings(final String first, final String second,
            final String third, final String fourth){
        this.first=first;
        this.second=second;
        this.third=third;
        this.fourth=fourth;
    }

    public static FourStrings fromArray(String[] words) throws Exception{
        if(words.length!=4){
            throw new Exception("words length should be 4, but it's "+words.length);
        }
        return new FourStrings(words[0], words[1], words[2], words[3]);
    }

    public static FourStrings emptyValue(){
        return new FourStrings(StringCommon.EMPTY,StringCommon.EMPTY,
                StringCommon.EMPTY, StringCommon.EMPTY);
    }

    public boolean isEmpty(){
        return first.isEmpty() && second.isEmpty()
                && third.isEmpty() && fourth.isEmpty();
    }
}
