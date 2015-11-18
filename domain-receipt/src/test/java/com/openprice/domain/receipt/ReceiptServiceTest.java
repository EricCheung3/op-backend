package com.openprice.domain.receipt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.web.multipart.MultipartFile;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.file.FileFolderSettings;
import com.openprice.file.FileSystemService;
import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.StoreBranch;
import com.openprice.parser.StoreChain;
import com.openprice.parser.data.Item;
import com.openprice.parser.data.ReceiptField;
import com.openprice.parser.data.ValueLine;
import com.openprice.parser.simple.SimpleParser;

@RunWith(MockitoJUnitRunner.class)
public class ReceiptServiceTest {
    @Mock
    ReceiptRepository receiptRepositoryMock;

    @Mock
    ReceiptImageRepository receiptImageRepositoryMock;

    @Mock
    ReceiptDataRepository receiptDataRepositoryMock;

    @Mock
    ReceiptItemRepository receiptItemRepositoryMock;

    @Mock
    ReceiptFeedbackRepository receiptFeedbackRepositoryMock;

    @Mock
    MultipartFile fileMock;

    @Mock
    SimpleParser simpleParser;

    FileSystemService fileSystemService;

    ReceiptService serviceToTest;

    static final String TEST_CONTENT = "test";

    @Before
    public void setup() throws Exception {
        fileSystemService = new FileSystemService(new FileFolderSettings());
        serviceToTest = new ReceiptService(receiptRepositoryMock,
                                           receiptImageRepositoryMock,
                                           receiptDataRepositoryMock,
                                           receiptItemRepositoryMock,
                                           receiptFeedbackRepositoryMock,
                                           fileSystemService,
                                           simpleParser);
    }

    @Test
    public void uploadImageForNewReceipt_ShouldSaveImageBase64String_andCreateReceipt() throws Exception {
        final byte[] content = TEST_CONTENT.getBytes();
        final String base64String = Base64.getEncoder().encodeToString(content);
        final UserAccount testUser = UserAccount.createTestUser("user23", "123@email.com");

        when(receiptImageRepositoryMock.save(isA(ReceiptImage.class))).thenAnswer( new Answer<ReceiptImage>() {
            @Override
            public ReceiptImage answer(InvocationOnMock invocation) throws Throwable {
                final ReceiptImage image = (ReceiptImage)invocation.getArguments()[0];
                image.setId("image123");
                return image;
            }

        });

        when(receiptRepositoryMock.save(isA(Receipt.class))).thenAnswer( new Answer<Receipt>() {
            @Override
            public Receipt answer(InvocationOnMock invocation) throws Throwable {
                final Receipt receipt = (Receipt)invocation.getArguments()[0];
                receipt.setId("receipt123");
                return receipt;
            }

        });

        final Receipt receipt = serviceToTest.uploadImageForNewReceipt(testUser, base64String);

        //receipt has correct data
        assertEquals("receipt123", receipt.getId());
        assertEquals(testUser, receipt.getUser());

        //receipt image has been saved
        final ReceiptImage image = receipt.getImages().get(0);
        assertEquals("image123", image.getId());
        assertEquals(ProcessStatusType.UPLOADED, image.getStatus());

        // check file exists and content is the same as test content
        final Path imageFile = fileSystemService.getReceiptImageSubFolder(testUser.getId()).resolve(image.getFileName());
        assertTrue(Files.exists(imageFile));
        BufferedReader reader = Files.newBufferedReader(imageFile, Charset.defaultCharset());
        assertEquals(TEST_CONTENT, reader.readLine());

    }

    @Test
    public void uploadImageForNewReceipt_ShouldSaveImageFile_andCreateReceipt() throws Exception {
        final byte[] content = TEST_CONTENT.getBytes();
        final UserAccount testUser = UserAccount.createTestUser("user23", "123@email.com");

        when(receiptImageRepositoryMock.save(isA(ReceiptImage.class))).thenAnswer( new Answer<ReceiptImage>() {
            @Override
            public ReceiptImage answer(InvocationOnMock invocation) throws Throwable {
                final ReceiptImage image = (ReceiptImage)invocation.getArguments()[0];
                image.setId("image123");
                return image;
            }

        });

        when(receiptRepositoryMock.save(isA(Receipt.class))).thenAnswer( new Answer<Receipt>() {
            @Override
            public Receipt answer(InvocationOnMock invocation) throws Throwable {
                final Receipt receipt = (Receipt)invocation.getArguments()[0];
                receipt.setId("receipt123");
                return receipt;
            }

        });

        when(fileMock.getBytes()).thenReturn(content);

        final Receipt receipt = serviceToTest.uploadImageForNewReceipt(testUser, fileMock);

        //receipt has correct data
        assertEquals("receipt123", receipt.getId());
        assertEquals(testUser, receipt.getUser());

        //receipt image has been saved
        final ReceiptImage image = receipt.getImages().get(0);
        assertEquals("image123", image.getId());
        assertEquals(ProcessStatusType.UPLOADED, image.getStatus());

        // check file exists and content is the same as test content
        final Path imageFile = fileSystemService.getReceiptImageSubFolder(testUser.getId()).resolve(image.getFileName());
        assertTrue(Files.exists(imageFile));
        BufferedReader reader = Files.newBufferedReader(imageFile, Charset.defaultCharset());
        assertEquals(TEST_CONTENT, reader.readLine());

    }

