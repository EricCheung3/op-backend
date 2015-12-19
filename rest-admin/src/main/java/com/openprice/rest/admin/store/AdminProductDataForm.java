package com.openprice.rest.admin.store;

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
public class AdminProductDataForm {

    private String name;

    private String number;

    private String price;

    private String naturalName;

    private String labelCodes;

    private String productCategory;
}
