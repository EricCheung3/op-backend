package com.openprice.parser.price;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.openprice.parser.ReceiptDataImpl;
import com.openprice.parser.api.ReceiptData;

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

        assertTrue(sizesAreConsistent(receipt));
    }

    public static boolean sizesAreConsistent(final ReceiptData receipt){
        return receipt.getReceiptLines().size() == receipt.getOriginalLines().size()
                &&
                receipt.getOriginalLines().size() ==
                receipt.getReceiptLines()
                .stream()
                .map(r->r.getNumber())
                .max(Comparator.comparing(i->i))
                .get()+1;
    }


    @Test
    public void fromContentLines_ShouldCreateReceiptData() throws Exception {
        final List<String> testData = new ArrayList<>();
        for (int i=0; i<10; i++) {
            testData.add("test receipt line " + i);
        }
        ReceiptData receipt = ReceiptDataImpl.fromContentLines(testData);
        assertEquals(10, receipt.getOriginalLines().size());

        assertTrue(sizesAreConsistent(receipt));
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

        assertTrue(sizesAreConsistent(receipt));
    }

}
