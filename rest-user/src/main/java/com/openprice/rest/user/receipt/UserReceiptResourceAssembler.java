package com.openprice.rest.user.receipt;

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

        // Hack solution to build template links.
        // Monitor https://github.com/spring-projects/spring-hateoas/issues/169 for nice solution from Spring HATEOAS
        final String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();

        final Link selfLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/user/receipts/"+ receipt.getId()), Link.REL_SELF);
        resource.add(selfLink);

        final Link imagesLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/user/receipts/"+ receipt.getId() + "/images" + UtilConstants.PAGINATION_TEMPLATES),
                UserReceiptResource.LINK_NAME_IMAGES);
        resource.add(imagesLink);

        final Link imageLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/user/receipts/"+ receipt.getId() + "/images/{imageId}"),
                UserReceiptResource.LINK_NAME_IMAGE);
        resource.add(imageLink);

        final Link resultLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/user/receipts/"+ receipt.getId() + "/result"),
                UserReceiptResource.LINK_NAME_PARSER_RESULT);
        resource.add(resultLink);

        final Link itemsLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/user/receipts/"+ receipt.getId() + "/result/items" + UtilConstants.PAGINATION_TEMPLATES),
                UserReceiptResource.LINK_NAME_ITEMS);
        resource.add(itemsLink);

        final Link itemLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/user/receipts/"+ receipt.getId() + "/result/items/{itemId}"),
                UserReceiptResource.LINK_NAME_ITEM);
        resource.add(itemLink);

        final Link feedbackLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/user/receipts/"+ receipt.getId() + "/feedback"),
                UserReceiptResource.LINK_NAME_FEEDBACK);
        resource.add(feedbackLink);

        final Link uploadLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/user/receipts/"+ receipt.getId() + "/images/upload"),
                UserReceiptResource.LINK_NAME_UPLOAD);
        resource.add(uploadLink);

        // TODO fix _embedded issue
        List<UserReceiptImageResource> images = new ArrayList<>();
        for (ReceiptImage image : receipt.getImages()) {
            images.add(imageResourceAssembler.toResource(image));
        }
        resource.setImages(images);

        return resource;
    }

}
