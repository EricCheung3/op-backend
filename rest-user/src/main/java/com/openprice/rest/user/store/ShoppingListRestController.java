package com.openprice.rest.user.store;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;

import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.account.user.UserAccountService;
import com.openprice.domain.shopping.ShoppingService;
import com.openprice.domain.shopping.ShoppingStore;
import com.openprice.domain.shopping.ShoppingStoreRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * REST API Controller for current user uploading shopping list from receipt.
 *
 */
@RestController
@Slf4j
public class ShoppingListRestController extends AbstractUserStoreRestController {

    @Inject
    public ShoppingListRestController(final UserAccountService userAccountService,
                                      final ShoppingService shoppingService,
                                      final ShoppingStoreRepository shoppingStoreRepository) {
        super(userAccountService, shoppingService, shoppingStoreRepository);
    }

    @RequestMapping(method = RequestMethod.POST, value = URL_USER_SHOPPINGLIST)
    public HttpEntity<Void> uploadShoppingList(@RequestBody final ShoppingListForm form) {
        final ShoppingStore store = saveShoppingList(form);
        final URI location = linkTo(methodOn(ShoppingStoreRestController.class).getUserShoppingStoreById(store.getId())).toUri();
        return ResponseEntity.created(location).body(null);
    }

    @Transactional
    private ShoppingStore saveShoppingList(final ShoppingListForm form) {
        final UserAccount currentUser = getCurrentAuthenticatedUser();
        final String chainCode = form.getChainCode();
        log.info("User <{}> add items to shopping store '{}'.", currentUser.getUsername(), chainCode);

        final ShoppingStore store = shoppingService.getShoppingStoreForStoreChain(currentUser, chainCode);
        for (final CreateShoppingItemForm item : form.getItems()) {
            shoppingService.addShoppingItemToStore(store, item.getCatalogCode(), item.getName());
        }
        return shoppingStoreRepository.save(store);
    }

}
