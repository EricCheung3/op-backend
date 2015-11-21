package com.openprice.domain.store;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.openprice.domain.common.Address;

@Service
public class StoreService {
    private final StoreChainRepository storeChainRepository;
    private final StoreBranchRepository storeBranchRepository;
    private final CatalogRepository catalogRepository;

    @Inject
    public StoreService(final StoreChainRepository storeChainRepository,
                        final StoreBranchRepository storeBranchRepository,
                        final CatalogRepository catalogRepository) {
        this.storeChainRepository = storeChainRepository;
        this.storeBranchRepository = storeBranchRepository;
        this.catalogRepository = catalogRepository;
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

    /**
     * Load and update catalog list for a store chain.
     * If catalog not exists in the chain, add it; otherwise, update the catalog.
     *
     * TODO: improve performance for large catalog list.
     *
     * @param chain
     * @param catalogs
     */
    public void loadCatalog(final StoreChain chain, final List<Catalog> catalogs) {
        for (final Catalog catalog : catalogs) {
            final Catalog existCatalog = catalogRepository.findByChainAndCode(chain, catalog.getCode());
            if (existCatalog != null) {
                existCatalog.setCategory(catalog.getCategory());
                existCatalog.setPrice(catalog.getPrice());
                existCatalog.setNaturalName(catalog.getNaturalName());
                existCatalog.setLabelCodes(catalog.getLabelCodes());
                catalogRepository.save(existCatalog);
            } else {
                Catalog newCatalog = chain.addCatalog(catalog.getCode(), catalog.getName(), catalog.getNumber(),
                        catalog.getCategory(), catalog.getPrice(), catalog.getNaturalName(), catalog.getLabelCodes());
                catalogRepository.save(newCatalog);
            }
        }
        storeChainRepository.save(chain);
    }
}
