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
import com.openprice.domain.shopping.ShoppingService;
import com.openprice.domain.shopping.ShoppingStore;
import com.openprice.domain.shopping.ShoppingStoreRepository;
import com.openprice.rest.user.store.CreateShoppingItemForm;
import com.openprice.rest.user.store.ShoppingListForm;
import com.openprice.rest.user.store.UpdateShoppingItemForm;
import com.openprice.store.StoreChain;
import com.openprice.store.StoreMetadata;

public class UserStoreApiDocumentation extends UserApiDocumentationBase {

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
                fieldWithPath("icon").description("Icon name, 'generic' if no grapic icon for the store."),
                fieldWithPath("displayName").description("Shopping list display name"),
                fieldWithPath("_embedded.shoppingItems").description("Shopping list items"),
                fieldWithPath("_links").description("<<resources-user-shopping-store-retrieve-links,Links>> to other resources")
            )
        ));
    }

    @Test
    public void uploadShoppingListExample() throws Exception {
        ShoppingListForm form =
            ShoppingListForm.builder()
                            .chainCode("safeway")
                            .item(CreateShoppingItemForm.builder().name("milk").catalogCode("MILK").build())
                            .item(CreateShoppingItemForm.builder().name("egg").catalogCode("EGG").build())
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
        final CreateShoppingItemForm form =
                CreateShoppingItemForm.builder()
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
                fieldWithPath("catalogCode").description("The code of catalog product, from parser result.")
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
                fieldWithPath("number").description("The number of items user wants to buy, default to 1, user can edit"),
                fieldWithPath("catalogCode").description("Optional catalog code."),
                fieldWithPath("catalog").description("Catalog object if catalogCode is not null."),
                fieldWithPath("categoryCode").description("The category this item belongs to."),
                fieldWithPath("_links").description("<<resources-user-shopping-item-retrieve-links,Links>> to other resources")
            )
        ));
    }

    @Test
    public void shoppingItemUpdateExample() throws Exception {
        final UpdateShoppingItemForm form =
                UpdateShoppingItemForm.builder()
                                      .name("2% milk")
                                      .number(1)
                                      .categoryCode("dairy")
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
                fieldWithPath("categoryCode").description("User changed ProductCategory code."),
                fieldWithPath("number").description("The number of items user wants to buy.")
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
                fieldWithPath("[].catalogCode").description("The catalog code."),
                fieldWithPath("[].receiptName").description("The catalog code name."),
                fieldWithPath("[].number").description("The catalog code number."),
                fieldWithPath("[].price").description("The price (unit price?)."),
                fieldWithPath("[].naturalName").description("Readable name for the catalog product."),
                fieldWithPath("[].categoryCode").description("The code of category this product belongs to.")
            )
        ));
    }

    @Inject
    ShoppingService shoppingService;

    @Inject
    StoreMetadata storeMetadata;

    @Inject
    ShoppingStoreRepository shoppingStoreRepository;

    @Inject
    ShoppingItemRepository shoppingItemRepository;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        createTestUser();
        createShoppingLists();
    }

    @After
    public void teardown() throws Exception {
        deleteShoppingLists();
        deleteTestUser();
    }

    protected void createShoppingLists() throws Exception {
        final UserAccount user = userAccountRepository.findByEmail(USERNAME);
        final StoreChain chain = storeMetadata.getStoreChainByCode("rcss");
        final ShoppingStore store = shoppingStoreRepository.save(ShoppingStore.createShoppingStore(user, chain));
        shoppingService.addShoppingItemToStore(store, "MILK_1200", "milk");
        shoppingService.addShoppingItemToStore(store, "EGG_1234", "egg");
    }

    protected void deleteShoppingLists() throws Exception {
        shoppingItemRepository.deleteAll();
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
