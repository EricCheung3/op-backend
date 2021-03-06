package com.openprice.rest.user;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.rest.LinkBuilder;

import lombok.Getter;
import lombok.Setter;

public class UserAccountResource extends Resource<UserAccount> {

    @Getter @Setter
    private String uploadUrl;

    @Getter @Setter
    private String receiptsUrl;

    public UserAccountResource(final UserAccount account) {
        super(account);
    }

    @Component
    public static class Assembler implements ResourceAssembler<UserAccount, UserAccountResource>, UserApiUrls {

        @Override
        public UserAccountResource toResource(final UserAccount userAccount) {
            final UserAccountResource resource = new UserAccountResource(userAccount);

            final LinkBuilder linkBuilder = new LinkBuilder(resource);
            linkBuilder.addLink(Link.REL_SELF, URL_USER, false, null)
                       .addLink("profile", URL_USER_PROFILE, false, null)
                       .addLink("upload", URL_USER_RECEIPTS_UPLOAD, false, null)
                       .addLink("shoppingList", URL_USER_SHOPPINGLIST, false, null)
                       .addLink("allReceipts", URL_USER_ALL_RECEIPTS, false, null)
                       .addLink("receipts", URL_USER_RECEIPTS, true, null)
                       .addLink("receipt", URL_USER_RECEIPTS_RECEIPT, false, null)
                       .addLink("stores", URL_USER_SHOPPING_STORES, true, null)
                       .addLink("store", URL_USER_SHOPPING_STORES_STORE, false, null)
                       .addLink("categories", URL_USER_CATEGORIES, false, null)
                       .addLink("searchStores", URL_USER_STORES_SEARCH, false, null);


            resource.setReceiptsUrl(resource.getLink(Link.REL_SELF).getHref() + "/receipts"); // HACK. Remove it after ABBYY license ok.
            resource.setUploadUrl(resource.getLink("upload").getHref());

            return resource;
        }

    }

}