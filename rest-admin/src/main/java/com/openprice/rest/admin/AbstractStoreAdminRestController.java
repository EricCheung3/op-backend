package com.openprice.rest.admin;

import com.openprice.domain.account.admin.AdminAccountService;
import com.openprice.domain.store.StoreChain;
import com.openprice.domain.store.StoreChainRepository;
import com.openprice.domain.store.StoreService;
import com.openprice.rest.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractStoreAdminRestController extends AbstractAdminRestController {

    protected final StoreService storeService;
    protected final StoreChainRepository storeChainRepository;

    public AbstractStoreAdminRestController(final AdminAccountService adminAccountService,
                                            final StoreService storeService,
                                            final StoreChainRepository storeChainRepository) {
        super(adminAccountService);
        this.storeService = storeService;
        this.storeChainRepository = storeChainRepository;
    }

    protected StoreChain loadStoreChainById(final String chainId) {
        final StoreChain chain = storeChainRepository.findOne(chainId);
        if (chain == null) {
            log.warn("ILLEGAL STORE CHAIN ACCESS! No such chian Id: {}.", chainId);
            throw new ResourceNotFoundException("No store chain with the id: " + chainId);
        }
        return chain;
    }

}
