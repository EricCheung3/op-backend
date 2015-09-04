package com.openprice.rest.user.store;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ShoppingListForm {
    @Getter @Setter
    private String storeId;
    
    @Getter @Setter
    private List<Item> items = new ArrayList<>();
    
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {
        @Getter @Setter
        private String name;
        
        @Getter @Setter
        private String price;
    }
}
