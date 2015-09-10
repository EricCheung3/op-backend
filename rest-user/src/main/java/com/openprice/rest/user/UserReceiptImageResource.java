package com.openprice.rest.user;

import org.springframework.hateoas.Resource;

import com.openprice.domain.receipt.ReceiptImage;

public class UserReceiptImageResource extends Resource<ReceiptImage> {
    public static final String LINK_NAME_RECEIPT = "receipt";
    public static final String LINK_NAME_DOWNLOAD = "download";
    public static final String LINK_NAME_BASE64 = "base64";
    
    public UserReceiptImageResource(final ReceiptImage resource) {
        super(resource);
    }

}
