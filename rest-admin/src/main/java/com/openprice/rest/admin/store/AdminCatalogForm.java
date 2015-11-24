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
public class AdminCatalogForm {

    private String code;

    private String name;

    private String number;

    private String category;

    private String price;

    private String naturalName;

    private String labelCodes;
}
