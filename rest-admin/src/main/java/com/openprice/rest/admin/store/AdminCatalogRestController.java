package com.openprice.rest.admin.store;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.openprice.domain.account.admin.AdminAccount;
import com.openprice.domain.account.admin.AdminAccountService;
import com.openprice.domain.store.Catalog;
import com.openprice.domain.store.CatalogRepository;
import com.openprice.domain.store.StoreChain;
import com.openprice.domain.store.StoreChainRepository;
import com.openprice.domain.store.StoreService;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.admin.AbstractStoreAdminRestController;
import com.openprice.rest.admin.AdminApiUrls;

import lombok.extern.slf4j.Slf4j;

/**
 * REST API Controller for store catalog management.
 *
 */
@RestController
@Slf4j
public class AdminCatalogRestController extends AbstractStoreAdminRestController {

    private final CatalogRepository catalogRepository;
    private final AdminCatalogResourceAssembler catalogResourceAssembler;

    @Inject
    public AdminCatalogRestController(final AdminAccountService adminAccountService,
                                      final StoreService storeService,
                                      final StoreChainRepository storeChainRepository,
                                      final CatalogRepository catalogRepository,
                                      final AdminCatalogResourceAssembler catalogResourceAssembler) {
        super(adminAccountService, storeService, storeChainRepository);
        this.catalogRepository = catalogRepository;
        this.catalogResourceAssembler = catalogResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET, value = AdminApiUrls.URL_ADMIN_CHAINS_CHAIN_CATALOGS)
    public HttpEntity<PagedResources<AdminCatalogResource>> getCatalogs(
            @PathVariable("chainId") final String chainId,
            @PageableDefault(size = UtilConstants.DEFAULT_RETURN_RECORD_COUNT, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<Catalog> assembler) throws ResourceNotFoundException {
        final StoreChain chain = loadStoreChainById(chainId);
        final Page<Catalog> catalogs = catalogRepository.findByChain(chain, pageable);
        return ResponseEntity.ok(assembler.toResource(catalogs, catalogResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.POST, value = AdminApiUrls.URL_ADMIN_CHAINS_CHAIN_CATALOGS)
    public HttpEntity<Void> createStoreCatalog(
            @PathVariable("chainId") final String chainId,
            @RequestBody final AdminCatalogForm form) throws ResourceNotFoundException {
        // TODO verify user input?

        final StoreChain store = loadStoreChainById(chainId);
        final Catalog catalog = newCatalog(form, store);
        final URI location = linkTo(methodOn(AdminCatalogRestController.class).getCatalogById(chainId, catalog.getId())).toUri();
        return ResponseEntity.created(location).body(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = AdminApiUrls.URL_ADMIN_CHAINS_CHAIN_CATALOGS_CATALOG)
    public HttpEntity<AdminCatalogResource> getCatalogById(
            @PathVariable("chainId") final String chainId,
            @PathVariable("catalogId") final String catalogId) throws ResourceNotFoundException {
        final StoreChain store = loadStoreChainById(chainId);
        final Catalog catalog = loadCatalogByIdAndCheckStore(catalogId, store);
        return ResponseEntity.ok(catalogResourceAssembler.toResource(catalog));
    }

    @RequestMapping(method = RequestMethod.PUT, value = AdminApiUrls.URL_ADMIN_CHAINS_CHAIN_CATALOGS_CATALOG)
    public HttpEntity<Void> updateCatalog(
            @PathVariable("chainId") final String chainId,
            @PathVariable("catalogId") final String catalogId,
            @RequestBody final AdminCatalogForm form) throws ResourceNotFoundException {
        // TODO verify user input?

        final StoreChain store = loadStoreChainById(chainId);
        final Catalog catalog = loadCatalogByIdAndCheckStore(catalogId, store);
        storeService.updateCatalog(catalog,
                                   form.getName(),
                                   form.getNumber(),
                                   form.getPrice(),
                                   form.getNaturalName(),
                                   form.getLabelCodes());
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = AdminApiUrls.URL_ADMIN_CHAINS_CHAIN_CATALOGS_CATALOG)
    public HttpEntity<Void> deleteCatalogById(
            @PathVariable("chainId") final String chainId,
            @PathVariable("catalogId") final String catalogId) throws ResourceNotFoundException {
        final StoreChain store = loadStoreChainById(chainId);
        final Catalog catalog = loadCatalogByIdAndCheckStore(catalogId, store);
        catalogRepository.delete(catalog);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = AdminApiUrls.URL_ADMIN_CHAINS_CHAIN_CATALOGS_UPLOAD)
    public HttpEntity<Void> uploadCatalogs(
            @PathVariable("chainId") final String chainId,
            @RequestParam("file") final MultipartFile file) throws ResourceNotFoundException {
        final StoreChain chain = loadStoreChainById(chainId);
        if (!file.isEmpty()) {
            storeService.loadCatalog(chain, file);
            final URI location = linkTo(methodOn(AdminCatalogRestController.class).getCatalogs(chainId, null, null)).toUri();
            return ResponseEntity.created(location).body(null);
        }
        else {
            log.error("No file uploaded!");
            return ResponseEntity.badRequest().build();
        }
    }

    private Catalog loadCatalogByIdAndCheckStore(final String catalogId, final StoreChain chain) {
        final Catalog catalog = catalogRepository.findOne(catalogId);
        if (catalog == null) {
            log.warn("ILLEGAL STORE CATALOG ACCESS! No such store catalog Id: {}.", catalogId);
            throw new ResourceNotFoundException("No store catalog with the id: " + catalogId);
        }
        if (!chain.equals(catalog.getChain())) {
            log.warn("ILLEGAL STORE CATALOG ACCESS! Caalog '{}' not belong to store chain '{}'.", catalogId, chain.getId());
            throw new ResourceNotFoundException("No store catalog with the id: " + catalogId);
        }
        return catalog;
    }

    @Transactional
    protected Catalog newCatalog(final AdminCatalogForm form, final StoreChain chain) {
        final AdminAccount currentAdmin = getCurrentAuthenticatedAdmin();
        log.debug("Admin {} created a new store catalog {} for chain {}", currentAdmin.getUsername(), form.getName(), chain.getName());
        return storeService.createCatalog(chain,
                                          form.getName(),
                                          form.getNumber(),
                                          form.getPrice(),
                                          form.getNaturalName(),
                                          form.getLabelCodes());
    }

}
