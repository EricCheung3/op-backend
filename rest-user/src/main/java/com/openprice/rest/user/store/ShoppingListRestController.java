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
import com.openprice.domain.shopping.ShoppingItem;
import com.openprice.domain.shopping.ShoppingItemRepository;
import com.openprice.domain.shopping.ShoppingService;
import com.openprice.domain.shopping.ShoppingStore;
import com.openprice.domain.shopping.ShoppingStoreRepository;
import com.openprice.rest.user.UserApiUrls;

/**
 * REST API Controller for current user uploading shopping list from receipt.
 *
 */
@RestController
public class ShoppingListRestController extends AbstractUserStoreRestController {

    private final ShoppingItemRepository shoppingItemRepository;

    @Inject
    public ShoppingListRestController(final UserAccountService userAccountService,
                                      final ShoppingService shoppingService,
                                      final ShoppingStoreRepository shoppingStoreRepository,
                                      final ShoppingItemRepository shoppingItemRepository) {
        super(userAccountService, shoppingService, shoppingStoreRepository);
        this.shoppingItemRepository = shoppingItemRepository;
    }

    @RequestMapping(method = RequestMethod.POST, value = UserApiUrls.URL_USER_SHOPPINGLIST)
    public HttpEntity<Void> uploadShoppingList(@RequestBody final ShoppingListForm form) {
        final ShoppingStore store = saveShoppingList(form);
        final URI location = linkTo(methodOn(ShoppingStoreRestController.class).getUserShoppingStoreById(store.getId())).toUri();
        return ResponseEntity.created(location).body(null);
    }

    @Transactional
    private ShoppingStore saveShoppingList(final ShoppingListForm form) {
        final UserAccount currentUser = getCurrentAuthenticatedUser();
        final String chainCode = form.getChainCode();

        final ShoppingStore store = shoppingService.getShoppingStoreForStoreChain(currentUser, chainCode);
        for (final ShoppingItemForm item : form.getItems()) {
            final ShoppingItem shoppingItem = store.addItem(item.getCatalogCode(), item.getName());
            shoppingItemRepository.save(shoppingItem);
        }
        return shoppingStoreRepository.save(store);
    }

}
