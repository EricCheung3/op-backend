package com.openprice.parser.data;

import lombok.Builder;
import lombok.Data;

/**
 * TODO add parser warning to database process log??
 * warning message and suggestion
 */
@Data
@Builder
public class Warning{
    final int line;//line number of the message
    final String message;
    final String suggestion;
}
