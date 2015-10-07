package com.openprice.parser.data;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import lombok.Data;


/**
 * things to skip
 */
@Data
public class Skip {

    //lines to skip before receipt finishes
    final List<String> skipBefore;

    //lines to skip with patterns of substrings
    final List<String> skipSubstring;

    //lines to skip after receipt finishes
    final List<String> skipAfter;

    public Skip(final List<String> skipBefore, final List<String> skipAfter){
        this.skipBefore = ImmutableList.copyOf(skipBefore);
        this.skipAfter = ImmutableList.copyOf(skipAfter);
        this.skipSubstring = new ArrayList<>(); // TODO what is the usage?
    }
}