package com.openprice.rest.site;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.account.user.UserAccountService;
import com.openprice.rest.LinkBuilder;

@Component
public class WebsiteResourceAssembler implements SiteApiUrls {

    public static final String LINK_NAME_SIGNIN = "signin";
    public static final String LINK_NAME_REGISTRATION = "registration";
    public static final String LINK_NAME_FORGET_PASSWORD = "forgetPassword";
    public static final String LINK_NAME_RESET_PASSWORD = "resetPassword";

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
                   .addLink(LINK_NAME_SIGNIN, "/signin", false, null)
                   .addLink(LINK_NAME_REGISTRATION, URL_PUBLIC_REGISTRATION, false, null)
                   .addLink(LINK_NAME_FORGET_PASSWORD, URL_PUBLIC_RESET_PASSWORD_REQUESTS, false, null)
                   .addLink(LINK_NAME_RESET_PASSWORD, URL_PUBLIC_RESET_PASSWORD_REQUESTS_REQUEST, false, null);

        return resource;
    }
}
