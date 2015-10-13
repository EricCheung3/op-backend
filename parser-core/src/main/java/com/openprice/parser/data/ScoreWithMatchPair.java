package com.openprice.parser.data;

import lombok.Value;

@Value
public class ScoreWithMatchPair<T> {
    double score;
    int  lineNumber;
    T match;
}
