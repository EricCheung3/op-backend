package com.openprice.store.data;

import com.openprice.common.StringCommon;
import com.openprice.store.FieldUtils;

import lombok.Value;

@Value
public class Address {
    String address1;
    String address2;
    String city;
    String state;
    String country;
    String postCode;

    public Address(
            final String address1,
            final String address2,
            final String city,
            final String state,
            final String country,
            final String postCode) {
        this.address1 = FieldUtils.cleanField(address1);
        this.address2 = FieldUtils.cleanField(address2);
        this.city = FieldUtils.cleanField(city);
        this.state = FieldUtils.cleanField(state);
        this.country = FieldUtils.cleanField(country);
        this.postCode = FieldUtils.cleanField(postCode);
    }

    public static Address emptyAddress() {
        return new Address(
                StringCommon.EMPTY,
                StringCommon.EMPTY,
                StringCommon.EMPTY,
                StringCommon.EMPTY,
                StringCommon.EMPTY,
                StringCommon.EMPTY);
    }
}
