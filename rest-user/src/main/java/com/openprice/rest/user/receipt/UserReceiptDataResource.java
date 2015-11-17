package com.openprice.rest.user.receipt;

import java.util.List;

import org.springframework.hateoas.Resource;

import com.openprice.domain.receipt.ReceiptData;

import lombok.Getter;
import lombok.Setter;

public class UserReceiptDataResource extends Resource<ReceiptData> {

    @Getter @Setter
    private List<UserReceiptItemResource> items;

    UserReceiptDataResource(final ReceiptData resource) {
        super(resource);
    }

}
