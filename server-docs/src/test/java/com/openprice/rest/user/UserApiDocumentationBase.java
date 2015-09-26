package com.openprice.rest.user;

import com.openprice.rest.ApiDocumentationBase;
import com.openprice.rest.UtilConstants;

public abstract class UserApiDocumentationBase extends ApiDocumentationBase {
    protected String userUrl() {
        return UtilConstants.API_ROOT + UserApiUrls.URL_USER;
    }

    protected String userProfileUrl() {
        return UtilConstants.API_ROOT + UserApiUrls.URL_USER_PROFILE;
    }

}
