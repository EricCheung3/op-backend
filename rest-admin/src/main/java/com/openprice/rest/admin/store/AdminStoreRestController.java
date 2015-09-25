package com.openprice.rest.admin.store;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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
import org.springframework.web.bind.annotation.RestController;

import com.openprice.domain.account.admin.AdminAccount;
import com.openprice.domain.account.admin.AdminAccountService;
import com.openprice.domain.store.Store;
import com.openprice.domain.store.StoreBranch;
import com.openprice.domain.store.StoreBranchRepository;
import com.openprice.domain.store.StoreRepository;
import com.openprice.domain.store.StoreService;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.admin.AbstractAdminRestController;
import com.openprice.rest.admin.AdminApiUrls;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AdminStoreRestController extends AbstractAdminRestController {
    private final StoreRepository storeRepository;
    private final StoreBranchRepository storeBranchRepository;
    private final StoreService storeService;
    private final AdminStoreResourceAssembler storeResourceAssembler;
    private final AdminStoreBranchResourceAssembler storeBranchResourceAssembler;

    @Inject
    public AdminStoreRestController(final AdminAccountService adminAccountService,
                                    final StoreRepository storeRepository,
                                    final StoreBranchRepository storeBranchRepository,
                                    final StoreService storeService,
                                    final AdminStoreResourceAssembler storeResourceAssembler,
                                    final AdminStoreBranchResourceAssembler storeBranchResourceAssembler) {
        super(adminAccountService);
        this.storeRepository = storeRepository;
        this.storeBranchRepository = storeBranchRepository;
        this.storeService = storeService;
        this.storeResourceAssembler = storeResourceAssembler;
        this.storeBranchResourceAssembler = storeBranchResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET, value = AdminApiUrls.URL_ADMIN_STORES)
    public HttpEntity<PagedResources<AdminStoreResource>> getAllStores(
            @PageableDefault(size = UtilConstants.DEFAULT_RETURN_RECORD_COUNT, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<Store> assembler) {
        final Page<Store> stores = storeRepository.findAll(new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Direction.ASC, "code"));
        return ResponseEntity.ok(assembler.toResource(stores, storeResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.POST, value = AdminApiUrls.URL_ADMIN_STORES)
    public HttpEntity<Void> createStore(@RequestBody final AdminStoreForm form) {
        // TODO verify user input

        Store store = newStore(form);
        URI location = linkTo(methodOn(AdminStoreRestController.class).getStoreById(store.getId())).toUri();
        return ResponseEntity.created(location).body(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = AdminApiUrls.URL_ADMIN_STORES_STORE)
    public HttpEntity<AdminStoreResource> getStoreById(@PathVariable("storeId") final String storeId)
            throws ResourceNotFoundException {
        final Store store = loadStoreById(storeId);
        return ResponseEntity.ok(storeResourceAssembler.toResource(store));
    }

    @RequestMapping(method = RequestMethod.PUT, value = AdminApiUrls.URL_ADMIN_STORES_STORE)
    public HttpEntity<Void> updateStore(@PathVariable("storeId") final String storeId,
                                        @RequestBody final AdminStoreForm form)
            throws ResourceNotFoundException {
        // TODO verify user input?

        final Store store = loadStoreById(storeId);
        storeRepository.save(form.updateStore(store));
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = AdminApiUrls.URL_ADMIN_STORES_STORE)
    public HttpEntity<Void> deleteStoreById(@PathVariable("storeId") final String storeId)
            throws ResourceNotFoundException {
        final Store store = loadStoreById(storeId);
        storeRepository.delete(store);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = AdminApiUrls.URL_ADMIN_STORES_STORE_BRANCHES)
    public HttpEntity<PagedResources<AdminStoreBranchResource>> getStoreBranches(
            @PathVariable("storeId") final String storeId,
            @PageableDefault(size = UtilConstants.DEFAULT_RETURN_RECORD_COUNT, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<StoreBranch> assembler) throws ResourceNotFoundException {
        final Store store = loadStoreById(storeId);
        final Page<StoreBranch> branches = storeBranchRepository.findByStore(store, pageable);
        return ResponseEntity.ok(assembler.toResource(branches, storeBranchResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.POST, value = AdminApiUrls.URL_ADMIN_STORES_STORE_BRANCHES)
    public HttpEntity<Void> createStoreBranch(
            @PathVariable("storeId") final String storeId,
            @RequestBody final AdminStoreBranchForm form) throws ResourceNotFoundException {
        // TODO verify user input?

        final Store store = loadStoreById(storeId);
        final StoreBranch branch = newStoreBranch(form, store);
        final URI location = linkTo(methodOn(AdminStoreRestController.class).getStoreBranchById(storeId, branch.getId())).toUri();
        return ResponseEntity.created(location).body(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = AdminApiUrls.URL_ADMIN_STORES_STORE_BRANCHES_BRANCH)
    public HttpEntity<AdminStoreBranchResource> getStoreBranchById(
            @PathVariable("storeId") final String storeId,
            @PathVariable("branchId") final String branchId) throws ResourceNotFoundException {
        final Store store = loadStoreById(storeId);
        final StoreBranch branch = loadStoreBranchByIdAndCheckStore(branchId, store);
        return ResponseEntity.ok(storeBranchResourceAssembler.toResource(branch));
    }

    @RequestMapping(method = RequestMethod.PUT, value = AdminApiUrls.URL_ADMIN_STORES_STORE_BRANCHES_BRANCH)
    public HttpEntity<Void> updateStoreBranch(
            @PathVariable("storeId") final String storeId,
            @PathVariable("branchId") final String branchId,
            @RequestBody final AdminStoreBranchForm form) throws ResourceNotFoundException {
        // TODO verify user input?

        final Store store = loadStoreById(storeId);
        final StoreBranch branch = loadStoreBranchByIdAndCheckStore(branchId, store);
        storeBranchRepository.save(form.updateStoreBranch(branch));
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = AdminApiUrls.URL_ADMIN_STORES_STORE_BRANCHES_BRANCH)
    public HttpEntity<Void> deleteStoreBranchById(
            @PathVariable("storeId") final String storeId,
            @PathVariable("branchId") final String branchId) throws ResourceNotFoundException {
        final Store store = loadStoreById(storeId);
        final StoreBranch branch = loadStoreBranchByIdAndCheckStore(branchId, store);
        storeBranchRepository.delete(branch);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    protected Store newStore(final AdminStoreForm form) {
        final AdminAccount currentAdmin = getCurrentAuthenticatedAdmin();
        log.debug("Admin {} created a new store {}", currentAdmin.getUsername(), form.getName());
        return storeService.createStore(form.getCode(),
                                        form.getName(),
                                        form.getCategories(),
                                        form.getIdentifyFields());
    }

    @Transactional
    protected StoreBranch newStoreBranch(final AdminStoreBranchForm form, final Store store) {
        final AdminAccount currentAdmin = getCurrentAuthenticatedAdmin();
        log.debug("Admin {} created a new branch {}", currentAdmin.getUsername(), form.getName());
        return storeService.createStoreBranch(store,
                                              form.getName(),
                                              form.getPhone(),
                                              form.getGstNumber(),
                                              form.getAddress1(),
                                              form.getAddress2(),
                                              form.getCity(),
                                              form.getState(),
                                              form.getZip(),
                                              form.getCountry());
    }

    private Store loadStoreById(final String storeId) {
        final Store store = storeRepository.findOne(storeId);
        if (store == null) {
            log.warn("ILLEGAL STORE ACCESS! No such store Id: {}.", storeId);
            throw new ResourceNotFoundException("No store with the id: " + storeId);
        }
        return store;
    }

    private StoreBranch loadStoreBranchByIdAndCheckStore(final String branchId, final Store store) {
        final StoreBranch branch = storeBranchRepository.findOne(branchId);
        if (branch == null) {
            log.warn("ILLEGAL STORE BRANCH ACCESS! No such store branch Id: {}.", branchId);
            throw new ResourceNotFoundException("No store branch with the id: " + branchId);
        }
        if (!store.equals(branch.getStore())) {
            log.warn("ILLEGAL STORE BRANCH ACCESS! Branch '{}' not belong to Store '{}'.", branchId, store.getId());
            throw new ResourceNotFoundException("No store branch with the id: " + branchId);
        }
        return branch;
    }

}
