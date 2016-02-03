package com.openprice.store;

import com.openprice.store.data.StoreBranchData;

public class StoreBranch {

    private final StoreBranchData storeBranchData;

    public StoreBranch(final StoreBranchData storeBranchData) {
        this.storeBranchData = storeBranchData;
    }

    public String getName() {
        return storeBranchData.getName();
    }

    public String getPhone() {
        return storeBranchData.getPhone();
    }
}
