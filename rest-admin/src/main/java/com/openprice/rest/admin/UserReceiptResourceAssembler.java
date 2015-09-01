package com.openprice.rest.admin;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.rest.common.UserReceiptImageResource;
import com.openprice.rest.common.UserReceiptResource;

@Component("admin_UserReceiptResourceAssembler")
public class UserReceiptResourceAssembler implements ResourceAssembler<Receipt, UserReceiptResource> {

    private final UserReceiptImageResourceAssembler imageResourceAssembler;
    
    @Inject
    public UserReceiptResourceAssembler(final UserReceiptImageResourceAssembler imageResourceAssembler) {
        this.imageResourceAssembler = imageResourceAssembler;
    }
    
    @Override
    public UserReceiptResource toResource(final Receipt receipt) {
        final UserReceiptResource resource = new UserReceiptResource(receipt);
        
        resource.add(linkTo(methodOn(UserReceiptRestController.class).getUserReceiptById(receipt.getUser().getId(), receipt.getId()))
                .withSelfRel());

        resource.add(linkTo(methodOn(UserReceiptRestController.class).getUploadReceiptImagePath(receipt.getUser().getId(), receipt.getId()))
                .withRel(UserReceiptResource.LINK_NAME_UPLOAD));

        // TODO fix _embedded issue
        List<UserReceiptImageResource> images = new ArrayList<>();
        for (ReceiptImage image : receipt.getImages()) {
            images.add(imageResourceAssembler.toResource(image));
        }
        resource.setImages(images);

        return resource;
    }

}
