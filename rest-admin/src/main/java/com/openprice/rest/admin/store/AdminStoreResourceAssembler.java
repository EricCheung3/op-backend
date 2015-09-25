package com.openprice.rest.admin.store;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mvc.BasicLinkBuilder;
import org.springframework.stereotype.Component;

import com.openprice.domain.store.Store;
import com.openprice.rest.UtilConstants;

@Component
public class AdminStoreResourceAssembler implements ResourceAssembler<Store, AdminStoreResource> {

    @Override
    public AdminStoreResource toResource(Store store) {
        final AdminStoreResource resource = new AdminStoreResource(store);

        resource.add(linkTo(methodOn(AdminStoreRestController.class).getStoreById(store.getId()))
                .withSelfRel());

        // Hack solution to build template links.
        // Monitor https://github.com/spring-projects/spring-hateoas/issues/169 for nice solution from Spring HATEOAS
        final String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
        final Link branchesLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/admin/stores/" + store.getId() + "/branches" + UtilConstants.PAGINATION_TEMPLATES),
                AdminStoreResource.LINK_NAME_BRANCHES);
        resource.add(branchesLink);

        final Link branchLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/admin/stores/" + store.getId() + "/branches/{branchId}"),
                AdminStoreResource.LINK_NAME_BRANCH);
        resource.add(branchLink);

        return resource;
    }

}
