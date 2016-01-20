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

    static final Integer rating = 0;
    static final String comment = "comment";

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
        final Receipt receipt = new Receipt();
        receipt.setId("receiptTest");
        receipt.setUser(testUser);
        receipt.setNeedFeedback(true);

        when(feedbackRepositoryMock.save(isA(ReceiptFeedback.class))).thenAnswer(new Answer<ReceiptFeedback>() {
            @Override
            public ReceiptFeedback answer(InvocationOnMock invocation) throws Throwable {
                return (ReceiptFeedback) invocation.getArguments()[0];
            }
        });

        // execute
        final ReceiptFeedback feedback = serviceToTest.addFeedback(receipt, rating, comment);

        // verify
        assertEquals(false, receipt.getNeedFeedback());
        assertEquals(receipt, feedback.getReceipt());
        assertEquals(rating, feedback.getRating());
        assertEquals(comment, feedback.getComment());

        verify(feedbackRepositoryMock, times(1)).save(isA(ReceiptFeedback.class));
        verify(receiptRepositoryMock, times(1)).save(isA(Receipt.class));
    }
}
