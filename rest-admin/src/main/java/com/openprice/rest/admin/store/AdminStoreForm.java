package com.openprice.rest.admin.store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.openprice.domain.store.Store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown=true)
@Builder
@NoArgsConstructor @AllArgsConstructor
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

    public AdminStoreForm(final Store store) {
        this.code = store.getCode();
        this.name = store.getName();
        this.categories = store.getCategories();
        this.identifyField = store.getIdentifyField();
    }

    public Store updateStore(final Store store) {
        store.setCode(code);
        store.setName(name);
        store.setCategories(categories);
        store.setIdentifyField(identifyField);
        return store;
    }
}
