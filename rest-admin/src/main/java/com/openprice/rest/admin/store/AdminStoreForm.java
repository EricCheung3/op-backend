package com.openprice.rest.admin.store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.openprice.domain.store.Store;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown=true)
@ToString
public class AdminStoreForm {
    @Getter @Setter
    private String code;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String categories;

    @Getter @Setter
    private String identifyField;

    public Store updateStore(Store store) {
        store.setCode(code);
        store.setName(name);
        store.setCategories(categories);
        store.setIdentifyField(identifyField);
        return store;
    }
}
