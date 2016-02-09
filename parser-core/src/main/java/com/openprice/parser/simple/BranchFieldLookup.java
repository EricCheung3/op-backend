package com.openprice.parser.simple;

import java.util.HashMap;
import java.util.Map;

import com.openprice.parser.ReceiptFieldType;
import com.openprice.store.StoreBranch;

import lombok.Getter;


/**
 * lookup for the field name of branch info given a field name
 */
public class BranchFieldLookup {

    @Getter
    private final Map<ReceiptFieldType, String> fieldToValue = new HashMap<ReceiptFieldType, String>();

    public String valueOf(final ReceiptFieldType field){
        return fieldToValue.get(field);
    }

    /*
     * Private constructor, so can only get StoreBranch from builder or static builder method.
     */
    public BranchFieldLookup(final StoreBranch branch) {
        // save store branch ground truth data into a map
        if (branch.getAddress() != null) {
            addGroundTruthValue(ReceiptFieldType.AddressLine1, branch.getAddress().getAddress1());
            addGroundTruthValue(ReceiptFieldType.AddressLine2, branch.getAddress().getAddress2());
            addGroundTruthValue(ReceiptFieldType.AddressCity, branch.getAddress().getCity());
            //addGroundTruthValue(ReceiptField.AddressProv, address.getProv()); // province code is too short, may cause parsing error
            addGroundTruthValue(ReceiptFieldType.AddressPost, branch.getAddress().getPostCode());
        }
        addGroundTruthValue(ReceiptFieldType.StoreID, branch.getStoreId());
        addGroundTruthValue(ReceiptFieldType.GstNumber, branch.getGstNumber());
        addGroundTruthValue(ReceiptFieldType.Phone, branch.getPhone());
        addGroundTruthValue(ReceiptFieldType.Slogan, branch.getSlogan());
    }

    private void addGroundTruthValue(final ReceiptFieldType fieldName, final String value) {
        if (value != null) {
            final String cleanedValue = value.trim();
            if (cleanedValue.length() > 2) {
                fieldToValue.put(fieldName, value.toLowerCase());
            }
        }
    }
}
