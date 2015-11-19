package com.openprice.rest.admin.receipt;

import java.util.List;

import org.springframework.hateoas.Resource;

import com.openprice.domain.receipt.Receipt;

import lombok.Getter;
import lombok.Setter;

public class AdminReceiptResource extends Resource<Receipt> {

    @Getter @Setter
    private String user;

    @Getter @Setter
    private String uploadTimestamp;


    @Getter @Setter
    private List<AdminReceiptImageResource> images;

    public AdminReceiptResource(final Receipt resource) {
        super(resource);
    }

}
