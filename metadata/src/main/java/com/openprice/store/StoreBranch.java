package com.openprice.store;

import com.openprice.store.data.StoreBranchData;

public class StoreBranch {

    private final StoreBranchData storeBranchData;

    public StoreBranch(final StoreBranchData storeBranchData) {
        this.storeBranchData = storeBranchData;
    }

    public static StoreBranch EmptyStoreBranch(){
        return new StoreBranch(StoreBranchData.empty());
    }

    public String getGstNumber(){
        return storeBranchData.getGstNumber();
    }

    public String getName() {
        return storeBranchData.getName();
    }

    public String getPhone() {
        return storeBranchData.getPhone();
    }

    public String getStoreId(){
        return storeBranchData.getStoreId();
    }

    public String getSlogan(){
        return storeBranchData.getSlogan();
    }

    public String getAddress1(){
        return storeBranchData.getAddress1();
    }

    public String getAddress2(){
        return storeBranchData.getAddress2();
    }

    public String getCity(){
        return storeBranchData.getCity();
    }

    public String getPostCode(){
        return storeBranchData.getPostCode();
    }

    public String getState(){
        return storeBranchData.getState();
    }

    public String getCountry(){
        return storeBranchData.getCountry();
    }

    @Override
    public String toString(){
        return storeBranchData.toString();
    }

}
