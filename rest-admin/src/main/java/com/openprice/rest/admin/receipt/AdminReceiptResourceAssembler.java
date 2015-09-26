package com.openprice.rest.admin.receipt;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        resource.setUser(receipt.getUser().getProfile().getDisplayName());
        // format uploaded timestamp
        LocalDateTime createdTime = receipt.getCreatedTime();
        resource.setUploadTimestamp(createdTime.format(DateTimeFormatter.ISO_DATE_TIME));

        // Temp solution for embedded resources
        // Monitor https://github.com/spring-projects/spring-hateoas/issues/270
        List<AdminReceiptImageResource> images = new ArrayList<>();
        for (ReceiptImage image : receipt.getImages()) {
            images.add(imageResourceAssembler.toResource(image));
        }
        resource.setImages(images);

        resource.add(linkTo(methodOn(AdminReceiptRestController.class).getReceiptById(receipt.getId()))
                .withSelfRel());

        // Hack solution to build template links.
        // Monitor https://github.com/spring-projects/spring-hateoas/issues/169 for nice solution from Spring HATEOAS
        final String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
        final Link imagesLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/admin/receipts/" + receipt.getId() + "/images" + UtilConstants.PAGINATION_TEMPLATES),
                AdminReceiptResource.LINK_NAME_IMAGES);
        resource.add(imagesLink);
        final Link imageLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/admin/receipts/" + receipt.getId() + "/images/{imageId}"),
                AdminReceiptResource.LINK_NAME_IMAGE);
        resource.add(imageLink);

        final Link itemsLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/admin/receipts/" + receipt.getId() + "/items" + UtilConstants.PAGINATION_TEMPLATES),
                AdminReceiptResource.LINK_NAME_ITEMS);
        resource.add(itemsLink);

        return resource;
    }

}
