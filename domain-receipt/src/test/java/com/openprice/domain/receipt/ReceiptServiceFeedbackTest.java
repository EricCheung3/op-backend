package com.openprice.domain.receipt;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.openprice.domain.account.user.UserAccount;

@RunWith(MockitoJUnitRunner.class)
public class ReceiptServiceFeedbackTest {

    static final Integer TEST_RATING = 0;
    static final String TEST_COMMENT = "test comment";

    @Mock
    ReceiptRepository receiptRepositoryMock;

    @Mock
    ReceiptFeedbackRepository feedbackRepositoryMock;

    @InjectMocks
    ReceiptService serviceToTest;

    @Test
    public void addFeedback_ShouldCreateNewFeedbackAndChangeNeedFeedback() throws Exception {
        // prepare
        final UserAccount testUser = UserAccount.testObjectBuilder()
                                                .id("user123")
                                                .email("user123@gmail.com")
                                                .build();
        final Receipt testReceipt = new Receipt();
        testReceipt.setId("receipt123");
        testReceipt.setUser(testUser);
        testReceipt.setNeedFeedback(true);

        when(feedbackRepositoryMock.save(isA(ReceiptFeedback.class))).thenAnswer(new Answer<ReceiptFeedback>() {
            @Override
            public ReceiptFeedback answer(InvocationOnMock invocation) throws Throwable {
                return (ReceiptFeedback) invocation.getArguments()[0];
            }
        });

        // execute
        final ReceiptFeedback feedback = serviceToTest.addFeedback(testReceipt, TEST_RATING, TEST_COMMENT);

        // verify
        assertEquals(false, testReceipt.getNeedFeedback());
        assertEquals(testReceipt, feedback.getReceipt());
        assertEquals(TEST_RATING, feedback.getRating());
        assertEquals(TEST_COMMENT, feedback.getComment());
        verify(feedbackRepositoryMock, times(1)).save(isA(ReceiptFeedback.class));
        verify(receiptRepositoryMock, times(1)).save(isA(Receipt.class));
    }
}
