package com.openprice.parser.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileNameIntPair {

    private final FieldName field;
    private final Integer intV;
}
