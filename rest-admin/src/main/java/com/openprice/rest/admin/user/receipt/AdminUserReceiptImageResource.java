package com.openprice.rest.admin.user.receipt;

import org.springframework.hateoas.Resource;

import com.openprice.domain.receipt.ReceiptImage;

public class AdminUserReceiptImageResource extends Resource<ReceiptImage> {

    public static final String LINK_NAME_RECEIPT = "receipt";
    public static final String LINK_NAME_DOWNLOAD = "download";
    public static final String LINK_NAME_BASE64 = "base64";

    public AdminUserReceiptImageResource(final ReceiptImage resource) {
        super(resource);
    }
}
