package com.openprice.rest.user;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
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
import com.openprice.domain.shopping.ShoppingItemRepository;
import com.openprice.domain.shopping.ShoppingStore;
import com.openprice.domain.shopping.ShoppingStoreRepository;
import com.openprice.domain.store.CatalogRepository;
import com.openprice.domain.store.StoreChain;
import com.openprice.domain.store.StoreChainRepository;
import com.openprice.rest.user.store.ShoppingItemForm;
import com.openprice.rest.user.store.ShoppingListForm;

public class UserStoreApiDocumentation extends UserApiDocumentationBase {

    @Inject
    StoreChainRepository storeRepository;

    @Inject
    CatalogRepository catalogRepository;

    @Inject
    ShoppingStoreRepository shoppingStoreRepository;

    @Inject
    ShoppingItemRepository shoppingItemRepository;

    @Test
    public void storeListExample() throws Exception {
        mockMvc
        .perform(get(userStoresUrl()).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-shopping-store-list-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link")
            ),
            responseFields(
                fieldWithPath("_embedded.shoppingStores").description("An array of <<resources-user-shopping-store, Shopping Store resources>>"),
                fieldWithPath("page").description("Pagination data"),
                fieldWithPath("_links").description("<<resources-user-shopping-store-list-links,Links>> to other resources")
            )
        ));
    }

    @Test
    public void storeRetrieveExample() throws Exception {
        mockMvc
        .perform(get(userStoreUrl()).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-shopping-store-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("user").description("The user link"),
                linkWithRel("items").description("The <<resources-user-shopping-items, Shopping Items>> link"),
                linkWithRel("item").description("The <<resources-user-shopping-item, Shopping Item>> link"),
                linkWithRel("catalogs").description("The <<resources-user-shopping-store-catalogs, Shopping Store Catalogs Query>> link")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("chainCode").description("Store Chain code this shopping list belongs to. Can be null?"),
                fieldWithPath("displayName").description("Shopping list display name"),
                fieldWithPath("items").description("Shopping list items"),
                fieldWithPath("_links").description("<<resources-user-shopping-store-retrieve-links,Links>> to other resources")
            )
        ));
    }

    @Test
    public void uploadShoppingListExample() throws Exception {
        ShoppingListForm form =
            ShoppingListForm.builder()
                            .chainCode("safeway")
                            .item(ShoppingItemForm.builder().name("milk").catalogCode("MILK").build())
                            .item(ShoppingItemForm.builder().name("egg").catalogCode("EGG").build())
                            .build();

        mockMvc
        .perform(
            post(userShoppingListUrl())
            .with(user(USERNAME))
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(form))
        )
        .andExpect(status().isCreated())
        .andDo(document("user-shoppinglist-upload-example",
            preprocessRequest(prettyPrint()),
            requestFields(
                fieldWithPath("chainCode").description("The code of the store chain for shopping list."),
                fieldWithPath("items").description("The shopping list items array.")
            )
        ));
    }

    @Test
    public void shoppingItemListExample() throws Exception {
        mockMvc
        .perform(get(userShoppingItemsUrl()).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-shopping-item-list-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link")
            ),
            responseFields(
                fieldWithPath("_embedded.shoppingItems").description("An array of <<resources-user-shopping-item,Shopping Item resources>>"),
                fieldWithPath("page").description("Pagination data"),
                fieldWithPath("_links").description("<<resources-user-shopping-item-list-links,Links>> to other resources")
            )
        ));
    }

    @Test
    public void shoppingItemCreateExample() throws Exception {
        final ShoppingItemForm form =
                ShoppingItemForm.builder()
                                .name("milk")
                                .catalogCode("MILK")
                                .build();
        mockMvc
        .perform(
            post(userShoppingItemsUrl())
            .with(user(USERNAME))
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(form))
        )
        .andExpect(status().isCreated())
        .andDo(document("user-shopping-item-create-example",
            preprocessRequest(prettyPrint()),
            requestFields(
                fieldWithPath("name").description("The item name copied from catalog, user can edit."),
                fieldWithPath("catalogCode").description("The code of catalog, from parser result.")
            )
        ));
    }

    @Test
    public void shoppingItemRetrieveExample() throws Exception {
        mockMvc
        .perform(get(userShoppingItemUrl()).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-shopping-item-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link."),
                linkWithRel("user").description("<<resources-user,Link>> to shopping list item owner."),
                linkWithRel("store").description("<<resources-user-shopping-store, Link>> to the shopping store this item belong to.")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("name").description("Shopping Item name, user can edit"),
                fieldWithPath("catalogCode").description("Optional catalog code."),
                fieldWithPath("catalog").description("Catalog object if catalogCode is not null."),
                fieldWithPath("_links").description("<<resources-user-shopping-item-retrieve-links,Links>> to other resources")
            )
        ));
    }

    @Test
    public void shoppingItemUpdateExample() throws Exception {
        final ShoppingItemForm form =
                ShoppingItemForm.builder()
                                .name("2% milk")
                                .build();
        mockMvc
        .perform(
            put(userShoppingItemUrl())
            .with(user(USERNAME))
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(form))
        )
        .andExpect(status().isNoContent())
        .andDo(document("user-shopping-item-update-example",
            preprocessRequest(prettyPrint()),
            requestFields(
                fieldWithPath("name").description("Shopping Item name"),
                fieldWithPath("catalogCode").description("Catalog code, ignored.")
            )
        ));
    }

    @Test
    public void shoppingItemDeleteExample() throws Exception {
        mockMvc
        .perform(delete(userShoppingItemUrl()).with(user(USERNAME)))
        .andExpect(status().isNoContent())
        .andDo(document("user-shopping-item-delete-example"));
    }

    @Test
    public void catalogQueryExample() throws Exception {
        mockMvc
        .perform(get(userShoppingStoreCatalogQueryUrl("egg")).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-shopping-store-catalogs-query-example",
            preprocessResponse(prettyPrint()),
            responseFields(
                fieldWithPath("[].id").description("Primary ID"),
                fieldWithPath("[].code").description("The catalog code."),
                fieldWithPath("[].name").description("The catalog name."),
                fieldWithPath("[].number").description("The catalog number."),
                fieldWithPath("[].price").description("The price (unit price?)."),
                fieldWithPath("[].naturalName").description("Readable name for the catalog."),
                fieldWithPath("[].labelCodes").description("The labels of the catalog.")
            )
        ));

    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        createTestUser();
        createStores();
        createShoppingLists();
    }

    @After
    public void teardown() throws Exception {
        deleteShoppingLists();
        deleteStores();
        deleteTestUser();
    }

    protected void createStores() throws Exception {
        storeRepository.save(StoreChain.createStoreChain("safeway", "Safeway"));

        StoreChain rcss = StoreChain.createStoreChain("rcss", "SuperStore");
        storeRepository.save(rcss);

        // add catalogs to rcss
        catalogRepository.save(rcss.addCatalog("egg", "1234", "1.99", "Large Egg", "food,egg"));
        catalogRepository.save(rcss.addCatalog("egg", "1235", "1.59", "Medium Egg", "food,egg"));
        catalogRepository.save(rcss.addCatalog("egg", "1236", "1.29", "Small Egg", "food,egg"));
    }

    protected void deleteStores() throws Exception {
        storeRepository.deleteAll();
    }

    protected void createShoppingLists() throws Exception {
        final UserAccount user = userAccountRepository.findByEmail(USERNAME);
        final StoreChain chain = storeRepository.findByCode("rcss");
        final ShoppingStore store = shoppingStoreRepository.save(ShoppingStore.createShoppingStore(user, chain));
        shoppingItemRepository.save(store.addItem("MILK", "milk"));
        shoppingItemRepository.save(store.addItem("Egg", "egg"));
        shoppingStoreRepository.save(store);
    }

    protected void deleteShoppingLists() throws Exception {
        shoppingStoreRepository.deleteAll();
    }

    private String userShoppingListUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        final String storesLink = JsonPath.read(responseContent, "_links.shoppingList.href");
        return UriTemplate.fromTemplate(storesLink).set("page", null).set("size", null).set("sort", null).expand();
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
        return JsonPath.read(responseContent, "_embedded.shoppingStores[0]._links.self.href");
    }

    private String userShoppingItemsUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userStoreUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        final String storesLink = JsonPath.read(responseContent, "_links.items.href");
        return UriTemplate.fromTemplate(storesLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    private String userShoppingItemUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userShoppingItemsUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.shoppingItems[0]._links.self.href");
    }

    private String userShoppingStoreCatalogQueryUrl(final String query) throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userStoreUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        final String storesLink = JsonPath.read(responseContent, "_links.catalogs.href");
        return UriTemplate.fromTemplate(storesLink).set("query", query).expand();
    }

}