    @Test
    public void appendImageToReceipt_ShouldSaveImageFile_andUpdateReceipt() throws Exception {
        final byte[] content = TEST_CONTENT.getBytes();
        final UserAccount testUser = UserAccount.createTestUser("user23", "123@email.com");
        final Receipt receipt = Receipt.createReceipt(testUser);
        receipt.setId("receipt123");
        receipt.getImages().add(new ReceiptImage()); //first image

        when(receiptImageRepositoryMock.save(isA(ReceiptImage.class))).thenAnswer( new Answer<ReceiptImage>() {
            @Override
            public ReceiptImage answer(InvocationOnMock invocation) throws Throwable {
                final ReceiptImage image = (ReceiptImage)invocation.getArguments()[0];
                image.setId("image456");
                return image;
            }

        });

        when(receiptRepositoryMock.save(isA(Receipt.class))).thenAnswer( new Answer<Receipt>() {
            @Override
            public Receipt answer(InvocationOnMock invocation) throws Throwable {
                return (Receipt)invocation.getArguments()[0];
            }
        });

        when(fileMock.getBytes()).thenReturn(content);

        final ReceiptImage image = serviceToTest.appendImageToReceipt(receipt, fileMock);

        assertEquals("receipt123", receipt.getId());
        assertEquals(testUser, receipt.getUser());

        assertEquals("image456", image.getId());
        assertEquals(receipt, image.getReceipt());
        assertEquals(ProcessStatusType.UPLOADED, image.getStatus());

        // check file exists and content is the same as test content
        final Path imageFile = fileSystemService.getReceiptImageSubFolder(testUser.getId()).resolve(image.getFileName());
        assertTrue(Files.exists(imageFile));
        BufferedReader reader = Files.newBufferedReader(imageFile, Charset.defaultCharset());
        assertEquals(TEST_CONTENT, reader.readLine());

    }

    @Test
    public void appendImageToReceipt_ShouldSaveImageBase64String_andUpdateReceipt() throws Exception {
        final byte[] content = TEST_CONTENT.getBytes();
        final String base64String = Base64.getEncoder().encodeToString(content);
        final UserAccount testUser = UserAccount.createTestUser("user23", "123@email.com");
        final Receipt receipt = Receipt.createReceipt(testUser);
        receipt.setId("receipt123");
        receipt.getImages().add(new ReceiptImage()); //first image

        when(receiptImageRepositoryMock.save(isA(ReceiptImage.class))).thenAnswer( new Answer<ReceiptImage>() {
            @Override
            public ReceiptImage answer(InvocationOnMock invocation) throws Throwable {
                final ReceiptImage image = (ReceiptImage)invocation.getArguments()[0];
                image.setId("image456");
                return image;
            }

        });

        when(receiptRepositoryMock.save(isA(Receipt.class))).thenAnswer( new Answer<Receipt>() {
            @Override
            public Receipt answer(InvocationOnMock invocation) throws Throwable {
                return (Receipt)invocation.getArguments()[0];
            }
        });

        final ReceiptImage image = serviceToTest.appendImageToReceipt(receipt, base64String);

        assertEquals("receipt123", receipt.getId());
        assertEquals(testUser, receipt.getUser());

        assertEquals("image456", image.getId());
        assertEquals(receipt, image.getReceipt());
        assertEquals(ProcessStatusType.UPLOADED, image.getStatus());

        // check file exists and content is the same as test content
        final Path imageFile = fileSystemService.getReceiptImageSubFolder(testUser.getId()).resolve(image.getFileName());
        assertTrue(Files.exists(imageFile));
        BufferedReader reader = Files.newBufferedReader(imageFile, Charset.defaultCharset());
        assertEquals(TEST_CONTENT, reader.readLine());

    }

    @Test
    public void getLatestReceiptParserResult_ShouldGenerateParserResult_IfNotInDatabase() throws Exception {
        final UserAccount testUser = UserAccount.createTestUser("user23", "123@email.com");
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
        receipt.setImages(images);

        final List<String> ocrTextList = Arrays.asList("ocr result1","ocr result2");

        final List<Item> items = new ArrayList<>();
        items.add(new Item("milk", "10.99", "1.99", "4.00", "food"));
        items.add(new Item("eggs", "4.99", "4.99", "12", "food"));

        final StoreChain chain = StoreChain.builder().code("rcss").build();
        final StoreBranch branch = StoreBranch.builder().branchName("Calgary Trail").build();
        final Map<ReceiptField, ValueLine> fieldToValueLine = new HashMap<ReceiptField, ValueLine>();
        fieldToValueLine.put(ReceiptField.Total, ValueLine.builder().line(-1).value("15.00").build());
        final ParsedReceipt receiptDebug = ParsedReceipt.builder().chain(chain).branch(branch).fieldToValueMap(fieldToValueLine).items(items).build();

        when(receiptDataRepositoryMock.findFirstByReceiptOrderByCreatedTimeDesc(eq(receipt))).thenReturn(null);
        when(receiptImageRepositoryMock.findByReceiptOrderByCreatedTime(eq(receipt))).thenReturn(images);
        when(simpleParser.parseOCRResults(eq(ocrTextList))).thenReturn(receiptDebug);
        when(receiptDataRepositoryMock.save(isA(ReceiptData.class))).thenAnswer( new Answer<ReceiptData>() {
            @Override
            public ReceiptData answer(InvocationOnMock invocation) throws Throwable {
                return (ReceiptData)invocation.getArguments()[0];
            }
        });

        final ReceiptData data = serviceToTest.getLatestReceiptParserResult(receipt);
        assertEquals("rcss", data.getChainCode());
        assertEquals("Calgary Trail", data.getBranchName());
        assertEquals(2, data.getItems().size());
        assertEquals("milk", data.getItems().get(0).getParsedName());
        assertEquals("10.99", data.getItems().get(0).getParsedPrice());
        assertEquals("eggs", data.getItems().get(1).getParsedName());
        assertEquals("4.99", data.getItems().get(1).getParsedPrice());

    }
}
