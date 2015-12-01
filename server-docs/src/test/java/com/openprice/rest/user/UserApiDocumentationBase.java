package com.openprice.rest.user;

import com.openprice.common.ApiConstants;
import com.openprice.rest.ApiDocumentationBase;

public abstract class UserApiDocumentationBase extends ApiDocumentationBase {
    protected String userUrl() {
        return ApiConstants.EXTERNAL_API_ROOT + UserApiUrls.URL_USER;
    }

    protected String userProfileUrl() {
        return ApiConstants.EXTERNAL_API_ROOT + UserApiUrls.URL_USER_PROFILE;
    }

}
