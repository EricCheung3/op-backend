package com.openprice.rest.user.receipt;

import org.springframework.hateoas.Resource;

import com.openprice.domain.receipt.ReceiptImage;

public class UserReceiptImageResource extends Resource<ReceiptImage> {

    public UserReceiptImageResource(final ReceiptImage resource) {
        super(resource);
    }

}
