package com.openprice.parser.ml.api.predictor;

import com.openprice.parser.ml.data.ItemEntity;

public interface EntityPredictor {

    //predict the ItemEntity of the token: name, number, or price
    ItemEntity classify(final String token);

}
