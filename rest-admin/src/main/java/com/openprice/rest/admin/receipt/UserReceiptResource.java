package com.openprice.rest.admin.receipt;

import java.util.List;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.core.Relation;

import com.openprice.domain.receipt.Receipt;

import lombok.Getter;
import lombok.Setter;

@Relation(value = "receipt")
public class UserReceiptResource extends Resource<Receipt> {

    public static final String LINK_NAME_IMAGES = "images";
    public static final String LINK_NAME_ITEMS = "items";
    public static final String LINK_NAME_UPLOAD = "upload";

    @Getter @Setter
    private List<UserReceiptImageResource> images;

    public UserReceiptResource(final Receipt resource) {
        super(resource);
    }

}
