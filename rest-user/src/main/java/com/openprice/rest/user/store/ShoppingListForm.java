package com.openprice.rest.user.store;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.openprice.domain.shopping.ShoppingItem;
import com.openprice.domain.shopping.ShoppingStore;

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
    private String chainCode;

    @Singular private List<Item> items = new ArrayList<>();

    public ShoppingStore addShoppingItems(final ShoppingStore store) {
        for (final ShoppingListForm.Item item : items) {
            final ShoppingItem shoppingItem = new ShoppingItem();
            shoppingItem.setStore(store);
            shoppingItem.setCatalogCode(item.getCatalogCode());
            shoppingItem.setName(item.getName());
            store.getItems().add(shoppingItem);
        }
        return store;
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    @Builder
    @NoArgsConstructor  @AllArgsConstructor
    @Data
    public static class Item {
        private String name;
        private String catalogCode;
    }
}
