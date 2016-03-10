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

    //chain code like "rcss" "safeway", underscore, lower-case letter and number only, no space, not starting with numbers
    private String code;

    private String name;

    private String identity;

    private String category;

    private String icon; // the icon name for the store. "generic" if designer hasn't put icon image.

    public static StoreChainData fromCodeOnly(final String code){
        return new StoreChainData(code, StringCommon.EMPTY, StringCommon.EMPTY, StringCommon.EMPTY, "generic"); // TODO put "generic" as constant
    }
}
