package com.openprice.rest.admin.receipt;

import org.springframework.hateoas.Resource;

import com.openprice.domain.receipt.ReceiptImage;

public class AdminReceiptImageResource extends Resource<ReceiptImage> {

    public AdminReceiptImageResource(final ReceiptImage resource) {
        super(resource);
    }
}
