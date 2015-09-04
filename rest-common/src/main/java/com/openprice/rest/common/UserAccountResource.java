package com.openprice.rest.common;
import org.springframework.hateoas.Resource;

import com.openprice.domain.account.UserAccount;

import lombok.Getter;
import lombok.Setter;

public class UserAccountResource extends Resource<UserAccount> {
    public static final String LINK_NAME_LOCK_STATE = "lockState";
    public static final String LINK_NAME_PROFILE = "profile";
    public static final String LINK_NAME_RECEIPTS = "receipts";
    public static final String LINK_NAME_RECEIPT = "receipt";
    public static final String LINK_NAME_STORES = "stores";
    public static final String LINK_NAME_STORE = "store";
    public static final String LINK_NAME_UPLOAD = "upload";
    public static final String LINK_NAME_SHOPPING_LIST = "shoppingList";
    
    @Getter @Setter
    private UserProfileResource profile;
    
    public UserAccountResource(final UserAccount account) {
        super(account);
        profile = new UserProfileResource(account.getProfile());
    }

}

