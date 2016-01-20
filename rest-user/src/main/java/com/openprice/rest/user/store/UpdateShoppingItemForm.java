package com.openprice.rest.user.store;

import java.io.Serializable;

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
@SuppressWarnings("serial")
public class UpdateShoppingItemForm implements Serializable {

    private String name;

    private int number;

    private String categoryCode; //should be the code of ProductCategory
}
