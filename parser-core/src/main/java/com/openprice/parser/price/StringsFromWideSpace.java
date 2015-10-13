package com.openprice.parser.price;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringsFromWideSpace {

    public static TwoStrings twoStrings(String line){
        line=line.trim();
        String[] words=line.split("\\s{5,}");
        try{
            if(words.length==2){
                return TwoStrings.fromArray(words);
            }

            words=line.split("\\s{4,}");
            if(words.length==2){
                return TwoStrings.fromArray(words);
            }

            words=line.split("\\s{3,}");
            if(words.length==2){
                return TwoStrings.fromArray(words);
            }

            words=line.split("\\s{2,}");
            if(words.length==2){
                return TwoStrings.fromArray(words);
            }

        }catch(Exception e){
            return TwoStrings.emptyValue();
        }

        return TwoStrings.emptyValue();
    }

    public static ThreeStrings trippleStrings(String line){
        line=line.trim();
        String[] words=line.split("\\s{5,}");
        try{
            if(words.length==3){
                return ThreeStrings.fromArray(words);
            }
            words=line.split("\\s{4,}");
            if(words.length==3){
                return ThreeStrings.fromArray(words);
            }
            words=line.split("\\s{3,}");
            if(words.length==3){
                return ThreeStrings.fromArray(words);
            }
            words=line.split("\\s{2,}");
            if(words.length==3){
                return ThreeStrings.fromArray(words);
            }
        }catch(Exception e){
            log.debug("e.message"+e.getMessage());
            return ThreeStrings.emptyValue();
        }
        return ThreeStrings.emptyValue();
    }

    public static FourStrings fourStrings(String line){
        line=line.trim();
        String[] words=line.split("\\s{5,}");
        try{
            if(words.length==4){
                return FourStrings.fromArray(words);
            }
            words=line.split("\\s{4,}");
            if(words.length==4){
                return FourStrings.fromArray(words);
            }
            words=line.split("\\s{3,}");
            if(words.length==4){
                return FourStrings.fromArray(words);
            }
            words=line.split("\\s{2,}");
            if(words.length==4){
                return FourStrings.fromArray(words);
            }
        }catch(Exception e){
            log.debug("e.message"+e.getMessage());
            return FourStrings.emptyValue();
        }
        return FourStrings.emptyValue();
    }
}
