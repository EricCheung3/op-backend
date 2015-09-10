package com.openprice.process;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.openprice.domain.receipt.ProcessLog;
import com.openprice.domain.receipt.ProcessLogRepository;
import com.openprice.domain.receipt.ProcessStatusType;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptImageRepository;

@RunWith(MockitoJUnitRunner.class)
public class StaticResultImageProcessorTest {

    @Mock
    ProcessLogRepository processLogRepositoryMock;

    @Mock
    ReceiptImageRepository receiptImageRepositoryMock;

    StaticResultImageProcessor processorToTest;

    @Before
    public void setup() throws Exception {
        processorToTest = new StaticResultImageProcessor(processLogRepositoryMock, receiptImageRepositoryMock);
    }

    @Test
    public void processImage_ShouldSaveProcessLog_ReceiptImage_WithStaticResult() {
        final String IMAGE_ID = "image001";
        final ReceiptImage image = new ReceiptImage();
        image.setId(IMAGE_ID);

        final ProcessItem item = new ProcessItem();
        item.setImage(image);
        item.setUsername("tester");

        when(receiptImageRepositoryMock.findOne(eq(IMAGE_ID))).thenReturn(image);

        processorToTest.processImage(item);

        {
            ArgumentCaptor<ProcessLog> argument = ArgumentCaptor.forClass(ProcessLog.class);
            verify(processLogRepositoryMock, times(1)).save(argument.capture());
            assertEquals(IMAGE_ID, argument.getValue().getImageId());
            assertEquals("static", argument.getValue().getServerName());
        }

        {
            ArgumentCaptor<ReceiptImage> argument = ArgumentCaptor.forClass(ReceiptImage.class);
            verify(receiptImageRepositoryMock, times(1)).save(argument.capture());
            assertEquals(IMAGE_ID, argument.getValue().getId());
            assertEquals(ProcessStatusType.SCANNED, argument.getValue().getStatus());
            // TODO verify parser result items. wait for the parser code finish
        }
    }
}
