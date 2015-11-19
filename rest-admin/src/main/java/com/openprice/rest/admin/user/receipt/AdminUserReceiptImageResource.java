package com.openprice.rest.admin.user.receipt;

import org.springframework.hateoas.Resource;

import com.openprice.domain.receipt.ReceiptImage;

public class AdminUserReceiptImageResource extends Resource<ReceiptImage> {

    public AdminUserReceiptImageResource(final ReceiptImage resource) {
        super(resource);
    }
}
