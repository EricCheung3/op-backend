package com.openprice.parser.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.openprice.parser.common.Levenshtein;
import com.openprice.parser.common.StringCommon;
import com.openprice.parser.data.DoubleFieldPair;
import com.openprice.parser.data.FieldName;
import com.openprice.parser.exception.FieldNameNotExistException;

/**
 * this is an interesting design. Then I don't have to call the reflect Method at run time
 *
 */
public class StoreMap {

    private final StoreBranch store;
    public StoreBranch store(){return store;}

    //mapping a fieldName to the value in the store
    private final Map<FieldName, String> fieldToValue=new HashMap<FieldName, String>();
    public Map<FieldName, String> fieldToValue(){return fieldToValue;}

    private StoreMap(final StoreBranch st){
        store=st;
        fieldToValue.put(FieldName.AddressLine1, store.getAddress().getLine1());
        fieldToValue.put(FieldName.AddressLine2, store.getAddress().getLine2());
        fieldToValue.put(FieldName.AddressCity, store.getAddress().getCity());
        fieldToValue.put(FieldName.AddressProv, store.getAddress().getProv());
        fieldToValue.put(FieldName.AddressPost, store.getAddress().getPost());
        //		fieldToValue.put(FieldNameAddressLines.Chain, store.chainName());
        //		fieldToValue.put(FieldNameAddressLines.AddressCountry, add.country());
        fieldToValue.put(FieldName.StoreBranch, store.getBranchName());
        fieldToValue.put(FieldName.GstNumber, store.getGstNumber());
        fieldToValue.put(FieldName.Phone, store.getPhone());
        fieldToValue.put(FieldName.Slogan, store.getSlogan());
    }

    public static StoreMap fromStore(final StoreBranch st){
        return new StoreMap(st);
    }

    public String value(final FieldName f) throws FieldNameNotExistException{
        if(!fieldToValue.containsKey(f))
            throw new FieldNameNotExistException("fieldName "+f+" was not contained in this class's map.");
        return fieldToValue.get(f);
    }

    /**
     * all the fields with a match score bigger than the given threshold
     * @param lineStr
     * @param threshold
     * @return
     */
    public List<DoubleFieldPair> allFieldMatchScores(final String lineStr, final double threshold)
            throws FieldNameNotExistException{
        final List<DoubleFieldPair> list=new ArrayList<DoubleFieldPair>();
        String lowerStr = lineStr.toLowerCase();
        for (FieldName fName : fieldToValue.keySet()) {
            double score=StringCommon.bestSliceMatching(lowerStr, value(fName).toLowerCase());
            if (score > threshold) {
                list.add(new DoubleFieldPair(score, fName));
            }
        }
        return list;
    }

    /**
     * get the mac score that matches the given string across all the fields
     * @param  lineStr given string
     * @return         [description]
     */
    public DoubleFieldPair maxFieldMatchScore(final String lineStr){
        double scoreMax=-1;
        FieldName matchedField=null;
        // logger.debug("lineStr="+lineStr);
        String lowerStr=lineStr.toLowerCase();
        for(FieldName fName:fieldToValue().keySet()){
            //			 logger.debug("\n@fieldLine().get(" + fName+ ")="+fieldLine().get(fName));
            double score=Levenshtein.compare(lowerStr, fieldToValue().get(fName).toLowerCase());
            //			 logger.debug("@score="+score);
            if(score>scoreMax){
                scoreMax=score;
                matchedField=fName;
            }
        }
        // logger.debug("scoreMax="+scoreMax);
        return new DoubleFieldPair(scoreMax, matchedField);
    }

    //TODO: Here the Store was not considered in comparing. Only the map was considered
    //I feel the two score functions should be separated into another class.

    @Override
    public boolean equals(Object that){
        if(that==null) return false;
        if(getClass() != that.getClass()) return false;
        final StoreMap another=(StoreMap)that;
        if( (this.fieldToValue()==null)? (another.fieldToValue()!=null)
                : !this.fieldToValue().equals(another.fieldToValue()) )
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.fieldToValue().hashCode();
        return hash;
    }

    @Override
    public String toString(){
        return store().toString();
    }

}

