package com.openprice.process;

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
        final Receipt receipt = Receipt.createReceipt(testUser);
        final ReceiptImage image = receipt.createImage();
        image.setId(IMAGE_ID);
        image.setFileName(TEST_FILENAME);
        return image;
    }

}
