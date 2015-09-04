package com.openprice.rest.user.store;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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

import com.openprice.domain.account.UserAccount;
import com.openprice.domain.account.UserAccountService;
import com.openprice.domain.shopping.ShoppingItem;
import com.openprice.domain.shopping.ShoppingItemRepository;
import com.openprice.domain.store.Store;
import com.openprice.domain.store.StoreRepository;
import com.openprice.rest.AbstractRestController;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.user.UserApiUrls;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ShoppingItemRestController extends AbstractRestController {
    private final StoreRepository storeRepository;
    private final ShoppingItemRepository shoppingItemRepository;
    private final ShoppingItemResourceAssembler shoppingItemResourceAssembler;

    @Inject
    public ShoppingItemRestController(final UserAccountService userAccountService,
                                      final ShoppingItemRepository shoppingItemRepository,
                                      final ShoppingItemResourceAssembler shoppingItemResourceAssembler,
                                      final StoreRepository storeRepository) {
        super(userAccountService);
        this.shoppingItemRepository = shoppingItemRepository;
        this.shoppingItemResourceAssembler = shoppingItemResourceAssembler;
        this.storeRepository = storeRepository;
    }
    
    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_SHOPPINGLIST)
    public HttpEntity<Void> getUploadShoppingListPath() {
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = UserApiUrls.URL_USER_SHOPPINGLIST)
    public HttpEntity<Void> uploadShoppingList(@RequestBody final ShoppingListForm form) {
        saveShoppingList(form);
        
        URI location = linkTo(methodOn(ShoppingItemRestController.class).getStoreShoppingItems(form.getStoreId(), null, null)).toUri();
        return ResponseEntity.created(location).body(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_SHOPPING_STORES_STORE_ITEMS)
    public HttpEntity<PagedResources<ShoppingItemResource>> getStoreShoppingItems(
            @PathVariable("storeId") final String storeId,
            @PageableDefault(size = 10, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<ShoppingItem> assembler) {
        final UserAccount currentUserAccount = getCurrentAuthenticatedUser();
        final Store store = getStoreByIdAndCheckOwner(storeId);
        final Page<ShoppingItem> items = shoppingItemRepository.findByUserAndStoreOrderByItemName(currentUserAccount, store, pageable);
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
    
    private Store getStoreByIdAndCheckOwner(final String storeId) {
        final Store store = storeRepository.findOne(storeId);
        if (store == null) {
            log.warn("ILLEGAL STORE ACCESS! No such store Id: {}.", storeId);
            throw new ResourceNotFoundException("No store with the id: " + storeId);
        }
        
        // TODO? May need to check if current user has the store
        
        return store;
    }
    
    private ShoppingItem getShoppingItemByIdAndCheckStore(final String storeId, final String itemId) {
        final Store store = storeRepository.findOne(storeId);
        if (store == null) {
            log.warn("ILLEGAL STORE ACCESS! No such store Id: {}.", storeId);
            throw new ResourceNotFoundException("No store with the id: " + storeId);
        }
        
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
    private void saveShoppingList(final ShoppingListForm form) {
        final UserAccount currentUser = getCurrentAuthenticatedUser();
        final Store store = getStoreByIdAndCheckOwner(form.getStoreId());
        final List<ShoppingItem> shoppingItems = new ArrayList<>();
        for (final ShoppingListForm.Item item : form.getItems()) {
            final ShoppingItem shoppingItem = new ShoppingItem();
            shoppingItem.setItemName(item.getName());
            shoppingItem.setItemPrice(item.getPrice());
            shoppingItem.setUser(currentUser);
            shoppingItem.setStore(store);
            shoppingItems.add(shoppingItem);
        }
        shoppingItemRepository.save(shoppingItems);
    }
}
