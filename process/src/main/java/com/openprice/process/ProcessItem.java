package com.openprice.process;

import java.util.Date;

import com.openprice.domain.receipt.ReceiptImage;

import lombok.Getter;
import lombok.Setter;

public class ProcessItem {

    @Getter @Setter
    private ReceiptImage image;

    @Getter @Setter
    private String userId;

    @Getter @Setter
    private String username; // for end user, it is user's email

    @Getter @Setter
    private Date addTime;

}
