package com.openprice.rest.user;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.rest.LinkBuilder;

@Component
public class UserAccountResourceAssembler implements ResourceAssembler<UserAccount, UserAccountResource>, UserApiUrls {

    @Override
    public UserAccountResource toResource(final UserAccount userAccount) {
        final UserAccountResource resource = new UserAccountResource(userAccount);

        final LinkBuilder linkBuilder = new LinkBuilder(resource);
        linkBuilder.addLink(Link.REL_SELF, URL_USER, false, null)
                   .addLink("profile", URL_USER_PROFILE, false, null)
                   .addLink("upload", URL_USER_RECEIPTS_UPLOAD, false, null)
                   .addLink("shoppingList", URL_USER_SHOPPINGLIST, false, null)
                   .addLink("receipts", URL_USER_RECEIPTS, true, null)
                   .addLink("receipt", URL_USER_RECEIPTS_RECEIPT, false, null)
                   .addLink("stores", URL_USER_SHOPPING_STORES, true, null)
                   .addLink("store", URL_USER_SHOPPING_STORES_STORE, false, null);

        resource.setReceiptsUrl(resource.getLink("receipts").getHref());
        resource.setUploadUrl(resource.getLink("upload").getHref());
        return resource;
    }

}
