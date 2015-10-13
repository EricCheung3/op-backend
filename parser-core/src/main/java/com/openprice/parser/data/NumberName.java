package com.openprice.parser.data;

import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class NumberName {
    String number;
    String name;
}
