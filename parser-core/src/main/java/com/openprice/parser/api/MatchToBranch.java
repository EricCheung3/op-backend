package com.openprice.parser.api;

import com.openprice.store.StoreBranch;

public interface MatchToBranch {

    //matching score of a branch and a receipt
    public double matchScore(final StoreBranch branch, final ReceiptData receipt);

}
