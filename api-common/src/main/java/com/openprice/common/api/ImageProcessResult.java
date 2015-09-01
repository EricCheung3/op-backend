package com.openprice.common.api;

import lombok.Getter;
import lombok.Setter;

public class ImageProcessResult {

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String fileName;

    @Getter @Setter
    private String ocrResult;

}
