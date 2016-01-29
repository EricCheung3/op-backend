package com.openprice.domain.receipt;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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
public class ReceiptParsingServiceTest {

    static final String USER_ID = "user123";
    static final String USER_EMAIL = "john.doe@email.com";
    static final String TEST_CONTENT = "test";
    static final String RECEIPT_ID = "receipt123";

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
    SimpleParser simpleParserMock;

    @InjectMocks
    ReceiptParsingService serviceToTest;


    private UserAccount testUser;
    private Receipt receipt;
    private ReceiptImage image1;
    private ReceiptImage image2;
    private ReceiptImage image3;

    @Before
    public void setup() {
        testUser = UserAccount.testObjectBuilder()
                              .id(USER_ID)
                              .email(USER_EMAIL)
                              .build();
        receipt = Receipt.testObjectBuilder()
                         .id(RECEIPT_ID)
                         .user(testUser)
                         .build();
        image1 = ReceiptImage.testObjectBuilder()
                             .receipt(receipt)
                             .fileName("test1")
                             .ocrResult("ocr result1")
                             .build();
        image2 = ReceiptImage.testObjectBuilder()
                             .receipt(receipt)
                             .fileName("test2")
                             .ocrResult("ocr result2")
                             .build();
        image3 = ReceiptImage.testObjectBuilder()
                             .receipt(receipt)
                             .fileName("test3")
                             .ocrResult(null)
                             .build();
    }

    @Test
    public void parseScannedReceiptImages_ShouldSetReceiptToWaitForResult_IfHasUploadedImage() throws Exception {
        when(receiptImageRepositoryMock.countByReceiptAndStatus(eq(receipt), eq(ProcessStatusType.UPLOADED))).thenReturn(1l);
        when(receiptRepositoryMock.save(isA(Receipt.class))).thenAnswer(new Answer<Receipt>() {
            @Override
            public Receipt answer(InvocationOnMock invocation) throws Throwable {
                return (Receipt) invocation.getArguments()[0];
            }
        });

        Receipt updatedReceipt = serviceToTest.parseScannedReceiptImages(receipt);

        assertEquals(ReceiptStatusType.WAIT_FOR_RESULT, updatedReceipt.getStatus());
        verify(receiptRepositoryMock, times(1)).save(isA(Receipt.class));
    }

    @Test
    public void parseScannedReceiptImages_ShouldSetReceiptToOCRError_IfScannedImagesAllError() throws Exception {
        when(receiptImageRepositoryMock.countByReceiptAndStatus(eq(receipt), eq(ProcessStatusType.UPLOADED))).thenReturn(0l);
        when(receiptImageRepositoryMock.findByReceiptAndStatusOrderByCreatedTime(eq(receipt), eq(ProcessStatusType.SCANNED)))
            .thenReturn(new ArrayList<>());
        when(receiptImageRepositoryMock.countByReceiptAndStatus(eq(receipt), eq(ProcessStatusType.SCANNED_ERR))).thenReturn(1l);

        when(receiptRepositoryMock.save(isA(Receipt.class))).thenAnswer(new Answer<Receipt>() {
            @Override
            public Receipt answer(InvocationOnMock invocation) throws Throwable {
                return (Receipt) invocation.getArguments()[0];
            }
        });

        Receipt updatedReceipt = serviceToTest.parseScannedReceiptImages(receipt);

        assertEquals(ReceiptStatusType.OCR_ERROR, updatedReceipt.getStatus());
        verify(receiptImageRepositoryMock, times(1)).countByReceiptAndStatus(eq(receipt), eq(ProcessStatusType.UPLOADED));
        verify(receiptImageRepositoryMock, times(1)).countByReceiptAndStatus(eq(receipt), eq(ProcessStatusType.SCANNED_ERR));
        verify(receiptRepositoryMock, times(1)).save(isA(Receipt.class));
    }

