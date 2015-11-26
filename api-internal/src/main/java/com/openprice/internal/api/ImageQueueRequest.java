package com.openprice.internal.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageQueueRequest {

    private String imageId;

    private String accountId;

}
