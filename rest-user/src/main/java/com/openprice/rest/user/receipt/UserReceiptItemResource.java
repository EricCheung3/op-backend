package com.openprice.rest.user.receipt;

import org.springframework.hateoas.Resource;

import com.openprice.domain.receipt.ReceiptItem;
import com.openprice.domain.store.Catalog;

import lombok.Getter;
import lombok.Setter;

public class UserReceiptItemResource extends Resource<ReceiptItem> {

    @Getter @Setter
    private Catalog catalog;

    public UserReceiptItemResource(ReceiptItem resource) {
        super(resource);
    }

}
