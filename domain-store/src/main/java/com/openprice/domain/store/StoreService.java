package com.openprice.domain.store;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.openprice.domain.common.Address;
import com.openprice.domain.product.ProductCategory;
import com.openprice.domain.product.ProductData;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StoreService {
    private final StoreChainRepository storeChainRepository;
    private final StoreBranchRepository storeBranchRepository;
    private final CatalogProductRepository catalogProductRepository;

    @Inject
    public StoreService(final StoreChainRepository storeChainRepository,
                        final StoreBranchRepository storeBranchRepository,
                        final CatalogProductRepository catalogProductRepository) {
        this.storeChainRepository = storeChainRepository;
        this.storeBranchRepository = storeBranchRepository;
        this.catalogProductRepository = catalogProductRepository;
    }

    public StoreChain createStoreChain(final String code,
                                       final String name) {
        final StoreChain chain = new StoreChain();
        chain.setCode(code);
        chain.setName(name);
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
        final StoreBranch branch = new StoreBranch();
        branch.setChain(chain);
        branch.setName(name);
        branch.setPhone(phone);
        branch.setGstNumber(gstNumber);
        branch.setAddress(new Address(address1, address2, city, state, zip, country));
        return storeBranchRepository.save(branch);
    }

    public CatalogProduct createCatalogProduct(final StoreChain storeChain,
                                               final String name,
                                               final String number,
                                               final String price,
                                               final String naturalName,
                                               final String labelCodes,
                                               final ProductCategory productCategory) {
        final CatalogProduct catalogProduct = new CatalogProduct(name, number);
        catalogProduct.setChain(storeChain);
        catalogProduct.setPrice(price);
        catalogProduct.setNaturalName(naturalName);
        catalogProduct.setLabelCodes(labelCodes);
        catalogProduct.setProductCategory(productCategory);
        return catalogProductRepository.save(catalogProduct);
    }

    public CatalogProduct updateCatalogProduct(final CatalogProduct catalog,
                                               final String price,
                                               final String naturalName,
                                               final String labelCodes,
                                               final ProductCategory productCategory) {
        catalog.setPrice(price);
        catalog.setNaturalName(naturalName);
        catalog.setLabelCodes(labelCodes);
        catalog.setProductCategory(productCategory);
        return catalogProductRepository.save(catalog);
    }

    public void deleteAllStores() {
        storeChainRepository.deleteAll();
    }

    /**
     * Load and update catalog list for a store chain.
     * If catalog product not exists in the chain, add it; otherwise, update the catalog product.
     *
     * TODO: improve performance for large catalog list.
     *
     * @param chain
     * @param productDatas product info with correct ProductCategory code
     */
    public void batchUpdateCatalog(final StoreChain chain, final ProductData[] productDatas) {
        int newCount = 0;
        int updateCount = 0;
        for (final ProductData productData : productDatas) {
            final CatalogProduct existCatalog = catalogProductRepository.findByChainAndCatalogCode(chain, productData.getCatalogCode());
            if (existCatalog != null) {
                updateCatalogProduct(existCatalog,
                                     productData.getPrice(),
                                     productData.getNaturalName(),
                                     productData.getLabelCodes(),
                                     ProductCategory.valueOf(productData.getProductCategory()));
                updateCount++;
            } else {
                createCatalogProduct(chain,
                                     productData.getName(),
                                     productData.getNumber(),
                                     productData.getPrice(),
                                     productData.getNaturalName(),
                                     productData.getLabelCodes(),
                                     ProductCategory.valueOf(productData.getProductCategory()));
                newCount++;
            }
        }
        log.info("Successfully loaded {} catalog products for store [{}].", productDatas.length, chain.getCode());
        log.info("{} new catalog products added and {} existing catalog products updated.", newCount, updateCount);
    }
}
