package com.openprice.rest.admin.user.receipt;

import java.util.List;

import org.springframework.hateoas.Resource;

import com.openprice.domain.receipt.Receipt;

import lombok.Getter;
import lombok.Setter;

public class AdminUserReceiptResource extends Resource<Receipt> {

    public static final String LINK_NAME_USER = "user";
    public static final String LINK_NAME_IMAGES = "images";
    public static final String LINK_NAME_IMAGE = "image";
    //public static final String LINK_NAME_ITEMS = "items";

    @Getter @Setter
    private List<AdminUserReceiptImageResource> images;

    public AdminUserReceiptResource(final Receipt resource) {
        super(resource);
    }

}
