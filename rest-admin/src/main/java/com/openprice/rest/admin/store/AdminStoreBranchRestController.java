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
import org.springframework.web.bind.annotation.RestController;

import com.openprice.domain.account.admin.AdminAccount;
import com.openprice.domain.account.admin.AdminAccountService;
import com.openprice.domain.store.StoreBranch;
import com.openprice.domain.store.StoreBranchRepository;
import com.openprice.domain.store.StoreChain;
import com.openprice.domain.store.StoreChainRepository;
import com.openprice.domain.store.StoreService;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.admin.AbstractStoreAdminRestController;

import lombok.extern.slf4j.Slf4j;

/**
 * REST API Controller for store branch management.
 *
 */
@RestController
@Slf4j
public class AdminStoreBranchRestController extends AbstractStoreAdminRestController {

    private final StoreBranchRepository storeBranchRepository;
    private final AdminStoreBranchResource.Assembler storeBranchResourceAssembler;

    @Inject
    public AdminStoreBranchRestController(final AdminAccountService adminAccountService,
                                          final StoreService storeService,
                                          final StoreChainRepository storeChainRepository,
                                          final StoreBranchRepository storeBranchRepository,
                                          final AdminStoreBranchResource.Assembler storeBranchResourceAssembler) {
        super(adminAccountService, storeService, storeChainRepository);
        this.storeBranchRepository = storeBranchRepository;
        this.storeBranchResourceAssembler = storeBranchResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_ADMIN_CHAINS_CHAIN_BRANCHES)
    public HttpEntity<PagedResources<AdminStoreBranchResource>> getStoreBranches(
            @PathVariable("chainId") final String chainId,
            @PageableDefault(size = UtilConstants.DEFAULT_RETURN_RECORD_COUNT, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<StoreBranch> assembler) throws ResourceNotFoundException {
        final StoreChain chain = loadStoreChainById(chainId);
        final Page<StoreBranch> branches = storeBranchRepository.findByChain(chain, pageable);
        return ResponseEntity.ok(assembler.toResource(branches, storeBranchResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.POST, value = URL_ADMIN_CHAINS_CHAIN_BRANCHES)
    public HttpEntity<Void> createStoreBranch(
            @PathVariable("chainId") final String chainId,
            @RequestBody final AdminStoreBranchForm form) throws ResourceNotFoundException {
        // TODO verify user input?

        final StoreChain store = loadStoreChainById(chainId);
        final StoreBranch branch = newStoreBranch(form, store);
        final URI location = linkTo(methodOn(AdminStoreBranchRestController.class).getStoreBranchById(chainId, branch.getId())).toUri();
        return ResponseEntity.created(location).body(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_ADMIN_CHAINS_CHAIN_BRANCHES_BRANCH)
    public HttpEntity<AdminStoreBranchResource> getStoreBranchById(
            @PathVariable("chainId") final String chainId,
            @PathVariable("branchId") final String branchId) throws ResourceNotFoundException {
        final StoreChain store = loadStoreChainById(chainId);
        final StoreBranch branch = loadStoreBranchByIdAndCheckStore(branchId, store);
        return ResponseEntity.ok(storeBranchResourceAssembler.toResource(branch));
    }

    @RequestMapping(method = RequestMethod.PUT, value = URL_ADMIN_CHAINS_CHAIN_BRANCHES_BRANCH)
    public HttpEntity<Void> updateStoreBranch(
            @PathVariable("chainId") final String chainId,
            @PathVariable("branchId") final String branchId,
            @RequestBody final AdminStoreBranchForm form) throws ResourceNotFoundException {
        // TODO verify user input?

        final StoreChain store = loadStoreChainById(chainId);
        final StoreBranch branch = loadStoreBranchByIdAndCheckStore(branchId, store);
        storeBranchRepository.save(form.updateStoreBranch(branch));
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = URL_ADMIN_CHAINS_CHAIN_BRANCHES_BRANCH)
    public HttpEntity<Void> deleteStoreBranchById(
            @PathVariable("chainId") final String chainId,
            @PathVariable("branchId") final String branchId) throws ResourceNotFoundException {
        final StoreChain store = loadStoreChainById(chainId);
        final StoreBranch branch = loadStoreBranchByIdAndCheckStore(branchId, store);
        storeBranchRepository.delete(branch);
        return ResponseEntity.noContent().build();
    }


    private StoreBranch loadStoreBranchByIdAndCheckStore(final String branchId, final StoreChain chain) {
        final StoreBranch branch = storeBranchRepository.findOne(branchId);
        if (branch == null) {
            log.warn("ILLEGAL STORE BRANCH ACCESS! No such store branch Id: {}.", branchId);
            throw new ResourceNotFoundException("No store branch with the id: " + branchId);
        }
        if (!chain.equals(branch.getChain())) {
            log.warn("ILLEGAL STORE BRANCH ACCESS! Branch '{}' not belong to store chain '{}'.", branchId, chain.getId());
            throw new ResourceNotFoundException("No store branch with the id: " + branchId);
        }
        return branch;
    }

    @Transactional
    protected StoreBranch newStoreBranch(final AdminStoreBranchForm form, final StoreChain chain) {
        final AdminAccount currentAdmin = getCurrentAuthenticatedAdmin();
        log.debug("Admin {} created a new store branch {} for chain {}", currentAdmin.getUsername(), form.getName(), chain.getName());
        return storeService.createStoreBranch(chain,
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

}
