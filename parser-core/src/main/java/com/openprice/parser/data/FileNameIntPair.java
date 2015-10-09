package com.openprice.parser.data;

import com.openprice.parser.FieldName;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileNameIntPair {

    private final FieldName field;
    private final Integer intV;
}
