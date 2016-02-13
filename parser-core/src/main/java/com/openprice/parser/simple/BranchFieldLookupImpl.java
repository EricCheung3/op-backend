package com.openprice.parser.simple;

import java.util.HashMap;
import java.util.Map;

import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.api.BranchFieldLookup;
import com.openprice.store.StoreBranch;

import lombok.Getter;


/**
 * lookup for the field name of branch info given a field name
 */
public class BranchFieldLookupImpl implements BranchFieldLookup{

    @Getter
    private final Map<ReceiptFieldType, String> fieldToValue = new HashMap<ReceiptFieldType, String>();

    @Override
    public String valueOf(final ReceiptFieldType field){
        return fieldToValue.get(field);
    }

    /*
     * Private constructor, so can only get StoreBranch from builder or static builder method.
     */
    public BranchFieldLookupImpl(final StoreBranch branch) {
        // save store branch ground truth data into a map
        if (branch != null) {
            addGroundTruthValue(ReceiptFieldType.AddressLine1, branch.getAddress1());
            addGroundTruthValue(ReceiptFieldType.AddressLine2, branch.getAddress2());
            addGroundTruthValue(ReceiptFieldType.AddressCity, branch.getCity());
            addGroundTruthValue(ReceiptFieldType.AddressState, branch.getState());
            addGroundTruthValue(ReceiptFieldType.AddressCountry, branch.getCountry());
            addGroundTruthValue(ReceiptFieldType.AddressPost, branch.getPostCode());
        }
        addGroundTruthValue(ReceiptFieldType.StoreID, branch.getStoreId());
        addGroundTruthValue(ReceiptFieldType.GstNumber, branch.getGstNumber());
        addGroundTruthValue(ReceiptFieldType.Phone, branch.getPhone());
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
