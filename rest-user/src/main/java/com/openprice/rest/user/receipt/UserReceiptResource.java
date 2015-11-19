package com.openprice.rest.user.receipt;

import java.util.List;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.core.Relation;

import com.openprice.domain.receipt.Receipt;

import lombok.Getter;
import lombok.Setter;

@Relation(value = "receipt") // TODO why doing this?
public class UserReceiptResource extends Resource<Receipt> {

    @Getter @Setter
    private List<UserReceiptImageResource> images;

    public UserReceiptResource(final Receipt resource) {
        super(resource);
    }

}
