package com.openprice.rest.admin.store;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openprice.domain.account.admin.AdminAccount;
import com.openprice.domain.account.admin.AdminAccountService;
import com.openprice.domain.product.ProductCategory;
import com.openprice.domain.product.ProductData;
import com.openprice.domain.store.CatalogProduct;
import com.openprice.domain.store.CatalogProductRepository;
import com.openprice.domain.store.StoreChain;
import com.openprice.domain.store.StoreChainRepository;
import com.openprice.domain.store.StoreService;
import com.openprice.rest.InvalidInputException;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.admin.AbstractStoreAdminRestController;

import lombok.extern.slf4j.Slf4j;

/**
 * REST API Controller for store catalog management.
 *
 */
@RestController
@Slf4j
public class AdminCatalogProductRestController extends AbstractStoreAdminRestController {

    private final CatalogProductRepository catalogProductRepository;
    private final AdminCatalogProductResource.Assembler catalogResourceAssembler;

    @Inject
    public AdminCatalogProductRestController(final AdminAccountService adminAccountService,
                                      final StoreService storeService,
                                      final StoreChainRepository storeChainRepository,
                                      final CatalogProductRepository catalogProductRepository,
                                      final AdminCatalogProductResource.Assembler catalogResourceAssembler) {
        super(adminAccountService, storeService, storeChainRepository);
        this.catalogProductRepository = catalogProductRepository;
        this.catalogResourceAssembler = catalogResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_ADMIN_CHAINS_CHAIN_CATALOGS)
    public HttpEntity<PagedResources<AdminCatalogProductResource>> getCatalogs(
            @PathVariable("chainId") final String chainId,
            @PageableDefault(size = UtilConstants.DEFAULT_RETURN_RECORD_COUNT, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<CatalogProduct> assembler) throws ResourceNotFoundException {
        final StoreChain chain = loadStoreChainById(chainId);
        final Page<CatalogProduct> catalogs = catalogProductRepository.findByChain(chain, pageable);
        return ResponseEntity.ok(assembler.toResource(catalogs, catalogResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.POST, value = URL_ADMIN_CHAINS_CHAIN_CATALOGS)
    public HttpEntity<Void> createStoreCatalogProduct(
            @PathVariable("chainId") final String chainId,
            @RequestBody final AdminProductDataForm form) throws ResourceNotFoundException {
        final CatalogProduct catalog = newCatalogProduct(form, chainId);
        final URI location = linkTo(methodOn(AdminCatalogProductRestController.class).getCatalogById(chainId, catalog.getId())).toUri();
        return ResponseEntity.created(location).body(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_ADMIN_CHAINS_CHAIN_CATALOGS_CATALOG)
    public HttpEntity<AdminCatalogProductResource> getCatalogById(
            @PathVariable("chainId") final String chainId,
            @PathVariable("catalogId") final String catalogId) throws ResourceNotFoundException {
        final StoreChain store = loadStoreChainById(chainId);
        final CatalogProduct catalog = loadCatalogByIdAndCheckStore(catalogId, store);
        return ResponseEntity.ok(catalogResourceAssembler.toResource(catalog));
    }

    @RequestMapping(method = RequestMethod.PUT, value = URL_ADMIN_CHAINS_CHAIN_CATALOGS_CATALOG)
    public HttpEntity<Void> updateCatalog(
            @PathVariable("chainId") final String chainId,
            @PathVariable("catalogId") final String catalogId,
            @RequestBody final AdminProductDataForm form) throws ResourceNotFoundException {
        updateCatalogProduct(chainId, catalogId, form);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = URL_ADMIN_CHAINS_CHAIN_CATALOGS_CATALOG)
    @Transactional
    public HttpEntity<Void> deleteCatalogById(
            @PathVariable("chainId") final String chainId,
            @PathVariable("catalogId") final String catalogId) throws ResourceNotFoundException {
        final StoreChain store = loadStoreChainById(chainId);
        final CatalogProduct catalog = loadCatalogByIdAndCheckStore(catalogId, store);
        catalogProductRepository.delete(catalog);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = URL_ADMIN_CHAINS_CHAIN_CATALOGS_UPLOAD)
    public HttpEntity<Void> uploadCatalogs(
            @PathVariable("chainId") final String chainId,
            @RequestParam("file") final MultipartFile file) throws ResourceNotFoundException {
        uploadCatalogFile(chainId, file);
        final URI location = linkTo(methodOn(AdminCatalogProductRestController.class).getCatalogs(chainId, null, null)).toUri();
        return ResponseEntity.created(location).body(null);
    }

    private CatalogProduct loadCatalogByIdAndCheckStore(final String catalogId, final StoreChain chain) {
        final CatalogProduct catalog = catalogProductRepository.findOne(catalogId);
        if (catalog == null) {
            log.warn("ILLEGAL STORE CATALOG ACCESS! No such store catalog product Id: {}.", catalogId);
            throw new ResourceNotFoundException("No store catalog product with the id: " + catalogId);
        }
        if (!chain.equals(catalog.getChain())) {
            log.warn("ILLEGAL STORE CATALOG ACCESS! Caalog product '{}' not belong to store chain '{}'.", catalogId, chain.getId());
            throw new ResourceNotFoundException("No store catalog product with the id: " + catalogId);
        }
        return catalog;
    }

    private ProductCategory validateProductCategory(final String productCatgoryCode) {
        final ProductCategory productCategory = ProductCategory.findByCode(productCatgoryCode);
        if (productCategory == null) {
            throw new InvalidInputException("Product Category cannot be found for "+productCatgoryCode);
        }
        return productCategory;
    }

    @Transactional
    private CatalogProduct newCatalogProduct(final AdminProductDataForm form, final String chainId) {
        final ProductCategory productCategory = validateProductCategory(form.getProductCategory());
        final StoreChain storeChain = loadStoreChainById(chainId);
        final AdminAccount currentAdmin = getCurrentAuthenticatedAdmin();
        final CatalogProduct catalog = storeService.createCatalogProduct(storeChain,
                                                                         form.getName(),
                                                                         form.getNumber(),
                                                                         form.getPrice(),
                                                                         form.getNaturalName(),
                                                                         form.getLabelCodes(),
                                                                         productCategory);
        log.debug("Admin {} created a new store catalog product {} for chain {}",
                currentAdmin.getUsername(), form.getName(), storeChain.getName());
        return catalog;
    }

    @Transactional
    private void updateCatalogProduct(final String chainId, final String catalogId, final AdminProductDataForm form) {
        final ProductCategory productCategory = validateProductCategory(form.getProductCategory());
        final StoreChain storeChain = loadStoreChainById(chainId);
        final CatalogProduct catalog = loadCatalogByIdAndCheckStore(catalogId, storeChain);
        final AdminAccount currentAdmin = getCurrentAuthenticatedAdmin();
        storeService.updateCatalogProduct(catalog,
                                          form.getPrice(),
                                          form.getNaturalName(),
                                          form.getLabelCodes(),
                                          productCategory);
        log.debug("Admin {} update existing store catalog product {} for chain {}",
                currentAdmin.getUsername(), form.getName(), storeChain.getName());
    }

    @Transactional
    private void uploadCatalogFile(final String chainId, final MultipartFile file) {
        final StoreChain chain = loadStoreChainById(chainId);
        log.info("Loading catalog for store [{}] from file '{}'...", chain.getCode(), file.getOriginalFilename());

        byte[] content = null;
        try {
            content = file.getBytes();
        } catch (IOException ex) {
            log.warn("No content of catalog data to load!");
            throw new InvalidInputException("No catalog content from file " + file.getOriginalFilename());
        }

        ProductData[] productDatas = null;

        // parse json catalog data
        final ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try (final InputStream is = new ByteArrayResource(content).getInputStream()){
            productDatas = mapper.readValue(is, ProductData[].class);
        } catch (Exception ex) {
            log.warn("Parse catalog file error!", ex);
            throw new InvalidInputException("Cannot load catalog json file " + file.getOriginalFilename()
                    + ", parsing json error " + ex.getMessage());
        }

        // validate product category
        final Set<String> unknownCategories = new HashSet<>();
        for (ProductData productData : productDatas) {
            if (StringUtils.isEmpty(productData.getProductCategory())) {
                productData.setProductCategory("uncategorized");
            } else if (ProductCategory.findByCode(productData.getProductCategory().toLowerCase()) == null) {
                log.warn("Upload catalog file with invalid product category '{}'", productData.getProductCategory());
                unknownCategories.add(productData.getProductCategory());
            } else {
                productData.setProductCategory(productData.getProductCategory().toLowerCase());
            }
        }
        if (!unknownCategories.isEmpty()) {
            throw new InvalidInputException("Catalog json file " + file.getOriginalFilename()
                    + " has unknown product category codes: " + unknownCategories.toString());
        }

        storeService.batchUpdateCatalog(chain, productDatas);
    }

}
