package com.openprice.domain.receipt;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.StoreBranch;
import com.openprice.parser.StoreChain;
import com.openprice.parser.data.Item;
import com.openprice.parser.data.ReceiptField;
import com.openprice.parser.data.ValueLine;
import com.openprice.parser.simple.SimpleParser;

@RunWith(MockitoJUnitRunner.class)
public class ReceiptServiceTest {

    static final String USER_ID = "user123";
    static final String USER_EMAIL = "john.doe@email.com";
    static final String TEST_CONTENT = "test";

    @Mock
    ReceiptRepository receiptRepositoryMock;

    @Mock
    ReceiptImageRepository receiptImageRepositoryMock;

    @Mock
    ReceiptResultRepository receiptResultRepositoryMock;

    @Mock
    ReceiptItemRepository receiptItemRepositoryMock;

    @Mock
    ReceiptFeedbackRepository receiptFeedbackRepositoryMock;

    @Mock
    SimpleParser simpleParser;

    @InjectMocks
    ReceiptService serviceToTest;

    @Test
    public void getLatestReceiptParserResult_ShouldGenerateParserResult_IfNotInDatabase() throws Exception {
        final UserAccount testUser = UserAccount.testObjectBuilder()
                                                .id(USER_ID)
                                                .email(USER_EMAIL)
                                                .build();
        final Receipt receipt = Receipt.createReceipt(testUser);
        receipt.setId("receipt123");

        final ReceiptImage image1 = new ReceiptImage();
        image1.setReceipt(receipt);
        image1.setFileName("test1");
        image1.setOcrResult("ocr result1");
        final ReceiptImage image2 = new ReceiptImage();
        image2.setReceipt(receipt);
        image2.setFileName("test2");
        image2.setOcrResult("ocr result2");
        final List<ReceiptImage> images = Arrays.asList(image1, image2);

        final List<String> ocrTextList = Arrays.asList("ocr result1","ocr result2");

        final List<Item> items = new ArrayList<>();
        items.add(new Item("milk", "10.99", "1.99", "4.00", "food"));
        items.add(new Item("eggs", "4.99", "4.99", "12", "food"));

        final StoreChain chain = StoreChain.builder().code("rcss").build();
        final StoreBranch branch = StoreBranch.builder().branchName("Calgary Trail").build();
        final Map<ReceiptField, ValueLine> fieldToValueLine = new HashMap<ReceiptField, ValueLine>();
        fieldToValueLine.put(ReceiptField.Total, ValueLine.builder().line(-1).value("15.00").build());
        final ParsedReceipt receiptDebug = ParsedReceipt.builder().chain(chain).branch(branch).fieldToValueMap(fieldToValueLine).items(items).build();

        when(receiptResultRepositoryMock.findFirstByReceiptOrderByCreatedTimeDesc(eq(receipt))).thenReturn(null);
        when(receiptImageRepositoryMock.findByReceiptOrderByCreatedTime(eq(receipt))).thenReturn(images);
        when(simpleParser.parseOCRResults(eq(ocrTextList))).thenReturn(receiptDebug);
        when(receiptResultRepositoryMock.save(isA(ReceiptResult.class))).thenAnswer( new Answer<ReceiptResult>() {
            @Override
            public ReceiptResult answer(InvocationOnMock invocation) throws Throwable {
                return (ReceiptResult)invocation.getArguments()[0];
            }
        });

        final ReceiptResult data = serviceToTest.getLatestReceiptResult(receipt);
        assertEquals("rcss", data.getChainCode());
        assertEquals("Calgary Trail", data.getBranchName());
        verify(receiptItemRepositoryMock, times(2)).save(isA(ReceiptItem.class));

    }
}
