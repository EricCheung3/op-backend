package com.openprice.rest.admin.store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.openprice.domain.store.StoreChain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown=true)
@Builder
@NoArgsConstructor @AllArgsConstructor
@Data
public class AdminStoreChainForm {
    private String code;

    private String name;

    private String categories;

    private String identifyFields;

    public AdminStoreChainForm(final StoreChain store) {
        this.code = store.getCode();
        this.name = store.getName();
        this.categories = store.getCategories();
        this.identifyFields = store.getIdentifyFields();
    }

    public StoreChain updateStore(final StoreChain store) {
        store.setCode(code);
        store.setName(name);
        store.setCategories(categories);
        store.setIdentifyFields(identifyFields);
        return store;
    }
}
