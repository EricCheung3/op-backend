package com.openprice.rest.user.store;

import org.springframework.security.access.AccessDeniedException;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.account.user.UserAccountService;
import com.openprice.domain.shopping.ShoppingService;
import com.openprice.domain.shopping.ShoppingStore;
import com.openprice.domain.shopping.ShoppingStoreRepository;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.user.AbstractUserRestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractUserStoreRestController extends AbstractUserRestController {

    protected final ShoppingStoreRepository shoppingStoreRepository;
    protected final ShoppingService shoppingService;

    public AbstractUserStoreRestController(final UserAccountService userAccountService,
                                           final ShoppingService shoppingService,
                                           final ShoppingStoreRepository shoppingStoreRepository) {
        super(userAccountService);
        this.shoppingService = shoppingService;
        this.shoppingStoreRepository = shoppingStoreRepository;
    }

    protected ShoppingStore getShoppingStoreByIdAndCheckUser(final String storeId)
            throws ResourceNotFoundException, AccessDeniedException {
        final UserAccount currentUser = getCurrentAuthenticatedUser();
        log.info("User {} works on shopping list {}...", currentUser.getUsername(), storeId);
        final ShoppingStore store = shoppingStoreRepository.findOne(storeId);
        if (store == null) {
            log.warn("ILLEGAL SHOPPING STORE ACCESS! No such ShoppingStore Id: {}.", storeId);
            throw new ResourceNotFoundException("No shopping store with the id: " + storeId);
        }
        if (!currentUser.equals(store.getUser())) {
            log.warn("ILLEGAL SHOPPING STORE ACCESS! ShoppingStore '{}' not belong to current user '{}'.", storeId, currentUser.getId());
            throw new AccessDeniedException("Cannot access the shopping store not belong to current user.");
        }
        return store;
    }

}
