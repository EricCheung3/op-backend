package com.openprice.store.data;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class StoreBranchData {

    private String name;

    private String phone;

    private String storeId; // identifier for store branch used by store chain

    private String gstNumber;

    private Address address;

    private String slogan;

    //private String address1;

    //private String address2;

    //private String city;

    //private String state;

    //private String postCode;

    //private String country;

    public static StoreBranchData fromNamePhoneStoreIdGstNumberAddressSlogan(
            final String name,
            final String phone,
            final String storeId,
            final String gstNumber,
            final Address address,
            final String slogan) {
        return new StoreBranchData(
                name,
                phone,
                storeId,
                gstNumber,
                address,
                slogan);
    }
}
