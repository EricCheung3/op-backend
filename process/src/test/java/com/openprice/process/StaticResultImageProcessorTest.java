package com.openprice.process;

import static org.junit.Assert.assertEquals;
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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.openprice.domain.receipt.OcrProcessLog;
import com.openprice.domain.receipt.ProcessStatusType;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImage;

@RunWith(MockitoJUnitRunner.class)
public class StaticResultImageProcessorTest extends AbstractProcessorTest {

    StaticResultImageProcessor processorToTest;

    @Before
    public void setup() throws Exception {
        processorToTest = new StaticResultImageProcessor(fileSystemService,
                                                         receiptParsingServiceMock,
                                                         processLogRepositoryMock,
                                                         receiptImageRepositoryMock,
                                                         userAccountRepositoryMock,
                                                         0);
    }

    @Test
    public void processImage_ShouldSaveProcessLog_ReceiptImage_WithStaticResult() {
        final ReceiptImage image = getTestReceiptImage();
        final ProcessItem item = new ProcessItem(IMAGE_ID, TEST_USER_ID, TEST_USER_ID, new Date());

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
            assertEquals(IMAGE_ID, argument.getValue().getImageId());
            assertEquals("Static", argument.getValue().getServerName());
        }

        {
            ArgumentCaptor<ReceiptImage> argument = ArgumentCaptor.forClass(ReceiptImage.class);
            verify(receiptImageRepositoryMock, times(1)).save(argument.capture());
            assertEquals(IMAGE_ID, argument.getValue().getId());
            assertEquals(ProcessStatusType.SCANNED, argument.getValue().getStatus());
        }
    }
}
