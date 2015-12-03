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
import com.openprice.domain.store.StoreChain;
import com.openprice.domain.store.StoreChainRepository;
import com.openprice.domain.store.StoreService;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.admin.AbstractStoreAdminRestController;

import lombok.extern.slf4j.Slf4j;

/**
 * REST API Controller for store chain management.
 *
 */
@RestController
@Slf4j
public class AdminStoreChainRestController extends AbstractStoreAdminRestController {

    private final AdminStoreChainResource.Assembler storeChainResourceAssembler;

    @Inject
    public AdminStoreChainRestController(final AdminAccountService adminAccountService,
                                         final StoreService storeService,
                                         final StoreChainRepository storeChainRepository,
                                         final AdminStoreChainResource.Assembler storeResourceAssembler) {
        super(adminAccountService, storeService, storeChainRepository);
        this.storeChainResourceAssembler = storeResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_ADMIN_CHAINS)
    public HttpEntity<PagedResources<AdminStoreChainResource>> getAllStoreChains(
            @PageableDefault(size = UtilConstants.DEFAULT_RETURN_RECORD_COUNT, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<StoreChain> assembler) {
        final Page<StoreChain> stores = storeChainRepository.findAll(
                new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Direction.ASC, "code"));
        return ResponseEntity.ok(assembler.toResource(stores, storeChainResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.POST, value = URL_ADMIN_CHAINS)
    public HttpEntity<Void> createStoreChain(@RequestBody final AdminStoreChainForm form) {
        // TODO verify user input

        final StoreChain chain = newStoreChain(form);
        final URI location = linkTo(methodOn(AdminStoreChainRestController.class).getStoreChainById(chain.getId())).toUri();
        return ResponseEntity.created(location).body(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_ADMIN_CHAINS_CHAIN)
    public HttpEntity<AdminStoreChainResource> getStoreChainById(@PathVariable("chainId") final String chainId)
            throws ResourceNotFoundException {
        final StoreChain chain = loadStoreChainById(chainId);
        return ResponseEntity.ok(storeChainResourceAssembler.toResource(chain));
    }

    @RequestMapping(method = RequestMethod.PUT, value = URL_ADMIN_CHAINS_CHAIN)
    public HttpEntity<Void> updateStoreChain(
            @PathVariable("chainId") final String chainId,
            @RequestBody final AdminStoreChainForm form) throws ResourceNotFoundException {
        // TODO verify user input?

        final StoreChain store = loadStoreChainById(chainId);
        storeChainRepository.save(form.updateStore(store));
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = URL_ADMIN_CHAINS_CHAIN)
    public HttpEntity<Void> deleteStoreChainById(@PathVariable("chainId") final String chainId)
            throws ResourceNotFoundException {
        final StoreChain chain = loadStoreChainById(chainId);
        storeChainRepository.delete(chain);
        return ResponseEntity.noContent().build();
    }


    @Transactional
    protected StoreChain newStoreChain(final AdminStoreChainForm form) {
        final AdminAccount currentAdmin = getCurrentAuthenticatedAdmin();
        log.debug("Admin {} created a new store chain {}", currentAdmin.getUsername(), form.getName());
        return storeService.createStoreChain(form.getCode(), form.getName());
    }

}
