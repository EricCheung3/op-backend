package com.openprice.rest.user.receipt;

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
public class UserReceiptResourceAssembler implements ResourceAssembler<Receipt, UserReceiptResource> {

    private final UserReceiptImageResourceAssembler imageResourceAssembler;

    @Inject
    public UserReceiptResourceAssembler(final UserReceiptImageResourceAssembler imageResourceAssembler) {
        this.imageResourceAssembler = imageResourceAssembler;
    }

    @Override
    public UserReceiptResource toResource(final Receipt receipt) {
        final UserReceiptResource resource = new UserReceiptResource(receipt);

        resource.add(linkTo(methodOn(UserReceiptRestController.class).getUserReceiptById(receipt.getId()))
                .withSelfRel());

        // Hack solution to build template links for "image" and "rating".
        // Monitor https://github.com/spring-projects/spring-hateoas/issues/169 for nice solution from Spring HATEOAS
        final String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
        final Link imagesLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/user/receipts/"+ receipt.getId() + "/images" + UtilConstants.PAGINATION_TEMPLATES),
                UserReceiptResource.LINK_NAME_IMAGES);
        resource.add(imagesLink);

        final Link imageLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/user/receipts/"+ receipt.getId() + "/images/{imageId}"),
                UserReceiptResource.LINK_NAME_IMAGE);
        resource.add(imageLink);

        final Link ratingLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/user/receipts/"+ receipt.getId() + "/feedback"),
                UserReceiptResource.LINK_NAME_FEEDBACK);
        resource.add(ratingLink);

        resource.add(linkTo(methodOn(UserReceiptRestController.class).getUserReceiptItems(receipt.getId()))
                .withRel(UserReceiptResource.LINK_NAME_ITEMS));

        resource.add(linkTo(methodOn(UserReceiptImageRestController.class).getUploadReceiptImagePath(receipt.getId()))
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
