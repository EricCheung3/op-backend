package com.openprice.parser.data;

import lombok.Builder;
import lombok.Data;

/*
 * write this class when thinking this
 * I want to use the builder object in this way.
 * FileNameIntPair.field(f).intV(int).build()
 */
@Data
@Builder
public class FileNameIntPair {

    private final FieldName field;
    private final Integer intV;
}
