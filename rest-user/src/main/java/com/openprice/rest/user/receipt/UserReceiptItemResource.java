package com.openprice.rest.user.receipt;

import org.springframework.hateoas.Resource;

import com.openprice.domain.receipt.ReceiptItem;

public class UserReceiptItemResource extends Resource<ReceiptItem> {

    public UserReceiptItemResource(ReceiptItem resource) {
        super(resource);
    }

}
