package com.openprice.rest.user.store;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@JsonIgnoreProperties(ignoreUnknown=true)
@Builder
@NoArgsConstructor @AllArgsConstructor
@Data
public class ShoppingListForm {
    private String storeId;

    @Singular private List<Item> items = new ArrayList<>();

    @JsonIgnoreProperties(ignoreUnknown=true)
    @Builder
    @NoArgsConstructor  @AllArgsConstructor
    @Data
    public static class Item {
        private String name;

        private String price;
    }
}
