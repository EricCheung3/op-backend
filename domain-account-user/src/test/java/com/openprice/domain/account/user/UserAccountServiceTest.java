package com.openprice.domain.account.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class UserAccountServiceTest {

    final String USER_EMAIL = "john.doe@email.com";
    final String INVALID_EMAIL = "non@email.com";

    @Mock
    UserAccountRepository accountRepositoryMock;

    @Mock
    UserResetPasswordRequestRepository userResetPasswordRequestRepositoryMock;

    @InjectMocks
    UserAccountService serviceToTest;

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

        final UserAccount newAccount = serviceToTest.createUserAccountByRegistrationData(USER_EMAIL, "password", "John", "Doe");

        assertEquals(USER_EMAIL, newAccount.getEmail());
        assertFalse(newAccount.getTrustedAccount());
        assertTrue(newAccount.isEnabled());
        assertEquals("John", newAccount.getProfile().getFirstName());
        assertEquals("Doe", newAccount.getProfile().getLastName());

        verify(accountRepositoryMock, times(1)).save(isA(UserAccount.class));
    }

    @Test
    public void createResetPasswordRequest_ShouldReturnNull_WhenNonRegisteredEmail() {
        when(accountRepositoryMock.findByEmail(INVALID_EMAIL)).thenReturn(null);

        final UserResetPasswordRequest request = serviceToTest.createResetPasswordRequest(INVALID_EMAIL);

        assertNull(request);
        verify(accountRepositoryMock, times(1)).findByEmail(eq(INVALID_EMAIL));
    }

    @Test
    public void createResetPasswordRequest_ShouldDeleteOldRequestAndCreateNewRequest() {
        final UserAccount user = UserAccount.testObjectBuilder()
                                            .email(USER_EMAIL)
                                            .build();
        final UserResetPasswordRequest oldRequest = UserResetPasswordRequest.createRequest(USER_EMAIL);

        when(accountRepositoryMock.findByEmail(USER_EMAIL)).thenReturn(user);
        when(userResetPasswordRequestRepositoryMock.findByEmail(USER_EMAIL)).thenReturn(Arrays.asList(oldRequest));
        when(userResetPasswordRequestRepositoryMock.save(isA(UserResetPasswordRequest.class))).thenAnswer( new Answer<UserResetPasswordRequest>() {
            @Override
            public UserResetPasswordRequest answer(InvocationOnMock invocation) throws Throwable {
                final UserResetPasswordRequest request = (UserResetPasswordRequest)invocation.getArguments()[0];
                request.setId("request123");
                return request;
            }
        });

        final UserResetPasswordRequest request = serviceToTest.createResetPasswordRequest(USER_EMAIL);

        assertNotNull(request);
        assertNotNull(request.getId());
        assertNotNull(request.getRequestTime());
        assertEquals(USER_EMAIL, request.getEmail());
        verify(accountRepositoryMock, times(1)).findByEmail(eq(USER_EMAIL));
        verify(userResetPasswordRequestRepositoryMock, times(1)).findByEmail(USER_EMAIL);
        verify(userResetPasswordRequestRepositoryMock, times(1)).save(isA(UserResetPasswordRequest.class));
    }
}
