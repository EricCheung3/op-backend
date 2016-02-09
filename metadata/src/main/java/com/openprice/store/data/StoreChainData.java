package com.openprice.store.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.openprice.common.StringCommon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class StoreChainData {

    private String code;

    private String name;

    private String identity;

    private String category;

    public static StoreChainData fromCodeOnly(final String code){
        return new StoreChainData(code, StringCommon.EMPTY, StringCommon.EMPTY, StringCommon.EMPTY);
    }
}
