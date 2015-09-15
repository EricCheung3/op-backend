package com.openprice.rest.admin;

import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.openprice.domain.admin.AdminAccount;
import com.openprice.domain.admin.AdminAccountService;

/**
 * RESTful API for AdminAccountResource.
 */
@RestController
public class AdminAccountRestController extends AbstractAdminRestController {

    private final AdminAccountResourceAssembler adminAccountResourceAssembler;

    @Inject
    public AdminAccountRestController(final AdminAccountService adminAccountService,
                                      final AdminAccountResourceAssembler adminAccountResourceAssembler) {
        super(adminAccountService);
        this.adminAccountResourceAssembler = adminAccountResourceAssembler;
    }

    /**
     * Get admin info, which contains role, links to other api.
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = AdminApiUrls.URL_ADMIN)
    @Transactional(readOnly=true)
    public HttpEntity<AdminAccountResource> getAdminAccount() {
        final AdminAccount currentUser = getCurrentAuthenticatedAdmin();
        final AdminAccountResource resource = adminAccountResourceAssembler.toResource(currentUser);
        return ResponseEntity.ok(resource);
    }
}
