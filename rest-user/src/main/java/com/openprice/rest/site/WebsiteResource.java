package com.openprice.rest.site;

import org.springframework.hateoas.ResourceSupport;

import com.openprice.domain.account.user.UserAccount;

import lombok.Getter;
import lombok.Setter;

public class WebsiteResource extends ResourceSupport {
    public static final String LINK_NAME_SIGNIN = "signin";
    public static final String LINK_NAME_REGISTRATION = "registration";

    @Getter @Setter
    private boolean authenticated;
    
    @Getter @Setter
    private UserAccount currentUser;

    @Override
    public String toString() {
        return super.toString() + ", authenticated: " + authenticated;
    }
}
