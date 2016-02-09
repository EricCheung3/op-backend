package com.openprice.store;

import com.openprice.common.StringCommon;
import com.openprice.store.data.Address;
import com.openprice.store.data.StoreBranchData;

public class StoreBranch {

    private final StoreBranchData storeBranchData;

    public StoreBranch(final StoreBranchData storeBranchData) {
        this.storeBranchData = storeBranchData;
    }

    public static StoreBranch EmptyStoreBranch(){
        return new StoreBranch(new StoreBranchData(
                    StringCommon.EMPTY,
                    StringCommon.EMPTY,
                    StringCommon.EMPTY,
                    StringCommon.EMPTY,
                    Address.emptyAddress(),
                    StringCommon.EMPTY
                    ));
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

    public Address getAddress(){
        return storeBranchData.getAddress();
    }

}
