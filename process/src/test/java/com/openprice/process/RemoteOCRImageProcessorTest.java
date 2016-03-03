package com.openprice.process;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.openprice.domain.receipt.OcrProcessLog;
import com.openprice.domain.receipt.ProcessStatusType;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.ocr.api.ImageProcessResult;
import com.openprice.ocr.client.OcrService;

@RunWith(MockitoJUnitRunner.class)
public class RemoteOCRImageProcessorTest extends AbstractProcessorTest {

    static final String TEST_SERVER_NAME = "test_ocr_server";
    static final String TEST_URL = "http://test_server/processor";

    @Mock
    OcrService ocrServiceMock;

    RemoteOCRImageProcessor processorToTest;

    @Before
    public void setup() throws Exception {
        processorToTest = new RemoteOCRImageProcessor(TEST_SERVER_NAME,
                                                      fileSystemService,
                                                      ocrServiceMock,
                                                      receiptParsingServiceMock,
                                                      processLogRepositoryMock,
                                                      receiptImageRepositoryMock,
                                                      userAccountRepositoryMock);
    }

    @Test
    public void processImage_ShouldSaveProcessLog_ReceiptImage_WithSuccessResult() {
        final ReceiptImage image = getTestReceiptImage();
        final ProcessItem item = new ProcessItem(IMAGE_ID, TEST_USER_ID, TEST_USER_ID, new Date());

        final ImageProcessResult mockResult = new ImageProcessResult(true, TEST_OCR_RESULT, null);

        when(ocrServiceMock.processUserReceiptImage(anyString())).thenReturn(mockResult);
        when(receiptImageRepositoryMock.findOne(eq(IMAGE_ID))).thenReturn(image);
        when(receiptParsingServiceMock.parseScannedReceiptImages(isA(Receipt.class))).thenAnswer(new Answer<Receipt>() {
            @Override
            public Receipt answer(InvocationOnMock invocation) throws Throwable {
                return (Receipt) invocation.getArguments()[0];
            }
        });

        processorToTest.processImage(item);

        {
            ArgumentCaptor<OcrProcessLog> argument = ArgumentCaptor.forClass(OcrProcessLog.class);
            verify(processLogRepositoryMock, times(1)).save(argument.capture());
            OcrProcessLog processLogRecord = argument.getValue();
            assertEquals(IMAGE_ID, processLogRecord.getImageId());
            assertEquals(TEST_OCR_RESULT, processLogRecord.getOcrResult());
            assertEquals(TEST_SERVER_NAME, processLogRecord.getServerName());
            assertTrue(processLogRecord.getStartTime() > 0);
        }

        {
            ArgumentCaptor<ReceiptImage> argument = ArgumentCaptor.forClass(ReceiptImage.class);
            verify(receiptImageRepositoryMock, times(1)).save(argument.capture());
            ReceiptImage imageRecord = argument.getValue();
            assertEquals(IMAGE_ID, imageRecord.getId());
            assertEquals(ProcessStatusType.SCANNED, imageRecord.getStatus());
            assertEquals(TEST_OCR_RESULT, imageRecord.getOcrResult());
        }
    }

    @Test
    public void processImage_ShouldSaveProcessLog_ReceiptImage_WithErrorResult() {
        final ReceiptImage image = getTestReceiptImage();
        final ProcessItem item = new ProcessItem(IMAGE_ID, TEST_USER_ID, TEST_USER_ID, new Date());

        final ImageProcessResult mockResult = new ImageProcessResult(false, null, TEST_OCR_ERROR);

        when(ocrServiceMock.processUserReceiptImage(anyString())).thenReturn(mockResult);
        when(receiptImageRepositoryMock.findOne(eq(IMAGE_ID))).thenReturn(image);
        when(receiptParsingServiceMock.parseScannedReceiptImages(isA(Receipt.class))).thenAnswer(new Answer<Receipt>() {
            @Override
            public Receipt answer(InvocationOnMock invocation) throws Throwable {
                return (Receipt) invocation.getArguments()[0];
            }
        });

        processorToTest.processImage(item);

        {
            ArgumentCaptor<OcrProcessLog> argument = ArgumentCaptor.forClass(OcrProcessLog.class);
            verify(processLogRepositoryMock, times(1)).save(argument.capture());
            OcrProcessLog processLogRecord = argument.getValue();
            assertEquals(IMAGE_ID, processLogRecord.getImageId());
            assertEquals(TEST_SERVER_NAME, processLogRecord.getServerName());
            assertEquals(TEST_OCR_ERROR, processLogRecord.getErrorMessage());
            assertTrue(processLogRecord.getStartTime() > 0);
        }

        {
            ArgumentCaptor<ReceiptImage> argument = ArgumentCaptor.forClass(ReceiptImage.class);
            verify(receiptImageRepositoryMock, times(1)).save(argument.capture());
            ReceiptImage imageRecord = argument.getValue();
            assertEquals(IMAGE_ID, imageRecord.getId());
            assertEquals(ProcessStatusType.SCANNED_ERR, imageRecord.getStatus());
            assertNull(imageRecord.getOcrResult());
        }
    }
}
