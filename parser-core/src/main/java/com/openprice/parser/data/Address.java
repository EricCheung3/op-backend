package com.openprice.parser.data;

import com.openprice.parser.ParserUtils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    private final String line1;//address line1
    private final String line2;//address line2
    private final String city;
    private final String prov;
    private final String country;
    private final String post;//post code

    public Address(final String line1, final String line2, final String city, final String prov, final String country, final String post) {
        this.line1 = ParserUtils.cleanField(line1);
        this.line2 = ParserUtils.cleanField(line2);
        this.city = ParserUtils.cleanField(city);
        this.prov = ParserUtils.cleanField(prov);
        this.country = ParserUtils.cleanField(country);
        this.post = ParserUtils.cleanField(post);
    }

    public static Address defaultAddress() {
        return new Address("", "", "", "", "", "");
    }
}
