package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateStringFeaturesTest {

    @Test
    public void test1() {
        final DateStringFeatures features = DateStringFeatures.fromString("a    df  10003");
        assertTrue(features.isContainsWideSpace());
        assertEquals("a", features.getBeforeWideSpace());
        assertTrue(!features.isBeforeWideSpaceEndsWithDigit());
        assertEquals("df  10003", features.getAfterWideSpace());
        assertTrue(!features.isAfterWideSpaceStartsWithDigit());

    }

    @Test
    public void test2() {
        final DateStringFeatures features = DateStringFeatures.fromString("6                                     3 /05/ 16 ");
        assertTrue(features.isContainsWideSpace());
        assertEquals("6", features.getBeforeWideSpace());
        assertTrue(features.isBeforeWideSpaceEndsWithDigit());
        assertEquals("3 /05/ 16 ", features.getAfterWideSpace());
        assertTrue(features.isAfterWideSpaceStartsWithDigit());
    }
}
