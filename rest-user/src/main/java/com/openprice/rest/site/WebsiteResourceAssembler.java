package com.openprice.rest.site;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mvc.BasicLinkBuilder;
import org.springframework.stereotype.Component;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.account.user.UserAccountService;
import com.openprice.rest.UtilConstants;

@Component
public class WebsiteResourceAssembler {

    private final UserAccountService userAccountService;

    @Inject
    public WebsiteResourceAssembler(final UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public WebsiteResource toResource() {
        final UserAccount currentUser = userAccountService.getCurrentUser();
        final WebsiteResource resource = new WebsiteResource();

        if (currentUser != null) {
            resource.setAuthenticated(true);
            resource.setCurrentUser(currentUser);
        } else {
            resource.setAuthenticated(false);
        }

        final String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();

        final Link selfLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT), Link.REL_SELF);
        resource.add(selfLink);

        final Link signinLink = new Link(
                new UriTemplate(baseUri + UtilConstants.URL_SIGNIN), WebsiteResource.LINK_NAME_SIGNIN);
        resource.add(signinLink);

        final Link registerUserLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + SiteApiUrls.URL_PUBLIC_REGISTRATION), WebsiteResource.LINK_NAME_REGISTRATION);
        resource.add(registerUserLink);

        final Link forgetPasswordLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + SiteApiUrls.URL_PUBLIC_RESET_PASSWORD_REQUESTS), WebsiteResource.LINK_NAME_FORGET_PASSWORD);
        resource.add(forgetPasswordLink);

        final Link resetPasswordLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + SiteApiUrls.URL_PUBLIC_RESET_PASSWORD_REQUESTS_REQUEST), WebsiteResource.LINK_NAME_RESET_PASSWORD);
        resource.add(resetPasswordLink);

        return resource;
    }
}
