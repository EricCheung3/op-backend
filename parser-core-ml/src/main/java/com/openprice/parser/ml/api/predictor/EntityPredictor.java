package com.openprice.parser.ml.api.predictor;

import com.openprice.parser.ml.data.ItemEntity;

public interface EntityPredictor {

    //predict the ItemEntity of the string
    ItemEntity predict(final String str);

}
