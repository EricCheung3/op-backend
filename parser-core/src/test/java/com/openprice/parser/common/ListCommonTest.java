package com.openprice.parser.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ListCommonTest{

    @Test
    public void matchAHeaderInListNo(){
       final List<String> list = new ArrayList<String>();
       list.add("abdfsdsad");
       assertTrue(!ListCommon.matchAHeaderInList(list, "Total", 0.6));
    }

    @Test
    public void matchAHeaderInListYes(){
       final List<String> list = new ArrayList<String>();
       list.add("total");
       assertTrue(ListCommon.matchAHeaderInList(list, "Totale", 0.6));
    }

    @Test
    public void oneStringTest4(){
        List<String> list=new ArrayList<String>();
        final String result=ListCommon.oneString(list, ",");
        assertEquals("", result);
    }

    @Test
    public void oneStringTest3(){
        List<String> list=new ArrayList<String>();
        list.add("ABC");
        final String result=ListCommon.oneString(list, ",");
        assertEquals("ABC", result);
    }

    @Test
    public void oneStringTest2(){
        List<String> list=new ArrayList<String>();
        for(int i=1;i<4;i++){
            list.add(i+"");
        }
        final String result=ListCommon.oneString(list, "\n");
        assertEquals("1\n2\n3", result);
    }

    @Test
    public void oneStringTest1(){
        List<String> list=new ArrayList<String>();
        for(int i=1;i<4;i++){
            list.add(i+"");
        }
        final String result=ListCommon.oneString(list, ",");
        assertEquals("1,2,3", result);
    }

    // public static void addToList(final List<String> list, final String s){
    //        if(s==null || s.isEmpty()) return;
    //        list.add(s);
    //   }

    //   /*
    //       sum up values in List
    //   */
    //   public static double sumList(final List<ValueLine> rList, WarningLogger wLogger){
    //       double total=0.0;
    //       for(int i=0; i<rList.size();i++){
    //          try{
    //              total+=Double.valueOf(StringCommon.formatPrice(rList.get(i).value()));
    //          }catch(Exception e){
    //               wLogger.addWarning(rList.get(i).line(), e.getMessage(), "Make this value numerical.");
    //          }
    //       }
    //       return total;
    //   }




}