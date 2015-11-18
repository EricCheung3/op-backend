package com.openprice.rest.admin.store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.openprice.domain.common.Address;
import com.openprice.domain.store.StoreBranch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class AdminStoreBranchForm {

    private String name;

    private String gstNumber;

    private String phone;

    private String address1;

    private String address2;

    private String city;

    private String state;

    private String zip;

    private String country;

    public StoreBranch updateStoreBranch(final StoreBranch branch) {
        branch.setName(name);
        branch.setGstNumber(gstNumber);
        branch.setPhone(phone);
        branch.setAddress(new Address(address1, address2, city, state, zip, country));
        return branch;
    }
}
