package com.openprice.rest.user.receipt;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.receipt.ReceiptItem;

@Component
public class UserReceiptItemResourceAssembler implements ResourceAssembler<ReceiptItem, UserReceiptItemResource> {

    @Override
    public UserReceiptItemResource toResource(final ReceiptItem receiptItem) {
        final UserReceiptItemResource resource = new UserReceiptItemResource(receiptItem);

        resource.add(linkTo(methodOn(UserReceiptDataRestController.class).getUserReceiptItemById(receiptItem.getReceiptData().getReceipt().getId(), receiptItem.getId()))
                .withSelfRel());
//
//        resource.add(linkTo(methodOn(UserReceiptRestController.class).getUserReceiptById(receiptImage.getReceipt().getId()))
//                .withRel(UserReceiptImageResource.LINK_NAME_RECEIPT));
//
//        resource.add(linkTo(methodOn(UserReceiptImageRestController.class).downloadUserReceiptImage(receiptImage.getReceipt().getId(), receiptImage.getId()))
//                .withRel(UserReceiptImageResource.LINK_NAME_DOWNLOAD));
//
//        resource.add(linkTo(methodOn(UserReceiptImageRestController.class).downloadUserReceiptImageAsBase64(receiptImage.getReceipt().getId(), receiptImage.getId()))
//                .withRel(UserReceiptImageResource.LINK_NAME_BASE64));

        return resource;
    }

}
