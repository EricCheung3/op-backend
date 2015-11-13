package com.openprice.rest.user.receipt;

import java.util.List;

import org.springframework.hateoas.Resource;

import com.openprice.domain.receipt.ParserResult;

import lombok.Getter;
import lombok.Setter;

public class UserReceiptParserResultResource extends Resource<ParserResult> {

    @Getter @Setter
    private List<UserReceiptItemResource> items;

    UserReceiptParserResultResource(final ParserResult resource) {
        super(resource);
    }

}
