package com.openprice.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LevenshteinTest{

    @Test
    public void testNonUnicode(){
        final String ascii = "lll";
       final String nonAscii = ascii + "‘";
       final String nonAsciiTurnedToAscii = nonAscii.replaceAll("\\P{Print}", "");
       assertEquals(ascii, nonAsciiTurnedToAscii);
       final double score = Levenshtein.compare(nonAscii, ascii);
       assertTrue(Math.abs(score-3/4.0)<0.001);
    }


    @Test
    public void matchingChars(){
       assertEquals(5, Levenshtein.matchingChars("Total Number Sold ", "Total"));
       assertEquals(1, Levenshtein.matchingChars("ABC", "A"));
       assertEquals(2, Levenshtein.matchingChars("ABC", "AB"));
       assertEquals(0, Levenshtein.matchingChars("", "AB"));
       assertEquals(0, Levenshtein.matchingChars("", ""));
       assertEquals(0, Levenshtein.matchingChars("AB", ""));
    }

    @Test(expected=IllegalArgumentException.class)
    public void mostSimilarInSetTwoWayTestException() throws Exception{
        Set<String> set= new HashSet<String>();
        Levenshtein.mostSimilarInSetTwoWay("ABC", set);
    }
    @Test(expected=IllegalArgumentException.class)
    public void mostSimilarInSetOneWayTestException() throws Exception{
        Set<String> set= new HashSet<String>();
        Levenshtein.mostSimilarInSetOneWay("ABC", set);
    }
    @Test(expected=IllegalArgumentException.class)
    public void mostSimilarInSetLevenshteinTestException() throws Exception{
        Set<String> set= new HashSet<String>();
        Levenshtein.mostSimilarInSetLevenshtein("ABC", set);
    }

    @Test(expected=IllegalArgumentException.class)
    public void mostSimilarInSetScoreTwoWayTestException() throws Exception{
        Set<String> set= new HashSet<String>();
        Levenshtein.mostSimilarScoreInSetTwoWay("ABC", set);
    }
    @Test(expected=IllegalArgumentException.class)
    public void mostSimilarScoreInSetOneWayTestException() throws Exception{
        Set<String> set= new HashSet<String>();
        Levenshtein.mostSimilarScoreInSetOneWay("ABC", set);
    }
    @Test(expected=IllegalArgumentException.class)
    public void mostSimilarScoreInSetLevenshteinTestException() throws Exception{
        Set<String> set= new HashSet<String>();
        Levenshtein.mostSimilarScoreInSetLevenshtein("ABC", set);
    }

    @Test
    public void mostSimilarInSetTestFindItself() throws Exception{
        Set<String> set= new HashSet<String>();
        set.add("ABC");
        assertEquals("ABC", Levenshtein.mostSimilarInSetTwoWay("ABC", set));
    }

    //TODO: find longer one?
//    @Test
//    public void mostSimilarInSetTestFindABNotA() throws Exception{
//        Set<String> set= new HashSet<String>();
//        set.add("AB");
//        set.add("A");
//        assertEquals("AB", Levenshtein.mostSimilarInSet("ABC", set));
//    }

    @Test
    public void mostSimilarInSetTestFindANotD() throws Exception{
        Set<String> set= new HashSet<String>();
        set.add("A");
        set.add("D");
        assertEquals("A", Levenshtein.mostSimilarInSetTwoWay("ABC", set));
    }

    @Test
    public void test1(){
        String s1="abc";
        String s2="abcd";
        String s3="abcde";
        assertTrue(Levenshtein.compare(s1, s2)>Levenshtein.compare(s1, s3));
    }

    @Test
    public void test2(){
        String s1="abc";
        String s2="";
        assertTrue(Levenshtein.compare(s1, s2)==0);

        String s3="";
        assertTrue(Levenshtein.compare(s2,s3)==1);
    }

    public void test3(){
        String s1="abc";
        String s2="abcd";
        String s3="abcde";

        assertTrue( Levenshtein.compare(s1, 3, s2, 4)==1);
        assertTrue( Levenshtein.compare(s1, 3, s3, 5)==2);
        assertTrue( Levenshtein.compare(s2, 4, s3, 5)==1);
    }

    public void test4(){
        log.debug(Levenshtein.compare("1234567890", "1234567980")+"");
        log.debug(Levenshtein.compare("472010", "47/2010")+"");
        log.debug(Levenshtein.compare("47/2010", "472011")+"");
        log.debug(Levenshtein.compare("47/2010", "AB.CDEF")+"");
        log.debug(Levenshtein.compare("4B.CDEFG", "47/2010")+"");
        log.debug(Levenshtein.compare("The quick fox jumped", "The fox jumped")+"");
        log.debug(Levenshtein.compare("The quick fox jumped", "The fox")+"");
        log.debug(Levenshtein.compare("kitten", "sitting")+"");
    }

    //    @Test
    //    public void testTwoFiles()throws Exception{
    //        final List<String> lines1=FileCommonT.readLinesFromResourceSkipEmpty("/testFiles/RCSS/CalgaryTrail/", "2015_02_09_13_16_07.jpg.henryHuang.txt");
    //        final List<String> lines2=FileCommonT.readLinesFromResourceSkipEmpty("/testFiles/RCSS/CalgaryTrail/", "2015_02_09_13_16_07.jpg.henryHuang_train.txt");
    //
    //        final String str1=StringCommon.flatten(lines1, "\n");
    //        final String str2=StringCommon.flatten(lines2, "\n");
    //        log.debug("str1=\n"+str1+"\n\n");
    //        log.debug("str2=\n"+str2);
    //
    //
    //        final double score=Levenshtein.compare(str1, str2);
    //        log.debug("str1.length()="+str1.length());
    //        log.debug("str2.length()="+str2.length());
    //        final double distance=(1-score)*Math.max(str1.length(), str2.length());
    //        log.debug("@@@score="+score);
    //        log.debug("@@@distance="+distance);
    //        assertTrue(score>=0 && score<=1);
    //    }


    @Test
    public void testSimple1(){
        final String str1="fire";
        final String str2="water";
        final double score=Levenshtein.compare(str1, str2);
        log.debug("score="+score);

        final double edit=(1-score)*Math.max(str1.length(), str2.length());
        assertTrue(Math.abs(edit-4)<0.00001);
    }

    @Test
    public void testSimple2(){
        final String str1="fire";
        final String str2="water";
        final double score=Levenshtein.compare(str1, str2);
        log.debug("score="+score);

        final double edit=(1-score)*Math.max(str1.length(), str2.length());
        assertTrue(Math.abs(edit-4)<0.00001);
    }

    @Test
    public void matchListTest1() throws Exception{
        final List<String> list=new ArrayList<String>();
        list.add("ab c e");
        assertTrue(Levenshtein.matchList("abce", list, 0.8));
    }

    @Test
    public void matchListTest2() throws Exception{
        final List<String> list=new ArrayList<String>();
        list.add("ab c e");
        assertTrue(!Levenshtein.matchList("abcd", list, 0.8));
    }

    @Test
    public void nonEmptyOverlapMatchTest1() throws Exception{
        final List<String> list1=new ArrayList<String>();
        list1.add("ab c e");
        final List<String> list2=new ArrayList<String>();
        list2.add("ab c e");
        assertTrue(Levenshtein.nonEmptyOverlapMatch(list1, list2, 0.8));
    }

    @Test
    public void nonEmptyOverlapMatchTest2() throws Exception{
        final List<String> list1=new ArrayList<String>();
        list1.add("ab c e");
        final List<String> list2=new ArrayList<String>();
        list2.add("ab c");
        assertTrue(!Levenshtein.nonEmptyOverlapMatch(list1, list2, 0.8));
    }

    @Test
    public void nonEmptyOverlapMatchTest3() throws Exception{
        final List<String> list1=new ArrayList<String>();
        list1.add("ab c e");
        final List<String> list2=new ArrayList<String>();
        list2.add("ab c");
        assertTrue(Levenshtein.nonEmptyOverlapMatch(list1, list2, 0.75));
    }

}
