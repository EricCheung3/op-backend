package com.openprice.rest.user.store;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
import com.openprice.domain.store.Catalog;
import com.openprice.domain.store.CatalogRepository;
import com.openprice.domain.store.StoreChain;
import com.openprice.domain.store.StoreChainRepository;
import com.openprice.rest.ResourceNotFoundException;

/**
 * REST API Controller for current user shopping list store management.
 *
 */
@RestController
public class ShoppingStoreRestController extends AbstractUserStoreRestController {

    private final StoreChainRepository storeChainRepository;
    private final CatalogRepository catalogRepository;
    private final ShoppingStoreResource.Assembler shoppingStoreResourceAssembler;

    @Inject
    public ShoppingStoreRestController(final UserAccountService userAccountService,
                                       final ShoppingService shoppingService,
                                       final ShoppingStoreRepository shoppingStoreRepository,
                                       final StoreChainRepository storeChainRepository,
                                       final CatalogRepository catalogRepository,
                                       final ShoppingStoreResource.Assembler shoppingStoreResourceAssembler) {
        super(userAccountService, shoppingService, shoppingStoreRepository);
        this.storeChainRepository = storeChainRepository;
        this.catalogRepository = catalogRepository;
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
    public HttpEntity<List<Catalog>> searchCatalogsForStoreChain(
            @PathVariable("storeId") final String storeId,
            @RequestParam("query") String query) throws ResourceNotFoundException {
        final ShoppingStore store = getShoppingStoreByIdAndCheckUser(storeId);
        final StoreChain chain = storeChainRepository.findByCode(store.getChainCode());
        if (chain != null && !StringUtils.isEmpty(query)){
            return ResponseEntity.ok(catalogRepository.searchCatalog(chain, query.toUpperCase()));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

}
