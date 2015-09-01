package com.openprice.rest.site;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mvc.BasicLinkBuilder;
import org.springframework.stereotype.Component;

import com.openprice.domain.account.UserAccount;
import com.openprice.domain.account.UserAccountService;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.UtilConstants;

@Component
public class WebsiteResourceAssembler {
    private final UserAccountService userAccountService;

    @Inject
    public WebsiteResourceAssembler(final UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public WebsiteResource toResource() {
        final UserAccount currentUser = this.userAccountService.getCurrentUser();
        final WebsiteResource resource = new WebsiteResource();
        
        if (currentUser != null) {
            resource.setAuthenticated(true);
            resource.setCurrentUser(currentUser);
        } else {
            resource.setAuthenticated(false);
        }

        try{
            resource.add(linkTo(methodOn(WebsiteRestController.class).getPublicWebsiteResource())
                    .withSelfRel());

            final String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();

            final Link signinLink = new Link(
                    new UriTemplate(baseUri + UtilConstants.URL_SIGNIN), WebsiteResource.LINK_NAME_SIGNIN);
            resource.add(signinLink);

            final Link registerUserLink = new Link(
                    new UriTemplate(baseUri + UtilConstants.API_ROOT + SiteApiUrls.URL_REGISTRATION_USERS), WebsiteResource.LINK_NAME_REGISTRATION);
            resource.add(registerUserLink);
        } catch (ResourceNotFoundException ex) {
        }

        return resource;
    }
}
