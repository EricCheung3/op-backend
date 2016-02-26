package com.openprice.parser;

/**
 * All fields in receipts we can recognize by string matching.
 *
 */
public enum ReceiptFieldType {
    Account,
    AddressLine1, AddressLine2, AddressCity, AddressState, AddressCountry, AddressPost,
    Approved, Author,
    Card, Cashier, Chain, ChainID,
    Date,Time,
    GstAmount, GstNumber,
    Phone,
    Deposit, Recycle, Ref,
    Saving,
    Slogan, StoreBranch, StoreID, SubTotal,
    Thankyou, Total, TotalSold,
    UnitPrice;

    //is an address field or not; the line is an address field if it is in the dictionary
    public boolean isAddressField(){
        return this.toString().startsWith("Address");
    }
}