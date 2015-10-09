package com.openprice.parser;


/**
 * similar to FieldName but with Address info detailed.
 */
public enum FieldName {
    Account,
    AddressLine1, AddressLine2, AddressCity, AddressProv, AddressPost,
    Approved, Author,
    Card, Cashier, Chain, ChainID,
    Date,
    GstAmount, GstNumber,
    Phone,
    Recycle, Ref,
    Slogan, StoreBranch, StoreID, SubTotal,
    Thankyou, Total, TotalSold ;

    //is an address field or not; the line is an address field if it is in the dictionary
    public boolean isAddressField(){
        return this.toString().startsWith("Address");
    }
}