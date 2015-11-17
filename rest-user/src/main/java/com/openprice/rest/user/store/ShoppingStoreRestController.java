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
import com.openprice.domain.shopping.ShoppingService;
import com.openprice.domain.shopping.ShoppingStore;
import com.openprice.domain.shopping.ShoppingStoreRepository;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.user.UserApiUrls;

@RestController
public class ShoppingStoreRestController extends AbstractUserStoreRestController {
    private final ShoppingStoreResourceAssembler shoppingStoreResourceAssembler;

    @Inject
    public ShoppingStoreRestController(final UserAccountService userAccountService,
                                       final ShoppingService shoppingService,
                                       final ShoppingStoreRepository shoppingStoreRepository,
                                       final ShoppingStoreResourceAssembler shoppingStoreResourceAssembler) {
        super(userAccountService, shoppingService, shoppingStoreRepository);
        this.shoppingStoreResourceAssembler = shoppingStoreResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_SHOPPING_STORES)
    public HttpEntity<PagedResources<ShoppingStoreResource>> getCurrentUserShoppingStores(
            @PageableDefault(size = 10, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<ShoppingStore> assembler) {
        final UserAccount currentUser = getCurrentAuthenticatedUser();
        final Page<ShoppingStore> stores = shoppingStoreRepository.findByUserOrderByDisplayName(currentUser, pageable);
        return ResponseEntity.ok(assembler.toResource(stores, shoppingStoreResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_SHOPPING_STORES_STORE)
    public HttpEntity<ShoppingStoreResource> getUserShoppingStoreById(@PathVariable("storeId") final String storeId)
            throws ResourceNotFoundException {
        final ShoppingStore store = getShoppingStoreByIdAndCheckUser(storeId);
        return ResponseEntity.ok(shoppingStoreResourceAssembler.toResource(store));
    }

}
