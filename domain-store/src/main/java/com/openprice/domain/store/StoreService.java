package com.openprice.domain.store;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.openprice.domain.common.Address;

@Service
public class StoreService {
    private final StoreRepository storeRepository;
    private final StoreBranchRepository storeBranchRepository;

    @Inject
    public StoreService(final StoreRepository storeRepository, final StoreBranchRepository storeBranchRepository) {
        this.storeRepository = storeRepository;
        this.storeBranchRepository = storeBranchRepository;
    }

    /**
     * Builder method to create a new store
     * @param code
     * @param name
     * @param categories
     * @param identifyField
     * @return
     */
    public Store createStore(final String code,
                             final String name,
                             final String categories,
                             final String identifyField) {
        Store store = new Store();
        store.setCode(code);
        store.setName(name);
        store.setCategories(categories);
        store.setIdentifyFields(identifyField);
        return storeRepository.save(store);
    }

    public StoreBranch createStoreBranch(final Store store,
                                         final String name,
                                         final String phone,
                                         final String gstNumber,
                                         final String address1,
                                         final String address2,
                                         final String city,
                                         final String state,
                                         final String zip,
                                         final String country) {
        StoreBranch branch = new StoreBranch();
        branch.setStore(store);
        branch.setName(name);
        branch.setPhone(phone);
        branch.setGstNumber(gstNumber);
        branch.setAddress(new Address(address1, address2, city, state, zip, country));
        return storeBranchRepository.save(branch);
    }
}
