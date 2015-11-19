package com.openprice.rest.site;

import org.springframework.hateoas.ResourceSupport;

import com.openprice.domain.account.user.UserAccount;

import lombok.Getter;
import lombok.Setter;

public class WebsiteResource extends ResourceSupport {

    @Getter @Setter
    private boolean authenticated;

    @Getter @Setter
    private UserAccount currentUser;

    @Override
    public String toString() {
        return super.toString() + ", authenticated: " + authenticated;
    }
}
