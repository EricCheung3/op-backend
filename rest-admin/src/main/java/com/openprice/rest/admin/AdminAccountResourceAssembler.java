package com.openprice.rest.admin;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mvc.BasicLinkBuilder;
import org.springframework.stereotype.Component;

import com.openprice.domain.admin.AdminAccount;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.admin.user.UserAccountRestController;

@Component
public class AdminAccountResourceAssembler implements ResourceAssembler<AdminAccount, AdminAccountResource> {

    @Override
    public AdminAccountResource toResource(final AdminAccount adminAccount) {
        final AdminAccountResource resource = new AdminAccountResource(adminAccount);

        resource.add(linkTo(methodOn(AdminAccountRestController.class).getAdminAccount()).withSelfRel());

        resource.add(linkTo(methodOn(UserAccountRestController.class).getUserAccounts(null, null))
                .withRel(AdminAccountResource.LINK_NAME_USERS));

        final String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();

        final Link userLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_USERS_USER),
                AdminAccountResource.LINK_NAME_USER);
        resource.add(userLink);

        return resource;

    }

}
