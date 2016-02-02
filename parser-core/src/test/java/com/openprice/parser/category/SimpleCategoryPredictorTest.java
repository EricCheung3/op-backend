package com.openprice.parser.category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * test on RCSS and Safeway using their productCategory.txt file
 */
@Slf4j
public class SimpleCategoryPredictorTest {

    private SimpleCategoryPredictor simplePredictorFromConfig;

    @Before
    public void init() throws Exception{
        simplePredictorFromConfig=SimpleCategoryPredictor.fromConfig();
    }

    @Test
    public void test1() throws Exception{
        final List<String> lines=new ArrayList<String>();
        lines.add("babyfood:gerber,");
        lines.add("apparel:lacepencil, sportwear");
        lines.add("babyitems:snug nb,kids, snug dry ");
        final SimpleCategoryPredictor simple=new SimpleCategoryPredictor(lines);
//        log.debug(simple.toString());
        assertEquals(3, simple.getCategoryToNames().size());

        assertEquals(2, simple.getCategoryToNames().get("babyfood").size());
        assertTrue(simple.getCategoryToNames().get("babyfood").contains("babyfood"));
        assertTrue(simple.getCategoryToNames().get("babyfood").contains("gerber"));

        assertEquals(3, simple.getCategoryToNames().get("apparel").size());
        assertTrue(simple.getCategoryToNames().get("apparel").contains("lacepencil"));
        assertTrue(simple.getCategoryToNames().get("apparel").contains("sportwear"));
        assertTrue(simple.getCategoryToNames().get("apparel").contains("apparel"));

        assertEquals(4, simple.getCategoryToNames().get("babyitems").size());
        assertTrue(simple.getCategoryToNames().get("babyitems").contains("snug nb"));
        assertTrue(simple.getCategoryToNames().get("babyitems").contains("kids"));
        assertTrue(simple.getCategoryToNames().get("babyitems").contains("snug dry"));
        assertTrue(simple.getCategoryToNames().get("babyitems").contains("babyitems"));
    }

    @Test
    public void configNotNull() throws Exception{
        final SimpleCategoryPredictor simple=SimpleCategoryPredictor.fromConfig();
        assertNotNull(simple);
    }

    @Test
    public void configTestCatetegoryNames() throws Exception{
        assertEquals("babyfood", simplePredictorFromConfig.mostMatchingCategory("babyfood"));
        assertEquals("babyitems", simplePredictorFromConfig.mostMatchingCategory("babyitems"));
    }

    @Test
    public void configTestFruits() throws Exception{
        assertEquals("fruit", simplePredictorFromConfig.mostMatchingCategory("app"));
        assertEquals("fruit", simplePredictorFromConfig.mostMatchingCategory("appl"));
        assertEquals("fruit", simplePredictorFromConfig.mostMatchingCategory("apple"));

        assertEquals("fruit", simplePredictorFromConfig.mostMatchingCategory("peach"));
        assertEquals("fruit", simplePredictorFromConfig.mostMatchingCategory("grap"));
    }

    @Test
    public void configTestDeli() throws Exception{
        assertEquals("deli", simplePredictorFromConfig.mostMatchingCategory("smokehouse"));
        assertEquals("deli", simplePredictorFromConfig.mostMatchingCategory("smokeho"));

        //TODO: it probably matched shower by mistake
//        assertEquals("deli", simplePredictorFromConfig.mostMatchingCategory("smoke"));

        assertEquals("deli", simplePredictorFromConfig.mostMatchingCategory("ham"));
        assertEquals("deli", simplePredictorFromConfig.mostMatchingCategory("ha"));

        assertTrue(simplePredictorFromConfig.getCategoryToNames().keySet().contains("cleaningsupplies"));
        assertTrue(simplePredictorFromConfig.getCategoryToNames().get("cleaningsupplies").contains("tide"));
        assertEquals("cleaningsupplies", simplePredictorFromConfig.mostMatchingCategoryForDebug("tide smpl lq hec"));
    }

}
