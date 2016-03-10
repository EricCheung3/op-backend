package com.openprice.parser.ml.api.predictor;

/**
 * predict the boundary of a line
 */
public interface BoundaryPredictor {

    //return an integer in 0 and an string str.length()-1
    int boundary1();

    //return an integer in 0 and an string str.length()-1;
    //boundary2 should be > boundary1
    int boundary2();

}
