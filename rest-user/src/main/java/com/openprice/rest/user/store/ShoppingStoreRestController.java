package com.openprice.rest.user.store;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.account.user.UserAccountService;
import com.openprice.domain.shopping.ShoppingService;
import com.openprice.domain.shopping.ShoppingStore;
import com.openprice.domain.shopping.ShoppingStoreRepository;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.store.CatalogProduct;
import com.openprice.store.StoreMetadata;

import lombok.extern.slf4j.Slf4j;

/**
 * REST API Controller for current user shopping list store management.
 *
 */
@RestController
@Slf4j
public class ShoppingStoreRestController extends AbstractUserStoreRestController {

    private final StoreMetadata storeMetadata;
    private final ShoppingStoreResource.Assembler shoppingStoreResourceAssembler;

    @Inject
    public ShoppingStoreRestController(final UserAccountService userAccountService,
                                       final ShoppingService shoppingService,
                                       final StoreMetadata storeMetadata,
                                       final ShoppingStoreRepository shoppingStoreRepository,
                                       final ShoppingStoreResource.Assembler shoppingStoreResourceAssembler) {
        super(userAccountService, shoppingService, shoppingStoreRepository);
        this.storeMetadata = storeMetadata;
        this.shoppingStoreResourceAssembler = shoppingStoreResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_USER_SHOPPING_STORES)
    public HttpEntity<PagedResources<ShoppingStoreResource>> getCurrentUserShoppingStores(
            @PageableDefault(size = 10, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<ShoppingStore> assembler) {
        final UserAccount currentUser = getCurrentAuthenticatedUser();
        final Page<ShoppingStore> stores = shoppingStoreRepository.findByUserOrderByDisplayName(currentUser, pageable);
        return ResponseEntity.ok(assembler.toResource(stores, shoppingStoreResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_USER_SHOPPING_STORES_STORE)
    public HttpEntity<ShoppingStoreResource> getUserShoppingStoreById(
            @PathVariable("storeId") final String storeId) throws ResourceNotFoundException {
        final ShoppingStore store = getShoppingStoreByIdAndCheckUser(storeId);
        return ResponseEntity.ok(shoppingStoreResourceAssembler.toResource(store));
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_USER_SHOPPING_STORES_STORE_CATALOGS)
    public HttpEntity<List<CatalogProduct>> searchCatalogsForStoreChain(
            @PathVariable("storeId") final String storeId,
            @RequestParam("query") String query) throws ResourceNotFoundException {
        final ShoppingStore store = getShoppingStoreByIdAndCheckUser(storeId);
        return ResponseEntity.ok(storeMetadata.findMatchingProducts(query, store.getChainCode(), 20));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = URL_USER_SHOPPING_STORES_STORE)
    public HttpEntity<Void> deleteUserShoppingStoreById(
            @PathVariable("storeId") final String storeId) throws ResourceNotFoundException {
        final ShoppingStore store = getShoppingStoreByIdAndCheckUser(storeId);
        log.info("User {} delete shopping store '{}'.", store.getUser().getUsername(), store.getChainCode());
        shoppingStoreRepository.delete(store);
        return ResponseEntity.noContent().build();
    }


}
