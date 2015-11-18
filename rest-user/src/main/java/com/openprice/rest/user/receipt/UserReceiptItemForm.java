package com.openprice.rest.user.receipt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.openprice.domain.receipt.ReceiptItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserReceiptItemForm {

    private String name;

    private String price;

    public ReceiptItem updateReceiptItem(ReceiptItem item) {
        item.setDisplayName(name);
        item.setDisplayPrice(price);
        return item;
    }
}
