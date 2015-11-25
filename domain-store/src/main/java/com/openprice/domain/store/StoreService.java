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
        storeChainRepository.save(chain);
        return storeBranchRepository.save(branch);
    }

    public Catalog createCatalog(final StoreChain chain,
                                     final String name,
                                     final String number,
                                     final String price,
                                     final String naturalName,
                                     final String labelCodes) {
        final Catalog catalog = chain.addCatalog(name, number, price, naturalName, labelCodes);
        storeChainRepository.save(chain);
        return catalogRepository.save(catalog);
    }

    public Catalog updateCatalog(final Catalog catalog,
                                 final String name,
                                 final String number,
                                 final String price,
                                 final String naturalName,
                                 final String labelCodes) {
        catalog.setName(name);
        catalog.setNumber(number);
        catalog.setPrice(price);
        catalog.setNaturalName(naturalName);
        catalog.setLabelCodes(labelCodes);
        catalog.setCode(Catalog.generateCatalogCode(name, number));
        return catalogRepository.save(catalog);
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
    public void loadCatalog(final StoreChain chain, final Catalog[] catalogs) {
        for (final Catalog catalog : catalogs) {
            final Catalog existCatalog = catalogRepository.findByChainAndCode(chain, catalog.getCode());
            if (existCatalog != null) {
                existCatalog.setPrice(catalog.getPrice());
                existCatalog.setNaturalName(catalog.getNaturalName());
                existCatalog.setLabelCodes(catalog.getLabelCodes());
                catalogRepository.save(existCatalog);
            } else {
                Catalog newCatalog = chain.addCatalog(catalog.getName(),
                                                      catalog.getNumber(),
                                                      catalog.getPrice(),
                                                      catalog.getNaturalName(),
                                                      catalog.getLabelCodes());
                catalogRepository.save(newCatalog);
            }
        }
        storeChainRepository.save(chain);
        log.info("Successfully loaded {} catalogs", catalogs.length);
    }

    public void loadCatalog(final StoreChain chain, final MultipartFile file) {
        byte[] content = null;
        try {
            content = file.getBytes();
        } catch (IOException ex) {
            log.error("No content of catalog data to load!");
            throw new RuntimeException("No catalog content.");
        }

        // parse json catalog data
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        InputStreamSource roleResource = new ByteArrayResource(content);
        try {
            Catalog[] catalogs = mapper.readValue(roleResource.getInputStream(), Catalog[].class);
            loadCatalog(chain, catalogs);
        } catch (IOException ex) {
            log.error("Parse catalog file error!", ex);
            throw new RuntimeException("Cannot load catalog json file.");
        }
    }
}
