package com.openprice.ocr.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ImageProcessRequest {
    @Getter @Setter
    private String userId;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String fileName;

}
