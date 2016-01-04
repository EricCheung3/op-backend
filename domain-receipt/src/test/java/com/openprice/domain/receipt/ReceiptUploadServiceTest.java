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
import java.util.Base64;

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

@RunWith(MockitoJUnitRunner.class)
public class ReceiptUploadServiceTest {

    static final String USER_ID = "user123";
    static final String USER_EMAIL = "john.doe@email.com";
    static final String TEST_CONTENT = "test";
    static final String TEST_OCR = "Superstore";

    @Mock
    ReceiptRepository receiptRepositoryMock;

    @Mock
    ReceiptImageRepository receiptImageRepositoryMock;

    @Mock
    MultipartFile fileMock;

    @Mock
    MultipartFile ocrMock;

    @Mock
    ReceiptService receiptServiceMock;

    FileSystemService fileSystemService;

    ReceiptUploadService serviceToTest;


    @Before
    public void setup() throws Exception {
        fileSystemService = new FileSystemService(new FileFolderSettings());
        serviceToTest = new ReceiptUploadService(receiptServiceMock,
                                                 receiptRepositoryMock,
                                                 receiptImageRepositoryMock,
                                                 fileSystemService);
    }

    @Test
    public void uploadImageForNewReceipt_ShouldSaveImageBase64String_andCreateReceipt() throws Exception {
        final byte[] content = TEST_CONTENT.getBytes();
        final String base64String = Base64.getEncoder().encodeToString(content);
        final UserAccount testUser = getTestUserAccount();

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

        final ReceiptImage image = serviceToTest.uploadImageForNewReceipt(testUser, base64String);

        //receipt image has been saved
        assertEquals("image123", image.getId());
        assertEquals(ProcessStatusType.UPLOADED, image.getStatus());

        //receipt has correct data
        assertEquals("receipt123", image.getReceipt().getId());
        assertEquals(testUser, image.getReceipt().getUser());

        // check file exists and content is the same as test content
        final Path imageFile = fileSystemService.getReceiptImageSubFolder(testUser.getId()).resolve(image.getFileName());
        assertTrue(Files.exists(imageFile));
        BufferedReader reader = Files.newBufferedReader(imageFile, Charset.defaultCharset());
        assertEquals(TEST_CONTENT, reader.readLine());

    }

    @Test
    public void uploadImageForNewReceipt_ShouldSaveImageFile_andCreateReceipt() throws Exception {
        final byte[] content = TEST_CONTENT.getBytes();
        final UserAccount testUser = getTestUserAccount();

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

        final ReceiptImage image = serviceToTest.uploadImageForNewReceipt(testUser, fileMock);

        //receipt image has been saved
        assertEquals("image123", image.getId());
        assertEquals(ProcessStatusType.UPLOADED, image.getStatus());

        //receipt has correct data
        assertEquals("receipt123", image.getReceipt().getId());
        assertEquals(testUser, image.getReceipt().getUser());

        // check file exists and content is the same as test content
        final Path imageFile = fileSystemService.getReceiptImageSubFolder(testUser.getId()).resolve(image.getFileName());
        assertTrue(Files.exists(imageFile));
        BufferedReader reader = Files.newBufferedReader(imageFile, Charset.defaultCharset());
        assertEquals(TEST_CONTENT, reader.readLine());

    }

    @Test
    public void appendImageToReceipt_ShouldSaveImageFile_andUpdateReceipt() throws Exception {
        final byte[] content = TEST_CONTENT.getBytes();
        final UserAccount testUser = getTestUserAccount();
        final Receipt receipt = Receipt.testObjectBuilder().user(testUser).build();

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
                final Receipt receipt = (Receipt)invocation.getArguments()[0];
                receipt.setId("receipt123");
                return receipt;
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
        final UserAccount testUser = getTestUserAccount();
        final Receipt receipt = Receipt.testObjectBuilder().user(testUser).build();

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
                final Receipt receipt = (Receipt)invocation.getArguments()[0];
                receipt.setId("receipt123");
                return receipt;
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
    public void hackloadImageFileAndOcrResultForNewReceipt_ShouldSaveImageFile_andCreateReceiptWithOcrResult()
            throws Exception {
        final UserAccount testUser = getTestUserAccount();

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

        when(fileMock.getBytes()).thenReturn(TEST_CONTENT.getBytes());
        when(ocrMock.getBytes()).thenReturn(TEST_OCR.getBytes());

        final ReceiptImage image = serviceToTest.hackloadImageFileAndOcrResultForNewReceipt(testUser, fileMock, ocrMock);

        //receipt image has been saved
        assertEquals("image123", image.getId());
        assertEquals(ProcessStatusType.SCANNED, image.getStatus());
        assertEquals(TEST_OCR, image.getOcrResult());

        //receipt has correct data
        assertEquals("receipt123", image.getReceipt().getId());
        assertEquals(testUser, image.getReceipt().getUser());

        // check file exists and content is the same as test content
        final Path imageFile = fileSystemService.getReceiptImageSubFolder(testUser.getId()).resolve(image.getFileName());
        assertTrue(Files.exists(imageFile));
        BufferedReader reader = Files.newBufferedReader(imageFile, Charset.defaultCharset());
        assertEquals(TEST_CONTENT, reader.readLine());

    }

    @Test
    public void hackloadOcrResult_ShouldUpdateReceiptOcrResultAndParse()
            throws Exception {
        final UserAccount testUser = getTestUserAccount();
        final Receipt receipt = Receipt.testObjectBuilder().id("receipt123").user(testUser).build();

        final ReceiptImage image = receipt.createImage();

        when(receiptImageRepositoryMock.countByReceipt(eq(receipt))).thenReturn(1l);
        when(receiptImageRepositoryMock.findFirstByReceiptOrderByCreatedTime(eq(receipt))).thenReturn(image);
        when(ocrMock.getBytes()).thenReturn(TEST_OCR.getBytes());

        serviceToTest.hackloadOcrResult(receipt, ocrMock);

        //receipt image has ocr data
        assertEquals(ProcessStatusType.SCANNED, image.getStatus());
        assertEquals(TEST_OCR, image.getOcrResult());
    }

    private UserAccount getTestUserAccount() {
        return UserAccount.testObjectBuilder()
                          .id(USER_ID)
                          .email(USER_EMAIL)
                          .build();
    }
}
