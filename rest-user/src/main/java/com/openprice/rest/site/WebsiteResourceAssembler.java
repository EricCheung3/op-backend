package com.openprice.rest.site;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.account.user.UserAccountService;
import com.openprice.rest.LinkBuilder;

@Component
public class WebsiteResourceAssembler implements SiteApiUrls {

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

        LinkBuilder linkBuilder = new LinkBuilder(resource);
        linkBuilder.addLink(Link.REL_SELF, "", false, null)
                   .addLink("signin", "/signin", false, null)
                   .addLink("registration", URL_PUBLIC_REGISTRATION, false, null)
                   .addLink("forgetPassword", URL_PUBLIC_RESET_PASSWORD_REQUESTS, false, null)
                   .addLink("resetPassword", URL_PUBLIC_RESET_PASSWORD_REQUESTS_REQUEST, false, null);

        return resource;
    }
}
