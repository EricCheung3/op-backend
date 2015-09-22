package com.openprice.rest.admin.receipt;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mvc.BasicLinkBuilder;
import org.springframework.stereotype.Component;

import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.rest.UtilConstants;

@Component
public class AdminReceiptResourceAssembler implements ResourceAssembler<Receipt, AdminReceiptResource> {

    private final AdminReceiptImageResourceAssembler imageResourceAssembler;

    @Inject
    public AdminReceiptResourceAssembler(final AdminReceiptImageResourceAssembler imageResourceAssembler) {
        this.imageResourceAssembler = imageResourceAssembler;
    }

    @Override
    public AdminReceiptResource toResource(final Receipt receipt) {
        final AdminReceiptResource resource = new AdminReceiptResource(receipt);

        // Temp solution for embedded resources
        // Monitor https://github.com/spring-projects/spring-hateoas/issues/270
        List<AdminReceiptImageResource> images = new ArrayList<>();
        for (ReceiptImage image : receipt.getImages()) {
            images.add(imageResourceAssembler.toResource(image));
        }
        resource.setImages(images);

        resource.add(linkTo(methodOn(AdminReceiptRestController.class).getReceiptById(receipt.getId()))
                .withSelfRel());

        resource.add(linkTo(methodOn(AdminReceiptRestController.class).getReceiptImages(receipt.getId(), null, null))
                .withRel(AdminReceiptResource.LINK_NAME_IMAGES));

        resource.add(linkTo(methodOn(AdminReceiptRestController.class).getReceiptItems(receipt.getId()))
                .withRel(AdminReceiptResource.LINK_NAME_ITEMS));

        // Hack solution to build template links.
        // Monitor https://github.com/spring-projects/spring-hateoas/issues/169 for nice solution from Spring HATEOAS
        final String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
        final Link imageLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/admin/receipts/" + receipt.getId() + "/images/{imageId}"),
                AdminReceiptResource.LINK_NAME_IMAGE);
        resource.add(imageLink);

        return resource;
    }

}
