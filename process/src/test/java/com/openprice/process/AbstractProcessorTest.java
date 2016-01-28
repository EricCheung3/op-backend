package com.openprice.process;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.mockito.Mock;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.receipt.OcrProcessLogRepository;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.file.FileFolderSettings;
import com.openprice.file.FileSystemService;

public abstract class AbstractProcessorTest {
    protected final String IMAGE_ID = "image001";
    protected static final String TEST_CONTENT = "test";
    protected final String TEST_USERNAME = "tester@openprice,com";
    protected final String TEST_USER_ID = "user001";
    protected final String TEST_FILENAME = "2015_09_09_12_30_10_001.jpg";
    protected final String TEST_OCR_RESULT = "SuperStore items";
    protected final String TEST_OCR_ERROR = "OCR Error";

    @Mock
    protected OcrProcessLogRepository processLogRepositoryMock;

    @Mock
    protected ReceiptImageRepository receiptImageRepositoryMock;

    protected FileSystemService fileSystemService = new FileSystemService(new FileFolderSettings()); // in memory VFS

    protected ReceiptImage getTestReceiptImage() {
        final UserAccount testUser = UserAccount.testObjectBuilder()
                                                .id(TEST_USER_ID)
                                                .email(TEST_USERNAME)
                                                .build();
        final Receipt receipt = Receipt.testObjectBuilder().user(testUser).build();
        final ReceiptImage image = ReceiptImage.testObjectBuilder()
                                               .id(IMAGE_ID)
                                               .receipt(receipt)
                                               .fileName(TEST_FILENAME)
                                               .build();

        final Path imageFile = fileSystemService.getReceiptImageSubFolder(TEST_USER_ID).resolve(image.getFileName());
        try (final OutputStream out = new BufferedOutputStream(Files.newOutputStream(imageFile, StandardOpenOption.CREATE_NEW)))
        {
            out.write(TEST_CONTENT.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException("System Error! Cannot save image.", ex);
        }

        return image;
    }

}
