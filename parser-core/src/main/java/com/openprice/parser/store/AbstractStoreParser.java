package com.openprice.parser.store;

import com.openprice.parser.LineFinder;
import com.openprice.parser.common.Levenshtein;
import com.openprice.parser.common.StringCommon;
import com.openprice.parser.data.FieldSet;
import com.openprice.parser.data.ValueLine;

public abstract class AbstractStoreParser implements StoreParser {
    private final LineFinder lineFinder;
    private final Config config;

    public AbstractStoreParser(final LineFinder lineFinder, final Config config) {
        this.lineFinder = lineFinder;
        this.config = config;
    }

    @Override
    public void checkFields(final FieldSet fields)throws Exception{
        //        if(fields.chain().getLine()<0){
        //            warningLogger().addWarning(-1, "No Chain line found. No line starts with "+config().chain(), "Perhaps the receipt picture is incomplete. retake a picture?");
        //        }
        //        if(fields.storeBranch().getLine()<0){
        //            warningLogger().addWarning(-1, "No Branch line found. No line starts with "+config().storeBranchHeader(), "Perhaps the line was not taken into the picture. retake a picture?");
        //        }
        //
        //        if(fields.total().getLine()<0){
        //            warningLogger().addWarning(-1, "No total line found. No line starts with "+config().totalHeader(), "Perhaps the line was not taken into the picture. retake a picture?");
        //        }
        //        if(fields.cashier().getLine()<0){
        //            warningLogger().addWarning(-1, "No CashierLine found. No line starts with "+config().cashierHeader(), "Perhaps the cashier line was taken into the picture. retake a picture?");
        //        }
        //        if(fields.account().getLine()<0)
        //            //throw new AccountLineException("No ACOUNT line found. accoutnLine="+accountLine);
        //            warningLogger().addWarning(-1, "No Account Line found. No line starting with "+config().accountHeader(), "Perhaps the receipt is incomplete or editing the Account line.");
        //        if(fields.author().getLine()<0)
        //            //throw new AuthorLineException("No AUTHOR line found. authorLine="+authorLine);
        //            warningLogger().addWarning(-1, "No Authorization Line found. No line starting with "+config().authorHeader(), "Perhaps the receipt is incomplete or editing the Authorization line.");
        //
        //        if(fields.date().getLine()<0)
        //            warningLogger().addWarning(-1, "No Date line found. No line starting with "+config().dateHeader()+"was found", "Perhaps the receipt is incomplete or editing the Authorization line.");
    }


    /** value is located at the tail */
    public ValueLine valueLineAtTail(final int line, final String header){
        String card="";
        try{
            card=getTail(lineFinder.getLines().get(line).trim(), header);
        }catch(Exception e){
            //            warningLogger().addWarning(line, "getTail:"+e.getMessage(),
            //                    "make sure the line starts with "+header);
        }
        return ValueLine.builder().value(card).line(line).build();
    }

    //get the tail after the header
    public String getTail(final String str, String head) throws Exception{
        if(head==null || head.isEmpty()) return str;
        head=head.replaceAll("\\s+", "");
        String headStr=StringCommon.firstNonSpaceChars(str, head.length());
        if(Levenshtein.compare(head, headStr)<=config.similarityThresholdOfTwoStrings()){
            throw new Exception("Head of str and head isn't similar. "+headStr+", "+head+", str="+str);
        }
        final String tail=StringCommon.removeFirstNonSpaceChars(str, head).trim();
        return tail;
    }

    public String getTailOnlyDigitsDots(final String str, final String head)throws Exception{
        return StringCommon.getOnlyDigitsDots(getTail(str, head));
    }

    @Override
    public ValueLine parseApproved(int lineNumber) throws Exception{
        return ValueLine.builder().value(lineFinder.getLines().get(lineNumber)).line(lineNumber).build();
    }

    @Override
    public ValueLine parseChain(int number) throws Exception{
        return ValueLine.builder().value(config.chain()).line(number).build();
    }

    @Override
    public ValueLine parseChainID(int number) throws Exception{
        // return new ValueLine(config.chainID(), number);
        return ValueLine.builder().value(lineFinder.getLines().get(number)).line(number).build();
    }

    public ValueLine parseSlogan(int lineNumber) throws Exception{
        return ValueLine.builder().value(lineFinder.getLines().get(lineNumber)).line(lineNumber).build();
    }

    public ValueLine parseThankyou(int lineNumber) throws Exception{
        return ValueLine.builder().value(lineFinder.getLines().get(lineNumber)).line(lineNumber).build();
    }

    //the line number at which parsing items should stop
    public int itemStopLine(final FieldSet fields){
        int stopLine =
                Math.max(fields.gstAmount().getLine(),
                        Math.max(
                                fields.total().getLine(),
                                fields.subTotal().getLine()
                                )
                        );
        if(stopLine<0) stopLine=Integer.MAX_VALUE;
        return stopLine;
    }

}
