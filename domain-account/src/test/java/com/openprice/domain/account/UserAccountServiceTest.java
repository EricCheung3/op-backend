package com.openprice.domain.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

@RunWith(MockitoJUnitRunner.class)
public class UserAccountServiceTest {
    @Mock
    UserAccountRepository accountRepositoryMock;

    @Mock
    UserProfileRepository profileRepositoryMock;

    UserAccountService serviceToTest;

    @Before
    public void setup() {
        serviceToTest = new UserAccountService(accountRepositoryMock, profileRepositoryMock);
    }

    @Test
    public void createUserAccountByRegistrationData_ShouldCreateNewUser() {

        when(accountRepositoryMock.count()).thenReturn(new Long(1l));
        when(accountRepositoryMock.save(isA(UserAccount.class))).thenAnswer( new Answer<UserAccount>() {
            @Override
            public UserAccount answer(InvocationOnMock invocation) throws Throwable {
                final UserAccount account = (UserAccount)invocation.getArguments()[0];
                account.setId("user123");
                return account;
            }

        });

        final UserAccount newAccount = serviceToTest.createUserAccountByRegistrationData("john.doe@email.com", "password", "John", "Doe");

        assertEquals("john.doe@email.com", newAccount.getEmail());
        assertFalse(newAccount.isTrustedAccount());
        //assertFalse(newAccount.isEnabled());
        assertEquals("John", newAccount.getProfile().getFirstName());
        assertEquals("Doe", newAccount.getProfile().getLastName());

        verify(accountRepositoryMock, times(1)).save(isA(UserAccount.class));
    }

}
