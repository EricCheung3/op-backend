package com.openprice.rest.admin.receipt;

import java.util.List;

import org.springframework.hateoas.Resource;

import com.openprice.domain.receipt.Receipt;

import lombok.Getter;
import lombok.Setter;

public class AdminReceiptResource extends Resource<Receipt> {
    public static final String LINK_NAME_IMAGES = "images";
    public static final String LINK_NAME_IMAGE = "image";
    public static final String LINK_NAME_ITEMS = "items";

    @Getter @Setter
    private List<AdminReceiptImageResource> images;

    public AdminReceiptResource(final Receipt resource) {
        super(resource);
    }

}
