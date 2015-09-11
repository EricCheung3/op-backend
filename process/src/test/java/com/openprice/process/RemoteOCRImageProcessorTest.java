package com.openprice.process;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

import com.openprice.common.api.ImageProcessResult;
import com.openprice.domain.receipt.ProcessLog;
import com.openprice.domain.receipt.ProcessLogRepository;
import com.openprice.domain.receipt.ProcessStatusType;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptImageRepository;

@RunWith(MockitoJUnitRunner.class)
public class RemoteOCRImageProcessorTest {
    @Mock
    ProcessLogRepository processLogRepositoryMock;

    @Mock
    ReceiptImageRepository receiptImageRepositoryMock;

    @Mock
    RestTemplate restTemplateMock;

    RemoteOCRImageProcessor processorToTest;

    @Before
    public void setup() throws Exception {
        processorToTest = new RemoteOCRImageProcessor("test_ocr_server", "http://test",
                processLogRepositoryMock, receiptImageRepositoryMock, restTemplateMock);
    }

    @Test
    public void processImage_ShouldSaveProcessLog_ReceiptImage_WithOCRResult() {
        final String IMAGE_ID = "image001";
        final String TEST_USERNAME = "tester@openprice,com";
        final String TEST_USERID = "user001";
        final String TEST_FILENAME = "2015_09_09_12_30_10_001.jpg";
        final String TEST_OCR_RESULT = "SuperStore items";

        final ReceiptImage image = new ReceiptImage();
        image.setId(IMAGE_ID);
        image.setFileName(TEST_FILENAME);

        final ProcessItem item = new ProcessItem();
        item.setImage(image);
        item.setUserId(TEST_USERID);
        item.setUsername(TEST_USERNAME);

        final ImageProcessResult mockResult = new ImageProcessResult();
        mockResult.setUsername(TEST_USERNAME);
        mockResult.setFileName(TEST_FILENAME);
        mockResult.setOcrResult(TEST_OCR_RESULT);

        when(restTemplateMock.getForObject(eq("http://test/process/{userId}?fileName={fileName}&username={username}"),
                                           eq(ImageProcessResult.class),
                                           eq(TEST_USERNAME),
                                           eq(TEST_FILENAME))).thenReturn(mockResult);
        when(receiptImageRepositoryMock.findOne(eq(IMAGE_ID))).thenReturn(image);

        processorToTest.processImage(item);

        {
            ArgumentCaptor<ProcessLog> argument = ArgumentCaptor.forClass(ProcessLog.class);
            verify(processLogRepositoryMock, times(1)).save(argument.capture());
            ProcessLog processLog = argument.getValue();
            assertEquals(IMAGE_ID, processLog.getImageId());
            assertEquals(TEST_USERNAME, processLog.getUsername());
            assertEquals(TEST_USERNAME, processLog.getUsername());
            assertEquals("test_ocr_server", processLog.getServerName());
            assertTrue(processLog.getStartTime() > 0);
        }

        {
            ArgumentCaptor<ReceiptImage> argument = ArgumentCaptor.forClass(ReceiptImage.class);
            verify(receiptImageRepositoryMock, times(1)).save(argument.capture());
            assertEquals(IMAGE_ID, argument.getValue().getId());
            assertEquals(ProcessStatusType.SCANNED, argument.getValue().getStatus());
            assertEquals(TEST_OCR_RESULT, argument.getValue().getOcrResult());
        }
    }

}
