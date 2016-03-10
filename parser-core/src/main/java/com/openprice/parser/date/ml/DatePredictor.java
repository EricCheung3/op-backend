package com.openprice.parser.date.ml;

import java.time.LocalDate;

public interface DatePredictor {

    LocalDate predict(final String dateString);
}
