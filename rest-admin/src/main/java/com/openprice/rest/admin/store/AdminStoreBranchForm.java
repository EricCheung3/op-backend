package com.openprice.rest.admin.store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.openprice.domain.common.Address;
import com.openprice.domain.store.StoreBranch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown=true)
@Builder
@NoArgsConstructor @AllArgsConstructor
@Data
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

    public AdminStoreBranchForm(final StoreBranch branch) {
        this.name = branch.getName();
        this.gstNumber = branch.getGstNumber();
        this.phone = branch.getPhone();
        this.address1 = branch.getAddress().getAddress1();
        this.address2 = branch.getAddress().getAddress2();
        this.city = branch.getAddress().getCity();
        this.state = branch.getAddress().getState();
        this.zip = branch.getAddress().getZip();
        this.country = branch.getAddress().getCountry();
    }
    public StoreBranch updateStoreBranch(final StoreBranch branch) {
        branch.setName(name);
        branch.setGstNumber(gstNumber);
        branch.setPhone(phone);
        branch.setAddress(new Address(address1, address2, city, state, zip, country));
        return branch;
    }
}
