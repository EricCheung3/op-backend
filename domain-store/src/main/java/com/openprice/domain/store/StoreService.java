package com.openprice.domain.store;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openprice.domain.common.Address;
import com.openprice.domain.product.ProductCategory;
import com.openprice.domain.product.ProductData;
import com.openprice.domain.product.ProductUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
                                       final String name) {
        final StoreChain chain = StoreChain.createStoreChain(code, name);
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
        return storeBranchRepository.save(branch);
    }

    public CatalogProduct createCatalog(final StoreChain chain,
                                        final String name,
                                        final String number,
                                        final String price,
                                        final String naturalName,
                                        final String labelCodes,
                                        final String productCategory) {
        final CatalogProduct catalog = chain.addCatalogProduct(name, number, price, naturalName, labelCodes, productCategory);
        return catalogRepository.save(catalog);
    }

    public CatalogProduct updateCatalog(final CatalogProduct catalog,
                                        final String name,
                                        final String number,
                                        final String price,
                                        final String naturalName,
                                        final String labelCodes,
                                        final String productCategory) {
        catalog.setCatalogCode(ProductUtils.generateCatalogCode(name, number));
        catalog.setPrice(price);
        catalog.setNaturalName(naturalName);
        catalog.setLabelCodes(labelCodes);
        catalog.setProductCategory(ProductCategory.valueOf(productCategory));
        return catalogRepository.save(catalog);
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
     * @param catalogs
     */
    public void loadCatalog(final StoreChain chain, final ProductData[] productDatas) {
        int newCount = 0;
        int updateCount = 0;
        for (final ProductData productData : productDatas) {
            final CatalogProduct existCatalog = catalogRepository.findByChainAndCatalogCode(chain, productData.getCatalogCode());
            if (existCatalog != null) {
                existCatalog.setPrice(productData.getPrice());
                existCatalog.setNaturalName(productData.getNaturalName());
                existCatalog.setLabelCodes(productData.getLabelCodes());
                existCatalog.setProductCategory(ProductCategory.valueOf(productData.getProductCategory()));
                catalogRepository.save(existCatalog);
                updateCount++;
            } else {
                CatalogProduct newCatalogProduct = chain.addCatalogProduct(productData.getName(),
                                                                           productData.getNumber(),
                                                                           productData.getPrice(),
                                                                           productData.getNaturalName(),
                                                                           productData.getLabelCodes(),
                                                                           productData.getProductCategory());
                catalogRepository.save(newCatalogProduct);
                newCount++;
            }
        }
        storeChainRepository.save(chain);
        log.info("Successfully loaded {} catalog products for store [{}].", productDatas.length, chain.getCode());
        log.info("{} new catalog products added and {} existing catalog products updated.", newCount, updateCount);
    }

    public void loadCatalog(final StoreChain chain, final MultipartFile file) {
        log.info("Loading catalog for store [{}] from file '{}'...", chain.getCode(), file.getOriginalFilename());

        byte[] content = null;
        try {
            content = file.getBytes();
        } catch (IOException ex) {
            log.warn("No content of catalog data to load!");
            throw new RuntimeException("No catalog content.");
        }

        // parse json catalog data
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        InputStreamSource roleResource = new ByteArrayResource(content);
        try {
            ProductData[] catalogs = mapper.readValue(roleResource.getInputStream(), ProductData[].class);
            loadCatalog(chain, catalogs);
        } catch (IOException ex) {
            log.warn("Parse catalog file error!", ex);
            throw new RuntimeException("Cannot load catalog json file.");
        }
    }
}
