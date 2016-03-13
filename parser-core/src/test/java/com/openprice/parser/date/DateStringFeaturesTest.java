package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    @Test
    public void manyWideSpaces() {
        final String str = "Term Tran       Store         Oper               02/ 27/ 15";
        final DateStringFeatures features = DateStringFeatures.fromString(str);
        log.debug("features="+features.toString());
    }

    @Test
    public void manyWideSpaces2() {
        final String str = "Term Tran       Store         Oper 1               0/ 27/ 15";
        final DateStringFeatures features = DateStringFeatures.fromString(str);
        log.debug("features="+features.toString());

    }

    @Test
    public void test11() {
        final String str = "sdfa DATE/TIME:             15/09/12 02:29:08";
        final DateStringFeatures features = DateStringFeatures.fromString(str);
        assertTrue(features.isContainsWideSpace());
        assertEquals("sdfa DATE/TIME:", features.getBeforeWideSpace());
        assertTrue(!features.isBeforeWideSpaceEndsWithDigit());
        assertEquals("15/09/12 02:29:08", features.getAfterWideSpace());
        assertTrue(features.isAfterWideSpaceStartsWithDigit());
    }

    @Test
    public void testNoHeadingString() {
        final String str = "             15/09/12 02:29:08";
        final DateStringFeatures features = DateStringFeatures.fromString(str);
        assertTrue(features.isContainsWideSpace());
        assertEquals("15/09/12 02:29:08", features.getBeforeWideSpace());
        assertTrue(features.isBeforeWideSpaceEndsWithDigit());
        assertEquals("", features.getAfterWideSpace());
        assertTrue(!features.isAfterWideSpaceStartsWithDigit());
    }

}
