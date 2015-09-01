package com.openprice.rest.admin;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import javax.inject.Inject;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mvc.BasicLinkBuilder;
import org.springframework.stereotype.Component;

import com.openprice.domain.account.UserAccount;
import com.openprice.rest.UtilConstants;

@Component
public class AdminAccountResourceAssembler implements ResourceAssembler<UserAccount, AdminAccountResource> {

    private final PagedResourcesAssembler<UserAccount> assembler;

    @Inject
    public AdminAccountResourceAssembler(final PagedResourcesAssembler<UserAccount> assembler) {
        this.assembler = assembler;
    }

    @Override
    public AdminAccountResource toResource(final UserAccount userAccount) {
        final AdminAccountResource resource = new AdminAccountResource(userAccount);

        resource.add(linkTo(methodOn(AdminAccountRestController.class).getAdminAccount()).withSelfRel());

        final String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();

        final Link usersLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_USERS),
                AdminAccountResource.LINK_NAME_USERS);
        resource.add(assembler.appendPaginationParameterTemplates(usersLink));

        final Link userLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_USERS_USER),
                AdminAccountResource.LINK_NAME_USER);
        resource.add(userLink);
        
        return resource;

    }

}
