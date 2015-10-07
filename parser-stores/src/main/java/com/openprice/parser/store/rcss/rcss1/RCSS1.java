package com.openprice.parser.store.rcss.rcss1;

import com.openprice.parser.data.FieldSet;
import com.openprice.parser.data.ValueLine;
import com.openprice.parser.store.Config;
import com.openprice.parser.store.StoreParser;

public class RCSS1 implements StoreParser {

    @Override
    public void checkFields(FieldSet fields) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public ValueLine parseAddress(int lineNumber) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValueLine parseAccount(int lineNumber) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValueLine parseAuthor(int lineNumber) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValueLine parseApproved(int lineNumber) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValueLine parseCard(int lineNumber) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValueLine parseChain(int lineNumber) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValueLine parseChainID(int lineNumber) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValueLine parseCashier(int lineNumber) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValueLine parseDate(int lineNumber) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValueLine parseDate2(int lineNumber) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValueLine parseDeposit(int lineNumber) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValueLine parseGstAmount(int gstLineNumber) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValueLine parseGstNumber(int gstLineNumber) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] parseItemPrice(int startLine, String line, String priceTail) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValueLine parsePhone(int line) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValueLine parseRef(int lineNumber) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValueLine parseRecycle(int number) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValueLine parseSaving(int number, String matchHead) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValueLine parseStoreBranch(int number) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValueLine parseStoreID(int lineNumber) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValueLine parseSubTotal(int line) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValueLine parseTotal(int line) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValueLine parseTotalSold(int line) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int findDateLine(FieldSet fields) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int findCashierLine(FieldSet fields) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean receiptFinished(String fieldHead) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean notBlockHeader(int line) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Config storeConfig() {
        // TODO Auto-generated method stub
        return null;
    }

}
