package com.openprice.domain.store;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.openprice.domain.common.Address;

@Service
public class StoreService {
    private final StoreChainRepository storeChainRepository;
    private final StoreBranchRepository storeBranchRepository;

    @Inject
    public StoreService(final StoreChainRepository storeChainRepository,
                        final StoreBranchRepository storeBranchRepository) {
        this.storeChainRepository = storeChainRepository;
        this.storeBranchRepository = storeBranchRepository;
    }

    public StoreChain createStoreChain(final String code,
                                       final String name,
                                       final String categories,
                                       final String identifyFields) {
        final StoreChain chain = StoreChain.createStoreChain(code, name);
        chain.setCategories(categories);
        chain.setIdentifyFields(identifyFields);
        return storeChainRepository.save(chain);
    }

    public StoreBranch createStoreBranch(final StoreChain chain,
                                         final String name,
                                         final String phone,
                                         final String gstNumber,
                                         final String address1,
                                         final String address2,
                                         final String city,
                                         final String state,
                                         final String zip,
                                         final String country) {
        final StoreBranch branch = chain.addBranch(name);
        branch.setPhone(phone);
        branch.setGstNumber(gstNumber);
        branch.setAddress(new Address(address1, address2, city, state, zip, country));
        storeChainRepository.save(chain);
        return storeBranchRepository.save(branch);
    }

    public void deleteAllStores() {
        storeChainRepository.deleteAll();
    }
}
