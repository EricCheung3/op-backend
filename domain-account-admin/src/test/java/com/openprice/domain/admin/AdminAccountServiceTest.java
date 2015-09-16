package com.openprice.domain.admin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.openprice.domain.account.admin.AdminAccount;
import com.openprice.domain.account.admin.AdminAccountRepository;
import com.openprice.domain.account.admin.AdminAccountService;

@RunWith(MockitoJUnitRunner.class)
public class AdminAccountServiceTest {
    @Mock
    AdminAccountRepository accountRepositoryMock;

    AdminAccountService serviceToTest;

    @Before
    public void setup() {
        serviceToTest = new AdminAccountService(accountRepositoryMock);
    }

    @Test
    public void createAdminAccount_ShouldCreateNewAdmin() {

        when(accountRepositoryMock.count()).thenReturn(new Long(1l));
        when(accountRepositoryMock.save(isA(AdminAccount.class))).thenAnswer( new Answer<AdminAccount>() {
            @Override
            public AdminAccount answer(InvocationOnMock invocation) throws Throwable {
                final AdminAccount account = (AdminAccount)invocation.getArguments()[0];
                account.setId("admin123");
                return account;
            }
        });

        final AdminAccount newAccount = serviceToTest.createAdminAccount(
            "johndoe", "password", "John", "Doe", "john.doe@mail.com", "CEO");

        assertEquals("johndoe", newAccount.getUsername());
        assertTrue(newAccount.isEnabled());
        assertEquals("John", newAccount.getFirstName());
        assertEquals("Doe", newAccount.getLastName());
        assertEquals("john.doe@mail.com", newAccount.getEmail());
        assertEquals("CEO", newAccount.getTitle());

        verify(accountRepositoryMock, times(1)).save(isA(AdminAccount.class));
    }

}
