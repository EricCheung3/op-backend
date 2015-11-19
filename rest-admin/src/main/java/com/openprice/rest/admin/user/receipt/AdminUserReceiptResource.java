package com.openprice.rest.admin.user.receipt;

import java.util.List;

import org.springframework.hateoas.Resource;

import com.openprice.domain.receipt.Receipt;

import lombok.Getter;
import lombok.Setter;

public class AdminUserReceiptResource extends Resource<Receipt> {

    @Getter @Setter
    private List<AdminUserReceiptImageResource> images;

    public AdminUserReceiptResource(final Receipt resource) {
        super(resource);
    }

}
