package com.openprice.rest.admin.store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.openprice.domain.store.StoreChain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class AdminStoreChainForm {

    private String code;

    private String name;

    public StoreChain updateStore(final StoreChain store) {
        store.setCode(code);
        store.setName(name);
        return store;
    }
}
