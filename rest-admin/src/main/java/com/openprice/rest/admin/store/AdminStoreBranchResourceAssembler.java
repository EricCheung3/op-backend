package com.openprice.rest.admin.store;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mvc.BasicLinkBuilder;
import org.springframework.stereotype.Component;

import com.openprice.domain.store.StoreBranch;
import com.openprice.rest.UtilConstants;

@Component
public class AdminStoreBranchResourceAssembler implements ResourceAssembler<StoreBranch, AdminStoreBranchResource> {
    @Override
    public AdminStoreBranchResource toResource(StoreBranch storeBranch) {
        final AdminStoreBranchResource resource = new AdminStoreBranchResource(storeBranch);

        resource.add(linkTo(methodOn(AdminStoreRestController.class).getStoreBranchById(storeBranch.getChain().getId(), storeBranch.getId()))
                .withSelfRel());

        // Hack solution to build template links.
        // Monitor https://github.com/spring-projects/spring-hateoas/issues/169 for nice solution from Spring HATEOAS
        final String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
        final Link chainLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/admin/chains/" + storeBranch.getChain().getId()),
                AdminStoreBranchResource.LINK_NAME_CHAIN);
        resource.add(chainLink);

        return resource;
    }

}
