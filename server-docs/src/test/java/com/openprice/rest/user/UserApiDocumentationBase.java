package com.openprice.rest.user;

import com.openprice.common.ApiConstants;
import com.openprice.rest.ApiDocumentationBase;

public abstract class UserApiDocumentationBase extends ApiDocumentationBase implements UserApiUrls {
    protected String userUrl() {
        return ApiConstants.EXTERNAL_API_ROOT + URL_USER;
    }

    protected String userProfileUrl() {
        return ApiConstants.EXTERNAL_API_ROOT + URL_USER_PROFILE;
    }

}
