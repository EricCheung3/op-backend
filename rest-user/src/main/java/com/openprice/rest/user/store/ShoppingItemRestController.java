package com.openprice.rest.user.store;

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

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.account.user.UserAccountService;
import com.openprice.domain.shopping.ShoppingItem;
import com.openprice.domain.shopping.ShoppingItemRepository;
import com.openprice.domain.shopping.ShoppingService;
import com.openprice.domain.shopping.ShoppingStore;
import com.openprice.domain.shopping.ShoppingStoreRepository;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.user.UserApiUrls;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ShoppingItemRestController extends AbstractUserStoreRestController {
    private final ShoppingItemRepository shoppingItemRepository;
    private final ShoppingItemResourceAssembler shoppingItemResourceAssembler;

    @Inject
    public ShoppingItemRestController(final UserAccountService userAccountService,
                                      final ShoppingService shoppingService,
                                      final ShoppingStoreRepository shoppingStoreRepository,
                                      final ShoppingItemRepository shoppingItemRepository,
                                      final ShoppingItemResourceAssembler shoppingItemResourceAssembler) {
        super(userAccountService, shoppingService, shoppingStoreRepository);
        this.shoppingItemRepository = shoppingItemRepository;
        this.shoppingItemResourceAssembler = shoppingItemResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_SHOPPINGLIST)
    public HttpEntity<Void> getUploadShoppingListPath() {
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = UserApiUrls.URL_USER_SHOPPINGLIST)
    public HttpEntity<Void> uploadShoppingList(@RequestBody final ShoppingListForm form) {
        final ShoppingStore store = saveShoppingList(form);
        final URI location = linkTo(methodOn(ShoppingStoreRestController.class).getUserShoppingStoreById(store.getId())).toUri();
        return ResponseEntity.created(location).body(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_SHOPPING_STORES_STORE_ITEMS)
    public HttpEntity<PagedResources<ShoppingItemResource>> getShoppingItems(
            @PathVariable("storeId") final String storeId,
            @PageableDefault(size = 10, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<ShoppingItem> assembler) {
        final ShoppingStore store = getShoppingStoreByIdAndCheckUser(storeId);
        final Page<ShoppingItem> items = shoppingItemRepository.findByStoreOrderByName(store, pageable);
        return ResponseEntity.ok(assembler.toResource(items, shoppingItemResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_SHOPPING_STORES_STORE_ITEMS_ITEM)
    public HttpEntity<ShoppingItemResource> getStoreShoppingItemById(
            @PathVariable("storeId") final String storeId,
            @PathVariable("itemId") final String itemId) throws ResourceNotFoundException {
        final ShoppingItem item = getShoppingItemByIdAndCheckStore(storeId, itemId);
        return ResponseEntity.ok(shoppingItemResourceAssembler.toResource(item));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = UserApiUrls.URL_USER_SHOPPING_STORES_STORE_ITEMS_ITEM)
    public HttpEntity<Void> deleteStoreShoppingItemById(
            @PathVariable("storeId") final String storeId,
            @PathVariable("itemId") final String itemId) throws ResourceNotFoundException {
        final ShoppingItem item = getShoppingItemByIdAndCheckStore(storeId, itemId);
        shoppingItemRepository.delete(item);
        return ResponseEntity.noContent().build();
    }

    private ShoppingItem getShoppingItemByIdAndCheckStore(final String storeId, final String itemId) {
        final ShoppingStore store = getShoppingStoreByIdAndCheckUser(storeId);

        final ShoppingItem item = shoppingItemRepository.findOne(itemId);
        if (item == null) {
            log.warn("ILLEGAL SHOPPING ITEM ACCESS! No such item Id: {}.", itemId);
            throw new ResourceNotFoundException("No shopping item with the id: " + itemId);
        }
        if (!store.equals(item.getStore())) {
            log.warn("ILLEGAL SHOPPING ITEM ACCESS! Item '{}' not belong to Store '{}'.", itemId, storeId);
            throw new ResourceNotFoundException("No item with the id: " + itemId); // we treat it as 404
        }
        return item;
    }

    @Transactional
    private ShoppingStore saveShoppingList(final ShoppingListForm form) {
        final UserAccount currentUser = getCurrentAuthenticatedUser();
        final String chainCode = form.getChainCode();

        //TODO get generic shopping list for store if chain code not provided
        final ShoppingStore store = shoppingService.getShoppingStoreForStoreChain(currentUser, chainCode);
        form.addShoppingItems(store);

        return shoppingStoreRepository.save(store);
    }
}
