package com.openprice.parser.generic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.openprice.parser.common.StringCommon;

public class GenericChainsTest {


    @Test
    public void noMatchShouldReturnEmptyChainName() throws Exception{
        final List<String> chainLines=new ArrayList<String>();
        chainLines.add("76:Quizino's Subs:Restaurant:QuizinosSubs");
        chainLines.add("81:SAFEWAY,Safeway:Grocery,Health:Safeway");
        chainLines.add("82:Saje West Edmonton:Health:SajeWestEdmonton");
        chainLines.add("83:Save-on-foods:Grocery:SaveOnFoods");
        GenericChains chains=new GenericChains(chainLines);
        final List<String> receipt=new ArrayList<String>();
        receipt.add("Not meant to be a chain name XXXVDSDFSDSDFSDSADSDFSAF");
        String quizino=chains.findChain(receipt);
        assertEquals(StringCommon.EMPTY, quizino);
    }

    @Test
    public void emptyReceiptShouldReturnEmptyChainString() throws Exception{
        final List<String> chainLines=new ArrayList<String>();
        chainLines.add("76:Quizino's Subs:Restaurant:QuizinosSubs");
        chainLines.add("81:SAFEWAY,Safeway:Grocery,Health:Safeway");
        chainLines.add("82:Saje West Edmonton:Health:SajeWestEdmonton");
        chainLines.add("83:Save-on-foods:Grocery:SaveOnFoods");
        GenericChains chains=new GenericChains(chainLines);
        String quizino=chains.findChain(new ArrayList<String>());
        assertEquals(StringCommon.EMPTY, quizino);
    }

    @Test
    public void receipt1LineTest1() throws Exception{
        final List<String> chainLines=new ArrayList<String>();
        chainLines.add("76:Quizino's Subs:Restaurant:QuizinosSubs");
        chainLines.add("81:SAFEWAY,Safeway:Grocery,Health:Safeway");
        chainLines.add("82:Saje West Edmonton:Health:SajeWestEdmonton");
        chainLines.add("83:Save-on-foods:Grocery:SaveOnFoods");
        GenericChains chains=new GenericChains(chainLines);
        final List<String> receipt=new ArrayList<String>();
        receipt.add("Quizino's Subs");
        String quizino=chains.findChain(receipt);
        assertEquals("QuizinosSubs", quizino);
    }

    @Test
    public void receipt1LineTest2() throws Exception{
        final List<String> chainLines=new ArrayList<String>();
        chainLines.add("76:Quizino's Subs:Restaurant:QuizinosSubs");
        chainLines.add("81:SAFEWAY,Safeway:Grocery,Health:Safeway");
        chainLines.add("82:Saje West Edmonton:Health:SajeWestEdmonton");
        chainLines.add("83:Save-on-foods:Grocery:SaveOnFoods");
        GenericChains chains=new GenericChains(chainLines);
        final List<String> receipt=new ArrayList<String>();
        receipt.add("SAFEWAY");
        String store=chains.findChain(receipt);
        assertEquals("Safeway", store);
    }

    @Test
    public void receipt1LineTest2CaseInsensitive() throws Exception{
        final List<String> chainLines=new ArrayList<String>();
        chainLines.add("76:Quizino's Subs:Restaurant:QuizinosSubs");
        chainLines.add("81:SAFEWAY,Safeway:Grocery,Health:Safeway");
        chainLines.add("82:Saje West Edmonton:Health:SajeWestEdmonton");
        chainLines.add("83:Save-on-foods:Grocery:SaveOnFoods");
        GenericChains chains=new GenericChains(chainLines);
        final List<String> receipt=new ArrayList<String>();
        receipt.add("safeway");
        String store=chains.findChain(receipt);
        assertEquals("Safeway", store);
    }
}
