package com.openprice.rest.admin.user.receipt;


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
import com.openprice.rest.admin.AdminAccountResource;
import com.openprice.rest.admin.AdminApiUrls;

@Component
public class AdminUserReceiptResourceAssembler implements ResourceAssembler<Receipt, AdminUserReceiptResource> {

    private final AdminUserReceiptImageResourceAssembler imageResourceAssembler;

    @Inject
    public AdminUserReceiptResourceAssembler(final AdminUserReceiptImageResourceAssembler imageResourceAssembler) {
        this.imageResourceAssembler = imageResourceAssembler;
    }

    @Override
    public AdminUserReceiptResource toResource(final Receipt receipt) {
        final AdminUserReceiptResource resource = new AdminUserReceiptResource(receipt);

        // Temp solution for embedded resources
        // Monitor https://github.com/spring-projects/spring-hateoas/issues/270
        List<AdminUserReceiptImageResource> images = new ArrayList<>();
        for (ReceiptImage image : receipt.getImages()) {
            images.add(imageResourceAssembler.toResource(image));
        }
        resource.setImages(images);

        resource.add(linkTo(methodOn(AdminUserReceiptRestController.class).getUserReceiptById(receipt.getUser().getId(), receipt.getId()))
                .withSelfRel());

        resource.add(linkTo(methodOn(AdminUserReceiptRestController.class).getUserReceiptImages(receipt.getUser().getId(), receipt.getId(), null, null))
                .withRel(AdminUserReceiptResource.LINK_NAME_IMAGES));

        // Hack solution to build template links.
        // Monitor https://github.com/spring-projects/spring-hateoas/issues/169 for nice solution from Spring HATEOAS
        final String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
        final Link imageLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/admin/users/" + receipt.getUser().getId()
                        + "/receipts/" + receipt.getId() + "/images/{imageId}"),
                AdminUserReceiptResource.LINK_NAME_IMAGE);
        resource.add(imageLink);

        final Link userLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_USERS_USER),
                AdminAccountResource.LINK_NAME_USER);
        resource.add(userLink);

        return resource;
    }

}
