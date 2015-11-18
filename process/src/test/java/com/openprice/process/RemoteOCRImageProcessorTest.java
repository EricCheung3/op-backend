package com.openprice.process;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import com.openprice.domain.receipt.ProcessLog;
import com.openprice.domain.receipt.ProcessStatusType;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.ocr.api.ImageProcessResult;

@RunWith(MockitoJUnitRunner.class)
public class RemoteOCRImageProcessorTest extends AbstractProcessorTest {

    @Mock
    RestTemplate restTemplateMock;

    RemoteOCRImageProcessor processorToTest;

    @Before
    public void setup() throws Exception {
        processorToTest = new RemoteOCRImageProcessor("test_ocr_server", "http://test",
                processLogRepositoryMock, receiptImageRepositoryMock, restTemplateMock);
    }

    @Test
    public void processImage_ShouldSaveProcessLog_ReceiptImage_WithSuccessResult() {
        final ReceiptImage image = getTestReceiptImage();
        final ProcessItem item = new ProcessItem();
        item.setImage(image);
        item.setUserId(TEST_USERID);
        item.setUsername(TEST_USERNAME);

        final ImageProcessResult mockResult = new ImageProcessResult();
        mockResult.setSuccess(true);
        mockResult.setOcrResult(TEST_OCR_RESULT);

        when(restTemplateMock.postForObject(eq("http://test/processor"),
                                           anyObject(),
                                           eq(ImageProcessResult.class))).thenReturn(mockResult);
        when(receiptImageRepositoryMock.findOne(eq(IMAGE_ID))).thenReturn(image);

        processorToTest.processImage(item);

        {
            ArgumentCaptor<ProcessLog> argument = ArgumentCaptor.forClass(ProcessLog.class);
            verify(processLogRepositoryMock, times(1)).save(argument.capture());
            ProcessLog processLogRecord = argument.getValue();
            assertEquals(IMAGE_ID, processLogRecord.getImageId());
            assertEquals(TEST_USERNAME, processLogRecord.getUsername());
            assertEquals(TEST_OCR_RESULT, processLogRecord.getOcrResult());
            assertEquals("test_ocr_server", processLogRecord.getServerName());
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
        final ProcessItem item = new ProcessItem();
        item.setImage(image);
        item.setUserId(TEST_USERID);
        item.setUsername(TEST_USERNAME);

        final ImageProcessResult mockResult = new ImageProcessResult();
        mockResult.setSuccess(false);
        mockResult.setErrorMessage(TEST_OCR_ERROR);

        when(restTemplateMock.postForObject(eq("http://test/processor"),
                                           anyObject(),
                                           eq(ImageProcessResult.class))).thenReturn(mockResult);
        when(receiptImageRepositoryMock.findOne(eq(IMAGE_ID))).thenReturn(image);

        processorToTest.processImage(item);

        {
            ArgumentCaptor<ProcessLog> argument = ArgumentCaptor.forClass(ProcessLog.class);
            verify(processLogRepositoryMock, times(1)).save(argument.capture());
            ProcessLog processLogRecord = argument.getValue();
            assertEquals(IMAGE_ID, processLogRecord.getImageId());
            assertEquals(TEST_USERNAME, processLogRecord.getUsername());
            assertEquals("test_ocr_server", processLogRecord.getServerName());
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