    @Test
    public void parseScannedReceiptImages_ShouldSetReceiptToHasResult_IfScannedImagesCanParse() throws Exception {
        final List<Item> items = new ArrayList<>();
        items.add(Item.fromNameBuyPUnitPWeightCategory("milk", "10.99", "1.99", "4.00", "food"));
        items.add(Item.fromNameBuyPUnitPWeightCategory("eggs", "4.99", "4.99", "12", "food"));

        final StoreChain chain = StoreChain.genericStoreChain("rcss");
        final StoreBranch branch = StoreBranch.builder().branchName("Calgary Trail").build();
        final Map<ReceiptField, ValueLine> fieldToValueLine = new HashMap<ReceiptField, ValueLine>();
        fieldToValueLine.put(ReceiptField.Total, new ValueLine("15.00", -1));
        final ParsedReceipt receiptDebug = ParsedReceipt.fromChainItemsMapBranch(chain, items, fieldToValueLine, branch);

        when(receiptImageRepositoryMock.countByReceiptAndStatus(eq(receipt), eq(ProcessStatusType.UPLOADED))).thenReturn(0l);
        when(receiptImageRepositoryMock.findByReceiptAndStatusOrderByCreatedTime(eq(receipt), eq(ProcessStatusType.SCANNED)))
            .thenReturn(Arrays.asList(image1, image2, image3));
        when(simpleParserMock.parseOCRResults(anyObject())).thenReturn(receiptDebug);
        when(receiptResultRepositoryMock.save(isA(ReceiptResult.class))).thenAnswer(new Answer<ReceiptResult>() {
            @Override
            public ReceiptResult answer(InvocationOnMock invocation) throws Throwable {
                return (ReceiptResult) invocation.getArguments()[0];
            }
        });
        when(receiptRepositoryMock.save(isA(Receipt.class))).thenAnswer(new Answer<Receipt>() {
            @Override
            public Receipt answer(InvocationOnMock invocation) throws Throwable {
                return (Receipt) invocation.getArguments()[0];
            }
        });

        Receipt updatedReceipt = serviceToTest.parseScannedReceiptImages(receipt);

        assertEquals(ReceiptStatusType.HAS_RESULT, updatedReceipt.getStatus());

        verify(receiptImageRepositoryMock, times(1)).countByReceiptAndStatus(eq(receipt), eq(ProcessStatusType.UPLOADED));
        verify(receiptImageRepositoryMock, times(0)).countByReceiptAndStatus(eq(receipt), eq(ProcessStatusType.SCANNED_ERR));
        verify(receiptImageRepositoryMock, times(1)).findByReceiptAndStatusOrderByCreatedTime(eq(receipt), eq(ProcessStatusType.SCANNED));
        verify(receiptRepositoryMock, times(1)).save(isA(Receipt.class));

        {
            ArgumentCaptor<ReceiptResult> argument = ArgumentCaptor.forClass(ReceiptResult.class);
            verify(receiptResultRepositoryMock, times(2)).save(argument.capture());
            ReceiptResult result = argument.getValue();
            assertEquals("rcss", result.getChainCode());
            assertEquals("Calgary Trail", result.getBranchName());
        }

    }

    @Test
    public void parseScannedReceiptImages_ShouldSetReceiptToParserError_IfScannedImagesCannotParse() throws Exception {
        when(receiptImageRepositoryMock.countByReceiptAndStatus(eq(receipt), eq(ProcessStatusType.UPLOADED))).thenReturn(0l);
        when(receiptImageRepositoryMock.findByReceiptAndStatusOrderByCreatedTime(eq(receipt), eq(ProcessStatusType.SCANNED)))
            .thenReturn(Arrays.asList(image1, image2, image3));
        when(simpleParserMock.parseOCRResults(anyObject())).thenReturn(null);
        when(receiptRepositoryMock.save(isA(Receipt.class))).thenAnswer(new Answer<Receipt>() {
            @Override
            public Receipt answer(InvocationOnMock invocation) throws Throwable {
                return (Receipt) invocation.getArguments()[0];
            }
        });

        Receipt updatedReceipt = serviceToTest.parseScannedReceiptImages(receipt);

        assertEquals(ReceiptStatusType.PARSER_ERROR, updatedReceipt.getStatus());

        verify(receiptImageRepositoryMock, times(1)).countByReceiptAndStatus(eq(receipt), eq(ProcessStatusType.UPLOADED));
        verify(receiptImageRepositoryMock, times(0)).countByReceiptAndStatus(eq(receipt), eq(ProcessStatusType.SCANNED_ERR));
        verify(receiptImageRepositoryMock, times(1)).findByReceiptAndStatusOrderByCreatedTime(eq(receipt), eq(ProcessStatusType.SCANNED));
        verify(receiptRepositoryMock, times(1)).save(isA(Receipt.class));

    }
}
