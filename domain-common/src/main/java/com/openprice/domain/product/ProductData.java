package com.openprice.domain.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class ProductData {
    private String name;
    private String number;
    private String price;
    private String naturalName;
    private String labelCodes;
    private String productCategory;

    public String getCatalogCode() {
        return ProductUtils.generateCatalogCode(name, number);
    }
}
