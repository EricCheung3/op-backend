package com.openprice.rest;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openprice.ApiDocsApplication;
import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.account.user.UserAccountRepository;
import com.openprice.domain.account.user.UserAccountService;
import com.openprice.domain.account.user.UserResetPasswordRequestRepository;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.domain.receipt.ReceiptParsingService;
import com.openprice.domain.receipt.ReceiptRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApiDocsApplication.class)
@WebIntegrationTest(randomPort = true)
@ActiveProfiles("dev")
public abstract class ApiDocumentationBase {

    public static final String USERNAME = "john.doe@email.com";

    @Rule
    public final RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");

    @Inject
    protected WebApplicationContext context;

    @Inject
    protected UserAccountService userAccountService;

    @Inject
    protected UserAccountRepository userAccountRepository;

    @Inject
    protected ReceiptParsingService receiptParsingService;

    @Inject
    protected ReceiptRepository receiptRepository;

    @Inject
    protected ReceiptImageRepository receiptImageRepository;

    @Inject
    protected UserResetPasswordRequestRepository userResetPasswordRequestRepository;

    @Inject
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                                      .apply(SecurityMockMvcConfigurers.springSecurity())
                                      .apply(documentationConfiguration(restDocumentation))
                                      .build();
    }

    protected String createTestUser() throws Exception {
        UserAccount account = userAccountService.createUserAccountByRegistrationData(USERNAME, "password", "John", "Doe");
        userAccountService.activateAccount(account.getId());
        account = userAccountRepository.findByEmail(USERNAME);
        account.getProfile().setPhone("780-888-1234");
        account.getProfile().getAddress().setAddress1("GroundTruth Inc.");
        account.getProfile().getAddress().setAddress2("123 Street");
        account.getProfile().getAddress().setCity("Edmonton");
        account.getProfile().getAddress().setState("AB");
        account.getProfile().getAddress().setCountry("Canada");
        userAccountRepository.save(account);
        return USERNAME;
    }

    protected void deleteTestUser() throws Exception {
        UserAccount account = userAccountRepository.findByEmail(USERNAME);
        userAccountRepository.delete(account);
    }

}
