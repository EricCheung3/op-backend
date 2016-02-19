package com.openprice.parser.api;

import com.openprice.store.StoreBranch;

/**
 * operations of matching fields, including matching to branch and matching to headers
 */
public interface MatchFields {

    //match to a branch and record matched information
    void matchToBranch(MatchedRecord record, ReceiptData receipt, StoreBranch storeBranch);

    //match to headers and record matched information
    void matchToHeaders(MatchedRecord record, ReceiptData receipt, StoreParser parser);
}
