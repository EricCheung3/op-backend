package com.openprice.rest.user;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.rest.LinkBuilder;

@Component
public class UserAccountResourceAssembler implements ResourceAssembler<UserAccount, UserAccountResource>, UserApiUrls {

    public static final String LINK_NAME_LOCK_STATE = "lockState";
    public static final String LINK_NAME_PROFILE = "profile";
    public static final String LINK_NAME_RECEIPTS = "receipts";
    public static final String LINK_NAME_RECEIPT = "receipt";
    public static final String LINK_NAME_STORES = "stores";
    public static final String LINK_NAME_STORE = "store";
    public static final String LINK_NAME_UPLOAD = "upload";
    public static final String LINK_NAME_SHOPPING_LIST = "shoppingList";

    @Override
    public UserAccountResource toResource(final UserAccount userAccount) {
        final UserAccountResource resource = new UserAccountResource(userAccount);
        final LinkBuilder linkBuilder = new LinkBuilder(resource);
        linkBuilder.addLink(Link.REL_SELF, URL_USER, false, null)
                   .addLink(LINK_NAME_PROFILE, URL_USER_PROFILE, false, null)
                   .addLink(LINK_NAME_UPLOAD, URL_USER_RECEIPTS_UPLOAD, false, null)
                   .addLink(LINK_NAME_SHOPPING_LIST, URL_USER_SHOPPINGLIST, false, null)
                   .addLink(LINK_NAME_RECEIPTS, URL_USER_RECEIPTS, true, null)
                   .addLink(LINK_NAME_RECEIPT, URL_USER_RECEIPTS_RECEIPT, false, null)
                   .addLink(LINK_NAME_STORES, URL_USER_SHOPPING_STORES, true, null)
                   .addLink(LINK_NAME_STORE, URL_USER_SHOPPING_STORES_STORE, false, null);
        return resource;
    }

}
