package com.openprice.rest.admin.receipt;

import org.springframework.hateoas.Resource;

import com.openprice.domain.receipt.ReceiptImage;

public class AdminReceiptImageResource extends Resource<ReceiptImage> {
    public static final String LINK_NAME_RECEIPT = "receipt";
    public static final String LINK_NAME_DOWNLOAD = "download";

    public AdminReceiptImageResource(final ReceiptImage resource) {
        super(resource);
    }
}