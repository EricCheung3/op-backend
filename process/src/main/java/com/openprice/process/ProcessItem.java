package com.openprice.process;

import java.util.Date;

import com.openprice.domain.receipt.ReceiptImage;

import lombok.Getter;
import lombok.Setter;

public class ProcessItem {

    @Getter @Setter
    private ReceiptImage image;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private Date addTime;

}
