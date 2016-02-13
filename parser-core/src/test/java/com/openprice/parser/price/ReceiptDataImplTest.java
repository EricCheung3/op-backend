package com.openprice.parser.price;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.openprice.parser.ReceiptDataImpl;
import com.openprice.parser.api.ReceiptData;
import com.openprice.parser.api.ReceiptLine;

public class ReceiptDataImplTest {

    @Test
    public void getOriginalLinesImmutableListEqualsList() throws Exception{
        final List<String> testData = new ArrayList<>();
        for (int i=0; i<10; i++) {
            testData.add("test receipt line " + i);
        }
        ReceiptData receipt = ReceiptDataImpl.fromContentLines(testData);
        final ImmutableList<String> immutable=receipt.getOriginalLines();
        assertEquals(testData, immutable);
        for(int i=0;i<testData.size();i++)
            assertEquals(testData.get(i), immutable.get(i));
    }


    @Test
    public void fromContentLines_ShouldCreateReceiptData() throws Exception {
        final List<String> testData = new ArrayList<>();
        for (int i=0; i<10; i++) {
            testData.add("test receipt line " + i);
        }
        ReceiptData receipt = ReceiptDataImpl.fromContentLines(testData);
        assertEquals(10, receipt.getOriginalLines().size());
    }

    @Test
    public void fromOCRResults_ShouldCreateReceiptData() throws Exception {
        final List<String> testData = new ArrayList<>();
        final StringBuilder ocrText = new StringBuilder();
        for (int i=0; i<10; i++) {
            ocrText.append("test receipt line ");
            ocrText.append(i);
            ocrText.append("\n");
        }
        testData.add(ocrText.toString());

        ReceiptData receipt = ReceiptDataImpl.fromOCRResults(testData);
        assertEquals(10, receipt.getOriginalLines().size());

    }

    @Test
    public void getTopBottomChainMatchingLines_ShouldReturn20Lines_When50Lines() throws Exception {
        final List<String> testData = new ArrayList<>();
        for (int i=0; i<50; i++) {
            testData.add("test receipt line " + i);
        }
        ReceiptData receipt = ReceiptDataImpl.fromContentLines(testData);
        final List<ReceiptLine> result = receipt.getTopBottomChainMatchingLines();
        assertEquals(20, result.size());
    }

    @Test
    public void getTopBottomChainMatchingLines_ShouldReturn20Lines_When21Lines() throws Exception {
        final List<String> testData = new ArrayList<>();
        for (int i=0; i<21; i++) {
            testData.add("test receipt line " + i);
        }
        ReceiptData receipt = ReceiptDataImpl.fromContentLines(testData);
        final List<ReceiptLine> result = receipt.getTopBottomChainMatchingLines();
        assertEquals(20, result.size());
    }

    @Test
    public void getTopBottomChainMatchingLines_ShouldReturnAllLines_IfLessThan10Lines() throws Exception {
        final List<String> testData = new ArrayList<>();
        for (int i=0; i<9; i++) {
            testData.add("test receipt line " + i);
        }
        ReceiptData receipt = ReceiptDataImpl.fromContentLines(testData);
        final List<ReceiptLine> result = receipt.getTopBottomChainMatchingLines();
        assertEquals(9, result.size());
    }

    @Test
    public void getTopBottomChainMatchingLines_ShouldReturnAllLines_If15Lines() throws Exception {
        final List<String> testData = new ArrayList<>();
        for (int i=0; i<15; i++) {
            testData.add("test receipt line " + i);
        }
        ReceiptData receipt = ReceiptDataImpl.fromContentLines(testData);
        final List<ReceiptLine> result = receipt.getTopBottomChainMatchingLines();
        assertEquals(15, result.size());
    }

    @Test
    public void getTopBottomChainMatchingLines_ShouldReturnAllLines_If20Lines() throws Exception {
        final List<String> testData = new ArrayList<>();
        for (int i=0; i<20; i++) {
            testData.add("test receipt line " + i);
        }
        ReceiptData receipt = ReceiptDataImpl.fromContentLines(testData);
        final List<ReceiptLine> result = receipt.getTopBottomChainMatchingLines();
        assertEquals(20, result.size());
    }

}
