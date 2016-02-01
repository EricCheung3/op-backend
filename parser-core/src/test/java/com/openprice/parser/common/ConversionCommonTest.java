package com.openprice.parser.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.Test;

public class ConversionCommonTest {

    @Test
    public void lowercaseListTest1() throws Exception{
        final List<String> orig=new ArrayList<String>();
        orig.add("AbC");
        final List<String> lowerList=ConversionCommon.lowercaseAll(orig);
        assertEquals(1,lowerList.size());
        assertTrue(lowerList.contains("abc"));
    }

    @Test
    public void lowercaseListTest2() throws Exception{
        final List<String> orig=new ArrayList<String>();
        final List<String> lowerList=ConversionCommon.lowercaseAll(orig);
        assertEquals(0,lowerList.size());
    }

    @Test
    public void lowercaseListTest3() throws Exception{
        final List<String> orig=new ArrayList<String>();
        orig.add("AbC");
        orig.add("d ef G");
        final List<String> lowerList=ConversionCommon.lowercaseAll(orig);
        assertEquals(2,lowerList.size());
        assertTrue(lowerList.contains("abc"));
        assertTrue(lowerList.contains("d ef g"));
    }

    @Test
    public void test1() throws Exception{
        final Map<String, Integer> orig=new HashMap<String, Integer>();
        orig.put("A", 1);
        orig.put("C", 2);
        orig.put("B", 3);
        final List<Map.Entry<String, Integer>> sorted=ConversionCommon.sortByValueDescending(orig);
        final List<String> truthKey = Arrays.asList("B","C","A");
        final List<Integer> truthValue = Arrays.asList(3,2,1);
        IntStream.range(0,3)
        .forEach(i->{
            assertEquals(truthKey.get(i), sorted.get(i).getKey());
            assertEquals(truthValue.get(i), sorted.get(i).getValue());
        });
    }

    @Test
    public void twoFieldLineToMapTest1(){
        final List<String> lines=new ArrayList<>();
        lines.add("A,a");
        final Map<String, String> map=ConversionCommon.twoFieldLineToMapFirstIsKey(lines, ",");
        assertEquals(1, map.size());
        assertTrue(map.containsKey("A"));
        assertEquals("a", map.get("A"));
    }

    @Test
    public void removeEmptyLinesTest() throws Exception{
        final List<String> orig=new ArrayList<String>();
        orig.add("AB");
        orig.add("CD");
        orig.add("");
        final List<String> shorterList=ConversionCommon.removeEmptyLines(orig);
        assertEquals(2, shorterList.size());
        assertEquals("AB", shorterList.get(0));
        assertEquals("CD", shorterList.get(1));
    }

    @Test
    public void removeCommentStringsTest() throws Exception{
        final List<String> orig=new ArrayList<String>();
        orig.add("AB");
        orig.add("CD");
        orig.add("#");
        orig.add("");
        final List<String> shorterList=ConversionCommon.removeCommentStrings(orig, "#");
        assertEquals(3, shorterList.size());
        assertEquals("AB", shorterList.get(0));
        assertEquals("CD", shorterList.get(1));
        assertEquals("", shorterList.get(2));
    }
}
