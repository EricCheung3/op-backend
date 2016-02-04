package com.openprice.rest.admin;

import com.openprice.domain.account.admin.AdminAccountService;
import com.openprice.store.StoreMetadata;

public abstract class AbstractStoreAdminRestController extends AbstractAdminRestController {

    protected final StoreMetadata storeMetadata;

    public AbstractStoreAdminRestController(final AdminAccountService adminAccountService,
                                            final StoreMetadata storeMetadata) {
        super(adminAccountService);
        this.storeMetadata = storeMetadata;
    }
}
