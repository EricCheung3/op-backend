package com.openprice.rest.user;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

import com.damnhandy.uri.template.UriTemplate;
import com.jayway.jsonpath.JsonPath;
import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.shopping.ShoppingItem;
import com.openprice.domain.shopping.ShoppingItemRepository;
import com.openprice.domain.store.StoreChain;
import com.openprice.domain.store.StoreChainRepository;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.user.store.ShoppingListForm;

public class UserStoreApiDocumentation extends UserApiDocumentationBase {
    @Inject
    protected StoreChainRepository storeRepository;

    @Inject
    protected ShoppingItemRepository shoppingItemRepository;

    @Test
    public void storeListExample() throws Exception {
        mockMvc
        .perform(get(userStoresUrl()).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-store-list-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link")
            ),
            responseFields(
                fieldWithPath("_embedded.storeChains").description("An array of <<resources-user-store,Store resources>>"),
                fieldWithPath("page").description("Pagination data"),
                fieldWithPath("_links").description("<<resources-user-store-list-links,Links>> to other resources")
            )
        ));
    }

    @Test
    public void storeRetrieveExample() throws Exception {
        mockMvc
        .perform(get(userStoreUrl()).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-store-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("user").description("The user link"),
                linkWithRel("items").description("The <<resources-user-store-items,Shopping Items>> link"),
                linkWithRel("item").description("The <<resources-user-store-item,Shopping Item>> link")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("code").description("Store code"),
                fieldWithPath("name").description("Store name"),
                fieldWithPath("categories").description("Store categories"),
                fieldWithPath("identifyFields").description("Store identify fields"),
                fieldWithPath("_links").description("<<resources-user-store-retrieve-links,Links>> to other resources")
            )
        ));

    }

    @Test
    public void uploadShoppingListExample() throws Exception {
        String responseContent =
            mockMvc
            .perform(get(UtilConstants.API_ROOT + UserApiUrls.URL_USER).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        String shoppinglistLink = JsonPath.read(responseContent, "_links.shoppingList.href");
        String storesLink = JsonPath.read(responseContent, "_links.stores.href");
        responseContent =
            mockMvc
            .perform(get(userStoresUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        String storeId = JsonPath.read(responseContent, "_embedded.storeChains[0].id");

        ShoppingListForm form = new ShoppingListForm();
        form.setStoreId(storeId);
        form.getItems().add(new ShoppingListForm.Item("milk", "4.99"));
        form.getItems().add(new ShoppingListForm.Item("eggs", "1.99"));

        mockMvc
        .perform(
            post(shoppinglistLink)
            .with(user(USERNAME))
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(form))
        )
        .andExpect(status().isCreated())
        .andDo(document("user-shoppinglist-upload-example",
            preprocessRequest(prettyPrint()),
            requestFields(
                fieldWithPath("storeId").description("The id of the store for shopping list."),
                fieldWithPath("items").description("The shopping list items array.")
            )
        ));

        deleteShoppingItems();
    }

    @Test
    public void shoppingItemExample() throws Exception {
        createShoppingItems();

        String responseContent =
            mockMvc
            .perform(get(userStoresUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        String storeLink = JsonPath.read(responseContent, "_embedded.storeChains[0]._links.self.href");
        responseContent =
            mockMvc
            .perform(get(storeLink).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        String itemsLink = JsonPath.read(responseContent, "_links.items.href");
        responseContent =
            mockMvc
            .perform(get(itemsLink).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        String itemLink = JsonPath.read(responseContent, "_embedded.shoppingItems[0]._links.self.href");

        mockMvc
        .perform(get(itemsLink).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-store-item-list-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link")
            ),
            responseFields(
                fieldWithPath("_embedded.shoppingItems").description("An array of <<resources-user-store-item,Shopping Item resources>>"),
                fieldWithPath("page").description("Pagination data"),
                fieldWithPath("_links").description("<<resources-user-store-item-list-links,Links>> to other resources")
            )
        ));

        mockMvc
        .perform(get(itemLink).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-store-item-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link."),
                linkWithRel("user").description("<<resources-user,Link>> to shopping list item owner."),
                linkWithRel("store").description("<<resources-user-store, Link>> to the store this item belong to.")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("itemName").description("Shopping Item name"),
                fieldWithPath("itemPrice").description("Shopping Item price"),
                fieldWithPath("_links").description("<<resources-user-store-item-retrieve-links,Links>> to other resources")
            )
        ));

        deleteShoppingItems();
    }


    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        createTestUser();
        createStores();
    }

    @After
    public void teardown() throws Exception {
        deleteStores();
        deleteTestUser();
    }

    protected void createStores() throws Exception {
        StoreChain store = new StoreChain();
        store.setCode("Safeway");
        store.setName("Safeway");
        store.setCategories("Grocery");
        store = storeRepository.save(store);

        store = new StoreChain();
        store.setCode("RCSS");
        store.setName("SuperStore");
        store.setCategories("Grocery");
        store = storeRepository.save(store);
    }

    protected void deleteStores() throws Exception {
        storeRepository.deleteAll();
    }

    protected void createShoppingItems() throws Exception {
        final UserAccount user = userAccountRepository.findByEmail(USERNAME);
        final StoreChain store = storeRepository.findByCode("Safeway");

        ShoppingItem item = new ShoppingItem();
        item.setUser(user);
        item.setStore(store);
        item.setItemName("Milk");
        item.setItemPrice("4.99");
        shoppingItemRepository.save(item);

        item = new ShoppingItem();
        item.setUser(user);
        item.setStore(store);
        item.setItemName("eggs");
        item.setItemPrice("1.99");
        shoppingItemRepository.save(item);
    }

    protected void deleteShoppingItems() throws Exception {
        shoppingItemRepository.deleteAll();
    }

    private String userStoresUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        final String storesLink = JsonPath.read(responseContent, "_links.stores.href");
        return UriTemplate.fromTemplate(storesLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    private String userStoreUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userStoresUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.storeChains[0]._links.self.href");
    }

}
