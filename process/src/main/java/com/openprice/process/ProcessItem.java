package com.openprice.process;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ProcessItem {

    private String imageId;

    private String ownerId;

    private String requesterId;

    private Date addTime;

}
