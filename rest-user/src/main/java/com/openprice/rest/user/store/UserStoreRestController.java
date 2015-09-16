package com.openprice.rest.user.store;

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
import org.springframework.web.bind.annotation.RestController;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.account.user.UserAccountService;
import com.openprice.domain.shopping.ShoppingItemRepository;
import com.openprice.domain.store.Store;
import com.openprice.domain.store.StoreRepository;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.user.AbstractUserRestController;
import com.openprice.rest.user.UserApiUrls;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserStoreRestController extends AbstractUserRestController {
    private final StoreRepository storeRepository;
    private final UserStoreResourceAssembler userStoreResourceAssembler;
    private final ShoppingItemRepository shoppingItemRepository;
    private final ShoppingItemResourceAssembler shoppingItemResourceAssembler;

    @Inject
    public UserStoreRestController(final UserAccountService userAccountService,
                                   final ShoppingItemRepository shoppingItemRepository,
                                   final ShoppingItemResourceAssembler shoppingItemResourceAssembler,
                                   final StoreRepository storeRepository,
                                   final UserStoreResourceAssembler userStoreResourceAssembler) {
        super(userAccountService);
        this.shoppingItemRepository = shoppingItemRepository;
        this.shoppingItemResourceAssembler = shoppingItemResourceAssembler;
        this.storeRepository = storeRepository;
        this.userStoreResourceAssembler = userStoreResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_SHOPPING_STORES)
    public HttpEntity<PagedResources<UserStoreResource>> getCurrentUserStores(
            @PageableDefault(size = 10, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<Store> assembler) {
        final UserAccount currentUser = getCurrentAuthenticatedUser();

        // FIXME get stores that current user has shopping list with
        // temp solution to get all sores

        final Page<Store> stores = storeRepository.findAll(pageable);
        return ResponseEntity.ok(assembler.toResource(stores, userStoreResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_SHOPPING_STORES_STORE)
    public HttpEntity<UserStoreResource> getUserStoreById(@PathVariable("storeId") final String storeId)
            throws ResourceNotFoundException {
        final Store store = storeRepository.findOne(storeId);
        if (store == null) {
            log.warn("ILLEGAL STORE ACCESS! No such store Id: {}.", storeId);
            throw new ResourceNotFoundException("No store with the id: " + storeId);
        }
        return ResponseEntity.ok(userStoreResourceAssembler.toResource(store));
    }

}
