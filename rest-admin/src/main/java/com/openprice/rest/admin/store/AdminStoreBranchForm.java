package com.openprice.rest.admin.store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.openprice.domain.common.Address;
import com.openprice.domain.store.StoreBranch;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown=true)
@ToString
public class AdminStoreBranchForm {
    @Getter @Setter
    private String name;

    @Getter @Setter
    private String gstNumber;

    @Getter @Setter
    private String slogan;

    @Getter @Setter
    private String phone;

    @Getter @Setter
    private String address1;

    @Getter @Setter
    private String address2;

    @Getter @Setter
    private String city;

    @Getter @Setter
    private String state;

    @Getter @Setter
    private String zip;

    @Getter @Setter
    private String country;

    public StoreBranch updateStoreBranch(final StoreBranch branch) {
        branch.setName(name);
        branch.setGstNumber(gstNumber);
        branch.setSlogan(slogan);
        branch.setPhone(phone);
        branch.setAddress(new Address(address1, address2, city, state, zip, country));
        return branch;
    }
}
