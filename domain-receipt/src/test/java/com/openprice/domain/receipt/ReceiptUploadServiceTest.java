package com.openprice.domain.receipt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.ByteStreams;
import com.openprice.domain.account.user.UserAccount;
import com.openprice.file.FileFolderSettings;
import com.openprice.file.FileSystemService;

@RunWith(MockitoJUnitRunner.class)
public class ReceiptUploadServiceTest {

    static final String USER_ID = "user123";
    static final String USER_EMAIL = "john.doe@email.com";
    static final String TEST_OCR = "Superstore";

    @Mock
    ReceiptRepository receiptRepositoryMock;

    @Mock
    ReceiptImageRepository receiptImageRepositoryMock;

    @Mock
    MultipartFile fileMock;

    @Mock
    MultipartFile ocrMock;

    FileSystemService fileSystemService;

    ReceiptUploadService serviceToTest;


    @Before
    public void setup() throws Exception {
        fileSystemService = new FileSystemService(new FileFolderSettings());
        serviceToTest = new ReceiptUploadService(receiptRepositoryMock,
                                                 receiptImageRepositoryMock,
                                                 fileSystemService);
    }

    @Test
    public void uploadImageBase64StringForNewReceipt_ShouldSaveImage_andCreateReceipt() throws Exception {
        final byte[] content = getTestImageContent();
        final String base64 = new String(Base64.getEncoder().encode(content));
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

        final ReceiptImage image = serviceToTest.uploadImageForNewReceipt(testUser, base64);

        // receipt image has been saved
        assertEquals("image123", image.getId());
        assertEquals(ProcessStatusType.UPLOADED, image.getStatus());

        // receipt has correct data
        assertEquals("receipt123", image.getReceipt().getId());
        assertEquals(testUser, image.getReceipt().getUser());
        assertEquals(ReceiptStatusType.WAIT_FOR_RESULT, image.getReceipt().getStatus());
        assertNotNull(image.getReceipt().getReceiptDate());
        verifySavedImage(testUser.getId(), image.getFileName(), content);

   }

    @Test
    public void uploadImageFileForNewReceipt_ShouldSaveImage_andCreateReceipt() throws Exception {
        final byte[] content = getTestImageContent();
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
        assertEquals(ReceiptStatusType.WAIT_FOR_RESULT, image.getReceipt().getStatus());
        assertNotNull(image.getReceipt().getReceiptDate());
        verifySavedImage(testUser.getId(), image.getFileName(), content);

    }

    @Test
    public void appendImageFileToReceipt_ShouldSaveImage_andUpdateReceipt() throws Exception {
        final byte[] content = getTestImageContent();
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
        verifySavedImage(testUser.getId(), image.getFileName(), content);

    }

    @Test
    public void appendImageBase64StringToReceipt_ShouldSaveImage_andUpdateReceipt() throws Exception {
        final byte[] content = getTestImageContent();
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
        verifySavedImage(testUser.getId(), image.getFileName(), content);

    }

    private UserAccount getTestUserAccount() {
        return UserAccount.testObjectBuilder()
                          .id(USER_ID)
                          .email(USER_EMAIL)
                          .build();
    }

    private byte[] getTestImageContent() throws Exception {
        Resource imageResource = new ClassPathResource("BostonPizza.JPG");
        return ByteStreams.toByteArray(imageResource.getInputStream());
    }

    private void verifySavedImage(final String userId, final String fileName, final byte[] content) throws Exception {
        final Path imageFile = fileSystemService.getReceiptImageSubFolder(userId).resolve(fileName);
        assertTrue(Files.exists(imageFile));
        byte[] data = Files.readAllBytes(imageFile);
        assertEquals(content.length, data.length);
    }
}
